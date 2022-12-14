package ee.andres.cafeteria.controller;

import ee.andres.cafeteria.response.ApiResponse;
import ee.andres.cafeteria.service.ProductService;
import ee.andres.cafeteria.service.ProductTypeService;
import ee.andres.cafeteria.service.UserService;
import ee.andres.cafeteria.types.ProductTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping(path="/api/v3")
public class ProductController {
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @GetMapping(path = "/types")
    public ResponseEntity<ApiResponse> getProductTypes(WebRequest request){
        ApiResponse response = getProductTypeService().getProductTypes(request.getLocale());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping(path = "/products/{type}")
    public ResponseEntity<ApiResponse> getProductsByType(@PathVariable(value = "type") String type, WebRequest request){
        ApiResponse response = getProductService().getProducts(getUserService().getByLoggedInUser(request.getUserPrincipal()), ProductTypes.valueOf(type),request.getLocale());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping(path = "/product/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable(value = "id") Long id){
        ApiResponse response = getProductService().getProductCountById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @PostMapping(path = "/quantity/{id}/{quantity}")
    public void updateQuantity(@PathVariable(value = "id") Long productId, @PathVariable(value = "quantity") Integer quantity){
            getProductService().setQuantity(productId, quantity);
    }

    public ProductTypeService getProductTypeService() {
        return productTypeService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public UserService getUserService() {
        return userService;
    }
}
