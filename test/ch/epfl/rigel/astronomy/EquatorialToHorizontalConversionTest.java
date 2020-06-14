package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.coordinates.EquatorialToHorizontalConversion;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */

class EquatorialToHorizontalConversionTest {

    @Test
    void apply() {
    }

//***************************************************************************************************************************************

    @Test  // Selon Book
    void applyWorksOncorrectValue() {
        EquatorialCoordinates ecl = EquatorialCoordinates.of(4.544705046, Angle.ofDeg(23.219444));
        EquatorialToHorizontalConversion equToHor = new EquatorialToHorizontalConversion(ZonedDateTime.of(LocalDate.of(1990,
                12, 15), LocalTime.of(17,36,51,230000000),
                ZoneOffset.UTC), GeographicCoordinates.ofDeg(0,52));
        Assertions.assertEquals(Angle.ofDeg(283.271023), equToHor.apply(ecl).az(),1E-7);
    }

    @Test
    void applyWorksOnIncorrectValue() {
        EquatorialCoordinates ecl = EquatorialCoordinates.of(1,Angle.ofDeg(23.219444));
        EquatorialToHorizontalConversion equToHor = new EquatorialToHorizontalConversion(ZonedDateTime.of(LocalDate.of(1990,
                12, 15), LocalTime.of(17,36,51,230000000),
                ZoneOffset.UTC), GeographicCoordinates.ofDeg(0,52));
        Assertions.assertEquals(91.1454282, equToHor.apply(ecl).azDeg(),1E-5);
    }

    @Test
    void applyWorks() {
        EquatorialToHorizontalConversion e = new EquatorialToHorizontalConversion(ZonedDateTime.of(LocalDate.of(2009, Month.JULY, 6), LocalTime.of(0, 0), ZoneOffset.UTC), GeographicCoordinates.ofDeg(45, 50));
        HorizontalCoordinates hor = e.apply(EquatorialCoordinates.of(Angle.ofDeg(139.686111), Angle.ofDeg(4.875278)));
        double az = hor.az();
        double alt = hor.alt();
        Assertions.assertEquals(0.19926125035109926, az, 10e-8);
        Assertions.assertEquals(-0.6025199424394778, alt, 10e-8);
    }


    @Test
    void equalsThrowsUOE() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            var conversion = new EquatorialToHorizontalConversion(
                    ZonedDateTime.of(LocalDate.of(1980, Month.APRIL, 22),
                            LocalTime.of(14, 36, 51, 67), ZoneOffset.UTC),
                    GeographicCoordinates.ofDeg(30, 45));
            conversion.equals(conversion);
        });
    }

    @Test
    void hashCodeThrowsUOE() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            var conversion = new EquatorialToHorizontalConversion(
                    ZonedDateTime.of(LocalDate.of(1980, Month.APRIL, 22),
                            LocalTime.of(14, 36, 51, 67), ZoneOffset.UTC),
                    GeographicCoordinates.ofDeg(30, 45));
            conversion.hashCode();
        });
    }
}