package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.*;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;

/**
 * The type Observed sky.
 *
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
public final class ObservedSky {
    private final Map<CelestialObject, CartesianCoordinates> mapCelestialObject;
    private final double[] cartCoordPlanets;
    private final double[] cartCoordStars;

    private final Sun sun;
    private final Moon moon;
    private final List<Planet> planets;
    private final StarCatalogue starsCatalogue;

    /**
     * Instantiates a new Observed sky.
     *
     * @param time          the time
     * @param coordinates   the coordinates
     * @param projection    the projection
     * @param starCatalogue the star catalogue
     */
    public ObservedSky(ZonedDateTime time, GeographicCoordinates coordinates, StereographicProjection projection, StarCatalogue starCatalogue) {


        mapCelestialObject = new HashMap<>();

        //Constant
        double daysSinceJ2010 = Epoch.J2010.daysUntil(time);
        EclipticToEquatorialConversion Econv = new EclipticToEquatorialConversion(time);
        EquatorialToHorizontalConversion Hconv = new EquatorialToHorizontalConversion(time, coordinates);
        Function<EquatorialCoordinates, CartesianCoordinates> horToCart = Hconv.andThen(projection);

        //SUN

        sun = SunModel.SUN.at(daysSinceJ2010, Econv);
        CartesianCoordinates SunCcoord = horToCart.apply(sun.equatorialPos());

        mapCelestialObject.put(sun, SunCcoord);

        //MOON
        moon = MoonModel.MOON.at(daysSinceJ2010, Econv);
        CartesianCoordinates MoonCcoord = horToCart.apply(moon.equatorialPos());

        mapCelestialObject.put(moon, MoonCcoord);

        //PLANET
        planets = new ArrayList<>();
        cartCoordPlanets = new double[14];

        int index = 0;
        for (PlanetModel planetModel : PlanetModel.ALL) {
            if (planetModel != PlanetModel.EARTH) {
                Planet planet = planetModel.at(daysSinceJ2010, Econv);
                CartesianCoordinates Ccoord = horToCart.apply(planet.equatorialPos());
                mapCelestialObject.put(planet, Ccoord);
                planets.add(planet);
                cartCoordPlanets[index] = Ccoord.x();
                cartCoordPlanets[index + 1] = Ccoord.y();
                index += 2;
            }
        }

        //Stars
        this.starsCatalogue = starCatalogue;
        List<Star> stars = starsCatalogue.stars();

        cartCoordStars = new double[stars.size() * 2];
        index = 0;
        for (Star s : stars) {
            CartesianCoordinates sCoord = horToCart.apply(s.equatorialPos());
            mapCelestialObject.put(s, sCoord);
            cartCoordStars[index] = sCoord.x();
            cartCoordStars[index + 1] = sCoord.y();
            index += 2;
        }
    }

    /**
     * Get sun sun.
     *
     * @return the sun
     */
    public Sun getSun() {
        return sun;
    }

    /**
     * Get sun position cartesian coordinates.
     *
     * @return the cartesian coordinates
     */
    public CartesianCoordinates getSunPosition() {
        return mapCelestialObject.get(sun);
    }

    /**
     * Get moon moon.
     *
     * @return the moon
     */
    public Moon getMoon() {
        return moon;
    }

    /**
     * Get moon position cartesian coordinates.
     *
     * @return the cartesian coordinates
     */
    public CartesianCoordinates getMoonPosition() {
        return mapCelestialObject.get(moon);
    }

    /**
     * Get planets list.
     *
     * @return the list
     */
    public List<Planet> getPlanets() {
        return Collections.unmodifiableList(planets);
    }

    /**
     * Get planets position double [ ].
     *
     * @return the double [ ]
     */
    public double[] getPlanetsPosition() {
        return Arrays.copyOf(cartCoordPlanets, cartCoordPlanets.length);
    }

    /**
     * Get stars list.
     *
     * @return the list
     */
    public List<Star> getStars() {
        return starsCatalogue.stars();
    }

    /**
     * Get stars position double [ ].
     *
     * @return the double [ ]
     */
    public double[] getStarsPosition() {
        return Arrays.copyOf(cartCoordStars, cartCoordStars.length);
    }

    /**
     * Gets asterisms.
     *
     * @return the asterisms
     */
    public Set<Asterism> getAsterisms() {
        return starsCatalogue.asterisms();
    }

    /**
     * Gets star index of asterism.
     *
     * @param asterism the asterism
     * @return the star index of asterism
     */
    public List<Integer> getStarIndexOfAsterism(Asterism asterism) {
        return starsCatalogue.asterismIndices(asterism);
    }


    /**
     * Object closest to optional.
     *
     * @param coordinates the coordinates
     * @param maxDist     the max dist
     * @return the optional
     */
    public Optional<CelestialObject> objectClosestTo(CartesianCoordinates coordinates, double maxDist) {
        CelestialObject closestObject = null;
        for (Map.Entry<CelestialObject, CartesianCoordinates> map : mapCelestialObject.entrySet()) {
            double distance = CartesianCoordinates.distance(coordinates, map.getValue());
            if (distance < maxDist) {
                maxDist = distance;
                closestObject = map.getKey();
            }
        }

        return (closestObject != null) ? Optional.of(closestObject) : Optional.empty();

    }
}
