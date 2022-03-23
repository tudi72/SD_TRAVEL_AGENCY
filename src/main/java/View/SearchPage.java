package View;

import javax.swing.*;
import java.awt.*;

public class SearchPage extends JFrame {

    JPanel mainPanel;
    JPanel upperPanel;
    JPanel middlePanel;
    JPanel lowerPanel;

    SearchPage(){
        this.setSize(600,600);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setLayout(new GridLayout(3,1,40,40));
        mainPanel.add(upperPanel);
        mainPanel.add(middlePanel);
        mainPanel.add(lowerPanel);

        this.setVisible(true);
        this.setContentPane(mainPanel);
    }

    JTextField destinationField;
    JTextField startDateField;
    JTextField endDateField;
    JTextField maxPriceField;
}
