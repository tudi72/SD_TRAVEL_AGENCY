package Service;

import Model.Destination;
import Model.Vacation;
import Repository.DestinationRepository;
import Repository.GenericInterface;
import Repository.VacationRepository;

import java.util.ArrayList;

public class AgencyService implements AgencyServiceInterface{

    GenericInterface repository = null;
    SystemService systemService = new SystemService();

    @Override
    public void addDestination(Destination destination) {
        repository = new DestinationRepository();
        repository.insert(destination);
    }

    @Override
    public boolean deleteDestination(Destination d) {
        repository = new DestinationRepository();
        systemService.deleteVacations(d);
        return repository.delete(d);
    }

    @Override
    public void addVacation(Vacation vacation) {
        repository = new VacationRepository();
        repository.insert(vacation);
    }

    @Override
    public boolean editVacation(Vacation newVacation) {
        repository = new VacationRepository();
        return repository.update(newVacation);
    }

    @Override
    public boolean deleteVacation(Vacation vacation) {
        repository = new VacationRepository();
        systemService.deleteBookings(vacation);
         return repository.delete(vacation);
    }

    @Override
    public ArrayList<Vacation> viewVacation() {
        repository = new VacationRepository();
        return (ArrayList<Vacation>) repository.executeQueryAndGetList("Vacation.SelectAll",null);
    }
}
