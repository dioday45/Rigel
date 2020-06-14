package ch.epfl.rigel.coordinates;

import java.util.Locale;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * Modelizes the system of castesian coordinates
 *
 */

public final class CartesianCoordinates {

    private final double x;
    private final double y;

    /**
     * private constructor
     * @param x : abscissa
     * @param y : ordinate
     */
    private CartesianCoordinates(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor static  of CartesianCoordinates
     * @param x : abscissa
     * @param y : ordinate
     * @return the CartesianCoordinates of abscissa x and ordinate y
     */
    public static CartesianCoordinates of(double x, double y){
        return new CartesianCoordinates(x, y);
    }

    /**
     * getter of x
     * @return x
     */
    public double x(){
        return x;
    }

    /**
     * getter of y
     * @return y
     */
    public double y(){
        return y;
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "Cartesian coordinates : (%s, %s)",this.x(), this.y());
    }

    /**
     * Compute the distance between two cartesian coordinates
     * @param c1 : cartesian coordinate 1
     * @param c2 : cartesian coordinate 2
     * @return the distance between c1 and c2
     */
    public static double distance(CartesianCoordinates c1, CartesianCoordinates c2) {
        return Math.sqrt(Math.pow((c2.x() - c1.x()), 2) + Math.pow((c2.y() - c1.y()), 2));
    }
}
