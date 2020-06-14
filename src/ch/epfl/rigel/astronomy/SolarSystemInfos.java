package ch.epfl.rigel.astronomy;

import java.util.List;

public enum SolarSystemInfos {
    SUN(Sun.getFrenchName(), 333.06, 1392684, 28, "average 5500°C", "/sun.jpg"),
    MOON(Moon.getFrenchName(), 0.0123, 3475, 0.166, "-233°C to 123°C", "/moon.jpg"),
    MERCURY(PlanetModel.MERCURY.getFrenchName(), 0.055, 4879, 0.38, "-173°C to 427°C", "/mercury.jpg"),
    VENUS(PlanetModel.VENUS.getFrenchName(), 0.0815, 12104, 0.9, "average 462°C", "/venus.jpg"),
    MARS(PlanetModel.MARS.getFrenchName(), 0.107, 6805, 0.38, "-87°C to -5°C", "/mars.jpg"),
    JUPITER(PlanetModel.JUPITER.getFrenchName(), 317.83, 142984, 2.528, "average -108°C", "/jupiter.jpg"),
    SATURNE(PlanetModel.SATURN.getFrenchName(), 95.152, 120536, 1.065, "-201°C to -139°C", "/saturn.png"),
    URANUS(PlanetModel.URANUS.getFrenchName(), 14.536, 51118, 0.886, "average -197°C", "/uranus.jpg"),
    NEPTUNE(PlanetModel.NEPTUNE.getFrenchName(), 17.15, 49528, 1.14, "average -201°C", "/neptune.jpg");

    public static final List<SolarSystemInfos> ALL = List.of(values());
    private final String name;
    private final double mass;
    private final double equatorialDiameter;
    private final double gravity;
    private final String temperature;
    private final String imageFile;

    SolarSystemInfos(String name, double mass, double equatorialDiameter, double gravity, String temperature, String imageFile) {
        this.name = name;
        this.mass = mass;
        this.equatorialDiameter = equatorialDiameter;
        this.gravity = gravity;
        this.temperature = temperature;
        this.imageFile = imageFile;
    }

    public double getMass() {
        return mass;
    }

    public double getEquatorialDiameter() {
        return equatorialDiameter;
    }

    public double getGravity() {
        return gravity;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getImageFile() {
        return imageFile;
    }

    public String getName() {
        return name;
    }
}
