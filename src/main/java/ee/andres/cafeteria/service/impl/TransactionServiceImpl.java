package ee.andres.cafeteria.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import ee.andres.cafeteria.dao.SoldItemsDao;
import ee.andres.cafeteria.formatter.CurrencyAmountFormatter;
import ee.andres.cafeteria.pojo.Product;
import ee.andres.cafeteria.pojo.Reservation;
import ee.andres.cafeteria.pojo.Seller;
import ee.andres.cafeteria.pojo.SoldItems;
import ee.andres.cafeteria.response.ApiResponse;
import ee.andres.cafeteria.service.ReservationService;
import ee.andres.cafeteria.service.TransactionService;
import ee.andres.cafeteria.validator.BusinessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("TransactionServiceImpl")
public class TransactionServiceImpl extends AbstractServiceImpl<SoldItems> implements TransactionService {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private SoldItemsDao soldItemsDao;
    @Autowired
    private CurrencyAmountFormatter currencyAmountFormatter;
    @Autowired
    @Qualifier("TransactionValidatorImpl")
    private BusinessValidator<Reservation> transactionValidator;

    @Override
    public ApiResponse commitTransaction(Seller seller, JsonNode transactionInfo) {
        Reservation reservation = getReservationService().getSellerReservationOnDate(seller, new Date());
        getTransactionValidator().validate(reservation, transactionInfo);
        List<Product> products = new ArrayList<>();
        products.addAll(reservation.getProduct());
        reservation.setProduct(null);
        getReservationService().delete(reservation);
        SoldItems items = new SoldItems();
        items.setSeller(seller);
        items.setProduct(products);
        items.setDateSold(new Date());
        items.setCashPayed(getCurrencyAmountFormatter().format(transactionInfo.get(REQUEST_ATTR_CASH_RECEIVED).asDouble()));
        items.setCashReturned(getCurrencyAmountFormatter().format(transactionInfo.get(REQUEST_ATTR_CASH_RETURNED).asDouble()));
        items.setTotalAmount(getCurrencyAmountFormatter().format(transactionInfo.get(REQUEST_ATTR_TOTAL_AMOUNT).asDouble()));
        getSoldItemsDao().save(items);
        return new ApiResponse(HttpStatus.OK.value(), null);
    }

    public ReservationService getReservationService() {
        return reservationService;
    }

    public SoldItemsDao getSoldItemsDao() {
        return soldItemsDao;
    }

    public CurrencyAmountFormatter getCurrencyAmountFormatter() {
        return currencyAmountFormatter;
    }

    public BusinessValidator<Reservation> getTransactionValidator() {
        return transactionValidator;
    }
}
