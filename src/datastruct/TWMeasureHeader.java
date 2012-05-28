package datastruct;

/**
 * The TWMeasureHeader class contains all settings of song's measure
 * 
 * 
 */
public class TWMeasureHeader {
	private TWTimeSignature timeSignature;
	
	/**
	 * Create measure with default settings
	 */
	public TWMeasureHeader() {
		timeSignature = new TWTimeSignature();
	}
	/**
	 * Full constructor
	 * @param timeSignature previously created Time Signature
	 */
	public TWMeasureHeader(TWTimeSignature timeSignature) {
		this.timeSignature = timeSignature;
	}
	/**
	 * Get time signature
	 * @return time signature
	 */
	public TWTimeSignature getTimeSignature() { return timeSignature; }
}
