package Service.Handlers;

import Model.Destination;
import Repository.DestinationRepository;

public class DestinationHandler {

    public static boolean checkDestination(String country,String city){
        DestinationRepository repository = new DestinationRepository();
        String[] params = new String[2];
        params[0] = country;
        params[1] = city;
        try{
            Destination des = repository.executeQueryAndGetValue("Destination.Select",params);
            if(des == null)
                return false;
            else
                return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
