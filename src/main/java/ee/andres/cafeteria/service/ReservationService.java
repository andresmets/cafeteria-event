package ee.andres.cafeteria.service;

import com.fasterxml.jackson.databind.JsonNode;
import ee.andres.cafeteria.pojo.Reservation;
import ee.andres.cafeteria.pojo.Seller;
import ee.andres.cafeteria.response.ApiResponse;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

public interface ReservationService extends CommonService<Reservation> {
    Reservation getSellerReservationOnDate(Seller seller, Date date);
    Map<Long, Integer> getCurrentReservationCounts();
    ApiResponse reserveProduct(Seller seller, Long productId, Locale locale);
    ApiResponse removeProduct(Seller seller, JsonNode basketInfo, Locale locale);
    ApiResponse getCurrentTotalReservations();
    ApiResponse getCurrentSellerReservations(Seller seller, Locale locale);
    ApiResponse cancelReservation(Seller seller, Locale locale);
}
