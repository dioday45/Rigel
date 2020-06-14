package ch.epfl.rigel.gui;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.io.UncheckedIOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
class BlackBodyColorTest {

/*    Map<Integer, Color> mapIntColor = BlackBodyColor.mapTempColor;

    @Test
    public void initWorks() throws UncheckedIOException {
        int temp = 0;
        for (Map.Entry<Integer, Color> integerColorEntry : mapIntColor.entrySet()) {

            assertTrue(mapIntColor.containsKey(1000+100*temp));
            temp += 1;
            //System.out.printf("Temp = %s , Couleur = %s%n", integerColorEntry.getKey(), integerColorEntry.getValue());
        }
        assertEquals(391, temp);
    }

    @Test
    public void colorForTemperatureWorks(){
        for(int i = 950; i < 40000; i+=100){
            for(int j = 0; j < 100; j++){
                if(i+j < 1000 || i+j > 40000){
                    int finalI = i;
                    int finalJ = j;
                    assertThrows(IllegalArgumentException.class, () -> {
                        BlackBodyColor.colorForTemperature(finalI + finalJ);
                    });
                }
                else {

                    assertEquals(mapIntColor.get(i+50), BlackBodyColor.colorForTemperature(i+j));

                }
            }
        }
    }*/

}