package ch.epfl.rigel.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * The type Date time bean.
 *
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 * modelizes an observable and modifiable representation of a data and time
 */
public final class DateTimeBean {

    private final ObjectProperty<LocalDate> localDateProperty;
    private final ObjectProperty<LocalTime> localTimeProperty;
    private final ObjectProperty<ZoneId> zoneIdProperty;


    /**
     * constructor of the DataTimeBean
     */
    public DateTimeBean() {
        localDateProperty = new SimpleObjectProperty<>(null);
        localTimeProperty = new SimpleObjectProperty<>(null);
        zoneIdProperty = new SimpleObjectProperty<>(null);
    }

    /**
     * Gets local date property.
     *
     * @return : the date property
     */
    public ObjectProperty<LocalDate> getLocalDateProperty() {
        return localDateProperty;
    }

    /**
     * Gets date.
     *
     * @return : the date
     */
    public LocalDate getDate() {
        return localDateProperty.get();
    }

    /**
     * changes the date to the given date
     *
     * @param date : value of the new date
     */
    public void setDate(LocalDate date) {
        localDateProperty.setValue(date);
    }

    /**
     * Gets local time property.
     *
     * @return : the date property
     */
    public ObjectProperty<LocalTime> getLocalTimeProperty() {
        return localTimeProperty;
    }

    /**
     * Gets time.
     *
     * @return : the time
     */
    public LocalTime getTime() {
        return localTimeProperty.get();
    }

    /**
     * sets the time to the give value
     *
     * @param time : value of the new time
     */
    public void setTime(LocalTime time) {
        localTimeProperty.setValue(time);
    }

    /**
     * Gets zone id property.
     *
     * @return : the zoneId property
     */
    public ObjectProperty<ZoneId> getZoneIdProperty() {
        return zoneIdProperty;
    }

    /**
     * Gets zone id.
     *
     * @return : the zoneId
     */
    public ZoneId getZoneId() {
        return zoneIdProperty.get();
    }

    /**
     * sets the zoneId to the given value
     *
     * @param zoneId : value of the new zoneId
     */
    public void setZoneId(ZoneId zoneId) {
        zoneIdProperty.setValue(zoneId);
    }

    /**
     * transforms the date, time and zoneId to an instance of ZonedDateTime
     *
     * @return : instance of ZonedDateTime from the date, time and zoneId
     */
    public ZonedDateTime getZonedDateTime() {
        return ZonedDateTime.of(getDate(), getTime(), getZoneId());
    }

    /**
     * sets the moment of observation to the given ZonedDateTime
     *
     * @param zonedDateTime : desired moment of observation
     */
    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        setDate(zonedDateTime.toLocalDate());
        setTime(zonedDateTime.toLocalTime());
        setZoneId(zonedDateTime.getZone());
    }
}
