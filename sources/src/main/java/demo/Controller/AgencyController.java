package demo.Controller;

import demo.Model.DTOs.VacationDTO;
import demo.Controller.TableGenerator.*;
import demo.Model.Destination;
import demo.Model.Vacation;
import demo.Service.*;
import demo.Service.Handlers.DestinationHandler;
import demo.View.AgencyPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.util.ArrayList;


public class AgencyController {

    private AgencyPage agencyPage;
    private VacationDTOGenerator vacationDTOGenerator = new VacationDTOGenerator();
    private DestinationDTOGenerator destinationDTOGenerator = new DestinationDTOGenerator();
    private BookingDTOGenerator bookingDTOGenerator = new BookingDTOGenerator();

    private AgencyServiceInterface agencyService = new AgencyService();
    private SystemService systemService = new SystemService();
    private String vacationId = null;
    private String destinationId = null;

    /**
     * constructor initializing all the actions possible for the travel agency
     * @param agencyPage
     */
    public AgencyController(AgencyPage agencyPage){
        this.agencyPage = agencyPage;

        this.agencyPage.setVisible(true);


        agencyPage.setShowAddDestinationButton(new ShowAddDestinationAction());
        agencyPage.setAddDestinationButton(new AddDestinationAction());

        agencyPage.setAddVacationButton(new AddVacationAction());
        agencyPage.setInsertVacationButton(new InsertVacationAction());

        agencyPage.setViewVacationButton(new ViewVacationAction());

        agencyPage.setDeleteDestinationButton(new DeleteDestinationAction());

        agencyPage.setDeleteVacationButton(new DeleteVacationAction());


        agencyPage.setEditVacationButton(new EditVacationAction());
        agencyPage.setFinalEditVacationButton(new FinalEditVacation());
    }

