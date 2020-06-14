package ch.epfl.rigel.astronomy;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
class EpochTest {

    @Test
    void singularDaysUntilTest() {
        assertEquals(2.25, Epoch.J2000.daysUntil(ZonedDateTime.of(LocalDate.of(2000, Month.JANUARY, 3),
                LocalTime.of(18, 0),
                ZoneOffset.UTC)));
        assertEquals(3.75, Epoch.J2010.daysUntil(ZonedDateTime.of(LocalDate.of(2010, Month.JANUARY, 3),
                LocalTime.of(18, 0),
                ZoneOffset.UTC)));
    }

    @Test
    void limitDaysUntilTest(){
        assertEquals(0, Epoch.J2000.daysUntil(ZonedDateTime.of(LocalDate.of(2000, Month.JANUARY, 1),
                LocalTime.of(12, 0),
                ZoneOffset.UTC)));
        assertEquals(0, Epoch.J2010.daysUntil(ZonedDateTime.of(LocalDate.of(2009, Month.DECEMBER, 31),
                LocalTime.of(0, 0),
                ZoneOffset.UTC)));
    }

    @Test
    void singularJulianCenturiesUntilTest() {
        assertEquals(0.000082136, Epoch.J2000.julianCenturiesUntil(ZonedDateTime.of(LocalDate.of(2000,
                Month.JANUARY, 4),
                LocalTime.of(12, 0),
                ZoneOffset.UTC)), .10f);
        assertEquals(1, Epoch.J2000.julianCenturiesUntil(ZonedDateTime.of(LocalDate.of(2100,
                Month.JANUARY, 1),
                LocalTime.of(12, 0),
                ZoneOffset.UTC)), .10f);
        assertEquals(0.000082136, Epoch.J2010.julianCenturiesUntil(ZonedDateTime.of(LocalDate.of(2010,
                Month.JANUARY, 4),
                LocalTime.of(0, 0),
                ZoneOffset.UTC)), .10f);
        assertEquals(1, Epoch.J2010.julianCenturiesUntil(ZonedDateTime.of(LocalDate.of(2111,
                Month.JANUARY, 1),
                LocalTime.of(0, 0),
                ZoneOffset.UTC)), .10f);
    }

