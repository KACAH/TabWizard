package datastruct;

/**
 * The TWExtendedNote is simple note + octave. For example E0, F#5...
 * 
 * @author Deniss Paltovs (KACAH)
 */
public class TWExtendedNote {
	private TWSimpleNote note;
	private int octave;
	
	private int noteID;
	
	/**
	 * @param note previously created Simple Note
	 * @param octave octave number
	 */
	public TWExtendedNote(TWSimpleNote note, int octave) {
		this.note = note;
		this.octave = octave;
		
		noteID = note.getID() + 12*octave;
	}
	/**
	 * Get note part
	 * @return Simple note
	 */
	public TWSimpleNote getNote() { return note; }
	/**
	 * Get octave part
	 * @return octave
	 */
	public int getOctave() { return octave; }
	/**
	 * Get note ID according to octave
	 * @return noteID
	 */
	public int getNoteID() { return noteID; }
}
