package ee.andres.cafeteria.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import ee.andres.cafeteria.response.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException e){
        JsonNode node = JsonNodeFactory.instance.textNode(e.getMessage());
        ApiResponse response = new ApiResponse(HttpStatus.NOT_FOUND.value(), node);
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ReservationsExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleReservationsExceeded(ReservationsExceededException e){
        JsonNode node = JsonNodeFactory.instance.textNode(e.getMessage());
        ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), node, e.getResolvedLabels());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }
}
