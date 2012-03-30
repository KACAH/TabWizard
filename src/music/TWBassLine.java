package music;
import java.util.Random;

import datastruct.TWChord;
import datastruct.TWDataException;
import datastruct.TWInstrumentTrack;
import datastruct.TWSimpleNote;

public class TWBassLine {

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
	
	static public void WritePauseBassLine(TWChord chord, TWInstrumentTrack track)throws TWDataException
	{
		Random rn = new Random();
		
		TWSimpleNote Note;
		int[] frets; 
		for(int i = 0; i < 8; i++)
		{
			int randPause = rn.nextInt(3);
			
			if(randPause == 0)
			{
				track.addRest(8);
			}
			else
			{
				Note = chord.getNote(0);
				frets = track.getFretsByNoteAndString(Note, 4); 
				track.addNoteNew(frets[0], 4, 8);
			}

		}
	}
	
	static public void WriteBassLine(TWChord chord, TWHarmony harmony, TWInstrumentTrack track)
	{
		TWScale HarmonyScale = TWScaleManager.constructHarmonyScale(harmony);
			
		Random rnd = new Random();
		
		TWSimpleNote Note;
		int[] frets; 
			
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
						Note = HarmonyScale.getNote(randNoteFromScale);
						frets = track.getFretsByNoteAndString(Note, 4); 
					    track.addNoteNew(frets[randNoteOnFret], 4, 8);
					    
					}
				}
				if(!PlaceInFirstPart)
				{
					Note = chord.getNote(0);
					frets = track.getFretsByNoteAndString(Note, 4); 
					track.addNoteNew(frets[0], 4, 8);
				}
			}
			if(i == 6 || i == 7)
			{
				Note = HarmonyScale.getNote(randNoteFromScale);
				frets = track.getFretsByNoteAndString(Note, 4); 
			    track.addNoteNew(frets[randNoteOnFret], 4, 8);
			}
		}
	}
}
