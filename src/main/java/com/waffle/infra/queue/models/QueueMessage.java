package com.waffle.infra.queue.models;


import com.waffle.infra.queue.models.utils.Jsonable;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class QueueMessage<T> implements Jsonable {
    private UUID id;
    private static final AtomicLong currentId = new AtomicLong(1);
    private T payload;

    public static <T> QueueMessage<T> with(T payload) {
        QueueMessage<T> m = new QueueMessage<>();
        m.id = generateId();
        m.payload = payload;
        return m;
    }

    private QueueMessage() {}


    private static synchronized UUID generateId() {
        return UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "QueueMessage{" +
                "id=" + id +
                ", payload=" + payload +
                '}';
    }
}
