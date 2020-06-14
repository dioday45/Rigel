package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */

class MoonModelTest {

    @Test
    void at() {
        assertEquals(14.211456457836277, MoonModel.MOON.at(-2313, new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2003,
                Month.SEPTEMBER, 1), LocalTime.of(0,0), ZoneOffset.UTC))).equatorialPos().raHr(), .10f);


    }

    @Test
    void at2(){
        assertEquals(-0.20114171346019355, MoonModel.MOON.at(-2313, new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2003,
                Month.SEPTEMBER, 1),LocalTime.of(0,0), ZoneOffset.UTC))).equatorialPos().dec(), .10f);

    }

    @Test
    void at3(){
        assertEquals( 0.009225908666849136, MoonModel.MOON.at(Epoch.J2010.daysUntil(ZonedDateTime.of(LocalDate.of(1979, 9, 1),LocalTime.of(0, 0),
                ZoneOffset.UTC)), new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(1979
                , 9, 1),LocalTime.of(0, 0),ZoneOffset.UTC))).angularSize());
    }

    @Test
    void at4(){
        assertEquals("Lune (22.5%)", MoonModel.MOON.at(Epoch.J2010.daysUntil(ZonedDateTime.of(LocalDate.of(2003, 9, 1),LocalTime.of(0, 0),
                ZoneOffset.UTC)), new EclipticToEquatorialConversion(ZonedDateTime.of( LocalDate.of(2003, 9, 1),
                LocalTime.of(0, 0),ZoneOffset.UTC))).info());
    }

    @Test
    void testMoon(){
        Moon m = MoonModel.MOON.at(-2313, new EclipticToEquatorialConversion(
                ZonedDateTime.of(LocalDate.of(2003,  Month.SEPTEMBER, 1), LocalTime.of(0,0), ZoneOffset.UTC)));
        assertEquals(14.211456457836277, m.equatorialPos().raHr(), 1e-8);
        assertEquals(-0.20114171346019355, m.equatorialPos().dec(), 1e-9);
        assertEquals(0.009225908666849136, MoonModel.MOON.at(Epoch.J2010.daysUntil(ZonedDateTime.of(LocalDate.of(1979, 9, 1),LocalTime.of(0, 0),
                ZoneOffset.UTC)), new EclipticToEquatorialConversion(ZonedDateTime.of(
                LocalDate.of(1979, 9, 1),LocalTime.of(0, 0),ZoneOffset.UTC))).
                angularSize());
        assertEquals("Lune (22.5%)", MoonModel.MOON.at(Epoch.J2010.daysUntil(ZonedDateTime.of(LocalDate.of(2003, 9, 1),LocalTime.of(0, 0),
                ZoneOffset.UTC)), new EclipticToEquatorialConversion(ZonedDateTime.of( LocalDate.of(2003, 9, 1),
                LocalTime.of(0, 0),ZoneOffset.UTC))).info());

    }

    @Test
    void atWorks() {
        ZonedDateTime zdt = ZonedDateTime.of(LocalDate.of(2003, Month.SEPTEMBER, 1), LocalTime.of(0, 0), ZoneOffset.UTC);
        MoonModel moonM = MoonModel.MOON;
        EclipticToEquatorialConversion eclToEq = new EclipticToEquatorialConversion(zdt);
        Moon moon = moonM.at(Epoch.J2010.daysUntil(zdt), eclToEq);
        EclipticToEquatorialConversion eclToEqTEMP = new EclipticToEquatorialConversion(zdt);
        EclipticCoordinates eclCoords = EclipticCoordinates.of(Angle.ofDeg(214.862515), Angle.ofDeg(1.716257));
        assertEquals(eclToEqTEMP.apply(eclCoords).raDeg(), moon.equatorialPos().raDeg(), 1e-6);
        assertEquals(eclToEqTEMP.apply(eclCoords).decDeg(), moon.equatorialPos().decDeg(), 1e-6);
        // PRIVATE TEST // MOON HAS NO GETTER
//  assertEquals(moon.phase(), 0.225, 1e-3);
        assertEquals(0, moon.magnitude());
        assertEquals(Angle.ofDeg(0.546822), moon.angularSize(), 1e-6);
        // MY TESTS
        zdt = ZonedDateTime.of(LocalDate.of(2001, Month.JULY, 24), LocalTime.of(22, 0), ZoneOffset.UTC);
        moonM = MoonModel.MOON;
        eclToEq = new EclipticToEquatorialConversion(zdt);
        moon = moonM.at(Epoch.J2010.daysUntil(zdt), eclToEq);
        assertEquals(181.54499252410398, moon.equatorialPos().raDeg(), 1e-6);
        assertEquals(4.914395456390821, moon.equatorialPos().decDeg(), 1e-6);
        assertEquals(0, moon.magnitude());
        assertEquals(0.009492523036897182, moon.angularSize());
    }
}