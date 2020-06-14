package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */

class GeographicCoordinatesTest {

    private static final GeographicCoordinates test = GeographicCoordinates.ofDeg(20, 30);



    @Test
    void ofDeg() {
        assertThrows(IllegalArgumentException.class, () -> {
            GeographicCoordinates.ofDeg(390, 20);
        });


    }

    @Test
    void isValidLonDeg() {
        assertEquals(true, GeographicCoordinates.isValidLonDeg(20));
        assertEquals(true, GeographicCoordinates.isValidLonDeg(-180));
        assertEquals(false, GeographicCoordinates.isValidLonDeg(180));
        assertEquals(false, GeographicCoordinates.isValidLonDeg(200));
    }

    @Test
    void isValidLatDeg() {
        assertEquals(true, GeographicCoordinates.isValidLatDeg(20));
        assertEquals(true, GeographicCoordinates.isValidLatDeg(-90));
        assertEquals(true, GeographicCoordinates.isValidLatDeg(90));
        assertEquals(false, GeographicCoordinates.isValidLatDeg(100));
    }

    @Test
    void lon() {
        assertEquals(Angle.ofDeg(20), test.lon());
    }

    @Test
    void lonDeg() {
        assertEquals(20, test.lonDeg());
    }

    @Test
    void lat() {
        assertEquals(Angle.ofDeg(30), test.lat());
    }

    @Test
    void latDeg() {
        assertEquals(29.999999999999996, test.latDeg());
    }

    @Test
    void testToString() {
        assertEquals("(lon = 20.0000°, lat = 30.0000°)", test.toString());
        GeographicCoordinates test = GeographicCoordinates.ofDeg(45, -90);
        assertEquals("(lon = 45.0000°, lat = -90.0000°)", test.toString());

    }

    /**
     * others group tests
     */

