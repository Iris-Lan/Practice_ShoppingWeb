package com.Iris.demo.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class JsonUtil {

    private static JsonUtil _instance = new JsonUtil();

    private ObjectMapper jsonMapper;

    private JsonUtil() {
        jsonMapper = new ObjectMapper();
    }

    public static String getJsonString(Object obj){
        try {
            return _instance.jsonMapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
