package ee.andres.cafeteria.pojo;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reservation", indexes = {@Index(unique = true, columnList = "id,seller_id")})
@NamedQuery(name="reservationOnDateBySeller", query = "from Reservation r outer join fetch r.product where r.seller.id = :id and date(r.transactionTime) = :date")
@NamedQuery(name="reservationsOnDate", query = "from Reservation r outer join fetch r.product where date(r.transactionTime) = :date")
public class Reservation extends CommonEntity {
    @OneToOne
    private Seller seller;
    @ManyToMany
    @JoinTable(name = "reservation_product",
            joinColumns = {@JoinColumn(name = "reservation_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")}, uniqueConstraints = {})
    private List<Product> product;
    @Column(name = "transaction_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionTime;

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }
}
