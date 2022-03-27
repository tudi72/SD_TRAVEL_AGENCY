package demo.Service;

import demo.Model.Booking;
import demo.Model.DTOs.BookingDTO;
import demo.Model.DTOs.VacationDTO;
import demo.Model.Destination;
import demo.Model.Mapper.BookingMapper;
import demo.Model.Mapper.VacationMapper;
import demo.Model.Vacation;
import demo.Repository.DestinationRepository;
import demo.Repository.GenericInterface;
import demo.Repository.VacationRepository;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class SystemService {

    private GenericInterface repository ;

    public Vacation findVacationById(String id){
        repository = new VacationRepository();
        String[] params = new String[1];
        try{

            params[0] = String.valueOf(Integer.parseInt(id));
            return (Vacation) repository.executeQueryAndGetList("Vacation.SelectById",params).get(0);

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //TODO solve the capacity problem
    public Boolean updateCapacity(Vacation vacation){
        repository = new VacationRepository();
        String[] params = new String[2];
        try{
            int capacity = vacation.getCapacity();

            if(vacation.getStatus().equals("NOT_BOOKED")){
                if(vacation.getCapacity() > 1){
                    vacation.setStatus("IN_PROGRESS");
                }
                else if(vacation.getCapacity() == 1){
                    vacation.setCapacity(capacity-1);
                    vacation.setStatus("BOOKED");
                }
                else if(vacation.getCapacity() == 0){
                    vacation.setStatus("BOOKED");
                }
                params[0] = vacation.getStatus();
                params[1] = String.valueOf(vacation.getId());
                repository.executeQuery("Vacation.UpdateStatus",params);
                params[0] = String.valueOf(vacation.getCapacity());
                repository.executeQuery("Vacation.UpdateCapacity",params);
                return true;
            }
            else if(vacation.getStatus().equals("IN_PROGRESS") && capacity > 1){
                vacation.setCapacity(capacity-1);
                params[0] = vacation.getStatus();
                params[1] = String.valueOf(vacation.getId());
                repository.executeQuery("Vacation.UpdateStatus",params);
                params[0] = String.valueOf(vacation.getCapacity());
                repository.executeQuery("Vacation.UpdateCapacity",params);
                return true;
            }
            else if(vacation.getStatus().equals("IN_PROGRESS") && capacity == 1){
                vacation.setStatus("BOOKED");
                params[0] = vacation.getStatus();
                params[1] = String.valueOf(vacation.getId());
                repository.executeQuery("Vacation.UpdateStatus",params);
                params[0] = String.valueOf(vacation.getCapacity()-1);
                repository.executeQuery("Vacation.UpdateCapacity",params);
                return true;
            }
            else return false;
        }catch (Exception e){
                return false;
        }
    }

    public ArrayList<Destination> selectDestination(){
        repository = new DestinationRepository();
        return repository.executeQueryAndGetList("Destination.SelectAll",null);
    }

    /**
     * method for searching an ID and creating an instance of vacation and checking if all fields are correctly defined
     * @param capacity
     * @param details
     * @param endDate
     * @param price
     * @param startDate
     * @param tableValue
     * @return
     */
    public Vacation createVacation(String capacity, String details, java.util.Date endDate, String price, java.util.Date startDate, String tableValue) {
        Vacation vacation = new Vacation();
        try{
            repository = new DestinationRepository();
            vacation.setCapacity(Integer.parseInt(capacity));
            vacation.setDetails(details);
            vacation.setStartDate(new java.sql.Date(startDate.getTime()));
            vacation.setEndDate(new java.sql.Date(endDate.getTime()));
            vacation.setPrice(Float.parseFloat(price));
            String[] params = new String[1];
            params[0] = tableValue;
            Destination destination = (Destination) repository.executeQueryAndGetValue("Destination.SelectById",params);
            vacation.setDestination(destination);
            vacation.setStatus("NOT_BOOKED");
            return vacation;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public Destination findDestination(String id){
        repository = new DestinationRepository();
        String[] params = new String[1];
        params[0] = id;
        return (Destination) repository.executeQueryAndGetValue("Destination.SelectById",params);
    }

    /**
     * helper function to delete all bookings done for a vacation
     * @param vacation
     */
    public boolean deleteBookings(Vacation vacation) {
        repository = new VacationRepository();
        String[] params = new String[1];
        params[0] = String.valueOf(vacation.getId());
        ArrayList<Booking> bookings = repository.executeQueryAndGetList("Booking.SelectByVacation",params);
        try{
            return true;
        }
        catch (Exception e){
            return  false;
        }
    }

    /**
     * helper function to delete all vacations for one destination
     * @param d
     */
    public boolean deleteVacations(Destination d) {
        repository = new VacationRepository();
        String[] params = new String[1];
        params[0] = String.valueOf(d.getId());
        ArrayList<Vacation> vacations = repository.executeQueryAndGetList("Vacation.SelectByDestId",params);
        try{
            vacations.stream().forEach(x -> repository.delete(x));
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * transform a list of Bookings - > Bookings DTOs for display
     * @param rawBookings
     * @return
     */
    public ArrayList<BookingDTO> mapRawToDBookingTO(ArrayList<Booking> rawBookings) {
        try{
            return (ArrayList<BookingDTO>)rawBookings.stream().map(x -> BookingMapper.convertToDTO(x)).collect(Collectors.toList());
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * transform list of Vacations -> Vacation DTOs for display
     * @param rawVacations
     * @return
     */
    public ArrayList<VacationDTO> mapRawToVacationTO(ArrayList<Vacation> rawVacations) {
        try{
            ArrayList<VacationDTO> DTOs = new ArrayList<>();
            for(Vacation raw: rawVacations){
                VacationDTO DTO = VacationMapper.convertToDTO(raw);
                DTOs.add(DTO);
            }
            return DTOs;
            //return (ArrayList<VacationDTO>)rawVacations.stream().map(x -> VacationMapper.convertToDTO(x)).collect(Collectors.toList());
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * method which converts java.util.Date to java.sql.Date
     * @param startDateFilter
     * @return Date
     */
    public java.sql.Date getDate(java.util.Date startDateFilter) {
        try{
            return new java.sql.Date(startDateFilter.getTime());
        }
        catch (Exception e){
            return null;
        }
    }
}


