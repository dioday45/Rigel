package ch.epfl.rigel.astronomy;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */

class HygDatabaseLoaderTest {

    private static final String HYG_CATALOGUE_NAME =
            "/hygdata_v3.csv";

    @Test
    void hygDatabaseIsCorrectlyInstalled() throws IOException {
        try (InputStream hygStream = getClass()
                .getResourceAsStream(HYG_CATALOGUE_NAME)) {
            assertNotNull(hygStream);
        }
    }

    @Test
    void hygDatabaseContainsRigel() throws IOException {
        try (InputStream hygStream = getClass()
                .getResourceAsStream(HYG_CATALOGUE_NAME)) {
            StarCatalogue catalogue = new StarCatalogue.Builder()
                    .loadFrom(hygStream, HygDatabaseLoader.INSTANCE)
                    .build();
            Star rigel = null;
            for (Star s : catalogue.stars()) {
                if (s.name().equalsIgnoreCase("rigel"))
                    rigel = s;
            }

            assertNotNull(rigel);
        }
    }

    @Test
    void load() throws IOException {
        String HYG_CATALOGUE_NAME = "/hygdata_v3.csv";
        InputStream hygStream = getClass().getResourceAsStream(HYG_CATALOGUE_NAME);

        BufferedReader hyg = new BufferedReader(new InputStreamReader(hygStream));
        String[] line = new String[37];
    }

    @Test
    void testInterrogation() throws IOException{
        try (InputStream hygStream = getClass()
                .getResourceAsStream(HYG_CATALOGUE_NAME)) {
            StarCatalogue builder = new StarCatalogue.Builder()
                    .loadFrom(hygStream, HygDatabaseLoader.INSTANCE)
                    .build();
            int i = 0;
            for (Star star : builder.stars()) {
                if (star.name().charAt(0) == '?') {
                    i = 1;
                    assertEquals(' ', star.name().charAt(1));
                }
            }
            assertEquals(1, i);
        }
    }


    @Test
    void hygDatabaseIsCorrectlyInstalled2() throws IOException {
        try (InputStream hygStream = getClass()
                .getResourceAsStream(HYG_CATALOGUE_NAME)) {
            assertNotNull(hygStream);
        }
    }

    @Test
    void hygDatabaseContainsRigel2() throws IOException {
        try (InputStream hygStream = getClass()
                .getResourceAsStream(HYG_CATALOGUE_NAME)) {
            StarCatalogue catalogue = new StarCatalogue.Builder()
                    .loadFrom(hygStream, HygDatabaseLoader.INSTANCE)
                    .build();
            Star rigel = null;
            for (Star s : catalogue.stars()) {
                if (s.name().equalsIgnoreCase("rigel"))
                    rigel = s;
            }
            assertNotNull(rigel);
        }
    }

    @Test
    void hygDatabaseContainsRigel1() throws IOException {
        try (InputStream hygStream = getClass()
                .getResourceAsStream(HYG_CATALOGUE_NAME)) {
            StarCatalogue catalogue = new StarCatalogue.Builder()
                    .loadFrom(hygStream, HygDatabaseLoader.INSTANCE)
                    .build();
            Star rigel = null;
            for (Star s : catalogue.stars()) {
                if (s.name().equalsIgnoreCase("rigel"))
                    rigel = s;
            }
            assertEquals(24436, rigel.hipparcosId());
            assertEquals(5.242298, rigel.equatorialPos().raHr(), 1e-6);
            assertEquals(-8.201640, rigel.equatorialPos().decDeg(), 1e-6);
            assertEquals(0.180, rigel.magnitude(), 1e-3);

        }
    }

    @Test
    void laststar() throws IOException {
        try (InputStream hygStream = getClass()
                .getResourceAsStream(HYG_CATALOGUE_NAME)) {
            StarCatalogue catalogue = new StarCatalogue.Builder()
                    .loadFrom(hygStream, HygDatabaseLoader.INSTANCE)
                    .build();
            Star lastone = null;
            for (Star s : catalogue.stars()) {
                if (s.name().equalsIgnoreCase("? Aqr"))
                    lastone = s;
            }
            assertEquals(0, lastone.hipparcosId());
            assertEquals(6.064662769813043, lastone.equatorialPos().ra(), 1e-6);
            assertEquals(-0.3919549465551, lastone.equatorialPos().dec(), 1e-6);
            assertEquals(5.900, lastone.magnitude(), 1e-3);
            assertEquals(lastone.colorTemperature(), (int) (4600 * (1 / (0.92 * 0 + 1.7) + 1 / (0.92 * 0 + 0.62))), 1e-3);


        }
    }

    @Test
    void blabla() throws IOException {
        try (InputStream hygStream = getClass()
                .getResourceAsStream(HYG_CATALOGUE_NAME)) {
            StarCatalogue catalogue = new StarCatalogue.Builder()
                    .loadFrom(hygStream, HygDatabaseLoader.INSTANCE)
                    .build();
            Star rigel = null;
            for (Star s : catalogue.stars()) {
                if (s.name().equalsIgnoreCase("rigel"))
                    rigel = s;
            }
            assertEquals(24436, rigel.hipparcosId());
        }
    }

    @Test
    void HygDatabaseLoadTest() throws IOException {
        try (InputStream hygStream = getClass().getResourceAsStream(HYG_CATALOGUE_NAME)) {
            StarCatalogue catalogue = new StarCatalogue.Builder().loadFrom(hygStream, HygDatabaseLoader.INSTANCE).build();
            Star rigel = catalogue.stars().get(1019);
            Star bellatrix = catalogue.stars().get(1068);
            Star betelgeuse = catalogue.stars().get(1213);
            Star nameEmpty = catalogue.stars().get(1212);
            Star nameEmpty2 = catalogue.stars().get(1208);
            Star ciEmpty = catalogue.stars().get(5041);
            //hipparcosId
            assertEquals(24436, rigel.hipparcosId());
            assertEquals(25336, bellatrix.hipparcosId());
            //colorTemperature
            assertEquals(10500, rigel.colorTemperature(), 1e2);
            assertEquals(3800, betelgeuse.colorTemperature(), 1e2);
            //names from PROPER
            assertEquals("Rigel", rigel.name());
            assertEquals("Bellatrix", bellatrix.name());
            //name without PROPER and without BAYER
            assertEquals("? Aur", nameEmpty.name());
            //name without PROPER but with BAYER
            assertEquals("Xi Aur", nameEmpty2.name());
            //check of colorTemperature without CI
            assertEquals((int) (4600 * (1 / 1.7 + 1 / 0.62)), ciEmpty.colorTemperature());
            //magnitudes (couldn't find any empty magnitude)
            assertEquals(0.18, rigel.magnitude(), 1e-6);
            assertEquals(0.45, betelgeuse.magnitude(), 1e-6);
            //equatorial coordinates (in radians)
            assertEquals(1.3724303693276385, rigel.equatorialPos().ra());
            assertEquals(-0.143145630755865, rigel.equatorialPos().dec());
            //hipparcosId without HIP
            assertEquals(0, ciEmpty.hipparcosId());
        }
    }


    @Test
    void resultedCatalogueContainsSameNumberStarsFromFile() throws IOException {
        try (InputStream hygStream = getClass().getResourceAsStream(HYG_CATALOGUE_NAME)) {
            StarCatalogue catalogue = new StarCatalogue.Builder().
                    loadFrom(hygStream, HygDatabaseLoader.INSTANCE).build();
            assertEquals(5067, catalogue.stars().size());
        }
    }

































}