import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Square extends Rectangle implements Snappable {

    // CHECk: this constructor must NOT be changed. Instead, resolve the
    // error by adding code elsewhere.
    public Square(List<TwoDPoint> vertices) {
        super(vertices);
    }

    /**
     * Given a list of vertices assumed to be provided in a counterclockwise order in a two-dimensional plane, checks
     * whether or not they constitute a valid square.
     *
     * @param vertices the specified list of vertices in a counterclockwise order
     * @return <code>true</code> if the four vertices can form a square, <code>false</code> otherwise.
     */
    @Override
    public boolean isMember(List<? extends Point> vertices) {
        return vertices.size() == 4 &&
                DoubleStream.of(getSideLengths()).boxed().collect(Collectors.toSet()).size() == 1;
    }

    /**
     * Snaps the sides of the square such that each corner (x,y) is modified to be a corner (x',y') where x' is the
     * the integer value closest to x and y' is the integer value closest to y. This, of course, may change the shape
     * to a general quadrilateral, hence the return type. The only exception is when the square is positioned in a way
     * where this approximation will lead it to vanish into a single point. In that case, a call to {@link #snap()}
     * will not modify this square in any way.
     */
    @Override
    public Quadrilateral snap() {
        List<TwoDPoint> vertices = getPosition();
        int i = 0;
        for (TwoDPoint point : vertices) {
            double x = point.coordinates()[0];
            double y = point.coordinates()[1];
            TwoDPoint newPoint = new TwoDPoint((int) Math.round(x),
                    (int) Math.round(y));
            vertices.set(i, newPoint);
            i++;
        }
        return new Quadrilateral(vertices);
        // CHECk
    }
}
