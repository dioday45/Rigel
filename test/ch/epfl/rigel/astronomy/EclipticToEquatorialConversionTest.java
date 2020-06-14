package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.Polynomial;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
class EclipticToEquatorialConversionTest {

    @Test
    void singularApplyBookValues() {
        LocalDate testDate = LocalDate.of(2009, Month.JULY, 7);
        LocalTime testTime = LocalTime.of(0,0);
        EclipticToEquatorialConversion test = new EclipticToEquatorialConversion(ZonedDateTime.of(testDate, testTime, ZoneOffset.UTC));
        assertEquals(2.508430994,test.apply(EclipticCoordinates.of(Angle.ofDMS(139,41,10), Angle.ofDMS(4,52,31))).ra(), 10e-6);

    }

    //******************************************************************************************************************
    @Test
    void EclipticToEquatorialConversion(){
        assertEquals(0.40907122964931697, Polynomial.of(Angle.ofArcsec(0.00181), Angle.ofArcsec(-0.0006), Angle.ofArcsec(-46.815), Angle.ofDMS(23, 26, 21.45))
                .at(Epoch.J2000.julianCenturiesUntil(ZonedDateTime.of(2009,7,6,0,0,0,0, ZoneOffset.UTC))), 10e-16);
    }

    @Test
    void applyRa() {
        EclipticToEquatorialConversion a = new EclipticToEquatorialConversion(ZonedDateTime.of(2009, 7, 6, 0, 0, 0, 0, ZoneOffset.UTC));
        assertEquals(0.34095012064184566,
                a.apply(EclipticCoordinates.of(Angle.ofDMS(139, 41, 10), Angle.ofDMS(4, 52, 31))).dec(), 10e-16);
    }
    @Test
    void applyDeg() {

        EclipticToEquatorialConversion b = new  EclipticToEquatorialConversion(ZonedDateTime.of(2009,7,6,0,0,0,0,ZoneOffset.UTC));
        assertEquals(9.581478170200256,
                b.apply(EclipticCoordinates.of(Angle.ofDMS(139,41,10), Angle.ofDMS(4,52,31))).raHr());
    }


    @Test
    void applyWorks() {
        EclipticToEquatorialConversion e = new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2009, Month.JULY, 6), LocalTime.of(0, 0), ZoneOffset.UTC));
        EquatorialCoordinates eq = e.apply(EclipticCoordinates.of(Angle.ofDeg(139.686111), Angle.ofDeg(4.875278)));
        double ra = eq.ra();
        double dec = eq.dec();
        assertEquals(Angle.ofHr(9.581478), ra, 10e-8);
        assertEquals(Angle.ofDMS(19, 32, 6.01), dec, 10e-8);
    }



    @Test
    void equalsThrowsUOE() {
        assertThrows(UnsupportedOperationException.class, () -> {
            var interval = ClosedInterval.symmetric(1);
            interval.equals(interval);
        });
    }

    @Test
    void hashCodeThrowsUOE() {
        assertThrows(UnsupportedOperationException.class, () -> {
            ClosedInterval.symmetric(1).hashCode();
        });}
}