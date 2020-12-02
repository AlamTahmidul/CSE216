import java.util.Map;
import java.util.TreeMap;

public class SparsePolynomial implements Polynomial {
    private final String polynomialString;
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

    public TreeMap<Integer, Integer> getPolynomialMap() {
        return polynomialMap;
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
        if (!polynomialMap.containsKey(d))
            return 0;
        return polynomialMap.get(d);
    }

    /**
     * @return true if the polynomial represents the zero constant
     */
    @Override
    public boolean isZero() {
        for (Map.Entry<Integer, Integer> entry : polynomialMap.entrySet())
            if (entry.getKey() != 0 || entry.getValue() != 0)
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
            TreeMap<Integer, Integer> tm = new TreeMap<Integer, Integer>();
            for (int i = 0; i <= dp.degree(); i++) {
                if (polynomialMap.containsKey(i)) {
                    tm.put(i, polynomialMap.get(i) + dp.getCoefficient(i));
                } else {
                    tm.put(i, dp.getCoefficient(i));
                }
            }
            for (Map.Entry<Integer, Integer> entry : polynomialMap.entrySet()) {
                tm.putIfAbsent(entry.getKey(), entry.getValue());
            }
            SparsePolynomial sp = new SparsePolynomial(polynomialString);
            sp.setPolynomialMap(tm);
            return sp;
        } else if (q instanceof SparsePolynomial) {
            // TODO: Sparse + Sparse
            SparsePolynomial sp = (SparsePolynomial) q;
            TreeMap<Integer, Integer> tm = new TreeMap<Integer, Integer>();
            for (Map.Entry<Integer, Integer> entry : sp.getPolynomialMap().entrySet()) {
                if (polynomialMap.containsKey(entry.getKey()))
                    tm.put(entry.getKey(), polynomialMap.get(entry.getKey()) + entry.getValue());
                else
                    tm.putIfAbsent(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<Integer, Integer> entry : polynomialMap.entrySet()) {
                tm.putIfAbsent(entry.getKey(), entry.getValue());
            }
            SparsePolynomial newSp = new SparsePolynomial(polynomialString);
            newSp.setPolynomialMap(tm);
            return newSp;
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
        if (q == null) throw new NullPointerException("Other polynomial can't be null");
        if (q instanceof DensePolynomial) {
            // Check: Sparse * Dense
            DensePolynomial dp = (DensePolynomial) q;
            if (isZero() || dp.isZero()) return new SparsePolynomial("0");
            TreeMap<Integer, Integer> tm = new TreeMap<Integer, Integer>();
            for (Map.Entry<Integer, Integer> entry : polynomialMap.entrySet()) {
                for (int i = 0; i <= dp.degree(); i++) {
                    if (tm.containsKey(i + entry.getKey())) {
                        tm.replace(i + entry.getKey(),
                                tm.get(i + entry.getKey()) + (dp.getCoefficient(i) * entry.getValue()));
                    } else {
                        tm.putIfAbsent(i + entry.getKey(), dp.getCoefficient(i) * entry.getValue());
                    }
                }
            }
            SparsePolynomial sp = new SparsePolynomial(polynomialString);
            sp.setPolynomialMap(tm);
            return sp;
        } else if (q instanceof SparsePolynomial) {
            // Check: Sparse * Sparse
            SparsePolynomial sp = (SparsePolynomial) q;
            if (sp.isZero() || isZero()) return new SparsePolynomial("0");
            TreeMap<Integer, Integer> tm = new TreeMap<Integer, Integer>();
            for (Map.Entry<Integer, Integer> entry : polynomialMap.entrySet()) {
                for (Map.Entry<Integer, Integer> otherEntry : sp.getPolynomialMap().entrySet()) {
                    if (tm.containsKey(entry.getKey() + otherEntry.getKey())) {
                        tm.replace(entry.getKey() + otherEntry.getKey(),
                                tm.get(entry.getKey() + otherEntry.getKey()) + (entry.getValue() * otherEntry.getValue()));
                    }  else {
                        tm.putIfAbsent(entry.getKey() + otherEntry.getKey(), entry.getValue() * otherEntry.getValue());
                    }
                }
            }
            SparsePolynomial newSp = new SparsePolynomial(polynomialString);
            newSp.setPolynomialMap(tm);
            return newSp;
        }
        throw new NullPointerException("Other polynomial is not a valid polynomial?");
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
        if (q == null) throw new NullPointerException("Other polynomial can't be null!");
        if (q instanceof DensePolynomial) {
            // TODO: Sparse - Dense
            DensePolynomial dp = (DensePolynomial) q;
            TreeMap<Integer, Integer> tm = new TreeMap<Integer, Integer>();
            for (int i = 0; i <= dp.degree(); i++) {
                if (polynomialMap.containsKey(i)) {
                    tm.put(i, polynomialMap.get(i) - dp.getCoefficient(i));
                } else {
                    tm.put(i, dp.getCoefficient(i));
                }
            }
            for (Map.Entry<Integer, Integer> entry : polynomialMap.entrySet()) {
                tm.putIfAbsent(entry.getKey(), entry.getValue());
            }
            SparsePolynomial sp = new SparsePolynomial(polynomialString);
            sp.setPolynomialMap(tm);
            return sp;
        } else if (q instanceof SparsePolynomial) {
            // TODO: Sparse - Sparse
            SparsePolynomial sp = (SparsePolynomial) q;
            TreeMap<Integer, Integer> tm = new TreeMap<Integer, Integer>();
            for (Map.Entry<Integer, Integer> entry : sp.getPolynomialMap().entrySet()) {
                if (polynomialMap.containsKey(entry.getKey()))
                    tm.put(entry.getKey(), polynomialMap.get(entry.getKey()) - entry.getValue());
                else
                    tm.putIfAbsent(entry.getKey(), entry.getValue() * -1);
            }
            for (Map.Entry<Integer, Integer> entry : polynomialMap.entrySet()) {
                tm.putIfAbsent(entry.getKey(), entry.getValue());
            }
            SparsePolynomial newSp = new SparsePolynomial(polynomialString);
            newSp.setPolynomialMap(tm);
            return newSp;
        }
        throw new NullPointerException("Other polynomial is not a valid polynomial");
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
     * @throws IllegalArgumentException if the user input is not a valid polynomial
     * @return {@literal true} if the class invariant holds, and {@literal false} otherwise.
     */
    @Override
    public boolean wellFormed() {
        if (polynomialString.length() == 0) return false;
        String[] split = polynomialString.split("[ +]+");
        if (split.length == 0) return false;
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
                else if (s2[0].contains("x") && s2[0].contains("-")) {
                    if (s2[0].split("[-x]+").length == 0) {
                        exp = 1;
                        coeff = -1;
                    } else if (Double.parseDouble(s2[0].split("[-x]+")[1]) % 1 != 0) return false;
                    else {
                        exp = 1;
                        coeff = Integer.parseInt(s2[0].split("[-x]+")[1]) * -1;
                    }
                }
                else if (s2[0].contains("x") && Double.parseDouble(s2[0].split("x")[0]) % 1 != 0) return false; // Case: 3.1x
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
        return toString().equals(that.toString());
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : polynomialMap.entrySet()) {
            if (entry.getValue() != 0) {
                if (entry.getKey() <= 1) {
                    if (entry.getKey() == 1)
                        if (entry.getValue() == 1)
                            s.insert(0, " + " + "x");
                        else
                            s.insert(0, " + " + entry.getValue() + "x");
                    else
                        s.insert(0, " + " + entry.getValue());
                } else {
                    s.insert(0, " + " + entry.getValue() + "x^" + entry.getKey());
                }
            }
        }
        if (s.length() == 0) return "0";
        return s.substring(3);
    }
}