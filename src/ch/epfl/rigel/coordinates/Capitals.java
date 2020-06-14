package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 */
public final class Capitals {

    private final static Map<String, String[]> MAP_CAPITAL_COORD = initialisation();
    private final static int GET_LON_INDEX = 0;
    private final static int GET_LAT_INDEX = 1;
    private final static int NBR_OF_ELEMENTS = 2;
    private final static int LAT_INDEX = 2;
    private final static int LON_INDEX = 3;
    private final static int CAPITAL_NAME_INDEX = 1;
    private final static int COUNTRY_CODE_INDEX = 4;

    /**
     * Constructs the map from the capital to the String list of his geographic coordinates
     * @return map from capital to coordinates
     */
    private static Map<String, String[]> initialisation(){

        Map<String, String[]> initList = new TreeMap<>();
        try(InputStream inputCapitals = Capitals.class.getResourceAsStream("/concap.csv");
            BufferedReader capitals = new BufferedReader(
                new InputStreamReader(inputCapitals, StandardCharsets.US_ASCII))){

            String[] splitedLines = capitals.readLine().split(",");
            String line, toSave;
            while((line = capitals.readLine()) != null){
                String[] toAdd = new String[NBR_OF_ELEMENTS];
                splitedLines = line.split(",");
                toSave = splitedLines[CAPITAL_NAME_INDEX] + ", " + splitedLines[COUNTRY_CODE_INDEX];
                toAdd[GET_LON_INDEX] = splitedLines[LON_INDEX].replace('.', ',');
                toAdd[GET_LAT_INDEX] = splitedLines[LAT_INDEX].replace('.', ',');
                initList.put(toSave, toAdd);
            }
        }catch (IOException e){
            System.out.println("error loading capitals");
        }
        return initList;
    }


    /**
     * gets the set of the capitals in the alphabetic order
     * @return set of the capitals
     */
    public static Set<String> getCapitals() {
        return Collections.unmodifiableSet(MAP_CAPITAL_COORD.keySet());
    }

    /**
     * gets the geographic longitude of the capital
     * @param capital : capital of which we want the longitude
     * @return string of the geographic longitude
     */
    public static String getLonForCapital(String capital){
        Preconditions.checkArgument(MAP_CAPITAL_COORD.get(capital) != null);
        return MAP_CAPITAL_COORD.get(capital)[GET_LON_INDEX];
    }

    /**
     * gets the geographic latitude of the capital
     * @param capital : capital of which we want the longitude
     * @return string of the geographic latitude
     */
    public static String getLatForCapital(String capital){
        Preconditions.checkArgument(MAP_CAPITAL_COORD.get(capital) != null);
        return MAP_CAPITAL_COORD.get(capital)[GET_LAT_INDEX];
    }

}
