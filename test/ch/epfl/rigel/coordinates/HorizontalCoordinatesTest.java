package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */



class HorizontalCoordinatesTest {

    @Test
    void errorHorizontalCoordinates(){
        assertThrows(IllegalArgumentException.class, () -> {
            HorizontalCoordinates.of(20, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            HorizontalCoordinates.of(2, 25);
        });
    }

    @Test
    void limitHorizontalCoordinates(){
        assertThrows(IllegalArgumentException.class, () -> {
            HorizontalCoordinates.of(Angle.TAU, 0);
        });
        assertEquals("(az=0.0000°, alt=0.0000°)", HorizontalCoordinates.of(0,0).toString());
        assertEquals("(az=0.0000°, alt=-90.0000°)", HorizontalCoordinates.of(0,-(Angle.TAU/4)).toString());
        assertEquals("(az=0.0000°, alt=90.0000°)", HorizontalCoordinates.of(0,(Angle.TAU/4)).toString());
    }

    @Test
    void singularHorizontalCoordinates(){
        //assertEquals((Angle.TAU/2)%.4f, HorizontalCoordinates.of(Angle.TAU/2, 0).az() );
    }

    @Test
    void rightAzOctantName() {
        assertEquals( "N", HorizontalCoordinates.ofDeg(0, 0).azOctantName("N", "E", "S", "O"));
    }

    @Test
    void arbitralAngularDistanceTo() {
        double delta1 = Angle.ofDeg(6.5682);
        double delta2 = Angle.ofDeg(8.5476);
        double phy1 = Angle.ofDeg(46.5183);
        double phy2 = Angle.ofDeg(47.3763);
        HorizontalCoordinates ceci = HorizontalCoordinates.of(delta1, phy1);
        HorizontalCoordinates cela = HorizontalCoordinates.of(delta2, phy2);
        assertEquals(0.0279, ceci.angularDistanceTo(cela), .4f);
    }
    @Test
    void rightToString(){
        assertEquals("(az=350.0000°, alt=7.2000°)", HorizontalCoordinates.ofDeg(350, 7.2).toString());
    }

    /**
     * others group test
     */





    @Test
    public void IllegalArgumentExceptionWorksOnofDeg() {
        assertThrows(IllegalArgumentException.class, () -> HorizontalCoordinates.ofDeg(360,0));
        assertThrows(IllegalArgumentException.class, () -> HorizontalCoordinates.ofDeg(0,360));
        assertThrows(IllegalArgumentException.class, () -> HorizontalCoordinates.ofDeg(40,400));
        assertThrows(IllegalArgumentException.class, () -> HorizontalCoordinates.ofDeg(-32,350));
    }

    @Test
    public void ozOctantNameWorks() {
        HorizontalCoordinates horizontalCoordinatesTest = HorizontalCoordinates.ofDeg(355,0);
        assertEquals("N",
                HorizontalCoordinates.ofDeg(22.49,0).azOctantName("N","E","S","W"));
        assertEquals("NE",
                HorizontalCoordinates.ofDeg(22.5,0).azOctantName("N","E","S","W"));
        assertEquals("E",
                HorizontalCoordinates.ofDeg(67.5,0).azOctantName("N","E","S","W"));
        assertEquals("E",
                HorizontalCoordinates.ofDeg(90,0).azOctantName("N","E","S","W"));
        assertEquals("S",
                HorizontalCoordinates.ofDeg(157.5,0).azOctantName("N","E","S","W"));
        assertEquals("SW",
                HorizontalCoordinates.ofDeg(212.5,0).azOctantName("N","E","S","W"));
        assertEquals("W",
                HorizontalCoordinates.ofDeg(280,0).azOctantName("N","E","S","W"));
        assertEquals("NW",
                HorizontalCoordinates.ofDeg(315,0).azOctantName("N","E","S","W"));
        assertEquals("N",
                HorizontalCoordinates.ofDeg(0,0).azOctantName("N","E","S","W"));
    }

    @Test
    public void angularDistanceToWorks() {
        assertEquals(1, HorizontalCoordinates.of(0,1)
                .angularDistanceTo(HorizontalCoordinates.of(0,0)));
        assertEquals(0, HorizontalCoordinates.of(0,0)
                .angularDistanceTo(HorizontalCoordinates.of(0,0)));
        assertEquals(0, HorizontalCoordinates.ofDeg(45,45)
                .angularDistanceTo(HorizontalCoordinates.ofDeg(45,45)));
        assertEquals(Math.PI, HorizontalCoordinates.of(Math.PI,0)
                .angularDistanceTo(HorizontalCoordinates.of(0,0)));
        assertEquals(0, HorizontalCoordinates.of(Math.PI/2,Math.PI/2)
                .angularDistanceTo(HorizontalCoordinates.of(Math.PI/2,Math.PI/2)));
        assertEquals(0.0279, HorizontalCoordinates.ofDeg(6.5682,46.5183)
                .angularDistanceTo(HorizontalCoordinates.ofDeg(8.5476,47.3763)), 1e-4);
    }


    @Test
    public void toStringWorks() {
        String test1 = HorizontalCoordinates.ofDeg(350,7.2).toString();
        assertEquals("(az=350.0000°, alt=7.2000°)"
                ,test1);

        String test2 = HorizontalCoordinates.ofDeg(97.9382,23.3233).toString();
        assertEquals("(az=97.9382°, alt=23.3233°)"
                ,test2);
    }





    @Test
    void ofDegWorksWithRandomValues() {
        double azDeg = 99.9898;
        double az = Angle.ofDeg(azDeg);
        double altDeg = -90;
        double alt = Angle.ofDeg(altDeg);
        HorizontalCoordinates horCoordinates = HorizontalCoordinates.ofDeg(azDeg, altDeg);
        assertEquals(horCoordinates.az(), az, 1e-6);
        assertEquals(horCoordinates.alt(), alt, 1e-6);
        assertEquals(horCoordinates.azDeg(), azDeg, 1e-6);
        assertEquals(horCoordinates.altDeg(), altDeg, 1e-6);

        azDeg = 350;
        az = Angle.ofDeg(azDeg);
        altDeg = 90;
        alt = Angle.ofDeg(altDeg);
        horCoordinates = HorizontalCoordinates.ofDeg(azDeg, altDeg);
        assertEquals(horCoordinates.az(), az, 1e-6);
        assertEquals(horCoordinates.alt(), alt, 1e-6);
        assertEquals(horCoordinates.azDeg(), azDeg, 1e-6);
        assertEquals(horCoordinates.altDeg(), altDeg, 1e-6);

        azDeg = 180;
        az = Angle.ofDeg(azDeg);
        altDeg = 0;
        alt = Angle.ofDeg(altDeg);
        horCoordinates = HorizontalCoordinates.ofDeg(azDeg, altDeg);
        assertEquals(horCoordinates.az(), az, 1e-6);
        assertEquals(horCoordinates.alt(), alt, 1e-6);
        assertEquals(horCoordinates.azDeg(), azDeg, 1e-6);
        assertEquals(horCoordinates.altDeg(), altDeg, 1e-6);

        azDeg = 359;
        az = Angle.ofDeg(azDeg);
        altDeg = 45.5;
        alt = Angle.ofDeg(altDeg);
        horCoordinates = HorizontalCoordinates.ofDeg(azDeg, altDeg);
        assertEquals(horCoordinates.az(), az, 1e-6);
        assertEquals(horCoordinates.alt(), alt, 1e-6);
        assertEquals(horCoordinates.azDeg(), azDeg, 1e-6);
        assertEquals(horCoordinates.altDeg(), altDeg, 1e-6);
    }

    @Test
    void ofDegWorksWithWrongValues() {
        assertThrows(IllegalArgumentException.class, () -> {
            double azDeg = -1;
            double altDeg = 5;
            HorizontalCoordinates.ofDeg(azDeg, altDeg);
        });
        assertDoesNotThrow( () -> {
            double azDeg = 0;
            double altDeg = 90;
            HorizontalCoordinates.ofDeg(azDeg, altDeg);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            double azDeg = 360;
            double altDeg = 5;
            HorizontalCoordinates.ofDeg(azDeg, altDeg);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            double azDeg = 0;
            double altDeg = -100;
            HorizontalCoordinates.ofDeg(azDeg, altDeg);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            double azDeg = 361;
            double altDeg = -90;
            HorizontalCoordinates.ofDeg(azDeg, altDeg);
        });
    }

    @Test
    void ofWorksWithRandomValues() {

        double azDeg = 99.9898;
        double az = Angle.ofDeg(azDeg);
        double altDeg = -90;
        double alt = Angle.ofDeg(altDeg);
        HorizontalCoordinates horCoordinates = HorizontalCoordinates.of(az, alt);
        assertEquals(horCoordinates.az(), az, 1e-6);
        assertEquals(horCoordinates.alt(), alt, 1e-6);
        assertEquals(horCoordinates.azDeg(), azDeg, 1e-6);
        assertEquals(horCoordinates.altDeg(), altDeg, 1e-6);

        azDeg = 350;
        az = Angle.ofDeg(azDeg);
        altDeg = 90;
        alt = Angle.ofDeg(altDeg);
        horCoordinates = HorizontalCoordinates.of(az, alt);
        assertEquals(horCoordinates.az(), az, 1e-6);
        assertEquals(horCoordinates.alt(), alt, 1e-6);
        assertEquals(horCoordinates.azDeg(), azDeg, 1e-6);
        assertEquals(horCoordinates.altDeg(), altDeg, 1e-6);

        azDeg = 180;
        az = Angle.ofDeg(azDeg);
        altDeg = 0;
        alt = Angle.ofDeg(altDeg);
        horCoordinates = HorizontalCoordinates.of(az, alt);
        assertEquals(horCoordinates.az(), az, 1e-6);
        assertEquals(horCoordinates.alt(), alt, 1e-6);
        assertEquals(horCoordinates.azDeg(), azDeg, 1e-6);
        assertEquals(horCoordinates.altDeg(), altDeg, 1e-6);

        azDeg = 359;
        az = Angle.ofDeg(azDeg);
        altDeg = 45.5;
        alt = Angle.ofDeg(altDeg);
        horCoordinates = HorizontalCoordinates.of(az, alt);
        assertEquals(horCoordinates.az(), az, 1e-6);
        assertEquals(horCoordinates.alt(), alt, 1e-6);
        assertEquals(horCoordinates.azDeg(), azDeg, 1e-6);
        assertEquals(horCoordinates.altDeg(), altDeg, 1e-6);
    }

    @Test
    void ofWorksWithWrongValues() {
        assertThrows(IllegalArgumentException.class, () -> {
            double azDeg = -1;
            double altDeg = 5;
            HorizontalCoordinates.of(Angle.ofDeg(azDeg), Angle.ofDeg(altDeg));
        });
        assertDoesNotThrow( () -> {
            double azDeg = 0;
            double altDeg = 90;
            HorizontalCoordinates.of(Angle.ofDeg(azDeg), Angle.ofDeg(altDeg));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            double azDeg = 360;
            double altDeg = 5;
            HorizontalCoordinates.of(Angle.ofDeg(azDeg), Angle.ofDeg(altDeg));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            double azDeg = 0;
            double altDeg = -100;
            HorizontalCoordinates.of(Angle.ofDeg(azDeg), Angle.ofDeg(altDeg));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            double azDeg = 361;
            double altDeg = -90;
            HorizontalCoordinates.of(Angle.ofDeg(azDeg), Angle.ofDeg(altDeg));
        });
    }

    @Test
    void toStringWorksB() {
        double azDeg = 179.8888888;
        double altDeg = 90;
        HorizontalCoordinates horCoordinates = HorizontalCoordinates.ofDeg(azDeg, altDeg);
        assertEquals(horCoordinates.toString(), "(az=179.8889°, alt=90.0000°)");
        azDeg = 0;
        altDeg = -87.1;
        horCoordinates = HorizontalCoordinates.ofDeg(azDeg, altDeg);
        assertEquals(horCoordinates.toString(), "(az=0.0000°, alt=-87.1000°)");
        azDeg = 359.2;
        altDeg = -0.1;
        horCoordinates = HorizontalCoordinates.ofDeg(azDeg, altDeg);
        assertEquals(horCoordinates.toString(), "(az=359.2000°, alt=-0.1000°)");
    }

    @Test
    void angularDistanceToWorksB() {
        double azDeg1 = 6.5682;
        double altDeg1 = 46.5183;
        double azDeg2 = 8.5476;
        double altDeg2 = 47.3763;
        HorizontalCoordinates horCoordinates1 = HorizontalCoordinates.ofDeg(azDeg1, altDeg1);
        HorizontalCoordinates horCoordinates2 = HorizontalCoordinates.ofDeg(azDeg2, altDeg2);
        assertEquals(horCoordinates1.angularDistanceTo(horCoordinates2), horCoordinates2.angularDistanceTo(horCoordinates1));
        assertEquals(horCoordinates1.angularDistanceTo(horCoordinates1), 0);
        assertEquals(horCoordinates2.angularDistanceTo(horCoordinates2), 0);
        assertEquals(horCoordinates1.angularDistanceTo(horCoordinates2), 0.0279, 1e-4);
    }

    @Test
    void azOctantNameWorks() {
        double azDeg = 0;
        double altDeg = -90;
        HorizontalCoordinates horCoordinates = HorizontalCoordinates.ofDeg(azDeg, altDeg);
        assertEquals(horCoordinates.azOctantName("N", "E", "S", "W"), "N");
        azDeg = 45;
        altDeg = 0;
        horCoordinates = HorizontalCoordinates.ofDeg(azDeg, altDeg);
        assertEquals(horCoordinates.azOctantName("N", "E", "S", "W"), "NE");
        azDeg = 90;
        altDeg = 84;
        horCoordinates = HorizontalCoordinates.ofDeg(azDeg, altDeg);
        assertEquals(horCoordinates.azOctantName("N", "E", "S", "W"), "E");
        azDeg = 135;
        altDeg = -55;
        horCoordinates = HorizontalCoordinates.ofDeg(azDeg, altDeg);
        assertEquals(horCoordinates.azOctantName("N", "E", "S", "W"), "SE");
        azDeg = 180;
        altDeg = 0;
        horCoordinates = HorizontalCoordinates.ofDeg(azDeg, altDeg);
        assertEquals(horCoordinates.azOctantName("N", "E", "S", "W"), "S");
        azDeg = 225;
        altDeg = 0;
        horCoordinates = HorizontalCoordinates.ofDeg(azDeg, altDeg);
        assertEquals(horCoordinates.azOctantName("N", "E", "S", "W"), "SW");
        azDeg = 270;
        altDeg = 55.44454;
        horCoordinates = HorizontalCoordinates.ofDeg(azDeg, altDeg);
        assertEquals(horCoordinates.azOctantName("N", "E", "S", "W"), "W");
        azDeg = 315;
        altDeg = 0;
        horCoordinates = HorizontalCoordinates.ofDeg(azDeg, altDeg);
        assertEquals(horCoordinates.azOctantName("N", "E", "S", "W"), "NW");
        azDeg = 335;
        altDeg = 0.5;
        horCoordinates = HorizontalCoordinates.ofDeg(azDeg, altDeg);
        assertEquals(horCoordinates.azOctantName("N", "E", "S", "W"), "NW");
    }


}

