package datastruct;

/**
 * The TWTimeSignature class represents measure's time signature - two ints numerator and denominator
 *
 * @author Deniss Paltovs (KACAH)
 */
public class TWTimeSignature {
	private int numerator;
	private int denominator;
	
	/**
	 * Default time signature 4/4
	 */
	public TWTimeSignature() {
		numerator = 4;
		denominator = 4;
	}
	/**
	 * @param numerator value of numerator
	 * @param denominator value of deniminator
	 */
	public TWTimeSignature(int numerator, int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}
	/**
	 * Get numerator
	 * @return numerator
	 */	
	public int getNumerator() { return numerator; }
	/**
	 * Set numerator
	 * @param numerator
	 */	
	public void setNumerator(int numerator) { this.numerator = numerator; }
	/**
	 * Get denominator
	 * @return denominator
	 */
	public int getDenominator() { return denominator; }
	/**
	 * Set denominator
	 * @param denominator denominator
	 */
	public void setDenominator(int denominator) { this.denominator = denominator; } 
	/**
	 * Check if two time signatures are equal
	 * @param second second Time Signature
	 * @return is or not
	 */
	public boolean isEqual(TWTimeSignature second) {
		return this.numerator == second.numerator && this.denominator == second.denominator;
	}
}
