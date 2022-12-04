CREATE TABLE queue_log(
        queue_name VARCHAR(256), message_uuid VARCHAR(256), inserted_at DATETIME,
        is_pushed BOOLEAN DEFAULT false, pushed_at DATETIME,
        is_received BOOLEAN DEFAULT false, received_at DATETIME,
        is_consumed BOOLEAN DEFAULT false, consumed_at DATETIME
);