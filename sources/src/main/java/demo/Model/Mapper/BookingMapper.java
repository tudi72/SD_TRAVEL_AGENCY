package demo.Model.Mapper;

import demo.Model.Booking;
import demo.Model.DTOs.BookingDTO;

public class BookingMapper {

    public static BookingDTO convertToDTO(Booking booking){
        BookingDTO dto = new BookingDTO(
                booking.getUser().getFirstName(),
                booking.getUser().getLastName(),
                booking.getUser().getEmail(),
                booking.getVacation().getDetails(),
                booking.getVacation().getStartDate(),
                booking.getVacation().getEndDate(),
                booking.getVacation().getPrice());
        return dto;
    }
}
