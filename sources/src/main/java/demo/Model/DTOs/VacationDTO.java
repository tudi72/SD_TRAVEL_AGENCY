package demo.Model.DTOs;

import java.sql.Date;

public class VacationDTO {
    private int id;

    private String destination;

    private String city;

    private String details;

    private Date startDate;

    private Date endDate;

    private float price;

    public VacationDTO(int id,String destination,String city,String details, Date startDate, Date endDate, float price) {
        this.id = id;
        this.details = details;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }
}
