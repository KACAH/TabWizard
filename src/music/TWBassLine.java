package music;
import java.util.Random;

import datastruct.TWChord;
import datastruct.TWDataException;
import datastruct.TWInstrumentTrack;
import datastruct.TWSimpleNote;

public class TWBassLine {

	/**
	 * Writes a simple bass line to the track
	 * @param chord chord, which notes are using to write a bass line
	 * @param track track on which we write a bass line
	 * @throws TWDataException
	 */
	static public void WriteSimpleBassLine(TWChord chord, TWInstrumentTrack track)throws TWDataException
	{
		TWSimpleNote Note;
		int[] frets; 
		for(int i = 0; i < 8; i++)
		{
			Note = chord.getNote(0);
			frets = track.getFretsByNoteAndString(Note, 4); 

			track.addNoteNew(frets[0], 4, 8);
		}
	}
	
	/**
	 * Writes a bass line with pauses to the track
	 * @param chord chord, which notes are using to write a bass line
	 * @param track track on which we write a bass line
	 * @throws TWDataException
	 */
	static public void WritePauseBassLine(TWChord chord, TWInstrumentTrack track)throws TWDataException
	{
		Random rn = new Random();
		
		TWSimpleNote Note;
		int[] frets; 
		for(int i = 0; i < 8; i++)
		{
			int randPause = rn.nextInt(3);
			
			if(randPause == 0)
				track.addRest(8);
			else
			{
				Note = chord.getNote(0);
				frets = track.getFretsByNoteAndString(Note, 4); 
				track.addNoteNew(frets[0], 4, 8);
			}
		}
	}
	
	/**
	 * Writes a bass line to the track
	 * @param chord chord, which notes are using to write a bass line
	 * @param harmony harmony, which notes we use to made a bass line
	 * @param track track on which we write a bass line
	 * @throws TWDataException
	 */
	static public void WriteBassLine(TWChord chord, TWHarmony harmony, TWInstrumentTrack track)
	{
		TWScale HarmonyScale = TWScaleManager.constructHarmonyScale(harmony);
		Random rnd = new Random();
			
		for(int i = 0; i < 8; i++)
		{
			int randNoteFromScale = rnd.nextInt(HarmonyScale.ScaleSize());
			int randNoteOnFret = rnd.nextInt(2);
			int randNoteInFirstPart = rnd.nextInt(4)+1;
			int randPlaceInFirstPart = rnd.nextInt(4);
			boolean PlaceInFirstPart = false;
			
			if(i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5)
			{
				if(randNoteInFirstPart == 4)
				{
					if(i == randPlaceInFirstPart)
					{
						PlaceInFirstPart = true;				
						BassLineNote(HarmonyScale.getNote(randNoteFromScale), randNoteOnFret, track);				    
					}
				}
				if(!PlaceInFirstPart)
					BassLineNote(chord.getNote(0), 0, track);
			}
			if(i == 6 || i == 7)
				BassLineNote(HarmonyScale.getNote(randNoteFromScale), randNoteOnFret, track);
		}
	}
	
	/**
	 * Writes a bass line note to the track
	 * @param Note note that we write to the track
	 * @param randNoteOnFret random integer, that chooses note from guitar fret
	 * @throws TWDataException
	 */
	private static void BassLineNote(TWSimpleNote Note, int randNoteOnFret, TWInstrumentTrack track)
	{
		int[] Frets = track.getFretsByNoteAndString(Note, 4);
		track.addNoteNew(Frets[randNoteOnFret], 4, 8);
	}
}
