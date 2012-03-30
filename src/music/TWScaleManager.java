package music;
import java.util.ArrayList;
import java.util.Iterator;

import datastruct.TWChord;
import datastruct.TWSimpleNote;
import java.io.*;

public class TWScaleManager {

	static private ArrayList<TWScale> Scales = new ArrayList<TWScale>();
	static public ArrayList<String> StringScales = new ArrayList<String>();

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
			{
				Notes = Notes + " " + newScale.getNote(i);
			}			

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
		while(itr1.hasNext()) {
			TWScale curScale = itr1.next();
			if ( curScale.getScaleName().equals(name) )
				return curScale;
		}
		return null;
	}

	static public TWScale transponScale(TWScale Scale, String newScaleKey)
	{
		String Notes[] = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

		int FailCount = 0;
		for(int i = 0; i < Notes.length; i++)
		{
			if(newScaleKey != Notes[i])
				FailCount++;
		}
		if(FailCount == Notes.length)
			return null;

		if(newScaleKey.equals(Notes[0]))
			return Scale;

		TWScale newScale = new TWScale(Scale.getScaleName());
		ArrayList<TWSimpleNote> scaleNotes = new ArrayList<TWSimpleNote>();
		scaleNotes = Scale.getNotes();
		boolean KeyAccepted = false;

		for(int i = 1; i < Notes.length; i++)
		{
			if(newScaleKey.equals(Notes[i]))
				KeyAccepted = true;
		}

		int NoteOrdinal = 0;
		for(int i = 0; i < Notes.length; i++)
		{
			if(newScaleKey == Notes[i])
				NoteOrdinal = i;
		}

		if(KeyAccepted)
		{
			for(int i = 0; i < Scale.ScaleSize(); i++)
			{
				for(int j = 0; j < Notes.length; j++)
				{
					if(scaleNotes.get(i).getName() == Notes[j])
					{
						if(j+NoteOrdinal < Notes.length)
							newScale.addSimpleNote(TWSimpleNote.noteByName(Notes[j+NoteOrdinal]));
						else
							newScale.addSimpleNote(TWSimpleNote.noteByName(Notes[j-Notes.length+NoteOrdinal]));
					}
				}
			}
		}
		return newScale;
	}

	static public TWScale transposeScaleForScaleList(TWScale newScale)
	{
		String Notes[] = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

		TWScale transposedScale = new TWScale(newScale.getScaleName());

		int Ordinal = 0;
		for(int i = 0; i < Notes.length; i++)
		{
			if(newScale.getNote(0).getName() == Notes[i])
				Ordinal = i;				
		}

		for(int i = 0; i < newScale.ScaleSize(); i++)
		{
			for(int j = 0; j < Notes.length; j++)
			{
				if(newScale.getNote(i).getName() == Notes[j])
				{
					if(j-Ordinal >= 0)
						transposedScale.addSimpleNote(TWSimpleNote.noteByName(Notes[j-Ordinal]));
					else
						transposedScale.addSimpleNote(TWSimpleNote.noteByName(Notes[j+Notes.length-Ordinal]));
				}
			}
		}
		return transposedScale;
	}

	static public boolean IsScaleEqualScale(TWScale newScale, TWScale exScale)
	{
		int isTrue = 0;    							// Must be equal ScaleSize
		if(newScale.ScaleSize() == exScale.ScaleSize())
		{
			for(int i = 0; i < newScale.ScaleSize(); i++)
			{
				for(int j = 0; j < exScale.ScaleSize(); j++)
				{
					if(newScale.getNote(i).getName().equals(exScale.getNote(j).getName()))
						isTrue++;
				}
			}
		}
		if(isTrue == exScale.ScaleSize())
			return true;
		else
			return false;
	}

	static public boolean HasScaleInList(TWScale newScale)
	{
		String Notes[] = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
		Iterator<TWScale> itr1 = Scales.iterator(); 


		while(itr1.hasNext()) 
		{
			TWScale curScale = itr1.next();
			for(int i = 0; i < Notes.length; i++)
			{
				if (IsScaleEqualScale(newScale, transponScale(curScale, Notes[i])))			
					return true;
			}
		}
		return false;
	}

	static public TWScale constructHarmonyScale(TWHarmony harmony)
	{
		TWScale Scale = new TWScale("Harmony Scale");

		TWSimpleNote[] AllNotes;
		AllNotes = new TWSimpleNote[harmony.HarmonySize()*3];

		for(int i = 0, j = 0, k = 0; i < harmony.HarmonySize()*3; i++, k++)
		{
			if(k == 3)
			{k = 0;	j++;}
			if(j == harmony.HarmonySize())	j = 0;

			AllNotes[i] = harmony.getChord(j).getNote(k);
		}		
		for(int i = 0; i < AllNotes.length; i++)
		{
			if( !scaleHasNote (Scale, AllNotes[i]) )
			{
				Scale.addSimpleNote( AllNotes[i] );
			}
		}		
		return Scale;
	}

	static int[] getNotesIndexFromScale(TWScale Scale, TWChord chord)
	{
		int NotesIndex[] = new int[3];

		for(int i = 0; i < Scale.ScaleSize(); i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if(chord.getNote(j) == Scale.getNote(i))
					NotesIndex[j] = i;
			}
		}
		return NotesIndex;
	}

	static boolean scaleHasNote(TWScale Scale, TWSimpleNote note)
	{
		for(int i = 0; i < Scale.ScaleSize(); i++)
		{      
			if(Scale.getNote(i) == note )
				return true;
		}
		return false;
	}


}
