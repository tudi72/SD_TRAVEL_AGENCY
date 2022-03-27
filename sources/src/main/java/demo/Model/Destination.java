package demo.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "destination")
@NamedNativeQueries({
        @NamedNativeQuery(name = "Destination.Select", query = "SELECT * FROM destination where country = ? and location = ?", resultClass = Destination.class),
        @NamedNativeQuery(name = "Destination.SelectAll", query = "SELECT * FROM destination", resultClass = Destination.class),
        @NamedNativeQuery(name = "Destination.SelectById", query = "SELECT * FROM destination where id = ?", resultClass = Destination.class)

})
    public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String location;

    @Column
    private String country;

    @OneToMany(mappedBy = "destination_id",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Vacation> vacationList = new ArrayList<>();

    public Destination(String location, String country) {
        this.location = location;
        this.country = country;
    }

    public Destination() {

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Vacation> getVacationList() {
        return vacationList;
    }

    public void setVacationList(List<Vacation> vacationList) {
        this.vacationList = vacationList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
