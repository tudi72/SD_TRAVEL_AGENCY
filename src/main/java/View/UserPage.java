package View;


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
    private JButton viewBookingsButton = new JButton("view my bookings");
    private JButton bookVacationButton = new JButton("book vacation");

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
        userFrame.setSize(800,400);
        userFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        userFrame.setLocationRelativeTo(null);

        leftPanel.setLayout(new GridLayout(5,1,30,30));
        leftPanel.add(viewBookingsButton);
        leftPanel.add(viewVacationButton);
        leftPanel.add(viewVacationByPriceButton);
        leftPanel.add(viewVacationByDestButton);
        leftPanel.add(bookVacationButton);

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
        userGenerateTableFrame(vacationAvailableFrame,table);
    }

    /**
     * Vacation by Destination Frame: table with list of vacation filtered by destination
     * @param table
     */
    public void setVacationByDestFrame(JTable table){
        userGenerateTableFrame(vacationByDestFrame,table);
    }

    /**
     * Bookings Frame : table with the user's list of bookings
     * @param table
     */
    public void setBookingsFrame(JTable table){
        userGenerateTableFrame(bookingsFrame,table);
    }

    /**
     * Vacation By Price Frame: window with list of vacations filtered by max price
     * @param table
     */
    public void setVacationByPriceFrame(JTable table){
        userGenerateTableFrame(vacationByPriceFrame,table);
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
     * @param frame
     * @param vacationTable
     */
    public void userGenerateTableFrame(JFrame frame,JTable vacationTable){
        this.table = vacationTable;
        vacationTable.setRowSelectionAllowed(true);
        JScrollPane pane = new JScrollPane(vacationTable);
        pane.setVisible(true);

        frame = new JFrame();
        frame.setSize(1200,400);
        frame.setLocationRelativeTo(null);

        frame.setContentPane(pane);
        frame.setVisible(true);
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

    public void setBookVacationButton(ActionListener a){bookVacationButton.addActionListener(a);}
    public void setTableAction(JTable table,MouseListener a){vacationTable = table;vacationTable.addMouseListener(a);}
    public void setRegisterButtonAction(ActionListener a){registerButton.addActionListener(a);}
    public void setLoginButtonAction(ActionListener a){loginButton.addActionListener(a);}
    public void setViewVacationAction(ActionListener a){viewVacationButton.addActionListener(a);}
    public void setViewVacationByDestButton(ActionListener a){viewVacationByDestButton.addActionListener(a);}
    public void setViewVacationByPriceButton(ActionListener a){ viewVacationByPriceButton.addActionListener(a); }
    public void setViewBookingsButton(ActionListener a){viewBookingsButton.addActionListener(a);}

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

}
