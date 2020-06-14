package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
class StarCatalogueTest {
    private Star rigel = new Star(24436, "Rigel", EquatorialCoordinates.of(Angle.ofHr(5.242298), Angle.ofDeg(-8.201640)), 0.180f, -0.03f);
    private Star betetlgeuse = new Star(27989, "Betelgeuse", EquatorialCoordinates.of(Angle.ofHr(5.919529), Angle.ofDeg(7.407063)), 0.450f, 1.50f);

    @Test
    void myStarCatalogueTest(){

        List<Star> listOfStar = new ArrayList<>();
        listOfStar.add(rigel);
        listOfStar.add(betetlgeuse);

        Asterism asterism = new Asterism(listOfStar);
        List<Asterism> listOfAsterism = new ArrayList<Asterism>();
        listOfAsterism.add(asterism);

        StarCatalogue starCatalogue = new StarCatalogue(listOfStar, listOfAsterism);

        assertEquals("[0, 1]", starCatalogue.asterismIndices(asterism).toString());
        assertEquals("[Rigel, Betelgeuse]", starCatalogue.stars().toString());
        assertFalse(!(starCatalogue.asterisms().containsAll(listOfAsterism)));

        listOfStar.remove(rigel);
        Asterism asterism1 = new Asterism(listOfStar);
        assertThrows(IllegalArgumentException.class, () -> {
            starCatalogue.asterismIndices(asterism1);
        });
        assertThrows( IllegalArgumentException.class, () -> {
            StarCatalogue test = new StarCatalogue(listOfStar, listOfAsterism);
        });

    }

    @Test
    void myImmuableList(){

        List<Star> listOfStar = new ArrayList<>();
        listOfStar.add(rigel);
        listOfStar.add(betetlgeuse);

        Asterism asterism = new Asterism(listOfStar);
        List<Asterism> listOfAsterism = new ArrayList<Asterism>();
        listOfAsterism.add(asterism);

        StarCatalogue starCatalogue = new StarCatalogue(listOfStar, listOfAsterism);

        List<Star> immuStar = starCatalogue.stars();
        Set<Asterism> immuAster = starCatalogue.asterisms();

        assertThrows(UnsupportedOperationException.class, () -> {
            immuStar.add(rigel);
        });

        assertThrows(UnsupportedOperationException.class, () -> {
            immuAster.add(asterism);
        });
    }

    @Test
    void myBuilderTest(){
        List<Star> listOfStar = new ArrayList<>();
        listOfStar.add(rigel);
        listOfStar.add(betetlgeuse);

        Asterism asterism = new Asterism(listOfStar);
        List<Asterism> listOfAsterism = new ArrayList<Asterism>();
        listOfAsterism.add(asterism);

        StarCatalogue.Builder builder = new StarCatalogue.Builder();
        builder.addStar(rigel);
        assert builder.stars().get(0).equals(rigel);

        builder.addAsterism(asterism);
        assert builder.asterisms().get(0).equals(asterism);

        assertThrows(IllegalArgumentException.class, () -> {
            builder.build();
        });
        builder.addStar(betetlgeuse);
        StarCatalogue starCatalogue = builder.build();

        assertEquals("[0, 1]", starCatalogue.asterismIndices(asterism).toString());
        assertEquals("[Rigel, Betelgeuse]", starCatalogue.stars().toString());


    }
 //*******************************************************************************************
        private String ASTERISM = "/asterisms.txt";
        private static final String HYG_CATALOGUE_NAME =
                "/hygdata_v3.csv";

        @Test
        void illegalArgumentIsThrownOnStarCatalogueConstructor() {
            List <Star> test = new ArrayList<Star>();
            test.add(new Star(453, "X-Wing", EquatorialCoordinates.of(1,1),1,
                    1));

            List <Star> listStar2 = new ArrayList<Star>();
            listStar2.add(new Star(453, "X-Wing", EquatorialCoordinates.of(1,1),1,
                    1));

            List<Asterism> asterism = new ArrayList<>();
            asterism.add(new Asterism(listStar2));

            assertThrows(IllegalArgumentException.class, () -> new StarCatalogue(test, asterism));
        }


