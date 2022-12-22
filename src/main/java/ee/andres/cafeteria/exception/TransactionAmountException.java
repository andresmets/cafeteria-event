package ee.andres.cafeteria.exception;

import com.fasterxml.jackson.databind.JsonNode;

public class TransactionAmountException extends RuntimeException {
    private final JsonNode resolvedLabels;

    public TransactionAmountException(String message, JsonNode resolvedLabels) {
        super(message);
        this.resolvedLabels = resolvedLabels;
    }

    public JsonNode getResolvedLabels() {
        return resolvedLabels;
    }
}
