package ee.andres.cafeteria.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ee.andres.cafeteria.dao.SoldItemsDao;
import ee.andres.cafeteria.exception.TransactionAmountException;
import ee.andres.cafeteria.formatter.CurrencyAmountFormatter;
import ee.andres.cafeteria.pojo.Product;
import ee.andres.cafeteria.pojo.Reservation;
import ee.andres.cafeteria.pojo.Seller;
import ee.andres.cafeteria.service.ReservationService;
import ee.andres.cafeteria.service.TransactionService;
import ee.andres.cafeteria.service.UserService;
import ee.andres.cafeteria.validator.impl.TransactionValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionControllerTest {
    @MockBean
    private TransactionServiceImpl transactionService;
    @MockBean
    private CurrencyAmountFormatter currencyAmountFormatter;
    @MockBean
    private UserService userService;
    @MockBean
    private ReservationService reservationService;
    @MockBean
    private SoldItemsDao soldItemsDao;
    @Autowired
    private TransactionValidatorImpl transactionValidator;

    @Test
    void commitTransactionAmountsNotMatch() {
        Seller seller = getSeller();
        JsonNode transaction = getTransaction();
        when(transactionService.getReservationService()).thenReturn(reservationService);
        when(reservationService.getSellerReservationOnDate(any(), any())).thenReturn(getUserReservation());
        when(transactionService.commitTransaction(any(), any())).thenCallRealMethod();
        when(transactionService.getTransactionValidator()).thenReturn(transactionValidator);
        when(transactionService.getCurrencyAmountFormatter()).thenReturn(currencyAmountFormatter);
        when(transactionService.getSoldItemsDao()).thenReturn(soldItemsDao);
        assertThrows(TransactionAmountException.class, () -> transactionService.commitTransaction(seller, transaction));
    }

    @Test
    void commitTransactionReturnedDoesNotMatch() {
        Seller seller = getSeller();
        JsonNode transaction = getTransaction();
        when(transactionService.getReservationService()).thenReturn(reservationService);
        when(reservationService.getSellerReservationOnDate(any(), any())).thenReturn(getUserReservation());
        when(transactionService.commitTransaction(any(), any())).thenCallRealMethod();
        when(transactionService.getTransactionValidator()).thenReturn(transactionValidator);
        when(transactionService.getCurrencyAmountFormatter()).thenReturn(currencyAmountFormatter);
        when(transactionService.getSoldItemsDao()).thenReturn(soldItemsDao);
        assertThrows(TransactionAmountException.class, () -> transactionService.commitTransaction(seller, transaction));
    }

    private Reservation getUserReservation() {
        Reservation reservation = new Reservation();
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setPrice(10L);
        products.add(product);
        product = new Product();
        product.setPrice(2L);
        products.add(product);
        reservation.setProduct(products);
        return reservation;
    }

    private JsonNode getTransaction() {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put(TransactionService.REQUEST_ATTR_CASH_RECEIVED, 10);
        node.put(TransactionService.REQUEST_ATTR_CASH_RETURNED, 1);
        node.put(TransactionService.REQUEST_ATTR_TOTAL_AMOUNT, 9);
        return node;
    }

    private JsonNode getTransactionReturned() {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put(TransactionService.REQUEST_ATTR_CASH_RECEIVED, 12);
        node.put(TransactionService.REQUEST_ATTR_CASH_RETURNED, 1);
        node.put(TransactionService.REQUEST_ATTR_TOTAL_AMOUNT, 12);
        return node;
    }

    private Seller getSeller() {
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setName("some name");
        return seller;
    }
}