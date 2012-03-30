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

		int[] frets;

		for(int i = 0; i < 8; i++)
		{
			TWSimpleNote BaseNote = chord.getNote(0);
			int randNote = rn.nextInt(3);
			int randString = rn.nextInt(6)+1;

			Note = chord.getNote(randNote);
			if(i == 0)
			{
				frets = track.getFretsByNoteAndString(BaseNote, 6);
				track.addNoteNew(frets[0], 6, 8);
				track.getLastNote().setSimpleEffect(TWNoteEffects.LETRING, true);
			}
			else
			{
				frets = track.getFretsByNoteAndString(Note, randString);
				track.addNoteNew(frets[0], randString, 8);
				track.getLastNote().setSimpleEffect(TWNoteEffects.LETRING, true);
			}
		}
	}

	static public void writeSoundWall(TWChord Chord, TWInstrumentTrack track) throws TWDataException
	{
		Random rn = new Random();
		int randOctava = rn.nextInt(2);

		int[] frets = track.getFretsByNoteAndString(Chord.getNote(0), 6);

		if(frets[0] < 5)
		{
			if(randOctava == 0)
			{
				for(int FullBeat = 0; FullBeat < 8; FullBeat++)
				{
					track.addNoteNew(frets[0], 6, 8);
					track.addNoteMore(frets[0] + 2, 5);
					track.addNoteMore(frets[0] + 2, 4);
				}
			}
			else
			{
				for(int FullBeat = 0; FullBeat < 8; FullBeat++)
				{
					track.addNoteNew(frets[1], 6, 8);
					track.addNoteMore(frets[1] + 2, 5);
					track.addNoteMore(frets[1] + 2, 4);
				}
			}
		}
		else
		{
			for(int FullBeat = 0; FullBeat < 8; FullBeat++)
			{
				track.addNoteNew(frets[0], 6, 8);
				track.addNoteMore(frets[0] + 2, 5);
				track.addNoteMore(frets[0] + 2, 4);
			}
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
			{
				for(int FullBeat = 0; FullBeat < 8; FullBeat++)
				{
					int randPause = rn.nextInt(4);

					if(randPause == 0)
					{
						track.addRest(8);
					}
					else
					{
						track.addNoteNew(frets[0], 6, 8);
						track.addNoteMore(frets[0] + 2, 5);
						track.addNoteMore(frets[0] + 2, 4);
					}
				}
			}
			else
			{
				for(int FullBeat = 0; FullBeat < 8; FullBeat++)
				{
					int randPause = rn.nextInt(4);

					if(randPause == 0)
					{
						track.addRest(8);
					}
					else
					{
						track.addNoteNew(frets[1], 6, 8);
						track.addNoteMore(frets[1] + 2, 5);
						track.addNoteMore(frets[1] + 2, 4);
					}
				}
			}
		}
		else
		{
			for(int FullBeat = 0; FullBeat < 8; FullBeat++)
			{
				int randPause = rn.nextInt(4);

				if(randPause == 0)
				{
					track.addRest(8);
				}
				else
				{
					track.addNoteNew(frets[0], 6, 8);
					track.addNoteMore(frets[0] + 2, 5);
					track.addNoteMore(frets[0] + 2, 4);
				}
			}
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
			{
				for(int FullBeat = 0; FullBeat < 8; FullBeat++)
				{
					int randPause = rn.nextInt(4);

					if(randPause == 0)
					{
						track.addNoteNew(frets[0], 6, 8);
						track.getLastNote().setSimpleEffect(7, true);
					}
					else
					{
						track.addNoteNew(frets[0], 6, 8);
						track.addNoteMore(frets[0] + 2, 5);
						track.addNoteMore(frets[0] + 2, 4);
					}
				}
			}
			else
			{
				for(int FullBeat = 0; FullBeat < 8; FullBeat++)
				{
					int randPause = rn.nextInt(4);

					if(randPause == 0)
					{
						track.addNoteNew(frets[0], 6, 8);
						track.getLastNote().setSimpleEffect(7, true);
					}
					else
					{
						track.addNoteNew(frets[1], 6, 8);
						track.addNoteMore(frets[1] + 2, 5);
						track.addNoteMore(frets[1] + 2, 4);
					}
				}
			}
		}
		else
		{
			for(int FullBeat = 0; FullBeat < 8; FullBeat++)
			{
				int randPause = rn.nextInt(4);

				if(randPause == 0)
				{
					track.addNoteNew(frets[0], 6, 8);
					track.getLastNote().setSimpleEffect(7, true);
				}
				else
				{
					track.addNoteNew(frets[0], 6, 8);
					track.addNoteMore(frets[0] + 2, 5);
					track.addNoteMore(frets[0] + 2, 4);
				}
			}
		}
	}
}
