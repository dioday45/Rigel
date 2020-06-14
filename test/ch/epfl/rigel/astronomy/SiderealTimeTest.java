package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */

class SiderealTimeTest {

    @Test
    void greenwich() {

        ZonedDateTime test1 = ZonedDateTime.of(1980, 4, 22,
                19, 36, 51, (int)67e7, ZoneId.of("UTC+5"));
        double t1 = SiderealTime.greenwich(test1);
        assertEquals(1.2221107819499224, t1);

        ZonedDateTime test2 = ZonedDateTime.of(2001, 1, 27,
                12, 0, 0, 0, ZoneId.of("UTC"));
        double t2 = SiderealTime.greenwich(test2);
        assertEquals(5.355270290366605, t2);

        ZonedDateTime test3 = ZonedDateTime.of(2004, 9,23,
                11,0,0,0,ZoneId.of("UTC"));
        double t3 = SiderealTime.greenwich(test3);
        assertEquals(2.9257399567031235, t3, 1e-10);

        ZonedDateTime test4 = ZonedDateTime.of(2001,9,11,
                8,14,0,0, ZoneId.of("UTC"));
        double t4 = SiderealTime.greenwich(test4);
        assertEquals( 1.9883078130455532, t4, 1e-10);


    }

    @Test
    void local2() {
        ZonedDateTime test1 =ZonedDateTime.of(1980,4,22,
                14,36,51,(int)67e7,ZoneOffset.UTC);
        assertEquals(Angle.ofHr(0.401452778), SiderealTime.local(test1,
                GeographicCoordinates.ofDeg(-64,45)), 1e-7);

    }
    //******************************************************************************************************************

    @Test
    void greenwichWorks() {
        double result = SiderealTime.greenwich(ZonedDateTime.of(LocalDate.of(2000,
                1, 1), LocalTime.of(12,0), ZoneOffset.UTC).plusDays(36525));
        assertEquals(4.908408026,result, 1e-5);

        double result2 = SiderealTime.greenwich(ZonedDateTime.of(LocalDate.of(2000,
                1, 1), LocalTime.of(12,0), ZoneOffset.UTC));
        assertEquals(4.89496,result2, 1e-5);

        double exempleBook = SiderealTime.greenwich(ZonedDateTime.of(LocalDate.of(1980,
                4, 22), LocalTime.of(14,36,51,670000000),
                ZoneOffset.UTC));
        assertEquals(Angle.ofHr(4.668119444),exempleBook, 1e-7);
    }

    @Test
    void localWorks() {
        double exempleBook = SiderealTime.local(ZonedDateTime.of(LocalDate.of(1980,
                4, 22), LocalTime.of(14,36,51,670000000), ZoneOffset.UTC),
                GeographicCoordinates.ofDeg(-64,0));
        assertEquals(Angle.ofHr(0.401453),exempleBook,1E-7);

    }

    @Test
    void greenwichA() {
        assertEquals(5.355270290366605,
                SiderealTime.greenwich(ZonedDateTime.of(2001,1,27, 12, 0 , 0,0, ZoneId.of("UTC"))), 1e-7);
        assertEquals(1.2220619247737088,
                SiderealTime.greenwich(ZonedDateTime.of(1980,4,22,14,36,51,67, ZoneId.of("UTC"))), 1e-7);
        assertEquals(2.9257399567031235,
                SiderealTime.greenwich(ZonedDateTime.of(2004,9,23, 11,0,0,0,ZoneId.of("UTC"))), 1e-7);
        assertEquals(1.9883078130455532,
                SiderealTime.greenwich(ZonedDateTime.of(2001,9,11,8,14,0,0, ZoneId.of("UTC"))),
                1e-7);
    }

    @Test
    void local() {
        assertEquals(1.74570958832716,
                SiderealTime.local(ZonedDateTime.of(1980,4,22,14,36,51,27, ZoneOffset.UTC), GeographicCoordinates.ofDeg(30,45)),
                1e-4);
    }


    @Test
    void greenwichWorksA() {
        double value = SiderealTime.greenwich(ZonedDateTime.of(1980, 4,22, 14, 36, 51, 670000000, ZoneOffset.UTC));
        double answer = Angle.ofHr(4.668119327);
        assertEquals(answer, value, 1e-10);
    }

    @Test
    void localWorksA() {
        double value = SiderealTime.local(ZonedDateTime.of(1980, 4,22, 14, 36, 51, 670000000, ZoneOffset.UTC), GeographicCoordinates.ofDeg(90, 45));
        double answer = Angle.normalizePositive(Angle.ofHr(4.668119327)+Math.PI/2);
        assertEquals(answer, value, 1e-10);
    }


    @Test
    void greenwichWorksB() {
        ZonedDateTime d = ZonedDateTime.of(LocalDate.of(1980, Month.APRIL, 22),
                LocalTime.of(14, 36, 51, (int)67e7), ZoneOffset.UTC);
        assertEquals(Angle.ofHr(4.668120), SiderealTime.greenwich(d), 1e-6);
    }

    @Test
    void localWorksB() {
        ZonedDateTime d = ZonedDateTime.of(LocalDate.of(1980, Month.APRIL, 22),
                LocalTime.of(14, 36, 51, (int)67e7), ZoneOffset.UTC);
        assertEquals(Angle.ofHr(4.668120) + Angle.ofDeg(30),
                SiderealTime.local(d, GeographicCoordinates.ofDeg(30, 45)),
                1e-6);
    }




}