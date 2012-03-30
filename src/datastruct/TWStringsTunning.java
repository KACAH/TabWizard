package datastruct;

/**
 * The TWStringsTunning class represents settings of the guitar strings (tunning)
 * 
 * @author Deniss Paltovs (KACAH)
 */
public class TWStringsTunning {
	private TWExtendedNote[] strings;
	
	public static final TWExtendedNote[] DRUMS =
	{new TWExtendedNote(TWSimpleNote.C,0), new TWExtendedNote(TWSimpleNote.C,0), new TWExtendedNote(TWSimpleNote.C,0), 	
	new TWExtendedNote(TWSimpleNote.C,0), new TWExtendedNote(TWSimpleNote.C,0), new TWExtendedNote(TWSimpleNote.C,0)};
	public static final TWExtendedNote[] STANDARD = 
	{new TWExtendedNote(TWSimpleNote.E,5), new TWExtendedNote(TWSimpleNote.B,4), new TWExtendedNote(TWSimpleNote.G,4), 
	 new TWExtendedNote(TWSimpleNote.D,4), new TWExtendedNote(TWSimpleNote.A,3), new TWExtendedNote(TWSimpleNote.E,3)};
	public static final TWExtendedNote[] BASS = 
	{new TWExtendedNote(TWSimpleNote.G,3), 
	 new TWExtendedNote(TWSimpleNote.D,3), new TWExtendedNote(TWSimpleNote.A,2), new TWExtendedNote(TWSimpleNote.E,2)};
	
	/**
	 * @param strings all strings tunning (you can use constants)
	 */
	public TWStringsTunning(TWExtendedNote[] strings) {	
		this.strings = strings;
	}
	/**
	 * Get note ID on string 
	 * @param index string id
	 * @return string note ID
	 */
	public int getStringInt(int index) {
		return strings[index].getNoteID();
	}
	/**
	 * Get Extended Note on string 
	 * @param index string id
	 * @return string Extended Note
	 */
	public TWExtendedNote getStringNote(int index) {
		return strings[index];
	}
	/**
	 * Get number of strings
	 * @return number of strings
	 */
	public int getNumStringsUsed() {
		return strings.length;
	}
	/**
	 * Check if given tunning is the same like me
	 * @param tunning tunning to check
	 * @return if given tunning is the same like me
	 */
	public boolean isLike(TWStringsTunning tunning) {
		if (getNumStringsUsed() != tunning.getNumStringsUsed())
			return false;
		
		for (int i = 0; i < getNumStringsUsed(); i++) {
			if (getStringInt(i) != tunning.getStringInt(i))
				return false;
		}
		return true;
	}
}
