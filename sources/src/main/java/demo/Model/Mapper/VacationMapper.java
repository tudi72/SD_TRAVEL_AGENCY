package demo.Model.Mapper;

import demo.Model.DTOs.VacationDTO;
import demo.Model.Vacation;

public class VacationMapper {

    public static VacationDTO convertToDTO(Vacation vacation){
        return new VacationDTO(
                vacation.getId(),
                vacation.getDestination().getCountry(),
                vacation.getDestination().getLocation(),
                vacation.getDetails(),
                vacation.getStartDate(),
                vacation.getEndDate(),
                vacation.getPrice());

    }
}
