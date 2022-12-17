package com.waffle.infra.queue.models;


import com.waffle.infra.queue.models.utils.Jsonable;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.UUID;

public class JobStatus implements Jsonable {
    private UUID uuid;
    private String queueName;
    private LocalDateTime insertedAt;
    private boolean isPushed;
    private boolean isReceived;
    private boolean isConsumed;
    private LocalDateTime pushedAt;
    private LocalDateTime receivedAt;
    private LocalDateTime consumedAt;
    private String comment;

    private JobStatus() {}

    public UUID getUuid() {
        return uuid;
    }

    public String getQueueName() {
        return queueName;
    }

    public LocalDateTime getInsertedAt() {
        return insertedAt;
    }

    public boolean isPushed() {
        return isPushed;
    }

    public boolean isReceived() {
        return isReceived;
    }

    public boolean isConsumed() {
        return isConsumed;
    }

    public LocalDateTime getPushedAt() {
        return pushedAt;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public LocalDateTime getConsumedAt() {
        return consumedAt;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "JobStatus{" +
                "uuid=" + uuid +
                ", queueName='" + queueName + '\'' +
                ", insertedAt=" + insertedAt +
                ", isPushed=" + isPushed +
                ", isReceived=" + isReceived +
                ", isConsumed=" + isConsumed +
                ", pushedAt=" + pushedAt +
                ", receivedAt=" + receivedAt +
                ", consumedAt=" + consumedAt +
                ", comment='" + comment + '\'' +
                '}';
    }

    public static class JobStatusBuilder {
        private final JobStatus jobStatus;

        private JobStatusBuilder(UUID uuid) {
            jobStatus = new JobStatus();
            jobStatus.uuid = uuid;
        }

        public static JobStatusBuilder getNew(UUID uuid) {
            return new JobStatusBuilder(uuid);
        }

        public JobStatusBuilder withQueueName(String queueName) {
            jobStatus.queueName = queueName;
            return this;
        }

        public JobStatusBuilder withInsertedAt(LocalDateTime insertedAt) {
            jobStatus.insertedAt = insertedAt;
            return this;
        }

        public JobStatusBuilder withIsPushed(boolean isPushed) {
            jobStatus.isPushed = isPushed;
            return this;
        }

        public JobStatusBuilder withIsReceived(boolean isReceived) {
            jobStatus.isReceived = isReceived;
            return this;
        }

        public JobStatusBuilder withIsConsumed(boolean isConsumed) {
            jobStatus.isConsumed = isConsumed;
            return this;
        }

        public JobStatusBuilder withPushedAt(LocalDateTime pushedAt) {
            jobStatus.pushedAt = pushedAt;
            return this;
        }

        public JobStatusBuilder withReceivedAt(LocalDateTime receivedAt) {
            jobStatus.receivedAt = receivedAt;
            return this;
        }

        public JobStatusBuilder withConsumedAt(LocalDateTime consumedAt) {
            jobStatus.consumedAt = consumedAt;
            return this;
        }

        public JobStatusBuilder withComment(String comment) {
            jobStatus.comment = comment;
            return this;
        }

        public JobStatus build() {
            return jobStatus;
        }
    }

}
