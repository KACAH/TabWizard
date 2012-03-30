package music;
import java.util.Random;

import datastruct.TWDataException;
import datastruct.TWInstrumentTrack;
import datastruct.TWSimpleNote;


public class TWRiffs {

	final static public int WITH_BASS = 0;
	final static public int WITHOUT_BASS = 1;
	
	static public void writeSimpleRiff(TWScale Scale, TWInstrumentTrack track, TWInstrumentTrack bassTrack) throws TWDataException
	{
		Random rn = new Random();
		int[] frets;
		TWSimpleNote MainNote = TWHarmonyGenerator.getMainChord().getNote(0);
		Scale = TWScaleManager.transponScale(Scale, MainNote.getName());

		for(int FullBeat = 0; FullBeat < 8;)
		{
			int randScaleNote = rn.nextInt(Scale.ScaleSize());
			int randMainOrScaleNote = rn.nextInt(2);
			int rand16Note = rn.nextInt(3);
			int randOctava = rn.nextInt(2);
			int randString = rn.nextInt(2)+5;

			for(int i = 0; i < Scale.ScaleSize(); i++)
			{
				if(randMainOrScaleNote == 0 && FullBeat < 8)
				{
					frets = track.getFretsByNoteAndString(MainNote, 6);

					if(rand16Note == 0 || rand16Note == 1)
					{
						track.addNoteNew(frets[0], 6, 8);
						track.getLastNote().setSimpleEffect(7, true);
						bassTrack.addNoteNew(frets[0], 4, 8);

						FullBeat++;
						i = Scale.ScaleSize() - 1;
					}
					if(rand16Note == 2)
					{
						track.addNoteNew(frets[0], 6, 16);
						track.getLastNote().setSimpleEffect(7, true);
						bassTrack.addNoteNew(frets[0], 4, 16);

						track.addNoteNew(frets[0], 6, 16);
						track.getLastNote().setSimpleEffect(7, true);
						bassTrack.addNoteNew(frets[0], 4, 16);

						FullBeat++;
						i = Scale.ScaleSize() - 1;
					}					
				}
				if(randMainOrScaleNote == 1 && FullBeat < 8)
				{
					if(randScaleNote == i)
					{
						frets = track.getFretsByNoteAndString(Scale.getNote(i), randString);

						if(randOctava == 0 || randOctava == 1)
						{
							track.addNoteNew(frets[0], randString, 8);
							bassTrack.addNoteNew(frets[0], randString-2, 8);
							FullBeat++;
						}
						else
						{
							track.addNoteNew(frets[1], randString, 8);
							bassTrack.addNoteNew(frets[0], randString-2, 8);
							FullBeat++;
						}
					}
				}
			}			
		}
	}	

	static public void writeSimplePowerRiff(TWScale Scale, TWInstrumentTrack track, TWInstrumentTrack bassTrack) throws TWDataException
	{
		Random rn = new Random();
		int[] frets;
		TWSimpleNote MainNote = TWHarmonyGenerator.getMainChord().getNote(0);
		Scale = TWScaleManager.transponScale(Scale, MainNote.getName());

		for(int FullBeat = 0; FullBeat < 8;)
		{
			int randScaleNote = rn.nextInt(Scale.ScaleSize());
			int randMainOrScaleNote = rn.nextInt(2);

			for(int i = 0; i < Scale.ScaleSize(); i++)
			{
				if(randMainOrScaleNote == 0 && FullBeat < 8)
				{
					frets = track.getFretsByNoteAndString(MainNote, 6);

					track.addNoteNew(frets[0], 6, 8);
					track.getLastNote().setSimpleEffect(7, true);
					bassTrack.addNoteNew(frets[0], 4, 8);

					FullBeat++;
					i = Scale.ScaleSize() - 1;					
				}

				if(randMainOrScaleNote == 1 && FullBeat < 8)
				{
					if(randScaleNote == i)
					{
						frets = track.getFretsByNoteAndString(Scale.getNote(i), 5);

						track.addNoteNew(frets[0], 5, 8);
						track.addNoteMore(frets[0]+2, 4);

						bassTrack.addNoteNew(frets[0], 3, 8);
						FullBeat++;
					}
				}
			}	
		}
	}

	

	public static void writePowerRiff(TWScale Scale, TWInstrumentTrack track, TWInstrumentTrack bassTrack) throws TWDataException
	{
		Random rn = new Random();
		int frets[];
		TWSimpleNote MainNote = TWHarmonyGenerator.getMainChord().getNote(0);
		Scale = TWScaleManager.transponScale(Scale, MainNote.getName());

		for(int FullBeat = 0; FullBeat < 8;)
		{
			int randScaleNote = rn.nextInt(Scale.ScaleSize());
			int randBaseOrScaleNote = rn.nextInt(3);
			int randPause = rn.nextInt(4);
			boolean pause = false;

			if(randPause == 0 && FullBeat < 8 && FullBeat != 0)
			{
				track.addRest(8);
				bassTrack.addRest(8);
				FullBeat++;
				pause = true;
			}

			for(int i = 0; i < Scale.ScaleSize(); i++)
			{
				if(randBaseOrScaleNote == 0 && FullBeat < 8 && !pause)
				{
					if(randScaleNote == i)
					{
						frets = track.getFretsByNoteAndString(Scale.getNote(i), 6);

						track.addNoteNew(frets[0], 6, 8);
						track.addNoteMore(frets[0]+2, 5);
						track.addNoteMore(frets[0]+2, 4);
						bassTrack.addNoteNew(frets[0], 4, 8);
						FullBeat++;

					}
				}
				else
				{
					if(FullBeat < 8)
					{
						frets = track.getFretsByNoteAndString(MainNote, 6);

						track.addNoteNew(frets[0], 6, 8);
						track.addNoteMore(frets[0]+2, 5);
						track.addNoteMore(frets[0]+2, 4);
						bassTrack.addNoteNew(frets[0], 4, 8);

						FullBeat++;
						i = Scale.ScaleSize() - 1;
					}
				}
			}
		}
	}
}


