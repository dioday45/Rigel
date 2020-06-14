package ch.epfl.rigel.astronomy;

import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * Modelizes an astronomical epoch
 */
public enum Epoch {

    J2000("J2000", ZonedDateTime.of(LocalDate.of(2000, Month.JANUARY, 1),
            LocalTime.NOON, ZoneOffset.UTC)),
    J2010("J2010", ZonedDateTime.of(LocalDate.of(2010, Month.JANUARY, 1).minusDays(1),
            LocalTime.MIDNIGHT, ZoneOffset.UTC));

    private static final double MILLIS_TO_DAYS = 1/(1000*60*60*24d);
    private static final double DAYS_TO_JULIANS = 1/36525d;
    private final ZonedDateTime epoch;
    private final String name;


    private Epoch(String name, ZonedDateTime begining){
        this.name = name;
        epoch = begining;
    }


    /**
     * @param when : date to analyze
     * @return the number of days between the epoch and when
     */
    public double daysUntil(ZonedDateTime when){
        return (MILLIS_TO_DAYS * epoch.until(when, ChronoUnit.MILLIS));
    }

    /**
     * @param when : date to analyze
     * @return the number of julian centuries between the epoch and when
     */
    public double julianCenturiesUntil(ZonedDateTime when){
        return (daysUntil(when) * DAYS_TO_JULIANS);
    }




}
