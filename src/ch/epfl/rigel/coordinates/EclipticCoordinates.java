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
 * Modelizes the system of ecliptic coordinates
 *
 */

public final class EclipticCoordinates extends SphericalCoordinates {

    private static final RightOpenInterval LON_INTERVAL = RightOpenInterval.of(0, Angle.TAU);
    private static final ClosedInterval LAT_INTERVAL = ClosedInterval.symmetric(Math.PI);

    /**
     * @param a : longitude
     * @param b : latitude
     */
    private EclipticCoordinates(double a, double b) {
        super(a, b);
    }

    /**
     * verify if latitude and longitude are valid. If so, return the GeographicCoordinates with
     * latitude and longitude changed to radian.
     *
     * @param lon  : longitude in radians
     * @param lat : latitude in radians
     * @throws IllegalArgumentException if lon is not on [0, TAU[, or lat in [-TAU/4, TAU/4]
     * @return a GeographicCoordinates
     *
     */
    public static EclipticCoordinates of(double lon, double lat) {

        lon = Preconditions.checkInInterval(LON_INTERVAL, lon);
        lat = Preconditions.checkInInterval(LAT_INTERVAL, lat);
        return new EclipticCoordinates(lon, lat);


    }

    /**
     * @return the ecliptic longitude in radians
     */
    public double lon() {
        return super.lon();
    }

    /**
     * @return the ecliptic longitude in degrees
     */
    public double lonDeg() {
        return super.lonDeg();
    }

    /**
     * @return the ecliptic latitude in radians
     */
    public double lat() {
        return super.lat();
    }

    /**
     * @return the ecliptic latitude in degrees
     */
    public double latDeg() {
        return super.latDeg();
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "(λ=%.4f°, β=%.4f°)", this.lonDeg(), this.latDeg());
    }
}
