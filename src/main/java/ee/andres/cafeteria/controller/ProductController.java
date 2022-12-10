package ee.andres.cafeteria.controller;

import ee.andres.cafeteria.response.ApiResponse;
import ee.andres.cafeteria.service.ProductService;
import ee.andres.cafeteria.service.ProductTypeService;
import ee.andres.cafeteria.service.UserService;
import ee.andres.cafeteria.types.ProductTypes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.WebRequest;

@Tag(name = "product", description = "the product API")
@Controller
public class ProductController {
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Operation(summary = "get product types", description = "get available product categories")
    @ApiResponses(value = {
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successful operation", content = { @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiResponse.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class)) })})
    @GetMapping(path = "/types")
    public ResponseEntity<ApiResponse> getProductTypes(WebRequest request){
        ApiResponse response = getProductTypeService().getProductTypes(request.getLocale());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    //illegal argument exception handling
    @GetMapping(path = "/products/{type}")
    public ResponseEntity<ApiResponse> getProductsByType(@PathVariable(value = "type") String type, WebRequest request){
        ApiResponse response = getProductService().getProducts(getUserService().getByLoggedInUser(request.getUserPrincipal()), ProductTypes.valueOf(type),request.getLocale());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping(path = "/product/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable(value = "type") Long id){
        ApiResponse response = getProductService().getProductCountById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @PostMapping(path = "/quantity/{id}")
    public void updateQuantity(@PathVariable(value = "id") Long productId, @RequestBody Integer quantity){
            getProductService().increaseQuantity(productId, quantity);
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
