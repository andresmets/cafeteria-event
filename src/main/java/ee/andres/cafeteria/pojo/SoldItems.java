package ee.andres.cafeteria.pojo;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

/**
 *  price amounts are represented as multiplier of 100 of the amount expressed as double (eg. 1.35  = 135)
 */
@Entity
@Table(name = "sold_items")
public class SoldItems extends CommonEntity{
    @ManyToMany
    private List<Product> product;
    @ManyToOne
    private Seller seller;
    @Column(name = "date_sold")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSold;
    @Column(name = "total_amount")
    private Long totalAmount;
    @Column(name = "cash_payed")
    private Long cashPayed;
    @Column(name = "cashReturned")
    private Long cashReturned;

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Date getDateSold() {
        return dateSold;
    }

    public void setDateSold(Date dateSold) {
        this.dateSold = dateSold;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getCashPayed() {
        return cashPayed;
    }

    public void setCashPayed(Long cashPayed) {
        this.cashPayed = cashPayed;
    }

    public Long getCashReturned() {
        return cashReturned;
    }

    public void setCashReturned(Long cashReturned) {
        this.cashReturned = cashReturned;
    }
}
