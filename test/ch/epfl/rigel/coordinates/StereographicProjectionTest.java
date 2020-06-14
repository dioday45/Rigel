package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import ch.epfl.test.TestRandomizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Tavares Agostinho (289061)
 * @author Jeremy Di Dio (301453)
 */
class StereographicProjectionTest {

    private  StereographicProjection projection = new StereographicProjection(HorizontalCoordinates.of(0,0));
    private HorizontalCoordinates horiz0 = HorizontalCoordinates.of(0,0);
    private HorizontalCoordinates horizPI = HorizontalCoordinates.of(3*Math.PI/4.0,Math.PI/4);

    @Test
    void singleValueCircleCenterForParallelTest() {
        CartesianCoordinates coordinates1 = projection.circleCenterForParallel(horiz0);
        CartesianCoordinates coordinates2 = projection.circleCenterForParallel(horizPI);

        assertEquals(0, coordinates1.x());
       // System.out.println("expected : Infinity, actual : " + coordinates1.y());
        assertEquals(Math.sqrt(2), coordinates2.y());


    }

    @Test
    void circleRadiusForParallel() {
        //System.out.println("expected : Infinity, actual : " + projection.circleRadiusForParallel(horiz0));
        assertEquals(1, projection.circleRadiusForParallel(horizPI), 10e-10);
    }

    @Test
    void applyToAngletest() {
        assertEquals(2, projection.applyToAngle(Math.PI), 10e-10);
    }

    @Test
    void apply() {
        assertEquals(0, projection.apply(horiz0).x());
        assertEquals(1, projection.apply(horizPI).x(), 10e-10);
        assertEquals(0, projection.apply(horiz0).y());
        assertEquals(Math.sqrt(2), projection.apply(horizPI).y(), 10e-10);
    }

    @Test
    void inverseApply() {
        assertEquals(horizPI.alt(),projection.inverseApply(projection.apply(horizPI)).alt(), 10e-10 );
        assertEquals(horizPI.az(),projection.inverseApply(projection.apply(horizPI)).az(), 10e-10 );
    }

    //others
    @Test
    void circleCenterForParallelWorks() {
        StereographicProjection test1 = new StereographicProjection(HorizontalCoordinates.of(0, 0));
        assertEquals(1, test1.circleCenterForParallel(HorizontalCoordinates.of(0, Math.PI / 2)).y());

        StereographicProjection test2 = new StereographicProjection(HorizontalCoordinates.of(0, Math.PI / 2));
        assertEquals(0,
                test2.circleCenterForParallel(HorizontalCoordinates.of(0, 0)).y(), 1e-10);

        StereographicProjection test3 = new StereographicProjection(HorizontalCoordinates.of(0,0));
        Assertions.assertEquals(Double.POSITIVE_INFINITY,
                test3.circleCenterForParallel(HorizontalCoordinates.of(0,0)).y());


        StereographicProjection test4 = new StereographicProjection(HorizontalCoordinates.of(0, -(Math.PI / 4)));
        assertEquals(-1,
                test4.circleCenterForParallel(HorizontalCoordinates.of(0, 0)).y(), 1e-10);

        StereographicProjection test5 = new StereographicProjection(HorizontalCoordinates.of(0, -(Math.PI / 4)));
        assertEquals(-0.5,
                test5.circleCenterForParallel(HorizontalCoordinates.of(0, -(Math.PI / 4))).y(), 1e-10);
    }

    @Test
    void circleRadiusForParallelWorks() {
        StereographicProjection test1 = new StereographicProjection(HorizontalCoordinates.of(0,0));
        Assertions.assertEquals(Double.POSITIVE_INFINITY, test1.circleRadiusForParallel(HorizontalCoordinates.of(0,0)));

        StereographicProjection test2 = new StereographicProjection(HorizontalCoordinates.of(0, Math.PI / 2));
        assertEquals(1, test2.circleRadiusForParallel(HorizontalCoordinates.of(0, 0)));

        StereographicProjection test3 = new StereographicProjection(HorizontalCoordinates.of(0, 0));
        assertEquals(0, test3.circleRadiusForParallel(HorizontalCoordinates.of(0, Math.PI / 2)), 1e-10);

        StereographicProjection test4 = new StereographicProjection(HorizontalCoordinates.of(0, 0));
        assertEquals(-1, test4.circleRadiusForParallel(HorizontalCoordinates.of(0, -Math.PI / 4)), 1e-10);
    }


