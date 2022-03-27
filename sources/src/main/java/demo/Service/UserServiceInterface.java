package demo.Service;

import demo.Model.User;
import demo.Model.Vacation;

import java.util.List;

public interface UserServiceInterface {
    /**
     * creates an account for an user and persists in the database
     * @param username
     * @param password
     * @param email
     * @param firstname
     * @param lastname
     * @return
     */
    public User register(String username, String password, String email, String firstname, String lastname);

    /**
     * Logs in pre-existent user
     * @param username
     * @param password
     * @return User
     */
    public User login(String username,String password);

    /**
     * filters the Vacation table based on destination/price/period/availability/id
     * @param param
     * @param valueParam
     * @return
     */
    public List filterVacationBy(String param,String[] valueParam);

    /**
     * User creates a booking and persists it to database
     * @param user
     * @param vacation
     */
    public void bookVacation(User user, Vacation vacation);

    /**
     * User can view all the bookings made by him
     * @param user
     * @return List of bookings
     */
    public List viewBookings(User user);

}
