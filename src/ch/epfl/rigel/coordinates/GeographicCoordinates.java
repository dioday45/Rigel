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
 * Modilzes the system of geographic coordinates
 *
 */

public final class GeographicCoordinates extends SphericalCoordinates {

    private static final RightOpenInterval LG_DEG_INTERVAL = RightOpenInterval.symmetric(360);
    private static final ClosedInterval LAT_DEG_INTERVAL = ClosedInterval.symmetric(180);

    /**
     * @param a : longitude
     * @param b : latitude
     */
    private GeographicCoordinates(double a, double b) {
        super(a, b);
    }

    /**
     * verify if lonDeg and latDeg are valid. If so, return the GeographicCoordinates with
     * lonDeg and latDeg changed to radian.
     *
     * @param lonDeg : longitude in degree
     * @param latDeg : latitude in degree
     * @throws IllegalArgumentException if lonDeg is not on [-180, 180[, or latDeg in [-90, 90]
     * @return a GeographicCoordinates
     */
    public static GeographicCoordinates ofDeg(double lonDeg, double latDeg) {

        latDeg = Preconditions.checkInInterval(LAT_DEG_INTERVAL, latDeg);
        lonDeg = Preconditions.checkInInterval(LG_DEG_INTERVAL, lonDeg);
        return new GeographicCoordinates(Angle.ofDeg(lonDeg), Angle.ofDeg(latDeg));

    }

    /**
     * @param lonDeg
     * @return true if lonDeg is a valid longitude
     */
    public static boolean isValidLonDeg(double lonDeg) {
        return LG_DEG_INTERVAL.contains(lonDeg);
    }

    /**
     * @param latDeg
     * @return true if latDeg is a valid latitude
     */
    public static boolean isValidLatDeg(double latDeg) {
        return LAT_DEG_INTERVAL.contains(latDeg);
    }

    /**
     * @return the longitude
     */
    public double lon() {
        return super.lon();
    }

    /**
     * @return the longitude in degree
     */
    public double lonDeg() {
        return super.lonDeg();
    }

    /**
     * @return the latitude
     */
    public double lat() {
        return super.lat();
    }

    /**
     * @return the latitude in degree
     */
    public double latDeg() {
        return super.latDeg();
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "(lon = %.4f°, lat = %.4f°)",this.lonDeg(),
                this.latDeg());
    }
}
