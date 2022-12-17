package com.waffle.infra.queue.dao;

import com.waffle.infra.queue.mapper.QueueLogMapper;
import com.waffle.infra.queue.models.JobStatus;
import com.waffle.infra.queue.models.QueueLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JobStatusDao {

    @Autowired
    QueueLogMapper queueLogMapper;

    public JobStatus getJobStatus(UUID uuid) {
        QueueLog queueLog = queueLogMapper.getQueueLog(uuid);
        return JobStatus.JobStatusBuilder.getNew(uuid)
                .withQueueName(queueLog.getQueueName())
                .withInsertedAt(queueLog.getInsertedAt())
                .withIsPushed(queueLog.isPushed())
                .withIsReceived(queueLog.isReceived())
                .withIsConsumed(queueLog.isConsumed())
                .withPushedAt(queueLog.getPushedAt())
                .withReceivedAt(queueLog.getReceivedAt())
                .withConsumedAt(queueLog.getConsumedAt())
                .withComment(queueLog.getComment())
                .build();
    }
}
