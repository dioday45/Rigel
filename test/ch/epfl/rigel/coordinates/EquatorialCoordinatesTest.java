package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */

class EquatorialCoordinatesTest {
    EquatorialCoordinates test = EquatorialCoordinates.of(2, Angle.TAU/5);

    @Test
    void of() {
        assertThrows(IllegalArgumentException.class, () -> {
            EquatorialCoordinates.of(25, 20);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            EquatorialCoordinates.of(3, 100);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            EquatorialCoordinates.of(24, 100);
        });

    }

/*    @Test
    void isValidRa() {
        assertEquals(true, EquatorialCoordinates.isValidRa(1.23));
        assertEquals(true, EquatorialCoordinates.isValidRa(0));
        assertEquals(false, EquatorialCoordinates.isValidRa(Angle.TAU));
        assertEquals(false, EquatorialCoordinates.isValidRa(30));

    }

    @Test
    void isValidDec() {
        assertEquals(true, EquatorialCoordinates.isValidDec(0));
        assertEquals(true, EquatorialCoordinates.isValidDec(-Angle.TAU/4));
        assertEquals(true, EquatorialCoordinates.isValidDec(Angle.TAU/4));
        assertEquals(false, EquatorialCoordinates.isValidDec(90));
    }*/

    @Test
    void ra() {
        assertEquals(2, test.ra());
    }

    @Test
    void raDeg() {
        assertEquals(Angle.toDeg(2), test.raDeg());
    }

    @Test
    void raHr() {
        assertEquals(Angle.toHr(2), test.raHr());
    }

    @Test
    void dec() {
        assertEquals(Angle.TAU/5, test.dec());
    }

    @Test
    void decDeg() {
        assertEquals(Angle.toDeg(Angle.TAU/5), test.decDeg());
    }

    /**
     * other group test
     */

