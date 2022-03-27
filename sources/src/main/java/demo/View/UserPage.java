package demo.View;


import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class UserPage extends JFrame {

    private JTextField usernameField = new JTextField("username");
    private JPasswordField passwordField = new JPasswordField("password");
    private JButton registerButton = new JButton("register");
    private JButton loginButton = new JButton("login");

    private JPanel mainPanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JButton viewVacationButton = new JButton("view vacation packages");
    private JTextField destinationField = new JTextField("fill destination");
    private JButton viewVacationByDestButton = new JButton("filter vacation by destination");
    private JTextField maxPriceField = new JTextField("max price ");
    private JButton viewVacationByPriceButton = new JButton("filter vacation by price");
    private JButton finalViewVacationByPriceButton = new JButton("filter");

    private JButton viewBookingsButton = new JButton("view my bookings");
    private JButton bookVacationButton = new JButton("book vacation");

    private JButton finalViewVacationByDestButton = new JButton(" filter");

    private JButton viewVacationByPeriodButton = new JButton("filter vacation by period");

    private JFrame userFrame;
    private JFrame vacationAvailableFrame;
    private JFrame vacationByDestFrame;
    private JFrame vacationByPriceFrame;
    private JFrame bookingsFrame;
    private JTable table;
    private JTable vacationTable;


    /**
     * User page: Login page
     */
    public UserPage(){
        this.setSize(400,300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        mainPanel.setLayout(new GridLayout(4,1,30,30));
        mainPanel.add(usernameField);
        mainPanel.add(passwordField);
        mainPanel.add(registerButton);
        mainPanel.add(loginButton);

        this.setVisible(true);
        mainPanel.setVisible(true);
        this.setContentPane(mainPanel);
    }

    /**
     * User page: Main Page with button
     */
    public void userMainPage(){
        cleanRoot(mainPanel);
        userFrame = new JFrame();
        userFrame.setSize(1200,400);
        userFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        userFrame.setLocationRelativeTo(null);

        leftPanel.setLayout(new GridLayout(6,1,30,30));
        leftPanel.add(viewBookingsButton);
        leftPanel.add(viewVacationButton);
        leftPanel.add(viewVacationByPriceButton);
        leftPanel.add(viewVacationByDestButton);
        leftPanel.add(bookVacationButton);
        leftPanel.add(viewVacationByPeriodButton);

        rightPanel.setLayout(new GridLayout(5,1,30,30));
        rightPanel.add(new JLabel());
        rightPanel.add(new JLabel());
        rightPanel.add(maxPriceField);
        rightPanel.add(destinationField);
        rightPanel.add(new JLabel());


        mainPanel.setLayout(new GridLayout(1,2,10,10));
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        this.setVisible(false);
        userFrame.setVisible(true);
        mainPanel.setVisible(true);
        userFrame.setContentPane(mainPanel);
    }

    /**
     * Vacation Available Frame: table with list of vacations with status available
     * @param table
     */
    public void setVacationAvailableFrame(JTable table){
//        cleanRoot(rightPanel);
//        rightPanel.setLayout(new GridLayout(1,1));
//        this.table = table;
//        this.table.setRowSelectionAllowed(true);
//        JScrollPane pane = new JScrollPane(table);
//        pane.setVisible(true);
//        rightPanel.add(table);
//        rightPanel.setVisible(true);
//        mainPanel.setVisible(true);
//        userFrame.setContentPane(mainPanel);
        userGenerateTableFrame(table);
    }

    /**
     * Vacation by Destination Frame: table with list of vacation filtered by destination
     * @param table
     */
    public void setVacationByDestPage(JTable table){

        userGenerateTableFrame(table);
    }

    /**
     * Vacation by Destination
     * @param table
     */
    public void setVacationByPeriodPage(JTable table){
        userGenerateTableFrame(table);
    }

    /**
     * Bookings Frame : table with the user's list of bookings
     * @param table
     */
    public void setBookingsFrame(JTable table){
        userGenerateTableFrame(table);
    }

    /**
     * Vacation By Price Frame: window with list of vacations filtered by max price
     * @param table
     */
    public void setVacationByPriceFrame(JTable table){
        userGenerateTableFrame(table);
    }
    /**
     *  method for returning the filter written in the destination field
     * @return destination string
     */
    public String getDestinationFilter(){
        return destinationField.getText();
    }

    /**
     * returns the max price filter
     * @return maximum price
     */
    public String getPriceFilter(){
        return maxPriceField.getText();
    }
    /**
     * Helper function to generate frame with table
     * @param vacationTable
     */
    public void userGenerateTableFrame(JTable vacationTable){
        cleanRoot(rightPanel);
        this.table = vacationTable;
        vacationTable.setRowSelectionAllowed(true);
        JScrollPane pane = new JScrollPane(vacationTable);
        pane.setVisible(true);
        rightPanel.setLayout(new GridLayout(1,1));
        rightPanel.add(pane);
        rightPanel.setVisible(true);
        mainPanel.setVisible(true);
        userFrame.setContentPane(mainPanel);
    }


    /**
     *  method for returning the password in text field
     * @return password string
     */
    public String getPassword(){
        return String.valueOf(passwordField.getPassword());
    }

    /**
     * method for returning the username in text field
     * @return username string
     */
    public String getUsername(){
        return usernameField.getText();
    }

    /**
     * method for returning the value of table when it is selected by user
     * @return
     */
    public String getTableValue(){
        int row = table.getSelectedRow();
        int column = table.getSelectedColumn();
        if(column != 0) return null;
        return String.valueOf(table.getValueAt(row,column));
    }
    /**
     * get the start date filter
     * @return java.util.Date
     */
    public java.util.Date getStartDateFilter(){
        return  startDateFilter.getDate();
    }

    /**
     * get the end date filter
     * @return java.util.Date
     */
    public java.util.Date getEndDateFilter(){
        return endDateFilter.getDate();
    }
    public String getEmail(){
        return emailField.getText();
    }
    public String getFirstName(){
        return firstNameField.getText();
    }
    public String getLastName(){
        return lastNameField.getText();
    }

    public void setBookVacationButton(ActionListener a){bookVacationButton.addActionListener(a);}
    public void setTableAction(JTable table,MouseListener a){vacationTable = table;vacationTable.addMouseListener(a);}

    public void setRegisterButtonAction(ActionListener a){registerButton.addActionListener(a);}
    public void setFinalRegisterAction(ActionListener a){finalRegisterButton.addActionListener(a);}
    public void setLoginButtonAction(ActionListener a){loginButton.addActionListener(a);}
    public void setViewVacationAction(ActionListener a){viewVacationButton.addActionListener(a);}
    public void setViewVacationByDestButton(ActionListener a){viewVacationByDestButton.addActionListener(a);}
    public void setFinalViewVacationByDestButton(ActionListener a){finalViewVacationByDestButton.addActionListener(a);}
    public void setViewVacationByPriceButton(ActionListener a){ viewVacationByPriceButton.addActionListener(a); }
    public void setFinalViewVacationByPriceButton(ActionListener a){ finalViewVacationByPriceButton.addActionListener(a); }
    public void setViewBookingsButton(ActionListener a){viewBookingsButton.addActionListener(a);}

    public void setViewVacationByPeriodButton(ActionListener a){viewVacationByPeriodButton.addActionListener(a);}
    public void setFinalViewVacationByPeriodButton(ActionListener a){finalViewVacationByPeriodButton.addActionListener(a);}

    /**
     * error message for when things go wrong
     * @param mess
     */
    public void inputError(String mess) {
        JOptionPane.showMessageDialog(mainPanel,mess);
    }

    /**
     * helper function to clean the panel we are currently working on
     * @param panel
     */
    void cleanRoot(JPanel panel){
        Component[] components = panel.getComponents();
        for(Component c : components){
            c.setVisible(false);
            panel.remove(c);
        }
    }

    /**
     * method for putting text fields with
     */
    public void viewVacationByPricePage() {
        cleanRoot(rightPanel);
        rightPanel.setLayout(new GridLayout(5,1,20,20));
        maxPriceField.setVisible(true);
        finalViewVacationByPriceButton.setVisible(true);

        rightPanel.add(maxPriceField);
        rightPanel.add(new JLabel());
        rightPanel.add(new JLabel());
        rightPanel.add(new JLabel());
        rightPanel.add(finalViewVacationByPriceButton);
        userFrame.setContentPane(mainPanel);
    }

    /**
     * the page with filter for choosing a vacation based on destination
     */
    public void setFinalViewVacationByDestPage() {
        cleanRoot(rightPanel);
        rightPanel.setLayout(new GridLayout(5,1,20,20));
        destinationField.setVisible(true);
        finalViewVacationByDestButton.setVisible(true);

        rightPanel.add(destinationField);
        rightPanel.add(new JLabel());
        rightPanel.add(new JLabel());
        rightPanel.add(new JLabel());
        rightPanel.add(finalViewVacationByDestButton);
        userFrame.setContentPane(mainPanel);
    }

    JCalendar startDateFilter = new JCalendar();
    JCalendar endDateFilter = new JCalendar();

    JButton finalViewVacationByPeriodButton = new JButton("fitler");


    /**
     * user page: page with filters for choosing the period desired
     */
    public void setFinalViewVacationByPeriodPage() {
        cleanRoot(rightPanel);
        rightPanel.setLayout(new GridLayout(3,1,20,20));
        rightPanel.add(startDateFilter);
        rightPanel.add(endDateFilter);

        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new GridLayout(3,1,10,10));
        lowerPanel.add(new JLabel());
        lowerPanel.add(new JLabel());
        lowerPanel.add(finalViewVacationByPeriodButton);
        rightPanel.add(lowerPanel);


        rightPanel.setVisible(true);
        mainPanel.setVisible(true);
        userFrame.setContentPane(mainPanel);
    }

    JTextField emailField = new JTextField("email");
    JTextField firstNameField = new JTextField("first name");
    JTextField lastNameField = new JTextField("last name");
    JButton finalRegisterButton = new JButton("register");



    public void setFinalViewRegisterPage() {
        cleanRoot(mainPanel);
        mainPanel.setLayout(new GridLayout(6,1,20,20));
        usernameField.setVisible(true);
        passwordField.setEchoChar((char)0);
        passwordField.setVisible(true);
        mainPanel.add(usernameField);
        mainPanel.add(passwordField);
        mainPanel.add(emailField);
        mainPanel.add(firstNameField);
        mainPanel.add(lastNameField);
        mainPanel.add(finalRegisterButton);

        mainPanel.setVisible(true);
        this.setContentPane(mainPanel);
    }
}
