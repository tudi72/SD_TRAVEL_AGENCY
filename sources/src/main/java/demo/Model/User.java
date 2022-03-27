package demo.Model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@NamedNativeQuery(name = "User.findByPassAndUser", query = "SELECT * FROM user WHERE username = ? and password = ?", resultClass = User.class)

public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name = "firstname",nullable = false)
    String firstName;

    @Column(name = "lastname", nullable = false)
    String lastName;

    @Column(name = "email",nullable = false)
    String email;

    @Column(name ="username",nullable = false)
    String username;

    @Column(name = "password", nullable = false)
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {

    }

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Booking> bookings = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
