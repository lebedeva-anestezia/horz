package com.nastik.horz.pojo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeSerializer extends StdSerializer<LocalDateTime> {

    public DateTimeSerializer() {
        this(null);
    }

    public DateTimeSerializer(Class<java.time.LocalDateTime> t) {
        super(t);
    }

    @Override
    public void serialize(
            LocalDateTime value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        jgen.writeObject(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(value.atZone(ZoneId.of("GMT"))));
    }
}
