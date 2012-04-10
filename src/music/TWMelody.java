package music;
import java.util.Random;

import datastruct.TWChord;
import datastruct.TWInstrumentTrack;
import datastruct.TWNoteEffects;


public class TWMelody {

	static int FullBeat;
	static boolean is16 = false;

	/**
	 * Writes a simple melody to the track
	 * @param harmony harmony that is used to write melody
	 * @param chord current chord, which notes are used to write melody
	 * @param track track on which we write a melody
	 */
	static public void WriteSimpleMelody (TWHarmony harmony, TWChord chord, TWInstrumentTrack track)
	{
		TWScale HarmonyScale = TWScaleManager.constructHarmonyScale(harmony);

		Random rn = new Random();
		int duration[] = {1, 2, 4, 8, 16};
		int frets[] = null;

		for(FullBeat = 64; FullBeat > 0;)
		{
			int randMelodyStep = rn.nextInt(5); 
			int randBaseNote = rn.nextInt(3);
			int randScaleNote = rn.nextInt(HarmonyScale.ScaleSize());
			int NormRestDot = rn.nextInt(14);
			int randDuration;

			if(!is16)
				randDuration = rn. nextInt(duration.length);
			else
			{
				randDuration = rn.nextInt(1)+4; // Half note after sixteenth sounds terrible
				NormRestDot = rn.nextInt(8);
			}

			if(!TWHarmonyManager.onceNoteInHarmony(harmony, HarmonyScale.getNote(randScaleNote)))	
					writeMelodyStep(HarmonyScale, chord, randBaseNote, randScaleNote, randMelodyStep, NormRestDot, randDuration, frets, track);
		}
	}

	/**
	 * The choice between the notes from the chord or from scale
	 * @param HarmonyScale scale that is used to write melody
	 * @param chord current chord, which notes are used to write melody
	 * @param randBaseNote random integer, which chooses note from chord
	 * @param randScaleNote random integer, which chooses note from scale
	 * @param randMelodyStep random integer, which chooses note from chord, or from scale
	 * @param NormRestDot random integer, which chooses note type (Normal, dotted, or rest)
	 * @param randDuration random integer, which chooses note duration
	 * @param frets frets on guitar neck
	 * @param track track on which we write a melody
	 */
	private static void writeMelodyStep(TWScale HarmonyScale, TWChord chord, int randBaseNote, int randScaleNote, int randMelodyStep, int NormRestDot, int randDuration, int[] frets, TWInstrumentTrack track)
	{
		for(int i = 0; i < HarmonyScale.ScaleSize(); i++)
		{
			// Note from harmony or not. Probability 1/5
			if(randMelodyStep == 0)
				if(randScaleNote == i && FullBeat >= 0)
				{
					frets = track.getFretsByNoteAndString(HarmonyScale.getNote(i), 4);
					setNormalDottedOrRest(randMelodyStep, NormRestDot, randDuration, frets, track);
				}
			else
				if(TWScaleManager.getNotesIndexFromScale(HarmonyScale, chord)[randBaseNote] == i && FullBeat >= 0)
				{
					frets = track.getFretsByNoteAndString(HarmonyScale.getNote(i), 4);
					setNormalDottedOrRest(randMelodyStep, NormRestDot, randDuration, frets, track);
				}
		}
	}
	
	/**
	 * Sets note type (Normal, dotted, or rest)
	 * @param randMelodyStep random integer, which chooses note from chord, or from scale
	 * @param NormRestDot random integer, which chooses note type (Normal, dotted, or rest)
	 * @param randDuration random integer, which chooses note duration
	 * @param frets frets on guitar neck
	 * @param track track on which we write a melody
	 */
	private static void setNormalDottedOrRest(int randMelodyStep, int NormRestDot, int randDuration, int[]frets, TWInstrumentTrack track)
	{	
		// Writes normal note, without dot. Probability 4/7
		if(NormRestDot == 0 || NormRestDot == 1 || NormRestDot == 2 || NormRestDot == 3 || NormRestDot == 4 || NormRestDot == 5 || NormRestDot == 6 || NormRestDot == 7)
			MelodyStepNotes(randMelodyStep, randDuration, frets, track);

		if(randMelodyStep == 0)
		{
			// Writes rest. Probability 1/14
			if(NormRestDot == 8)
				RestDurationChoice(randDuration, track);
		}
		else
			return;
		
		// Writes note, with dot. Probability 2/7
		if(NormRestDot == 9 || NormRestDot == 10 || NormRestDot == 11 || NormRestDot == 12)
			MelodyStepDotted(randMelodyStep, randDuration, frets, track);
	}

	/**
	 * Note duration choice for melody step
	 * @param randMelodyStep random integer, which chooses note from chord, or from scale
	 * @param randDuration random integer, which chooses note duration
	 * @param frets frets on guitar neck
	 * @param track track on which we write a melody
	 */
	private static void MelodyStepNotes(int randMelodyStep, int randDuration, int[] frets, TWInstrumentTrack track)
	{
		if(randMelodyStep == 0)
		{
			NoteDurationChoice(randDuration, 2, frets, track);
			NoteDurationChoice(randDuration, 3, frets, track);
			NoteDurationChoice(randDuration, 4, frets, track);
		}
		else
		{
			NoteDurationChoice(randDuration, 0, frets, track);
			NoteDurationChoice(randDuration, 1, frets, track);
			NoteDurationChoice(randDuration, 2, frets, track);
		}
	}

