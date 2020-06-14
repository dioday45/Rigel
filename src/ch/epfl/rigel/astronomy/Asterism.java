package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;

import java.util.Collections;
import java.util.List;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * Modelizes an Asterism
 */
public final class Asterism {

    private final List<Star> listOfStars;

    /**
     * Constructor of the Asterism
     * @param stars : List of the stars of the Asterism
     * @throws IllegalArgumentException if stars is empty
     */
    public Asterism(List<Star> stars){
        Preconditions.checkArgument(!stars.isEmpty());
        listOfStars = List.copyOf(stars);
    }

    /**
     * getter of the stars of the Asterism
     * @return list of the stars of the asterism
     */
    public List<Star> stars(){
        return listOfStars;
    }
}
