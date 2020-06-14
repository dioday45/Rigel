package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;

import java.util.function.Function;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 * <p>
 * Modelizes a stereographic projection
 */

public final class StereographicProjection implements Function<HorizontalCoordinates, CartesianCoordinates> {

    private final double cosLatCenter;
    private final double sinLatCenter;
    private final double lonCenter;
    private final double latCenter;

    /**
     * Creates the StereographicProjection of given center
     *
     * @param center : HorizontalCoordinate of the center
     */
    public StereographicProjection(HorizontalCoordinates center) {

        cosLatCenter = Math.cos(center.alt());
        sinLatCenter = Math.sin(center.alt());
        lonCenter = center.az();
        latCenter = center.alt();

    }

    /**
     * returns the CartesianCoordinates of the center of the circle corresponding to the
     * projection of the parallel
     *
     * @param hor : HorizontalCoordinates of the parallel
     * @return : center of the circle corresponding to the projection
     */
    public CartesianCoordinates circleCenterForParallel(HorizontalCoordinates hor) {
        double coordY = (cosLatCenter / (Math.sin(hor.alt()) + sinLatCenter));
        return CartesianCoordinates.of(0, coordY);
    }

    /**
     * gives the radius of the circle corresponding to the projection of the parallel
     *
     * @param parallel : HorizontalCoordinates of the parallel
     * @return : radius of the circle correspondig to the projection
     */
    public double circleRadiusForParallel(HorizontalCoordinates parallel) {
        return (Math.cos(parallel.alt()) / (Math.sin(parallel.alt()) + sinLatCenter));
    }

    /**
     * returns the projected diameter of a sphere of apparent diameter of size rad centred in
     * the center of the projection
     *
     * @param rad : apparent diameter of the sphere
     * @return : projected diameter
     */
    public double applyToAngle(double rad) {
        return (2 * Math.tan(rad / 4));
    }

    /**
     * projects the HorizontalCoordinate to a CartesianCoordinate
     *
     * @param azAlt : HorizontalCoordinate to project
     * @return : projected coordinate
     */

    @Override
    public CartesianCoordinates apply(HorizontalCoordinates azAlt) {

        double cosProjLat = Math.cos(azAlt.alt());
        double sinProjLat = Math.sin(azAlt.alt());
        double deltaLon = (azAlt.az() - lonCenter);
        double sinDeltaLon = Math.sin(deltaLon);
        double cosDeltaLon = Math.cos(deltaLon);
        double d = 1 / (1 + sinProjLat * sinLatCenter + cosProjLat * cosLatCenter * cosDeltaLon);

        double x = d * cosProjLat * sinDeltaLon;
        double y = d * (sinProjLat * cosLatCenter - cosProjLat * sinLatCenter * cosDeltaLon);

        return CartesianCoordinates.of(x, y);
    }

    /**
     * gives the HorizontalCoordinate of the CartesianCoordinate
     *
     * @param xy : CartesianCoordinate to be converted
     * @return : converted CartesianCoordinate
     * @throws IllegalArgumentException if converted lon is not on [0, TAU[, or lat in [-TAU/4, TAU/4]
     */
    public HorizontalCoordinates inverseApply(CartesianCoordinates xy) {
        double rho = Math.sqrt(Math.pow(xy.x(), 2) + Math.pow(xy.y(), 2));
        double rhoSquare = Math.pow(rho, 2);
        double sinC = ((2 * rho) / (rhoSquare + 1));
        double cosC = ((1 - rhoSquare) / (rhoSquare + 1));

        if (xy.x() == 0 && xy.y() == 0) {
            return HorizontalCoordinates.of(lonCenter, latCenter);
        } else {
            double lon = Math.atan2(xy.x() * sinC, rho * cosLatCenter * cosC - xy.y() * sinLatCenter * sinC) + lonCenter;
            lon = Angle.normalizePositive(lon);
            double lat = Math.asin(cosC * sinLatCenter + ((xy.y() * sinC * cosLatCenter) / rho));

            return HorizontalCoordinates.of(lon, lat);
        }
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
        return String.format("StereographicProjection{cosLatCenter=%s, sinLatCenter=%s lonCenter=%s}",
                cosLatCenter, sinLatCenter, lonCenter);

    }
}
