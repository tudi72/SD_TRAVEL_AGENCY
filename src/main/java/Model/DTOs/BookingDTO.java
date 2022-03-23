package Model.DTOs;

import java.sql.Date;

public class BookingDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String details;

    private Date startDate;

    private Date endDate;

    private float price;


    public BookingDTO(String firstName, String lastName, String email, String details, Date startDate, Date endDate, float price) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.details = details;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }
}
