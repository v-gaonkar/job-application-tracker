package com.devmadesimple.jobtracker.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
public class DashboardResponse implements Serializable {

    private long totalApplications;

    private Map<String, Long> statusBreakdown;

    private long upcomingInterviewsCount;

    private long staleApplicationsCount;

    private Map<String, Long> applicationsPerWeek;
}
