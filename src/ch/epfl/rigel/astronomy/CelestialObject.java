package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import java.util.Objects;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * The type Celestial object.
 */
public abstract class CelestialObject {

    private final String name;
    private final EquatorialCoordinates equatorialPos;
    private final float angularSize;
    private final float magnitude;


    /**
     * (package private) Constructor of the CelestialObject
     *
     * @param name          the name of the celestial object
     * @param equatorialPos the equatorial position
     * @param angularSize   the angular size
     * @param magnitude     the magnitude
     * @throws IllegalArgumentException if the angular size is negative
     * @throws NullPointerException if the name or the equatorialPos is null
     */
    CelestialObject(String name, EquatorialCoordinates equatorialPos, float angularSize, float magnitude){

        Preconditions.checkArgument(!(angularSize < 0));

        this.name = Objects.requireNonNull(name);
        this.equatorialPos = Objects.requireNonNull(equatorialPos);
        this.angularSize = angularSize;
        this.magnitude = magnitude;
    }

    /**
     * @return the name of the celestial object
     */
    public String name(){
        return this.name;
    }

    /**
     * @return the angular size
     */
    public double angularSize(){
        return this.angularSize;
    }

    /**
     * @return the magnitude
     */
    public double magnitude(){
        return this.magnitude;
    }

    /**
     * @return the equatorial coordinates
     */
    public EquatorialCoordinates equatorialPos(){
        return this.equatorialPos;
    }

    /**
     * @return the info of the celestial object
     */
    public String info(){
        return this.name();
    }

    @Override
    public String toString() {
        return this.info();
    }
}
