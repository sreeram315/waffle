package com.waffle.infra.queue.models.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.waffle.infra.queue.exceptions.JsonConversionExcetion;

import java.util.Map;

public interface Jsonable {

    default String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new JsonConversionExcetion(e.getMessage());
        }
    }

    static <T, R> String toJson(Map<T, R> map) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new JsonConversionExcetion(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    default <T extends Jsonable> T fromJson(String json) {
        return (T) fromJson(json, this.getClass());
    }

    static <T> T fromJson(String json, Class<T> type) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            return mapper.readValue(json, type);
        }
        catch (JsonProcessingException e){
            throw new JsonConversionExcetion(e.getMessage());
        }
    }

}
