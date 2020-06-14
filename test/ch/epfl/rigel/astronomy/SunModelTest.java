package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.StringReader;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * SunModelTest
 *
 * @author Robin Jaccard (310682)
 * @author Carl Schütz (289080)
 */
public class SunModelTest {

    @Test
    void atWorks() {
        //System.out.println(Epoch.J2010.daysUntil(ZonedDateTime.of(1988, 7, 27, 0, 0, 0,0, ZoneOffset.UTC)));
        Sun sunTest1 = SunModel.SUN.at(-2349, new EclipticToEquatorialConversion(ZonedDateTime.of(2003, 7,
                27, 0, 0, 0, 0, ZoneOffset.UTC)));

        assertEquals(Angle.ofDeg(201.1591307), sunTest1.meanAnomaly(),1e-6);
        assertEquals(Angle.ofDeg(123.5806048),sunTest1.eclipticPos().lon(), 1e-6);
    }

    @Test
    void angularSizeSunWorks () {
        Sun sunTest = SunModel.SUN.at(-2349, new EclipticToEquatorialConversion(ZonedDateTime.of(1988, 7,
                27, 0, 0, 0, 0, ZoneOffset.UTC)));

        assertEquals(Angle.ofDeg(0.524980784), sunTest.angularSize(), 1e-6);
    }


    @Test
    void atWorksA() {
        ZonedDateTime zdt = ZonedDateTime.of(LocalDate.of(2003, Month.JULY, 27), LocalTime.of(0, 0), ZoneOffset.UTC);
        SunModel sunM = SunModel.SUN;
        EclipticToEquatorialConversion eclToEq = new EclipticToEquatorialConversion(zdt);
        Sun sun = sunM.at(Epoch.J2010.daysUntil(zdt), eclToEq);
        EclipticToEquatorialConversion eclToEqTEMP = new EclipticToEquatorialConversion(zdt);
        var eq = eclToEqTEMP.apply(EclipticCoordinates.of(Angle.ofDeg(123.580601), 0));
        assertEquals(eq.toString(), sun.equatorialPos().toString());
        assertEquals(0.009161771275103092, sun.angularSize(), 1e-6);
        assertEquals(Angle.normalizePositive(-40.47140884399414), sun.meanAnomaly(), 1e-6);
        //MY RESULTS
        zdt = ZonedDateTime.of(LocalDate.of(2011, Month.JULY, 28), LocalTime.of(12, 0), ZoneOffset.UTC);
        sunM = SunModel.SUN;
        eclToEq = new EclipticToEquatorialConversion(zdt);
        sun = sunM.at(Epoch.J2010.daysUntil(zdt), eclToEq);
        assertEquals("(ra = 8.4950h, dec = 18.9977°)", sun.equatorialPos().toString());
        assertEquals(0.009163237176835537, sun.angularSize(), 1e-6);
        //assertEquals(9.820953369140625, sun.meanAnomaly(), 1e-6);
        zdt = ZonedDateTime.of(LocalDate.of(2010, Month.JANUARY, 1), LocalTime.of(12, 0), ZoneOffset.UTC);
        sunM = SunModel.SUN;
        eclToEq = new EclipticToEquatorialConversion(zdt);
        sun = sunM.at(Epoch.J2010.daysUntil(zdt), eclToEq);
        assertEquals("(ra = 18.7950h, dec = -22.9852°)", sun.equatorialPos().toString());
        assertEquals(0.009462808258831501, sun.angularSize(), 1e-6);
        //assertEquals(-0.03624628111720085, sun.meanAnomaly(), 1e-6);
    }
    @Test
    void SunModelat() {
        assertEquals(5.9325494700300885, SunModel.SUN.at(27 + 31, new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2010, Month.FEBRUARY, 27), LocalTime.of(0, 0), ZoneOffset.UTC))).equatorialPos().ra());
        assertEquals(8.392682808297808, SunModel.SUN.at(-2349, new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2003, Month.JULY,
                27), LocalTime.of(0, 0, 0, 0), ZoneOffset.UTC))).equatorialPos().raHr(), 1e-10);
        assertEquals(19.35288373097352, SunModel.SUN.at(-2349, new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2003, Month.JULY,
                27), LocalTime.of(0, 0, 0, 0), ZoneOffset.UTC))).equatorialPos().decDeg(), 1e-10);
    }
    @Test
    void color(){
        assertEquals(10515, new Star(24436, "Rigel", EquatorialCoordinates.of(0, 0), 0, -0.03f)
                .colorTemperature());
        assertEquals(3793, new Star(27989, "Betelgeuse", EquatorialCoordinates.of(0, 0), 0, 1.50f).colorTemperature());
    }
}


