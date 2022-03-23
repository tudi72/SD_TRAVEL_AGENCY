package Model;

import javax.persistence.*;
import javax.transaction.*;
import java.sql.Date;
//import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vacation")
@NamedNativeQueries({
        @NamedNativeQuery(name = "Vacation.SelectAll", query = "SELECT * FROM vacation", resultClass = Vacation.class),
        @NamedNativeQuery(name = "Vacation.SelectById", query = "SELECT * FROM vacation where id = ?", resultClass = Vacation.class),
        @NamedNativeQuery(name = "Vacation.SelectByAvailability", query = "SELECT * FROM vacation WHERE status <> 2 and capacity > 0", resultClass = Vacation.class),
        @NamedNativeQuery(name = "Vacation.SelectByDestination", query =
                "SELECT vacation.* FROM vacation join destination WHERE destination.country = ? or destination.location = ? and status <> 2", resultClass = Vacation.class),
        @NamedNativeQuery(name = "Vacation.SelectByPrice", query = "SELECT vacation.* FROM vacation WHERE price < ? and status <> 2", resultClass = Vacation.class),
        @NamedNativeQuery(name = "Vacation.SelectByPeriod", query = "SELECT vacation.* FROM vacation WHERE startDate = ? and endDate = ? and status <> 2", resultClass = Vacation.class),
        @NamedNativeQuery(name = "Vacation.UpdateCapacity", query = "Update vacation set capacity = ? where id = ?"),
        @NamedNativeQuery(name = "Vacation.UpdateStatus", query = "Update vacation set status = ? where id = ?"),
        @NamedNativeQuery(name = "Vacation.SelectByDestId", query = "SELECT* FROM vacation where destination_id = ?", resultClass = Vacation.class)
})
public class Vacation {

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public Vacation(int capacity, String details, float price, Date startDate, Date endDate, String status) {
        this.capacity = capacity;
        this.details = details;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "details")
    private String details;

    @Column(name = "price")
    private float price;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_id",nullable = false)
    private Destination destination_id = new Destination();

    @OneToMany(mappedBy = "vacation",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Booking> bookings = new ArrayList<>();
    public Vacation() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Destination getDestination() {
        return destination_id;
    }

    public void setDestination(Destination destination) {
        this.destination_id = destination;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

}
