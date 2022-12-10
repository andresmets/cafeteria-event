package ee.andres.cafeteria.dao;

import ee.andres.cafeteria.pojo.ProductType;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductTypeDao extends AbstractDao<ProductType> {
    public List<ProductType> loadAllTypes() {
        Query q = getEntityManager().createNamedQuery("loadAllTypes", ProductType.class);
        return q.getResultList();
    }
}
