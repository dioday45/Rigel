package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */

class MoonTest {

    @Test
    void info() {
        Moon lune = new Moon(EquatorialCoordinates.of(1, 0), 20, 12, (float)0.45454545);
        assertEquals("Lune (45.5%)", lune.toString());
    }

    @Test
    void myConstruct(){
        assertThrows(IllegalArgumentException.class, () -> {
            Moon lune = new Moon(EquatorialCoordinates.of(1, 0), 20, 12, 7);
        });
    }

    //others

    @Test
    void infoToStringWorksOnMoon() {
        Moon test = new Moon(EquatorialCoordinates.of(1,1),1,1,1);
        assertEquals("Lune (100.0%)", test.toString());
    }

    @Test
    void infoWorks() {
        Moon m = new Moon(EquatorialCoordinates.of(0,0), 1f, 10f, 0.7f);
        assertEquals("Lune (70.0%)", m.info());
        m = new Moon(EquatorialCoordinates.of(0,0), 1f, 10f, 0.012f);
        assertEquals("Lune (1.2%)", m.info());
        m = new Moon(EquatorialCoordinates.of(0,0), 1f, 10f, 1f);
        assertEquals("Lune (100.0%)", m.info());
        m = new Moon(EquatorialCoordinates.of(0,0), 1f, 10f, 0f);
        assertEquals("Lune (0.0%)", m.info());
    }

    @Test
    @SuppressWarnings("unused")
    void constructorThrowsIAEforWrongPhase() {
        assertThrows(IllegalArgumentException.class, () ->  {
            Moon m = new Moon(EquatorialCoordinates.of(2, 1), 0,  2, 1.1f);
        });
        assertThrows(IllegalArgumentException.class, () ->  {
            Moon m = new Moon(EquatorialCoordinates.of(2, 1), 0,  2, -0.1f);
        });
        assertDoesNotThrow(() ->  {
            Moon m = new Moon(EquatorialCoordinates.of(2, 1), 0,  2, 0f);
        });
        assertDoesNotThrow(() ->  {
            Moon m = new Moon(EquatorialCoordinates.of(2, 1), 0,  2, 1f);
        });
        assertDoesNotThrow(() ->  {
            Moon m = new Moon(EquatorialCoordinates.of(2, 1), 0,  2, 0.5f);
        });
    }

    @Test
    @SuppressWarnings("unused")
    void constructorThrowsUsualExceptions() {
        assertThrows(NullPointerException.class, () ->  {
            Moon m = new Moon(null, 0,  2, 1f);
        });
        assertDoesNotThrow(() ->  {
            Moon m = new Moon(EquatorialCoordinates.of(1, 1.5), 0,  2, 0f);
        });
        assertThrows(IllegalArgumentException.class, () ->  {
            Moon m = new Moon(EquatorialCoordinates.of(2, 1), -1,  2, -0.1f);
        });
        assertDoesNotThrow(() ->  {
            Moon m = new Moon(EquatorialCoordinates.of(2, 1), 5678,  2, 1f);
        });
        assertThrows(NullPointerException.class, () ->  {
            Moon m = new Moon(null, 1,  2, 1f);
        });
    }

    @Test
    void gettersWork() {
        float angularSize = 2;
        var eqPos = EquatorialCoordinates.of(0,0);
        float magnitude = 2;
        float phase = 0.5f;
        Moon m = new Moon(eqPos, angularSize, magnitude, phase);
        assertEquals(angularSize, m.angularSize());
        assertEquals(magnitude, m.magnitude());
        assertEquals("Lune", m.name());
        assertEquals(0, m.equatorialPos().ra());
        assertEquals(0, m.equatorialPos().dec());

        angularSize = 0.2f;
        eqPos = EquatorialCoordinates.of(3,1);
        magnitude = 2.5f;
        phase = 0.23f;
        m = new Moon(eqPos, angularSize, magnitude, phase);
        assertEquals(angularSize, m.angularSize());
        assertEquals(magnitude, m.magnitude());
        assertEquals("Lune", m.name());
        assertEquals(3, m.equatorialPos().ra());
        assertEquals(1, m.equatorialPos().dec());
    }

    @Test
    void toStringWorks() {
        Moon m = new Moon(EquatorialCoordinates.of(0,0), 1f, 10f, 0.7f);
        assertEquals("Lune (70.0%)", m.toString());

        Moon moon = new Moon(EquatorialCoordinates.of(Angle.ofDeg(55.8),
                Angle.ofDeg(19.7)), 37.5f, -1, 0.3752f);
        assertEquals("Lune", moon.name());
        assertEquals("Lune (37.5%)", moon.info());
        assertEquals(EquatorialCoordinates.of(Angle.ofDeg(55.8),
                Angle.ofDeg(19.7)).dec(), moon.equatorialPos().dec());
        assertEquals(EquatorialCoordinates.of(Angle.ofDeg(55.8),
                Angle.ofDeg(19.7)).ra(), moon.equatorialPos().ra()); //checking equatorial position
        assertThrows(IllegalArgumentException.class, () -> {new Moon(EquatorialCoordinates.of(Angle.ofDeg(55.8),
                Angle.ofDeg(19.7)), 37.5f, -1, -0.1f); });
    }
}