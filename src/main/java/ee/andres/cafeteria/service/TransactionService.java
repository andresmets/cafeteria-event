package ee.andres.cafeteria.service;

import com.fasterxml.jackson.databind.JsonNode;
import ee.andres.cafeteria.pojo.Seller;
import ee.andres.cafeteria.response.ApiResponse;

public interface TransactionService {
    String REQUEST_ATTR_CASH_RECEIVED = "received";
    String REQUEST_ATTR_CASH_RETURNED = "returned";
    String REQUEST_ATTR_TOTAL_AMOUNT = "amount";

    ApiResponse commitTransaction(Seller seller, JsonNode transactionInfo);
}
