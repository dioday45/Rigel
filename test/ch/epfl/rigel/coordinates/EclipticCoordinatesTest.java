package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */

class EclipticCoordinatesTest {

    /**
     * Others group test
     */
    
    @Test
    public void IllegalArgumentExceptionWorksOnof() {
        assertThrows(IllegalArgumentException.class, () -> EclipticCoordinates.of(Angle.TAU,0));
        assertThrows(IllegalArgumentException.class, () -> EclipticCoordinates.of(0,Angle.TAU));
        assertThrows(IllegalArgumentException.class, () -> EclipticCoordinates.of(0,600));
        assertThrows(IllegalArgumentException.class, () -> EclipticCoordinates.of(-34,-3));
    }

    @Test
    public void EclipticCoordinatesWorksOnCorrectValues() {
        EclipticCoordinates eclipticCoordinatesTest = EclipticCoordinates.of(Angle.TAU/2,Angle.ofDeg(90));
        assertEquals(Angle.TAU/2,eclipticCoordinatesTest.lon());
        assertEquals(90,eclipticCoordinatesTest.latDeg());
        assertEquals(180,eclipticCoordinatesTest.lonDeg());
        assertEquals(Angle.ofDeg(90), eclipticCoordinatesTest.lat());
    }

    @Test
    public void toStringWorks() {
        String test1 = EclipticCoordinates.of(Math.PI,Math.PI/4).toString();
        assertEquals("(λ=180.0000°, β=45.0000°)", test1);
        String test2 = EclipticCoordinates.of(Angle.ofDeg(92.3049), Angle.ofDeg(32.0932)).toString();
        assertEquals("(λ=92.3049°, β=32.0932°)", test2);
    }

    @Test
    void ofWorksWithRandomValues() {
        double lonDeg = 99.9898;
        double lon = Angle.ofDeg(lonDeg);
        double latDeg = -90;
        double lat = Angle.ofDeg(latDeg);
        EclipticCoordinates eclCoordinates = EclipticCoordinates.of(lon, lat);
        assertEquals(eclCoordinates.lon(), lon, 1e-6);
        assertEquals(eclCoordinates.lat(), lat, 1e-6);
        assertEquals(eclCoordinates.latDeg(), latDeg, 1e-6);
        assertEquals(eclCoordinates.lonDeg(), lonDeg, 1e-6);

        lonDeg = 354.55;
        lon = Angle.ofDeg(lonDeg);
        latDeg = 90;
        lat = Angle.ofDeg(latDeg);
        eclCoordinates = EclipticCoordinates.of(lon, lat);
        assertEquals(eclCoordinates.lon(), lon, 1e-6);
        assertEquals(eclCoordinates.lat(), lat, 1e-6);
        assertEquals(eclCoordinates.latDeg(), latDeg, 1e-6);
        assertEquals(eclCoordinates.lonDeg(), lonDeg, 1e-6);

        lonDeg = 0;
        lon = Angle.ofDeg(lonDeg);
        latDeg = 0;
        lat = Angle.ofDeg(latDeg);
        eclCoordinates = EclipticCoordinates.of(lon, lat);
        assertEquals(eclCoordinates.lon(), lon, 1e-6);
        assertEquals(eclCoordinates.lat(), lat, 1e-6);
        assertEquals(eclCoordinates.latDeg(), latDeg, 1e-6);
        assertEquals(eclCoordinates.lonDeg(), lonDeg, 1e-6);

        lonDeg = 179.99;
        lon = Angle.ofDeg(lonDeg);
        latDeg = 45.5;
        lat = Angle.ofDeg(latDeg);
        eclCoordinates = EclipticCoordinates.of(lon, lat);
        assertEquals(eclCoordinates.lon(), lon, 1e-6);
        assertEquals(eclCoordinates.lat(), lat, 1e-6);
        assertEquals(eclCoordinates.latDeg(), latDeg, 1e-6);
        assertEquals(eclCoordinates.lonDeg(), lonDeg, 1e-6);
    }

    @Test
    void ofWorksWithWrongValues() {
        assertThrows(IllegalArgumentException.class, () -> {
            double lonDeg = 360;
            double latDeg = 5;
            EclipticCoordinates.of(Angle.ofDeg(lonDeg), Angle.ofDeg(latDeg));
        });
        assertDoesNotThrow( () -> {
            double lonDeg = 0;
            double latDeg = -90;
            EclipticCoordinates.of(Angle.ofDeg(lonDeg), Angle.ofDeg(latDeg));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            double lonDeg = 360.1;
            double latDeg = 5;
            EclipticCoordinates.of(Angle.ofDeg(lonDeg), Angle.ofDeg(latDeg));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            double lonDeg = 0;
            double latDeg = -100;
            EclipticCoordinates.of(Angle.ofDeg(lonDeg), Angle.ofDeg(latDeg));
        });
    }

    @Test
    void toStringWorksB() {
        double lonDeg = 179.8888888;
        double latDeg = 90;
        EclipticCoordinates eclCoordinates = EclipticCoordinates.of(Angle.ofDeg(lonDeg), Angle.ofDeg(latDeg));
        assertEquals(eclCoordinates.toString(), "(λ=179.8889°, β=90.0000°)");
        lonDeg = 356.1;
        latDeg = -87.1;
        eclCoordinates = EclipticCoordinates.of(Angle.ofDeg(lonDeg), Angle.ofDeg(latDeg));
        assertEquals(eclCoordinates.toString(), "(λ=356.1000°, β=-87.1000°)");
    }

}