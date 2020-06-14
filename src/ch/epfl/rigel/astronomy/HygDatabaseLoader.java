package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * The enum Hyg database loader
 *
 */
public enum HygDatabaseLoader implements StarCatalogue.Loader {

    INSTANCE;

    //position in the hydata list
    private static final int HIP = 1;
    private static final int PROPER = 6;
    private static final int MAG = 13;
    private static final int CI = 16;
    private static final int RARAD = 23;
    private static final int DECRAD = 24;
    private static final int BAYER = 27;
    private static final int CON = 29;


    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {
        try (BufferedReader hyg = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.US_ASCII))) {

            //jump the first line of the hygdata
            String[] splitedLine = hyg.readLine().split(",");

            int hip;
            double mag, ci;
            String name, line;

            while ((line = hyg.readLine()) != null) {

                splitedLine = line.split(",");
                EquatorialCoordinates starCoord = EquatorialCoordinates.of(Double.parseDouble(splitedLine[RARAD]), Double.parseDouble(splitedLine[DECRAD]));

                //Hip
                hip = (!splitedLine[HIP].isEmpty()) ? Integer.parseInt(splitedLine[HIP]) : 0;
                //Mag
                mag = (!splitedLine[MAG].isEmpty()) ? Double.parseDouble(splitedLine[MAG]) : 0;
                //Ci
                ci = (!splitedLine[CI].isEmpty()) ? Double.parseDouble(splitedLine[CI]) : 0;
                //Name
                if (!splitedLine[PROPER].isEmpty()) {
                    name = splitedLine[PROPER];
                } else {
                    name = (!splitedLine[BAYER].isEmpty()) ? splitedLine[BAYER] : "?";
                    name += " " +
                            splitedLine[CON];

                }

                builder.addStar(new Star(hip, name, starCoord, (float) mag, (float) ci));

            }


        }
    }


}