    @Test
    void limitJulianCenturiesTest(){
        assertEquals(0, Epoch.J2000.julianCenturiesUntil(ZonedDateTime.of(LocalDate.of(2000,
                Month.JANUARY, 1),
                LocalTime.of(12, 0),
                ZoneOffset.UTC)), .10f);
        assertEquals(0, Epoch.J2010.julianCenturiesUntil(ZonedDateTime.of(LocalDate.of(2010,
                Month.DECEMBER, 31),
                LocalTime.of(0, 0),
                ZoneOffset.UTC)), .10f);
    }

//**********************************************************************************************************************
    @Test
    void daysUntilJ2000Works() {
        double days = Epoch.J2000.daysUntil(ZonedDateTime.of(LocalDate.of(2000, 1, 2),
                LocalTime.of(12,0), ZoneOffset.UTC));
        assertEquals(1,days);

        double days2 = Epoch.J2000.daysUntil(ZonedDateTime.of(LocalDate.of(2000, 1, 2),
                LocalTime.of(0,0), ZoneOffset.UTC));
        assertEquals(0.5,days2);

        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2000, 1, 3),
                LocalTime.of(18, 0),
                ZoneOffset.UTC);
        assertEquals(2.25,Epoch.J2000.daysUntil(d));
    }

    @Test
    void julianCenturyUntilWorks() {

        double julianCenturies2 = Epoch.J2000.julianCenturiesUntil(ZonedDateTime.of(LocalDate.of(2000,
                1, 1), LocalTime.of(12,0), ZoneOffset.UTC).plusDays(7305+36525));
        assertEquals(1.2,julianCenturies2);

        double julianCenturies3 = Epoch.J2000.julianCenturiesUntil(ZonedDateTime.of(LocalDate.of(2000,
                1, 1), LocalTime.of(12,0), ZoneOffset.UTC).plusDays(36525));
        assertEquals(1,julianCenturies3);

        double julianCenturies4 = Epoch.J2000.julianCenturiesUntil(ZonedDateTime.of(LocalDate.of(2000,
                1, 1), LocalTime.of(0,0), ZoneOffset.UTC).minusDays(36525));
        assertEquals(-1.0000136892539357,julianCenturies4, 1e-10);

        double julianCenturies5 = Epoch.J2000.julianCenturiesUntil(ZonedDateTime.of(LocalDate.of(2000,
                1, 1), LocalTime.of(0,0), ZoneOffset.UTC));
        assertEquals(-1.3689E-5,julianCenturies5, 1e-3);

        double exempleBook = Epoch.J2000.julianCenturiesUntil(ZonedDateTime.of(LocalDate.of(1980,
                4, 22), LocalTime.of(14,36,51,67), ZoneOffset.UTC));
        assertEquals(-0.196947,exempleBook,1e-4);
    }

    @Test
    void daysUntilWorks() {
        double days = Epoch.J2000.daysUntil(ZonedDateTime.of(LocalDate.of(2000, Month.JANUARY, 1),
                LocalTime.of(12, 0), ZoneOffset.UTC));
        assertEquals(0, days);
        days = Epoch.J2000.daysUntil(ZonedDateTime.of(LocalDate.of(2000, Month.JANUARY, 2),
                LocalTime.of(12, 0), ZoneOffset.UTC));
        assertEquals(1, days);
        days = Epoch.J2000.daysUntil(ZonedDateTime.of(LocalDate.of(2000, Month.JANUARY, 2),
                LocalTime.of(0, 0), ZoneOffset.UTC));
        assertEquals(0.5, days);
        days = Epoch.J2000.daysUntil(ZonedDateTime.of(LocalDate.of(2000, Month.JANUARY, 3),
                LocalTime.of(18, 0), ZoneOffset.UTC));
        assertEquals(2.25, days);
        days = Epoch.J2010.daysUntil(ZonedDateTime.of(LocalDate.of(2009, Month.DECEMBER, 31),
                LocalTime.of(0, 0), ZoneOffset.UTC));
        assertEquals(0, days);
        days = Epoch.J2010.daysUntil(ZonedDateTime.of(LocalDate.of(2010, Month.JANUARY, 1),
                LocalTime.of(6, 0), ZoneOffset.UTC));
        assertEquals(1.25, days);
        days = Epoch.J2000.daysUntil(ZonedDateTime.of(1980, 4, 22, 0, 0,
                0, 0, ZoneOffset.UTC));
        assertEquals(-7193.5, days);
    }

    @Test
    void julianCenturiesUntilWorks() {
        double centuries = Epoch.J2000.julianCenturiesUntil(ZonedDateTime.of(LocalDate.of(2000,
                Month.JANUARY, 1), LocalTime.of(12, 0), ZoneOffset.UTC));
        assertEquals(0, centuries);
        centuries = Epoch.J2000.julianCenturiesUntil(ZonedDateTime.of(LocalDate.of(2100,
                Month.JANUARY, 1), LocalTime.of(12, 0), ZoneOffset.UTC));
        assertEquals(1, centuries);
        double days = Epoch.J2000.daysUntil(ZonedDateTime.of(LocalDate.of(2000,
                Month.JANUARY, 3), LocalTime.of(18, 0), ZoneOffset.UTC));
        centuries = Epoch.J2000.julianCenturiesUntil(ZonedDateTime.of(LocalDate.of(2000,
                Month.JANUARY, 3), LocalTime.of(18, 0), ZoneOffset.UTC));
        assertEquals(days/36525, centuries);
        days = Epoch.J2010.daysUntil(ZonedDateTime.of(LocalDate.of(2013,
                Month.JANUARY, 3), LocalTime.of(18, 0), ZoneOffset.UTC));
        centuries = Epoch.J2010.julianCenturiesUntil(ZonedDateTime.of(LocalDate.of(2013,
                Month.JANUARY, 3), LocalTime.of(18, 0), ZoneOffset.UTC));
        assertEquals(days/36525, centuries);
        centuries = Epoch.J2000.julianCenturiesUntil(ZonedDateTime.of(1980, 4,
                22, 0, 0, 0, 0, ZoneOffset.UTC));
        assertEquals(-0.196947296, centuries, 10e-10);
    }

    @Test
    void daysUntilWorksB() {
        ZonedDateTime d = ZonedDateTime.of(LocalDate.of(2000, Month.JANUARY, 3),
                LocalTime.of(18, 0), ZoneOffset.UTC);
        assertEquals(2.25, Epoch.J2000.daysUntil(d), 1e-8);
        d = ZonedDateTime.of(LocalDate.of(2000, Month.JANUARY, 1),
                LocalTime.of(12, 0), ZoneOffset.UTC);
        assertEquals(0.0, Epoch.J2000.daysUntil(d), 1e-8);
        d = ZonedDateTime.of(LocalDate.of(2010, Month.JANUARY, 2),
                LocalTime.of(12, 0), ZoneOffset.UTC);
        assertEquals(2.5, Epoch.J2010.daysUntil(d), 1e-8);
    }

    @Test
    void julianCenturiesUntilWorksB() {
        ZonedDateTime d = ZonedDateTime.of(LocalDate.of(2100, Month.JANUARY, 1),
                LocalTime.of(12, 0), ZoneOffset.UTC);
        assertEquals(1, Epoch.J2000.julianCenturiesUntil(d), 1e-8);
        d = ZonedDateTime.of(LocalDate.of(2009, Month.DECEMBER, 31),
                LocalTime.of(0, 0), ZoneOffset.UTC);
        assertEquals(0, Epoch.J2010.julianCenturiesUntil(d), 1e-8);
    }


}