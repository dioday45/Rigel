package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.ClosedInterval;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * Modelizes a Star
 *
 */
public final class Star extends CelestialObject {

    private static final ClosedInterval COLOR_INDEX_INTERVAL = ClosedInterval.of(-0.5, 5.5);

    private final int hipparcosId;
    private final float colorIndex;
    private final int colorTemperature;

    /**
     * Constructor of the Star
     *
     * @param hipparcosId   : Hipparcos Id of the star
     * @param name          : name of the star
     * @param equatorialPos : equatorial position
     * @param magnitude     : magnitude
     * @param colorIndex    : colorIndex
     * @throws IllegalArgumentException if the Hipparcos is negative or the color index not
     * in [-0.5, 5.5]
     */
    public Star(int hipparcosId, String name, EquatorialCoordinates equatorialPos,
                float magnitude, float colorIndex) {

        super(name, equatorialPos, 0, magnitude);


        Preconditions.checkArgument(!(hipparcosId < 0));
        Preconditions.checkInInterval(COLOR_INDEX_INTERVAL, colorIndex);
        this.colorIndex = colorIndex;
        this.hipparcosId = hipparcosId;
        double tempToReturn = 4600 * ((1 / (0.92 * colorIndex + 1.7)) + (1 / (0.92 * colorIndex + 0.62)));
        colorTemperature = (int) Math.floor(tempToReturn);

    }

    /**
     * getter of the Hipparcos Id
     *
     * @return Hipparcos Id
     */
    public int hipparcosId() {
        return hipparcosId;
    }

    /**
     * getter of the color's temperature
     *
     * @return rounded to the closest int of the color
     */
    public int colorTemperature() {
        return colorTemperature;
    }
}
