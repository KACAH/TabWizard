package datastruct;

import java.util.List;
import java.util.ArrayList;

/**
 * The TWTrack class represents all song tracks including percussions.
 * 
 * 
 */
abstract public class TWTrack {
	protected TWTrackHeader header;
	protected TWSongPart parentPart;
	
	protected List<TWMeasure> measures;
	
	protected TWNote lastNote;
	
	TWTrack(TWSongPart parentPart, TWTrackHeader header) {
		this.parentPart = parentPart;
		this.header = header;
		
		lastNote = null;
		
		measures = new ArrayList<TWMeasure>();
	}
	/**
	 * Get header of the track
	 * @return  header of the track
	 */
	public TWTrackHeader getHeader() { return header; }
	
	//measures will be added internally
	void addMeasure(TWMeasure measure) {
		measures.add(measure);
	}
	/**
	 * Get measure with given index
	 * @param index number of measure
	 * @return measure
	 */
	public TWMeasure getMeasure(int index) {
		return measures.get(index);
	}
	/**
	 * Get last measure of this track
	 * @return last measure of this track
	 */
	public TWMeasure getLastMeasure() {
		if (measures.size() > 0)
			return measures.get(measures.size()-1);
		return null;
	}
	/**
	 * Get last beat of this track
	 * @return last beat of this track
	 */
	public TWBeat getLastBeat() {
		return getLastMeasure().getLastBeat();
	}
	/**
	 * Get last note of this track
	 * @return last note of this track
	 */
	public TWNote getLastNote() { return lastNote; }

	private void checkLastMeasure() {
		if (getLastMeasure() == null)
		{
			parentPart.addMeasure( new TWMeasureHeader() );
			return;
		}
		if (getLastMeasure().isFull())
			parentPart.addMeasure( new TWMeasureHeader(getLastMeasure().getHeader().getTimeSignature()) );
	}
	/**
	 * More comfortable way to add notes to the song. Creates new beat for note.
	 * @param value note value on string
	 * @param string string number
	 * @param duration duration (you can use constants)
	 */
	public void addNoteNew(int value, int string, int duration) {
		checkLastMeasure();
		TWBeat beat = new TWBeat( getLastMeasure(), new TWDuration(duration) );
		TWNote note = new TWNote(value);
		beat.addNote(note, string);
		
		lastNote = note;
	}
	/**
	 * More comfortable way to add notes to the song. Creates new beat for note.
	 * @param note previously created Note
	 * @param string string number
	 * @param duration duration (you can use constants)s
	 */
	public void addNoteNew(TWNote note, int string, int duration) {
		checkLastMeasure();
		TWBeat beat = new TWBeat( getLastMeasure(), new TWDuration(duration) );
		beat.addNote(note, string);
		
		lastNote = note;
	}
	/**
	 * More comfortable way to add notes to the song. Adds note to the last beat.
	 * @param value note value on string
	 * @param string string number
	 * @throws TWDataException if last beat doesn't exist
	 */
	public void addNoteMore(int value, int string) throws TWDataException {
		if (getLastBeat() == null)
			throw new TWDataException("Last beat doesn't exist");
			
		TWBeat beat = getLastBeat();
		TWNote note = new TWNote(value);
		beat.addNote(note, string);
			
		lastNote = note;
	}
	/**
	 * More comfortable way to add notes to the song. Adds note to the last beat.
	 * @param note previously created Note
	 * @param string string number
	 * @throws TWDataException if last beat doesn't exist
	 */
	public void addNoteMore(TWNote note, int string) throws TWDataException {
		if (getLastBeat() == null)
			throw new TWDataException("Last beat doesn't exist");
			
		TWBeat beat = getLastBeat();
		beat.addNote(note, string);
			
		lastNote = note;
	}
	/**
	 * Add rest to this track
	 * @param duration duration of rest (you can use constants)
	 */
	public void addRest(int duration) {
		TWBeat beat = new TWBeat( getLastMeasure(), new TWDuration(duration) );
		beat.setRest(true);
	}
}
