package com.devmadesimple.jobtracker.service;

import com.devmadesimple.jobtracker.dto.request.RoundRequest;
import com.devmadesimple.jobtracker.dto.response.RoundResponse;
import com.devmadesimple.jobtracker.entity.Application;
import com.devmadesimple.jobtracker.entity.Round;
import com.devmadesimple.jobtracker.exception.ApplicationNotFoundException;
import com.devmadesimple.jobtracker.exception.DuplicateRoundException;
import com.devmadesimple.jobtracker.exception.RoundNotFoundException;
import com.devmadesimple.jobtracker.mapper.RoundMapper;
import com.devmadesimple.jobtracker.repository.ApplicationRepository;
import com.devmadesimple.jobtracker.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoundService {

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    RoundMapper roundMapper;

    @Autowired
    CurrentUserProvider currentUserProvider;

    public Long currentUserId() {
        return currentUserProvider.getCurrentUser().getId();
    }

    private Application getOwnedApplication(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id " + applicationId));
        if (!application.getUser().getId().equals(currentUserProvider.getCurrentUser().getId())) {
            throw new ApplicationNotFoundException("Application not found with id " + applicationId);
        }
        return application;
    }

    private Round getOwnedRound(Long id) {
        Round round = roundRepository.findById(id)
                .orElseThrow(() -> new RoundNotFoundException("Round not found with id " + id));
        if (!round.getApplication().getUser().getId().equals(currentUserProvider.getCurrentUser().getId())) {
            throw new RoundNotFoundException("Round not found with id " + id);
        }
        return round;
    }

    @CacheEvict(value = "dashboard", key = "#root.target.currentUserId()")
    public RoundResponse addRound(RoundRequest request) {
        Application application = getOwnedApplication(request.getApplicationId());

        Round round = new Round();
        round.setRound(request.getRound());
        round.setStatus(request.getStatus());
        round.setInterviewDate(request.getInterviewDate());
        round.setNotes(request.getNotes());
        round.setApplication(application);

        try {
            Round response = roundRepository.save(round);
            return roundMapper.toResponse(response);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateRoundException("An interview round with this name and date already exists for this application");
        }
    }

    public List<RoundResponse> getRoundsForApplication(Long applicationId) {
        getOwnedApplication(applicationId);
        return roundRepository.findByApplication_Id(applicationId).stream()
                .map(roundMapper::toResponse)
                .toList();
    }

    @CacheEvict(value = "dashboard", key = "#root.target.currentUserId()")
    public RoundResponse updateRound(RoundRequest request, Long id) {
        Round round = getOwnedRound(id);
        Application application = getOwnedApplication(request.getApplicationId());

        round.setRound(request.getRound());
        round.setStatus(request.getStatus());
        round.setInterviewDate(request.getInterviewDate());
        round.setNotes(request.getNotes());
        round.setApplication(application);

        try {
            Round response = roundRepository.save(round);
            return roundMapper.toResponse(response);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateRoundException("An interview round with this name and date already exists for this application");
        }
    }

    @CacheEvict(value = "dashboard", key = "#root.target.currentUserId()")
    public void deleteRound(Long id) {
        Round round = getOwnedRound(id);
        roundRepository.delete(round);
    }

    public List<Round> getRoundsWithinRange(LocalDateTime start, LocalDateTime end) {
        return roundRepository.findByInterviewDateBetween(start, end);
    }

    public List<RoundResponse> getAllRoundsForUser() {
        Long userId = currentUserProvider.getCurrentUser().getId();
        return roundRepository.findByApplication_User_Id(userId).stream()
                .map(roundMapper::toResponse)
                .toList();
    }
}