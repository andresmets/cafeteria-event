package ee.andres.cafeteria.pojo;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "product")
@NamedQuery(name="loadByType", query = "from Product where type.type = :type order by id")
public class Product extends CommonEntity {

    @Column(name = "name")
    private String name;
    @OneToOne
    private ProductType type;
    @Column(name = "price")
    private Long price;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "date_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;
    @Column(name = "product_image")
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }
}
