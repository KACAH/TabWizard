package music;
import java.util.Random;

import datastruct.TWDataException;
import datastruct.TWPercussionTrack;


public class TWDrums {

	static public void WriteSimpleDrums_1_5(TWPercussionTrack track)throws TWDataException
	{
		Random rnd = new Random();

		for(int i = 0; i < 8; i++)
		{
			int randFill = rnd.nextInt(30);
			int randBD = rnd.nextInt(10);

			if(randFill == 0)
			{
				InsertSimpleBreak(i, track);
				i = 8;
			}
			else
			{
				track.addNoteNew(42, 2, 8);
				if(i == 0)
					track.addNoteMore(36, 6);
				if(randBD == 0 || randBD == 1)
					track.addNoteMore(36, 5);
				if(i == 4)
					track.addNoteMore(38, 5);
				if(randBD == 3)
					track.addNoteMore(38, 4);
			}
		}
	}

	static public void WriteSimpleDrums_1_3(TWPercussionTrack track) throws TWDataException
	{
		Random rnd = new Random();

		for(int i = 0; i < 8; i++)
		{
			int randBD = rnd.nextInt(10);
			int randBreak = rnd.nextInt(30);

			if(randBreak == 0)
			{
				InsertSimpleBreak(i, track);
				i = 8;
			}
			else
			{
				track.addNoteNew(42, 2, 8);
				if(i == 0 || i == 4)
					track.addNoteMore(36, 6);
				if(randBD == 0 || randBD == 1)
					track.addNoteMore(36, 5);
				if(i == 2 || i == 6)
					track.addNoteMore(38, 5);
			}
		}
	}

	static public void WriteSimpleDrums_1_2(TWPercussionTrack track) throws TWDataException
	{
		Random rnd = new Random();

		for(int i = 0; i < 8; i++)
		{
			boolean doubleBass = false;
			int randBD = rnd.nextInt(10);
			int randBreak = rnd.nextInt(30);

			if(randBreak == 0)
			{
				InsertSimpleBreak(i, track);
				i = 8;
			}
			else
			{
				if(i == 0 || i == 2 || i == 4 || i == 6)
				{
					if(randBD == 0 || randBD == 1 || randBD == 2 || randBD == 4)
					{ 
						track.addNoteNew(42, 2, 16);

						track.getLastBeat().removeNote(6);
						track.addNoteMore(36, 6);
						track.addNoteNew(36, 6, 16);
						doubleBass = true;
					}
				}

				if(!doubleBass)
					track.addNoteNew(42, 2, 8);

				if(i == 0 || i == 2 || i == 4 || i == 6)
				{
					track.addNoteMore(36, 6);
				}

				if(i == 1 || i == 3 || i == 5 || i == 7)
					track.addNoteMore(38, 5);
			}
		}
	}

	static public void WriteDoubleHatDrums_1_5(TWPercussionTrack track)throws TWDataException
	{
		Random rnd = new Random();

		for(int i = 0; i < 8; i++)
		{
			int randBD = rnd.nextInt(10);
			int randFill = rnd.nextInt(30);

			if(randFill == 0)
			{
				InsertSimpleBreak(i, track);
				i = 8;
			}
			else
			{
				track.addNoteNew(42, 2, 16);

				if(i == 0)
					track.addNoteMore(36, 6);
				if(randBD == 0 || randBD == 1)
					track.addNoteMore(36, 5);
				if(i == 4)
					track.addNoteMore(38, 5);
				if(randBD == 3)
					track.addNoteMore(38, 4);
				if(i == 0 && randBD == 5)
					track.addNoteMore(49, 1);

				track.addNoteNew(42, 2, 16);
			}
		}
	}	

	static public void WriteDoubleHatDrums_1_3(TWPercussionTrack track) throws TWDataException
	{
		Random rnd = new Random();

		for(int i = 0; i < 8; i++)
		{
			int randBD = rnd.nextInt(10);
			int randBreak = rnd.nextInt(30);

			if(randBreak == 0)
			{
				InsertSimpleBreak(i, track);
				i = 8;
			}
			else
			{
				track.addNoteNew(42, 2, 16);

				if(i == 0 || i == 4)
					track.addNoteMore(36, 6);
				if(randBD == 0 || randBD == 1)
					track.addNoteMore(36, 5);
				if(i == 2 || i == 6)
					track.addNoteMore(38, 5);
				if(i == 0 && randBD == 5)
					track.addNoteMore(49, 1);

				track.addNoteNew(42, 2, 16);
			}
		}
	}

