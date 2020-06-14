package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * Modelizes the system of spherical coordinates
 *
 */
abstract class SphericalCoordinates {

    //angles en radian
    private final double longitude;
    private final double latitude;

    /**
     * constructor of SphericalCoordinates
     * @param lg : longitude
     * @param lt : latitude
     */
    SphericalCoordinates(double lg, double lt){
        this.longitude = lg;
        this.latitude = lt;
    }

    /**
     *
     * @return the longitude
     */
    double lon(){
        return longitude;
    }

    /**
     *
     * @return the longitude in degrees
     */
    double lonDeg(){
        return Angle.toDeg(longitude);
    }

    /**
     *
     * @return the latitude
     */
    double lat(){
        return latitude;
    }

    /**
     *
     * @return the latitude in degrees
     */
    double latDeg(){
        return Angle.toDeg(latitude);
    }


    @Override
    public final boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final int hashCode() {
        throw new UnsupportedOperationException();
    }

}
