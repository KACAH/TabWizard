package datastruct;

/**
 * The TWDuration class represents Note's duration. Also it contains some constants, witch help to work with duration
 * 
 * 
 */
public class TWDuration {
	public static final int WHOLE = 1;
	public static final int HALF = 2;
	public static final int QUARTER = 4;
	public static final int EIGHTH = 8;
	public static final int SIXTEENTH = 16;
	public static final int THIRTY_SECOND = 32;
	public static final int SIXTY_FOURTH = 64;
	
	private int value;
	private boolean triplet;
	
	/**
	 * @param value of the duration
	 */
	public TWDuration(int value) {
		this.value = value;
	}
	/**
	 * Get value of the duration
	 * @return value of the duration
	 */
	public int getValue() { return value; }
	/**
	 * Set this duration as a part of triplet
	 * @param triplet triplet state
	 */
	public void setTriplet(boolean triplet) { this.triplet = triplet; }
	/**
	 * Check if this duration is a part of triplet
	 * @return if this duration is a part of triplet
	 */
	public boolean isTriplet() { return triplet; }
}
