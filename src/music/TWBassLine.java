package music;
import java.util.Random;

import datastruct.TWChord;
import datastruct.TWDataException;
import datastruct.TWInstrumentTrack;
import datastruct.TWSimpleNote;

/**
 * The TWBassLine class. Writes a bass lines to the track
 * 
 */

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
		
		//One measure contains only eighth notes
		for(int i = 0; i < 8; i++)
		{
			//Use first, main note from given chord
			Note = chord.getNote(0);
			frets = track.getFretsByNoteAndString(Note, 4); 

			//Writes it on fourth string
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
		
		//One measure contains only eighth notes
		for(int i = 0; i < 8; i++)
		{
			int randPause = rn.nextInt(3);
			
			//Writes eighth rest. Probability 1/3
			if(randPause == 0)
				track.addRest(8);
			else
			{
				//Use first, main note from given chord
				Note = chord.getNote(0);
				frets = track.getFretsByNoteAndString(Note, 4); 

				//Writes it on fourth string
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
			
		//One measure contains only eighth notes
		for(int i = 0; i < 8; i++)
		{
			int randNoteFromScale = rnd.nextInt(HarmonyScale.ScaleSize());
			int randNoteOnFret = rnd.nextInt(2);
			int randNoteInFirstPart = rnd.nextInt(4);
			int randPlaceInFirstPart = rnd.nextInt(4);
			boolean PlaceInFirstPart = false;
			
			//Bass line divided into two parts. First part contains 6 eighth notes, 
			//preferably main note from chord.
			//Second part contains 2 eighth notes.
			
			if(i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5)
			{
				//Writes random note in first part. Probability 1/4
				if(randNoteInFirstPart == 0)
				{
					//Writes note in random first part place. Probability 1/4
					if(i == randPlaceInFirstPart)
					{
						PlaceInFirstPart = true;				
						BassLineNote(HarmonyScale.getNote(randNoteFromScale), randNoteOnFret, track);				    
					}
				}
				//Writes main note.
				if(!PlaceInFirstPart)
					BassLineNote(chord.getNote(0), 0, track);
			}
			//Second part. Writes random note from harmony scale.
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
