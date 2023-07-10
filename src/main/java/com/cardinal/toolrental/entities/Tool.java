package com.cardinal.toolrental.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String code;
    @NotNull(message = "A tool type must be provided")
    @Enumerated(EnumType.STRING)
    private ToolType type;
    @NotNull(message = "A tool brand must be provided")
    @Enumerated(EnumType.STRING)
    private ToolBrand brand;


    private double dailyCharge;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;

    public enum ToolType {
        Ladder,
        Chainsaw,
        Jackhammer
    }

    public enum ToolBrand {
        Stihl,
        Werner,
        DeWalt,
        Ridgid
    }

    /**
     * Constructor
     *
     * Values for daily charge and which days to charge for are predetermined
     * There is no need to supply those values
     *
     * @param code
     * @param type
     * @param brand
     */
    public Tool(String code, ToolType type, ToolBrand brand) {
        this.code = code;
        this.type = type;
        this.brand = brand;

        switch(type) {
            case Ladder:
                this.dailyCharge = 1.99;
                this.weekdayCharge = true;
                this.weekendCharge = true;
                this.holidayCharge = false;
                break;
            case Chainsaw:
                this.dailyCharge = 1.49;
                this.weekdayCharge = true;
                this.weekendCharge = false;
                this.holidayCharge = true;
                break;
            case Jackhammer:
                this.dailyCharge = 2.99;
                this.weekdayCharge = true;
                this.weekendCharge = false;
                this.holidayCharge = false;
                break;
        }
    }
}
