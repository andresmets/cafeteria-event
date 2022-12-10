package ee.andres.cafeteria.pojo;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "retail_seller")
public class Seller extends CommonEntity{
    @Column(name = "name")
    private String name;
    @Column(name = "hired_on")
    @Temporal(TemporalType.DATE)
    private Date hiredOnDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getHiredOnDate() {
        return hiredOnDate;
    }

    public void setHiredOnDate(Date hiredOnDate) {
        this.hiredOnDate = hiredOnDate;
    }
}
