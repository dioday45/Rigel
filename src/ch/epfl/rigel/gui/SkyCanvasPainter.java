package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.Asterism;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.Planet;
import ch.epfl.rigel.astronomy.Star;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The type Sky canvas painter.
 *
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453) <p> Class able to draw the sky on a canvas
 */
public final class SkyCanvasPainter {
    private final static ClosedInterval MAGNITUDE_INTERVAL = ClosedInterval.of(-2, 5);

    private final Canvas canvas;
    private final GraphicsContext ctx;

    /**
     * Instantiates a new Sky canvas painter.
     *
     * @param canvas the canvas
     */
    public SkyCanvasPainter(Canvas canvas) {
        this.canvas = canvas;
        ctx = canvas.getGraphicsContext2D();

    }

    /**
     * Clear the canvas
     */
    public void clear() {
        ctx.setFill(Color.BLACK);
        ctx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }


    /**
     * Draw the sun.
     *
     * @param sky           the sky
     * @param projection    the projection
     * @param planeToCanvas the plane to canvas
     * @return the sky canvas painter
     */
    public SkyCanvasPainter drawSun(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas) {
        Point2D sun = planeToCanvas.transform(sky.getSunPosition().x(), sky.getSunPosition().y());
        double size = projection.applyToAngle(sky.getSun().angularSize());
        size = planeToCanvas.deltaTransform(size, 0).magnitude();

        ctx.setFill(Color.YELLOW.deriveColor(1, 1, 1, 0.25));
        ctx.fillOval(sun.getX() - (size * 2.2) / 2, sun.getY() - (size * 2.2) / 2, size * 2.2, size * 2.2);
        ctx.setFill(Color.YELLOW);
        ctx.fillOval(sun.getX() - (size + 2) / 2, sun.getY() - (size + 2) / 2, size + 2, size + 2);
        ctx.setFill(Color.WHITE);
        ctx.fillOval(sun.getX() - size / 2, sun.getY() - size / 2, size, size);

        return this;

    }

    /**
     * Draw the moon.
     *
     * @param sky           the sky
     * @param projection    the projection
     * @param planeToCanvas the plane to canvas
     * @return the sky canvas painter
     */
    public SkyCanvasPainter drawMoon(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas) {
        Point2D moon = planeToCanvas.transform(sky.getMoonPosition().x(), sky.getMoonPosition().y());
        double size = projection.applyToAngle(sky.getMoon().angularSize());
        size = planeToCanvas.deltaTransform(size, 0).magnitude();

        ctx.setFill(Color.WHITE);
        ctx.fillOval(moon.getX() - size / 2, moon.getY() - size / 2, size, size);

        return this;
    }


    /**
     * Draw the stars and the asterisms
     *
     * @param sky           the sky
     * @param projection    the projection
     * @param planeToCanvas the plane to canvas
     * @return the sky canvas painter
     */
    public SkyCanvasPainter drawStars(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas) {
        int nbrTransformedPoints = sky.getStarsPosition().length;

        double[] transformedPoints = new double[nbrTransformedPoints];
        planeToCanvas.transform2DPoints(sky.getStarsPosition(), 0, transformedPoints, 0, nbrTransformedPoints / 2);

        //Asterisms
        ctx.setStroke(Color.BLUE);
        ctx.setLineWidth(1);


        Set<Asterism> asterismList = sky.getAsterisms();

        for (Asterism asterism : asterismList) {
            ctx.beginPath();

            List<Integer> starsIndex = new ArrayList<>(sky.getStarIndexOfAsterism(asterism));
            for (int i = 1; i < starsIndex.size(); ++i) {
                //star1
                double x1 = transformedPoints[2 * starsIndex.get(i - 1)];
                double y1 = transformedPoints[2 * starsIndex.get(i - 1) + 1];
                //star2
                double x2 = transformedPoints[2 * starsIndex.get(i)];
                double y2 = transformedPoints[2 * starsIndex.get(i) + 1];

                if (canvas.getBoundsInLocal().contains(x1, y1) || canvas.getBoundsInLocal().contains(x2, y2)) {
                    ctx.moveTo(x1, y1);
                    ctx.lineTo(x2, y2);
                }
            }
            ctx.stroke();
        }

        //Stars
        double diametre = 0;
        int index = 0;
        double correction = 0;
        List<Star> starList = new ArrayList<>(sky.getStars());
        for (Star star : starList) {
            diametre = transformedDiametre(star.magnitude(), projection, planeToCanvas);
            correction = diametre / 2;
            ctx.setFill(BlackBodyColor.colorForTemperature(star.colorTemperature()));
            ctx.fillOval(transformedPoints[2 * index] - correction, transformedPoints[2 * index + 1] - correction, diametre, diametre);
            index += 1;
        }

        return this;

    }

