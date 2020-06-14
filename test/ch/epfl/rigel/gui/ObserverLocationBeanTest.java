package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import org.junit.jupiter.api.Test;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */

class ObserverLocationBeanTest {
    @Test
    void mainTest() {
        ObserverLocationBean test = new ObserverLocationBean();
        test.setLatDeg(30);
        test.setLonDeg(30);
        System.out.println(test.getLatDeg() + ", " + test.getLonDeg());
        System.out.println(test.getCoordinates());
        test.setLonDeg(40);
        test.setLatDeg(40);
        System.out.println(test.getLatDeg() + ", " + test.getLonDeg());
        System.out.println(test.getCoordinates());
        test.setCoordinates(GeographicCoordinates.ofDeg(50, 50));
        System.out.println(test.getLatDeg() + ", " + test.getLonDeg());
        System.out.println(test.getCoordinates());

    }

}