package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
class ObservedSkyTest {

    String HYG_CATALOGUE_NAME = "/hygdata_v3.csv";
    String AST_CATALOGUE_NAME = "/asterisms.txt";
    InputStream hygStream = getClass().getResourceAsStream(HYG_CATALOGUE_NAME);

    StarCatalogue.Builder builder = new StarCatalogue.Builder().loadFrom(hygStream, HygDatabaseLoader.INSTANCE);

    InputStream astStream = getClass().getResourceAsStream(AST_CATALOGUE_NAME);
    StarCatalogue catalogue = builder.loadFrom(astStream, AsterismLoader.INSTANCE).build();


    ZonedDateTime time = ZonedDateTime.of(LocalDate.of(2020, Month.APRIL, 4), LocalTime.of(0, 0), ZoneOffset.UTC);
    GeographicCoordinates geoCoords = GeographicCoordinates.ofDeg(30, 45);
    StereographicProjection stereo = new StereographicProjection(HorizontalCoordinates.ofDeg(20, 22));
    EquatorialToHorizontalConversion convEquToHor = new EquatorialToHorizontalConversion(time, geoCoords);
    EclipticToEquatorialConversion convEcltoEqu = new EclipticToEquatorialConversion(time);
    ObservedSky sky = new ObservedSky(time, geoCoords, stereo, catalogue);
    Sun sun = SunModel.SUN.at(Epoch.J2010.daysUntil(time), convEcltoEqu);
    Moon moon = MoonModel.MOON.at(Epoch.J2010.daysUntil(time), convEcltoEqu);


    ObservedSkyTest() throws IOException {

    }

    @Test
    void getSunPosition() {
        CartesianCoordinates sunCoord = stereo.apply(convEquToHor.apply(sun.equatorialPos()));
        assertEquals(sunCoord.x(), sky.getSunPosition().x());
        assertEquals(sunCoord.y(), sky.getSunPosition().y());
    }

    @Test
    void getMoon() {
    }

    @Test
    void getMoonPosition() {
        CartesianCoordinates moonCoord = stereo.apply(convEquToHor.apply(moon.equatorialPos()));
        assertEquals(moonCoord.x(), sky.getMoonPosition().x());
        assertEquals(moonCoord.y(), sky.getMoonPosition().y());
    }

    @Test
    void getPlanets() {
    }

    @Test
    void getPlanetsPosition() {
        int i = 0;
        for (PlanetModel planet : PlanetModel.ALL) {
            if (planet != PlanetModel.EARTH) {
                Planet planet1 = planet.at(Epoch.J2010.daysUntil(time), convEcltoEqu);
                assertEquals(stereo.apply(convEquToHor.apply(planet1.equatorialPos())).x(), sky.getPlanetsPosition()[i]);
                assertEquals(stereo.apply(convEquToHor.apply(planet1.equatorialPos())).y(), sky.getPlanetsPosition()[i + 1]);

                i += 2;
            }
        }
    }

    @Test
    void getStars() {
    }

    @Test
    void getStarsPosition() {
        int i = 0;
        for (Star star : sky.getStars()) {
            assertEquals(stereo.apply(convEquToHor.apply(star.equatorialPos())).x(), sky.getStarsPosition()[i]);
            assertEquals(stereo.apply(convEquToHor.apply(star.equatorialPos())).y(), sky.getStarsPosition()[i + 1]);
            i += 2;
        }
        assertEquals(catalogue.stars().size(), sky.getStars().size());
    }

    @Test
    void getAsterisms() {
    }

    @Test
    void getStarIndexOfAsterism() {
    }

    @Test
    void objectClosestTo() throws IOException {
        assertEquals(Optional.empty(), sky.objectClosestTo(stereo.apply(new EquatorialToHorizontalConversion(time, geoCoords).apply(EquatorialCoordinates.of(0.004696959812148989,
                -0.8618930353430763))), 0.001));
        assertEquals("Tau Phe", sky.objectClosestTo(stereo.apply(new EquatorialToHorizontalConversion(time, geoCoords)
                .apply(EquatorialCoordinates.of(0.004696959812148989, -0.861893035343076))), 0.1).get().name());
    }
}