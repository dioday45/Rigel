package ch.epfl.rigel.gui;

import java.time.Duration;

/**
 * The enum Named time accelerator.
 *
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
public enum NamedTimeAccelerator {

    //all different type of possible accelerator
    TIMES_1("1x", 1),
    TIMES_30("30x", 30),
    TIMES_300("300x", 300),
    TIMES_3000("3000x", 3000),
    DAY("jour", 60, Duration.parse("PT24H")),
    SIDEREAL_DAY("jour sidéral", 60, Duration.parse("PT23H56M4S"));

    private final String name;
    TimeAccelerator accelerator;

    /**
     * constructor for NamedTimeAccelerator with continuous accelerator
     *
     * @param name  : name of the entity
     * @param alpha : factor used to compute the corresponding accelerator
     */
    NamedTimeAccelerator(String name, int alpha) {
        this.name = name;
        this.accelerator = TimeAccelerator.continuous(alpha);
    }

    /**
     * Constructor for NamedTimeAccelerator with discrete accelerator
     *
     * @param name      : name of the entity
     * @param frequency : factor used to compute the corresponding accelerator
     * @param step      : factor used to compute the corresponding accelerator
     */

    NamedTimeAccelerator(String name, int frequency, Duration step) {
        this.name = name;
        this.accelerator = TimeAccelerator.discrete(frequency, step);
    }


    /**
     * getter for the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * getter for the accelerator
     *
     * @return the accelerator
     */
    public TimeAccelerator getAccelerator() {
        return this.accelerator;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