    @Test
    void applyToAngleWorks() {
        StereographicProjection test1 = new StereographicProjection(HorizontalCoordinates.of(0, 0));
        assertEquals(0, test1.applyToAngle(0));

        StereographicProjection test2 = new StereographicProjection(HorizontalCoordinates.of(0, 0));
        assertEquals(1.09260498, test1.applyToAngle(2), 1e-8);
    }

    // Du coup j'ai utilisé la normalisation positive pour être dans le domaine de az de HOrizontal. J'utilise pas interval ouvert et reduce ?
    @Test
    void inverseApplyWorks() {

        StereographicProjection test3 = new StereographicProjection(HorizontalCoordinates.of(0, Math.PI / 4));
        assertEquals(-0.691505427, test3.inverseApply(CartesianCoordinates.of(10, 15)).alt(), 1e-7);
    }


    @Test
    void applyWorks() {
        StereographicProjection test1 = new StereographicProjection(HorizontalCoordinates.of(0, Math.PI / 3));
        assertEquals(0.767326988, test1.apply(HorizontalCoordinates.of(Math.PI, Math.PI/4)).y(), 1e-7);

        StereographicProjection test2 = new StereographicProjection(HorizontalCoordinates.of(0, Math.PI / 3));
        assertEquals(0.329938145, test2.apply(HorizontalCoordinates.of(1, Math.PI/4)).x(), 1e-7);


    }

    @Test
    void equalsThrowsUOE() {
        assertThrows(UnsupportedOperationException.class, () -> {
            var proj = new StereographicProjection(HorizontalCoordinates.ofDeg(187, 43));
            proj.equals(proj);
        });
    }

    @Test
    void hashCodeThrowsUOE() {
        assertThrows(UnsupportedOperationException.class, () -> {
            var proj = new StereographicProjection(HorizontalCoordinates.ofDeg(187, 43));
            proj.hashCode();
        });
    }



    @Test
    void applyWorks1() {
        var proj = new StereographicProjection(HorizontalCoordinates.ofDeg(187, 43));
        assertEquals(0, proj.apply(HorizontalCoordinates.ofDeg(187, 43)).x());
        assertEquals(0, proj.apply(HorizontalCoordinates.ofDeg(187, 43)).y());
        // MY RESULTS
        proj = new StereographicProjection(HorizontalCoordinates.ofDeg(0, 0));
        assertEquals(0, proj.apply(HorizontalCoordinates.ofDeg(0, 43)).x());
        assertEquals(0.39391047561494236, proj.apply(HorizontalCoordinates.ofDeg(0, 43)).y());
        assertEquals(1, proj.apply(HorizontalCoordinates.ofDeg(0, 90)).y());
        assertEquals(-1, proj.apply(HorizontalCoordinates.ofDeg(0, -90)).y());
        assertEquals((1.0/0.0), proj.apply(HorizontalCoordinates.ofDeg(180, 0)).x());
        proj = new StereographicProjection(HorizontalCoordinates.ofDeg(180, 0));
        assertEquals(-(1.0/0.0), proj.apply(HorizontalCoordinates.ofDeg(0, 0)).x());
        assertEquals(-0.014265634085599539, proj.apply(HorizontalCoordinates.ofDeg(54, -89)).x());
        assertEquals(-1.0102107064313057, proj.apply(HorizontalCoordinates.ofDeg(54, -89)).y());
        assertEquals(0, proj.apply(HorizontalCoordinates.ofDeg(180, -9)).x());
    }

    @Test
    void inverseApplyWorks1() {
        var proj = new StereographicProjection(HorizontalCoordinates.ofDeg(0, 0));
        assertEquals(0, proj.inverseApply(CartesianCoordinates.of(0, 0.39391047561494236)).azDeg(), 1e-8);
        assertEquals(43, proj.inverseApply(CartesianCoordinates.of(0, 0.39391047561494236)).altDeg(), 1e-8);
        proj = new StereographicProjection(HorizontalCoordinates.ofDeg(180, 0));
        assertEquals(54, proj.inverseApply(CartesianCoordinates.of(-0.014265634085599539, -1.0102107064313057)).azDeg(), 1e-8);
        assertEquals(-89, proj.inverseApply(CartesianCoordinates.of(-0.014265634085599539, -1.0102107064313057)).altDeg(), 1e-8);
    }

