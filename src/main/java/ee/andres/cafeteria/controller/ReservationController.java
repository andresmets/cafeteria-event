package ee.andres.cafeteria.controller;

import com.fasterxml.jackson.databind.JsonNode;
import ee.andres.cafeteria.response.ApiResponse;
import ee.andres.cafeteria.service.ReservationService;
import ee.andres.cafeteria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserService userService;

    @GetMapping(path = "/reserved")
    public ResponseEntity<ApiResponse> getTotalReserved(){
        return ResponseEntity.status(HttpStatus.OK).body(getReservationService().getCurrentTotalReservations());
    }

    @GetMapping(path = "/basket")
    public ResponseEntity<ApiResponse> getShoppingBasket(WebRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(getReservationService().getCurrentSellerReservations(getUserService().getByLoggedInUser(request.getUserPrincipal()), request.getLocale()));
    }

    @PostMapping(path = "/select")
    public ResponseEntity<ApiResponse> reserve(@RequestBody JsonNode productId, WebRequest request) {
        ApiResponse response = getReservationService().reserveProduct(getUserService().getByLoggedInUser(request.getUserPrincipal()), productId.get("productId").asLong(), request.getLocale());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @PostMapping(path = "/remove")
    public ResponseEntity<ApiResponse> remove(@RequestBody JsonNode product, WebRequest request) {
        ApiResponse response = getReservationService().removeProduct(getUserService().getByLoggedInUser(request.getUserPrincipal()), product, request.getLocale());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @PostMapping(path = "/cancel")
    public ResponseEntity<ApiResponse> cancelReservation(WebRequest request) {
        ApiResponse response = getReservationService().cancelReservation(getUserService().getByLoggedInUser(request.getUserPrincipal()), request.getLocale());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    public ReservationService getReservationService() {
        return reservationService;
    }

    public UserService getUserService() {
        return userService;
    }
}
