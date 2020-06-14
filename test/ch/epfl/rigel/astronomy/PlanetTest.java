package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */

class PlanetTest {

    private static final Planet test1 = new Planet("test1", EquatorialCoordinates.of(2, 0), 20, 20);

    @Test
    void myConstructor() {
        Planet test5 = new Planet("test1", EquatorialCoordinates.of(2, 0), 0, 20);
        assertThrows(IllegalArgumentException.class, () -> {
            Planet test2 = new Planet("test1", EquatorialCoordinates.of(2, 0), -2, 20);
        });

        assertThrows(NullPointerException.class, () -> {
            Planet test3 = new Planet(null, EquatorialCoordinates.of(2, 0), 20, 20);
        });

        assertThrows(NullPointerException.class, () -> {
            Planet test4 = new Planet("test1", null, 20, 20);
        });

    }

    @Test
    void myName() {
        assertEquals("test1", test1.name());
        assertEquals("test1", test1.info());
    }

    @Test
    void myAngularSize() {
        assertEquals(20, test1.angularSize());
    }

    @Test
    void myMagnitude(){
        assertEquals(20, test1.magnitude());
    }


    //others
    @Test
    void infoWorks() {
        Planet p = new Planet("Jupiter", EquatorialCoordinates.of(0, 0), 1f,
                10f);
        assertEquals("Jupiter", p.info());
    }

    @Test
    void toStringWorks() {
        Planet p = new Planet("Jupiter", EquatorialCoordinates.of(0, 0), 1f,
                10f);
        assertEquals("Jupiter", p.toString());
    }

    @Test
    @SuppressWarnings("unused")
    void constructorThrowsUsualExceptions() {
        assertThrows(NullPointerException.class, () -> {
            Planet p = new Planet(null, EquatorialCoordinates.of(0, 0), 1f,
                    10f);
        });
        assertDoesNotThrow(() -> {
            Planet p = new Planet("Jupiter", EquatorialCoordinates.of(0, 0), 1f,
                    1000f);
        });
        assertThrows(NullPointerException.class, () -> {
            Planet p = new Planet("Jupiter", null, 1f, 10f);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Planet p = new Planet("Jupiter", EquatorialCoordinates.of(0, 0),
                    -0.1f, 10f);
        });
    }

    @Test
    void gettersWork() {
        float angularSize = 2;
        var eqPos = EquatorialCoordinates.of(0, 0);
        float magnitude = -26.7f;
        String name = "Neptune";
        Planet p = new Planet(name, eqPos, angularSize, magnitude);
        assertEquals(angularSize, p.angularSize());
        assertEquals(magnitude, p.magnitude());
        assertEquals(name, p.name());
        assertEquals(0, p.equatorialPos().ra());
        assertEquals(0, p.equatorialPos().dec());

        angularSize = 0.2f;
        eqPos = EquatorialCoordinates.of(3, 1);
        magnitude = 5045f;
        name = "ajsgdusahjd";
        p = new Planet(name, eqPos, angularSize, magnitude);
        assertEquals(angularSize, p.angularSize());
        assertEquals(magnitude, p.magnitude());
        assertEquals(name, p.name());
        assertEquals(3, p.equatorialPos().ra());
        assertEquals(1, p.equatorialPos().dec());
    }

}