package Controller;

import View.TableGenerator.BookingGenerator;
import View.TableGenerator.GeneratorInterface;
import View.TableGenerator.VacationGenerator;
import Model.Booking;
import Model.DTOs.BookingDTO;
import Model.DTOs.VacationDTO;
import Model.User;
import Model.Vacation;
import Service.Handlers.LoginHandler;
import Service.SystemService;
import Service.UserService;
import Service.UserServiceInterface;
import View.UserPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class UserController {

    private UserPage userPage;
    private User user;
    private UserServiceInterface userService = new UserService();
    private SystemService systemService = new SystemService();
    private GeneratorInterface generator = new VacationGenerator();

    public UserController(UserPage userPage){
        this.userPage = userPage;
        this.userPage.setVisible(true);
        userPage.setRegisterButtonAction(new UserController.RegisterAction());
        userPage.setLoginButtonAction(new UserController.LoginAction());
        userPage.setViewVacationAction(new UserController.ViewVacation());
        userPage.setViewVacationByDestButton(new ViewVacationByDest());
        userPage.setViewVacationByPriceButton(new ViewVacationByPrice());
        userPage.setViewBookingsButton(new ViewBookings());
        userPage.setBookVacationButton(new ShowVacationForBooking());
    }


    /**
     * login page: register new user
     */
    private class RegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String username = userPage.getUsername();
            String password = userPage.getPassword();
            if(!LoginHandler.findUser(username,password)){
                user = userService.register(userPage.getUsername(),userPage.getPassword());
                userPage.userMainPage();
            }
            else {
                userPage.inputError("User already existent");
            }
        }
    }

    /**
     * login page: login user,redirect to user functionalities page
     */
    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String username = userPage.getUsername();
            String password = userPage.getPassword();
            if(LoginHandler.findUser(username,password)){
                user = userService.login(userPage.getUsername(),userPage.getPassword());
                userPage.userMainPage();
            }
            else {
                userPage.inputError("Couldn't find user");
            }
        }
    }

    /**
     * user page -> Vacation Available Frame: display list of vacations
     */
    private class ViewVacation implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            ArrayList<Vacation> rawVacationList = (ArrayList<Vacation>) userService.filterVacationBy("Availability",null);
            ArrayList<VacationDTO> vacationList = systemService.mapRawToVacationTO(rawVacationList);
            if(vacationList == null){
                userPage.inputError("No vacations available");
            }
            else
                userPage.setVacationAvailableFrame(generator.generateTable(vacationList));

        }
    }

    /**
     * user page -> Vacation By destination frame
     */
    private class ViewVacationByDest implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] valueParam = new String[2];
            valueParam[0] = userPage.getDestinationFilter();
            valueParam[1] = userPage.getDestinationFilter();
            ArrayList<Vacation> vacations = (ArrayList<Vacation>) userService.filterVacationBy("Destination",valueParam);
            if(vacations == null){
                userPage.inputError("No vacations available for this destination");
            }
            else
                userPage.setVacationByDestFrame(generator.generateTable(vacations));
        }
    }

    /**
     * user page -> New Window with filter for vacations by price
     */
    private class ViewVacationByPrice implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] params = new String[1];
            params[0] = userPage.getPriceFilter();
            ArrayList<Vacation> vacationList = (ArrayList<Vacation>) userService.filterVacationBy("Price",params);
            if(vacationList == null){
                userPage.inputError("No vacations available for this price");
            }
            else userPage.setVacationAvailableFrame(generator.generateTable(vacationList));

        }
    }

    /**
     * user page -> New Window with bookings of current user
     */
    private class ViewBookings implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            generator = new BookingGenerator();
            ArrayList<Booking> rawBookings = (ArrayList) userService.viewBookings(user);
            ArrayList<BookingDTO> bookings = systemService.mapRawToDBookingTO(rawBookings);
            if(bookings == null){
                userPage.inputError("You have no bookings");
            }
            else
                userPage.setBookingsFrame(generator.generateTable(bookings));
        }
    }

    /**
     * when table is selected on id field ->vacation is selected and it creates a booking
     * if vacation NOT_BOOKED -> status IN_PROGRESS,check capacity -> capacity changed
     * if vacation IN_PROGRESS -> check capacity -> capacity changed
     * if check capacity is zero -> change status BOOKED
     */
    private class SelectedRowAction implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

            Vacation vacation = systemService.findVacationById(userPage.getTableValue());

            if(vacation == null){

                userPage.inputError("System error: cannot find vacation");
            }
            else{

                if(systemService.updateCapacity(vacation)){

                    userService.bookVacation(user,vacation);
                    userPage.inputError("Successfully booked vacation");
                }
                else{
                    userPage.inputError("Vacation is fully booked");
                }
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {

        }
        @Override
        public void mouseReleased(MouseEvent e) {
        }
        @Override
        public void mouseEntered(MouseEvent e) {

        }
        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * user page: reacts when user clicks on button " book vacation"
     * opens list with vacations for booking
     */
    private class ShowVacationForBooking implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<Vacation> vacationList = (ArrayList<Vacation>) userService.filterVacationBy("Availability",null);
            if(vacationList == null){
                userPage.inputError("Not vacations available so far");
            }
            else {
                JTable table = generator.generateTable(vacationList);
                userPage.setVacationAvailableFrame(table);
                userPage.setTableAction(table, new SelectedRowAction());
            }
        }
    }

}
