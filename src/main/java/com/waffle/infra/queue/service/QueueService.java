package com.waffle.infra.queue.service;

import java.util.UUID;

public interface QueueService<T> {

    UUID push(T t);

    @SuppressWarnings("unused")
    void listen(String message);
}
