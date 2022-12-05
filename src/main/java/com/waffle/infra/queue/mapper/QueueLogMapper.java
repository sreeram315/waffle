package com.waffle.infra.queue.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper
public interface QueueLogMapper {

    @Select("""
            INSERT INTO queue_log(queue_name, message_uuid, inserted_at)
            VALUES (#{queue_name},
                    #{message_uuid, typeHandler = com.waffle.infra.queue.typehandler.UuidTypeHandler},
                    #{inserted_at})
            """)
    void insert(@Param("queue_name") String queue_name,
                @Param("message_uuid") UUID message_uuid,
                @Param("inserted_at") LocalDateTime now);

    @Update("""
            UPDATE queue_log
            SET is_pushed=true, pushed_at=#{now}
            WHERE message_uuid=#{message_uuid, typeHandler = com.waffle.infra.queue.typehandler.UuidTypeHandler}
            """)
    void update_as_pushed(@Param("message_uuid") UUID message_uuid, @Param("now") LocalDateTime now);

    @Update("""
            UPDATE queue_log
            SET is_consumed=true, consumed_at=#{now}
            WHERE message_uuid=#{message_uuid, typeHandler = com.waffle.infra.queue.typehandler.UuidTypeHandler}
            """)
    void update_as_consumed(@Param("message_uuid") UUID message_uuid, @Param("now") LocalDateTime now);

    @Update("""
            UPDATE queue_log
            SET is_received=true, received_at=#{now}
            WHERE message_uuid=#{message_uuid, typeHandler = com.waffle.infra.queue.typehandler.UuidTypeHandler}
            """)
    void update_as_received(@Param("message_uuid") UUID uuid, @Param("now") LocalDateTime now);

    @Update("""
            UPDATE queue_log
            SET comment=#{message}
            WHERE message_uuid=#{message_uuid, typeHandler = com.waffle.infra.queue.typehandler.UuidTypeHandler}
            """)
    void update_comment(@Param("message_uuid") UUID uuid, @Param("message") String message);
}
