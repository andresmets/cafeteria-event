package ee.andres.cafeteria.pojo;

import ee.andres.cafeteria.types.ProductTypes;
import jakarta.persistence.*;

@Entity
@Table(name = "product_type")
@NamedQueries(@NamedQuery(name = "loadAllTypes", query = "from ProductType"))
public class ProductType extends CommonEntity{
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ProductTypes type;
    @Column(name = "description")
    private String description;

    public ProductTypes getType() {
        return type;
    }

    public void setType(ProductTypes type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
