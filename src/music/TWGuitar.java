package music;
import java.util.Random;

import datastruct.TWChord;
import datastruct.TWDataException;
import datastruct.TWInstrumentTrack;
import datastruct.TWNoteEffects;
import datastruct.TWSimpleNote;


public class TWGuitar {

	static public void AcousticArpeggio(TWChord chord, TWInstrumentTrack track)throws TWDataException
	{
		Random rn = new Random();
		TWSimpleNote Note;

		for(int i = 0; i < 8; i++)
		{
			TWSimpleNote BaseNote = chord.getNote(0);
			int randNote = rn.nextInt(3);
			int randString = rn.nextInt(6)+1;

			Note = chord.getNote(randNote);
			if(i == 0)
				AcousticArpeggioNote(BaseNote, 6, track);
			else
				AcousticArpeggioNote(Note, randString, track);
		}
	}
	
	private static void AcousticArpeggioNote(TWSimpleNote Note, int String, TWInstrumentTrack track)
	{
		int[] frets = track.getFretsByNoteAndString(Note, String);
		track.addNoteNew(frets[0], String, 8);
		track.getLastNote().setSimpleEffect(TWNoteEffects.LETRING, true);
	}

	static public void writeSoundWall(TWChord Chord, TWInstrumentTrack track) throws TWDataException
	{
		Random rn = new Random();
		int randOctava = rn.nextInt(2);

		int[] frets = track.getFretsByNoteAndString(Chord.getNote(0), 6);

		if(frets[0] < 5)
		{
			if(randOctava == 0)
				SoundWallMeasure(frets, 0, track);
			else
				SoundWallMeasure(frets, 1, track);
		}
		else
			SoundWallMeasure(frets, 0, track);
	}
	
	private static void SoundWallMeasure(int[] frets, int fretOctava, TWInstrumentTrack track) throws TWDataException
	{
		for(int FullBeat = 0; FullBeat < 8; FullBeat++)
		{
			track.addNoteNew(frets[0], 6, 8);
			track.addNoteMore(frets[0] + 2, 5);
			track.addNoteMore(frets[0] + 2, 4);
		}
	}

	static public void writePauseSoundWall(TWChord Chord, TWInstrumentTrack track) throws TWDataException
	{
		Random rn = new Random();
		int randOctava = rn.nextInt(2);

		int[] frets = track.getFretsByNoteAndString(Chord.getNote(0), 6);

		if(frets[0] < 2)
		{
			if(randOctava == 0)
				PauseSoundWallMeasure(frets, 0, track);
			else
				PauseSoundWallMeasure(frets, 0, track);
		}
		else
			PauseSoundWallMeasure(frets, 0, track);
	}

	private static void PauseSoundWallMeasure(int[] frets, int fretOctava, TWInstrumentTrack track) throws TWDataException
	{
		Random rn = new Random();
		
		for(int FullBeat = 0; FullBeat < 8; FullBeat++)
		{
			int randPause = rn.nextInt(4);

			if(randPause == 0)
				track.addRest(8);
			else
				SoundWallPowerBeat(frets, fretOctava, track);
		}
	}
	
	static public void writeHardSoundWall(TWChord Chord, TWInstrumentTrack track) throws TWDataException
	{
		Random rn = new Random();
		int randOctava = rn.nextInt(2);

		int[] frets = track.getFretsByNoteAndString(Chord.getNote(0), 6);

		if(frets[0] < 2)
		{
			if(randOctava == 0)
				HardSoundWallMeasure(frets, 0, track);
			else
				HardSoundWallMeasure(frets, 1, track);
		}
		else
			HardSoundWallMeasure(frets, 0, track);
	}
	
	private static void HardSoundWallMeasure(int[] frets, int fretOctava, TWInstrumentTrack track) throws TWDataException
	{
		Random rn = new Random();
		
		for(int FullBeat = 0; FullBeat < 8; FullBeat++)
		{
			int randPalm = rn.nextInt(4);

			if(randPalm == 0)
				HardSoundWallPalmMuteBeat(frets, track);
			else
				SoundWallPowerBeat(frets, fretOctava, track);
		}
	}
	
	private static void HardSoundWallPalmMuteBeat(int[] frets, TWInstrumentTrack track)
	{
		track.addNoteNew(frets[0], 6, 8);
		track.getLastNote().setSimpleEffect(7, true);
	}
	
	private static void SoundWallPowerBeat(int[] frets, int fretOctava, TWInstrumentTrack track) throws TWDataException
	{
		track.addNoteNew(frets[fretOctava], 6, 8);
		track.addNoteMore(frets[fretOctava] + 2, 5);
		track.addNoteMore(frets[fretOctava] + 2, 4);
	}
}
