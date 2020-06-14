package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableObjectValue;


/**
 * The type Observer location bean.
 *
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
public final class ObserverLocationBean {
    private final SimpleDoubleProperty lonDeg;
    private final SimpleDoubleProperty latDeg;
    private final ObservableObjectValue<GeographicCoordinates> coordinates;

    /**
     * Instantiates a new Observer location bean.
     */
    public ObserverLocationBean() {
        lonDeg = new SimpleDoubleProperty();
        latDeg = new SimpleDoubleProperty();
        coordinates = Bindings.createObjectBinding(() ->
                GeographicCoordinates.ofDeg(lonDeg.doubleValue(), latDeg.doubleValue()), lonDeg, latDeg);
    }

    /**
     * Gets lon deg.
     *
     * @return the lon deg
     */
    public double getLonDeg() {
        return latDeg.get();
    }

    /**
     * Set lon deg.
     *
     * @param lonDeg the lon deg
     */
    public void setLonDeg(double lonDeg) {
        this.lonDeg.setValue(lonDeg);
    }

    /**
     * getter for the property lonDeg.
     *
     * @return the simple double property lonDeg
     */
    public SimpleDoubleProperty lonDegProperty() {
        return lonDeg;
    }

    /**
     * Gets lat deg.
     *
     * @return the lat deg
     */
    public double getLatDeg() {
        return latDeg.get();
    }

    /**
     * Set lat deg.
     *
     * @param latDeg the lat deg
     */
    public void setLatDeg(double latDeg) {
        this.latDeg.setValue(latDeg);
    }

    /**
     * getter for the property latDeg.
     *
     * @return the simple double property latDeg
     */
    public SimpleDoubleProperty latDegProperty() {
        return latDeg;
    }

    /**
     * Gets coordinates.
     *
     * @return the coordinates
     */
    public GeographicCoordinates getCoordinates() {
        return coordinates.get();
    }

    /**
     * Sets coordinates.
     *
     * @param coordinates the coordinates
     */
    public void setCoordinates(GeographicCoordinates coordinates) {
        this.setLatDeg(coordinates.latDeg());
        this.setLonDeg(coordinates.lonDeg());
    }

    /**
     * gets the observable object of the coordinates
     *
     * @return the observable object of the coordinates
     */
    public ObservableObjectValue<GeographicCoordinates> coordinatesProperty() {
        return coordinates;
    }
}
