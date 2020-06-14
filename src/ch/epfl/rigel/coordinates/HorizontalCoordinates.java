package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * Modelizes the system of horizontal coordinates
 *
 */
public final class HorizontalCoordinates extends SphericalCoordinates{

    private static final RightOpenInterval AZ_INTERVAL = RightOpenInterval.of(0, Angle.TAU);
    private static final ClosedInterval ALT_INTERVAL = ClosedInterval.symmetric(Math.PI);
    /**
     * constructor of SphericalCoordinates
     *
     * @param lg : longitude
     * @param lt : latitude
     */
    private HorizontalCoordinates(double lg, double lt) {
        super(lg, lt);
    }

    /**
     * Constructor of HorizontalCoordinates from radians values
     *
     * @param az : azimut in radians
     * @param alt : altitude in radians
     * @throws IllegalArgumentException if az is not on [0, TAU[, or alt in [-TAU/4, TAU/4]
     * @return return the HorizontalCoordinates in radians
     */
    public static HorizontalCoordinates of(double az, double alt){

        Preconditions.checkInInterval(AZ_INTERVAL, az);
        Preconditions.checkInInterval(ALT_INTERVAL, alt);
        return new HorizontalCoordinates(az, alt);

    }

    /**
     * Constructor of HorizontalCoordinates from degree values
     * @param azDeg : azimut in degrees
     * @param altDeg : altitude in degrees
     * @throws IllegalArgumentException if azDeg is not on [0, 360[, or altDeg in [-90, 90]
     * @return returns the HorizontalCoordinates
     */
    public static HorizontalCoordinates ofDeg(double azDeg, double altDeg){

        double az = Preconditions.checkInInterval(AZ_INTERVAL, Angle.ofDeg(azDeg));
        double alt = Preconditions.checkInInterval(ALT_INTERVAL, Angle.ofDeg(altDeg));
        return new HorizontalCoordinates(az, alt);

    }

    /**
     * @return : azimut in radian
     */
    public double az(){
        return super.lon();
    }

    /**
     *
     * @return : azimut in degrees
     */
    public double azDeg(){
        return super.lonDeg();
    }

    /**
     *
     * @return : altitude in radians
     */
    public double alt(){
        return super.lat();
    }

    /**
     *
     * @return : altitude in degrees
     */
    public double altDeg(){
        return super.latDeg();
    }

    /**
     * Tells in which octant is the azimut of the HorizontalCoordinate
     * @param n : String for the north
     * @param e : String for the east
     * @param s : String for the south
     * @param w : String for the west
     * @return : String composed of the given Strings that tells the octant
     */
    public String azOctantName(String n, String e, String s, String w){

        int azOctant = (int)Math.floor((this.azDeg() + 22.5)/45);

        switch (azOctant){
            case 1 :
                return n+e;
            case 2 :
                return e;
            case 3 :
                return s+e;
            case 4 :
                return s;
            case 5 :
                return s+w;
            case 6 :
                return w;
            case 7 :
                return n+w;
            default:
                return n;
        }

    }

    /**
     * return the angular distance between two HorizontalCoordinates
     * @param that : HorizontalCoordinate of which we want to know the distance
     * @return : distance this HorizontalCoordinate and the given coordinate
     */
    public double angularDistanceTo(HorizontalCoordinates that){

        double sinAlt = Math.sin(this.alt()) * Math.sin(that.alt());
        double cosAlt = Math.cos(this.alt()) * Math.cos(that.alt());

        return Math.acos(sinAlt + cosAlt * Math.cos(this.az() - that.az()));
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "(az=%.4f°, alt=%.4f°)", this.azDeg(), this.altDeg());
    }
}