    @Test
    void ofDegOG() {
        assertThrows(IllegalArgumentException.class, () -> {
            GeographicCoordinates.ofDeg(180.0000, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            GeographicCoordinates.ofDeg(-180, 91);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            GeographicCoordinates.ofDeg(-180, -90.001);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            GeographicCoordinates.ofDeg(-180.1, 90);
        });

    }

    @Test
    void isValidLonDegWorksOnCorrectsValues() {
        assertEquals(true, GeographicCoordinates.isValidLonDeg(0));
        assertEquals(true, GeographicCoordinates.isValidLonDeg(-180));
        assertEquals(true, GeographicCoordinates.isValidLonDeg(179.9999));
        assertEquals(true, GeographicCoordinates.isValidLonDeg(34.56353434));
    }
    @Test
    void isValidLongDegWorksOnIncorrectValues() {
        assertEquals(false, GeographicCoordinates.isValidLonDeg(180));
        assertEquals(false, GeographicCoordinates.isValidLonDeg(-180.01));
        assertEquals(false, GeographicCoordinates.isValidLonDeg(360));
    }

    @Test
    void isValidLatDegOnCorrectValues() {
        assertEquals(true, GeographicCoordinates.isValidLatDeg(90));
        assertEquals(true, GeographicCoordinates.isValidLatDeg(-90));
        assertEquals(true, GeographicCoordinates.isValidLatDeg(89.9999));
        assertEquals(true, GeographicCoordinates.isValidLatDeg(0.000));
    }

    @Test
    void isValidLatDegWorksOnIncorrectValues() {
        assertEquals(false, GeographicCoordinates.isValidLatDeg(90.01));
        assertEquals(false, GeographicCoordinates.isValidLatDeg(-90.01));
        assertEquals(false, GeographicCoordinates.isValidLatDeg(360));
    }

    @Test
    void lonDegAndlatDegOnCorrectValues() {
        GeographicCoordinates a = GeographicCoordinates.ofDeg(0,0);
        assertEquals(0, a.lonDeg());
        assertEquals(0, a.latDeg());
        GeographicCoordinates b = GeographicCoordinates.ofDeg(-180,-90);
        assertEquals(-180, b.lonDeg());
        assertEquals(-90, b.latDeg());
        GeographicCoordinates c = GeographicCoordinates.ofDeg(34.987,-67.908);
        assertEquals(34.987, c.lonDeg());
        assertEquals(-67.908, c.latDeg());
    }

    @Test
    void lonAndLatOnCorrectValues() {
        GeographicCoordinates a = GeographicCoordinates.ofDeg(0,0);
        assertEquals(Angle.ofDeg(0), a.lon());
        assertEquals(Angle.ofDeg(0), a.lat());
        GeographicCoordinates b = GeographicCoordinates.ofDeg(-180,-90);
        assertEquals(Angle.ofDeg(-180), b.lon());
        assertEquals(Angle.ofDeg(-90), b.lat());
        GeographicCoordinates c = GeographicCoordinates.ofDeg(34.987,-67.908);
        assertEquals(Angle.ofDeg(34.987), c.lon());
        assertEquals(Angle.ofDeg(-67.908), c.lat());
    }

    @Test
    void testToStringOG() {
        String a = GeographicCoordinates.ofDeg(34.9087,45.5678).toString();
        assertEquals("(lon = 34.9087°, lat = 45.5678°)", a);
        String b = GeographicCoordinates.ofDeg(0,-89.999).toString();
        assertEquals("(lon = 0.0000°, lat = -89.9990°)", b);
    }
    @Test
    void equalsThrowsUOE() {
        assertThrows(UnsupportedOperationException.class, () -> {
            var interval = GeographicCoordinates.ofDeg(1,1);
            interval.equals(interval);
        });
    }

    @Test
    void hashCodeThrowsUOE() {
        assertThrows(UnsupportedOperationException.class, () -> {
            GeographicCoordinates.ofDeg(0,0).hashCode();
        });
    }

    @Test
    void ofDegWorksWithRandomValues() {
        double lonDeg = 99.9898;
        double lon = Angle.ofDeg(lonDeg);
        double latDeg = -90;
        double lat = Angle.ofDeg(latDeg);
        GeographicCoordinates geoCoordinates = GeographicCoordinates.ofDeg(lonDeg, latDeg);
        assertEquals(geoCoordinates.lon(), lon, 1e-6);
        assertEquals(geoCoordinates.lat(), lat, 1e-6);
        assertEquals(geoCoordinates.latDeg(), latDeg, 1e-6);
        assertEquals(geoCoordinates.lonDeg(), lonDeg, 1e-6);

        lonDeg = -0.7878;
        lon = Angle.ofDeg(lonDeg);
        latDeg = 90;
        lat = Angle.ofDeg(latDeg);
        geoCoordinates = GeographicCoordinates.ofDeg(lonDeg, latDeg);
        assertEquals(geoCoordinates.lon(), lon, 1e-6);
        assertEquals(geoCoordinates.lat(), lat, 1e-6);
        assertEquals(geoCoordinates.latDeg(), latDeg, 1e-6);
        assertEquals(geoCoordinates.lonDeg(), lonDeg, 1e-6);

        lonDeg = -180;
        lon = Angle.ofDeg(lonDeg);
        latDeg = 0;
        lat = Angle.ofDeg(latDeg);
        geoCoordinates = GeographicCoordinates.ofDeg(lonDeg, latDeg);
        assertEquals(geoCoordinates.lon(), lon, 1e-6);
        assertEquals(geoCoordinates.lat(), lat, 1e-6);
        assertEquals(geoCoordinates.latDeg(), latDeg, 1e-6);
        assertEquals(geoCoordinates.lonDeg(), lonDeg, 1e-6);

        lonDeg = 179.45;
        lon = Angle.ofDeg(lonDeg);
        latDeg = 45.5;
        lat = Angle.ofDeg(latDeg);
        geoCoordinates = GeographicCoordinates.ofDeg(lonDeg, latDeg);
        assertEquals(geoCoordinates.lon(), lon, 1e-6);
        assertEquals(geoCoordinates.lat(), lat, 1e-6);
        assertEquals(geoCoordinates.latDeg(), latDeg, 1e-6);
        assertEquals(geoCoordinates.lonDeg(), lonDeg, 1e-6);
    }

    @Test
    void ofDegWorksWithWrongValues() {
        assertThrows(IllegalArgumentException.class, () -> {
            double lonDeg = 180;
            double latDeg = 5;
            GeographicCoordinates.ofDeg(lonDeg, latDeg);
        });
        assertDoesNotThrow( () -> {
            double lonDeg = -180;
            double latDeg = 5;
            GeographicCoordinates.ofDeg(lonDeg, latDeg);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            double lonDeg = 180;
            double latDeg = 5;
            GeographicCoordinates.ofDeg(lonDeg, latDeg);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            double lonDeg = 0;
            double latDeg = -100;
            GeographicCoordinates.ofDeg(lonDeg, latDeg);
        });
    }

    @Test
    void isValidLonDegWorks() {
        double lonDeg = 179.8888888;
        assertTrue(GeographicCoordinates.isValidLonDeg(lonDeg));
        lonDeg = 180;
        assertFalse(GeographicCoordinates.isValidLonDeg(lonDeg));
        lonDeg = 190;
        assertFalse(GeographicCoordinates.isValidLonDeg(lonDeg));
        lonDeg = -182;
        assertFalse(GeographicCoordinates.isValidLonDeg(lonDeg));
        lonDeg = -180;
        assertTrue(GeographicCoordinates.isValidLonDeg(lonDeg));
    }

    @Test
    void isValidLatDegWorks() {
        double latDeg = 179.8888888;
        assertFalse(GeographicCoordinates.isValidLatDeg(latDeg));
        latDeg = -90;
        assertTrue(GeographicCoordinates.isValidLatDeg(latDeg));
        latDeg = 90;
        assertTrue(GeographicCoordinates.isValidLatDeg(latDeg));
        latDeg = 90.5;
        assertFalse(GeographicCoordinates.isValidLatDeg(latDeg));
        latDeg = -90.1;
        assertFalse(GeographicCoordinates.isValidLatDeg(latDeg));
        latDeg = 0;
        assertTrue(GeographicCoordinates.isValidLatDeg(latDeg));
    }

    @Test
    void toStringWorks() {
        double lonDeg = 179.8888888;
        double latDeg = 90;
        GeographicCoordinates geoCoordinates = GeographicCoordinates.ofDeg(lonDeg, latDeg);
        assertEquals(geoCoordinates.toString(), "(lon = 179.8889°, lat = 90.0000°)");
        lonDeg = -89.1;
        latDeg = -87.1;
        geoCoordinates = GeographicCoordinates.ofDeg(lonDeg, latDeg);
        assertEquals(geoCoordinates.toString(), "(lon = -89.1000°, lat = -87.1000°)");
    }

}