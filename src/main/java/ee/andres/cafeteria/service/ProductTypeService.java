package ee.andres.cafeteria.service;

import ee.andres.cafeteria.response.ApiResponse;

import java.util.Locale;

public interface ProductTypeService {
    ApiResponse getProductTypes(Locale locale);
}
