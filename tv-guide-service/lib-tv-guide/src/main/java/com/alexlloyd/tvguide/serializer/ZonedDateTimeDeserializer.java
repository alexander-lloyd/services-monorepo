package com.alexlloyd.tvguide.serializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {
    private final DateTimeFormatter formatter;

    public ZonedDateTimeDeserializer(DateTimeFormatter formatter) {
        super();
        this.formatter = formatter;
    }

    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String stringDate = jsonParser.getValueAsString();
        return ZonedDateTime.parse(stringDate, formatter);
    }
}
