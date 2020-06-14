package ch.epfl.rigel.gui;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
class TimeAcceleratorTest {

    LocalDate day = LocalDate.of(2020, 4, 20);
    LocalTime time = LocalTime.of(21, 00);
    ZoneOffset zone = ZoneOffset.UTC;

    @Test
    void continuous() {

        LocalTime timeEnd = time.plusMinutes(11)
                .plusSeconds(42);
        ZonedDateTime begin = ZonedDateTime.of(day, time, zone);
        ZonedDateTime result = ZonedDateTime.of(day, timeEnd, zone);

        assertEquals(result, TimeAccelerator.continuous(300).adjust(begin, (long) (2.34 * 1e9)));

        ZonedDateTime initialTime = ZonedDateTime.parse("2020-04-17T21:00:00+00:00");
        ZonedDateTime laterTime = TimeAccelerator.continuous(300).adjust(initialTime, (long) (2.34 * 1e9));
        assertEquals(ZonedDateTime.parse("2020-04-17T21:11:42+00:00"), laterTime);
    }


    @Test
    void discrete() {
        ZonedDateTime initialTime = ZonedDateTime.of(day, time, zone);
        ZonedDateTime laterTime = TimeAccelerator.discrete(10, Duration.parse("PT23H56M4S"))
                .adjust(initialTime, (long) (2.34 * 1e9));
        assertEquals(ZonedDateTime.parse("2020-05-13T19:29:32+00:00"), laterTime);
    }
}