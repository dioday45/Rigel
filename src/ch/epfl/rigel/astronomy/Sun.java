package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.util.Objects;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * Modelizes a Sun
 *
 */
public final class Sun extends CelestialObject {

    private final EclipticCoordinates eclipticPos;
    private final float meanAnomaly;
    private static final String SUN_FRENCH_NAME = "Soleil";


    /**
     * Instantiates a new Sun.
     *
     * @param eclipticPos   the ecliptic position of the sun
     * @param equatorialPos the equatorial position of the sun
     * @param angularSize   the angular size
     * @param meanAnomaly   the mean anomaly
     * @throws NullPointerException if the eclipticPos is null
     */
    public Sun(EclipticCoordinates eclipticPos, EquatorialCoordinates equatorialPos, float angularSize, float meanAnomaly) {
        super(SUN_FRENCH_NAME, equatorialPos, angularSize, (float) -26.7);
        this.eclipticPos = Objects.requireNonNull(eclipticPos);
        this.meanAnomaly = meanAnomaly;
    }


    /**
     * @return the ecliptic coordinates of the sun
     */
    public EclipticCoordinates eclipticPos() {
        return eclipticPos;
    }

    /**
     * @return the mean anomaly (double)
     */
    public double meanAnomaly() {
        return (double) meanAnomaly;
    }

    public static String getFrenchName(){
        return SUN_FRENCH_NAME;
    }
}
