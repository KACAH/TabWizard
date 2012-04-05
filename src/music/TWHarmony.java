package music;
import java.util.ArrayList;

import datastruct.TWChord;

public class TWHarmony {

	private ArrayList<TWChord> HarmonyChords;

	TWHarmony(){
		this.HarmonyChords = new ArrayList<TWChord>();
	}
	/**
	 * Add chord to harmony
	 * @param chord chord, that should be added to harmony
	 */
	public void addChord(TWChord chord){
		if(!HarmonyChords.contains(chord))
			HarmonyChords.add(chord);
	}
	/**
	 * Get harmony size
	 * @return Harmony size
	 */
	public int HarmonySize(){
		int size = HarmonyChords.size();
		return size;
	}
	/**
	 * Get chord from Harmony by index
	 * @param index
	 * @return chord
	 */
	public TWChord getChord(int index){ 
		return HarmonyChords.get(index); 
	}
	/**
	 * Get chords from harmony
	 * @return ArrayList with chords
	 */
	public ArrayList<TWChord> getChords(){
		return HarmonyChords;
	}	
}

