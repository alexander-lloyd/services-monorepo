package com.alexlloyd.tvguide.config;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.alexlloyd.tvguide.serializer.ZonedDateTimeDeserializer;
import com.alexlloyd.tvguide.serializer.ZonedDateTimeSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss xx");

    @Bean
    public XmlMapper xmlMapper() {
        XmlMapper xmlMapper = new XmlMapper();
        SimpleModule module = new SimpleModule();

        registerSerializers(module);
        registerDeserializers(module);

        xmlMapper.registerModule(module);

        return xmlMapper;
    }

    private void registerSerializers(SimpleModule module) {
        module.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer(formatter));
    }

    private void registerDeserializers(SimpleModule module) {
        module.addDeserializer(ZonedDateTime.class, new ZonedDateTimeDeserializer(formatter));
    }
}