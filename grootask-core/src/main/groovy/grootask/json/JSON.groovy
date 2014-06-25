package grootask.json

import com.fasterxml.jackson.databind.ObjectMapper

class JSON {

    static <T> T fromJson(String json, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper()

        return mapper.readValue(json, clazz)
    }

    static <T> String toJson(T object) {
        ObjectMapper mapper = new ObjectMapper()
        StringWriter writer = new StringWriter()

        mapper.writeValue(writer, object)

        return writer.toString()
    }


}
