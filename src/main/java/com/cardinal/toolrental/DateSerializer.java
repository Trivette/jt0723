package com.cardinal.toolrental;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@JsonComponent
class DateSerializer extends JsonSerializer<Date> {

    public static final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());

    public DateSerializer() {
        this(null);
    }

    public DateSerializer(Class<Date> t) {
        super();
    }

    @Override
    public void serialize(
            Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {


        jgen.writeString(sdf.format(value));

    }
}