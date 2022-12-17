package com.waffle.infra.queue.dao;


import com.waffle.infra.queue.mapper.QueueLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class QueueLogDao {

    @Autowired
    QueueLogMapper queueLogMapper;

    public void insert(String queue_name, UUID uuid) {
        queueLogMapper.insert(queue_name, uuid, LocalDateTime.now());
    }

    public void updatePushAsSuccess(UUID uuid) {
        queueLogMapper.updateAsPushed(uuid, LocalDateTime.now());
    }

    public void updateAsConsumed(UUID uuid) {
        queueLogMapper.updateAsConsumed(uuid, LocalDateTime.now());
    }

    public void updateAsReceived(UUID uuid) {
        queueLogMapper.updateAsReceived(uuid, LocalDateTime.now());
    }

    public void updateComment(UUID uuid, String message) {
        queueLogMapper.updateComment(uuid, message);
    }
}
