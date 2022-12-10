package ee.andres.cafeteria.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import ee.andres.cafeteria.dao.ReservationDao;
import ee.andres.cafeteria.exception.ReservationsExceededException;
import ee.andres.cafeteria.pojo.Product;
import ee.andres.cafeteria.pojo.Reservation;
import ee.andres.cafeteria.pojo.Seller;
import ee.andres.cafeteria.response.ApiResponse;
import ee.andres.cafeteria.service.ProductService;
import ee.andres.cafeteria.service.ReservationService;
import ee.andres.cafeteria.validator.BusinessValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("ReservationService")
public class ReservationServiceImpl extends AbstractServiceImpl<Reservation> implements ReservationService {
    private static final String RESPONSE_ATTR_RESERVATION = "reservation";
    private static final String RESPONSE_ATTR_TOTALS = "totals";
    private static final String LABEL_CHECKOUT = "labelCheckout";
    private static final String LABEL_RESET = "labelCancel";
    private static final String LABEL_RESERVED = "labelReserved";
    private static final String LABEL_TOTAL = "labelTotal";
    private static final String LABEL_CASH_NOT_RECEIVED = "labelCashNotReceived";
    private static final String LABEL_CASH_RETURNED = "labelCashReturned";
    private static final String LABEL_CASH_RECEIVED = "labelCashReceived";
    private static final String LABEL_CLOSE = "labelClose";
    private static final String REQUEST_ATTRIBUTE_PRODUCT_ID = "productId";

    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private BusinessValidator<Product> reservationValidator;

    @Override
    public Reservation getSellerReservationOnDate(Seller seller, Date date) {
        if (seller == null) {
            return null;
        }
        return getReservationDao().getReservationOnDate(seller, date);
    }

    public Map<Long, Integer> getCurrentReservationCounts() {
        List<Reservation> reservations = getReservationDao().getAllReservationsOnDate(new Date());
        return getReservationTotals(reservations);
    }

    private Map<Long, Integer> getReservationTotals(List<Reservation> reservations) {
        Map<Long, Integer> map = new HashMap<>();
        if (reservations != null) {
            for (Reservation r : reservations) {
                if (r != null && r.getProduct() != null) {
                    for (Product p : r.getProduct()) {
                        if (!map.containsKey(p.getId())) {
                            map.put(p.getId(), 1);
                            continue;
                        }
                        map.put(p.getId(), map.get(p.getId()) + 1);
                    }
                }
            }
        }
        return map;
    }

    @Override
    public ApiResponse reserveProduct(Seller seller, Long productId, Locale locale) {
        Product product = getProduct(productId);
        if (!reservationValidator.validate(product)) {
            throw new ReservationsExceededException("reservations exceeded for product", responseLabels(Collections.singletonList(LABEL_RESERVED), locale));
        }
        product.setQuantity(product.getQuantity() - 1);
        getProductService().update(product);
        Reservation reservation = getSellerReservationOnDate(seller, new Date());
        if (reservation == null) {
            reservation = new Reservation();
            reservation.setSeller(seller);
            reservation.setTransactionTime(new Date());
            reservation.setProduct(new ArrayList<>());
            reservation.getProduct().add(product);
            save(reservation);
        } else {
            reservation.getProduct().add(product);
            update(reservation);
        }
        return new ApiResponse(HttpStatus.OK.value(), responseNode(Collections.singletonList(reservation)));
    }

    private Product getProduct(Long productId) {
        Product product = getProductService().getProductById(productId);
        if (product == null) {
            throw new EntityNotFoundException("reservations exceeded product limit");
        }
        return product;
    }

    @Override
    public ApiResponse removeProduct(Seller seller, JsonNode basketInfo, Locale locale) {
        Product product = getProduct(basketInfo.get(REQUEST_ATTRIBUTE_PRODUCT_ID).asLong());
        Reservation reservation = getSellerReservationOnDate(seller, new Date());
        if(reservation == null){
            throw new EntityNotFoundException("reservation does not exist");
        }
        product.setQuantity(product.getQuantity()+1);
        getProductService().update(product);
        Product toRemove = getFirstProductToRemove(product.getId(), reservation.getProduct());
        if(toRemove != null){
            reservation.getProduct().remove(toRemove);
        }
        getReservationDao().update(reservation);
        return new ApiResponse(HttpStatus.OK.value(), responseNode(Collections.singletonList(reservation)));
    }

    private Product getFirstProductToRemove(Long id, List<Product> products){
        for(Product p : products){
            if(p.getId().equals(id)){
                return p;
            }
        }
        return null;
    }
    @Override
    public ApiResponse getCurrentTotalReservations() {
        Map<Long, Integer> totals = getCurrentReservationCounts();
        return new ApiResponse(HttpStatus.OK.value(), responseNode(totals));
    }

    @Override
    public ApiResponse getCurrentSellerReservations(Seller seller, Locale locale) {
        Reservation reservation = getSellerReservationOnDate(seller, new Date());
        return new ApiResponse(HttpStatus.OK.value(), responseNode(Arrays.asList(RESPONSE_ATTR_RESERVATION, RESPONSE_ATTR_TOTALS),
                Arrays.asList(reservation, getReservationTotals(Collections.singletonList(reservation)))),
                responseLabels(Arrays.asList(LABEL_CHECKOUT,LABEL_RESET, LABEL_TOTAL, LABEL_CASH_NOT_RECEIVED, LABEL_CASH_RETURNED, LABEL_CASH_RECEIVED, LABEL_CLOSE), locale));
    }

    @Override
    public ApiResponse cancelReservation(Seller seller, Locale locale) {
        Reservation reservation = getSellerReservationOnDate(seller, new Date());
        if(reservation == null){
            throw new EntityNotFoundException("no active reservation");
        }
        getReservationDao().delete(reservation);
        return new ApiResponse(HttpStatus.OK.value());
    }

    @Override
    public void save(Reservation reservation) {
        getReservationDao().save(reservation);
    }

    @Override
    public void update(Reservation reservation) {
        getReservationDao().update(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        getReservationDao().delete(reservation);
    }

    public ReservationDao getReservationDao() {
        return reservationDao;
    }

    public ProductService getProductService() {
        return productService;
    }

    public BusinessValidator<Product> getReservationValidator() {
        return reservationValidator;
    }
}
