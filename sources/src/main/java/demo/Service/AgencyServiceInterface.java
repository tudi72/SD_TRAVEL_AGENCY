package demo.Service;

import demo.Model.Destination;
import demo.Model.Vacation;

import java.util.ArrayList;

public interface AgencyServiceInterface {
    /**
     * inserts into database destination from GUI
     * @param d
     */
    public void addDestination(Destination d);

    /**
     * deletes a destination and all its foreign constraints
     * @param d
     * @return
     */
    public boolean deleteDestination(Destination d);

    /**
     * inserts into database vacation from GUI
     * @param vacation
     */
    public void addVacation(Vacation vacation);

    /**
     * edit a vacation from GUI
     * @param newVacation
     * @return
     */
    public boolean editVacation(Vacation newVacation);

    /**
     * delete a vacation from GUI by clicking on list of vacation
     * @param vacation
     * @return
     */
    public boolean deleteVacation(Vacation vacation);

    /**
     * returns all vacations from database
     * @return list<vacation>
     */
    public ArrayList<Vacation> viewVacation();


}
