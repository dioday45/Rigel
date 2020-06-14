package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * Modelizes how the Sun moves
 *
 */
public enum SunModel implements CelestialObjectModel<Sun> {
    SUN;

    private static final double ECCENTRICITY = 0.016705;
    private static final double LON_SUN_J2010 = Angle.ofDeg(279.557208);
    private static final double LON_SUN_PERIGEE = Angle.ofDeg(283.112438);
    private static final double THETA_ZERO = Angle.ofDeg(0.533128);
    private static final double DIV_ANGULAR_SIZE = (1 - Math.pow(ECCENTRICITY, 2));
    private static final double CST_MEAN_ANO = (Angle.TAU / 365.242191);

    @Override
    public Sun at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {

        double meanAnomaly = Angle.normalizePositive(CST_MEAN_ANO * daysSinceJ2010) + LON_SUN_J2010- LON_SUN_PERIGEE;
        meanAnomaly = Angle.normalizePositive(meanAnomaly);
        double trueAnomaly = meanAnomaly + 2 * ECCENTRICITY * Math.sin(meanAnomaly);

        double lonEclipGeoSun = trueAnomaly + LON_SUN_PERIGEE;
        lonEclipGeoSun = Angle.normalizePositive(lonEclipGeoSun);

        EclipticCoordinates eclipCoorSun = EclipticCoordinates.of(lonEclipGeoSun, 0);
        EquatorialCoordinates equaCoorSun = eclipticToEquatorialConversion.apply(eclipCoorSun);

        double angularSize = THETA_ZERO * ((1 + ECCENTRICITY * Math.cos(trueAnomaly)) / DIV_ANGULAR_SIZE);

        return new Sun(eclipCoorSun, equaCoorSun, (float) angularSize, (float) meanAnomaly);

    }
}
