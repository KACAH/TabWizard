package datastruct;

import java.util.List;
import java.util.ArrayList;

public class TWChord {
	public static final int MAX_NOTES_NUMBER = 5;
	
	private List<TWSimpleNote> chordNotes;
	private String name;
	
	TWChord(String name) {
		this.name = name;
		
		chordNotes = new ArrayList<TWSimpleNote>();
	}
	
	void addNote(TWSimpleNote note) throws TWDataException {
		if (chordNotes.contains(note))
			return;
		
		if (chordNotes.size() == MAX_NOTES_NUMBER)
			throw new TWDataException("Max allowed notes number in chord is "+MAX_NOTES_NUMBER);
		
		chordNotes.add(note);
	}
	/**
	 * Get chord notes
	 * @return ArrayList of notes
	 */
	public List<TWSimpleNote> getNotes() { return chordNotes; }
	/**
	 * Get note of the chord by index
	 * @param index note index
	 * @return Note
	 */
	public TWSimpleNote getNote(int index) { return chordNotes.get(index); }
	/**
	 * Check if note exists in this chord
	 * @param note note to check
	 * @return if note exists in this chord
	 */
	public boolean hasNote(TWSimpleNote note) {
		return chordNotes.contains(note);
	}
	/**
	 * Get chord name
	 * @return chord names
	 */
	public String getName() { return name; }
}