    @Test
    void applyToAngleWorks1() {
        var proj = new StereographicProjection(HorizontalCoordinates.ofDeg(0, 0));
        double angle = 0;
        assertEquals(angle, proj.applyToAngle(angle));
        angle = 2;
        assertEquals(1.09260498, proj.applyToAngle(angle), 1e-8);
    }

    @Test
    void circleCenterForParallelWorks1() {
        var proj = new StereographicProjection(HorizontalCoordinates.ofDeg(0, 0));
        assertEquals((1.0/0.0), proj.circleCenterForParallel(HorizontalCoordinates.of(0, 0)).y());
        assertEquals(0, proj.circleCenterForParallel(HorizontalCoordinates.of(0, 0)).x());
        // MY RESULTS
        proj = new StereographicProjection(HorizontalCoordinates.ofDeg(80, 75));
        assertEquals(-8.180580722285631, proj.circleCenterForParallel(HorizontalCoordinates.ofDeg(58, -86)).y());
        assertEquals(0, proj.circleCenterForParallel(HorizontalCoordinates.ofDeg(58, -86)).x());
        assertEquals(0, proj.circleCenterForParallel(HorizontalCoordinates.ofDeg(75, 85)).x());
        assertEquals(0.13190782211719446, proj.circleCenterForParallel(HorizontalCoordinates.ofDeg(75, 85)).y());
        assertEquals(0.13397459621556135, proj.circleCenterForParallel(HorizontalCoordinates.ofDeg(80, 75)).y());
    }

    @Test void circleRadiusForParallelWorks1() {
        var proj = new StereographicProjection(HorizontalCoordinates.ofDeg(0, 0));
        assertEquals((1.0/0.0), proj.circleRadiusForParallel(HorizontalCoordinates.of(0, 0)));
        // MY RESULTS
        assertEquals(5.67128181961771, proj.circleRadiusForParallel(HorizontalCoordinates.ofDeg(45, 10)));
        proj = new StereographicProjection(HorizontalCoordinates.ofDeg(10, 10));
        assertEquals(2.835640909808855, proj.circleRadiusForParallel(HorizontalCoordinates.ofDeg(10, 10)));
        assertEquals(-0.21407400337105734, proj.circleRadiusForParallel(HorizontalCoordinates.ofDeg(0, -80)));
    }
    @Test
    void applyWorks2(){
        HorizontalCoordinates h1 = HorizontalCoordinates.of(Math.PI/4, Math.PI/6);
        HorizontalCoordinates center1 = HorizontalCoordinates.of(0,0);
        StereographicProjection e = new StereographicProjection(center1);
        double p = Math.sqrt(6);
        CartesianCoordinates a1 = CartesianCoordinates.of(p/(4+p), 2/(4+p));
        CartesianCoordinates c1 = e.apply(h1);
        assertEquals(a1.x(), c1.x(), 1e-8);
        assertEquals(a1.y(), c1.y(), 1e-8);

        HorizontalCoordinates h2 = HorizontalCoordinates.of(Math.PI/2, Math.PI/2);
        HorizontalCoordinates center2 = HorizontalCoordinates.of(Math.PI/4, Math.PI/4);
        StereographicProjection e2 = new StereographicProjection(center2);
        double p2 = Math.sqrt(2);
        CartesianCoordinates a2 = CartesianCoordinates.of(0, p2/(2+p2));
        CartesianCoordinates c2 = e2.apply(h2);
        assertEquals(a2.x(), c2.x(), 1e-8);
        assertEquals(a2.y(), c2.y(), 1e-8);
    }

    @Test
    void circleCenterForParallelWorks2(){
        HorizontalCoordinates h1 = HorizontalCoordinates.of(Math.PI/4, Math.PI/6);
        HorizontalCoordinates center1 = HorizontalCoordinates.of(0,0);
        StereographicProjection s = new StereographicProjection(center1);
        CartesianCoordinates a1 = s.circleCenterForParallel(h1);
        assertEquals(0, a1.x(), 1e-10);
        assertEquals(2, a1.y(), 1e-10);
    }

