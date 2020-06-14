package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.*;
import ch.epfl.rigel.coordinates.Capitals;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.*;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.UnaryOperator;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
public class Main extends Application {

    private static final GeographicCoordinates INIT_GEO_COORD = GeographicCoordinates.ofDeg(6.57, 46.52);
    private static final HorizontalCoordinates INIT_HOR_COORD = HorizontalCoordinates.ofDeg(180.000000000001, 15);
    private static final double INIT_FIELD_OF_VIEW = 100;
    private static final double INIT_MIN_HEIGHT = 600;
    private static final double INIT_MIN_WIDTH = 800;
    private static final String TITLE = "Rigel";
    private static final String RESET = "\uf0e2";
    private static final String PLAY = "\uf04b";
    private static final String PAUSE = "\uf04c";
    private static final String ENTER_FULL_SCREEN = "\uf065";
    private static final String EXIT_FULL_STRING = "\uf066";
    private static final String SELECT_TO_EDIT = "Default";
    private static final double ZOOM_PLANETS_INFOS = 50;
    private static final ClosedInterval VIEWABLE_INTERVAL = ClosedInterval.of(5, 90);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //Starting Menu and infos images
        Image startingEarth = new Image(getClass().getResourceAsStream("/sunrise-earth.jpg"));
        Image infosStars = new Image(getClass().getResourceAsStream("/stars.jpg"));

        //Initial zoned date time
        ZonedDateTime when = ZonedDateTime.now();
        DateTimeBean dateTimeBean = new DateTimeBean();
        dateTimeBean.setZonedDateTime(when);
        TimeAnimator animator = new TimeAnimator(dateTimeBean);

        //Initial values for observer location
        ObserverLocationBean observerLocationBean = new ObserverLocationBean();
        observerLocationBean.setCoordinates(INIT_GEO_COORD);

        //Initial viewing parameters
        ViewingParametersBean viewingParametersBean = new ViewingParametersBean();
        viewingParametersBean.setCenter(INIT_HOR_COORD);
        viewingParametersBean.setFieldOfViewDeg(INIT_FIELD_OF_VIEW);

        //Sky canvas manager
        SkyCanvasManager canvasManager = getCanvasManager(dateTimeBean, observerLocationBean, viewingParametersBean);

        //Simulation Borderpane
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(initControlBar(animator, dateTimeBean, observerLocationBean, stage, canvasManager, viewingParametersBean));
        assert canvasManager != null;
        borderPane.setCenter(initImageSky(canvasManager));
        borderPane.setBottom(initText(viewingParametersBean, canvasManager));

        stage.setTitle(TITLE);
        stage.setMinHeight(INIT_MIN_HEIGHT);
        stage.setMinWidth(INIT_MIN_WIDTH);

