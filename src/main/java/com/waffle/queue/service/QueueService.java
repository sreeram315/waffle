package com.waffle.queue.service;

import java.util.UUID;

public interface QueueService<T> {

    UUID send(T t);

    @SuppressWarnings("unused")
    void listen(String message);
}
