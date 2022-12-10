package ee.andres.cafeteria.exception;

import com.fasterxml.jackson.databind.JsonNode;

public class ReservationsExceededException extends RuntimeException {
    private final JsonNode resolvedLabels;
    public ReservationsExceededException(String message, JsonNode resolvedLabels) {
        super(message);
        this.resolvedLabels = resolvedLabels;
    }

    public JsonNode getResolvedLabels() {
        return resolvedLabels;
    }
}
