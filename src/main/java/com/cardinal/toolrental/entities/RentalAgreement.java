package com.cardinal.toolrental.entities;

import com.cardinal.toolrental.dto.RentalRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RentalAgreement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @ManyToOne(cascade = {CascadeType.ALL})
    private Tool tool;
    @NotNull
    @OneToOne(cascade = {CascadeType.ALL})
    private RentalRequestDTO rentalRequestDTO;


    @DateTimeFormat(pattern = "mm/dd/yyyy")
    private LocalDate dueDate;
    private int chargeDays;
    private double preDiscountCharge;
    private double discountAmount;
    private double finalAmount;

    /**
     * Constructor
     *
     * @param tool
     * @param rentalRequestDTO
     */
    public RentalAgreement(Tool tool, RentalRequestDTO rentalRequestDTO) {
        this.tool = tool;
        this.rentalRequestDTO = rentalRequestDTO;

        dueDate = rentalRequestDTO.getStartDate().plusDays(rentalRequestDTO.getDays());
        chargeDays = calculateChargeDays();
        preDiscountCharge = chargeDays*tool.getDailyCharge();
        discountAmount = round(preDiscountCharge*(rentalRequestDTO.getDiscount()/100.0), 2);
        finalAmount = round(preDiscountCharge - discountAmount, 2);
    }

    /**
     * Charge days are determined by the type of tool
     * @return number of days to charge the customer
     */
    private int calculateChargeDays() {
        Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

        int count = 0;
        LocalDate currentDate = rentalRequestDTO.getStartDate();
        while(currentDate.isBefore(dueDate)) {
            if (isIndependenceDayObserved(currentDate) || isLaborDay((currentDate))) {
                if(tool.isHolidayCharge()) count++;
            } else if(weekend.contains(currentDate.getDayOfWeek())) {
                if(tool.isWeekendCharge()) count++;
            } else {
                if(tool.isWeekdayCharge()) count++;
            }
            currentDate = currentDate.plusDays(1);
        }

        return count;
    }

    /**
     * Check if Independence Day is observed on the supplied date
     *
     * True if the Date is Independence Day
     * or the Date is 7/3 and it's a Friday
     * or the Date is 7/5 and it's a Monday
     * @param date
     * @return
     */
    private boolean isIndependenceDayObserved(LocalDate date) {
        LocalDate freedom = LocalDate.of(date.getYear(), 7, 4);
        if(DayOfWeek.SATURDAY.equals(freedom.getDayOfWeek())) {
            return date.isEqual(freedom.minusDays(1));
        } else if(DayOfWeek.SUNDAY.equals(freedom.getDayOfWeek())) {
            return date.isEqual(freedom.plusDays(1));
        } else return date.isEqual(freedom);
    }

    /**
     * Round value to nearest decimal place
     * @param value
     * @param places
     * @return
     */
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Check if the supplied date is Labor Day
     *
     * True if the date is a Monday between 9/1 and 9/7 inclusive
     * @param date
     * @return
     */
    private boolean isLaborDay(LocalDate date) {
        return date.getMonth().equals(Month.SEPTEMBER) && date.getDayOfWeek().equals(DayOfWeek.MONDAY) && date.getDayOfMonth() < 8;
    }

}
