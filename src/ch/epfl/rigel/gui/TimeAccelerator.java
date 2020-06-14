package ch.epfl.rigel.gui;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 * <p>
 * represents the functionnaities of a time accelerator
 */
@FunctionalInterface
public interface TimeAccelerator {


    /**
     * accelerates the time in a continuous way
     *
     * @param accelerator : desired accelerator
     * @return : instance of TimeAccelerator with the desired acceleration
     */
    static TimeAccelerator continuous(int accelerator) {
        return (initialTime, diffRealTime) -> initialTime.plusNanos(accelerator * diffRealTime);
    }

    /**
     * accelerates the time by discrete steps
     *
     * @param frequency : frequency at which the time is accelerated by second
     * @param steps     : duration that is added at each acceleration
     * @return : instance of TimeAccelerator with the desired acceleration
     */
    static TimeAccelerator discrete(long frequency, Duration steps) {
        return (initialTime, diffRealTime) -> {
            Duration adaptedDuration = steps.multipliedBy((frequency * diffRealTime) / 1000000000L);
            return initialTime.plus(adaptedDuration);
        };
    }

    /**
     * how the time should be adjusted
     *
     * @param initialTime  : initial time of the acceleration
     * @param diffRealTime : difference between the real initial time and real actual time
     *                     in nanoseconds
     * @return adjusted time
     */
    ZonedDateTime adjust(ZonedDateTime initialTime, long diffRealTime);


}
