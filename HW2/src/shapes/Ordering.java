import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ordering {

    static class XLocationComparator implements Comparator<TwoDShape> {
        @Override
        public int compare(TwoDShape o1, TwoDShape o2) {
            return 0; // TODO
        }
    }

    static class AreaComparator implements Comparator<SymmetricTwoDShape> {
        @Override
        public int compare(SymmetricTwoDShape o1, SymmetricTwoDShape o2) {
            return Double.compare(o1.area(), o2.area()); // Check
        }
    }

    static class SurfaceAreaComparator implements Comparator<ThreeDShape> {
        @Override
        public int compare(ThreeDShape o1, ThreeDShape o2) {
            if (o1.getClass() != o2.getClass()) {
                if (o1 instanceof Cuboid) {
                    return Double.compare(((Cuboid) o1).surfaceArea(),
                            ((Cuboid) o2).surfaceArea());
                } else if (o1 instanceof Sphere) {
                    return Double.compare(((Sphere) o1).surfaceArea(),
                            ((Sphere) o2).surfaceArea());
                }
            } else {
                if (o1 instanceof )
            }
            return 0; // TODO
        }
    }

    // TODO: there's a lot wrong with this method. correct it so that it can work properly with generics.
    static <E> void copy(List<E> source, List<E> destination) {
        destination.addAll(source);
    }

    public static void main(String[] args) {
        List<TwoDShape> shapes = new ArrayList<>();
        List<SymmetricTwoDShape> symmetricshapes = new ArrayList<>();
        List<ThreeDShape> threedshapes = new ArrayList<>();

        /*
         * uncomment the following block and fill in the "..." constructors to create actual instances. If your
         * implementations are correct, then the code should compile and yield the expected results of the various
         * shapes being ordered by their smallest x-coordinate, area, volume, surface area, etc. */

        /*
        symmetricshapes.add(new Rectangle(...));
        symmetricshapes.add(new Square(...));
        symmetricshapes.add(new Circle(...));

        copy(symmetricshapes, shapes); // note-1 //
        shapes.add(new Quadrilateral(new ArrayList<>()));
         */

        // sorting 2d shapes according to various criteria
        shapes.sort(new XLocationComparator());
        symmetricshapes.sort(new XLocationComparator());
        symmetricshapes.sort(new AreaComparator());

        // sorting 3d shapes according to various criteria
        Collections.sort(threedshapes);
        threedshapes.sort(new SurfaceAreaComparator());

        /*
         * if your changes to copy() are correct, uncommenting the following block will also work as expected note that
         * copy() should work for the line commented with 'note-1' while at the same time also working with the lines
         * commented with 'note-2' and 'note-3'. */

        /*
        List<Number> numbers = new ArrayList<>();
        List<Double> doubles = new ArrayList<>();
        Set<Square>        squares = new HashSet<>();
        Set<Quadrilateral> quads   = new LinkedHashSet<>();

        copy(doubles, numbers); // note-2 //
        copy(squares, quads);   // note-3 //
        */
    }
}