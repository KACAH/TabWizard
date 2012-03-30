package datastruct;

/**
 * The TWSimpleNote enum is musical representation of notes. 
 * 
 * @author Deniss Paltovs (KACAH)
 */
public enum TWSimpleNote {
	C(0,"C"), C_(1,"C#"), D(2,"D"), D_(3,"D#"), E(4,"E"), F(5,"F"),
	F_(6,"F#"), G(7,"G"), G_(8,"G#"), A(9,"A"), A_(10,"A#"), B(11,"B"), ;
	
	private TWSimpleNote(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	private int id;
	private String name;
	
	public static final TWSimpleNote[] ALL_ARRAY = {C, C_, D, D_, E, F, F_, G, G_, A, A_, B};

	/**
	 * Convert String representation of note to TWSimpleNote
	 * @param note string note representation
	 * @return note
	 */
	static public TWSimpleNote noteByName(String note) {
		for (int i = 0; i < ALL_ARRAY.length; i++)
			if (ALL_ARRAY[i].getName().equals(note))
				return ALL_ARRAY[i];
		return null;
	}
	/**
	 * Get note numeric representation
	 * @return note numeric representation
	 */
	public int getID() { return id; }
	/**
	 * Get note string representation
	 * @return note string representation
	 */
	public String getName() { return name; }
}
