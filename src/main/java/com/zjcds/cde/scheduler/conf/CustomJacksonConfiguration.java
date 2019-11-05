package com.zjcds.cde.scheduler.conf;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zjcds.cde.scheduler.domain.enums.EnumValue;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * jackson 相关配置
 *
 * @author luokp on 2018/5/24.
 */
@JsonComponent
public class CustomJacksonConfiguration {

    /**
     * EnumValue序列化
     */
    public static class EnumValueSerializer extends JsonSerializer<EnumValue> {
        @Override
        public void serialize(EnumValue value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
            Optional<EnumValue> data = Optional.of(value);
            if (data.isPresent()) {
                Map<String, String> map = new HashMap<>();
                if (value.getKey() == null) {
                    map.put("key", null);
                } else {
                    map.put("key", value.getKey().toString());
                }
                if (value.getValue() == null) {
                    map.put("value", null);
                } else {
                    map.put("value", value.getValue().toString());
                }

                jsonGenerator.writeObject(map);
            } else {
                jsonGenerator.writeString("");
            }
        }
    }

}
