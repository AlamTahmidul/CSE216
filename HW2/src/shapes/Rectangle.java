import java.util.List;

public class Rectangle extends Quadrilateral implements SymmetricTwoDShape {

    public Rectangle(List<TwoDPoint> vertices) {
        super(vertices);
    }

    /**
     * The center of a rectangle is calculated to be the point of intersection of its diagonals.
     *
     * @return the center of this rectangle.
     */
    @Override
    public Point center() {
        List<TwoDPoint> points = getPosition();
        double[] diag1p1 = points.get(0).coordinates();
        double[] diag1p2 = points.get(2).coordinates();
        double x1 = (diag1p1[0] + diag1p2[0]) / 2.0;
        double y1 = (diag1p1[1] + diag1p2[1]) / 2.0;
        return new Point() {
            @Override
            public double[] coordinates() {
                return new double[]{x1, y1};
            }
        };
    }

    @Override
    public boolean isMember(List<? extends Point> vertices) {
        List<TwoDPoint> points = getPosition();
        return vertices.size() == 4 &&
                (points.get(0).coordinates()[0] == points.get(3).coordinates()[0] &&
                        points.get(0).coordinates()[1] == points.get(1).coordinates()[1] &&
                        points.get(1).coordinates()[0] == points.get(2).coordinates()[0] &&
                        points.get(2).coordinates()[1] == points.get(3).coordinates()[1]); // CHECK
    }

    @Override
    public double area() {
        double[] lengths = getSideLengths();
        return (lengths[0] * lengths[1]);
    }
}
