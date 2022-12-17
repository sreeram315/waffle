package com.waffle.infra.queue.exceptions;

public class JsonConversionExcetion extends RuntimeException {
    public JsonConversionExcetion(String message) {
        super(message);
    }
}
