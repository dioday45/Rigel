package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */

class CartesianCoordinatesTest {

    //others
    @Test
    void ofAndGettersWork() {
        double x = 5;
        double y = -6.454;
        var cart = CartesianCoordinates.of(x, y);
        assertEquals(x, cart.x());
        assertEquals(y, cart.y());
        x = 89.45;
        y = 0;
        cart = CartesianCoordinates.of(x, y);
        assertEquals(x, cart.x());
        assertEquals(y, cart.y());
    }

    @Test
    void equalsThrowsUOE() {
        assertThrows(UnsupportedOperationException.class, () -> {
            var coordinates = CartesianCoordinates.of(0, 0);
            coordinates.equals(coordinates);
        });
    }

    @Test
    void hashCodeThrowsUOE() {
        assertThrows(UnsupportedOperationException.class, () -> {
            CartesianCoordinates.of(0, 0).hashCode();
        });
    }

}