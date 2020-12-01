import java.util.Arrays;
import java.util.Map;

public class DensePolynomial implements Polynomial {
    private int[] coefficients;
    private String polynomialString = "";

    public DensePolynomial(String polynomialString) {
        this.polynomialString = polynomialString;
        if (!wellFormed())
            throw new IllegalArgumentException("Invalid Argument: " + polynomialString);
    }

    public void setCoefficients(int[] coefficients) {
        this.coefficients = coefficients;
    }

    public static void main(String[] args) {
//        DensePolynomial dp = new DensePolynomial("2x^3 + + 3x + -1");
        DensePolynomial dp2 = new DensePolynomial("1");
        System.out.println("Degree: " + dp2.degree());
        System.out.println("DP2: " + dp2);
//        DensePolynomial dp3 = new DensePolynomial("");
        DensePolynomial dp4 = new DensePolynomial("3x + 2");
        System.out.println("Degree: " + dp4.degree());
        System.out.println("DP4: " + dp4);
//        TODO: DensePolynomial dp5 = new DensePolynomial("2x^3 + + 3x + -1 1");
        DensePolynomial dp6 = new DensePolynomial("2x^10 + 3x^6 + -1");
        System.out.println("Degree: " + dp6.degree());
        System.out.println("DP6: " + dp6);
    }

    /**
     * Returns the degree of the polynomial.
     *
     * @return the largest exponent with a non-zero coefficient.  If all terms have zero exponents, it returns 0.
     */
    @Override
    public int degree() {
        return coefficients.length <= 1 ? 0 : coefficients.length - 1;
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
        return (d < 0 || d > coefficients.length) ? 0 : coefficients[d];
    }

    /**
     * @return true if the polynomial represents the zero constant
     */
    @Override
    public boolean isZero() {
        for (int i : coefficients)
            if (i != 0)
                return false;
        return true;
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
        if (q == null) throw new NullPointerException("Other Polynomial cannot be null");
        if (q instanceof SparsePolynomial) {
            SparsePolynomial sp =  (SparsePolynomial) q;
            int[] newCoeff = new int[coefficients.length];
            if (sp.getPolynomialMap().lastKey() < 0) throw new IllegalArgumentException("q contains negative exponents");
            else {
                int i = 0;
                for (Map.Entry<Integer, Integer> entry : sp.getPolynomialMap().entrySet()) {
                    if (entry.getKey() <= coefficients.length - 1) {
                        int temp = coefficients[entry.getKey()] + entry.getValue();
                        newCoeff[entry.getKey()] = temp;
                    } else if (i == 0) {
                        newCoeff = new int[entry.getKey()];
                    } else {
                        newCoeff[entry.getKey()] = entry.getValue();
                    }
                    i++;
                }
                DensePolynomial dp = new DensePolynomial(polynomialString);
                dp.setCoefficients(newCoeff);
//                dp.setPolynomialString(dp.toString());
            }
        } else if (q instanceof DensePolynomial) {
            DensePolynomial qDense = (DensePolynomial) q;
            int max = Math.max(degree(), qDense.degree());
            int[] newCoeff = new int[max];
            if (degree() > qDense.degree())  {
                int i;
                for (i = 0; i <= qDense.degree(); i++) {
                    newCoeff[i] = coefficients[i] + qDense.getCoefficient(i);
                }
                for (; i <= degree(); i++) {
                    newCoeff[i] = coefficients[i];
                }
            } else {
                int i;
                for (i = 0; i <= degree(); i++) {
                    newCoeff[i] = coefficients[i] + qDense.getCoefficient(i);
                }
                for (; i <= qDense.degree(); i++) {
                    newCoeff[i] = qDense.getCoefficient(i);
                }
            }
            DensePolynomial newPoly = new DensePolynomial(polynomialString);
            newPoly.setCoefficients(newCoeff);
            return newPoly;
        }
        throw new NullPointerException("Other polynomial is not a valid polynomial?");
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
        if (q == null) throw new NullPointerException("The other polynomial can't be null!");
        if (q instanceof SparsePolynomial) {
            // Check: Dense * Sparse
            SparsePolynomial sp = (SparsePolynomial) q;
            int[] newCoeff = new int[degree() + sp.degree()];
            for (Map.Entry<Integer, Integer> entry : sp.getPolynomialMap().entrySet()) {
                for (int i = 0; i <= degree(); i++) {
                    if (i + entry.getKey() < 0) throw new IllegalArgumentException("Dense*Sparse contains a negative " +
                            "exponent.");
                    newCoeff[i + entry.getKey()] += coefficients[i] * entry.getValue();
                }
            }
            DensePolynomial dp = new DensePolynomial(polynomialString);
            dp.setCoefficients(newCoeff);
            return dp;
        } else if (q instanceof DensePolynomial) {
            // Check: Dense * Dense
            DensePolynomial dp = (DensePolynomial) q;
            int[] newCoeff = new int[degree() + dp.degree()];
            for (int i = 0; i <= degree(); i++) {
                for (int j = 0; j <= dp.degree(); j++) {
                    newCoeff[i + j] += coefficients[i] * dp.getCoefficient(j);
                }
            }
            DensePolynomial dp2 = new DensePolynomial(polynomialString);
            dp2.setCoefficients(newCoeff);
            return dp2;
        }
        throw new NullPointerException("Other Polynomial is not a valid polynomial?");
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
        if (q == null) throw new NullPointerException("Other Polynomial cannot be null");
        if (q instanceof SparsePolynomial) {
            SparsePolynomial sp =  (SparsePolynomial) q;
            int[] newCoeff = new int[coefficients.length];
            if (sp.getPolynomialMap().lastKey() < 0) throw new IllegalArgumentException("q contains negative exponents");
            else {
                int i = 0;
                for (Map.Entry<Integer, Integer> entry : sp.getPolynomialMap().entrySet()) {
                    if (entry.getKey() <= coefficients.length - 1) {
                        int temp = coefficients[entry.getKey()] - entry.getValue();
                        newCoeff[entry.getKey()] = temp;
                    } else if (i == 0) {
                        newCoeff = new int[entry.getKey()];
                    } else {
                        newCoeff[entry.getKey()] = entry.getValue();
                    }
                    i++;
                }
                DensePolynomial dp = new DensePolynomial(polynomialString);
                dp.setCoefficients(newCoeff);
//                dp.setPolynomialString(dp.toString());
                return dp;
            }
        } else if (q instanceof DensePolynomial) {
            DensePolynomial qDense = (DensePolynomial) q;
            int max = Math.max(degree(), qDense.degree());
            int[] newCoeff = new int[max];
            if (degree() > qDense.degree())  {
                int i;
                for (i = 0; i <= qDense.degree(); i++) {
                    newCoeff[i] = coefficients[i] - qDense.getCoefficient(i);
                }
                for (; i <= degree(); i++) {
                    newCoeff[i] = coefficients[i];
                }
            } else {
                int i;
                for (i = 0; i <= degree(); i++) {
                    newCoeff[i] = coefficients[i] - qDense.getCoefficient(i);
                }
                for (; i <= qDense.degree(); i++) {
                    newCoeff[i] = qDense.getCoefficient(i);
                }
            }
            DensePolynomial newPoly = new DensePolynomial(polynomialString);
            newPoly.setCoefficients(newCoeff);
            return newPoly;
        }
        throw new NullPointerException("Other polynomial is not a valid polynomial?");
    }

