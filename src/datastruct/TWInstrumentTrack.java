package datastruct;

/**
 * The TWInstrumentTrack class represents all song tracks except percussions.
 * 
 *
 */
public class TWInstrumentTrack extends TWTrack {
	
	/**
	 * Create track with default settings. Is good for testing or if need to change only a few settings
	 */
	public TWInstrumentTrack(TWSongPart parentPart, TWTrackHeader header) {
		super(parentPart, header);
	}
	
	/**
	 * Get all frets with given Simple Note from given string
	 * @param note note to find on string
	 * @param string string index
	 * @return array of fret indexes
	 */
	public int[] getFretsByNoteAndString(TWSimpleNote note, int string) {
		//string - 1 because string numeration starts from 1, but array from 0
		int startNote = header.getStringsTunning().getStringNote(string-1).getNote().getID();
		int wantedNote = note.getID();
		
		int firstCorrectFred;
		if (startNote <= wantedNote) {
			firstCorrectFred = wantedNote-startNote;
		} else {	
			firstCorrectFred = 12-startNote+wantedNote;
		}
		
		if (firstCorrectFred + 24 <= TWDefaults.FRETS_NUM) {
			int []res = {firstCorrectFred,firstCorrectFred+12,firstCorrectFred+24}; 
			return res;
		}
		int []res = {firstCorrectFred,firstCorrectFred+12}; 
		return res;
	}
}
