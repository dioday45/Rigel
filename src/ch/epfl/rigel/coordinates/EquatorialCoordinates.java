package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * Modelizes the system of equatorial coordinatees
 *
 */
public final class EquatorialCoordinates extends SphericalCoordinates {

    private static final RightOpenInterval RA_INTERVAL = RightOpenInterval.of(0, Angle.TAU);
    private static final ClosedInterval DEC_INTERVAL = ClosedInterval.symmetric(Math.PI);

    /**
     * @param a : longitude
     * @param b : latitude
     */
    private EquatorialCoordinates(double a, double b) {
        super(a, b);
    }

    /**
     * verify if ra and dec are valid. If so, return the GeographicCoordinates with
     * ra and dec in radian else throws a IllegalArgumentException.
     *
     * @param ra  : right ascension in radian
     * @param dec : declination in radian
     * @throws IllegalArgumentException if ra is not on [0, TAU[, or ldec in [-TAU/4, TAU/4]
     * @return a GeographicCoordinates
     */
    public static EquatorialCoordinates of(double ra, double dec) {

        ra = Preconditions.checkInInterval(RA_INTERVAL, ra);
        dec = Preconditions.checkInInterval(DEC_INTERVAL, dec);
        return new EquatorialCoordinates(ra, dec);

    }

    /**
     * @return the right ascension
     */
    public double ra() {
        return super.lon();
    }

    /**
     * @return the right ascension in degree
     */
    public double raDeg() {
        return super.lonDeg();
    }

    /**
     * @return the right ascension in hour
     */
    public double raHr() {
        return Angle.toHr(super.lon());
    }

    /**
     * @return the declination
     */
    public double dec() {
        return super.lat();
    }

    /**
     * @return the declination in degree
     */
    public double decDeg() {
        return super.latDeg();
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "(ra = %.4fh, dec = %.4f°)", this.raHr(), this.decDeg());
    }
}
