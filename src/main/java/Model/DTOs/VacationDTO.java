package Model.DTOs;

import java.sql.Date;

public class VacationDTO {
    private String destination;

    private String city;

    private String details;

    private Date startDate;

    private Date endDate;

    private float price;

    public VacationDTO(String destination,String city,String details, Date startDate, Date endDate, float price) {
        this.details = details;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }
}
