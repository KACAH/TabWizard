package music;
import java.util.ArrayList;

import datastruct.TWChord;

public class TWHarmony {

	private ArrayList<TWChord> HarmonyChords;

	TWHarmony()
	{
		this.HarmonyChords = new ArrayList<TWChord>();
	}

	public void addChord(TWChord chord)
	{
		if(!HarmonyChords.contains(chord))
		{
			HarmonyChords.add(chord);
		}
	}
	
	public int HarmonySize()
	{
		int size = HarmonyChords.size();
		return size;
	}
	
	public TWChord getChord(int index) 
	{ 
		return HarmonyChords.get(index); 
	}
	
	public ArrayList<TWChord> getChords()
	{
		return HarmonyChords;
	}	
}