	static public void WriteDoubleBassDrums_1_3(TWPercussionTrack track) throws TWDataException
	{
		Random rnd = new Random();

		for(int i = 0; i < 8; i++)
		{
			int randBD = rnd.nextInt(10);
			int randBreak = rnd.nextInt(30);

			if(randBreak == 0)
			{
				InsertSimpleBreak(i, track);
				i = 8;
			}
			else
			{
				track.addNoteNew(42, 2, 16);
				track.addNoteMore(36, 6);

				if(i == 2 || i == 6)
					track.addNoteMore(38, 5);
				if(i == 0 && randBD == 5)
					track.addNoteMore(49, 1);

				track.addNoteNew(36, 6, 16);
			}
		}
	}

	static public void WriteDoubleBassDrums_1_2(TWPercussionTrack track) throws TWDataException
	{
		Random rnd = new Random();

		for(int i = 0; i < 8; i++)
		{
			int randBD = rnd.nextInt(10);
			int randBreak = rnd.nextInt(30);

			if(randBreak == 0)
			{
				InsertSimpleBreak(i, track);
				i = 8;
			}
			else
			{
				track.addNoteNew(42, 2, 16);
				track.addNoteMore(36, 6);

				if(i == 1 || i == 3 || i == 5 || i == 7)
					track.addNoteMore(38, 5);
				if(i == 0 && randBD == 5)
					track.addNoteMore(49, 1);

				track.addNoteNew(36, 6, 16);
			}
		}
	}

	static public void WriteOpenHatDrums_1_5(TWPercussionTrack track)throws TWDataException
	{
		Random rnd = new Random();

		for(int i = 0; i < 8; i++)
		{
			int randBreak = rnd.nextInt(30);
			int randExtraDrum = rnd.nextInt(10);
			int randRest = rnd.nextInt(2);
			int randElem = rnd.nextInt(4);

			if(randBreak == 0)
			{
				InsertSimpleBreak(i, track);
				i = 8;
			}
			else
			{
				if(i == 0 || i == 2 || i == 4 || i == 6)
					track.addNoteNew(46, 2, 8);

				if(i == 1 || i == 3 || i == 5 || i == 7)
				{
					if(randRest == 0)
						track.addRest(8);
					else
					{
						if(randElem == 0)
							track.addNoteNew(36, 6, 8);
						if(randElem == 1)
							track.addNoteNew(38, 6, 8);
						if(randElem == 2)
							track.addNoteNew(43, 6, 8);
						if(randElem == 3)
							track.addNoteNew(47, 6, 8);
					}
				}

				if(i == 0)
					track.addNoteMore(36, 6);
				if(i == 4)
					track.addNoteMore(38, 5);

				if(randExtraDrum == 0 || randExtraDrum == 1)
					track.addNoteMore(36, 5);
				if(randExtraDrum == 3)
					track.addNoteMore(38, 4);
			}
		}
	}

	static public void WriteOpenHatDrums_1_3(TWPercussionTrack track) throws TWDataException
	{
		Random rnd = new Random();

		for(int i = 0; i < 8; i++)
		{
			int randExtraDrum = rnd.nextInt(10);
			int randBreak = rnd.nextInt(30);
			int randRest = rnd.nextInt(2);

			if(randBreak == 0)
			{
				InsertSimpleBreak(i, track);
				i = 8;
			}
			else
			{
				if(i == 0 || i == 2 || i == 4 || i ==6)
					track.addNoteNew(46, 2, 8);

				if(i == 1 || i == 3 || i == 5 || i == 7)
				{
					if(randRest == 0)
						track.addRest(8);
					else
						track.addNoteNew(36, 6, 8);
				}

				if(i == 0 || i == 4)
					track.addNoteMore(36, 6);
				if(randExtraDrum == 0 || randExtraDrum == 1)
					track.addNoteMore(36, 5);
				if(i == 2 || i == 6)
					track.addNoteMore(38, 5);
			}
		}
	}

