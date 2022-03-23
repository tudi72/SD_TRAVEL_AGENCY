package View;

import com.toedter.calendar.JCalendar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Date;

public class AgencyPage extends JFrame {

    private JButton showAddDestinationButton = new JButton("add destination");
    private JButton addDestinationButton = new JButton("add destination");
    private JButton deleteDestinationButton = new JButton("delete destination");
    private JButton addVacationButton = new JButton("add vacation");
    private JButton viewVacationButton = new JButton("view vacation packages");
    private JButton editVacationButton = new JButton("edit vacation");
    private JButton finalEditVacationButton = new JButton("edit ");
    private JButton deleteVacationButton = new JButton("delete vacation");


    private JPanel mainPanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JPanel rightPanel = new JPanel();

    JTextField capacityField = new JTextField("fill maximum capacity");
    JTextField detailsField = new JTextField("fill details");
    JTextField priceField = new JTextField("fill price");
    JLabel startDateLabel;
    JLabel endDateLabel;
    JButton insertVacationButton = new JButton("insert vacation");
    JCalendar calendar1 = new JCalendar();
    JCalendar calendar2 = new JCalendar();
    JTable table;
    JTable vacationTable;
    JTable destinationTable;
    JTable editVacationTable;
    JTable editDestinationTable;

    JFrame vacationFrame;

    private final JTextField countryField = new JTextField("fill country");
    private final JTextField cityField = new JTextField("fill city");

    JTextField editCapacity;
    JTextField editDetails;
    JTextField editPrice;
    JCalendar editStartDate;
    JCalendar editEndDate;

