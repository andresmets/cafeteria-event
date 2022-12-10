package ee.andres.cafeteria.service.impl;

import ee.andres.cafeteria.dao.ProductTypeDao;
import ee.andres.cafeteria.pojo.ProductType;
import ee.andres.cafeteria.response.ApiResponse;
import ee.andres.cafeteria.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service("ProductTypeService")
public class ProductTypeServiceImpl extends AbstractServiceImpl<ProductType> implements ProductTypeService {
    @Autowired
    private ProductTypeDao productTypeDao;

    @Override
    public ApiResponse getProductTypes(Locale locale) {
        List<ProductType> types = getProductTypeDao().loadAllTypes();
        return new ApiResponse(HttpStatus.OK.value(), responseNode(types), responseLabels(types.stream().map(x -> x.getType().name()).toList(),locale));
    }

    public ProductTypeDao getProductTypeDao() {
        return productTypeDao;
    }
}
