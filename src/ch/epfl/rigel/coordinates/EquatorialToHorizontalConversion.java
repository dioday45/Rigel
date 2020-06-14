package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.SiderealTime;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.math.Angle;

import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 *
 * Creates a conversion from Equatorial to Horizontal
 *
 */
public final class EquatorialToHorizontalConversion implements Function<EquatorialCoordinates, HorizontalCoordinates> {

    private final double sideralTime;
    private final double cosLatitude;
    private final double sinLatitude;


    /**
     * Constructor of the conversion from Equatorial to Horizontal
     * @param when : ZonedDateTime of the observation
     * @param where : GeographicCoordinates of the observation
     */
    public EquatorialToHorizontalConversion(ZonedDateTime when, GeographicCoordinates where){
        sideralTime = SiderealTime.local(when, where);
        cosLatitude = Math.cos(where.lat());
        sinLatitude = Math.sin(where.lat());

    }

    /**
     * makes the conversion from EquatorialCoordinates to HorizontalCoordinates
     * @param equatorialCoordinates : EquatorialCoordinates to be converted
     * @return : converted coordinates in HorizontalCoordinates
     */
    @Override
    public HorizontalCoordinates apply(EquatorialCoordinates equatorialCoordinates) {
        double angleHoraire = (sideralTime - equatorialCoordinates.ra());
        double cosDelinaison = Math.cos(equatorialCoordinates.dec());
        double sinDeclinaison = Math.sin(equatorialCoordinates.dec());
        double cosAngleHoraire = Math.cos(angleHoraire);
        double sinAngleHoraire = Math.sin(angleHoraire);

        double hauteur = Math.asin(sinDeclinaison * sinLatitude + cosDelinaison * cosLatitude * cosAngleHoraire);
        double azimut = Math.atan2(-cosDelinaison * cosLatitude * sinAngleHoraire,
                sinDeclinaison - sinLatitude * Math.sin(hauteur));
        azimut = Angle.normalizePositive(azimut);

        return HorizontalCoordinates.of(azimut, hauteur);
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }
}
