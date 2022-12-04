package com.mybatisSample.queue.dao;


import com.mybatisSample.queue.mapper.QueueLogMapper;
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

    public void update_push_as_success(UUID uuid) {
        queueLogMapper.update_as_pushed(uuid, LocalDateTime.now());
    }

    public void update_as_consumed(UUID uuid) {
        queueLogMapper.update_as_consumed(uuid, LocalDateTime.now());
    }

    public void update_as_received(UUID uuid) {
        queueLogMapper.update_as_received(uuid, LocalDateTime.now());
    }

    public void update_comment(UUID uuid, String message) {
        queueLogMapper.update_comment(uuid, message);
    }
}
