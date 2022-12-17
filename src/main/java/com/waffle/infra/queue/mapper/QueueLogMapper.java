package com.waffle.infra.queue.mapper;

import com.waffle.infra.queue.mapper.typehandler.UuidTypeHandler;
import com.waffle.infra.queue.models.QueueLog;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper
public interface QueueLogMapper {

    @Select("""
            INSERT INTO queue_log(queue_name, message_uuid, inserted_at)
            VALUES (#{queue_name},
                    #{message_uuid, typeHandler = com.waffle.infra.queue.mapper.typehandler.UuidTypeHandler},
                    #{inserted_at})
            """)
    void insert(@Param("queue_name") String queue_name,
                @Param("message_uuid") UUID message_uuid,
                @Param("inserted_at") LocalDateTime now);

    @Update("""
            UPDATE queue_log
            SET is_pushed=true, pushed_at=#{now}
            WHERE message_uuid=#{message_uuid, typeHandler = com.waffle.infra.queue.mapper.typehandler.UuidTypeHandler}
            """)
    void updateAsPushed(@Param("message_uuid") UUID message_uuid, @Param("now") LocalDateTime now);

    @Update("""
            UPDATE queue_log
            SET is_consumed=true, consumed_at=#{now}
            WHERE message_uuid=#{message_uuid, typeHandler = com.waffle.infra.queue.mapper.typehandler.UuidTypeHandler}
            """)
    void updateAsConsumed(@Param("message_uuid") UUID message_uuid, @Param("now") LocalDateTime now);

    @Update("""
            UPDATE queue_log
            SET is_received=true, received_at=#{now}
            WHERE message_uuid=#{message_uuid, typeHandler = com.waffle.infra.queue.mapper.typehandler.UuidTypeHandler}
            """)
    void updateAsReceived(@Param("message_uuid") UUID uuid, @Param("now") LocalDateTime now);

    @Update("""
            UPDATE queue_log
            SET comment=#{message}
            WHERE message_uuid=#{message_uuid, typeHandler = com.waffle.infra.queue.mapper.typehandler.UuidTypeHandler}
            """)
    void updateComment(@Param("message_uuid") UUID uuid, @Param("message") String message);


    @Results(id = "queueLogMap", value = {
            @Result(property = "uuid", column = "message_uuid", typeHandler = UuidTypeHandler.class),
            @Result(property = "queueName", column = "queue_name"),
            @Result(property = "insertedAt", column = "inserted_at"),
            @Result(property = "isPushed", column = "is_pushed"),
            @Result(property = "pushedAt", column = "pushed_at"),
            @Result(property = "isReceived", column = "is_received"),
            @Result(property = "receivedAt", column = "received_at"),
            @Result(property = "isConsumed", column = "is_consumed"),
            @Result(property = "consumedAt", column = "consumed_at"),
            @Result(property = "comment", column = "comment")
    })
    @Select("""
            SELECT  message_uuid, queue_name, inserted_at, is_pushed, pushed_at, is_received, received_at,
                    is_consumed, consumed_at, comment
            FROM    queue_log
            WHERE   message_uuid=#{message_uuid, typeHandler = com.waffle.infra.queue.mapper.typehandler.UuidTypeHandler}
            """)
    QueueLog getQueueLog(@Param("message_uuid") UUID uuid);
}
