package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;

import java.util.List;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 *  Enumerate all the planet models
 *
 */
public enum PlanetModel implements CelestialObjectModel<Planet> {

    /**
     * Mercury planet model.
     */
    MERCURY("Mercure", 0.24085, 75.5671, 77.612, 0.205627,
            0.387098, 7.0051, 48.449, 6.74, -0.42),
    /**
     * Venus planet model.
     */
    VENUS("Vénus", 0.615207, 272.30044, 131.54, 0.006812,
            0.723329, 3.3947, 76.769, 16.92, -4.40),
    /**
     * Earth planet model.
     */
    EARTH("Terre", 0.999996, 99.556772, 103.2055, 0.016671,
            0.999985, 0, 0, 0, 0),
    /**
     * Mars planet model.
     */
    MARS("Mars", 1.880765, 109.09646, 336.217, 0.093348,
            1.523689, 1.8497, 49.632, 9.36, -1.52),
    /**
     * Jupiter planet model.
     */
    JUPITER("Jupiter", 11.857911, 337.917132, 14.6633, 0.048907,
            5.20278, 1.3035, 100.595, 196.74, -9.40),
    /**
     * Saturn planet model.
     */
    SATURN("Saturne", 29.310579, 172.398316, 89.567, 0.053853,
            9.51134, 2.4873, 113.752, 165.60, -8.88),
    /**
     * Uranus planet model.
     */
    URANUS("Uranus", 84.039492, 356.135400, 172.884833, 0.046321,
            19.21814, 0.773059, 73.926961, 65.80, -7.19),
    /**
     * Neptune planet model.
     */
    NEPTUNE("Neptune", 165.84539, 326.895127, 23.07, 0.010483,
            30.1985, 1.7673, 131.879, 62.20, -6.87);

    /**
     * The All.
     */
    public static final List<PlanetModel> ALL = List.of(values());
    private final String frenchName;
    private final double tropicYear;
    private final double lonJ2010;
    private final double lonPerigee;
    private final double eccentricity;
    private final double semiMajorAxis;
    private final double orbitalInclination;
    private final double lonNode;
    private final double angularSize;
    private final double magnitude;

    PlanetModel(String frenchName, double tropicYear, double lonJ2010, double lonPerigee,
                double eccentricity, double semiMajorAxis, double orbitalInclination,
                double lonNode, double angularSize, double magnitude) {

        this.frenchName = frenchName;
        this.tropicYear = tropicYear;
        this.lonJ2010 = Angle.ofDeg(lonJ2010);
        this.lonPerigee = Angle.ofDeg(lonPerigee);
        this.eccentricity = eccentricity;
        this.semiMajorAxis = semiMajorAxis;
        this.orbitalInclination = Angle.ofDeg(orbitalInclination);
        this.lonNode = Angle.ofDeg(lonNode);
        this.angularSize = Angle.ofArcsec(angularSize);
        this.magnitude = magnitude;

    }

    @Override
    public Planet at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {

        double meanAnomaly = meanAnomaly(daysSinceJ2010);
        double earthMeanAnomaly = EARTH.meanAnomaly(daysSinceJ2010);

        double realAnomaly = realAnomaly(meanAnomaly);
        double earthRealAnomaly = EARTH.realAnomaly(earthMeanAnomaly);

        double radiusToSun = radiusToSun(realAnomaly);
        double earthRadiusToSun = EARTH.radiusToSun(earthRealAnomaly);

        double lonHelio = lonHelio(realAnomaly);
        double earthHelio = EARTH.lonHelio(earthRealAnomaly);

        double sinLonHelioLonNode = Math.sin(lonHelio - lonNode);
        double latEclipHelio = Math.asin(sinLonHelioLonNode * Math.sin(orbitalInclination));

        double radiusEclip = radiusToSun * Math.cos(latEclipHelio);
        double lonEclipHelio = Math.atan2(sinLonHelioLonNode * Math.cos(orbitalInclination),
                Math.cos(lonHelio - lonNode)) + lonNode;


        double rSinLonPlanetLonEarth = earthRadiusToSun * Math.sin(lonEclipHelio - earthHelio);
        double lonEclipGeo = (semiMajorAxis < EARTH.semiMajorAxis) ?

                Math.PI + earthHelio + Math.atan2(radiusEclip * Math.sin(earthHelio - lonEclipHelio),
                        earthRadiusToSun - radiusEclip * Math.cos(earthHelio - lonEclipHelio))

                : lonEclipHelio + Math.atan2(rSinLonPlanetLonEarth,
                radiusEclip - earthRadiusToSun * Math.cos(lonEclipHelio - earthHelio));

        lonEclipGeo = Angle.normalizePositive(lonEclipGeo);
        double latEclipGeo = Math.atan((radiusEclip * Math.tan(latEclipHelio) * Math.sin(lonEclipGeo - lonEclipHelio)) /
                (rSinLonPlanetLonEarth));


        EclipticCoordinates planetEquCoor = EclipticCoordinates.of(lonEclipGeo, latEclipGeo);
        EquatorialCoordinates equaCoordtoReturn = eclipticToEquatorialConversion.apply(planetEquCoor);

        double rhoSquare = Math.pow(earthRadiusToSun, 2) + Math.pow(radiusToSun, 2)
                - 2 * earthRadiusToSun * radiusToSun * Math.cos(lonHelio - earthHelio) * Math.cos(latEclipHelio);
        double rho = Math.sqrt(rhoSquare);
        double angularSizeToReturn = angularSize / rho;

        double phase = (1 + Math.cos(lonEclipGeo - lonHelio)) / 2;
        double magnitudeToReturn = magnitude + 5 * Math.log10((radiusToSun * rho) / Math.sqrt(phase));


        return new Planet(frenchName, equaCoordtoReturn, (float) angularSizeToReturn, (float) magnitudeToReturn);

    }

    /**
     * Computation of the mean anomaly
     */
    private double meanAnomaly(double daysSinceJ2010) {
        return Angle.normalizePositive((Angle.TAU / 365.242191) * (daysSinceJ2010 / tropicYear)) + lonJ2010 - lonPerigee;
    }

    /**
     * Computation of the real anomaly
     */
    private double realAnomaly(double meanAnomaly) {
        return Angle.normalizePositive(meanAnomaly + 2 * eccentricity * Math.sin(meanAnomaly));
    }

    /**
     * Computation of the radius
     */
    private double radiusToSun(double realAnomaly) {
        return (semiMajorAxis * (1 - Math.pow(eccentricity, 2))) / (1 + eccentricity * Math.cos(realAnomaly));
    }

    /**
     * Computation of the longitude
     */
    private double lonHelio(double realAnomaly) {
        return realAnomaly + lonPerigee;
    }

    public String getFrenchName() {
        return frenchName;
    }
}
