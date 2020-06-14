package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.Epoch;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.Polynomial;

import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 * 
 * Creates a conversion from Ecliptic to Equatorial
 * 
 */
public final class EclipticToEquatorialConversion implements Function<EclipticCoordinates, EquatorialCoordinates> {

    private final double sinObliquity;
    private final double cosObliquity;
    private static final double A = Angle.ofArcsec(0.00181);
    private static final double B = (-Angle.ofArcsec(0.0006));
    private static final double C = (-Angle.ofArcsec(46.815));
    private static final double D = Angle.ofDMS(23, 26, 21.45);
    private static final Polynomial EPSILON_POLYNOME = Polynomial.of(A,B,C,D);

    /**
     * Constructor of the conversion from Ecliptic to Equatorial
     * @param when : Moment of the observation
     */
    public EclipticToEquatorialConversion(ZonedDateTime when){

        double julianCenturies = Epoch.J2000.julianCenturiesUntil(when);
        double epsilon = EPSILON_POLYNOME.at(julianCenturies);

        sinObliquity = Math.sin(epsilon);
        cosObliquity = Math.cos(epsilon);

    }

    /**
     * converts the EclipticCoordinates to EquatorialCoordinates
     * @param eclipticCoordinates : EclipticCoordinates to be converted
     * @return : converted coordinates in EquatorialCoordinates
     */
    @Override
    public EquatorialCoordinates apply(EclipticCoordinates eclipticCoordinates) {

        double sinLongitude = Math.sin(eclipticCoordinates.lon());
        double cosLongitude = Math.cos(eclipticCoordinates.lon());
        double sinLatitude = Math.sin(eclipticCoordinates.lat());
        double cosLatitude = Math.cos(eclipticCoordinates.lat());
        double tanLatitude = Math.tan(eclipticCoordinates.lat());

        double rightAscension = Math.atan2(sinLongitude* cosObliquity - tanLatitude* sinObliquity,cosLongitude );
        rightAscension = Angle.normalizePositive(rightAscension);

        double declination = Math.asin(sinLatitude* cosObliquity + cosLatitude * sinObliquity * sinLongitude);

        return EquatorialCoordinates.of(rightAscension, declination);
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }
}