    @Test
    void circleRadiusForParallelWorks2(){
        HorizontalCoordinates h2 = HorizontalCoordinates.of(Math.PI/2, Math.PI/2);
        HorizontalCoordinates center2 = HorizontalCoordinates.of(Math.PI/4, Math.PI/4);
        StereographicProjection e2 = new StereographicProjection(center2);
        double rho1 = e2.circleRadiusForParallel(h2);
        assertEquals(0, rho1, 1e-10);
    }

    @Test
    void applyToAngle2(){
        HorizontalCoordinates center2 = HorizontalCoordinates.of(Math.PI/4, Math.PI/4);
        StereographicProjection e2 = new StereographicProjection(center2);
        double z = e2.applyToAngle(Math.PI/2);
       // System.out.println(z);
    }
    @Test
    void applyWorks3(){
        HorizontalCoordinates h1 = HorizontalCoordinates.of(Math.PI/4, Math.PI/6);
        HorizontalCoordinates center1 = HorizontalCoordinates.of(0,0);
        StereographicProjection e = new StereographicProjection(center1);
        double p = Math.sqrt(6);
        CartesianCoordinates a1 = CartesianCoordinates.of(p/(4+p), 2/(4+p));
        CartesianCoordinates c1 = e.apply(h1);
        assertEquals(a1.x(), c1.x(), 1e-8);
        assertEquals(a1.y(), c1.y(), 1e-8);

        HorizontalCoordinates h2 = HorizontalCoordinates.of(Math.PI/2, Math.PI/2);
        HorizontalCoordinates center2 = HorizontalCoordinates.of(Math.PI/4, Math.PI/4);
        StereographicProjection e2 = new StereographicProjection(center2);
        double p2 = Math.sqrt(2);
        CartesianCoordinates a2 = CartesianCoordinates.of(0, p2/(2+p2));
        CartesianCoordinates c2 = e2.apply(h2);
        assertEquals(a2.x(), c2.x(), 1e-8);
        assertEquals(a2.y(), c2.y(), 1e-8);
    }

    @Test
    void circleCenterForParallelWorks3(){
        HorizontalCoordinates h1 = HorizontalCoordinates.of(Math.PI/4, Math.PI/6);
        HorizontalCoordinates center1 = HorizontalCoordinates.of(0,0);
        StereographicProjection s = new StereographicProjection(center1);
        CartesianCoordinates a1 = s.circleCenterForParallel(h1);
        assertEquals(0, a1.x(), 1e-10);
        assertEquals(2, a1.y(), 1e-10);
    }

    @Test
    void circleRadiusForParallelWorks3(){
        HorizontalCoordinates h2 = HorizontalCoordinates.of(Math.PI/2, Math.PI/2);
        HorizontalCoordinates center2 = HorizontalCoordinates.of(Math.PI/4, Math.PI/4);
        StereographicProjection e2 = new StereographicProjection(center2);
        double rho1 = e2.circleRadiusForParallel(h2);
        assertEquals(0, rho1, 1e-10);
    }

    @Test
    void applyToAngle3(){
        HorizontalCoordinates center2 = HorizontalCoordinates.of(Math.PI/4, Math.PI/4);
        StereographicProjection e2 = new StereographicProjection(center2);
        double z = e2.applyToAngle(Math.PI/2);
    }

    /**
     * tests du prof
     */

    @Test
    void circleForEquatorIsALineWhenCenterIsOnEquatorToo() {
        HorizontalCoordinates h00 = HorizontalCoordinates.of(0, 0);
        var p = new StereographicProjection(h00);
        var c = p.circleCenterForParallel(h00);
        var r = p.circleRadiusForParallel(h00);
        assertEquals(0, c.x());
        assertTrue(Double.isInfinite(c.y()));
        assertTrue(Double.isInfinite(r));
    }

    @Test
    void circlesForParallelsAreConcentricWhenCenterIsOnPole() {
        HorizontalCoordinates h = HorizontalCoordinates.ofDeg(0, 90);
        var p = new StereographicProjection(h);
        var prevRadius = -0d;
        for (int i = 0; i < 10; i++) {
            HorizontalCoordinates parallel = HorizontalCoordinates.ofDeg(0, 90d - i);
            var c = p.circleCenterForParallel(parallel);
            var r = p.circleRadiusForParallel(parallel);
            assertEquals(0, c.x());
            assertEquals(0, c.y(), 1e-8);
            assertTrue(prevRadius < r);
            prevRadius = r;
        }
    }