	/**
	 * Dotted note duration choice for melody step
	 * @param randMelodyStep random integer, which chooses note from chord, or from scale
	 * @param randDuration random integer, which chooses note duration
	 * @param frets frets on guitar neck
	 * @param track track on which we write a melody
	 */
	private static void MelodyStepDotted(int randMelodyStep, int randDuration, int[] frets, TWInstrumentTrack track)
	{
		if(randMelodyStep == 0)
		{		
			DottedDurationChoice(randDuration, 2, frets, track);
			DottedDurationChoice(randDuration, 3, frets, track);
		}
		else
		{			
			DottedDurationChoice(randDuration, 1, frets, track);
			DottedDurationChoice(randDuration, 2, frets, track);
			DottedDurationChoice(randDuration, 3, frets, track);
		}
	}

	/**
	 * Note duration choice
	 * @param randDuration random integer, which chooses note duration
	 * @param reqDuration required duration
	 * @param frets frets on guitar neck
	 * @param track track on which we write a melody
	 */
	private static void NoteDurationChoice(int randDuration, int reqDuration, int[] frets, TWInstrumentTrack track)
	{
		for(int i = 0, j = 64; i < 5; i++, j = j/2) 
		{
			if(randDuration == i && FullBeat >= j)
				if(reqDuration == randDuration)
					writeNote(frets, randDuration, track);
		}
	}
	
	/**
	 * Dotted note duration choice
	 * @param randDuration random integer, which chooses note duration
	 * @param reqDuration required duration
	 * @param frets frets on guitar neck
	 * @param track track on which we write a melody
	 */
	private static void DottedDurationChoice(int randDuration, int reqDuration, int[] frets, TWInstrumentTrack track)
	{
		for(int i = 1, j = 48; i < 4; i++, j = j/2)
		{
			if(randDuration == i && FullBeat >= j)
				if(reqDuration == randDuration)
				writeDottedNote(frets, randDuration, track);
		}
	}

	/**
	 * Rest duration choice
	 * @param randDuration random integer, which chooses note duration
	 * @param track track on which we write a melody
	 */
	private static void RestDurationChoice(int randDuration, TWInstrumentTrack track)
	{
		for(int i = 0, j = 64; i < 4; i++, j = j/2)
		{
			if(randDuration == i && FullBeat >= j)
				writeRest(randDuration, track);
		}
	}

	/**
	 * Write note on track
	 * @param frets frets on guitar neck
	 * @param randDuration random integer, which chooses note duration
	 * @param track track on which we write a melody
	 */
	private static void writeNote(int[] frets, int randomDuration, TWInstrumentTrack track)
	{
		int duration[] = {1, 2, 4, 8, 16};

		track.addNoteNew(frets[0], 4, duration[randomDuration]);

		if(randomDuration == 0)
		{
			track.getLastNote().setSimpleEffect(TWNoteEffects.VIBRATO, true);
			FullBeat -= 64;	
		}
		if(randomDuration == 1)
		{
			track.getLastNote().setSimpleEffect(TWNoteEffects.VIBRATO, true);
			FullBeat -= 32;	
		}
		if(randomDuration == 2)
			FullBeat -= 16;
		if(randomDuration == 3)
			FullBeat -= 8;
		if(randomDuration == 4)
			FullBeat -= 4;
	}

	/**
	 * Write dotted note on track
	 * @param frets frets on guitar neck
	 * @param randDuration random integer, which chooses note duration
	 * @param track track on which we write a melody
	 */
	private static void writeDottedNote(int[] frets, int randomDuration, TWInstrumentTrack track)
	{
		int duration[] = {1, 2, 4, 8, 16};

		if(randomDuration == 0 || randomDuration == 4)
			return;

		track.addNoteNew(frets[0], 4, duration[randomDuration]);
		track.getLastBeat().setDotted(true);

		if(randomDuration == 1)
		{
			FullBeat -= 48;
			track.getLastNote().setSimpleEffect(TWNoteEffects.VIBRATO, true);
		}
		if(randomDuration == 2)
			FullBeat -= 24;
		if(randomDuration == 3)
			FullBeat -= 12;
	}

	/**
	 * Write rest on track
	 * @param randDuration random integer, which chooses note duration
	 * @param track track on which we write a melody
	 */
	private static void writeRest(int randomDuration, TWInstrumentTrack track)
	{
		int duration[] = {1, 2, 4, 8, 16};

		if(randomDuration == 4)
			return;

		track.addRest(duration[randomDuration]);

		if(randomDuration == 0)
			FullBeat -= 64;
		if(randomDuration == 1)
			FullBeat -= 32;
		if(randomDuration == 2)
			FullBeat -= 16;
		if(randomDuration == 3)
			FullBeat -= 8;
	}
}
