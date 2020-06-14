package ch.epfl.rigel;

import ch.epfl.rigel.math.Interval;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
public final class Preconditions {
    private Preconditions() {
    }

    ;

    /**
     * check if the boolean condition is true
     * @param isTrue : given argument to check
     * @throws IllegalArgumentException if isTrue is false
     */
    public static void checkArgument(boolean isTrue) {
        if (!isTrue) {
            throw new IllegalArgumentException("The condition is false");
        }
    }

    /**
     * checks if a value is in an interval
     *
     * @param interval : given interval to check if the value is in it.
     * @param value    : given value to check if in the interval
     * @throws IllegalArgumentException if the interval doesn't contain the value
     * @return value if in the interval
     */
    public static double checkInInterval(Interval interval, double value) {
        if (!interval.contains(value)) {
            throw new IllegalArgumentException(String.format("The value %s is not in the interval : %s", value, interval.toString()));
        } else {
            return value;
        }
    }
}
