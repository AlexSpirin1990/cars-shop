package com.cognizant.cars_shop.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class InstantSerializer extends JsonSerializer<Instant> {

    @Override
    public void serialize(Instant date,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
        throws IOException {
        jsonGenerator.writeObject(date.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
