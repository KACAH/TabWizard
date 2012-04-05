package music;
import java.util.Random;

import datastruct.TWChord;
import datastruct.TWDataException;
import datastruct.TWInstrumentTrack;
import datastruct.TWNoteEffects;
import datastruct.TWSimpleNote;


public class TWGuitar {

	/**
	 * Writes Arpeggio to the track
	 * @param chord chord, which notes are using to write an arpeggio
	 * @param track track on which we write an arpeggio
	 * @throws TWDataException
	 */
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
	
	/**
	 * Writes Arpeggio note to the track
	 * @param Note that writes on track
	 * @param String guitar string on which will be writes note
	 * @param track track on which we write an arpeggio
	 */
	private static void AcousticArpeggioNote(TWSimpleNote Note, int String, TWInstrumentTrack track)
	{
		int[] frets = track.getFretsByNoteAndString(Note, String);
		track.addNoteNew(frets[0], String, 8);
		track.getLastNote().setSimpleEffect(TWNoteEffects.LETRING, true);
	}

	/**
	 * Writes Sound wall to the track
	 * @param Chord chord, which notes are using to write an arpeggio
	 * @param track track on which we write an arpeggio
	 * @throws TWDataException
	 */
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
	
	/**
	 * Writes Sound Wall notes to the measure on track
	 * @param frets frets on guitar neck
	 * @param FretOcatave choice of note with a difference of one octave 
	 * @param track track on which we write an arpeggio
	 */
	private static void SoundWallMeasure(int[] frets, int fretOctave, TWInstrumentTrack track) throws TWDataException
	{
		for(int FullBeat = 0; FullBeat < 8; FullBeat++)
		{
			track.addNoteNew(frets[fretOctave], 6, 8);
			track.addNoteMore(frets[fretOctave] + 2, 5);
			track.addNoteMore(frets[fretOctave] + 2, 4);
		}
	}
	
	/**
	 * Writes Sound Wall with pauses to the track
	 * @param chord chord, which notes are using to write an arpeggio
	 * @param track track on which we write an arpeggio
	 * @throws TWDataException
	 */
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

	/**
	 * Writes Sound Wall with pauses note to the track
	 * @param frets frets on guitar neck
	 * @param FretOcatave choice of note with a difference of one octave 
	 * @param track track on which we write an arpeggio
	 */
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
	/**
	 * Writes Hard Sound Wall to the track
	 * @param chord chord, which notes are using to write an arpeggio
	 * @param track track on which we write an arpeggio
	 * @throws TWDataException
	 */
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
	
	/**
	 * Writes Hard Sound Wall notes to the measure on track
	 * @param frets frets on guitar neck
	 * @param FretOcatave choice of note with a difference of one octave 
	 * @param track track on which we write an arpeggio
	 */
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
	
	/**
	 * Writes Sound Wall beat with palm mute effect on track
	 * @param frets frets on guitar neck
	 * @param track track on which we write an arpeggio
	 */
	private static void HardSoundWallPalmMuteBeat(int[] frets, TWInstrumentTrack track)
	{
		track.addNoteNew(frets[0], 6, 8);
		track.getLastNote().setSimpleEffect(7, true);
	}
	
	/**
	 * Writes Sound Wall quint to the track
	 * @param frets frets on guitar neck
	 * @param FretOcatave choice of note with a difference of one octave 
	 * @param track track on which we write an arpeggio
	 */
	private static void SoundWallPowerBeat(int[] frets, int fretOctava, TWInstrumentTrack track) throws TWDataException
	{
		track.addNoteNew(frets[fretOctava], 6, 8);
		track.addNoteMore(frets[fretOctava] + 2, 5);
		track.addNoteMore(frets[fretOctava] + 2, 4);
	}
}