        //activation of planet information handler while simulating
        Map<String, SolarSystemInfos> infosMap = initMapNamePlanetInfos();
        borderPane.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown()) {
                borderPane.setRight(planetsInfos(canvasManager, infosMap));
            }
        });

        //Starting Borderpane (menu)
        BorderPane startingPane = new BorderPane();
        BackgroundSize bSize = BackgroundSize.DEFAULT;
        Background startingBackground = new Background(new BackgroundImage(startingEarth, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize));
        startingPane.setBackground(startingBackground);


        //Infos borderPane
        BorderPane infosPane = new BorderPane();
        Background infosBackground = new Background(new BackgroundImage(infosStars, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize));
        infosPane.setBackground(infosBackground);

        //Principal Scene
        Scene scene = new Scene(startingPane, 1600, 800);

        //Starting menu
        startingPane.setCenter(StartingVBox(scene, borderPane, infosPane));

        //Infos menu
        infosPane.setCenter(infosBox(scene, startingPane));

        stage.setTitle(TITLE);
        stage.setMinHeight(INIT_MIN_HEIGHT);
        stage.setMinWidth(INIT_MIN_WIDTH);

        stage.setScene(scene);
        stage.show();
        borderPane.getCenter().requestFocus();
    }

    /**
     * Use locationBox and observationBox to compute the HBox
     *
     * @return the HBox of the top control bar with : longitude, latitude, date, hour and ZoneId.
     */
    private HBox initControlBar(TimeAnimator animator, DateTimeBean dateTimeBean, ObserverLocationBean observerLocationBean,
                                Stage stage, SkyCanvasManager canvasManager, ViewingParametersBean viewingParametersBean) {

        Separator separator = new Separator(Orientation.VERTICAL);
        Separator separator1 = new Separator(Orientation.VERTICAL);
        Separator separator2 = new Separator(Orientation.VERTICAL);

        HBox bigBox = new HBox(locationBox(observerLocationBean),
                separator, observationBox(animator, dateTimeBean),
                separator1, planetsCenter(animator, canvasManager, viewingParametersBean, stage),
                separator2, timeFlow(animator, dateTimeBean, stage));
        bigBox.setStyle("-fx-spacing: 4; -fx-padding: 4;");

        return bigBox;
    }

    /**
     * @return the HBox with latitude and longitude nodes
     */
    private HBox locationBox(ObserverLocationBean observerLocationBean) {
        //Longitude
        Label obsText = new Label("Longitude (°) : ");
        obsText.setStyle("-fx-pref-width: 80; -fx-alignment: baseline-right;");

        TextField lonTextField = new TextField();
        lonTextField.setTextFormatter(positionFormatter(true, observerLocationBean));
        lonTextField.setStyle("-fx-pref-width: 60; -fx-alignment: baseline-right;");

        //Latitude
        Label obsText2 = new Label("Latitude (°) : ");
        obsText2.setStyle("-fx-pref-width: 70; -fx-alignment: baseline-right;");

        TextField latTextField = new TextField();
        latTextField.setTextFormatter(positionFormatter(false, observerLocationBean));
        latTextField.setStyle("-fx-pref-width: 60; -fx-alignment: baseline-right;");

        ComboBox<String> capitals = new ComboBox<>();
        capitals.getItems().add(SELECT_TO_EDIT);
        capitals.getItems().addAll(Capitals.getCapitals());
        capitals.setValue(SELECT_TO_EDIT);
        capitals.getSelectionModel().selectedItemProperty().addListener((o, oV, nV) -> {
            boolean test = nV.equals(SELECT_TO_EDIT);
            if (test) {
                latTextField.disableProperty().setValue(false);
                lonTextField.disableProperty().setValue(false);
            } else {
                latTextField.setText(Capitals.getLatForCapital(nV));
                lonTextField.setText(Capitals.getLonForCapital(nV));
                latTextField.disableProperty().setValue(true);
                lonTextField.disableProperty().setValue(true);

            }
        });


        //HBox to return
        HBox observationBox = new HBox(obsText, lonTextField, obsText2, latTextField, capitals);
        observationBox.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");
        return observationBox;
    }


    /**
     * Computation of the formatter for longitude and latitude
     *
     * @param isLon : true if we want the formatter for the longitude and false if we want the one for the latitude
     * @return the corresponding formatter
     */
    private TextFormatter<Number> positionFormatter(boolean isLon, ObserverLocationBean observerLocationBean) {


        NumberStringConverter stringConverter = new NumberStringConverter("#0.00");
        UnaryOperator<TextFormatter.Change> Filter = (change -> {
            try {
                String newText = change.getControlNewText();
                double newDeg = stringConverter.fromString(newText).doubleValue();
                if (isLon) {
                    return GeographicCoordinates.isValidLonDeg(newDeg) ? change : null;
                } else {
                    return GeographicCoordinates.isValidLatDeg(newDeg) ? change : null;
                }
            } catch (Exception e) {
                return null;
            }
        });

        TextFormatter<Number> lonTextFormatter = new TextFormatter<>(stringConverter, 0, Filter);
        if (isLon) {
            lonTextFormatter.valueProperty().bindBidirectional(observerLocationBean.lonDegProperty());
        } else {
            lonTextFormatter.valueProperty().bindBidirectional(observerLocationBean.latDegProperty());
        }
        return lonTextFormatter;
    }


    /**
     * @return the HBox with the date, hour and zoneId nodes
     */
    private HBox observationBox(TimeAnimator animator, DateTimeBean dateTimeBean) {


        //Date
        Label date = new Label("Date : ");
        date.setStyle("-fx-pref-width: 35; -fx-alignment: baseline-right;");

        DatePicker datePicker = new DatePicker();

        datePicker.valueProperty().bindBidirectional(dateTimeBean.getLocalDateProperty());
        datePicker.disableProperty().bind(animator.runningProperty());

        datePicker.setStyle("-fx-pref-width: 120;");

        //Hour
        Label hour = new Label("Heure : ");
        hour.setStyle("-fx-pref-width: 45; -fx-alignment: baseline-right;");

        DateTimeFormatter hmsFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTimeStringConverter stringConverter = new LocalTimeStringConverter(hmsFormatter, hmsFormatter);
        TextFormatter<LocalTime> timeFormatter = new TextFormatter<>(stringConverter);
        timeFormatter.valueProperty().bindBidirectional(dateTimeBean.getLocalTimeProperty());

        TextField hourField = new TextField();
        hourField.disableProperty().bind(animator.runningProperty());
        hourField.setTextFormatter(timeFormatter);
        hourField.setStyle("-fx-pref-width: 75;\n-fx-alignment: baseline-right;");

        //Time zone
        Label zone = new Label("Zone : ");
        zone.setStyle("-fx-pref-width: 40; -fx-alignment: baseline-right;");

        ComboBox<ZoneId> timeZone = new ComboBox<>();
        TreeSet<String> sortedZone = new TreeSet<>(ZoneId.getAvailableZoneIds());
        for (String zoneString : sortedZone) {
            timeZone.getItems().add(ZoneId.of(zoneString));
        }

        timeZone.valueProperty().bindBidirectional(dateTimeBean.getZoneIdProperty());
        timeZone.disableProperty().bind(animator.runningProperty());
        timeZone.setStyle("-fx-pref-width: 180;");


        HBox observationTimeBox = new HBox(date, datePicker, hour, hourField, zone, timeZone);
        observationTimeBox.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");
        return observationTimeBox;

    }

    /**
     * @return the HBox with the buttons for the simulation
     */
    private HBox timeFlow(TimeAnimator animator, DateTimeBean dateTimeBean, Stage stage) {

        ComboBox<NamedTimeAccelerator> accelerator = new ComboBox<>();
        accelerator.getItems().addAll(NamedTimeAccelerator.values());
        accelerator.setValue(NamedTimeAccelerator.TIMES_300);
        accelerator.disableProperty().bind(animator.runningProperty());
        animator.acceleratorProperty().bind(Bindings.select(accelerator.valueProperty(), "accelerator"));

        Button resetButton = new Button(RESET);
        Button playButton = new Button(PLAY);

        //Full screen btn
        Button fullScreen = new Button(ENTER_FULL_SCREEN);


        try (InputStream fontStream = getClass().getResourceAsStream("/Font Awesome 5 Free-Solid-900.otf")) {
            Font fontAwesome = Font.loadFont(fontStream, 15);
            resetButton.setFont(fontAwesome);
            playButton.setFont(fontAwesome);
            fullScreen.setFont(fontAwesome);
        } catch (IOException e) {
            System.out.println("error in loading font awesome");
        }

        resetButton.setOnAction(e -> dateTimeBean.setZonedDateTime(ZonedDateTime.now()));
        resetButton.disableProperty().bind(animator.runningProperty());

        playButton.textProperty().bind(Bindings.when(animator.runningProperty()).then(PAUSE).otherwise(PLAY));
        playButton.setOnAction(p -> {
            if (animator.isRunning()) animator.stop();
            else animator.start();
        });

        fullScreen.textProperty().bind(Bindings.when(stage.fullScreenProperty()).then(EXIT_FULL_STRING).otherwise(ENTER_FULL_SCREEN));
        fullScreen.setOnAction(e -> stage.setFullScreen(!stage.isFullScreen()));

        HBox timeFlowBox = new HBox(accelerator, resetButton, playButton, fullScreen);
        timeFlowBox.setStyle("-fx-spacing: inherit;");
        return timeFlowBox;

    }


    private Pane initImageSky(SkyCanvasManager canvasManager) {
        Pane toReturn = new Pane();

        Canvas sky = canvasManager.canvas();
        toReturn.getChildren().add(sky);
        sky.heightProperty().bind(toReturn.heightProperty());
        sky.widthProperty().bind(toReturn.widthProperty());


        return toReturn;
    }

    private SkyCanvasManager getCanvasManager(DateTimeBean dateTimeBean, ObserverLocationBean observerLocationBean, ViewingParametersBean viewingParametersBean) {

        //with asterisms
        try (InputStream hs = getClass().getResourceAsStream("/hygdata_v3.csv");
             InputStream as = getClass().getResourceAsStream("/asterisms.txt")) {
            StarCatalogue catalogue = new StarCatalogue.Builder()
                    .loadFrom(hs, HygDatabaseLoader.INSTANCE).loadFrom(as, AsterismLoader.INSTANCE).build();

            return new SkyCanvasManager(catalogue, dateTimeBean, observerLocationBean, viewingParametersBean);

        } catch (IOException ex) {
            System.out.println("error loading hygdata_v3.csv");
        }

        return null;
    }

    private HBox initText(ViewingParametersBean viewingParametersBean, SkyCanvasManager canvasManager) {
        Text fieldview = new Text();
        fieldview.textProperty().bind(Bindings.format("Champ de vue %.1f°", viewingParametersBean.fieldOfViewDegProperty()));

        Text center = new Text();
        center.setStyle("-fx-alignment: baseline-center;");
        center.textProperty().setValue("none");
        center.textProperty().bind(Bindings.createObjectBinding(() -> (canvasManager.objectUnderMouseProperty().get() != null) ?
                        canvasManager.objectUnderMouseProperty().getValue().info() : null
                , canvasManager.objectUnderMouseProperty()));


        Text right = new Text();
        right.setStyle("-fx-alignment: baseline-left;");
        right.textProperty().setValue("none");
        right.textProperty().bind(Bindings.format("Azimut : %.2f°, Hauteur : %.2f° ", canvasManager.mouseAzDegProperty(), canvasManager.mouseAltDegProperty()));


        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);
        HBox toReturn = new HBox(fieldview, region1, center, region2, right);
        toReturn.setStyle("-fx-padding: 4;\n-fx-background-color: white;");
        return toReturn;
    }

