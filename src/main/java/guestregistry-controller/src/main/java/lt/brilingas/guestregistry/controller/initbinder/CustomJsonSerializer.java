package lt.brilingas.guestregistry.controller.initbinder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.springframework.boot.jackson.JsonComponent;
import java.io.IOException;
import java.lang.reflect.Type;

@JsonComponent  //to apply for all instances of ObjectMapper (of Jackson)
public class CustomJsonSerializer {
    public static class StringTrimToNullJsonDeserializer extends StringDeserializer {
        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            String str = super.deserialize(jsonParser, deserializationContext);
            return trimStringToNull(str);
        }
    }

    public static class StringTrimToNullJsonSerializer extends StdScalarSerializer<String> {
        private final StringSerializer stringSerializer = new StringSerializer();

        public StringTrimToNullJsonSerializer() {
            super(String.class, false);
        }

        @Override
        public boolean isEmpty(SerializerProvider provider, String value) {
            return trimStringToNull(value) == null;
        }

        @Override
        public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            stringSerializer.serialize(trimStringToNull(value), gen, provider);
        }

        @Override
        public final void serializeWithType(String value, JsonGenerator gen, SerializerProvider provider,
                                            TypeSerializer typeSer) throws IOException {
            super.serializeWithType(value, gen, provider, typeSer);
        }

        @Override
        public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
            return stringSerializer.getSchema(provider, typeHint);
        }

        @Override
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint)
                throws JsonMappingException {
            stringSerializer.acceptJsonFormatVisitor(visitor, typeHint);
        }
    }

    private static String trimStringToNull(String str) {
        if (str == null) {
            return null;
        } else {
            String value = str.trim();
            if (value.isEmpty()) {
                return null;
            } else {
                return value;
            }
        }
    }
}
