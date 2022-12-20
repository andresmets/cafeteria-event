package ee.andres.cafeteria.validator.impl;

import ee.andres.cafeteria.pojo.Product;
import ee.andres.cafeteria.validator.BusinessValidator;
import org.springframework.stereotype.Service;

@Service("ReservationValidatorImpl")
public class ReservationValidatorImpl implements BusinessValidator<Product> {
    @Override
    public boolean validate(Product product) {
        return product.getQuantity() > 0;
    }
}
