import java.util.Map;
import java.util.TreeMap;

public class SparsePolynomial implements Polynomial {
    private String polynomialString = "";
    private TreeMap<Integer, Integer> polynomialMap;

    public SparsePolynomial(String polynomialString) {
        this.polynomialString = polynomialString;
        if (!wellFormed()) {
            throw new IllegalArgumentException("Invalid Argument: " + polynomialString);
        }
    }

    public void setPolynomialMap(TreeMap<Integer, Integer> polynomialMap) {
        this.polynomialMap = polynomialMap;
    }

    public String getPolynomialString() {
        return polynomialString;
    }

    public void setPolynomialString(String polynomialString) {
        this.polynomialString = polynomialString;
    }

    public TreeMap<Integer, Integer> getPolynomialMap() {
        return polynomialMap;
    }

    public static void main(String[] args) {
        SparsePolynomial sp = new SparsePolynomial("3x + 2");
        System.out.println("sp (Original): 3x + 2");
        System.out.println("sp (toString): " + sp.toString());
        SparsePolynomial sp2 = new SparsePolynomial("3x^2 + 2");
        System.out.println("sp2 (Original): 3x^2 + 2");
        System.out.println("sp2 (toString): " + sp2.toString());
        System.out.println("sp2 (toString_minus): " + sp2.minus().toString());
//        SparsePolynomial sp3 = new SparsePolynomial("");
    }

    /**
     * Returns the degree of the polynomial.
     *
     * @return the largest exponent with a non-zero coefficient.  If all terms have zero exponents, it returns 0.
     */
    @Override
    public int degree() {
        return polynomialMap.firstKey();
    }

    /**
     * Returns the coefficient corresponding to the given exponent.  Returns 0 if there is no term with that exponent
     * in the polynomial.
     *
     * @param d the exponent whose coefficient is returned.
     * @return the coefficient of the term of whose exponent is d.
     */
    @Override
    public int getCoefficient(int d) {
        return polynomialMap.get(d);
    }

    /**
     * @return true if the polynomial represents the zero constant
     */
    @Override
    public boolean isZero() {
        for (Map.Entry<Integer, Integer> entry : polynomialMap.entrySet())
            if (entry.getKey() != 0 && entry.getValue() != 0)
                return false;
        return true;
    }

    /**
     * Returns a polynomial by adding the parameter to the current instance. Neither the current instance nor the
     * parameter are modified.
     *
     * @param q the non-null polynomial to add to <code>this</code>
     * @return <code>this + </code>q
     * @throws NullPointerException if q is null or if q is not a valid instance of Dense/SparsePolynomial
     */
    @Override
    public Polynomial add(Polynomial q) {
        if (q == null) throw new NullPointerException("Other polynomial can't be null!");
        if (q instanceof DensePolynomial) {
            // TODO: Sparse + Dense
            DensePolynomial dp = (DensePolynomial) q;
            
        } else if (q instanceof SparsePolynomial) {
            // TODO: Sparse + Sparse
            SparsePolynomial sp = (SparsePolynomial) q;
        }
        throw new NullPointerException("Other polynomial is not a valid polynomial");
    }

    /**
     * Returns a polynomial by multiplying the parameter with the current instance.  Neither the current instance nor
     * the parameter are modified.
     *
     * @param q the polynomial to multiply with <code>this</code>
     * @return <code>this * </code>q
     * @throws NullPointerException if q is null
     */
    @Override
    public Polynomial multiply(Polynomial q) {
        return null;
    }

    /**
     * Returns a  polynomial by subtracting the parameter from the current instance. Neither the current instance nor
     * the parameter are modified.
     *
     * @param q the non-null polynomial to subtract from <code>this</code>
     * @return <code>this - </code>q
     * @throws NullPointerException if q is null
     */
    @Override
    public Polynomial subtract(Polynomial q) {
        return null;
    }

    /**
     * Returns a polynomial by negating the current instance. The current instance is not modified.
     *
     * @return -this
     */
    @Override
    public Polynomial minus() {
        SparsePolynomial sp = new SparsePolynomial(polynomialString);
        TreeMap<Integer, Integer> newPoly = new TreeMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : polynomialMap.entrySet()) {
            newPoly.put(entry.getKey(), entry.getValue() * -1);
        }
        sp.setPolynomialMap(newPoly);
        return sp;
    }

    /**
     * Checks if the class invariant holds for the current instance.
     *
     * @return {@literal true} if the class invariant holds, and {@literal false} otherwise.
     */
    @Override
    public boolean wellFormed() {
        if (polynomialString.length() == 0) return false;
        String[] split = polynomialString.split("[ +]+");
        String[] operatorCheck = polynomialString.split("[ \\da-z^-]+");
        if (split.length != operatorCheck.length && operatorCheck.length > 0) return false;

        polynomialMap = new TreeMap<Integer, Integer>();
        for (String s : split) {
            String[] s2 = s.split("\\^");
            if (s2.length > 1) {
                int temp;
                if (Double.parseDouble(s2[1]) % 1 != 0) return false;
                else if (s2[0].split("x").length == 0) temp = 1;
                else if (Double.parseDouble(s2[0].split("x")[0]) % 1 != 0 || Double.parseDouble(s2[1].split("x")[0]) % 1 != 0) return false;
                else temp = Integer.parseInt(s2[0].split("x")[0]);
                polynomialMap.put(Integer.parseInt(s2[1]), temp);
            } else {
                int exp, coeff;
                if (s2[0].split("x").length == 0) exp = coeff = 1; // Case: x
                else if (s2[0].contains("x") && Double.parseDouble(s2[0].split("x")[0]) % 1 != 0) return false; // Case: 3.0x
                else if ( s2[0].contains("x") ) { // Case: 3.0x
                    exp = 1;
                    coeff = Integer.parseInt(s2[0].split("x")[0]);
                }
                else { // Case: 3.0
                    exp = 0;
                    if (Double.parseDouble(s2[0].split("X")[0]) % 1 != 0) return false;
                    else coeff = Integer.parseInt(s2[0].split("x")[0]);
                }
                polynomialMap.put(exp, coeff);
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SparsePolynomial that = (SparsePolynomial) o;
        return polynomialMap.equals(that.polynomialMap);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : polynomialMap.entrySet()) {
            if (entry.getKey() <= 1) {
                if (entry.getKey() == 1)
                    s.insert(0, " + " + entry.getValue() + "x");
                else
                    s.insert(0, " + " +entry.getValue());
            } else {
                s.insert(0, " + " + entry.getValue() + "x^" + entry.getKey());
            }
        }
        return s.toString().substring(3);
    }
}
