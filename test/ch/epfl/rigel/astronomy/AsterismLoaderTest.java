package ch.epfl.rigel.astronomy;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
class AsterismLoaderTest {

    @Test
    void myLoadTest() throws IOException {
        InputStream astStream = getClass().getResourceAsStream("/asterisms.txt");
        InputStream hygStream = getClass().getResourceAsStream("/hygdata_v3.csv");

        int lines = 0;
        StarCatalogue builder = new StarCatalogue.Builder()
                .loadFrom(hygStream, HygDatabaseLoader.INSTANCE)
                .loadFrom(astStream, AsterismLoader.INSTANCE)
                .build();
        for (Asterism a : builder.asterisms()) {
            lines += 1;
        }
        assertEquals(153, lines);


    }

    //*****************************************************************************************************
    private String ASTERISM = "/asterisms.txt";
    private static final String HYG_CATALOGUE_NAME =
            "/hygdata_v3.csv";
    private static final String ASTERISM_CATALOGUE_NAME = "/asterisms.txt";


    @Test
    void hygDatabaseIsCorrectlyInstalled() throws IOException {
        try (InputStream hygStream = getClass()
                .getResourceAsStream(ASTERISM)) {
            assertNotNull(hygStream);
        }
    }

    @Test
    void hygDatabaseContainsRigel() throws IOException {
        InputStream hygStream2 = getClass()
                .getResourceAsStream(ASTERISM);
        try (InputStream hygStream = getClass()
                .getResourceAsStream(HYG_CATALOGUE_NAME)) {
            StarCatalogue catalogue = new StarCatalogue.Builder()
                    .loadFrom(hygStream, HygDatabaseLoader.INSTANCE).
                            loadFrom(hygStream2, AsterismLoader.INSTANCE).build();

            for (Asterism a : catalogue.asterisms()) {

                if(a.stars().get(0).hipparcosId() == 24436){
                    assertEquals(27366, a.stars().get(1).hipparcosId());
                    assertEquals(26727, a.stars().get(2).hipparcosId());
                    assertEquals(27989, a.stars().get(3).hipparcosId());
                    assertEquals(28614, a.stars().get(4).hipparcosId());
                    assertEquals(29426, a.stars().get(5).hipparcosId());
                    assertEquals(28716, a.stars().get(6).hipparcosId());
                    assertThrows(ArrayIndexOutOfBoundsException.class, ()-> a.stars().get(7).hipparcosId());
                }
            }

        }
    }

    @Test
    void loadReturnCatalogueWith153Asterism() throws IOException {
        InputStream asterismsStream = getClass().getResourceAsStream(ASTERISM);
        try (InputStream hygStream = getClass().getResourceAsStream(HYG_CATALOGUE_NAME)) {

            StarCatalogue testCatalogue = new StarCatalogue.Builder().loadFrom(hygStream, HygDatabaseLoader.INSTANCE).
                    loadFrom(asterismsStream, AsterismLoader.INSTANCE).build();

            assertEquals(153, testCatalogue.asterisms().size());

        }
    }

    @Test
    void loadWorks() throws IOException {
        InputStream hygStream2 = getClass()
                .getResourceAsStream(ASTERISM);
        try (InputStream hygStream = getClass()
                .getResourceAsStream(HYG_CATALOGUE_NAME)) {
            StarCatalogue catalogue = new StarCatalogue.Builder()
                    .loadFrom(hygStream, HygDatabaseLoader.INSTANCE).
                            loadFrom(hygStream2, AsterismLoader.INSTANCE).build();

            for (Asterism a : catalogue.asterisms()) {
                //System.out.println(a.stars().get(0).hipparcosId());
                if (a.stars().get(0).hipparcosId() == 93683) {
                    assertEquals(94141, a.stars().get(1).hipparcosId());
                    assertEquals(95168, a.stars().get(2).hipparcosId());
                    assertThrows(ArrayIndexOutOfBoundsException.class, () -> a.stars().get(3).hipparcosId());

                }
            }
        }
    }

    @Test
    void asterismDatabaseIsCorrectlyInstalled() throws IOException {
        try (InputStream asterismStream = getClass()
                .getResourceAsStream(ASTERISM_CATALOGUE_NAME)) {
            assertNotNull(asterismStream);
        }
    }


    @Test
    void asterismsIndicesWorksFromLoadedStream() throws IOException {
        InputStream hygStream2 = getClass()
                .getResourceAsStream(ASTERISM);
        try (InputStream hygStream = getClass()
                .getResourceAsStream(HYG_CATALOGUE_NAME)) {
            StarCatalogue catalogue = new StarCatalogue.Builder()
                    .loadFrom(hygStream, HygDatabaseLoader.INSTANCE).
                            loadFrom(hygStream2, AsterismLoader.INSTANCE).build();

            for (Asterism a : catalogue.asterisms()) {
                //System.out.println(a.stars().get(0).hipparcosId());
                if (a.stars().get(0).hipparcosId() == 24436) {
                    //1019 indice de Rigel dans le catalogue de toutes les étoiles
                    assertEquals(1019, catalogue.asterismIndices(a).get(0));
                }
            }

        }
    }


    private static final String AST_CATALOGUE_NAME = "/asterisms.txt";

    @Test
    void astDatabaseIsCorrectlyInstalled() throws IOException {
        try (InputStream hygStream = getClass()
                .getResourceAsStream(AST_CATALOGUE_NAME)) {
            assertNotNull(hygStream);
        }
    }

    @SuppressWarnings("unused")
    @Test
    void astDatabaseWorks() throws IOException {
        InputStream hygStream = null;
        InputStream astStream = null;
        try {
            hygStream = getClass().getResourceAsStream("/hygdata_v3.csv");
            astStream = getClass().getResourceAsStream(AST_CATALOGUE_NAME);
            StarCatalogue catalogue = new StarCatalogue.Builder()
                    .loadFrom(hygStream, HygDatabaseLoader.INSTANCE)
                    .loadFrom(astStream, AsterismLoader.INSTANCE).build();

        } finally {
            hygStream.close();
            astStream.close();
        }
    }

}