    @Test
    void ofWorks() {
        assertThrows(IllegalArgumentException.class, () -> {
            EquatorialCoordinates.of(1, Angle.TAU/2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            EquatorialCoordinates.of(-1, 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            EquatorialCoordinates.of(Angle.TAU, 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            EquatorialCoordinates.of(Angle.TAU/2, Angle.ofDeg(90.0001));
        });

    }


    @Test
    void raDegAndDecDegWorksOnNormalValues() {
        EquatorialCoordinates a = EquatorialCoordinates.of(0, 0);
        assertEquals(0, a.raDeg());
        assertEquals(0, a.decDeg());
        EquatorialCoordinates b = EquatorialCoordinates.of(Angle.ofDeg(12.534), -Angle.ofDeg(50.56));
        assertEquals(12.534, b.raDeg());
        assertEquals(-50.56, b.decDeg());

    }

    @Test
    void raAndDecOnNormalValues() {
        EquatorialCoordinates a = EquatorialCoordinates.of(0, 0);
        assertEquals(0, a.ra());
        assertEquals(0, a.dec());
        EquatorialCoordinates b = EquatorialCoordinates.of(Angle.ofDeg(212.56),-Angle.ofDeg(45.56));
        assertEquals(Angle.ofDeg(212.56), b.ra());
        assertEquals(Angle.ofDeg(-45.56), b.dec());
    }

    @Test
    void raHrOnNormalValues() {
        EquatorialCoordinates a = EquatorialCoordinates.of(0, 0);
        assertEquals(0, a.ra());
        assertEquals(0, a.dec());
        EquatorialCoordinates b = EquatorialCoordinates.of(Angle.ofDeg(212.56),-Angle.ofDeg(45.56));
        assertEquals(Angle.toHr(Angle.ofDeg(212.56)), b.raHr());
    }

    @Test
    void testToString() {
        String a = EquatorialCoordinates.of(Angle.ofHr(1.5),Angle.ofDeg(45)).toString();
        assertEquals("(ra = 1.5000h, dec = 45.0000°)", a);
        String b = EquatorialCoordinates.of(Angle.ofHr(0),0).toString();
        assertEquals("(ra = 0.0000h, dec = 0.0000°)", b);
        String c = EquatorialCoordinates.of(Angle.ofHr(1.5), -Angle.ofDeg(90)).toString();
        assertEquals("(ra = 1.5000h, dec = -90.0000°)", c);
    }
    @Test
    void equalsThrowsUOE() {
        assertThrows(UnsupportedOperationException.class, () -> {
            var interval = EquatorialCoordinates.of(1,1);
            interval.equals(interval);
        });
    }

    @Test
    void hashCodeThrowsUOE() {
        assertThrows(UnsupportedOperationException.class, () -> {
            EquatorialCoordinates.of(0,0).hashCode();
        });
    }


    @Test
    void ofWorksWithRandomValues() {

        double raDeg = 99.9898;
        double ra = Angle.ofDeg(raDeg);
        double raHr = Angle.toHr(ra);
        double decDeg = -90;
        double dec = Angle.ofDeg(decDeg);
        EquatorialCoordinates eqCoordinates = EquatorialCoordinates.of(ra, dec);
        assertEquals(eqCoordinates.ra(), ra, 1e-6);
        assertEquals(eqCoordinates.dec(), dec, 1e-6);
        assertEquals(eqCoordinates.raDeg(), raDeg, 1e-6);
        assertEquals(eqCoordinates.decDeg(), decDeg, 1e-6);
        assertEquals(eqCoordinates.raHr(), raHr, 1e-6);

        raDeg = 350;
        ra = Angle.ofDeg(raDeg);
        raHr = Angle.toHr(ra);
        decDeg = 90;
        dec = Angle.ofDeg(decDeg);
        eqCoordinates = EquatorialCoordinates.of(ra, dec);
        assertEquals(eqCoordinates.ra(), ra, 1e-6);
        assertEquals(eqCoordinates.dec(), dec, 1e-6);
        assertEquals(eqCoordinates.raDeg(), raDeg, 1e-6);
        assertEquals(eqCoordinates.decDeg(), decDeg, 1e-6);
        assertEquals(eqCoordinates.raHr(), raHr, 1e-6);

        raDeg = 180;
        ra = Angle.ofDeg(raDeg);
        raHr = Angle.toHr(ra);
        decDeg = 0;
        dec = Angle.ofDeg(decDeg);
        eqCoordinates = EquatorialCoordinates.of(ra, dec);
        assertEquals(eqCoordinates.ra(), ra, 1e-6);
        assertEquals(eqCoordinates.dec(), dec, 1e-6);
        assertEquals(eqCoordinates.raDeg(), raDeg, 1e-6);
        assertEquals(eqCoordinates.decDeg(), decDeg, 1e-6);
        assertEquals(eqCoordinates.raHr(), raHr, 1e-6);

        raDeg = 359;
        ra = Angle.ofDeg(raDeg);
        raHr = Angle.toHr(ra);
        decDeg = 45.5;
        dec = Angle.ofDeg(decDeg);
        eqCoordinates = EquatorialCoordinates.of(ra, dec);
        assertEquals(eqCoordinates.ra(), ra, 1e-6);
        assertEquals(eqCoordinates.dec(), dec, 1e-6);
        assertEquals(eqCoordinates.raDeg(), raDeg, 1e-6);
        assertEquals(eqCoordinates.decDeg(), decDeg, 1e-6);
        assertEquals(eqCoordinates.raHr(), raHr, 1e-6);
    }

    @Test
    void ofWorksWithWrongValues() {
        assertThrows(IllegalArgumentException.class, () -> {
            double raDeg = -1;
            double decDeg = 5;
            EquatorialCoordinates.of(Angle.ofDeg(raDeg), Angle.ofDeg(decDeg));
        });
        assertDoesNotThrow( () -> {
            double raDeg = 0;
            double decDeg = 90;
            EquatorialCoordinates.of(Angle.ofDeg(raDeg), Angle.ofDeg(decDeg));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            double raDeg = 360;
            double decDeg = 5;
            EquatorialCoordinates.of(Angle.ofDeg(raDeg), Angle.ofDeg(decDeg));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            double raDeg = 0;
            double decDeg = -100;
            EquatorialCoordinates.of(Angle.ofDeg(raDeg), Angle.ofDeg(decDeg));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            double raDeg = 361;
            double decDeg = -90;
            EquatorialCoordinates.of(Angle.ofDeg(raDeg), Angle.ofDeg(decDeg));
        });
    }

    @Test
    void toStringWorks() {
        double raHr = 22;
        double rad = Angle.ofHr(raHr);
        double decDeg = 90;
        EquatorialCoordinates eqCoordinates = EquatorialCoordinates.of(rad, Angle.ofDeg(decDeg));
        assertEquals(eqCoordinates.toString(), "(ra = 22.0000h, dec = 90.0000°)");
        raHr = 0.6666;
        rad = Angle.ofHr(raHr);
        decDeg = -87.1;
        eqCoordinates = EquatorialCoordinates.of(rad, Angle.ofDeg(decDeg));
        assertEquals(eqCoordinates.toString(), "(ra = 0.6666h, dec = -87.1000°)");
        raHr = 23.5444;
        rad = Angle.ofHr(raHr);
        decDeg = -0.1;
        eqCoordinates = EquatorialCoordinates.of(rad, Angle.ofDeg(decDeg));
        assertEquals(eqCoordinates.toString(), "(ra = 23.5444h, dec = -0.1000°)");
    }


}