        @Test
        void starCatalogueInstanceIsImmutableTroughAsterismsOrStars() {
            List <Star> listStar1 = new ArrayList<Star>();
            listStar1.add(new Star(453, "X-Wing", EquatorialCoordinates.of(1,1),1,
                    1));

            List <Star> listStar2 = new ArrayList<Star>();
            listStar2.add(new Star(511, "X-Wing", EquatorialCoordinates.of(1,1),1,
                    1));

            List <Star> listStar3 = new ArrayList<Star>();
            listStar3.add(new Star(456, "X-Wing", EquatorialCoordinates.of(1,1),1,
                    1));

            List<Asterism> asterism = new ArrayList<>();
            asterism.add(new Asterism(listStar1));

            //StarCatalogue test1 = new StarCatalogue(listStar1, asterism);
            StarCatalogue test2 = new StarCatalogue(listStar1, asterism);

            List<Star> testImmut = test2.stars();
            assertThrows(UnsupportedOperationException.class, ()-> testImmut.add(listStar2.get(0)));

            Set<Asterism> testImmutAst = test2.asterisms();
            assertThrows(UnsupportedOperationException.class, ()-> testImmutAst.add(new Asterism(listStar2)));
        }

        @Test
        void starCatalogueIsImmutable() {
            List <Star> listStar1 = new ArrayList<Star>();
            listStar1.add(new Star(453, "X-Wing", EquatorialCoordinates.of(1,1),1,
                    1));

            List<Asterism> asterisms = new ArrayList<>();
            asterisms.add(new Asterism(listStar1));

            StarCatalogue test = new StarCatalogue(listStar1, asterisms);

            listStar1.clear();
            assertTrue(!test.stars().isEmpty());

            asterisms.clear();
            assertTrue(!test.asterisms().isEmpty());
        }


        /* On remarque que si deux asterism n'ont pas la même adresse malgré qu'il contiennent une même lliste d'étoile,
        la méthode ne marche pas... (voir ex: assertequals1 vs le 2ème  */
        @Test
        void asterismIndicesWorks() {

            List <Star> listStarAll = new ArrayList<Star>();

            Star star1 = new Star(453, "X-Wing", EquatorialCoordinates.of(1,1),1,
                    1);
            Star star2 = new Star(511, "Y-Wing", EquatorialCoordinates.of(1,1),1,
                    1);
            Star star3 = new Star(456, "Z-Wing", EquatorialCoordinates.of(1,1),1,
                    1);
            listStarAll.add(star1);
            listStarAll.add(star2);
            listStarAll.add(star3);

            List <Star> listStar1 = new ArrayList<Star>();

            listStar1.add(star1);
            listStar1.add(star3);

            List <Star> listStar2 = new ArrayList<Star>();
            listStar2.add(star2);


            Asterism indice03 = new Asterism(listStar1);
            Asterism indice1 = new Asterism(listStar2);
            List<Asterism> asterisms = new ArrayList<>();
            asterisms.add(indice03);
            asterisms.add(indice1);

            StarCatalogue test = new StarCatalogue(listStarAll, asterisms);
            List<Integer> result1 = new ArrayList<>();
            result1.add(1);
            assertEquals(result1,test.asterismIndices(indice1));

            List<Integer> result2 = new ArrayList<>();
            result2.add(0);
            result2.add(2);
            assertEquals(result2,test.asterismIndices(indice03));

        }

        @Test
        void asterismIndicesthrowsIllegalArgument() {

            Star star1 = new Star(453, "X-Wing", EquatorialCoordinates.of(1,1),1,
                    1);
            Star star2 = new Star(511, "Y-Wing", EquatorialCoordinates.of(1,1),1,
                    1);
            Star star3 = new Star(456, "Z-Wing", EquatorialCoordinates.of(1,1),1,
                    1);
            List <Star> listStar1 = new ArrayList<Star>();
            List<Star> listStar2 = new ArrayList<>();

            listStar2.add(star2);
            listStar1.add(star1);
            listStar1.add(star3);
            Asterism asterism = new Asterism(listStar1);
            Asterism invalidAsterism = new Asterism(listStar2);

            List<Asterism> listAsterism = new ArrayList<>();
            listAsterism.add(asterism);

            StarCatalogue test = new StarCatalogue(listStar1, listAsterism);
            assertThrows(IllegalArgumentException.class, ()->test.asterismIndices(invalidAsterism));

        }

