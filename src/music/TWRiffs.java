package music;
import java.util.Random;

import datastruct.TWDataException;
import datastruct.TWInstrumentTrack;
import datastruct.TWSimpleNote;


public class TWRiffs {

	/**
	 * Writes a simple riff to the track
	 * @param Scale scale that is used to write riff
	 * @param track track on which we write a riff
	 * @param bassTrack track on which we write a riff bass part
	 * @throws TWDataException
	 */
	static public void writeSimpleRiff(TWScale Scale, TWInstrumentTrack track, TWInstrumentTrack bassTrack) throws TWDataException
	{
		Random rn = new Random();
		int[] frets;
		TWSimpleNote MainNote = TWHarmonyGenerator.getMainChord().getNote(0);
		Scale = TWScaleManager.transponScale(Scale, MainNote.getName());

		for(int FullBeat = 0; FullBeat < 8; FullBeat++)
		{
			int randScaleNote = rn.nextInt(Scale.ScaleSize());
			int randMainOrScaleNote = rn.nextInt(2);
			int rand16Note = rn.nextInt(3);
			int randOctava = rn.nextInt(3);
			int randString = rn.nextInt(2)+5;

			for(int i = 0; i < Scale.ScaleSize(); i++)
			{
				//Using main chord note. Probability 1/2
				if(randMainOrScaleNote == 0 && FullBeat < 8)
				{
					frets = track.getFretsByNoteAndString(MainNote, 6);

					//Using eighth note. Probability 2/3
					if(rand16Note == 0 || rand16Note == 1)
					{
						SimpleRiffNote(frets, 0, 6, 4, 8, true, track, bassTrack);
						i = Scale.ScaleSize() - 1;
					}
					//Using sixteenth note. Probability 1/3
					if(rand16Note == 2)
					{
						SimpleRiffNote(frets, 0, 6, 4, 16, true, track, bassTrack);
						SimpleRiffNote(frets, 0, 6, 4, 16, true, track, bassTrack);
						i = Scale.ScaleSize() - 1;
					}					
				}

				//Using note from scale. Probability 1/2
				if(randMainOrScaleNote == 1 && FullBeat < 8)
				{
					//Using concrete note from scale. Probability depends on Scale size
					if(randScaleNote == i)
					{
						frets = track.getFretsByNoteAndString(Scale.getNote(i), randString);

						//Write note from different octave. Probability depends on Scale size
						if(randOctava == 0 || randOctava == 1)
							SimpleRiffNote(frets, 0, randString, randString-2, 8, false, track, bassTrack);
						else
							SimpleRiffNote(frets, 1, randString, randString-2, 8, false, track, bassTrack);
					}
				}
			}			
		}
	}	

	/**
	 * Adds a note to the track and sets effects
	 * @param frets frets on guitar neck
	 * @param FretOcatave choice of note with a difference of one octave 
	 * @param string guitar string that will be used
	 * @param string Bass guitar string that will be used
	 * @param duration duration of note
	 * @param PalmMute effect, that can be used
	 * @param track track on which we write a note
	 * @param bassTrack track on which we write a note
	 */
	private static void SimpleRiffNote(int[] frets, int FretOcatave, int string, int bassString, int duration, boolean PalmMute, TWInstrumentTrack track, TWInstrumentTrack bassTrack)
	{
		track.addNoteNew(frets[FretOcatave], string, duration);
		if(PalmMute)
			track.getLastNote().setSimpleEffect(7, true);
		bassTrack.addNoteNew(frets[0], bassString, duration);
	}

