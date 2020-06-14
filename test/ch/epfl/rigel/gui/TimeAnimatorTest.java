package ch.epfl.rigel.gui;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
class TimeAnimatorTest {

    @Test
    void isRunning() {
        DateTimeBean bean = new DateTimeBean();
        bean.setZonedDateTime(ZonedDateTime.of(LocalDate.now(), LocalTime.now(), ZoneId.systemDefault()));
        TimeAnimator anim = new TimeAnimator(bean);
        assertFalse(anim.isRunning());
        anim.start();
        assertTrue(anim.isRunning());
        anim.stop();
        assertFalse(anim.isRunning());

    }
}