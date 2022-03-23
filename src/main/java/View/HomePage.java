package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {

    private JButton buttonAgency = new JButton("agency");
    private JButton buttonUser = new JButton("user");
    private JPanel mainPanel = new JPanel();

    /**
     * initial page setup for size, close operation, and window location
     */
    public HomePage(){
        this.setSize(400,400);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        SetUserButton();

        this.setContentPane(mainPanel);
        mainPanel.setVisible(true);
        this.setVisible(true);
    }

    /**
     * method for putting buttons with available users for this desktop app
     */
    public void SetUserButton(){
        mainPanel.add(buttonAgency);
        mainPanel.add(buttonUser);


        mainPanel.setLayout(new GridLayout(1,2,50,50));
    }

    /**
     * setting the action listeners for the buttons
     */
    public void setButtonAgencyAction(ActionListener a){buttonAgency.addActionListener(a);}
    public void setButtonUserAction(ActionListener a){buttonUser.addActionListener(a);}
}