	/**
	 * Writes a simple power riff to the track
	 * @param Scale scale that is used to write riff
	 * @param track track on which we write a riff
	 * @param bassTrack track on which we write a riff bass part
	 * @throws TWDataException
	 */
	static public void writeSimplePowerRiff(TWScale Scale, TWInstrumentTrack track, TWInstrumentTrack bassTrack) throws TWDataException
	{
		Random rn = new Random();
		int[] frets;
		TWSimpleNote MainNote = TWHarmonyGenerator.getMainChord().getNote(0);
		Scale = TWScaleManager.transponScale(Scale, MainNote.getName());

		for(int FullBeat = 0; FullBeat < 8; FullBeat++)
		{
			int randScaleNote = rn.nextInt(Scale.ScaleSize());
			int randMainOrScaleNote = rn.nextInt(2);

			for(int i = 0; i < Scale.ScaleSize(); i++)
			{
				//Using main chord note. Probability 1/2
				if(randMainOrScaleNote == 0 && FullBeat < 8)
				{
					frets = track.getFretsByNoteAndString(MainNote, 6);

					track.addNoteNew(frets[0], 6, 8);
					track.getLastNote().setSimpleEffect(7, true); // set Palm Mute
					bassTrack.addNoteNew(frets[0], 4, 8);

					i = Scale.ScaleSize() - 1;					
				}
				//Using note from scale. Probability 1/2
				if(randMainOrScaleNote == 1 && FullBeat < 8)
					if(randScaleNote == i)
					{
						frets = track.getFretsByNoteAndString(Scale.getNote(i), 5);

						track.addNoteNew(frets[0], 5, 8);
						track.addNoteMore(frets[0]+2, 4); // writes a power chord
						bassTrack.addNoteNew(frets[0], 3, 8);
					}
			}	
		}
	}

	/**
	 * Writes a power riff to the track
	 * @param Scale scale that is used to write riff
	 * @param track track on which we write a riff
	 * @param bassTrack track on which we write a riff bass part
	 * @throws TWDataException
	 */
	public static void writePowerRiff(TWScale Scale, TWInstrumentTrack track, TWInstrumentTrack bassTrack) throws TWDataException
	{
		Random rn = new Random();
		TWSimpleNote MainNote = TWHarmonyGenerator.getMainChord().getNote(0);
		Scale = TWScaleManager.transponScale(Scale, MainNote.getName());

		for(int FullBeat = 0; FullBeat < 8;)
		{
			int randScaleNote = rn.nextInt(Scale.ScaleSize());
			int randBaseOrScaleNote = rn.nextInt(2);
			int randPause = rn.nextInt(4);
			boolean pause = false;

			//Writes pause
			if(randPause == 0 && FullBeat < 8 && FullBeat != 0)	{
				PowerRiffPause(track, bassTrack);
				FullBeat++;
				pause = true;
			}

			for(int i = 0; i < Scale.ScaleSize(); i++)

				//Using main chord note. Probability 1/2
				if(randBaseOrScaleNote == 0 && FullBeat < 8 && !pause)
				{
					if(randScaleNote == i)	{
						PowerRiffBeat(track.getFretsByNoteAndString(Scale.getNote(i), 6), track, bassTrack);					
						FullBeat++;
					}
				}
			//Using note from scale. Probability 1/2
				else
					if(FullBeat < 8){
						PowerRiffBeat(track.getFretsByNoteAndString(MainNote, 6), track, bassTrack);
						FullBeat++;
						i = Scale.ScaleSize() - 1;
					}
		}
	}

	/**
	 * Adds a note quint to the track
	 * @param frets frets on guitar neck
	 * @param track track on which we write a quint
	 * @param bassTrack track on which we write a quint
	 * @throws TWDataException
	 */
	private static void PowerRiffBeat(int[] frets, TWInstrumentTrack track, TWInstrumentTrack bassTrack) throws TWDataException	{
		track.addNoteNew(frets[0], 6, 8);
		track.addNoteMore(frets[0]+2, 5); // writes a power chord
		track.addNoteMore(frets[0]+2, 4); // writes a power chord
		bassTrack.addNoteNew(frets[0], 4, 8);
	}

	/**
	 * Adds a rest to the track
	 * @param track track on which we write a rest
	 * @param bassTrack track on which we write a rest
	 * @throws TWDataException
	 */
	private static void PowerRiffPause(TWInstrumentTrack track, TWInstrumentTrack bassTrack) throws TWDataException {
		track.addRest(8);
		bassTrack.addRest(8);
	}

}


