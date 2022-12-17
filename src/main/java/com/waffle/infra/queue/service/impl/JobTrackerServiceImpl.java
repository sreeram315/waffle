package com.waffle.infra.queue.service.impl;

import com.waffle.infra.queue.dao.JobStatusDao;
import com.waffle.infra.queue.models.JobStatus;
import com.waffle.infra.queue.service.JobTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class JobTrackerServiceImpl implements JobTrackerService {

    @Autowired
    JobStatusDao jobStatusDao;

    @Override
    @GetMapping("/queue/status/get")
    public ResponseEntity<String> getJobStatus(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        JobStatus jobStatus = jobStatusDao.getJobStatus(uuid);
        return ResponseEntity.ok(jobStatus.toJson());
    }
}
