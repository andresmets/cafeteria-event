package ee.andres.cafeteria.controller;

import com.fasterxml.jackson.databind.JsonNode;
import ee.andres.cafeteria.response.ApiResponse;
import ee.andres.cafeteria.service.TransactionService;
import ee.andres.cafeteria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    @PostMapping(path = "/sell")
    public ResponseEntity<ApiResponse> reserve(@RequestBody JsonNode transaction, WebRequest request) {
        ApiResponse response = getTransactionService().commitTransaction(getUserService().getByLoggedInUser(request.getUserPrincipal()), transaction);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public UserService getUserService() {
        return userService;
    }
}
