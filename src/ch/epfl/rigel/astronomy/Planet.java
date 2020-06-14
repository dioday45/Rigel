package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * Modelizes a Planet
 *
 */

public final class Planet extends CelestialObject {
    /**
     * Constructor of Planet
     *
     * @param name          : name of the planet
     * @param equatorialPos : coordinates of the planet
     * @param angularSize   : size of the planet
     * @param magnitude     : magnitude of the planet
     */
    public Planet(String name, EquatorialCoordinates equatorialPos, float angularSize, float magnitude) {
        super(name, equatorialPos, angularSize, magnitude);
    }
}
