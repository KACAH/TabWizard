package music;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import datastruct.TWChord;
import datastruct.TWDataException;
import datastruct.TWInstrumentTrack;
import datastruct.TWSimpleNote;


public class TWHarmonyManager {

	/**
	 * Writes a harmony to the track
	 * @param chord current chord from harmony, which writes on track
	 * @param track track on which we write a harmony
	 * @throws TWDataException
	 */
	static public void writeHarmony(TWChord chord, TWInstrumentTrack track)throws TWDataException
	{
		TWSimpleNote Note;
		int[] frets; 
		for(int i = 0; i < 3; i++)
		{
			Note = chord.getNote(i);
			frets = track.getFretsByNoteAndString(Note, i+1); 
			if (i == 0)
				track.addNoteNew(frets[0], i+1, 1);
			else
				track.addNoteMore(frets[0], i+1);
		}
	}

	/**
	 * Check note that doesn't appropriate to the chord
	 * @param chord chord which we want to check
	 * @return note name, that tone doesn't appropriate to the chord
	 */
	static public String TonicFunctionState(TWChord chord)
	{
		String Notes[] = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

		Pattern p = Pattern.compile("[A-Z]+[#]*m+");
		Matcher m = p.matcher(chord.getName());
		boolean state = m.matches(); 

		if(state)// Minor State
		{
			for(int i = 0; i < Notes.length; i++)
			{
				if(chord.getNote(2).getName().equals(Notes[i]))
				{
					if(i+1 > 11)
						return Notes[i-11];
					else
						return Notes[i+1];
				}
			}
		}
		// Major State
		else
			for(int i = 0; i < Notes.length; i++)
			{
				if(chord.getNote(2).getName().equals(Notes[i]))
				{
					if(i-1 < 0)
						return Notes[i+11];
					else
						return Notes[i-1];
				}
			}
		return null;
	}

	/**
	 * Checks, is note repeated when harmony constructs
	 * @param harmony harmony, which should be checked
	 * @param note Note that we are looking for in the scale
	 * @throws TWDataException
	 */
	static boolean onceNoteInHarmony(TWHarmony harmony, TWSimpleNote note)
	{
		TWSimpleNote[] AllNotes;
		AllNotes = new TWSimpleNote[harmony.HarmonySize()*3];

		for(int i = 0, j = 0, k = 0; i < harmony.HarmonySize()*3; i++, k++)
		{
			if(k == 3)
			{
				k = 0;	
				j++;
			}
			if(j == harmony.HarmonySize())	
				j = 0;

			AllNotes[i] = harmony.getChord(j).getNote(k);
		}

		int NoteCount = 0;
		for(int i = 0; i < AllNotes.length; i++)
		{
			if(note == AllNotes[i])
				NoteCount++;
		}
		if(NoteCount == 1)
			return true;
		else
			return false;
	}
}
