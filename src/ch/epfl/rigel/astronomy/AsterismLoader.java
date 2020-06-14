package ch.epfl.rigel.astronomy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * creates a loader for the Asterisms
 */
public enum AsterismLoader implements StarCatalogue.Loader {

    INSTANCE;

    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.US_ASCII))) {

            //Links hipparcosId to the Star
            Map<Integer, Star> hipStar = new HashMap<>();
            for (Star star : builder.stars()) {
                hipStar.put(star.hipparcosId(), star);
            }

            String b = "";
            List<Star> asterismToAdd = new ArrayList<>();

            while ((b = reader.readLine()) != null) {

                String[] starsHip = b.split(",");

                for (String s : starsHip) {

                    int starNumber = Integer.parseInt(s);
                    asterismToAdd.add(hipStar.get(starNumber));

                }
                builder.addAsterism(new Asterism(asterismToAdd));
                asterismToAdd.clear();

            }
        }
    }
}
