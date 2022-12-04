package com.waffle.queue.models;

import com.google.gson.Gson;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class QueueMessage<T> {
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

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", payload=" + payload +
                '}';
    }
}
