package Model.Mapper;

import Model.DTOs.VacationDTO;
import Model.Vacation;

public class VacationMapper {

    public static VacationDTO convertToDTO(Vacation vacation){
        return new VacationDTO(
                vacation.getDestination().getCountry(),
                vacation.getDestination().getLocation(),
                vacation.getDetails(),
                vacation.getStartDate(),
                vacation.getEndDate(),
                vacation.getPrice());

    }
}
