package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * AsterismTest
 *
 * @author Robin Jaccard (310682)
 * @author Carl Schütz (289080)
 */
public class AsterismTest {

    private static List<Star> newModifiableStarList(String... starNames) {
        var eqPos = EquatorialCoordinates.of(0, 0);
        var stars = new ArrayList<Star>();
        for (var starName : starNames) {
            stars.add(new Star(stars.size() + 1, starName, eqPos, 0, 0));
        }
        return stars;
    }
    @Test
    void constructorCopiesStarList() {
        var l = newModifiableStarList("Rigel");
        var s = l.get(0);
        var a = new Asterism(l);
        l.clear();
        assertEquals(1, a.stars().size());
        assertEquals(s, a.stars().get(0));
    }

    @Test
    void accessorDoesNotAllowEncapsulationViolation() {
        var a = new Asterism(newModifiableStarList("Rigel", "Aldebaran", "Sirius"));
        try {
            a.stars().clear();
        } catch (UnsupportedOperationException e) {
            // If UOE is thrown, the list is unmodifiable, which is correct.
        }
        assertEquals(3, a.stars().size());
    }

    @Test
    void illegalArgumentIsThrownOnAsterismConstructor() {
        assertThrows(IllegalArgumentException.class, ()-> new Asterism(new ArrayList<Star>()));
    }

    @Test
    void starsWorks() {
        List<Star> listOfStars = new ArrayList<Star>();

        listOfStars.add(new Star(1,"etoile des neiges",
                EquatorialCoordinates.of(1,1),1,1));

        listOfStars.add(new Star(2,"star wars",
                EquatorialCoordinates.of(1,1),1,1));
        Asterism asterismTest = new Asterism(listOfStars);

        assertEquals("[etoile des neiges, star wars]", asterismTest.stars().toString());
    }



        @SuppressWarnings("unused")
        @Test
        void constructorThrowsIAE() {
            assertThrows(IllegalArgumentException.class, () -> {
                List<Star> liste = new ArrayList<>();
                Asterism a = new Asterism(liste);
            });
            assertDoesNotThrow(() -> {
                List<Star> liste = new ArrayList<>();
                Star s = new Star(0, "Rigel", EquatorialCoordinates.of(0, 0), -50f,
                        -0.5f);
                liste.add(s);
                Asterism a = new Asterism(liste);
            });
        }

        @Test
        void starsIsImmutable() {
            assertThrows(Exception.class, () -> {
                List<Star> liste = new ArrayList<>();
                Star s = new Star(0, "Rigel", EquatorialCoordinates.of(0, 0), -50f,
                        -0.5f);
                liste.add(s);
                Asterism a = new Asterism(liste);
                a.stars().clear();
            });

            List<Star> liste = new ArrayList<>();
            Star s = new Star(0, "Rigel", EquatorialCoordinates.of(0, 0), -50f,
                    -0.5f);
            liste.add(s);
            Asterism a = new Asterism(liste);
            liste.clear();
            assertTrue(!a.stars().isEmpty());


        }




}