	private static void InsertSimpleBreak(int TactBalance, TWPercussionTrack track)
	{
		Random rnd = new Random();

		for(int i = 8; i > TactBalance;)
		{
			int randDrumElement = rnd.nextInt(6);
			int randPause = rnd.nextInt(10);
			int rand16 = rnd.nextInt(4);
			int randTriplet = rnd.nextInt(6);

			if(randPause == 0)
			{
				track.addRest(8);
				i--;
			}
			else
			{
				if(randDrumElement == 0)
				{
					if(rand16 == 0)
					{
						if(randTriplet == 0)
						{
							track.addNoteNew(38, 5, 16);
							track.getLastBeat().getDuration().setTriplet(true);
							track.addNoteNew(38, 5, 16);
							track.getLastBeat().getDuration().setTriplet(true);
							track.addNoteNew(38, 5, 16);
							track.getLastBeat().getDuration().setTriplet(true);
							i--;
						}
						else
						{
							track.addNoteNew(38, 5, 16);
							track.addNoteNew(38, 5, 16);
							i--;
						}
					}
					else
					{
						track.addNoteNew(38, 5, 8);
						i--;
					}
				}
				if(randDrumElement == 1)
				{
					if(rand16 == 0)
					{
						if(randTriplet == 0)
						{
							track.addNoteNew(43, 4, 16);
							track.getLastBeat().getDuration().setTriplet(true);
							track.addNoteNew(43, 4, 16);
							track.getLastBeat().getDuration().setTriplet(true);
							track.addNoteNew(43, 4, 16);
							track.getLastBeat().getDuration().setTriplet(true);
							i--;
						}
						else
						{
							track.addNoteNew(43, 4, 16);
							track.addNoteNew(43, 4, 16);
							i--;
						}
					}
					else
					{
						track.addNoteNew(43, 4, 8);
						i--;
					}
				}
				if(randDrumElement == 2)
				{
					if(rand16 == 0)
					{
						if(randTriplet == 0)
						{
							track.addNoteNew(45, 3, 16);
							track.getLastBeat().getDuration().setTriplet(true);
							track.addNoteNew(45, 3, 16);
							track.getLastBeat().getDuration().setTriplet(true);
							track.addNoteNew(45, 3, 16);
							track.getLastBeat().getDuration().setTriplet(true);
							i--;
						}
						else
						{
							track.addNoteNew(45, 4, 16);
							track.addNoteNew(45, 4, 16);
							i--;
						}
					}
					else
					{
						track.addNoteNew(45, 4, 8);
						i--;
					}
				}
				if(randDrumElement == 3)
				{
					if(rand16 == 0)
					{
						if(randTriplet == 0)
						{
							track.addNoteNew(47, 2, 16);
							track.getLastBeat().getDuration().setTriplet(true);
							track.addNoteNew(47, 2, 16);
							track.getLastBeat().getDuration().setTriplet(true);
							track.addNoteNew(47, 2, 16);
							track.getLastBeat().getDuration().setTriplet(true);
							i--;
						}
						else
						{
							track.addNoteNew(47, 4, 16);
							track.addNoteNew(47, 4, 16);
							i--;	
						}
					}
					else
					{
						track.addNoteNew(47, 4, 8);
						i--;
					}
				}
				if(randDrumElement == 4)
				{
					if(rand16 == 0)
					{
						if(randTriplet == 0)
						{
							track.addNoteNew(41, 4, 16);
							track.getLastBeat().getDuration().setTriplet(true);
							track.addNoteNew(41, 4, 16);
							track.getLastBeat().getDuration().setTriplet(true);
							track.addNoteNew(41, 4, 16);
							track.getLastBeat().getDuration().setTriplet(true);
							i--;
						}
						else
						{
							track.addNoteNew(41, 4, 16);
							track.addNoteNew(41, 4, 16);
							i--;
						}
					}
					else
					{
						track.addNoteNew(41, 4, 8);
						i--;
					}
				}
				if(randDrumElement == 5)
				{
					if(rand16 == 0)
					{
						if(randTriplet == 0)
						{
							track.addNoteNew(36, 6, 16);
							track.getLastBeat().getDuration().setTriplet(true);
							track.addNoteNew(36, 6, 16);
							track.getLastBeat().getDuration().setTriplet(true);
							track.addNoteNew(36, 6, 16);
							track.getLastBeat().getDuration().setTriplet(true);
							i--;
						}
						else
						{
							track.addNoteNew(36, 6, 16);
							track.addNoteNew(36, 6, 16);
							i--;
						}
					}
					else
					{
						track.addNoteNew(36, 6, 8);
						i--;
					}
				}
			}
		}
	}
}