    /**
     * agency page: after clicking add vacation -> select a destination for vacation
     */
    private class AddVacationAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            ArrayList<Destination> rawDestinations = systemService.selectDestination();
            JTable table = (new DestinationGenerator()).generateTable(rawDestinations);
            agencyPage.setSelectDestForVacation(table);
            agencyPage.setTableAction(new SelectTableCellAction());
        }
    }


    /**
     * agency page: redirecting to add destination page
     */
    private class ShowAddDestinationAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            agencyPage.setAddDestinationPanel();
        }
    }

    /**
     * agency page: inserting a destination into database
     */
    private class AddDestinationAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Destination testDestination = new Destination(agencyPage.getCity(), agencyPage.getCountry());
            if(!DestinationHandler.checkDestination(agencyPage.getCountry(), agencyPage.getCity())){
                agencyPage.inputError("Destination added succesfully");
                agencyService.addDestination(testDestination);
            }
            else
                agencyPage.inputError("Destination exists");

        }
    }

    /**
     * agency page : when clicked add vacation -> displays list of destinations for agency to choose one
     */
    private class SelectTableCellAction implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println(agencyPage.getTableValue());
            agencyPage.setAddVacationPanel();
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
     * agency page : after filling in fields for vacation -> inserts vacation to database
     */
    private class InsertVacationAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(agencyPage.getStartDate());
            Vacation vacation = systemService.createVacation(agencyPage.getCapacity(),agencyPage.getDetails(),
                    agencyPage.getEndDate(),agencyPage.getPrice(),agencyPage.getStartDate(),agencyPage.getTableValue());
            if(vacation == null){
                agencyPage.inputError("Cannot insert vacation, some fields are incorrect");
            }
            else{
                agencyPage.inputError("Successfully inserted");
                agencyService.addVacation(vacation);

            }
        }
    }

    /**
     * agency page: after clicking "VIEW VACATION PACKAGES "  -> display frame with vacations
     */
    private class ViewVacationAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<Vacation> rawVacations = agencyService.viewVacation();
            ArrayList<VacationDTO> vacationDTOS = systemService.mapRawToVacationTO(rawVacations);
            agencyPage.setVacationTable(vacationDTOGenerator.generateTable(vacationDTOS));
        }
    }

    /**
     * agency page: after it was chosen the destination,it will be deleted
     */
    private class DeleteDestinationAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            ArrayList<Destination> rawDestinations = systemService.selectDestination();
            JTable table = (new DestinationGenerator()).generateTable(rawDestinations);
            agencyPage.setDestinationTableForDelete(table);
            agencyPage.setDestinationTableAction(new DeleteDestinationTableAction());

        }
    }


    /**
     * Mouse Listener: delete destination when clicked on ID
     */
    private class DeleteDestinationTableAction implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            String destinationId = agencyPage.getDestinationIdToDelete();
            Destination destination = systemService.findDestination(destinationId);
            if(destination == null){
                agencyPage.inputError("Cannot delete destination");
            }
            else{
                agencyPage.inputError("Successfully deleted");
                agencyService.deleteDestination(destination);
            }
        }
        @Override
        public void mousePressed(MouseEvent e) { }
        @Override
        public void mouseReleased(MouseEvent e) { }
        @Override
        public void mouseEntered(MouseEvent e) { }
        @Override
        public void mouseExited(MouseEvent e) { }
    }

    /**
     * agency page: choose a vacation and delete it
     */
    private class DeleteVacationAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            agencyPage.setVacationTableForDelete((new VacationGenerator()).generateTable(agencyService.viewVacation()));
            agencyPage.setVacationTableAction(new DeleteVacationTableAction());

        }
    }

    /**
     * agency page: delete the vacation clicked on vacation table
     */
    private class DeleteVacationTableAction implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            String vacationId = agencyPage.getVacationIdToDelete();
            Vacation vacation = systemService.findVacationById(vacationId);
            if(vacation == null){
                agencyPage.inputError("Cannot delete vacation");
            }
            else{
                agencyPage.inputError("Successfully deleted");
                agencyService.deleteVacation(vacation);
            }
        }
        @Override
        public void mousePressed(MouseEvent e) { }
        @Override
        public void mouseReleased(MouseEvent e) { }
        @Override
        public void mouseEntered(MouseEvent e) { }
        @Override
        public void mouseExited(MouseEvent e) { }
    }

    /**
     * after choosing the vacation to be deleted we choose the new destination
     */
    private class MiddleEditVacationAction implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            vacationId = agencyPage.getVacationIDToBeEdited();
            JTable table = (new DestinationGenerator()).generateTable(systemService.selectDestination());
            agencyPage.setDestinationToEditTable(table);
            agencyPage.setEditDestinationTable(new AfterMiddleEditVacationAction());
        }
        @Override
        public void mousePressed(MouseEvent e) { }
        @Override
        public void mouseReleased(MouseEvent e) { }
        @Override
        public void mouseEntered(MouseEvent e) { }
        @Override
        public void mouseExited(MouseEvent e) { }
    }

    /**
     * agency page: after choosing all fields, we update the vacation
     */
    private class FinalEditVacation implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           Vacation vacation = systemService.createVacation(
                   agencyPage.getCapacityToBeEdited(),
                   agencyPage.getDetailsToBeEdited(),
                   agencyPage.getEndDateToBeEdited(),
                   agencyPage.getPriceToBeEdited(),
                   agencyPage.getStartDateToBeEdited(),destinationId);
           if(vacation == null){
               agencyPage.inputError("Some details are not correct,please review them");
           }
           else{
               vacation.setId(Integer.parseInt(vacationId));

               if(agencyService.editVacation(vacation)){
                   agencyPage.cleanRoot(agencyPage.getWorkingPanel());
                   agencyPage.inputError("Successfully edited");
               }
               else agencyPage.inputError("Error at editing, review the information");

           }
        }
    }

    /**
     *  agency page: after clicking edit vacation,it redirect to a table with vacation list
     */
    private class EditVacationAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<Vacation> rawVacations = agencyService.viewVacation();
            JTable table = (new VacationGenerator()).generateTable(rawVacations);
            agencyPage.setVacationToEditTable(table);
            agencyPage.setEditTableAction(new MiddleEditVacationAction());
        }
    }

    /**
     * after clicking the vacation to be edited and destination chosen
     * the agency page redirects to filling in the other text fields
     */
    private class AfterMiddleEditVacationAction implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            destinationId = agencyPage.getDestinationIdToBeEdited();
            Vacation vacation = systemService.findVacationById(vacationId);
            if(vacation == null){
                agencyPage.inputError("couldn't find the selected vacation");
            }
            else{
                String price = String.valueOf(vacation.getPrice());
                Date startDate = vacation.getStartDate();
                Date endDate = vacation.getEndDate();
                String details = vacation.getDetails();
                String capacity = String.valueOf(vacation.getCapacity());
                agencyPage.setVacationToEdit(capacity,startDate,endDate,details,price);
            }
        }
        @Override
        public void mousePressed(MouseEvent e) { }
        @Override
        public void mouseReleased(MouseEvent e) { }
        @Override
        public void mouseEntered(MouseEvent e) { }
        @Override
        public void mouseExited(MouseEvent e) { }
    }
}
