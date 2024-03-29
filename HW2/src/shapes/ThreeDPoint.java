/**
 * An unmodifiable point in the three-dimensional space. The coordinates are specified by exactly three doubles (its
 * <code>x</code>, <code>y</code>, and <code>z</code> values).
 */
public class ThreeDPoint implements Point {

    private double x = 0.0;
    private double y = 0.0;
    private double z = 0.0;

    public ThreeDPoint(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        // CHECK
    }

    /**
     * @return the (x,y,z) coordinates of this point as a <code>double[]</code>.
     */
    @Override
    public double[] coordinates() {
        return new double[]{x, y, z}; // CHECk
    }
}