    /**
     * travel agency main page constructor with buttons for each action possible
     */
    public AgencyPage(){
        this.setSize(1000,800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        leftPanel.setLayout(new GridLayout(6,1,10,10));
        leftPanel.add(addVacationButton);
        leftPanel.add(deleteDestinationButton);
        leftPanel.add(showAddDestinationButton);
        leftPanel.add(viewVacationButton);
        leftPanel.add(deleteVacationButton);
        leftPanel.add(editVacationButton);

        mainPanel.setLayout(new GridLayout(1,2));
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        this.setContentPane(mainPanel);
        mainPanel.setVisible(true);

    }

    /**
     * this method resets panel and makes each component invisible
     * @param panel
     * @pre component.setVisible might be true
     * @post component.setVisible is false
     */
    public void cleanRoot(JPanel panel){
        Component[] components = panel.getComponents();
        for(Component c : components){
            c.setVisible(false);
            panel.remove(c);
        }
    }

    /**
     * method for showing the text fields when the action " add destination" is clicked
     */
    public void setAddDestinationPanel(){
        cleanRoot(rightPanel);
        rightPanel.setLayout(new GridLayout(4,1,10,10));

        rightPanel.add(countryField);
        rightPanel.add(cityField);
        rightPanel.add(new JLabel());
        rightPanel.add(addDestinationButton);

        countryField.setVisible(true);
        cityField.setVisible(true);
        addDestinationButton.setVisible(true);
        rightPanel.setVisible(true);
        mainPanel.setVisible(true);
        this.setContentPane(mainPanel);
    }

    /**
     * agency page -> after clicking add vacation we need to choose firstly the destination
     * to which we want to go
     */
    public void setSelectDestForVacation(JTable table){
        cleanRoot(rightPanel);
        this.table = table;
        this.table.setVisible(true);
        rightPanel.add(new JScrollPane(table));
        this.setContentPane(mainPanel);
    }

    /**
     * method for returning the value of table when it is selected by agency
     * @return String
     */
    public String getTableValue(){
        int row = table.getSelectedRow();
        int column = table.getSelectedColumn();
        if(column != 0) return null;
        return String.valueOf(table.getValueAt(row,column));
    }

    /**
     * agency page: -> right panel with fields for inserting vacation
     */
    public void setAddVacationPanel(){
        this.cleanRoot(rightPanel);

        rightPanel.setLayout(new GridLayout(6,1,20,20));

        startDateLabel = new JLabel("start date");
        startDateLabel.setLayout(new BorderLayout());
        startDateLabel.add(calendar1,BorderLayout.CENTER);

        endDateLabel = new JLabel("end date");
        endDateLabel.setLayout(new BorderLayout());
        endDateLabel.add(calendar2, BorderLayout.CENTER);

        capacityField.setVisible(true);
        detailsField.setVisible(true);
        priceField.setVisible(true);
        startDateLabel.setVisible(true);
        endDateLabel.setVisible(true);
        insertVacationButton.setVisible(true);
        rightPanel.setVisible(true);

        rightPanel.add(capacityField);
        rightPanel.add(detailsField);
        rightPanel.add(priceField);
        rightPanel.add(startDateLabel);
        rightPanel.add(endDateLabel);
        rightPanel.add(insertVacationButton);
        this.setContentPane(mainPanel);
    }

    /**
     * right panel : list of vacation packages
     * @param table
     */
    public void setVacationTable(JTable table){
        vacationFrame = new JFrame();
        vacationFrame.setSize(1000,300);
        vacationFrame.setVisible(true);
        vacationFrame.setLocationRelativeTo(null);
        cleanRoot(rightPanel);
        this.table = table;
        this.table.setVisible(true);
        vacationFrame.setContentPane(new JScrollPane(table));
    }

    /**
     * agency page -> after clicking add vacation we need to choose firstly the destination
     * to which we want to go
     */
    public void setDestinationTableForDelete(JTable table){
        cleanRoot(rightPanel);
        this.destinationTable = table;
        this.destinationTable.setVisible(true);
        rightPanel.add(new JScrollPane(destinationTable));
        this.setContentPane(mainPanel);
    }

    /**
     * agency page -> after clicking delete vacation, client chooses vacation to be deleted
     * @param table
     */
    public void setVacationTableForDelete(JTable table){
        cleanRoot(rightPanel);
        this.vacationTable = table;
        this.vacationTable.setVisible(true);
        rightPanel.add(new JScrollPane(vacationTable));
        this.setContentPane(mainPanel);
    }

    /**
     * agency page ->set panel with vacations, client chooses one to be edited
     * @param table
     */
    public void setVacationToEditTable(JTable table){
        cleanRoot(rightPanel);
        this.editVacationTable = table;
        this.editVacationTable.setVisible(true);
        rightPanel.add(new JScrollPane(editVacationTable));
        this.setContentPane(mainPanel);
    }

    /**
     * edit vacation action: displays list of destination to be edited with
     * @param table
     */
    public void setDestinationToEditTable(JTable table){
        cleanRoot(rightPanel);
        this.editDestinationTable = table;
        this.editDestinationTable.setVisible(true);
        rightPanel.add(new JScrollPane(editDestinationTable));
        this.setContentPane(mainPanel);
    }

    /**
     * method for edit GUI
     * @param capacity
     * @param startDate
     * @param endDate
     * @param details
     * @param price
     */
    public void setVacationToEdit(String capacity,Date startDate,Date endDate,String details,String price){

        editCapacity = new JTextField("capacity: "+ capacity);
        editStartDate = new JCalendar();
        editStartDate.setDate(startDate);
        editEndDate = new JCalendar();
        editEndDate.setDate(endDate);
        editDetails = new JTextField("details: " + details);
        editPrice = new JTextField("price: " + price);

        cleanRoot(rightPanel);
        rightPanel.setLayout(new GridLayout(6,1,20,20));
        rightPanel.add(editCapacity);
        rightPanel.add(editStartDate);
        rightPanel.add(editEndDate);
        rightPanel.add(editDetails);
        rightPanel.add(editPrice);
        rightPanel.add(finalEditVacationButton);
        this.setContentPane(mainPanel);
    }



    /**
     * error message for when things go wrong
     * @param mess
     */
    public void inputError(String mess) {
        JOptionPane.showMessageDialog(mainPanel,mess);
    }

    public JPanel getWorkingPanel(){
        return rightPanel;
    }

    public String getCity(){
        return cityField.getText();
    }
    public String getCountry(){
        return countryField.getText();
    }
    public String getCapacity(){
        return capacityField.getText();
    }
    public Date getStartDate(){
        return calendar1.getDate();
    }
    public Date getEndDate(){
        return calendar2.getDate();
    }
    public String getDetails(){
        return detailsField.getText();
    }
    public String getPrice(){
        return priceField.getText();
    }
    public String getDestinationIdToDelete(){
        if(destinationTable.getSelectedColumn() != 0) return null;
        return String.valueOf(destinationTable.getValueAt(destinationTable.getSelectedRow(),destinationTable.getSelectedColumn()));
    }
    public String getVacationIdToDelete(){
        if(vacationTable.getSelectedColumn() != 0) return null;
        return String.valueOf(vacationTable.getValueAt(vacationTable.getSelectedRow(),vacationTable.getSelectedColumn()));
    }

    public String getCapacityToBeEdited(){
        return editCapacity.getText();
    }
    public String getPriceToBeEdited(){
        return editPrice.getText();
    }
    public Date getStartDateToBeEdited(){
        return editStartDate.getDate();
    }
    public Date getEndDateToBeEdited(){
        return editEndDate.getDate();
    }
    public String getDetailsToBeEdited(){
        return editDetails.getText();
    }
    public String getVacationIDToBeEdited(){
        if(editVacationTable.getSelectedColumn() != 0) return null;
        return String.valueOf(editVacationTable.getValueAt(editVacationTable.getSelectedRow(),
                editVacationTable.getSelectedColumn()));
    }
    public String getDestinationIdToBeEdited(){
        if(editDestinationTable.getSelectedColumn() != 0) return null;
        return String.valueOf(editDestinationTable.getValueAt(
                editDestinationTable.getSelectedRow(),editDestinationTable.getSelectedColumn()
        ));
    }

    public void setEditDestinationTable(MouseListener a){editDestinationTable.addMouseListener(a);}
    public void setEditTableAction(MouseListener a){editVacationTable.addMouseListener(a);}
    public void setTableAction(MouseListener a){table.addMouseListener(a);}
    public void setDestinationTableAction(MouseListener a){destinationTable.addMouseListener(a);}
    public void setVacationTableAction(MouseListener a){vacationTable.addMouseListener(a);}
    public void setAddVacationButton(ActionListener a){ addVacationButton.addActionListener(a); }
    public void setDeleteVacationButton(ActionListener a){ deleteVacationButton.addActionListener(a); }
    public void setShowAddDestinationButton(ActionListener a){ showAddDestinationButton.addActionListener(a); }
    public void setAddDestinationButton(ActionListener a){ addDestinationButton.addActionListener(a); }
    public void setDeleteDestinationButton(ActionListener a){ deleteDestinationButton.addActionListener(a);}
    public void setInsertVacationButton(ActionListener a){ insertVacationButton.addActionListener(a); }
    public void setViewVacationButton(ActionListener a){
        viewVacationButton.addActionListener(a);
    }
    public void setEditVacationButton(ActionListener a){editVacationButton.addActionListener(a);}
    public void setFinalEditVacationButton(ActionListener a){finalEditVacationButton.addActionListener(a);}
}
