package music;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import datastruct.TWChord;
import datastruct.TWSimpleNote;

/**
 * The TWScaleManager class manage scales, that are used in the program
 * 
 * @author Daniil Jurjev
 */
public class TWScaleManager {
	static private ArrayList<TWScale> Scales = new ArrayList<TWScale>();
	static public ArrayList<String> StringScales = new ArrayList<String>(); //Using in combo boxes

	/**
	 * Load scales form file
	 * @param fileName name of file, which contains scales
	 */
	static public void loadScales(String fileName)
	{		
		Scales.clear();
		StringScales.clear();

		try {
			FileReader read = new FileReader(fileName);
			BufferedReader stream = new BufferedReader(read);

			String line;
			line = stream.readLine();
			String[] count = line.split("( )+");
			int ScalesCount;
			ScalesCount = Integer.parseInt(count[0]);

			for(int i = 0; i < ScalesCount; i++)
			{
				line = stream.readLine();
				TWScale scale = parseScale(line);
				Scales.add(scale);
				StringScales.add(line);
			}
			stream.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Writes scales to file
	 * @param fileName name of file, which contains scales
	 * @param newScale the new scale that should be written to a file
	 */
	static public void writeScaleToFile(String fileName, TWScale newScale)
	{
		try {	
			newScale = TWScaleManager.transposeScaleForScaleList(newScale);

			FileReader read = new FileReader(fileName);
			BufferedReader stream = new BufferedReader(read);

			String newName = newScale.getScaleName();
			newName = newName.replace(" ", "");

			String Count = stream.readLine();
			int ScalesCount = Integer.parseInt(Count);

			String line;
			String all = "";
			String Notes = "";

			for(int i = 0; i < newScale.ScaleSize(); i++)
				Notes = Notes + " " + newScale.getNote(i);		

			Notes = Notes.replace("_", "#");

			for(int i = 0; i < ScalesCount; i++)
			{
				line = stream.readLine();
				all = all + line + "\r\n";
			}
			all = ScalesCount+1 + "\r\n" + all + newName + " " + newScale.ScaleSize() + Notes; 

			FileWriter fos = new FileWriter(fileName); 
			BufferedWriter out = new BufferedWriter(fos);

			out.write(all);	
			out.close();

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Deletes a scale from the list and file
	 * @param fileName name of file, which contains scales
	 * @param scaleName The name of the scale which should be removed
	 * @throws IOException
	 */
	static public void deleteScaleFromList(String fileName, String scaleName) throws IOException
	{
		Iterator<TWScale> itr1 = Scales.iterator(); 
		int index = 0;

		while(itr1.hasNext()) 
		{
			TWScale curScale = itr1.next();

			if ( curScale.getScaleName().equals(scaleName))
			{
				Scales.remove(index);
				StringScales.remove(index);
				break;
			}
			index++;
		}

		String all = "";

		Iterator<TWScale> itr2 = Scales.iterator(); 
		while(itr2.hasNext())
		{
			TWScale curScale = itr2.next();

			String Notes = "";
			for(int i = 0; i < curScale.ScaleSize(); i++)
				Notes = Notes + " " + curScale.getNote(i);

			Notes = Notes.replace("_", "#");

			all = all + curScale.getScaleName() + " " + curScale.ScaleSize() + Notes + "\r\n";
		}
		all = Scales.size() + "\r\n" + all;

		FileWriter fos = new FileWriter(fileName); 
		BufferedWriter out = new BufferedWriter(fos);

		out.write(all);	
		out.close();
	}


	/**
	 * Parse scale form string
	 * @param str string to parse
	 * @return parsed scale
	 */
	static public TWScale parseScale(String str)
	{
		String[] parts = str.split("( )+");

		if(parts.length > 1)
		{
			TWScale Scale = new TWScale(parts[0]);

			for(int i = 0; i < Integer.parseInt(parts[1]); i++)
			{
				TWSimpleNote Note = TWSimpleNote.noteByName(parts[i+2]);
				Scale.addSimpleNote(Note);
			}
			return Scale;
		}
		else
			return null;
	}

	static public TWScale getScaleByName(String name)
	{
		name = name.replace(" ", "");
		Iterator<TWScale> itr1 = Scales.iterator(); 
		while(itr1.hasNext()) 
		{
			TWScale curScale = itr1.next();
			if ( curScale.getScaleName().equals(name) )
				return curScale;
		}
		return null;
	}

	/**
	 * Transposes a scale in the required key
	 * @param Scale scale that you want to transpose
	 * @param newScaleKey note in which the file should be transposed
	 * @return transposed scale
	 */
	static public TWScale transponScale(TWScale Scale, String newScaleKey)
	{
		String Notes[] = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

		int FailCount = 0;
		for(int i = 0; i < Notes.length; i++)
			if(newScaleKey != Notes[i])
				FailCount++;

		if(FailCount == Notes.length)
			return null;

		if(newScaleKey.equals(Notes[0])) //doesn't require transposition
			return Scale;


		TWScale newScale = new TWScale(Scale.getScaleName());
		ArrayList<TWSimpleNote> scaleNotes = new ArrayList<TWSimpleNote>();
		scaleNotes = Scale.getNotes();
		boolean KeyAccepted = false;

		for(int i = 1; i < Notes.length; i++)
			if(newScaleKey.equals(Notes[i]))
				KeyAccepted = true;

		int NoteOrdinal = 0;
		for(int i = 0; i < Notes.length; i++) //shift counting
			if(newScaleKey == Notes[i])
				NoteOrdinal = i;

		if(KeyAccepted)
			for(int i = 0; i < Scale.ScaleSize(); i++)
				for(int j = 0; j < Notes.length; j++)
					if(scaleNotes.get(i).getName() == Notes[j])
					{
						if(j+NoteOrdinal < Notes.length) //note adding considering transposing shift
							newScale.addSimpleNote(TWSimpleNote.noteByName(Notes[j+NoteOrdinal])); 
						else //note adding considering transposing shift 
							newScale.addSimpleNote(TWSimpleNote.noteByName(Notes[j-Notes.length+NoteOrdinal])); 
					}

		return newScale;
	}

	/**
	 * Transposes a scale in the required key for scale list (required key is C)
	 * @param newScale scale that you want to transpose
	 * @return transposed scale
	 */
	static public TWScale transposeScaleForScaleList(TWScale newScale)
	{
		String Notes[] = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

		TWScale transposedScale = new TWScale(newScale.getScaleName());

		int Ordinal = 0;
		for(int i = 0; i < Notes.length; i++)
			if(newScale.getNote(0).getName() == Notes[i])
				Ordinal = i;				

		for(int i = 0; i < newScale.ScaleSize(); i++)
			for(int j = 0; j < Notes.length; j++)
				if(newScale.getNote(i).getName() == Notes[j])
				{
					if(j-Ordinal >= 0)
						transposedScale.addSimpleNote(TWSimpleNote.noteByName(Notes[j-Ordinal]));
					else
						transposedScale.addSimpleNote(TWSimpleNote.noteByName(Notes[j+Notes.length-Ordinal]));
				}
		return transposedScale;
	}

	/**
	 * Compares the scales on the equality
	 * @param newScale scale that you want to compare
	 * @param exScale second scale that you want to compare with first scale
	 * @return if first scale equals second scale
	 */
	static public boolean IsScaleEqualScale(TWScale newScale, TWScale exScale)
	{
		int isTrue = 0; // Must be equal ScaleSize

		if(newScale.ScaleSize() == exScale.ScaleSize())
			for(int i = 0; i < newScale.ScaleSize(); i++)
				for(int j = 0; j < exScale.ScaleSize(); j++)
					if(newScale.getNote(i).getName().equals(exScale.getNote(j).getName()))
						isTrue++;

		if(isTrue == exScale.ScaleSize())
			return true;
		else
			return false;
	}

	/**
	 * Checks for scale in scale list
	 * @param newScale scale you want to check
	 * @return if scale already exists
	 */
	static public boolean HasScaleInList(TWScale newScale)
	{
		String Notes[] = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
		Iterator<TWScale> itr1 = Scales.iterator(); 

		while(itr1.hasNext()) 
		{
			TWScale curScale = itr1.next();
			for(int i = 0; i < Notes.length; i++)
				if (IsScaleEqualScale(newScale, transponScale(curScale, Notes[i])))			
					return true;
		}
		return false;
	}

	/**
	 * Constructs scale using harmony
	 * @param harmony harmony which should be using to build scale
	 * @return scale, builded using harmony
	 */
	static public TWScale constructHarmonyScale(TWHarmony harmony)
	{
		TWScale Scale = new TWScale("Harmony Scale");

		//All notes from harmony with repeating. Every chord has 3 notes
		TWSimpleNote[] AllNotes = new TWSimpleNote[harmony.HarmonySize()*3];

		for(int i = 0, j = 0, k = 0; i < harmony.HarmonySize()*3; i++, k++)
		{
			if(k == 3){
				k = 0;
				j++;
			}

			if(j == harmony.HarmonySize())	
				j = 0;

			//Adds to all harmony notes array every note from harmony chords.
			AllNotes[i] = harmony.getChord(j).getNote(k);
		}	

		for(int i = 0; i < AllNotes.length; i++) //Note adding to scale without repeating
			if(!scaleHasNote(Scale, AllNotes[i]))
				Scale.addSimpleNote(AllNotes[i]);

		return Scale;
	}

	/**
	 * Get the indexes of the chord notes in the required scale
	 * @param Scale scale in which you want to find the indexes
	 * @param chord chord, that contains required notes
	 * @return array of indexes
	 */
	static int[] getNotesIndexFromScale(TWScale Scale, TWChord chord)
	{
		int NotesIndex[] = new int[3];

		for(int i = 0; i < Scale.ScaleSize(); i++)
			for(int j = 0; j < 3; j++)
				if(chord.getNote(j) == Scale.getNote(i))
					NotesIndex[j] = i;

		return NotesIndex;
	}

	/**
	 * Checks scale for note
	 * @param Scale scale which should be checked
	 * @param note Note, that we must find in scale
	 * @return if scale contain note
	 */
	static boolean scaleHasNote(TWScale Scale, TWSimpleNote note)
	{
		for(int i = 0; i < Scale.ScaleSize(); i++)  
			if(Scale.getNote(i) == note )
				return true;

		return false;
	}
}
