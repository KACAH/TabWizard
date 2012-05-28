package datastruct;

import java.util.Random;

/**
 * The TWTrackHeader class contains all information about tracks
 * 
 * 
 */
public class TWTrackHeader {
	protected String name;
	
	protected short volume;
	protected short balance;
	
	protected TWColor color;
	protected TWStringsTunning stringsTunning;
	
	protected short instrument;
	
	/**
	 * Create track header with default settings. Is good for testing or if need to change only a few settings
	 */
	public TWTrackHeader() {
		name = "Track";
		
		volume = TWDefaults.VOLUME;
		balance = TWDefaults.BALANCE;
		
		color = new TWColor();
		stringsTunning = new TWStringsTunning(TWStringsTunning.STANDARD);
		
		instrument = TWDefaults.INSTRUMENT;
	}
	/**
	 * Full constructor for normal track header
	 * @param name name of the track
	 * @param volume volume of track
	 * @param balance channel stereo balance
	 * @param color track color in Guitar Pro
	 * @param stringsTunning previously created Strings Tunning
	 */
	public TWTrackHeader(String name, short volume, short balance, TWColor color, TWStringsTunning stringsTunning, short instrument) {
		this.name = name;
		
		this.volume = volume;
		this.balance = balance;
		
		this.color = color;
		this.stringsTunning = stringsTunning;
		
		this.instrument = instrument;
	}
	/**
	 * Short constructor - only name and instrument ID
	 * @param name track name
	 * @param instrument instrument ID
	 */
	public TWTrackHeader(String name, short instrument) {
		this.name = name;
		this.instrument = instrument;
		
		volume = TWDefaults.VOLUME;
		balance = TWDefaults.BALANCE;
		
		Random rn = new Random();
		color = new TWColor(rn.nextInt(256),rn.nextInt(256),rn.nextInt(256));
		switch (instrument) {
			case TWInstruments.BASS_GUITAR: stringsTunning = new TWStringsTunning(TWStringsTunning.BASS); break; 
			case TWInstruments.DRUMS: stringsTunning = new TWStringsTunning(TWStringsTunning.DRUMS); break;
			default: stringsTunning = new TWStringsTunning(TWStringsTunning.STANDARD); break; 
		}
		
	}
	/**
	 * Get track balance
	 * @return balance
	 */
	public short getBalance() { return balance; }
	/**
	 * Set track balance
	 * @param balance track balance
	 */
	public void setBalance(short balance) { this.balance = balance; }
	/**
	 * Get track volume
	 * @return volume
	 */
	public short getVolume() { return volume; }
	/**
	 * Set track volume
	 * @param volume track volume
	 */
	public void setVolume(short volume) { this.volume = volume; }
	/**
	 * Get track color
	 * @return color
	 */
	public TWColor getColor() { return color; }
	/**
	 * Set track color
	 * @param color track color
	 */
	public void setColor(TWColor color) { this.color = color; }
	/**
	 * Get track name
	 * @return name
	 */
	public String getName() { return name; }
	/**
	 * Get track strings tunning
	 * @return strings tunning
	 */
	public TWStringsTunning getStringsTunning() { return stringsTunning; }
	/**
	 * Get instrument of track
	 * @return instrument of track
	 */
	public short getInstrument() { return instrument; }
	/**
	 * Check if this track is percussion track
	 * @return if this track is percussion track
	 */
	public boolean isPercussionTrack() { return instrument == TWInstruments.DRUMS; }
	/**
	 * Check if given track header has same parameters
	 * @param header header to compare
	 * @return if given track has same parameters
	 */
	public boolean isLike(TWTrackHeader header) {
		return volume == header.volume &&
			   balance == header.balance &&
			   stringsTunning.isLike(header.stringsTunning) &&
			   instrument == header.instrument;
	}
}