//-------------------------------------------------------------------------BONUS-----------------------------------------------------------------------------------------

    /**
     * Creation of the HBox (ComboBox) with all the selectionable planets and celestial object that can be seen in more details
     *
     * @param animator              : the current timeAnimator
     * @param canvasManager         : the current SkyCanvasManager
     * @param viewingParametersBean : the current ViewingParametersBean
     * @param stage                 : the current Stage
     * @return the HBox
     */
    private HBox planetsCenter(TimeAnimator animator, SkyCanvasManager canvasManager,
                               ViewingParametersBean viewingParametersBean, Stage stage) {
        ComboBox<String> planets = new ComboBox<>();
        planets.getItems().add(SELECT_TO_EDIT);
        planets.setValue(SELECT_TO_EDIT);
        planets.disableProperty().bind(animator.runningProperty());

        //mapping the name of the Celestial Object from SolarSystemInfos
        // and their respective index in the current observed sky planetPosition list.
        Map<String, int[]> planetsCoord = new TreeMap<>();
        for (SolarSystemInfos p : SolarSystemInfos.ALL) {
            planets.getItems().add(p.getName());
        }

        int i = 0;
        for (PlanetModel p : PlanetModel.ALL) {
            if (p != PlanetModel.EARTH) {
                int[] test = {i, i + 1};
                planetsCoord.put(p.getFrenchName(), test);
                i += 2;
            }
        }

        planets.getSelectionModel().selectedItemProperty().addListener((o, oV, nV) -> {
            boolean test = nV.equals(SELECT_TO_EDIT);

            //only if not in the 'default' mode
            if (!test) {
                if (nV.equals("Soleil")) {
                    if (isViewable(stage, canvasManager.projectionProperty().get().inverseApply(
                            canvasManager.getObservedSkyProperty().get().getSunPosition()).altDeg())) {

                        viewingParametersBean.setCenter(canvasManager.projectionProperty().get().inverseApply(
                                canvasManager.getObservedSkyProperty().get().getSunPosition()));
                        viewingParametersBean.setFieldOfViewDeg(ZOOM_PLANETS_INFOS);
                    }

                } else if (nV.equals("Lune")) {
                    if (isViewable(stage, canvasManager.projectionProperty().get().inverseApply(
                            canvasManager.getObservedSkyProperty().get().getMoonPosition()).altDeg())) {

                        viewingParametersBean.setCenter(canvasManager.projectionProperty().get().inverseApply(
                                canvasManager.getObservedSkyProperty().get().getMoonPosition()));
                        viewingParametersBean.setFieldOfViewDeg(ZOOM_PLANETS_INFOS);
                    }

                } else {
                    //Find the selectioned planet and draw it if viewable
                    for (String planetName : planetsCoord.keySet()) {
                        if (nV.equals(planetName)) {
                            CartesianCoordinates planetCoord = CartesianCoordinates.of(
                                    canvasManager.getObservedSkyProperty().get().getPlanetsPosition()[planetsCoord.get(planetName)[0]],
                                    canvasManager.getObservedSkyProperty().get().getPlanetsPosition()[planetsCoord.get(planetName)[1]]);

                            if (isViewable(stage, canvasManager.projectionProperty().get().inverseApply(planetCoord).altDeg())) {
                                viewingParametersBean.setCenter(canvasManager.projectionProperty().get().inverseApply(planetCoord));
                                viewingParametersBean.setFieldOfViewDeg(ZOOM_PLANETS_INFOS);
                            }
                        }
                    }
                }
            }
        });

        return new HBox(planets);
    }

    /**
     * @param stage  : the current Stage
     * @param center : the position to test
     * @return true if center is viewable (if center can be in the field of view)
     */

    private boolean isViewable(Stage stage, double center) {
        Popup popup = new Popup();
        popup.setAutoHide(true);

        Label white = new Label("Sorry ;(\n" +
                "The selected planet is under the horizon line.\n" +
                "Please run the simulation and try again !");
        white.setFont(Font.font("Verdana", 25));
        white.setTextFill(Color.WHITE);
        white.setTextAlignment(TextAlignment.CENTER);

        HBox popupBox = new HBox(white);
        popupBox.setAlignment(Pos.CENTER);

        popup.getContent().addAll(popupBox);

        //Center cannot be under the horizon line
        boolean isViewable = VIEWABLE_INTERVAL.contains(center);
        if (!isViewable) {
            popup.show(stage);
        }

        return isViewable;
    }

    /**
     * Javafx style taken from the internet at "fxexperience.com", credit to Jasper Potts, Dec 20, 2011.
     *
     * @return the javafx style for the button
     */
    private String buttonStyle() {
        return "-fx-background-color: #c3c4c4," +
                "linear-gradient(#d6d6d6 50%, white 100%)," +
                "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);" +
                "-fx-background-radius: 30;" +
                "-fx-background-insets: 0,1,1;" +
                "-fx-text-fill: black;" +
                "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );";
    }

    /**
     * Creating the starting menu VBox with the title and the 2 buttons
     *
     * @param scene      : the curent Scene
     * @param borderPane : the borderpane of the simulation
     * @param infosPane  : the borderpane with all the informations
     * @return the VBox
     */
    private VBox StartingVBox(Scene scene, BorderPane borderPane, BorderPane infosPane) {
        Text title = new Text("The Rigel Project");
        title.setFill(Color.WHITE);
        title.setFont(new Font("Verdana", 70));
        title.setStrokeWidth(1);
        title.setStroke(Color.BLACK);

        Button simulationBtn = new Button("Start the simulation");
        simulationBtn.setStyle(buttonStyle());
        simulationBtn.setOnAction(e -> scene.setRoot(borderPane));

        Button infosBtn = new Button("Infos");
        infosBtn.setStyle(buttonStyle());
        infosBtn.setOnAction(e -> scene.setRoot(infosPane));

        VBox startingBox = new VBox(20);
        startingBox.getChildren().addAll(title, simulationBtn, infosBtn);
        startingBox.setAlignment(Pos.CENTER);

        return startingBox;
    }

    /**
     * Creates a VBox with some informations about the project and
     * a return button (to go back to the main menu)
     *
     * @param scene              : the current Scene
     * @param startingBorderPane : the borderPane of the starting menu
     * @return the VBox
     */
    private VBox infosBox(Scene scene, BorderPane startingBorderPane) {
        Button getBack = new Button("Return");
        getBack.setOnAction(e -> scene.setRoot(startingBorderPane));
        getBack.setStyle(buttonStyle());

        //Read a .txt file
        String bibliographyString;
        List<String> fullTxt = new ArrayList<>();
        InputStream biblio = getClass().getResourceAsStream("/bibliography.txt");

        try (BufferedReader bibliography = new BufferedReader(new InputStreamReader(biblio))) {
            while ((bibliographyString = bibliography.readLine()) != null) {
                fullTxt.add(bibliographyString);
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }

        //Add the text from the .txt file to the Label
        Label infos = new Label();
        infos.setTextAlignment(TextAlignment.LEFT);
        infos.setAlignment(Pos.CENTER);
        infos.setTextFill(Color.WHITE);
        infos.setFont(Font.font("Verdana", 16));
        for (String s : fullTxt) {
            infos.setText(infos.getText() + "\n" + s);
        }

        VBox box = new VBox(infos, getBack);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(50);
        return box;
    }

    private Map<String, SolarSystemInfos> initMapNamePlanetInfos() {
        Map<String, SolarSystemInfos> toReturn = new HashMap<>();
        for (SolarSystemInfos value : SolarSystemInfos.values()) {
            toReturn.put(value.getName(), value);
        }
        return toReturn;
    }

    /**
     * Create a VBox with informations about some planet and celestial objet
     *
     * @param canvasManager : the current SkyCanvasManager
     * @return the VBox
     */
    private VBox planetsInfos(SkyCanvasManager canvasManager, Map<String, SolarSystemInfos> infosMap) {
        VBox box = new VBox(10);
        box.setStyle("-fx-background-color: black");
        box.setAlignment(Pos.CENTER);

        Label mass = new Label();
        mass.setTextFill(Color.WHITE);

        Label diameter = new Label();
        diameter.setTextFill(Color.WHITE);

        Label gravity = new Label();
        gravity.setTextFill(Color.WHITE);

        Label temperature = new Label();
        temperature.setTextFill(Color.WHITE);

        ImageView planetView;

        if (canvasManager.objectUnderMouse.get() == null || infosMap.get(canvasManager.objectUnderMouse.get().name()) == null)
            return null;
        String planetName = canvasManager.objectUnderMouse.get().name();
        mass.setText(String.format("Mass : %s Earth", infosMap.get(planetName).getMass()));
        diameter.setText(String.format("Equatorial Diameter : %s km", infosMap.get(planetName).getEquatorialDiameter()));
        gravity.setText(String.format("Gravity : %s g", infosMap.get(planetName).getGravity()));
        temperature.setText(String.format("Temperature : %s", infosMap.get(planetName).getTemperature()));
        planetView = new ImageView(new Image(getClass().getResourceAsStream(infosMap.get(planetName).getImageFile()),
                384, 216, false, false));

        box.getChildren().addAll(planetView, mass, diameter, gravity, temperature);
        return box;
    }


}
