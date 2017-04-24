package application.ext

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature

import javax.ws.rs.ext.ContextResolver
import javax.ws.rs.ext.Provider

@Provider
class CustomObjectMapperProvider implements ContextResolver<ObjectMapper> {

    final ObjectMapper defaultObjectMapper

    CustomObjectMapperProvider() {
        defaultObjectMapper = createDefaultMapper()
    }

    @Override
    ObjectMapper getContext(Class<?> type) {
        return defaultObjectMapper
    }

    private static ObjectMapper createDefaultMapper() {
        final ObjectMapper result = new ObjectMapper()
                .setPropertyNamingStrategy( PropertyNamingStrategy.SNAKE_CASE)
                //.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
                .disable(SerializationFeature.INDENT_OUTPUT)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)

        return result
    }

}
