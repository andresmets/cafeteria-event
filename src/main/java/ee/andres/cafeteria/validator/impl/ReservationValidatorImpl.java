package ee.andres.cafeteria.validator.impl;

import ee.andres.cafeteria.pojo.Product;
import ee.andres.cafeteria.service.ReservationService;
import ee.andres.cafeteria.validator.BusinessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("ReservationValidatorImpl")
public class ReservationValidatorImpl implements BusinessValidator<Product> {
    @Autowired
    private ReservationService reservationService;

    @Override
    public boolean validate(Product product) {
        Map<Long, Integer> totals = reservationService.getCurrentReservationCounts();
        if(totals != null && totals.get(product.getId()) != null){
            return product.getQuantity() > totals.get(product.getId());
        }
        return true;
    }

    public ReservationService getReservationService() {
        return reservationService;
    }
}
