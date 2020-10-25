import java.util.List;

// Completed : a missing interface method must be implemented in this class to
// make it compile. This must be in terms of volume().
public class Cuboid implements ThreeDShape {

    private final ThreeDPoint[] vertices = new ThreeDPoint[8];

    /**
     * Creates a cuboid out of the list of vertices. It is expected that the vertices are provided in
     * the order as shown in the figure given in the homework document (from v0 to v7).
     *
     * @param vertices the specified list of vertices in three-dimensional space.
     */
    public Cuboid(List<ThreeDPoint> vertices) {
        if (vertices.size() != 8)
            throw new IllegalArgumentException(String.format("Invalid set of vertices specified for %s",
                    this.getClass().getName()));
        int n = 0;
        for (ThreeDPoint p : vertices) this.vertices[n++] = p;
    }

    @Override
    public double volume() {
        double x1 = this.vertices[0].coordinates()[0];
        double x2 = this.vertices[0].coordinates()[1];
        return Math.pow(Math.abs(x2 - x1), 3);
    }

    @Override
    public ThreeDPoint center() {
        double xSum = 0.0,ySum = 0.0 ,zSum = 0.0;
        for (ThreeDPoint point : vertices) {
            xSum += point.coordinates()[0];
            ySum += point.coordinates()[1];
            zSum += point.coordinates()[2];
        }
        return (new ThreeDPoint(xSum / 8.0, ySum / 8.0, zSum / 8.0));
    }

    @Override
    public int compareTo(ThreeDShape o) {
        return Double.compare(this.volume(), o.volume());
    }
}
