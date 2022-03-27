package demo.Controller;

import demo.Service.Handlers.FormatHandler;
import demo.Model.Booking;
import demo.Model.DTOs.BookingDTO;
import demo.Model.DTOs.VacationDTO;
import demo.Model.User;
import demo.Model.Vacation;
import demo.Service.Handlers.LoginHandler;
import demo.Service.SystemService;
import demo.Service.UserService;
import demo.Service.UserServiceInterface;
import demo.View.UserPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class UserController extends HomeController{

    private UserPage userPage;
    private User user;
    private UserServiceInterface userService = new UserService();
    private SystemService systemService = new SystemService();

    public UserController(UserPage userPage){
        this.userPage = userPage;
        this.userPage.setVisible(true);
        this.home.setVisible(false);
        userPage.setRegisterButtonAction(new UserController.RegisterAction());
        userPage.setFinalRegisterAction(new FinalRegisterAction());
        userPage.setLoginButtonAction(new UserController.LoginAction());
        userPage.setViewVacationAction(new UserController.ViewVacation());
        userPage.setViewVacationByDestButton(new ViewVacationByDest());
        userPage.setFinalViewVacationByDestButton(new FinalViewVacationByDest());

        userPage.setViewVacationByPriceButton(new ViewVacationByPrice());
        userPage.setFinalViewVacationByPriceButton(new FinalViewVacationByPrice());

        userPage.setViewVacationByPeriodButton(new ViewVacationByPeriod());
        userPage.setFinalViewVacationByPeriodButton(new FinalViewVacationByPeriod());

        userPage.setViewBookingsButton(new ViewBookings());
        userPage.setBookVacationButton(new ShowVacationForBooking());
    }

    /**
     * redirects to register page
     */
    private class RegisterAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            userPage.setFinalViewRegisterPage();
        }
    }
    /**
     * register page: clicking on the button to register
     */
    private class FinalRegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String username = userPage.getUsername();
            String password = userPage.getPassword();
            String email    = userPage.getEmail();
            String firstname = userPage.getFirstName();
            String lastname = userPage.getLastName();
            if(!LoginHandler.findUser(username,password)){
                user = userService.register(username,password,email,firstname,lastname);
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
            if(vacationList.size() == 0){
                userPage.inputError("No vacations available");
            }
            else
                userPage.setVacationAvailableFrame(vacationGenerator.generateTable(vacationList));

        }
    }

    /**
     * user page -> Vacation By destination frame
     */
    private class ViewVacationByDest implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            userPage.setFinalViewVacationByDestPage();
        }
    }
    private class FinalViewVacationByDest implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] valueParam = new String[2];
            valueParam[0] = userPage.getDestinationFilter();
            valueParam[1] = userPage.getDestinationFilter();
            ArrayList<Vacation> vacations = (ArrayList<Vacation>) userService.filterVacationBy("Destination",valueParam);
            ArrayList<VacationDTO> vacationDTOS = systemService.mapRawToVacationTO(vacations);
            if(vacations.size() == 0){
                userPage.inputError("No vacations available for this destination");
            }
            else
                userPage.setVacationByDestPage(vacationGenerator.generateTable(vacationDTOS));
        }
    }

    /**
     * user page -> New Window with filter for vacations by price
     */
    private class ViewVacationByPrice implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            userPage.viewVacationByPricePage();
        }
    }

    /**
     * user page + window with price filter -> display the vacation by applying those filters
     */
    private class FinalViewVacationByPrice implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String[] params = new String[1];
            params[0] = userPage.getPriceFilter();
            ArrayList<Vacation> rawVacation = (ArrayList<Vacation>) userService.filterVacationBy("Price",params);
            ArrayList<VacationDTO> vacationDTOS = systemService.mapRawToVacationTO(rawVacation);
            if(!FormatHandler.checkIntFormat(params[0])){
                userPage.inputError("Please check the price filter");
            }
            else if(vacationDTOS.size() == 0){
                userPage.inputError("No vacations available for this price");
            }
            else userPage.setVacationAvailableFrame(vacationGenerator.generateTable(vacationDTOS));

        }
    }

    private class ViewVacationByPeriod implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            userPage.setFinalViewVacationByPeriodPage();
        }
    }

    /**
     * user page -> New Window with bookings of current user
     */
    private class ViewBookings implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<Booking> rawBookings = (ArrayList) userService.viewBookings(user);
            ArrayList<BookingDTO> bookings = systemService.mapRawToDBookingTO(rawBookings);
            if(bookings.size() == 0){
                userPage.inputError("You have no bookings");
            }
            else
                userPage.setBookingsFrame(bookingGenerator.generateTable(bookings));
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
            ArrayList<VacationDTO> vacationDTOS = systemService.mapRawToVacationTO(vacationList);
            if(vacationDTOS == null | vacationDTOS.size() == 0){
                userPage.inputError("Not vacations available so far");
            }
            else {
                JTable table = vacationGenerator.generateTable(vacationDTOS);
                userPage.setVacationAvailableFrame(table);
                userPage.setTableAction(table, new SelectedRowAction());
            }
        }
    }


    private class FinalViewVacationByPeriod implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] params = new String[2];
            params[0] = String.valueOf(systemService.getDate(userPage.getStartDateFilter()));
            params[1] = String.valueOf(systemService.getDate(userPage.getEndDateFilter()));
            ArrayList<Vacation> rawVacation = (ArrayList<Vacation>) userService.filterVacationBy("Period",params);
            ArrayList<VacationDTO> vacationDTOS = systemService.mapRawToVacationTO(rawVacation);
            if(vacationDTOS == null || vacationDTOS.size() == 0){
                userPage.inputError("Couldn't find any vacation in this period");
            }
            else
                userPage.userGenerateTableFrame(vacationGenerator.generateTable(vacationDTOS));
        }
    }
}
