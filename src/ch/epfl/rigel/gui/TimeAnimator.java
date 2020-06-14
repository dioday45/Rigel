package ch.epfl.rigel.gui;

import javafx.animation.AnimationTimer;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.ZonedDateTime;

/**
 * The type Time animator.
 *
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
public final class TimeAnimator extends AnimationTimer {

    private final SimpleBooleanProperty running;
    private final SimpleObjectProperty<TimeAccelerator> accelerator;
    private final DateTimeBean dateTimeBean;
    private long lastTime;
    private boolean firstCall;
    private ZonedDateTime firstDay;

    /**
     * Instantiates a new Time animator.
     *
     * @param dateTimeBean the date time bean
     */
    public TimeAnimator(DateTimeBean dateTimeBean) {
        this.dateTimeBean = dateTimeBean;
        this.lastTime = 0;
        firstCall = true;
        accelerator = new SimpleObjectProperty<>(null);
        running = new SimpleBooleanProperty(false);
        firstDay = null;
    }

    /**
     * Accelerator property simple object property.
     *
     * @return : the accelerator property
     */
    public SimpleObjectProperty<TimeAccelerator> acceleratorProperty() {
        return accelerator;
    }

    /**
     * Gets accelerator.
     *
     * @return the accelerator
     */
    public TimeAccelerator getAccelerator() {
        return accelerator.get();
    }

    /**
     * Sets accelerator.
     *
     * @param accelerator the accelerator
     */
    public void setAccelerator(TimeAccelerator accelerator) {
        this.accelerator.set(accelerator);
    }


    /**
     * To know if the program is running
     *
     * @return the read only boolean property running
     */
    public ReadOnlyBooleanProperty runningProperty() {
        return running;
    }

    /**
     * gets the status of the animation
     *
     * @return : true if the animation is running, else false
     */
    public boolean isRunning() {
        return running.get();
    }


    @Override
    public void handle(long l) {
        if (isRunning() && !firstCall) {
            dateTimeBean.setZonedDateTime(getAccelerator().adjust(firstDay, l - lastTime));
        } else if (firstCall) {
            firstCall = false;
            firstDay = dateTimeBean.getZonedDateTime();
            lastTime = l;
        }
    }


    @Override
    public void start() {
        running.set(true);
        super.start();

    }

    @Override
    public void stop() {
        running.set(false);
        firstCall = true;
        super.stop();
    }
}

