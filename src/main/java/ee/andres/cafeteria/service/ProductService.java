package ee.andres.cafeteria.service;

import ee.andres.cafeteria.pojo.Product;
import ee.andres.cafeteria.pojo.Seller;
import ee.andres.cafeteria.response.ApiResponse;
import ee.andres.cafeteria.types.ProductTypes;

import java.util.Locale;

public interface ProductService extends CommonService<Product> {
    void increaseQuantity(Long id, Integer quantity);
    void setQuantity(Long id, Integer quantity);
    ApiResponse getProducts(Seller seller, ProductTypes type, Locale locale);
    ApiResponse getProductCountById(Long id);
    Product getProductById(Long id);
}
