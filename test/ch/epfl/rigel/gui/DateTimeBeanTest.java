package ch.epfl.rigel.gui;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
class DateTimeBeanTest {

    LocalTime time = LocalTime.of(12,35);
    LocalDate date = LocalDate.of(1998, 2, 5);
    ZoneId zone = ZoneOffset.UTC;


    @Test
    void getDate() {
        DateTimeBean bean = new DateTimeBean();
        bean.setDate(date);
        assertEquals(bean.getDate(), date);
    }


    @Test
    void getTime() {
        DateTimeBean bean = new DateTimeBean();
        bean.setTime(time);
        assertEquals(bean.getTime(), time);
    }

    @Test
    void getZoneId() {
        DateTimeBean bean = new DateTimeBean();
        bean.setZoneId(zone);
        assertEquals(bean.getZoneId(), zone);
    }


    @Test
    void getZonedDateTime() {
        DateTimeBean bean = new DateTimeBean();
        bean.setZoneId(zone);
        bean.setTime(time);
        bean.setDate(date);
        assertEquals(ZonedDateTime.of(date, time, zone), bean.getZonedDateTime());

    }

    @Test
    void setZonedDateTime() {
        DateTimeBean bean = new DateTimeBean();
        bean.setZonedDateTime(ZonedDateTime.of(date, time, zone));
        assertEquals(ZonedDateTime.of(date, time, zone), bean.getZonedDateTime());
    }
}