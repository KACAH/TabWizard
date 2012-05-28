package datastruct;

/**
 * The TWBeat class represents one beat of the measure
 * 
 */
public class TWBeat {
	private TWDuration duration;
	private TWNote[] stringNotes;
	private int numNotes;
	
	private TWMeasure measure; 
	private TWStroke stroke;
	
	private boolean dotted;
	private boolean rest;
	private boolean fadeIn;
	
	/**
	 * After Beat creation you don't need to add it to measure, this will be done automatically.  
	 * @param measure Measure in which this beat will be added. 
	 * This parameter is needed to setup strings of the Beat and add Beate to measure,
	 * because of this would be stupid, if there were possibility to add Note on nonexistent string.
	 * @param duration Duration of the beat
	 */
	public TWBeat(TWMeasure measure, TWDuration duration) {
		this.measure = measure;
		this.duration = duration;
		numNotes = 0;
		
		dotted = false;
		rest = false;
		fadeIn = false;
		stroke = null;
		
		measure.addBeat(this);
		stringNotes = new TWNote[measure.getTrack().getHeader().getStringsTunning().getNumStringsUsed()];
	}
	
	//all array indexes are string-1 because of normally first string number is 1 not 0
	/**
	 * Add a note to beat's string. You should check string number by {@link #getNumStrings() } method
	 * @param note Note to add
	 * @param string index of the string
	 */
	public void addNote(TWNote note, int string) {
		if (stringNotes[string-1] == null)
			numNotes++;
		stringNotes[string-1] = note;
	}
	/**
	 * Remove note from the string
	 * @param string index of the string
	 */
	public void removeNote(int string) {
		if (stringNotes[string-1] != null)
			numNotes--;
		stringNotes[string-1] = null;
	}
	/**
	 * Get note on the string
	 * @param string
	 * @return Note on string or null
	 */
	public TWNote getNote(int string) {
		return stringNotes[string-1];
	}
	/**
	 * Check if Beat is empty
	 * @return if Beat is empty
	 */
	public boolean isEmpty() {
		return numNotes == 0;
	}
	/**
	 * Get number of notes in this beat
	 * @return number of notes
	 */
	public int getNumNotes() {
		return numNotes;
	}
	/**
	 * Get number of strings. It will be equal track's number of strings
	 * @return number of strings
	 */
	public int getNumStrings() {
		return stringNotes.length;
	}
	/**
	 * Check if note exists on string
	 * @param string index of string
	 * @return if note exists
	 */
	public boolean noteExists(int string) {
		return stringNotes[string-1] != null;
	}
	/**
	 * Get duration of beat
	 * @return duration of beat
	 */
	public TWDuration getDuration() { return duration; }
	/**
	 * Get measure of beat
	 * @return measure of beat
	 */
	public TWMeasure getMeasure() { return measure; }
	
	//Beat effects
	/**
	 * Set beat dotted or not (+0.5*duration)
	 * @param dotted effect state
	 */
	public void setDotted(boolean dotted) { this.dotted = dotted; }
	/**
	 * Check if the beat is dotted
	 * @return if the beat is dotted
	 */
	public boolean isDotted() { return dotted; }
	/**
	 * Set beat rested(pause) or not 
	 * @param rest effect state
	 */
	public void setRest(boolean rest) { this.rest = rest; }
	/**
	 * Check if the beat is rested
	 * @return if the beat is rested
	 */
	public boolean isRest() { return rest; }
	/**
	 * Set beat faded in or not 
	 * @param fadeIn effect state
	 */
	public void setFadeIn(boolean fadeIn) { this.fadeIn = fadeIn; }
	/**
	 * Check if the beat is faded in
	 * @return if the beat is faded in
	 */
	public boolean isFadeIn() { return fadeIn; }
	/**
	 * Add stroke effect to this beat
	 * @param stroke stroke effect
	 */
	public void addStroke(TWStroke stroke) {
		this.stroke = stroke;
	}
	/**
	 * Remove stroke effect from this beat
	 */
	public void removeStroke() { 
		stroke = null; 
	}
	/**
	 * Check if the beat has stroke effect
	 * @return if the beat has stroke effect
	 */
	public boolean hasStroke() { return stroke != null; }
	/**
	 * Get stroke of the beat
	 * @return stroke or null
	 */
	public TWStroke getStroke() {return stroke;}
}
