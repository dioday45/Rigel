package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.ClosedInterval;

import java.util.Locale;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 *  Modelizes a Moon
 *
 */
public final class Moon extends CelestialObject {

    private static final ClosedInterval PHASE_INTERVAL = ClosedInterval.of(0, 1);
    private final float phase;
    private final static String MOON_FRENCH_NAME = "Lune";

    /**
     * Instantiates a new Moon.
     *
     * @param equatorialPos the equatorial position
     * @param angularSize   the angular size
     * @param magnitude     the magnitude
     * @param phase         the phase of the moon
     * @throws IllegalArgumentException if the phase is not int the interval [0, 1]
     */
    public Moon(EquatorialCoordinates equatorialPos, float angularSize, float magnitude, float phase) {
        super(MOON_FRENCH_NAME, equatorialPos, angularSize, magnitude);
        this.phase = (float)Preconditions.checkInInterval(PHASE_INTERVAL, phase);
    }

    public static String getFrenchName(){
        return MOON_FRENCH_NAME;
    }

    @Override
    public String info() {
        return String.format(Locale.ROOT,
                "%s (%.1f%%)", this.name(), this.phase * 100);
    }
}
