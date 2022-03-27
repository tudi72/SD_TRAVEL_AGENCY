package demo.Service;

import demo.Model.Booking;
import demo.Model.User;
import demo.Model.Vacation;
import demo.Repository.*;

import java.util.ArrayList;

public class UserService implements UserServiceInterface{

    private GenericInterface repository = null;

    @Override
    public User register(String username, String password, String email, String firstname, String lastname) {
        repository = new UserRepository();
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        repository.insert(user);
        return user;
    }

    @Override
    public User login(String username, String password) {
        repository = new UserRepository();
        String[] params = new String[2];
        params[0] = username;
        params[1] = password;
       try{
           return (User)repository.executeQueryAndGetValue("User.findByPassAndUser",params);
//          return (User) repository.executeQueryAndGetList("User.findByPassAndUser",params).remove(0);
       }
       catch(Exception e){
           e.printStackTrace();
           return null;
       }
    }

    @Override
    public ArrayList<Vacation> filterVacationBy(String param, String[] valueParam) {
        repository = new VacationRepository();
        String query = "Vacation.SelectBy" + param;
        try{
             ArrayList<Vacation> vacations = (ArrayList<Vacation>) repository.executeQueryAndGetList(query,valueParam);
             return vacations;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void bookVacation(User user,Vacation vacation) {
        repository = new BookingRepository();
        Booking booking = new Booking(user,vacation);
        repository.insert(booking);
    }

    @Override
    public ArrayList<Booking> viewBookings(User user) {
        repository = new BookingRepository();
      try{
            String[] params = new String[1];
            params[0] = String.valueOf(user.getId());
            return (ArrayList<Booking>) repository.executeQueryAndGetList("Booking.SelectByUser",params);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
