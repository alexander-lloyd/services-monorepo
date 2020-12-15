package com.alexlloyd.tvguide.serializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {
    private final DateTimeFormatter formatter;

    public ZonedDateTimeSerializer(DateTimeFormatter formatter) {
        super();
        this.formatter = formatter;
    }

    @Override
    public void serialize(ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String formattedDate = zonedDateTime.format(this.formatter);
        jsonGenerator.writeString(formattedDate);
    }
}
