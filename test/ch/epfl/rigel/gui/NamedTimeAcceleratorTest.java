package ch.epfl.rigel.gui;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */

class NamedTimeAcceleratorTest {

    @Test
    void getName() {
        assertEquals("1x", NamedTimeAccelerator.TIMES_1.getName());
        assertEquals("30x", NamedTimeAccelerator.TIMES_30.getName());
        assertEquals("300x", NamedTimeAccelerator.TIMES_300.getName());
        assertEquals("3000x", NamedTimeAccelerator.TIMES_3000.getName());
        assertEquals("jour", NamedTimeAccelerator.DAY.getName());
        assertEquals("jour sidéral", NamedTimeAccelerator.SIDEREAL_DAY.getName());
    }




}