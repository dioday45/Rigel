package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Transform;

/**
 * The type Sky canvas manager.
 *
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
public final class SkyCanvasManager {

    private static final ClosedInterval FIVE_TO_NINETY_DEG = ClosedInterval.of(5, 90);
    private static final ClosedInterval THIRTY_TO_HUNDREDFIFTY_DEG = ClosedInterval.of(30, 150);
    private static final double STEP_AZ = 10;
    private static final double STEP_ALT = 5;

    /**
     * The Mouse az deg.
     */
    public final ObservableDoubleValue mouseAzDeg;
    /**
     * The Mouse alt deg.
     */
    public final ObservableDoubleValue mouseAltDeg;
    private final Canvas canvas;
    private final SkyCanvasPainter painter;
    private final ObservableObjectValue<StereographicProjection> projection;
    private final ObservableObjectValue<ObservedSky> observedSkyProperty;
    private final ObservableObjectValue<Transform> planeToCanvas;
    private final ObservableObjectValue<HorizontalCoordinates> mouseHorizontalPosition;
    private final SimpleObjectProperty<CartesianCoordinates> mousePosition;
    /**
     * The Object under mouse.
     */
    public ObservableObjectValue<CelestialObject> objectUnderMouse;


    /**
     * Instantiates a new Sky canvas manager.
     *
     * @param catalogue             the catalogue
     * @param timeBean              the time bean
     * @param locationBean          the location bean
     * @param viewingParametersBean the viewing parameters bean
     */
    public SkyCanvasManager(StarCatalogue catalogue, DateTimeBean timeBean,
                            ObserverLocationBean locationBean, ViewingParametersBean viewingParametersBean) {
        //déclaration
        canvas = new Canvas(800, 600);
        painter = new SkyCanvasPainter(canvas);
        mousePosition = new SimpleObjectProperty<>(CartesianCoordinates.of(0, 0));


        //projection
        projection = Bindings.createObjectBinding(() ->
                new StereographicProjection(viewingParametersBean.getCenter()), viewingParametersBean.centerProperty());

        //Observed sky
        observedSkyProperty = Bindings.createObjectBinding(() ->
                        new ObservedSky(timeBean.getZonedDateTime(), locationBean.getCoordinates(), projection.get(), catalogue),
                timeBean.getLocalDateProperty(),
                timeBean.getLocalTimeProperty(),
                timeBean.getZoneIdProperty(),
                projection,
                locationBean.coordinatesProperty());
        observedSkyProperty.addListener((o, oV, nV) -> draw());

        //plane to canvas
        planeToCanvas = Bindings.createObjectBinding(() -> {
                    double projectionValue = projection.getValue().applyToAngle(Angle.ofDeg(viewingParametersBean.getFieldOfViewDeg()));
                    return Transform.affine(canvas.getWidth() / projectionValue, 0, 0,
                            canvas.getWidth() / -projectionValue,
                            canvas.getWidth() / 2, canvas.getHeight() / 2);
                }
                ,
                projection,
                canvas.heightProperty(),
                canvas.widthProperty(),
                viewingParametersBean.fieldOfViewDegProperty());
        planeToCanvas.addListener((o, oV, nV) -> draw());

        //Mouse position
        canvas.setOnMouseMoved(e ->
                mousePosition.set(CartesianCoordinates.of(e.getX(), e.getY())));

        //mouse Horizontal position
        mouseHorizontalPosition = Bindings.createObjectBinding(() ->
                {
                    try {
                        Point2D coord = new Point2D(mousePosition.getValue().x(), mousePosition.getValue().y());
                        coord = planeToCanvas.getValue().inverseTransform(coord);
                        return projection.getValue().inverseApply(CartesianCoordinates.of(coord.getX(), coord.getY()));
                    } catch (NonInvertibleTransformException e) {
                        return HorizontalCoordinates.of(0, 0);
                    }

                },
                projection,
                planeToCanvas, mousePosition);

        //Object under mouse
        objectUnderMouse = Bindings.createObjectBinding(() -> {
                    try {
                        Point2D point = new Point2D(mousePosition.getValue().x(), mousePosition.getValue().y());
                        point = planeToCanvas.getValue().inverseTransform(point);
                        CartesianCoordinates coord = CartesianCoordinates.of(point.getX(), point.getY());
                        Point2D maxDist = new Point2D(10, 0);
                        maxDist = planeToCanvas.getValue().inverseDeltaTransform(maxDist);
                        return observedSkyProperty.getValue().objectClosestTo(coord, maxDist.getX()).orElse(null);
                    } catch (NonInvertibleTransformException e) {
                        return null;
                    }
                }
                , planeToCanvas
                , observedSkyProperty
                , mousePosition);


        //Mouse azDeg and altDeg
        mouseAzDeg = Bindings.createDoubleBinding(() -> mouseHorizontalPosition.getValue().azDeg(), mouseHorizontalPosition);

        mouseAltDeg = Bindings.createDoubleBinding(() -> mouseHorizontalPosition.getValue().altDeg(), mouseHorizontalPosition);

        canvas.setOnMousePressed(k -> {
            if (k.isPrimaryButtonDown()) {
                canvas.requestFocus();
            }
        });

        canvas.setOnKeyPressed(k -> {
            switch (k.getCode()) {
                case LEFT:
                    viewingParametersBean.setCenter(
                            HorizontalCoordinates.of(
                                    Angle.normalizePositive(viewingParametersBean.getCenter().az() - Angle.ofDeg(STEP_AZ))
                                    , viewingParametersBean.getCenter().alt()));
                    break;
                case RIGHT:
                    viewingParametersBean.setCenter(
                            HorizontalCoordinates.of(
                                    Angle.normalizePositive(viewingParametersBean.getCenter().az() + Angle.ofDeg(STEP_AZ))
                                    , viewingParametersBean.getCenter().alt()));
                    break;
                case UP:
                    viewingParametersBean.setCenter(
                            HorizontalCoordinates.ofDeg(
                                    viewingParametersBean.getCenter().azDeg()
                                    , FIVE_TO_NINETY_DEG.clip(viewingParametersBean.getCenter().altDeg() + STEP_ALT)));
                    break;
                case DOWN:
                    viewingParametersBean.setCenter(
                            HorizontalCoordinates.ofDeg(
                                    viewingParametersBean.getCenter().azDeg()
                                    , FIVE_TO_NINETY_DEG.clip(viewingParametersBean.getCenter().altDeg() - STEP_ALT)));
                    break;
                default:
                    break;
            }
            k.consume();
        });

        canvas.setOnScroll(s -> {
            double deg = (Math.abs(s.getDeltaX()) > Math.abs(s.getDeltaY())) ? s.getDeltaX() : s.getDeltaY();
            viewingParametersBean.setFieldOfViewDeg(THIRTY_TO_HUNDREDFIFTY_DEG.clip(viewingParametersBean.getFieldOfViewDeg() - deg));
        });


    }

    /**
     * Object under mouse property observable object value.
     *
     * @return the observable object value
     */
    public ObservableObjectValue<CelestialObject> objectUnderMouseProperty() {
        return objectUnderMouse;
    }

    /**
     * Mouse altitude deg property observable double value.
     *
     * @return the observable double value
     */
    public ObservableDoubleValue mouseAltDegProperty() {
        return mouseAltDeg;
    }

    /**
     * Mouse azimuth deg property observable double value.
     *
     * @return the observable double value
     */
    public ObservableDoubleValue mouseAzDegProperty() {
        return mouseAzDeg;
    }

    private void draw() {
        painter.clear();
        painter.drawSun(observedSkyProperty.get(), projection.get(), planeToCanvas.get())
                .drawMoon(observedSkyProperty.get(), projection.get(), planeToCanvas.get())
                .drawPlanets(observedSkyProperty.get(), projection.get(), planeToCanvas.get())
                .drawStars(observedSkyProperty.get(), projection.get(), planeToCanvas.get())
                .drawHorizon(observedSkyProperty.get(), projection.get(), planeToCanvas.get());
    }

    public ObservableObjectValue<ObservedSky> getObservedSkyProperty() {
        return observedSkyProperty;
    }

    public ObservableObjectValue<StereographicProjection> projectionProperty() {
        return projection;
    }

    /**
     * Canvas canvas.
     *
     * @return the canvas
     */
    public Canvas canvas() {
        return canvas;
    }
}