    /**
     * Returns a polynomial by negating the current instance. The current instance is not modified.
     *
     * @return -this
     */
    @Override
    public Polynomial minus() {
        DensePolynomial dp = new DensePolynomial(polynomialString);
        int[] c = new int[dp.degree() + 1];
        for (int i = 0; i < coefficients.length; i++) {
            c[i] = coefficients[i] * -1;
        }
        dp.setCoefficients(c);
        return dp;
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
        for (String s : split) if (s.contains("^-")) return false;
        String[] operatorCheck = polynomialString.split("[ \\da-z^-]+");
        if (split.length != operatorCheck.length && operatorCheck.length > 0) return false;

        for (int i = 0; i < split.length; i++) {
            String[] s = split[i].split("\\^");

            if (i == 0) { // First Element
                if (s.length > 1)  {
                    // TODO: ["3x", "2"] or ["x", "2"]
                    if (Double.parseDouble(s[1]) % 1 != 0) return false;
                    coefficients = new int[Integer.parseInt(s[1]) + 1];
                    if (s[0].split("[a-z]+").length == 0) { // Must be coefficient of 1
                        coefficients[Integer.parseInt(s[1])] = 1;
                    } else {
                        if (Double.parseDouble(s[0].split("[a-z]+")[0]) % 1 != 0) return false;
                        coefficients[Integer.parseInt(s[1])] = Integer.parseInt(s[0].split("[a-z]+")[0]);
                    }
                } else {
                    // TODO: ["x"] or ["2"]
                    if (Character.isAlphabetic(s[0].charAt(0))) {
                        coefficients = new int[2];
                        coefficients[1] = 1;
                    } else {
                        String[] t = s[0].split("x");
                        if (Double.parseDouble(t[0]) % 1 != 0) return false;
                        if (s[0].contains("x")) coefficients = new int[]{0, Integer.parseInt(t[0])};
                        else coefficients = new int[]{Integer.parseInt(t[0])};
                    }
                }
            }

            // Attempt to create the polynomial
            if (s.length > 1)  {
                // Done: ["3x", "2"] or ["x", "2"]
                if (Double.parseDouble(s[1]) % 1 != 0) return false;
                if (s[0].split("[a-z]+").length == 0) { // Must be coefficient of 1
                    coefficients[Integer.parseInt(s[1])] = 1;
                } else {
                    if (Double.parseDouble(s[0].split("[a-z]+")[0]) % 1 != 0) return false;
                    coefficients[Integer.parseInt(s[1])] = Integer.parseInt(s[0].split("[a-z]+")[0]);
                }
            } else {
                // Done: ["x"] or ["2"]
                if (Character.isAlphabetic(s[0].charAt(0))) {
                    coefficients[1] = 1;
                } else {
                    String[] t = s[0].split("x");
                    if (Double.parseDouble(t[0]) % 1 != 0) return false;
                    coefficients[0] = Integer.parseInt(t[0]);
                }
            }

        }
        System.out.println("\nDEBUG:");
        System.out.println(Arrays.toString(split));
        System.out.println("Length of split: " + split.length);
        System.out.println("Operator Array: " + Arrays.toString(operatorCheck));
        System.out.println("Operator Array Length: " + operatorCheck.length);
        return true;
    }

    /**
     * Gets the string representation of the current polynomial
     *
     * @return the string representation of the current polynomial
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = coefficients.length - 1; i >= 0; i--) {
            if (coefficients[i] != 0) {
                if (i <= 1) {
                    if (i == 1) {
                        s.append(coefficients[i]).append("x").append(" + ");
                    } else {
                        s.append(coefficients[i]);
                    }
                } else s.append(coefficients[i]).append("x^").append(i).append(" + ");
            }
        }
        return s.toString();
    }

    /**
     * Checks if two Polynomial objects are equal
     *
     * @param o the polynomial to check
     * @return {@literal true} if the polynomial is a dense polynomial with the same coefficients and {@literal false
     * } otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DensePolynomial that = (DensePolynomial) o;
        return Arrays.equals(coefficients, that.coefficients);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coefficients);
    }
}
