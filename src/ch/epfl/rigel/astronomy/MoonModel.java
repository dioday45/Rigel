package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * Modelizes the movement of the moon
 *
 */
public enum MoonModel implements CelestialObjectModel<Moon> {

    MOON;

    private static final double MEAN_LONGITUDE = Angle.ofDeg(91.929336);
    private static final double MEAN_LONGITUDE_PERIGEE = Angle.ofDeg(130.143076);
    private static final double NODE_LONGITUDE = Angle.ofDeg(291.682547);
    private static final double ORBIT_INCLINAISON = Angle.ofDeg(5.145396);
    private static final double EXCENTRICITY = 0.0549;

    private static final double CST_FOR_ORB_LON = Angle.ofDeg(13.1763966);
    private static final double CST_FOR_MEAN_ANO = Angle.ofDeg(0.1114041);
    private static final double CST_FOR_EV = Angle.ofDeg(1.2739);
    private static final double CST_FOR_AE = Angle.ofDeg(0.1858);
    private static final double CST_FOR_A3 = Angle.ofDeg(0.37);
    private static final double CST_FOR_EC = Angle.ofDeg(6.2886);
    private static final double CST_FOR_A4 = Angle.ofDeg(0.214);
    private static final double CST_FOR_V = Angle.ofDeg(0.6583);
    private static final double CST_FOR_ECLIP_LON = Angle.ofDeg(0.0529539);
    private static final double CST_FOR_CORR_ECLIP_LON = Angle.ofDeg(0.16);
    private static final double CST_FOR_ANG_SIZE = Angle.ofDeg(0.5181);



    @Override
    public Moon at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {

        Sun sun = SunModel.SUN.at(daysSinceJ2010, eclipticToEquatorialConversion);
        double sunMean = sun.meanAnomaly();
        double sunEclipLon = sun.eclipticPos().lon();


        //Computation of real Orbital Longitude
        double orbitalLongitude = (CST_FOR_ORB_LON * daysSinceJ2010) + MEAN_LONGITUDE;
        orbitalLongitude = Angle.normalizePositive(orbitalLongitude);

        double meanAnomaly = orbitalLongitude - (CST_FOR_MEAN_ANO * daysSinceJ2010) - MEAN_LONGITUDE_PERIGEE;
        meanAnomaly = Angle.normalizePositive(meanAnomaly);

        double correctionEv = CST_FOR_EV  * Math.sin(2 * (orbitalLongitude - sunEclipLon) - meanAnomaly);
        double sinSunMean = Math.sin(sunMean);
        double correctionAe = CST_FOR_AE * sinSunMean;
        double correctionA3 = CST_FOR_A3 * sinSunMean;


        double correctedAnomaly = meanAnomaly + correctionEv - correctionAe - correctionA3;


        double correctionEc = CST_FOR_EC * Math.sin(correctedAnomaly);
        double correctionA4 = CST_FOR_A4 * Math.sin(2 * correctedAnomaly);
        double correctedOrbitalLong = orbitalLongitude + correctionEv + correctionEc - correctionAe + correctionA4;
        double correctionV = CST_FOR_V * Math.sin(2 * (correctedOrbitalLong - sunEclipLon));
        double realOrbitalLongitude = correctedOrbitalLong + correctionV;

        //Computation of moon ecliptic coordinates and convert to equatorial coordinates
        EclipticCoordinates moonEclipticCoordinates = eclipticCoord(daysSinceJ2010, sun, realOrbitalLongitude);
        EquatorialCoordinates moonEquatorialCoordinates = eclipticToEquatorialConversion.apply(moonEclipticCoordinates);

        //Computation of moon phase
        double moonPhase = (1 - Math.cos(realOrbitalLongitude - sunEclipLon)) / 2;

        //computation of angularSize
        double p = (1 - (Math.pow(EXCENTRICITY , 2))) / (1 + EXCENTRICITY  * Math.cos(correctedAnomaly + correctionEc));
        double moonAngularSize = CST_FOR_ANG_SIZE / p;

        //Instantiation of the moon to return
        return new Moon(moonEquatorialCoordinates, (float) moonAngularSize, 0, (float) moonPhase);


    }

    /**
     * Computation of the ecliptic coordinates
     */
    private EclipticCoordinates eclipticCoord(double daysSinceJ2010, Sun sun, double realOrbitalLongitude) {

        double meanLongitude = NODE_LONGITUDE - (CST_FOR_ECLIP_LON * daysSinceJ2010);
        meanLongitude = Angle.normalizePositive(meanLongitude);

        double correctedLongitude = meanLongitude - (CST_FOR_CORR_ECLIP_LON * Math.sin(sun.meanAnomaly()));

        double sinOrbLonCorrLon = Math.sin(realOrbitalLongitude - correctedLongitude);

        double longitude = Math.atan2((sinOrbLonCorrLon  * Math.cos(ORBIT_INCLINAISON))
                , (Math.cos(realOrbitalLongitude - correctedLongitude)));
        longitude = Angle.normalizePositive(longitude) + correctedLongitude;
        longitude = Angle.normalizePositive(longitude);

        double latitude = Math.asin(sinOrbLonCorrLon  * Math.sin(ORBIT_INCLINAISON));

        return EclipticCoordinates.of(longitude, latitude);
    }

}
