package music;
import datastruct.TWSimpleNote;
import java.util.ArrayList;

public class TWScale {

	private String name;
	private ArrayList<TWSimpleNote> ScaleNotes;

	public TWScale(String Name)
	{
		this.name = Name;
		this.ScaleNotes = new ArrayList<TWSimpleNote>();
	}

	public void addSimpleNote(TWSimpleNote Note)
	{
		if(!ScaleNotes.contains(Note))
		{
			ScaleNotes.add(Note);
		}
	}

	public int ScaleSize()
	{
		int size = ScaleNotes.size();
		return size;
	}	

	public TWSimpleNote getNote(int index) 
	{ 
		return ScaleNotes.get(index); 
	}

	public ArrayList<TWSimpleNote> getNotes()
	{
		return ScaleNotes;
	}

	public String getScaleName()
	{
		return name;
	}	
}
