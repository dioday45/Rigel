package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 * <p>
 * Modelizes a Star Cattalogue
 */
public final class StarCatalogue {

    private final List<Star> stars;
    private final Map<Asterism, List<Integer>> mapAsterismIndexs;


    /**
     * Instantiates a new Star catalogue, throw an exception
     * if some stars of the list "asterisms" are not in the list "stars"
     *
     * @param stars     the stars
     * @param asterisms the asterisms
     * @throws IllegalArgumentException if some stars of the asterism are not on the list of Stars
     */
    public StarCatalogue(List<Star> stars, List<Asterism> asterisms) {

        Map<Star, Integer> mapStarIndex = new HashMap<>();
        for (int i = 0; i < stars.size(); i++) {
            mapStarIndex.put(stars.get(i), i);
        }

        Map<Asterism, List<Integer>> muableMapAsterismIndexs = new HashMap<>();

        //creates a set with all the stars
        for (Asterism a : asterisms) {
            muableMapAsterismIndexs.put(a, new ArrayList<>());
            for (Star star : a.stars()) {
                Preconditions.checkArgument(mapStarIndex.get(star) != null);
                muableMapAsterismIndexs.get(a).add(mapStarIndex.get(star));
            }


        }

        this.stars = List.copyOf(stars);
        mapAsterismIndexs = Map.copyOf(muableMapAsterismIndexs);


    }

    /**
     * @return list of stars
     */
    public List<Star> stars() {
        return stars;
    }

    /**
     * @return set of asterisms
     */
    public Set<Asterism> asterisms() {
        return Collections.unmodifiableSet(mapAsterismIndexs.keySet());
    }

    /**
     * Asterism indices list. Throws an IllegalArgumentException if the asterism is not on the list
     *
     * @param asterism the asterism
     * @return the list
     */
    public List<Integer> asterismIndices(Asterism asterism) {
        Preconditions.checkArgument(asterisms().contains(asterism));
        return Collections.unmodifiableList(mapAsterismIndexs.get(asterism));

    }

    /**
     * Loader of catalogue
     */
    public interface Loader {
        /**
         * loads the stars and/or asterisms from the inputStream and adds them to the Builder
         *
         * @param inputStream : given stream to load
         * @param builder     : builder of the StarCatalogue
         * @throws IOException the io exception
         */
        public abstract void load(InputStream inputStream, Builder builder) throws IOException;

    }

    /**
     * Builds the StarCatalogue
     */
    public static class Builder {

        private List<Star> buildingStarCat;
        private List<Asterism> buildingAsterismCat;

        /**
         * Constructor by default
         */
        public Builder() {
            buildingStarCat = new ArrayList<>();
            buildingAsterismCat = new ArrayList<>();
        }

        /**
         * adds the star to the building star catalogue
         *
         * @param star : star to add
         * @return : the builder of the StarCatalogue
         */
        public Builder addStar(Star star) {
            buildingStarCat.add(star);
            return this;
        }

        /**
         * @return : return an unmodifiable list of the stars in the building StarCatalogue
         */
        public List<Star> stars() {
            return Collections.unmodifiableList(buildingStarCat);
        }

        /**
         * adds the asterism to the building star catalogue
         *
         * @param asterism : asterism to add
         * @return : the builder of the StarCatalogue
         */
        public Builder addAsterism(Asterism asterism) {
            buildingAsterismCat.add(asterism);
            return this;
        }

        /**
         * Asterisms list.
         *
         * @return :return an unmodifiable list of the asterisms in the building StarCatalogue
         */
        public List<Asterism> asterisms() {
            return Collections.unmodifiableList(buildingAsterismCat);
        }

        /**
         * loads to the building StarCatalogue the stars and/or asterisms it gets from the
         * inputStream
         *
         * @param inputStream : stream to load from
         * @param loader      : loader of the stream
         * @return : the builder of the StarCatalogue
         * @throws IOException the io exception
         */
        public Builder loadFrom(InputStream inputStream, Loader loader) throws IOException {
            loader.load(inputStream, this);
            return this;
        }

        /**
         * Build star catalogue.
         *
         * @return the star catalogue
         */
        public StarCatalogue build() {
            return new StarCatalogue(buildingStarCat, buildingAsterismCat);
        }
    }


}
