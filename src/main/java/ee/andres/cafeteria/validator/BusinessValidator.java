package ee.andres.cafeteria.validator;

import com.fasterxml.jackson.databind.JsonNode;

public interface BusinessValidator<T> {
    boolean validate(T t);
    void validate(T t, JsonNode parameters);
}
