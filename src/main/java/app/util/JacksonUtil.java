package app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readValue(String source, Class<T> clazz) throws JsonProcessingException {
        return (T) objectMapper.readValue(source, clazz);
    }

    public static <T> String writeValue(T value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }
}