    /**
     * Draw the planets
     *
     * @param sky           the sky
     * @param projection    the projection
     * @param planeToCanvas the plane to canvas
     * @return the sky canvas painter
     */
    public SkyCanvasPainter drawPlanets(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas) {

        ctx.setFill(Color.LIGHTGRAY);

        int nbrTransformedPoints = sky.getPlanetsPosition().length;

        double[] transformedPoints = new double[nbrTransformedPoints];
        planeToCanvas.transform2DPoints(sky.getPlanetsPosition(), 0, transformedPoints, 0, nbrTransformedPoints / 2);

        double diametre = 0;
        int index = 0;
        double correction = 0;
        List<Planet> planetList = sky.getPlanets();
        for (Planet planet : planetList) {
            diametre = transformedDiametre(planet.magnitude(), projection, planeToCanvas);
            correction = diametre / 2;
            ctx.fillOval(transformedPoints[2 * index] - correction, transformedPoints[2 * index + 1] - correction, diametre, diametre);
            index += 1;
        }

        return this;
    }


    private double transformedDiametre(double magnitude, StereographicProjection projection, Transform planeToCanvas) {
        double factor, diametre;
        magnitude = MAGNITUDE_INTERVAL.clip(magnitude);
        factor = (99 - 17 * magnitude) / 140;
        diametre = factor * projection.applyToAngle(Angle.ofDeg(0.5));
        return planeToCanvas.deltaTransform(diametre, 0).magnitude();
    }


    /**
     * Draw the horizon.
     *
     * @param sky           the sky
     * @param projection    the projection
     * @param planeToCanvas the plane to canvas
     */
    public void drawHorizon(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas) {
        HorizontalCoordinates horizon = HorizontalCoordinates.of(0, 0);
        CartesianCoordinates projectedCenter = projection.circleCenterForParallel(horizon);

        double transformedDiameter = planeToCanvas.deltaTransform(2 * projection.circleRadiusForParallel(horizon), 0)
                .magnitude();
        Point2D centerHorizon = planeToCanvas.transform(projectedCenter.x(), projectedCenter.y());
        ctx.setStroke(Color.RED);
        ctx.setLineWidth(2);
        double correction = transformedDiameter / 2;
        ctx.strokeOval(centerHorizon.getX() - correction, centerHorizon.getY() - correction, transformedDiameter, transformedDiameter);


        HorizontalCoordinates ordinalCoordinates;
        CartesianCoordinates projectedOrdinal;
        Point2D pointOrdinal;
        ctx.setFill(Color.RED);
        ctx.setTextAlign(TextAlignment.CENTER);
        ctx.setTextBaseline(VPos.TOP);
        for (int ordinal = 0; ordinal < 8; ordinal++) {
            ordinalCoordinates = HorizontalCoordinates.ofDeg(ordinal * 45, -0.5);
            projectedOrdinal = projection.apply(ordinalCoordinates);
            pointOrdinal = planeToCanvas.transform(projectedOrdinal.x(), projectedOrdinal.y());
            ctx.fillText(ordinalCoordinates.azOctantName("N", "E", "S", "O"), pointOrdinal.getX(), pointOrdinal.getY());
        }

    }


}
