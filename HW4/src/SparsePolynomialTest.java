import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SparsePolynomialTest {

    @Test
    void testDegree() {
        Polynomial p1 = new SparsePolynomial("x");
        Polynomial p2 = new SparsePolynomial("16x^3");
        Polynomial p3 = new SparsePolynomial("1");

        assertEquals(p1.degree(), 1);
        assertEquals(p2.degree(), 3);
        assertEquals(p3.degree(), 0);
    }

    @Test
    void testGetCoefficient() {
        Polynomial p1 = new SparsePolynomial("x");
        Polynomial p2 = new SparsePolynomial("16x^3");
        Polynomial p3 = new SparsePolynomial("1");

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
        Polynomial p1 = new SparsePolynomial("0");
        Polynomial p2 = new SparsePolynomial("x");
        Polynomial p3 = new SparsePolynomial("1");
        Polynomial p4 = new SparsePolynomial("4x^2 + 3x + 2");

        assertTrue(p1.isZero());
        assertFalse(p2.isZero());
        assertFalse(p3.isZero());
        assertFalse(p4.isZero());
    }

    @Test
    void testAdd() {
        Polynomial p1 = new SparsePolynomial("4x^2");
        Polynomial p2 = new SparsePolynomial("3x");
        Polynomial p3 = new SparsePolynomial("0");
        Polynomial p4 = new DensePolynomial("5x");
        Polynomial p5 = new DensePolynomial("3");

        assertEquals(p1.add(p2), new SparsePolynomial("4x^2 + 3x"));
        assertEquals(p2.add(p1), new SparsePolynomial("4x^2 + 3x"));
        assertEquals(p1.add(p3), p3.add(p1));
        assertEquals(p2.add(p3), p3.add(p2));
        assertEquals(p1.add(p2).add(p4), new SparsePolynomial("4x^2 + 8x"));
        assertEquals(p1.add(p2).add(p4).add(p5), new SparsePolynomial("4x^2 + 8x + 3"));
        assertThrows(NullPointerException.class, () -> p1.add(null));
        assertThrows(NullPointerException.class, () -> p1.add(p2).add(null));
    }

    @Test
    void testMultiply() {
        Polynomial p1 = new SparsePolynomial("3x^2");
        Polynomial p2 = new DensePolynomial("5x + 3");
        Polynomial p3 = new DensePolynomial("0");
        Polynomial p4 = new SparsePolynomial("0");

        assertEquals(p1.multiply(p2), new SparsePolynomial("15x^3 + 9x^2"));
        assertEquals(p1.multiply(p3), new SparsePolynomial("0"));
        assertTrue(p2.multiply(p3).multiply(p1).isZero());
        assertTrue(p3.multiply(p4).isZero());
    }

    @Test
    void testSubtract() {
        Polynomial p1 = new SparsePolynomial("4x^2");
        Polynomial p2 = new SparsePolynomial("3x");
        Polynomial p3 = new SparsePolynomial("0");
        Polynomial p4 = new DensePolynomial("5x");
        Polynomial p5 = new DensePolynomial("3");

        assertEquals(p1.subtract(p2), new SparsePolynomial("4x^2 + -3x"));
        assertEquals(p2.subtract(p1), new SparsePolynomial("-4x^2 + 3x"));
        assertEquals(p1.subtract(p3), new SparsePolynomial("4x^2"));
        assertEquals(p2.subtract(p3), new SparsePolynomial("3x"));
        assertEquals(p1.subtract(p2).subtract(p4), new SparsePolynomial("4x^2 + -8x"));
        assertEquals(p1.subtract(p2).subtract(p4).subtract(p5), new SparsePolynomial("4x^2 + -8x + -3"));
        assertThrows(NullPointerException.class, () -> p1.add(null));
        assertThrows(NullPointerException.class, () -> p1.add(p2).add(null));
    }

    @Test
    void testMinus() {
        Polynomial p1 = new SparsePolynomial("0");
        Polynomial p2 = new SparsePolynomial("x");
        Polynomial p3 = new SparsePolynomial("3x");
        Polynomial p4 = new SparsePolynomial("46x^2");
        Polynomial p5 = new SparsePolynomial("46x^2 + 9x + 11");

        assertEquals(p1.minus(), new SparsePolynomial("0"));
        assertEquals(p2.minus(), new SparsePolynomial("-x"));
        assertEquals(p3.minus(), new SparsePolynomial("-3x"));
        assertEquals(p4.minus(), new SparsePolynomial("-46x^2"));
        assertEquals(p5.minus(), new SparsePolynomial("-46x^2 + -9x + -11"));
    }

    @Test
    void testWellFormed() {
        assertThrows(IllegalArgumentException.class, () -> new SparsePolynomial(""));
        assertThrows(IllegalArgumentException.class, () -> new SparsePolynomial("3.5x^7 + 3x^6"));
        assertThrows(IllegalArgumentException.class, () -> new SparsePolynomial("3/5x^7 + 3x^6"));
        assertThrows(IllegalArgumentException.class, () -> new SparsePolynomial("2x^3 + + 3x + 1"));
        assertThrows(IllegalArgumentException.class, () -> new SparsePolynomial("2x^3.1 + 3x + 1"));
        assertThrows(IllegalArgumentException.class, () -> new SparsePolynomial("2.1x^3 + 3x + 1"));
        assertThrows(IllegalArgumentException.class, () -> new SparsePolynomial("-1.1"));
        assertThrows(IllegalArgumentException.class, () -> new SparsePolynomial("abcde"));

        assertEquals((new SparsePolynomial("3x^7 + 3x^8")).toString(), "3x^8 + 3x^7");
    }
}