        @Test
        void emptyConstructorOfBuilderWorks() {
            StarCatalogue.Builder test = new StarCatalogue.Builder();
            assertTrue(test.asterisms().isEmpty());
            assertTrue(test.stars().isEmpty());
        }

        @Test
        void addStarOrAsterismFromBuilderWorks() {
            StarCatalogue.Builder test = new StarCatalogue.Builder();
            test.addStar(new Star(456, "Z-Wing", EquatorialCoordinates.of(1,1),1,
                    1));
            assertTrue(!test.stars().isEmpty());

            List <Star> listStar1 = new ArrayList<Star>();
            listStar1.add(new Star(453, "X-Wing", EquatorialCoordinates.of(1,1),1,
                    1));

            test.addAsterism(new Asterism(listStar1));
            assertTrue(!test.asterisms().isEmpty());
        }

        @Test
        void viewStarsOrAsterismsAreUnmodifiable() {
            StarCatalogue.Builder test = new StarCatalogue.Builder();
            List <Star> listStar1 = new ArrayList<Star>();

            Star star1 = new Star(453, "X-Wing", EquatorialCoordinates.of(1,1),1,
                    1);
            test.addStar(star1);

            listStar1.add(star1);
            Asterism asterism = new Asterism(listStar1);

            assertThrows(UnsupportedOperationException.class, ()-> test.stars().clear());
            assertThrows(UnsupportedOperationException.class, ()-> test.asterisms().clear());

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


        @Test
        void builderWorks() {
            assertThrows(IllegalArgumentException.class, () -> {
                StarCatalogue.Builder scb = new StarCatalogue.Builder();
                List<Star> liste1 = new ArrayList<>();
                Star s1 = new Star(0, "name1", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s2 = new Star(0, "name2", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s3 = new Star(0, "name3", EquatorialCoordinates.of(0, 0), 0, 0);
                liste1.add(s1);
                liste1.add(s2);
                liste1.add(s3);
                Asterism a = new Asterism(liste1);
                scb.addStar(s1).addStar(s2).addAsterism(a).build();
            });
            assertDoesNotThrow(() -> {
                StarCatalogue.Builder scb = new StarCatalogue.Builder();
                List<Star> liste1 = new ArrayList<>();
                Star s1 = new Star(0, "name1", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s2 = new Star(0, "name2", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s3 = new Star(0, "name3", EquatorialCoordinates.of(0, 0), 0, 0);
                liste1.add(s1);
                liste1.add(s2);
                liste1.add(s3);
                Asterism a = new Asterism(liste1);
                scb.addStar(s1).addStar(s2).addStar(s3).addAsterism(a).build();
            });
            assertThrows(UnsupportedOperationException.class, () -> {
                StarCatalogue.Builder scb = new StarCatalogue.Builder();
                List<Star> liste1 = new ArrayList<>();
                Star s1 = new Star(0, "name1", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s2 = new Star(0, "name2", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s3 = new Star(0, "name3", EquatorialCoordinates.of(0, 0), 0, 0);
                liste1.add(s1);
                liste1.add(s2);
                liste1.add(s3);
                Asterism a = new Asterism(liste1);
                scb.addStar(s1).addStar(s2).addStar(s3).addAsterism(a);
                scb.asterisms().add(a);
            });
            assertThrows(UnsupportedOperationException.class, () -> {
                StarCatalogue.Builder scb = new StarCatalogue.Builder();
                List<Star> liste1 = new ArrayList<>();
                Star s1 = new Star(0, "name1", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s2 = new Star(0, "name2", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s3 = new Star(0, "name3", EquatorialCoordinates.of(0, 0), 0, 0);
                liste1.add(s1);
                liste1.add(s2);
                liste1.add(s3);
                Asterism a = new Asterism(liste1);
                scb.addStar(s1).addStar(s2).addStar(s3).addAsterism(a);
                scb.stars().add(s3);
            });
            StarCatalogue.Builder scb = new StarCatalogue.Builder();
            List<Star> liste1 = new ArrayList<>();
            Star s1 = new Star(0, "name1", EquatorialCoordinates.of(0, 0), 0, 0);
            Star s2 = new Star(0, "name2", EquatorialCoordinates.of(0, 0), 0, 0);
            Star s3 = new Star(0, "name3", EquatorialCoordinates.of(0, 0), 0, 0);
            liste1.add(s1);
            liste1.add(s2);
            liste1.add(s3);
            Asterism a = new Asterism(liste1);
            scb.addStar(s1).addStar(s2).addStar(s3).addAsterism(a);
            List <Star> scbViewS = scb.stars();
            List <Asterism> scbViewA = scb.asterisms();
            assertTrue(scbViewS.size() == 3);
            scb.addStar(s3);
            assertTrue(scbViewS.size() == 4);
            assertTrue(scbViewA.size() == 1);
            scb.addAsterism(a);
            assertTrue(scbViewA.size() == 2);
            assertTrue(scb.build().asterisms().size() == 1);
            assertTrue(scb.build().stars().size() == 4);
        }
        public static String str = "CoronaVirus !!!";


        @Test
        void starsIsImmutable() {
            assertThrows(UnsupportedOperationException.class, () -> {
                List<Star> liste1 = new ArrayList<>();
                List<Asterism> liste2 = new ArrayList<>();
                StarCatalogue sc = new StarCatalogue(liste1, liste2);
                sc.stars().add(new Star(0, "name", EquatorialCoordinates.of(0, 0), 0, 0));
            });

            List<Star> liste1 = new ArrayList<>();
            List<Asterism> liste2 = new ArrayList<>();
            StarCatalogue sc = new StarCatalogue(liste1, liste2);
            liste1.add(new Star(0, "name", EquatorialCoordinates.of(0, 0), 0, 0));
            assertTrue(sc.stars().isEmpty());
        }

        @Test
        void asterismsIsImmutable() {
            assertThrows(UnsupportedOperationException.class, () -> {
                List<Star> liste1 = new ArrayList<>();
                liste1.add(new Star(0, "name", EquatorialCoordinates.of(0, 0), 0, 0));
                List<Asterism> liste2 = new ArrayList<>();
                StarCatalogue sc = new StarCatalogue(liste1, liste2);
                sc.asterisms().add(new Asterism(liste1));
            });

            List<Star> liste1 = new ArrayList<>();
            liste1.add(new Star(0, "name", EquatorialCoordinates.of(0, 0), 0, 0));
            List<Asterism> liste2 = new ArrayList<>();
            StarCatalogue sc = new StarCatalogue(liste1, liste2);
            liste2.add(new Asterism(liste1));
            assertTrue(sc.asterisms().isEmpty());
        }

        @SuppressWarnings("unused")
        @Test
        void constructorThrowsExceptions() {
            assertThrows(IllegalArgumentException.class, () -> {
                List<Star> liste1 = new ArrayList<>();
                Star s1 = new Star(0, "name1", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s2 = new Star(0, "name2", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s3 = new Star(0, "name3", EquatorialCoordinates.of(0, 0), 0, 0);
                liste1.add(s1);
                liste1.add(s2);
                liste1.add(s3);
                List<Asterism> liste2 = new ArrayList<>();
                liste2.add(new Asterism(liste1));
                liste1.remove(s1);
                StarCatalogue sc = new StarCatalogue(liste1, liste2);
            });
            assertDoesNotThrow(() -> {
                List<Star> liste1 = new ArrayList<>();
                Star s1 = new Star(0, "name1", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s2 = new Star(0, "name2", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s3 = new Star(0, "name3", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s4 = new Star(0, "name4", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s5 = new Star(0, "name5", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s6 = new Star(0, "name6", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s7 = new Star(0, "name7", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s8 = new Star(0, "name8", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s9 = new Star(0, "name9", EquatorialCoordinates.of(0, 0), 0, 0);
                liste1.add(s1);
                liste1.add(s2);
                liste1.add(s3);
                liste1.add(s4);
                liste1.add(s5);
                liste1.add(s6);
                liste1.add(s7);
                liste1.add(s8);
                liste1.add(s9);
                List<Star> listeA1 = new ArrayList<>();
                listeA1.add(s1);
                listeA1.add(s2);
                listeA1.add(s3);
                List<Star> listeA2 = new ArrayList<>();
                listeA2.add(s4);
                listeA2.add(s5);
                listeA2.add(s6);
                List<Star> listeA3 = new ArrayList<>();
                listeA3.add(s7);
                listeA3.add(s8);
                listeA3.add(s9);
                List<Asterism> liste2 = new ArrayList<>();
                liste2.add(new Asterism(listeA1));
                liste2.add(new Asterism(listeA2));
                liste2.add(new Asterism(listeA3));
                StarCatalogue sc = new StarCatalogue(liste1, liste2);
            });
            assertDoesNotThrow(() -> {
                List<Star> liste1 = new ArrayList<>();
                Star s1 = new Star(0, "name1", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s2 = new Star(0, "name2", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s3 = new Star(0, "name3", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s4 = new Star(0, "name4", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s5 = new Star(0, "name5", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s6 = new Star(0, "name6", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s7 = new Star(0, "name7", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s8 = new Star(0, "name8", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s9 = new Star(0, "name9", EquatorialCoordinates.of(0, 0), 0, 0);
                liste1.add(s1);
                liste1.add(s2);
                liste1.add(s3);
                liste1.add(s4);
                liste1.add(s5);
                liste1.add(s6);
                liste1.add(s7);
                liste1.add(s8);
                liste1.add(s9);
                List<Star> listeA1 = new ArrayList<>();
                listeA1.add(s1);
                listeA1.add(s2);
                listeA1.add(s3);
                List<Star> listeA2 = new ArrayList<>();
                listeA2.add(s1);
                listeA2.add(s2);
                listeA2.add(s3);
                List<Star> listeA3 = new ArrayList<>();
                listeA3.add(s5);
                listeA3.add(s7);
                listeA3.add(s9);
                List<Asterism> liste2 = new ArrayList<>();
                liste2.add(new Asterism(listeA1));
                liste2.add(new Asterism(listeA2));
                liste2.add(new Asterism(listeA3));
                StarCatalogue sc = new StarCatalogue(liste1, liste2);
            });
        }

        @Test
        void asterismIndicesWorksA() {
            assertThrows(IllegalArgumentException.class, () -> {
                List<Star> liste1 = new ArrayList<>();
                Star s1 = new Star(0, "name1", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s2 = new Star(0, "name2", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s3 = new Star(0, "name3", EquatorialCoordinates.of(0, 0), 0, 0);
                liste1.add(s1);
                liste1.add(s2);
                liste1.add(s3);
                List<Asterism> liste2 = new ArrayList<>();
                liste2.add(new Asterism(liste1));
                StarCatalogue sc = new StarCatalogue(liste1, liste2);
                liste1.remove(s1);
                Asterism a = new Asterism(liste1);
                sc.asterismIndices(a);
            });
            assertThrows(UnsupportedOperationException.class, () -> {
                List<Star> liste1 = new ArrayList<>();
                Star s1 = new Star(0, "name1", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s2 = new Star(0, "name2", EquatorialCoordinates.of(0, 0), 0, 0);
                Star s3 = new Star(0, "name3", EquatorialCoordinates.of(0, 0), 0, 0);
                liste1.add(s1);
                liste1.add(s2);
                liste1.add(s3);
                List<Asterism> liste2 = new ArrayList<>();
                Asterism a = new Asterism(liste1);
                liste2.add(a);
                StarCatalogue sc = new StarCatalogue(liste1, liste2);
                sc.asterismIndices(a).add(1);
            });

            List<Star> liste1 = new ArrayList<>();
            Star s1 = new Star(0, "name1", EquatorialCoordinates.of(0, 0), 0, 0);
            Star s2 = new Star(0, "name2", EquatorialCoordinates.of(0, 0), 0, 0);
            Star s3 = new Star(0, "name3", EquatorialCoordinates.of(0, 0), 0, 0);
            Star s4 = new Star(0, "name4", EquatorialCoordinates.of(0, 0), 0, 0);
            Star s5 = new Star(0, "name5", EquatorialCoordinates.of(0, 0), 0, 0);
            Star s6 = new Star(0, "name6", EquatorialCoordinates.of(0, 0), 0, 0);
            Star s7 = new Star(0, "name7", EquatorialCoordinates.of(0, 0), 0, 0);
            Star s8 = new Star(0, "name8", EquatorialCoordinates.of(0, 0), 0, 0);
            Star s9 = new Star(0, "name9", EquatorialCoordinates.of(0, 0), 0, 0);
            liste1.add(s1);
            liste1.add(s2);
            liste1.add(s3);
            liste1.add(s4);
            liste1.add(s5);
            liste1.add(s6);
            liste1.add(s7);
            liste1.add(s8);
            liste1.add(s9);
            List<Star> listeA1 = new ArrayList<>();
            listeA1.add(s1);
            listeA1.add(s2);
            listeA1.add(s3);
            List<Star> listeA2 = new ArrayList<>();
            listeA2.add(s4);
            listeA2.add(s5);
            listeA2.add(s6);
            List<Star> listeA3 = new ArrayList<>();
            listeA3.add(s7);
            listeA3.add(s8);
            listeA3.add(s9);
            List<Asterism> liste2 = new ArrayList<>();
            Asterism a1 = new Asterism(listeA1);
            Asterism a2 = new Asterism(listeA2);
            Asterism a3 = new Asterism(listeA3);
            liste2.add(a1);
            liste2.add(a2);
            liste2.add(a3);
            StarCatalogue sc = new StarCatalogue(liste1, liste2);
            assertTrue(sc.asterismIndices(a1).size() == 3);
            assertEquals(0, sc.asterismIndices(a1).get(0));
            assertEquals(1, sc.asterismIndices(a1).get(1));
            assertEquals(2, sc.asterismIndices(a1).get(2));
            assertTrue(sc.asterismIndices(a2).size() == 3);
            assertEquals(3, sc.asterismIndices(a2).get(0));
            assertEquals(4, sc.asterismIndices(a2).get(1));
            assertEquals(5, sc.asterismIndices(a2).get(2));
            assertTrue(sc.asterismIndices(a3).size() == 3);
            assertEquals(6, sc.asterismIndices(a3).get(0));
            assertEquals(7, sc.asterismIndices(a3).get(1));
            assertEquals(8, sc.asterismIndices(a3).get(2));
            liste1.clear();
            s1 = new Star(0, "name1", EquatorialCoordinates.of(0, 0), 0, 0);
            s2 = new Star(0, "name2", EquatorialCoordinates.of(0, 0), 0, 0);
            s3 = new Star(0, "name3", EquatorialCoordinates.of(0, 0), 0, 0);
            s4 = new Star(0, "name4", EquatorialCoordinates.of(0, 0), 0, 0);
            s5 = new Star(0, "name5", EquatorialCoordinates.of(0, 0), 0, 0);
            s6 = new Star(0, "name6", EquatorialCoordinates.of(0, 0), 0, 0);
            s7 = new Star(0, "name7", EquatorialCoordinates.of(0, 0), 0, 0);
            s8 = new Star(0, "name8", EquatorialCoordinates.of(0, 0), 0, 0);
            s9 = new Star(0, "name9", EquatorialCoordinates.of(0, 0), 0, 0);
            liste1.add(s1);
            liste1.add(s2);
            liste1.add(s3);
            liste1.add(s4);
            liste1.add(s5);
            liste1.add(s6);
            liste1.add(s7);
            liste1.add(s8);
            liste1.add(s9);
            listeA1.clear();
            listeA1.add(s1);
            listeA1.add(s2);
            listeA1.add(s3);
            listeA2.clear();
            listeA2.add(s1);
            listeA2.add(s2);
            listeA2.add(s3);
            listeA3.clear();
            listeA3.add(s5);
            listeA3.add(s7);
            listeA3.add(s9);
            liste2.clear();
            a1 = new Asterism(listeA1);
            a2 = new Asterism(listeA2);
            a3 = new Asterism(listeA3);
            liste2.add(a1);
            liste2.add(a2);
            liste2.add(a3);
            sc = new StarCatalogue(liste1, liste2);
            assertTrue(sc.asterismIndices(a1).size() == 3);
            assertEquals(0, sc.asterismIndices(a1).get(0));
            assertEquals(1, sc.asterismIndices(a1).get(1));
            assertEquals(2, sc.asterismIndices(a1).get(2));
            assertTrue(sc.asterismIndices(a2).size() == 3);
            assertEquals(0, sc.asterismIndices(a2).get(0));
            assertEquals(1, sc.asterismIndices(a2).get(1));
            assertEquals(2, sc.asterismIndices(a2).get(2));
            assertTrue(sc.asterismIndices(a3).size() == 3);
            assertEquals(4, sc.asterismIndices(a3).get(0));
            assertEquals(6, sc.asterismIndices(a3).get(1));
            assertEquals(8, sc.asterismIndices(a3).get(2));
        }



    }