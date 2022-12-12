package ee.andres.cafeteria.service.impl;

import ee.andres.cafeteria.dao.ProductDao;
import ee.andres.cafeteria.pojo.Product;
import ee.andres.cafeteria.pojo.Seller;
import ee.andres.cafeteria.response.ApiResponse;
import ee.andres.cafeteria.service.ProductService;
import ee.andres.cafeteria.types.ProductTypes;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service("ProductService")
public class ProductServiceImpl extends AbstractServiceImpl<Product> implements ProductService {

    @Autowired
    private ProductDao productDAO;

    /**
     * adds quantity to existing count
     * @param id
     * @param quantity
     */
    @Override
    public void increaseQuantity(Long id, Integer quantity) {
        Product product = getProductDAO().getById(id, Product.class);
        if(product == null){
            throw new EntityNotFoundException(String.format("product with id: %d cannot be found", id));
        }
        product.setQuantity(product.getQuantity() + quantity);
        getProductDAO().update(product);
    }

    /**
     * updates the product count with new count
     * @param id
     * @param quantity
     */
    @Override
    public void setQuantity(Long id, Integer quantity) {
        Product product = getProductDAO().getById(id, Product.class);
        if(product == null){
            throw new EntityNotFoundException(String.format("product with id: %d cannot be found", id));
        }
        product.setQuantity(quantity);
        getProductDAO().update(product);
    }

    @Override
    public ApiResponse getProducts(Seller seller, ProductTypes type, Locale locale) {
        List<Product> products = getProductDAO().getProductsByType(type);
        return new ApiResponse(HttpStatus.OK.value(), responseNode(products), responseLabels(Collections.singletonList("labelInBasket"), locale));
    }

    @Override
    public Product getProductById(Long id) {
        return getProductDAO().getById(id, Product.class);
    }

    @Override
    public ApiResponse getProductCountById(Long id) {
        Product product = getProductById(id);
        if(product == null){
            throw new EntityNotFoundException("product not found: " + id);
        }
        return new ApiResponse(HttpStatus.OK.value(), responseNode(Collections.singletonList(product)));
    }

    public ProductDao getProductDAO() {
        return productDAO;
    }

    @Override
    public void save(Product product) {
        getProductDAO().save(product);
    }

    @Override
    public void update(Product product) {
        getProductDAO().update(product);
    }

    @Override
    public void delete(Product product) {
        getProductDAO().delete(product);
    }
}
