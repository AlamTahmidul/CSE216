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
        return false;
    }

    /**
     * Returns a polynomial by adding the parameter to the current instance. Neither the current instance nor the
     * parameter are modified.
     *
     * @param q the non-null polynomial to add to <code>this</code>
     * @return <code>this + </code>q
     * @throws NullPointerException if q is null
     */
    @Override
    public Polynomial add(Polynomial q) {
        return null;
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
                polynomialMap.put(temp, Integer.parseInt(s2[1]));
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
}
