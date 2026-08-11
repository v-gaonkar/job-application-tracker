package com.devmadesimple.jobtracker.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailExists(EmailAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<?> handleCompanyNotFound(CompanyNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SkillNameAlreadyExistsException.class)
    public ResponseEntity<?> handleSkillNameExists(SkillNameAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SkillNotFoundException.class)
    public ResponseEntity<?> handleSkillNotFound(SkillNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<?> handleApplicationNotFound(ApplicationNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateApplicationException.class)
    public ResponseEntity<?> handleDuplicateApplication(DuplicateApplicationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateRoundException.class)
    public ResponseEntity<?> handleDuplicateRound(DuplicateRoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RoundNotFoundException.class)
    public ResponseEntity<?> handleRoundNotFound(RoundNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailTemplateNotFoundException.class)
    public ResponseEntity<?> handleEmailTemplateNotFound(EmailTemplateNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<?> handleContactNotFound(ContactNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateContactException.class)
    public ResponseEntity<?> handleDuplicateContact(DuplicateContactException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
