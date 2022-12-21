package Entities;

import Constants.LogLevel;

public enum RentPeriod {
    MONTHLY,
    WEEKLY,
    DAILY,
    YEARLY;

    public static RentPeriod getEnum(String value) {
        for(RentPeriod v : values())
            if(v.name().equalsIgnoreCase(value)) return v;
        
        System.out.println(LogLevel.WARNING + "No RentPeriod was found for '" + value + "'. Returning MONTHLY.");
        return RentPeriod.MONTHLY;
    }
}
