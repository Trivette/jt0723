package com.cardinal.toolrental.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
public class RentalRequestDTO {
    /**
     * This object is supplied in the body to the createRentalAgreement Service
     * Since this is user supplied, this is the most critical step of data validation in the application
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotNull(message = "Invalid tool code: No value provided")
    private String toolCode;
    @NotNull(message = "Invalid rental days: No amount provided")
    @Min(value=1, message="Please pick a value of 1 or more for total rental days!")
    private int days;
    @NotNull(message = "Invalid discount: No discount provided")
    @Min(value=0, message="Please give a discount between 0-100")
    @Max(value=100, message="Please give a discount between 0-100")
    private int discount;
    @NotNull(message = "Invalid start date: No date provided")
    @DateTimeFormat(pattern = "mm/dd/yyyy")
    private LocalDate startDate;

    public RentalRequestDTO(String toolCode, int days, int discount, LocalDate startDate) {
        this.toolCode = toolCode;
        this.days = days;
        this.discount = discount;
        this.startDate = startDate;
    }
}
