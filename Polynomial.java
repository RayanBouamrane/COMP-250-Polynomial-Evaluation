//Name: Rayan Bouamrane
//Student ID: 260788250

package assignments2018.a2template;

import java.math.BigInteger;

public class Polynomial {
    private SLinkedList<Term> polynomial;

    public int size() {
        return polynomial.size();
    }

    private Polynomial(SLinkedList<Term> p) {
        polynomial = p;
    }

    public Polynomial() {
        polynomial = new SLinkedList<Term>();
    }

    // Returns a deep copy of the object.
    public Polynomial deepClone() {
        return new Polynomial(polynomial.deepClone());
    }

    public void addTerm(Term t) {

        int i = 0;
        BigInteger o = new BigInteger("0");
        for (Term c : polynomial) {
            if (t.getExponent() == c.getExponent()) {
                BigInteger n = t.getCoefficient().add(c.getCoefficient());
                if (n.compareTo(o) <= 0) {
                    polynomial.remove(i);
                    return;
                } else {
                    c.setCoefficient(t.getCoefficient().add(c.getCoefficient()));
                    return;
                }
            }
            if (c.getExponent() < t.getExponent())
                continue;
            i++;
        }
        polynomial.add(i, t);

    }

    // Hint: Notice that the function SLinkedList.get(index) method is O(n),
    // so if this method were to call the get(index)
    // method n times then the method would be O(n^2).
    // Instead, use a Java enhanced for loop to iterate through
    // the terms of an SLinkedList.
		/*
		for (Term currentTerm: polynomial)
		{
			// The for loop iterates over each term in the polynomial!!
			// Example: System.out.println(currentTerm.getExponent()) should print the exponents of each term in the polynomial when it is not empty.  
		}
		*/
    public Term getTerm(int index) {
        return polynomial.get(index);
    }

    public static Polynomial add(Polynomial p1, Polynomial p2) {

        Polynomial n = new Polynomial();

        for (Term c : p1.polynomial)
            n.addTerm(c);
        for (Term c : p2.polynomial)
            n.addTerm(c);
        return n;
    }

    private void multiplyTerm(Term t) {

        for (Term c : polynomial) {
            c.setExponent((c.getExponent() + t.getExponent()));
            c.setCoefficient(t.getCoefficient().multiply(c.getCoefficient()));
        }
    }

    public static Polynomial multiply(Polynomial p1, Polynomial p2) {

        Polynomial r = new Polynomial();
        for (Term c : p2.polynomial) {
            Polynomial temp = p1.deepClone();
            temp.multiplyTerm(c);
            r = add(r, temp);

        }
        return r;
    }

    // Hint:  The time complexity of eval() must be order O(m),
    // where m is the largest degree of the polynomial. Notice
    // that the function SLinkedList.get(index) method is O(m),
    // so if your eval() method were to call the get(index)
    // method m times then your eval method would be O(m^2).
    // Instead, use a Java enhanced for loop to iterate through
    // the terms of an SLinkedList.

    public BigInteger eval(BigInteger x) {

        BigInteger r = new BigInteger("0");
        int i = 0;
        for (Term c : polynomial) {
            int d = i - c.getExponent();
            if (i == 0)
                d = 1;
            for (int z = 0; d > 0; d--)
                r = r.multiply(x);
            r = r.add(c.getCoefficient());
            i = c.getExponent();
        }
        for (int z = 0; i > 0; i--)
            r = r.multiply(x);
        return r;
    }

    // Checks if this polynomial is same as the polynomial in the argument
    public boolean checkEqual(Polynomial p) {
        if (polynomial == null || p.polynomial == null || size() != p.size())
            return false;

        int index = 0;
        for (Term term0 : polynomial) {
            Term term1 = p.getTerm(index);

            if (term0.getExponent() != term1.getExponent() ||
                    term0.getCoefficient().compareTo(term1.getCoefficient()) != 0 || term1 == term0)
                return false;

            index++;
        }
        return true;
    }

    // This method blindly adds a term to the end of LinkedList polynomial.
    // Avoid using this method in your implementation as it is only used for testing.
    public void addTermLast(Term t) {
        polynomial.addLast(t);
    }

    // This is used for testing multiplyTerm
    public void multiplyTermTest(Term t) {
        multiplyTerm(t);
    }

    @Override
    public String toString() {
        if (polynomial.size() == 0) return "0";
        return polynomial.toString();
    }
}
