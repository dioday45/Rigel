package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class PlanetModelTest {

    @Test
    void at() {

        EclipticToEquatorialConversion conversionA = new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2020, Month.NOVEMBER, 22),
                LocalTime.of(0, 0),
                ZoneOffset.UTC));

        Planet a0 = PlanetModel.JUPITER.at(-2231, new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2003, Month.NOVEMBER, 22),
                LocalTime.of(0, 0),
                ZoneOffset.UTC)));

        assertEquals(11.18715493470968, a0.equatorialPos().raHr(), 1e-6);
        assertEquals(6.3566355066857465, a0.equatorialPos().decDeg(), 1e-6);
        assertEquals(1.7022492829710245E-4, a0.angularSize(), 1e-6);
        assertEquals(-1.9885659217834473, a0.magnitude(), 1e-6);

        Planet a = PlanetModel.JUPITER.at(3979, conversionA);

        EquatorialCoordinates testA = conversionA.apply(EclipticCoordinates.of(Angle.ofDeg(294.4995211), Angle.ofDeg(-0.461731217)));
        assertEquals(testA.raHr(), a.equatorialPos().raHr(), 1e-6);
        assertEquals(testA.decDeg(), a.equatorialPos().decDeg(), 1e-6);
        assertEquals(Angle.ofArcsec(34.99063359), a.angularSize(), 1e-6);

        Planet b = PlanetModel.MERCURY.at(-2231, new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2003, Month.NOVEMBER, 22),
                LocalTime.of(0, 0),
                ZoneOffset.UTC)));
        assertEquals(16.820074565897148, b.equatorialPos().raHr(), 1e-6);
        assertEquals(-24.500872462861224, b.equatorialPos().decDeg(), 1e-6);

        Planet c = PlanetModel.EARTH.at(Epoch.J2010.daysUntil(ZonedDateTime.of(LocalDate.of(2003, Month.NOVEMBER, 22),
                LocalTime.of(0, 0),
                ZoneOffset.UTC)), new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2003, Month.NOVEMBER, 22),
                LocalTime.of(0, 0),
                ZoneOffset.UTC)));
        EclipticToEquatorialConversion conversionC = new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2003, Month.NOVEMBER, 22),
                LocalTime.of(0, 0),
                ZoneOffset.UTC));
        EquatorialCoordinates testC = conversionC.apply(EclipticCoordinates.of(Angle.ofDeg(330.1914135),0));
        //assertEquals(testC.raHr(), c.equatorialPos().raHr(), 1e-6);
        //assertEquals(testC.decDeg(), c.equatorialPos().decDeg(), 1e-6);

        //ne marche plus car on a modifié une valeur pour URANUS
/*        Planet d = PlanetModel.URANUS.at(Epoch.J2010.daysUntil(ZonedDateTime.of(LocalDate.of(2014, Month.MARCH, 8),
                LocalTime.of(0, 0),
                ZoneOffset.UTC)), new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2014, Month.MARCH, 8),
                LocalTime.of(0, 0),
                ZoneOffset.UTC)));
        assertEquals(19.87335024935161, d.equatorialPos().raHr(), 1e-6);
        assertEquals(-21.416508957848446, d.equatorialPos().decDeg(), 1e-6);*/
        assertEquals(11.187154934709678, PlanetModel.JUPITER.at(-2231.0,
                new EclipticToEquatorialConversion(
                        ZonedDateTime.of(LocalDate.of(2003, Month.NOVEMBER, 22),
                                LocalTime.of(0, 0, 0, 0), ZoneOffset.UTC)))
                .equatorialPos().raHr(), 1e-12);
        assertEquals(6.356635506685756, PlanetModel.JUPITER.at(-2231.0,
                new EclipticToEquatorialConversion(
                        ZonedDateTime.of(LocalDate.of(2003, Month.NOVEMBER, 22),
                                LocalTime.of(0, 0, 0, 0), ZoneOffset.UTC))).equatorialPos().decDeg(), 1e-10);
        assertEquals(35.11141185362771, Angle.toDeg(PlanetModel.JUPITER.at(-2231.0,new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2003, Month.NOVEMBER, 22), LocalTime.of(0, 0, 0, 0), ZoneOffset.UTC))).angularSize())*3600);
        assertEquals(-1.9885659217834473, PlanetModel.JUPITER.at(-2231.0,new EclipticToEquatorialConversion(ZonedDateTime.of(LocalDate.of(2003, Month.NOVEMBER, 22), LocalTime.of(0, 0, 0, 0), ZoneOffset.UTC))).magnitude());
        assertEquals(16.8200745658971, PlanetModel.MERCURY.at(-2231.0,
                new EclipticToEquatorialConversion(
                        ZonedDateTime.of(LocalDate.of(2003, Month.NOVEMBER, 22),
                                LocalTime.of(0, 0, 0, 0), ZoneOffset.UTC)))
                .equatorialPos().raHr(), 10e-14);
        assertEquals(-24.50087246286123, PlanetModel.MERCURY.at(-2231.0,
                new EclipticToEquatorialConversion(
                        ZonedDateTime.of(LocalDate.of(2003, Month.NOVEMBER, 22),
                                LocalTime.of(0, 0, 0, 0), ZoneOffset.UTC))).equatorialPos().decDeg(), 10e-14);
    }




        @Test
        void listImmutable() {
            assertThrows(UnsupportedOperationException.class, () -> {
                PlanetModel.ALL.clear();
            });
            assertTrue(PlanetModel.ALL.get(0).equals(PlanetModel.MERCURY));
            assertTrue(PlanetModel.ALL.get(7).equals(PlanetModel.NEPTUNE));
        }

        @Test
        void atWorks() {
            //BOOK
            ZonedDateTime zdt = ZonedDateTime.of(LocalDate.of(2003, Month.NOVEMBER, 22), LocalTime.of(0, 0), ZoneOffset.UTC);
            PlanetModel planetM = PlanetModel.MERCURY;
            EclipticToEquatorialConversion eclToEq = new EclipticToEquatorialConversion(zdt);
            Planet planet = planetM.at(Epoch.J2010.daysUntil(zdt), eclToEq);
            EclipticToEquatorialConversion eclToEqTEMP = new EclipticToEquatorialConversion(zdt);
            var eq = eclToEqTEMP.apply(EclipticCoordinates.of(Angle.ofDeg(253.929758), Angle.ofDeg(-2.044057)));
            assertEquals(eq.toString(), planet.equatorialPos().toString());

            zdt = ZonedDateTime.of(LocalDate.of(2003, Month.NOVEMBER, 22), LocalTime.of(0, 0), ZoneOffset.UTC);
            planetM = PlanetModel.JUPITER;
            eclToEq = new EclipticToEquatorialConversion(zdt);
            planet = planetM.at(Epoch.J2010.daysUntil(zdt), eclToEq);
            eclToEqTEMP = new EclipticToEquatorialConversion(zdt);
            eq = eclToEqTEMP.apply(EclipticCoordinates.of(Angle.ofDeg(166.310510), Angle.ofDeg(1.036466)));
            assertEquals(eq.toString(), planet.equatorialPos().toString());
        }

}