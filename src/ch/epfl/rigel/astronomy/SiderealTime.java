package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.Polynomial;
import ch.epfl.rigel.math.RightOpenInterval;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 *  * Modelizes the SideralTime
 *
 */

public final class SiderealTime {

    private static final double NANO_TO_HOURS = 1d/(3.6e12);
    private static final Polynomial POLYNOME_S0 = Polynomial.of(0.000025862, 2400.051336, 6.697374558);
    private static final RightOpenInterval ZERO_TO_TWENTY_FOUR = RightOpenInterval.of(0, 24);
    private static final double CST_HOURS = 1.002737909;

    private SiderealTime() {
    }

    /**
     * @param when : instant time to test
     * @return the Sidereal Time of when in radian and normalize to the interval [0, TAU[
     */
    public static double greenwich(ZonedDateTime when) {
        //change when to the time in UTC
        ZonedDateTime greenwichTime = when.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime greenwichTimeStartOfDay = greenwichTime.truncatedTo(ChronoUnit.DAYS);

        //Computation of T and t
        double T = Epoch.J2000.julianCenturiesUntil(greenwichTimeStartOfDay);
        double t = greenwichTimeStartOfDay.until(greenwichTime, ChronoUnit.NANOS) * NANO_TO_HOURS  ;

        double S0 = POLYNOME_S0.at(T);
        double S1 = CST_HOURS * t;

        double Sg = S0 + S1;
        Sg = ZERO_TO_TWENTY_FOUR.reduce(Sg);
        Sg = Angle.ofHr(Sg);

        return Sg;
    }


    /**
     * add the greenwich sideral time of "when" to the longitude of "where" normalized to the interval [0, TAU[
     *
     * @param when  : date to analyze
     * @param where : place of the local instant "when"
     * @return the Sideral time in radian of when in the local time at a place given (where)
     */
    public static double local(ZonedDateTime when, GeographicCoordinates where) {
        return Angle.normalizePositive(greenwich(when) + where.lon());
    }
}
