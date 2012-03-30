package datastruct;

import java.io.*;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class TWChordManager {
	static private List<TWChord> chords = new ArrayList<TWChord>();
	/**
	 * Load chords form file
	 * @param fileName name of file, which contains chords
	 * @throws TWDataException
	 */
	static public void loadChords(String fileName) throws TWDataException {
		chords.clear();
		try {
			FileReader fis = new FileReader(fileName); 
			BufferedReader in = new BufferedReader(fis);
			
			String line;
			while ( (line = in.readLine()) != null) {
				TWChord chord = parseChord(line);
				//check if chord with such name already exists
				if (chord != null && getChordByName(chord.getName()) == null )
					chords.add(chord);
			}
			
			in.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	/**
	 * Parse chord form string
	 * @param in string to parse
	 * @return parsed chord
	 * @throws TWDataException
	 */
	static public TWChord parseChord(String in) throws TWDataException {
		String[] parts = in.split("( )+");
		//first element must be name, and chord must contain at least 1 note
		if (parts.length < 2) {
			System.err.println("There are not notes in parsing chord");
			return null;
		}
		
		TWChord chord = new TWChord(parts[0]);
		for (int i = 1; i < parts.length; i++) {
			TWSimpleNote note = TWSimpleNote.noteByName(parts[i]);
			if (note == null) {
				System.err.println("Note with name "+parts[i]+" doesn't exist");
				return null;
			}
			chord.addNote(note);
		}
		return chord;
	}
	/**
	 * Get chord by it's name
	 * @param name name of the chord
	 * @return chord or null
	 */
	static public TWChord getChordByName(String name) {
		Iterator<TWChord> itr1 = chords.iterator(); 
		while( itr1.hasNext() ) {
			TWChord curChord = itr1.next();
			if ( curChord.getName().equals(name) )
				return curChord;
		}
		return null;
	}
	/**
	 * Get ArrayList of chords which contains notes in params
	 * @param notes all notes to find
	 * @return ArrayList of chords
	 */
	static public List<TWChord> getChordsByNotes(String ... notes) {
		List<TWChord> res = new ArrayList<TWChord>();
		//check if there are no notes to check or there are too many notes
		if (notes.length == 0 || notes.length > TWChord.MAX_NOTES_NUMBER) {
			System.err.println("Incorrect arguments count in getChordsByNotes");
			return res;
		}
		
		//check all chords
		Iterator<TWChord> itr1 = chords.iterator(); 
		while( itr1.hasNext() ) {
			TWChord curChord = itr1.next();
			
			//check all notes from param
			boolean hasAllNotes = true;
			for (int i = 0; i < notes.length; i++) {
				TWSimpleNote note = TWSimpleNote.noteByName(notes[i]);
				if (!curChord.hasNote(note)) {
					hasAllNotes = false;
					break;
				}
			}
			if (hasAllNotes)
				res.add(curChord);
		}
		return res;
	}
	
	/**
	 * Get ArrayList of chords which contains notes in params
	 * @param notes all notes to find
	 * @return ArrayList of chords
	 */
	static public List<TWChord> getChordsByNotes(TWSimpleNote ... notes) {
		List<TWChord> res = new ArrayList<TWChord>();
		//check if there are no notes to check or there are too many notes
		if (notes.length == 0 || notes.length > TWChord.MAX_NOTES_NUMBER) {
			System.err.println("Incorrect arguments count in getChordsByNotes");
			return res;
		}
		
		//check all chords
		Iterator<TWChord> itr1 = chords.iterator(); 
		while( itr1.hasNext() ) {
			TWChord curChord = itr1.next();
			
			//check all notes from param
			boolean hasAllNotes = true;
			for (int i = 0; i < notes.length; i++) {
				if (!curChord.hasNote(notes[i])) {
					hasAllNotes = false;
					break;
				}
			}
			if (hasAllNotes)
				res.add(curChord);
		}
		return res;
	}
}
