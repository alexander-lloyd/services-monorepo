package com.alexlloyd.tvguide.serializer;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZonedDateTimeSerializerTest {
    private XmlMapper xmlMapper;

    @BeforeEach
    public void beforeEach() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss xx");
        ZonedDateTimeSerializer dateTimeSerializer = new ZonedDateTimeSerializer(formatter);
        SimpleModule module = new SimpleModule();
        module.addSerializer(ZonedDateTime.class, dateTimeSerializer);

        xmlMapper = new XmlMapper();
        xmlMapper.registerModule(module);
    }

    @DisplayName("should serialize a ZonedDateTime")
    @Test
    public void testSerialization() throws JsonProcessingException {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2018, 1, 5, 0, 0, 0, 0, ZoneId.of("UTC"));

        String serialized = xmlMapper.writeValueAsString(zonedDateTime);

        assertEquals("<ZonedDateTime>20180105000000 +0000</ZonedDateTime>", serialized);
    }

    @DisplayName("should serialize a ZonedDateTime")
    @Test
    public void testSerialization2() throws JsonProcessingException {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(0, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"));

        String serialized = xmlMapper.writeValueAsString(zonedDateTime);

        assertEquals("<ZonedDateTime>00010101000000 +0000</ZonedDateTime>", serialized);
    }
}