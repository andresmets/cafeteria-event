package ee.andres.cafeteria.validator.impl;

import com.fasterxml.jackson.databind.JsonNode;
import ee.andres.cafeteria.pojo.Product;
import ee.andres.cafeteria.validator.BusinessValidator;
import org.springframework.stereotype.Component;

@Component("ReservationValidatorImpl")
public class ReservationValidatorImpl implements BusinessValidator<Product> {
    @Override
    public boolean validate(Product product) {
        return product.getQuantity() > 0;
    }

    @Override
    public void validate(Product product, JsonNode parameters) {
        throw new UnsupportedOperationException("method is not implemented");
    }
}
