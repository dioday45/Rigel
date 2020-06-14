package ch.epfl.rigel.gui;


import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Black body color.
 *
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
public final class BlackBodyColor {

    private final static Map<Integer, Color> mapTempColor = initialisation();
    private final static ClosedInterval colorInterval = ClosedInterval.of(1000, 40000);

    private BlackBodyColor() {
    }

    //initialisation of the map temperature - color
    private static Map<Integer, Color> initialisation() {

        Map<Integer, Color> initMapTempColor = new HashMap<>();

        try (BufferedReader buffered = new BufferedReader(new InputStreamReader(
                BlackBodyColor.class.getResourceAsStream("/bbr_color.txt"), StandardCharsets.US_ASCII))) {

            String b, intToPut, correspondantColor;

            while ((b = buffered.readLine()) != null) {
                if (b.charAt(0) == '#' || b.charAt(11) == '2') {
                    continue;
                }
                intToPut = b.substring(1, 6).trim();
                correspondantColor = b.substring(80, 87);
                initMapTempColor.put(Integer.parseInt(intToPut), Color.valueOf(correspondantColor));
            }

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return initMapTempColor;

    }

    /**
     * returns the Color of the temperature rounded to the closest multiple of 100
     *
     * @param temperature : temperature in Kelvin
     * @return : Color of the temperature
     */
    public static Color colorForTemperature(int temperature) {
        Preconditions.checkInInterval(colorInterval, temperature);
        return mapTempColor.get((int) Math.round(temperature / 100d) * 100);

    }


}
