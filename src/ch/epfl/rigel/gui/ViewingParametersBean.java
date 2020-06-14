package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;


/**
 * The type Viewing parameters bean.
 *
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
public final class ViewingParametersBean {

    private final SimpleDoubleProperty fieldOfViewDeg;
    private final SimpleObjectProperty<HorizontalCoordinates> center;

    /**
     * Constructor of the viewing parameters bean
     */
    public ViewingParametersBean() {
        fieldOfViewDeg = new SimpleDoubleProperty();
        center = new SimpleObjectProperty<>();
    }

    /**
     * Gets field of view deg.
     *
     * @return value of the field of view
     */
    public double getFieldOfViewDeg() {
        return fieldOfViewDeg.get();
    }

    /**
     * Sets field of view deg.
     *
     * @param fieldOfViewDeg : new value of the field of view
     */
    public void setFieldOfViewDeg(double fieldOfViewDeg) {
        this.fieldOfViewDeg.set(fieldOfViewDeg);
    }

    /**
     * Field of view deg property simple double property.
     *
     * @return property of the field of view
     */
    public SimpleDoubleProperty fieldOfViewDegProperty() {
        return fieldOfViewDeg;
    }

    /**
     * Gets center.
     *
     * @return the value of the center of the view
     */
    public HorizontalCoordinates getCenter() {
        return center.get();
    }

    /**
     * Sets center.
     *
     * @param center new value of the center
     */
    public void setCenter(HorizontalCoordinates center) {
        this.center.set(center);
    }

    /**
     * Center property simple object property.
     *
     * @return property of the center of the field of view
     */
    public SimpleObjectProperty<HorizontalCoordinates> centerProperty() {
        return center;
    }
}
