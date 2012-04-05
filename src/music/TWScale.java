package music;
import datastruct.TWSimpleNote;
import java.util.ArrayList;

public class TWScale {

	private String name;
	private ArrayList<TWSimpleNote> ScaleNotes;

	public TWScale(String Name)	{
		this.name = Name;
		this.ScaleNotes = new ArrayList<TWSimpleNote>();
	}

	/**
	 * Adds note to the scale
	 * @param Note note that should be added to scale
	 */
	public void addSimpleNote(TWSimpleNote Note)
	{
		if(!ScaleNotes.contains(Note))
			ScaleNotes.add(Note);
	}
	
	/**
	 * Get scale size
	 * @return scale size
	 */
	public int ScaleSize()	{
		int size = ScaleNotes.size();
		return size;
	}	

	/**
	 * Get note of the scale by index
	 * @param index note index
	 * @return Note
	 */
	public TWSimpleNote getNote(int index){ 
		return ScaleNotes.get(index); 
	}
	
	/**
	 * Get scale notes
	 * @return ArrayList of notes
	 */
	public ArrayList<TWSimpleNote> getNotes(){
		return ScaleNotes;
	}
	
	/**
	 * Get scale name
	 * @return scale name
	 */
	public String getScaleName(){
		return name;
	}	
}
