package ee.andres.cafeteria.validator.impl;

import com.fasterxml.jackson.databind.JsonNode;
import ee.andres.cafeteria.exception.TransactionAmountException;
import ee.andres.cafeteria.pojo.Product;
import ee.andres.cafeteria.pojo.Reservation;
import ee.andres.cafeteria.service.TransactionService;
import ee.andres.cafeteria.validator.BusinessValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("TransactionValidatorImpl")
public class TransactionValidatorImpl implements BusinessValidator<Reservation> {
    @Override
    public boolean validate(Reservation reservation) {
        throw new UnsupportedOperationException("method is not implemented");
    }

    @Override
    public void validate(Reservation reservation, JsonNode parameters) {
        if(Optional.ofNullable(reservation).isEmpty()){
            throw new EntityNotFoundException("reservation is not present");
        }
        if (Optional.ofNullable(reservation.getProduct()).isPresent()) {
            Long total = getTotalAmount(reservation);
            if (!total.equals(parameters.get(TransactionService.REQUEST_ATTR_TOTAL_AMOUNT).asLong())) {
                throw new TransactionAmountException("amount does not match with the request value", null);
            }
            Long received = parameters.get(TransactionService.REQUEST_ATTR_CASH_RECEIVED).asLong();
            Long returned = parameters.get(TransactionService.REQUEST_ATTR_CASH_RETURNED).asLong();
            if (received - total != returned) {
                throw new TransactionAmountException("returned amount does not match", null);
            }
        } else {
            throw new EntityNotFoundException("reservation does not contain products");
        }
    }

    private Long getTotalAmount(Reservation reservation) {
        Long total = 0L;
        for(Product p : reservation.getProduct()){
            total += p.getPrice();
        }
        return total;
    }
}
