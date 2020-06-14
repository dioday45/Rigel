package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

import java.util.Arrays;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * class that represents a polynomial function
 *
 */
public final class Polynomial {

    private final double[] polynome;

    /**
     * constructor that initializes an empty array that stores the coefficients
     *
     * @param polynome : desired polynome to be created
     */
    private Polynomial(double[] polynome) {
        this.polynome = polynome;
    }

    /**
     * creates an array that stores the coefficients in decreasing order of the power
     *
     * @param coefficientN : first coefficient
     * @param coefficients : array of the other coefficients
     * @throws IllegalArgumentException if coefficientN is 0
     * @return : desired polynomial function with the given coefficients
     */
    public static Polynomial of(double coefficientN, double... coefficients) {
        Preconditions.checkArgument(coefficientN != 0);
        double[] toReturn = new double[(coefficients.length + 1)];
        toReturn[0] = coefficientN;
        System.arraycopy(coefficients, 0, toReturn, 1, coefficients.length);
        return (new Polynomial(toReturn));
    }

    /**
     * evaluates the polynomial function with the given value
     *
     * @param x : desired value to evaluate the function
     * @return : result of the evaluation
     */
    public double at(double x) {
        int degrePoly = (polynome.length - 1);
        double constante = polynome[degrePoly];
        if (degrePoly == 0) {
            return constante;
        } else {
            double toReturn = polynome[0] * x;
            for (int i = 1; i < degrePoly; i++) {
                toReturn = ((toReturn + polynome[i]) * x);
            }
            return (toReturn + constante);
        }
    }

    /**
     * writes the polynomial function
     *
     * @return : string of the polynomial function
     */
    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder();
        int posLastCoef = (polynome.length - 1);
        for (int i = 0; i <= posLastCoef; i++) {
            if (i != posLastCoef && polynome[i] != 0) {
                if (Math.abs(polynome[i]) != 1) {
                    toReturn.append(polynome[i]);
                    toReturn.append('x');
                } else {
                    if (i == 0 && polynome[i] < 0) {
                        toReturn.append('-');
                    }
                    toReturn.append('x');
                }
                if (posLastCoef - i != 1) {
                    toReturn.append('^');
                    toReturn.append(posLastCoef - i);
                }
                if (polynome[i + 1] > 0) {
                    toReturn.append('+');
                }
            } else if (polynome[i] == 0 && posLastCoef == 0) {
                toReturn.append(polynome[i]);
            } else if (i == posLastCoef && polynome[i] != 0) {
                toReturn.append(polynome[i]);
            }
        }
        return toReturn.toString();
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }
}
