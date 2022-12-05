DROP TABLE IF EXISTS queue_log;
CREATE TABLE queue_log(
        queue_name VARCHAR(256), message_uuid VARCHAR(256), inserted_at DATETIME(6),
        is_pushed BOOLEAN DEFAULT false, pushed_at DATETIME(6),
        is_received BOOLEAN DEFAULT false, received_at DATETIME(6),
        is_consumed BOOLEAN DEFAULT false, consumed_at DATETIME(6),
        comment LONGTEXT
);