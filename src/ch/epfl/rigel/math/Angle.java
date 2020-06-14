package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

import java.lang.Math;


/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * class with static method to help manipulate angles (in radian, degree and hour)
 */
public final class Angle {

    public static final double TAU = (2 * Math.PI);
    private static final double SEC_TO_DEG = (1d / (60 * 60));
    private static final double HR_TO_RAD = (TAU / 24);
    private static final double RAD_TO_HR = 1 / HR_TO_RAD;
    private static final double MIN_TO_SEC = 60d;
    private static final RightOpenInterval BASE_60_INTERVAL = RightOpenInterval.of(0,60);
    private static final RightOpenInterval TAU_INTERVAL = RightOpenInterval.of(0, Angle.TAU);

    private Angle() {
    }

    ;

    /**
     * normalize the angle, putting it in the interval : [0, TAU[
     *
     * @param rad : given angle in radian
     * @return : return the angle normalized in radian
     */
    public static double normalizePositive(double rad) {
        return TAU_INTERVAL.reduce(rad);
    }

    /**
     * return angle corresponding to the given seconds
     *
     * @param sec : given seconds
     * @return : angle in radian corresponding to the seconds
     */
    public static double ofArcsec(double sec) {
        return Math.toRadians(sec * SEC_TO_DEG);
    }

    /**
     * return the angle in radian corresponding to the given coordinates
     * in deg, min and sec
     *
     * @param deg : given coordinate of the degrees
     * @param min : given coordinate of the minutes
     * @param sec : given coordinates of the seconds
     * @throws IllegalArgumentException if min or sec not in [0, 60[
     * @return : returns the angle in radian
     */
    public static double ofDMS(int deg, int min, double sec) {

        Preconditions.checkInInterval(BASE_60_INTERVAL, sec);
        Preconditions.checkInInterval(BASE_60_INTERVAL, min);
        Preconditions.checkArgument(deg >= 0);
        return (ofDeg(deg) + ofArcsec(MIN_TO_SEC * min + sec));

    }

    /**
     * conversion from degrees to radians
     *
     * @param deg : given angle in degrees
     * @return : converted angle in radians
     */
    public static double ofDeg(double deg) {
        return Math.toRadians(deg);
    }

    /**
     * conversion from radian to degrees
     *
     * @param rad : given angle in radian
     * @return : converted angle
     */
    public static double toDeg(double rad) {
        return Math.toDegrees(rad);
    }

    /**
     * conversion from hours to radians
     *
     * @param hr : given angle in hours
     * @return : converted angle in radian
     */
    public static double ofHr(double hr) {
        return (hr * HR_TO_RAD);
    }

    /**
     * converts given angle from radians to hours
     *
     * @param rad : given angle in radian
     * @return : converted angle in hours
     */
    public static double toHr(double rad) {
        return (rad * RAD_TO_HR);
    }


}
