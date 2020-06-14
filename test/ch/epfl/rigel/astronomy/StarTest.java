package ch.epfl.rigel.astronomy;


import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * StarTest
 *
 * @author Robin Jaccard (310682)
 * @author Carl Schütz (289080)
 */
public class StarTest {

    @Test
    void IllegalArgumentIsThrowOnStarConstrucor () {
        assertThrows(IllegalArgumentException.class, ()-> new Star(-1,"bonj",
                EquatorialCoordinates.of(1,1),1,1));

        assertThrows(IllegalArgumentException.class, ()-> new Star(0,"bonj",
                EquatorialCoordinates.of(1,1),1,(float)5.6));

        assertThrows(IllegalArgumentException.class, ()-> new Star(0,"bonj",
                EquatorialCoordinates.of(1,1),1,-1));

        assertThrows(IllegalArgumentException.class, ()-> new Star(-1,"bonj",
                EquatorialCoordinates.of(1,1),1,20));

    }

    @Test
    void hipparcosIdWorks() {
        Star test1 = new Star(0,"bonj",
                EquatorialCoordinates.of(1,1),1,1);
        assertEquals(0, test1.hipparcosId());

        Star test2 = new Star(90,"bonj",
                EquatorialCoordinates.of(1,1),1,1);
        assertEquals(90, test2.hipparcosId());
    }

    @Test
    void colorTemperatureWorks() {
        Star test1 = new Star(0,"bonj",
                EquatorialCoordinates.of(1,1),1,1);
        assertEquals(4742, test1.colorTemperature());

        Star test2 = new Star(0,"bonj",
                EquatorialCoordinates.of(1,1),1,5);
        assertEquals(1611, test2.colorTemperature());

        Star test3 = new Star(0,"bonj",
                EquatorialCoordinates.of(1,1),1,0);
        assertEquals(10125, test3.colorTemperature());
    }

    /*@Test
    void colorTempPrecisionWorks() {
        Star test1 = new Star(0,"bonj",
                EquatorialCoordinates.of(1,1),1,1);
        assertEquals(4742.738178, test1.colorTemperatureDouble(), 1e-6);

        Star test2 = new Star(0,"bonj",
                EquatorialCoordinates.of(1,1),1,5);
        assertEquals(1611.384784, test2.colorTemperatureDouble(), 1e-6);
    }
    */



    @Test
    @SuppressWarnings("unused")
    void constructorThrowsUsualExceptions() {
        assertThrows(NullPointerException.class, () -> {
            Star s = new Star(2, null, EquatorialCoordinates.of(0, 0), 1f, 5f);
        });
        assertThrows(NullPointerException.class, () -> {
            Star s = new Star(2, "Rigel", null, 1f, 5f);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Star s = new Star(-1, "Rigel", EquatorialCoordinates.of(0, 0), 1f, 5f);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Star s = new Star(0, "Rigel", EquatorialCoordinates.of(0, 0), 10000f, 5.6f);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Star s = new Star(0, "Rigel", EquatorialCoordinates.of(0, 0), 10000f, -0.6f);
        });
        assertDoesNotThrow(() -> {
            Star s = new Star(0, "Rigel", EquatorialCoordinates.of(0, 0), -50f, -0.5f);
        });
        assertDoesNotThrow(() -> {
            Star s = new Star(1555555, "Rigel", EquatorialCoordinates.of(0, 0), -50f, 5.5f);
        });
    }

    @Test
    void constructorAndGettersWork() {
        int hipparCosId = 5;
        String name = "Rigel";
        var eqPos = EquatorialCoordinates.of(0, 0);
        float magnitude = -26.7f;
        float colorIndex = 5.4f;
        Star s = new Star(hipparCosId, name,eqPos, magnitude, colorIndex);
        assertEquals(hipparCosId, s.hipparcosId());
        assertEquals(0, s.angularSize());
        assertEquals(magnitude, s.magnitude());
        assertEquals(name, s.name());
        assertEquals(0, s.equatorialPos().ra());
        assertEquals(0, s.equatorialPos().dec());

        hipparCosId = 8;
        name = "Rigel555";
        eqPos = EquatorialCoordinates.of(2.5, 0.55);
        magnitude = 8787f;
        colorIndex = 0.8f;
        s = new Star(hipparCosId, name,eqPos, magnitude, colorIndex);
        assertEquals(hipparCosId, s.hipparcosId());
        assertEquals(0, s.angularSize());
        assertEquals(magnitude, s.magnitude());
        assertEquals(name, s.name());
        assertEquals(2.5, s.equatorialPos().ra());
        assertEquals(0.55, s.equatorialPos().dec());
    }

    @Test
    void infoWorks() {
        Star s = new Star(1555555, "Rigel", EquatorialCoordinates.of(0, 0), -50f, 5.5f);
        assertEquals("Rigel", s.info());
    }

    @Test
    void toStringWorks() {
        Star s = new Star(1555555, "Rigel", EquatorialCoordinates.of(0, 0), -50f, 5.5f);
        assertEquals("Rigel", s.info());
    }

    @Test
    void colorTemperatureWorksA() {
        //MY VALUES
        Star s = new Star(1555555, "Rigel", EquatorialCoordinates.of(0, 0), -50f, 5.5f);
        assertEquals(1490, s.colorTemperature());
        s = new Star(1555555, "Rigel", EquatorialCoordinates.of(0, 0), -50f, -0.5f);
        assertEquals(32459, s.colorTemperature());
        s = new Star(1555555, "Rigel", EquatorialCoordinates.of(0, 0), -50f, 2.5f);
        assertEquals(2725, s.colorTemperature());
        //RIGEL
        s = new Star(1555555, "Rigel", EquatorialCoordinates.of(0, 0), -50f, -0.03f);
        assertEquals(10515, s.colorTemperature());
        //BEGELTEUSE
        s = new Star(1555555, "Begelteuse", EquatorialCoordinates.of(0, 0), -50f, 1.5f);
        assertEquals(3793, s.colorTemperature());
    }


}
