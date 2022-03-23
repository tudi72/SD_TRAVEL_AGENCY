package View;

import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class SearchSpace extends JFrame{
    private JPanel mainPanel;
    private JPanel upperPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private JTextField textField2;
    private JTextField textField3;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JLabel maxPriceLabel;
    private JSlider slider1;
    private JButton vacationButton;
    private JButton button1;
    private JComboBox comboBox1;

    public SearchSpace(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setContentPane(mainPanel);
        this.setVisible(true);

        this.pack();
    }

}