    @Test
    void circleCenterWorksOnKnownValues() {
        var c = HorizontalCoordinates.ofDeg(218, 47);
        var p = new StereographicProjection(c);

        var h1 = HorizontalCoordinates.ofDeg(0, 77.35);
        var h2 = HorizontalCoordinates.ofDeg(0, -59.92);
        var h3 = HorizontalCoordinates.ofDeg(0, 48.16);
        var h4 = HorizontalCoordinates.ofDeg(0, 67.60);
        var h5 = HorizontalCoordinates.ofDeg(0, 60.92);

        assertEquals(0.3995117214732776, p.circleCenterForParallel(h1).y(), 1e-8);
        assertEquals(-5.09057610324184, p.circleCenterForParallel(h2).y(), 1e-8);
        assertEquals(0.4619445280874631, p.circleCenterForParallel(h3).y(), 1e-8);
        assertEquals(0.41185969509630954, p.circleCenterForParallel(h4).y(), 1e-8);
        assertEquals(0.42484284228350655, p.circleCenterForParallel(h5).y(), 1e-8);
    }

    @Test
    void circleRadiusWorksOnKnownValues() {
        var c = HorizontalCoordinates.ofDeg(218, 47);
        var p = new StereographicProjection(c);

        var h1 = HorizontalCoordinates.ofDeg(0, 77.35);
        var h2 = HorizontalCoordinates.ofDeg(0, -59.92);
        var h3 = HorizontalCoordinates.ofDeg(0, 48.16);
        var h4 = HorizontalCoordinates.ofDeg(0, 67.60);
        var h5 = HorizontalCoordinates.ofDeg(0, 60.92);

        assertEquals(0.1282862205640954, p.circleRadiusForParallel(h1), 1e-8);
        assertEquals(-3.7411249651227934, p.circleRadiusForParallel(h2), 1e-8);
        assertEquals(0.45182127263573607, p.circleRadiusForParallel(h3), 1e-8);
        assertEquals(0.23012889503059386, p.circleRadiusForParallel(h4), 1e-8);
        assertEquals(0.30276687752311254, p.circleRadiusForParallel(h5), 1e-8);
    }

    @Test
    void applyWorksOnKnownValues() {
        var c = HorizontalCoordinates.ofDeg(7, -8);
        var p = new StereographicProjection(c);

        var h1 = HorizontalCoordinates.ofDeg(194.776, -1.3687);
        var h2 = HorizontalCoordinates.ofDeg(323.862, 57.8918);
        var h3 = HorizontalCoordinates.ofDeg(27.340, 15.3776);
        var h4 = HorizontalCoordinates.ofDeg(167.208, -14.0298);
        var h5 = HorizontalCoordinates.ofDeg(91.850, -49.2648);

        assertEquals(-6.027159362737581, p.apply(h1).x(), 1e-8);
        assertEquals(-7.196643283588719, p.apply(h1).y(), 1e-8);
        assertEquals(-0.2870261650172454, p.apply(h2).x(), 1e-8);
        assertEquals(0.7050904403871205, p.apply(h2).y(), 1e-8);
        assertEquals(0.18034386905858862, p.apply(h3).x(), 1e-8);
        assertEquals(0.20901167654970165, p.apply(h3).y(), 1e-8);
        assertEquals(2.5315796074175525, p.apply(h4).x(), 1e-8);
        assertEquals(-2.829098199043636, p.apply(h4).y(), 1e-8);
        assertEquals(0.5586168229780969, p.apply(h5).x(), 1e-8);
        assertEquals(-0.6379295511304205, p.apply(h5).y(), 1e-8);
    }

