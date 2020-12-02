import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DensePolynomialTest {

    @Test
    void testDegree() {
        Polynomial p1 = new DensePolynomial("x");
        Polynomial p2 = new DensePolynomial("16x^3");
        Polynomial p3 = new DensePolynomial("1");

        assertEquals(p1.degree(), 1);
        assertEquals(p2.degree(), 3);
        assertEquals(p3.degree(), 0);
    }

    @Test
    void testGetCoefficient() {
        Polynomial p1 = new DensePolynomial("x");
        Polynomial p2 = new DensePolynomial("16x^3");
        Polynomial p3 = new DensePolynomial("1");

        assertEquals(p1.getCoefficient(2), 0);
        assertEquals(p1.getCoefficient(1), 1);
        assertEquals(p1.getCoefficient(0), 0);
        assertEquals(p2.getCoefficient(3), 16);
        assertEquals(p2.getCoefficient(2), 0);
        assertEquals(p2.getCoefficient(1), 0);
        assertEquals(p2.getCoefficient(0), 0);
        assertEquals(p3.getCoefficient(0), 1);
        assertEquals(p3.getCoefficient(1), 0);
    }

    @Test
    void testIsZero() {
        Polynomial p1 = new DensePolynomial("0");
        Polynomial p2 = new DensePolynomial("x");
        Polynomial p3 = new DensePolynomial("1");
        Polynomial p4 = new DensePolynomial("4x^2 + 3x + 2");

        assertTrue(p1.isZero());
        assertFalse(p2.isZero());
        assertFalse(p3.isZero());
        assertFalse(p4.isZero());
    }

    @Test
    void testAdd() {
        Polynomial p1 = new DensePolynomial("4x^2");
        Polynomial p2 = new DensePolynomial("3x");
        Polynomial p3 = new DensePolynomial("0");
        Polynomial p4 = new SparsePolynomial("5x");
        Polynomial p5 = new SparsePolynomial("3");
        Polynomial p6 = new SparsePolynomial("2x^-2");

        assertEquals(p1.add(p2), p2.add(p1));
        assertEquals(p2.add(p1), new DensePolynomial("4x^2 + 3x"));
        assertEquals(p1.add(p3), p3.add(p1));
        assertEquals(p2.add(p3), p3.add(p2));
        assertEquals(p2.add(p4), new DensePolynomial("8x"));

        assertEquals(p1.add(p2).add(p4).add(p5).toString(), (new SparsePolynomial("4x^2 + 8x + 3")).toString());

        assertThrows(IllegalArgumentException.class, () -> p1.add(p6));
        assertThrows(NullPointerException.class, () -> p1.add(null));
        assertThrows(NullPointerException.class, () -> p1.add(p2).add(null));
    }

    @Test
    void testMultiply() {
    }

    @Test
    void testSubtract() {
        Polynomial p1 = new DensePolynomial("4x^2");
        Polynomial p2 = new DensePolynomial("3x");
        Polynomial p3 = new DensePolynomial("0");
        Polynomial p4 = new SparsePolynomial("5x");
        Polynomial p5 = new SparsePolynomial("3");
        Polynomial p6 = new SparsePolynomial("2x^-2");

        assertEquals(p1.subtract(p2).toString(), "4x^2 + -3x");
        assertEquals(p2.subtract(p1).toString(), "-4x^2 + 3x");
        assertEquals(p1.subtract(p3).toString(), p1.toString());
        assertEquals(p2.subtract(p3).toString(), p2.toString());
        assertEquals(p2.subtract(p4).toString(), "-2x");

        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p6));
        assertThrows(NullPointerException.class, () -> p1.subtract(null));
        assertThrows(NullPointerException.class, () -> p1.subtract(p2).subtract(null));
    }

    @Test
    void testMinus() {
        Polynomial p1 = new DensePolynomial("0");
        Polynomial p2 = new DensePolynomial("x");
        Polynomial p3 = new DensePolynomial("3x");
        Polynomial p4 = new DensePolynomial("46x^2");
        Polynomial p5 = new DensePolynomial("46x^2 + 9x + 11");

        assertEquals(p1.minus().toString(), (new DensePolynomial("0")).toString());
        assertEquals(p2.minus().toString(), (new DensePolynomial("-x")).toString());
        assertEquals(p3.minus().toString(), (new DensePolynomial("-3x")).toString());
        assertEquals(p4.minus().toString(), (new DensePolynomial("-46x^2")).toString());
        assertEquals(p5.minus().toString(), (new DensePolynomial("-46x^2 + -9x + -11")).toString());
    }

    @Test
    void wellFormed() {
        assertThrows(IllegalArgumentException.class, () -> new DensePolynomial(""));
        assertThrows(IllegalArgumentException.class, () -> new DensePolynomial("3.5x^7 + 3x^6"));
        assertThrows(IllegalArgumentException.class, () -> new DensePolynomial("3/5x^7 + 3x^6"));
        assertThrows(IllegalArgumentException.class, () -> new DensePolynomial("2x^3 + + 3x + 1"));
        assertThrows(IllegalArgumentException.class, () -> new DensePolynomial("2x^3.1 + 3x + 1"));
        assertThrows(IllegalArgumentException.class, () -> new DensePolynomial("2.1x^3 + 3x + 1"));
        assertThrows(IllegalArgumentException.class, () -> new DensePolynomial("-1.1"));

        assertEquals((new DensePolynomial("3x^8 + 3x^7")).toString(), "3x^8 + 3x^7");
    }
}