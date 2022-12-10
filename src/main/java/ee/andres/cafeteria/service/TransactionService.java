package ee.andres.cafeteria.service;

import com.fasterxml.jackson.databind.JsonNode;
import ee.andres.cafeteria.pojo.Seller;
import ee.andres.cafeteria.response.ApiResponse;

public interface TransactionService {
    ApiResponse commitTransaction(Seller seller, JsonNode transactionInfo);
}