    @Test
    void inverseApplyWorksOnKnownValues() {
        var c = HorizontalCoordinates.ofDeg(7, -8);
        var p = new StereographicProjection(c);

        var c1 = CartesianCoordinates.of(1.3157, 2.2248);
        var c2 = CartesianCoordinates.of(-0.7715, 2.4744);
        var c3 = CartesianCoordinates.of(-2.9761, -0.9206);
        var c4 = CartesianCoordinates.of(-0.3114, -2.2065);
        var c5 = CartesianCoordinates.of(-2.9714, 1.4337);
        var c6 = CartesianCoordinates.of(0,0);

        assertEquals(2.7798306016641288, p.inverseApply(c1).az(), 1e-8);
        assertEquals(0.7431497822670021, p.inverseApply(c1).alt(), 1e-8);
        assertEquals(3.564595263665816, p.inverseApply(c2).az(), 1e-8);
        assertEquals(0.8302218440696163, p.inverseApply(c2).alt(), 1e-8);
        assertEquals(3.85447206842368, p.inverseApply(c3).az(), 1e-8);
        assertEquals(-0.057186083723119924, p.inverseApply(c3).alt(), 1e-8);
        assertEquals(3.4000601082999165, p.inverseApply(c4).az(), 1e-8);
        assertEquals(-0.6945319987205353, p.inverseApply(c4).alt(), 1e-8);
        assertEquals(3.828042199469258, p.inverseApply(c5).az(), 1e-8);
        assertEquals(0.3625637559048031, p.inverseApply(c5).alt(), 1e-8);
        assertEquals(c.alt(),p.inverseApply(c6).alt(), 1e-8);
        assertEquals(c.az(), p.inverseApply(c6).az(), 1e-8);
    }

    @Test
    void centerIsProjectedToOrigin() {
        var rng = TestRandomizer.newRandom();
        for (int i = 0; i < TestRandomizer.RANDOM_ITERATIONS; i++) {
            var centerAzDeg = rng.nextDouble(0, 360);
            var centerAltDeg = rng.nextDouble(-89.9999, 90);
            var center = HorizontalCoordinates.ofDeg(centerAzDeg, centerAltDeg);
            var proj = new StereographicProjection(center);
            var projCenter = proj.apply(center);
            assertEquals(0, projCenter.x(), 1e-9);
            assertEquals(0, projCenter.y(), 1e-9);
        }
    }

    @Test
    void applyAndInverseApplyAreInverses() {
        var rng = TestRandomizer.newRandom();
        for (int i = 0; i < TestRandomizer.RANDOM_ITERATIONS; i++) {
            var centerAzDeg = rng.nextDouble(0, 360);
            var centerAltDeg = rng.nextDouble(-89.9999, 90);
            var center = HorizontalCoordinates.ofDeg(centerAzDeg, centerAltDeg);
            var proj = new StereographicProjection(center);
            for (int j = 0; j < TestRandomizer.RANDOM_ITERATIONS; j++) {
                var azDeg = rng.nextDouble(0, 360);
                var altDeg = rng.nextDouble(-90, 90);
                var hor = HorizontalCoordinates.ofDeg(azDeg, altDeg);
                var car = proj.apply(hor);
                var hor2 = proj.inverseApply(car);
                assertEquals(hor.azDeg(), hor2.azDeg(), 1e-8);
                assertEquals(hor.altDeg(), hor2.altDeg(), 1e-8);
            }
        }
    }

    @Test
    void applyToAngleWorksForAnglesOnTheHorizon() {
        var rng = TestRandomizer.newRandom();
        for (int i = 0; i < TestRandomizer.RANDOM_ITERATIONS; i++) {
            var centerAzDeg = rng.nextDouble(60, 300);
            var center = HorizontalCoordinates.ofDeg(centerAzDeg, 0);
            var angleDeg = rng.nextDouble(60);
            var proj = new StereographicProjection(center);
            var l = HorizontalCoordinates.ofDeg(centerAzDeg - angleDeg / 2d, 0);
            var r = HorizontalCoordinates.ofDeg(centerAzDeg + angleDeg / 2d, 0);
            var pL = proj.apply(l);
            var pR = proj.apply(r);
            var pAngle = proj.applyToAngle(Math.toRadians(angleDeg));
            assertEquals(pR.x() - pL.x(), pAngle, 1e-8);
        }
    }

    @Test
    void spEqualsThrowsUOE() {
        assertThrows(UnsupportedOperationException.class, () -> {
            var c = new StereographicProjection(HorizontalCoordinates.of(0, 0));
            c.equals(c);
        });
    }

    @Test
    void spHashCodeThrowsUOE() {
        assertThrows(UnsupportedOperationException.class, () -> {
            new StereographicProjection(HorizontalCoordinates.of(0, 0)).hashCode();
        });
    }
}