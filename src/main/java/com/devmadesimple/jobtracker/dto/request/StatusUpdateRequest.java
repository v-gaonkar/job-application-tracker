package com.devmadesimple.jobtracker.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusUpdateRequest {
    @Pattern(regexp = "Applied|Under Process|Interview|Offer|Rejected|Withdrawn",
            message = "Invalid status value")
    private String status;
}