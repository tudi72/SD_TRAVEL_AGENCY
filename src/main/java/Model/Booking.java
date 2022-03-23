package Model;
import javax.persistence.*;

@Entity
@Table(name = "booking")
@NamedNativeQueries({
        @NamedNativeQuery(name = "Booking.SelectByUser", query = "SELECT * FROM booking WHERE user_id = ?", resultClass = Booking.class),
        @NamedNativeQuery(name = "Booking.SelectByVacation", query = "SELECT * FROM booking WHERE vacation_id = ?", resultClass = Booking.class)
})
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public Booking(User user, Vacation vacation) {
        this.user = user;
        this.vacation = vacation;
    }

    public Booking() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "vacation_id")
    private Vacation vacation;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vacation getVacation() {
        return vacation;
    }

    public void setVacation(Vacation vacation) {
        this.vacation = vacation;
    }
}
