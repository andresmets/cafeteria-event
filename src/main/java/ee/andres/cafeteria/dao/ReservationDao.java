package ee.andres.cafeteria.dao;

import ee.andres.cafeteria.pojo.Reservation;
import ee.andres.cafeteria.pojo.Seller;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ReservationDao extends AbstractDao<Reservation> {

    public Reservation getReservationOnDate(Seller seller, Date date) {
        TypedQuery<Reservation> q = getEntityManager().createNamedQuery("reservationOnDateBySeller", Reservation.class);
        q.setParameter("id", seller.getId());
        q.setParameter("date", date);
        try {
            return q.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    public List<Reservation> getAllReservationsOnDate(Date date) {
        TypedQuery<Reservation> q = getEntityManager().createNamedQuery("reservationsOnDate", Reservation.class);
        q.setParameter("date", date);
        return q.getResultList();
    }
}
