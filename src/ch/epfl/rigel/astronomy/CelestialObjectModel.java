package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 * <p>
 * Modelizes the way the CelestialObject moves
 */
public interface CelestialObjectModel<O> {
    /**
     * modelizes the object according to the model for the given number of days since J2010
     *
     * @param daysSinceJ2010                 : number of days since J2010
     * @param eclipticToEquatorialConversion : conversion of the CelestialObject
     * @return the object modelized
     */
    public abstract O at(double daysSinceJ2010,
                         EclipticToEquatorialConversion eclipticToEquatorialConversion);
}
