package ee.andres.cafeteria.dao;

import ee.andres.cafeteria.pojo.Product;
import ee.andres.cafeteria.types.ProductTypes;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao extends AbstractDao<Product> {

    public List<Product> getProductsByType(ProductTypes type) {
        Query q = getEntityManager().createNamedQuery("loadByType");
        q.setParameter("type", type);
        return q.getResultList();
    }
}
