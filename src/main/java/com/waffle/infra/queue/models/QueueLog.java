package com.waffle.infra.queue.models;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class QueueLog {
    UUID uuid;
    String queueName;
    LocalDateTime insertedAt;
    boolean isPushed;
    boolean isReceived;
    boolean isConsumed;
    LocalDateTime pushedAt;
    LocalDateTime receivedAt;
    LocalDateTime consumedAt;
    String comment;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public LocalDateTime getInsertedAt() {
        return insertedAt;
    }

    public void setInsertedAt(LocalDateTime insertedAt) {
        this.insertedAt = insertedAt;
    }

    public boolean isPushed() {
        return isPushed;
    }

    public void setPushed(boolean pushed) {
        isPushed = pushed;
    }

    public boolean isReceived() {
        return isReceived;
    }

    public void setReceived(boolean received) {
        isReceived = received;
    }

    public boolean isConsumed() {
        return isConsumed;
    }

    public void setConsumed(boolean consumed) {
        isConsumed = consumed;
    }

    public LocalDateTime getPushedAt() {
        return pushedAt;
    }

    public void setPushedAt(LocalDateTime pushedAt) {
        this.pushedAt = pushedAt;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public LocalDateTime getConsumedAt() {
        return consumedAt;
    }

    public void setConsumedAt(LocalDateTime consumedAt) {
        this.consumedAt = consumedAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueueLog queueLog = (QueueLog) o;
        return isPushed == queueLog.isPushed && isReceived == queueLog.isReceived && isConsumed == queueLog.isConsumed
                && uuid.equals(queueLog.uuid) && queueName.equals(queueLog.queueName) &&
                pushedAt.equals(queueLog.pushedAt) && Objects.equals(receivedAt, queueLog.receivedAt) &&
                Objects.equals(consumedAt, queueLog.consumedAt) && Objects.equals(comment, queueLog.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, queueName, isPushed, isReceived, isConsumed, pushedAt, receivedAt, consumedAt,
                comment);
    }
}
