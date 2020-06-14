package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import ch.epfl.test.TestRandomizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */

class SunTest {
    @Test
    void myConstruct(){
        assertThrows(NullPointerException.class, () -> {
            Sun test1 = new Sun(null, EquatorialCoordinates.of(2, 0), 20, 20);
        });
    }



    @Test
    void meanAnomaly() {
        Sun test1 = new Sun(EclipticCoordinates.of(2, 0), EquatorialCoordinates.of(2, 0), 20, 20);
        assertEquals(20, test1.meanAnomaly());
    }

    //others
    @Test
    void infoWorks() {
        Sun s = new Sun(EclipticCoordinates.of(0, 0), EquatorialCoordinates.of(0,0), 1f, 10f);
        assertEquals("Soleil", s.info());
    }

    @Test
    void toStringWorks() {
        Sun s = new Sun(EclipticCoordinates.of(0, 0), EquatorialCoordinates.of(0,0), 1f, 10f);
        assertEquals("Soleil", s.toString());
    }

    @Test
    @SuppressWarnings("unused")
    void constructorThrowsIAEforNullEclipticCoords() {
        assertThrows(NullPointerException.class, () ->  {
            Sun s = new Sun(null , EquatorialCoordinates.of(0,0), 1f, 10f);
        });
        assertDoesNotThrow(() ->  {
            Sun s = new Sun(EclipticCoordinates.of(0, 0) , EquatorialCoordinates.of(0,0), 1f, 10f);
        });
    }

    @Test
    @SuppressWarnings("unused")
    void constructorThrowsUsualExceptions() {
        assertThrows(NullPointerException.class, () ->  {
            Sun s = new Sun(EclipticCoordinates.of(0, 0) , null, 1f, 10f);
        });
        assertDoesNotThrow(() ->  {
            Sun s = new Sun(EclipticCoordinates.of(0, 0) , EquatorialCoordinates.of(0,0), 1f, -0.5f);
        });
        assertThrows(IllegalArgumentException.class, () ->  {
            Sun s = new Sun(EclipticCoordinates.of(0, 0) , EquatorialCoordinates.of(0,0), -1f, 10f);
        });
    }

    @Test
    void gettersWork() {
        float angularSize = 2;
        float meanAnomaly = 5f;
        var eqPos = EquatorialCoordinates.of(0,0);
        var eclPos = EclipticCoordinates.of(0, 0);
        float magnitude = -26.7f;
        Sun s = new Sun(eclPos , eqPos, angularSize, meanAnomaly);
        assertEquals(angularSize, s.angularSize());
        assertEquals(magnitude, s.magnitude());
        assertEquals("Soleil", s.name());
        assertEquals(0, s.equatorialPos().ra());
        assertEquals(0, s.equatorialPos().dec());
        assertEquals(0, s.eclipticPos().lon());
        assertEquals(0, s.eclipticPos().lat());

        angularSize = 0.2f;
        meanAnomaly = 5f;
        eqPos = EquatorialCoordinates.of(3,1);
        eclPos = EclipticCoordinates.of(2, 1);
        magnitude = -26.7f;
        s = new Sun(eclPos , eqPos, angularSize, meanAnomaly);
        assertEquals(angularSize, s.angularSize());
        assertEquals(magnitude, s.magnitude());
        assertEquals("Soleil", s.name());
        assertEquals(3, s.equatorialPos().ra());
        assertEquals(1, s.equatorialPos().dec());
        assertEquals(2, s.eclipticPos().lon());
        assertEquals(1, s.eclipticPos().lat());

        Sun sun = new Sun(EclipticCoordinates.of(Angle.ofDeg(53), Angle.ofDeg(38)),
                EquatorialCoordinates.of(Angle.ofDeg(55.8),Angle.ofDeg(24)),
                0.4f, 5.f);
        assertEquals("Soleil", sun.info());
        assertEquals(EquatorialCoordinates.of(Angle.ofDeg(55.8),
                Angle.ofDeg(24)).dec(), sun.equatorialPos().dec());
        assertEquals(EquatorialCoordinates.of(Angle.ofDeg(55.8),
                Angle.ofDeg(19.7)).ra(), sun.equatorialPos().ra()); //checking equatorial position
        assertEquals(5.f, sun.meanAnomaly());
        assertEquals(-26.7f, sun.magnitude());

        //test pour eclipticPos throws un null
        assertThrows(NullPointerException.class, () -> { new Sun(null,
                EquatorialCoordinates.of(Angle.ofDeg(55.8),Angle.ofDeg(24)),
                0.4f, 5.f); });

    }

    @Test
    void sunMeanAnomalyIsCorrect() {
        var rng = TestRandomizer.newRandom();
        for (int i = 0; i < TestRandomizer.RANDOM_ITERATIONS; i++) {
            var meanAnomaly = (float) rng.nextDouble(0, 2d * Math.PI);
            var ecl = EclipticCoordinates.of(0, 0);
            var equ = EquatorialCoordinates.of(0, 0);
            var s = new Sun(ecl, equ, 0f, meanAnomaly);
            assertEquals(meanAnomaly, s.meanAnomaly());
        }
    }

}