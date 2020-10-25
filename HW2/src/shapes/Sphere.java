import java.util.*;

public class Sphere implements ThreeDShape {
    private ThreeDPoint vertex = new ThreeDPoint(0.0, 0.0, 0.0);
    private double radius = 0.0;

    public Sphere(double radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Invalid Radius!");
        }
        this.radius = radius;
    }

    public Sphere(double x, double y, double z, double r) {
        if (r < 0) {
            throw new IllegalArgumentException("Invalid Radius!");
        }
        this.vertex = new ThreeDPoint(x, y, z);
        this.radius = r;
    }

    @Override
    public Point center() {
        return new Point() {
            @Override
            public double[] coordinates() {
                return vertex.coordinates();
            }
        };
    }

    @Override
    public double volume() {
        return (4.0 / 3.0) * Math.PI * Math.pow(this.radius, 3);
    }

    public double surfaceArea() {
        return 4.0 * Math.PI * Math.pow(this.radius, 2);
    }

    @Override
    public int compareTo(ThreeDShape o) {
        return Double.compare(this.volume(), o.volume());
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setVertex(ThreeDPoint vertex) {
        this.vertex = vertex;
    }

}
