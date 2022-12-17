package com.waffle.infra.queue.service;

import com.waffle.infra.queue.models.JobStatus;
import org.springframework.http.ResponseEntity;

public interface JobTrackerService {
    ResponseEntity<String> getJobStatus(String uuid);
}
