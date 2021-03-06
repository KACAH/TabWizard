package music;
import java.util.Random;

import datastruct.TWDataException;
import datastruct.TWPercussionTrack;

/**
 * The TWDrums class. Writes drums to the track
 * 
 */

public class TWDrums {

	/**
	 * Writes a simple drums to the track. Strong beat is 1 and 5
	 * @param track Percussion track on which we write a drums
	 * @throws TWDataException
	 */
	static public void WriteSimpleDrums_1_5(TWPercussionTrack track)throws TWDataException
	{
		Random rnd = new Random();

		for(int i = 0; i < 8; i++)
		{
			int randFill = rnd.nextInt(30);
			int randBD = rnd.nextInt(10); 

			//Writes a drum break on measure tail, or remainder. Probability 1/30
			if(randFill == 0)
			{
				InsertSimpleBreak(i, track);
				i = 8;
			}
			else
			{
				//Writes elements on one beat
				track.addNoteNew(42, 2, 8);
				
				if(i == 0)
					track.addNoteMore(36, 6); // Strong beat
				if(randBD == 0 || randBD == 1)
					track.addNoteMore(36, 5);
				if(i == 4)
					track.addNoteMore(38, 5); // Strong beat
				if(randBD == 3)
					track.addNoteMore(38, 4);
			}
		}
	}

	/**
	 * Writes a simple drums to the track. Strong beat 1 and 3
	 * @param track Percussion track on which we write a drums
	 * @throws TWDataException
	 */
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
					track.addNoteMore(36, 6); // Strong beats
				if(randBD == 0 || randBD == 1)
					track.addNoteMore(36, 5);
				if(i == 2 || i == 6)
					track.addNoteMore(38, 5); // Strong beats
			}
		}
	}

	/**
	 * Writes a simple drums to the track. Strong beat is 1 and 2 
	 * @param track Percussion track on which we write a drums
	 * @throws TWDataException
	 */
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
					if(randBD == 0 || randBD == 1 || randBD == 2 || randBD == 4)
					{ 
						track.addNoteNew(42, 2, 16);

						track.getLastBeat().removeNote(6);
						track.addNoteMore(36, 6);
						track.addNoteNew(36, 6, 16); // add double bass
						doubleBass = true;
					}
				
				if(!doubleBass)
					track.addNoteNew(42, 2, 8);

				if(i == 0 || i == 2 || i == 4 || i == 6) // Strong beat
					track.addNoteMore(36, 6);

				if(i == 1 || i == 3 || i == 5 || i == 7) // Strong beat
					track.addNoteMore(38, 5); 
			}
		}
	}

	/**
	 * Writes a simple drums to the track. Strong beat is 1 and 5
	 * @param track Percussion track on which we write a drums
	 * @throws TWDataException
	 */
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
					track.addNoteMore(36, 6); // Strong beat
				if(randBD == 0 || randBD == 1)
					track.addNoteMore(36, 5);
				if(i == 4)
					track.addNoteMore(38, 5); // Strong beat
				if(randBD == 3)
					track.addNoteMore(38, 4);
				if(i == 0 && randBD == 5)
					track.addNoteMore(49, 1);

				track.addNoteNew(42, 2, 16);
			}
		}
	}	

	/**
	 * Writes a simple drums to the track. Strong beat is 1 and 3
	 * @param track Percussion track on which we write a drums
	 * @throws TWDataException
	 */
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
					track.addNoteMore(36, 6); // Strong beat
				if(randBD == 0 || randBD == 1)
					track.addNoteMore(36, 5);
				if(i == 2 || i == 6)
					track.addNoteMore(38, 5); // Strong beat
				if(i == 0 && randBD == 5)
					track.addNoteMore(49, 1);

				track.addNoteNew(42, 2, 16);
			}
		}
	}

	/**
	 * Writes a simple drums to the track. Strong beat is 1 and 3
	 * @param track Percussion track on which we write a drums
	 * @throws TWDataException
	 */
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
					track.addNoteMore(38, 5); // Strong beat
				if(i == 0 && randBD == 5)
					track.addNoteMore(49, 1);

				track.addNoteNew(36, 6, 16);
			}
		}
	}

	/**
	 * Writes a simple drums to the track. Strong beat is 1 and 2
	 * @param track Percussion track on which we write a drums
	 * @throws TWDataException
	 */
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

				if(i == 1 || i == 3 || i == 5 || i == 7) // Strong beat
					track.addNoteMore(38, 5);
				if(i == 0 && randBD == 5)
					track.addNoteMore(49, 1);

				track.addNoteNew(36, 6, 16);
			}
		}
	}

	/**
	 * Writes a simple drums to the track. Strong beat is 1 and 5
	 * @param track Percussion track on which we write a drums
	 * @throws TWDataException
	 */
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
					track.addNoteMore(36, 6); // Strong beat
				if(i == 4)
					track.addNoteMore(38, 5); // Strong beat

				if(randExtraDrum == 0 || randExtraDrum == 1)
					track.addNoteMore(36, 5);
				if(randExtraDrum == 3)
					track.addNoteMore(38, 4);
			}
		}
	}

	/**
	 * Writes a simple drums to the track. Strong beat is 1 and 3
	 * @param track Percussion track on which we write a drums
	 * @throws TWDataException
	 */
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
					track.addNoteMore(36, 6); // Strong beat
				
				if(randExtraDrum == 0 || randExtraDrum == 1)
					track.addNoteMore(36, 5);
				if(i == 2 || i == 6)
					track.addNoteMore(38, 5); // Strong beat
			}
		}
	}

	/**
	 * Writes a drum break to the track
	 * @param TactBalance a free space in measure
	 * @param track Percussion track on which we write a drums
	 */
	private static void InsertSimpleBreak(int TactBalance, TWPercussionTrack track)
	{
		Random rnd = new Random();

		for(int i = 8; i > TactBalance; i--)
		{
			int randDrumElement = rnd.nextInt(6);
			int randPause = rnd.nextInt(10);
			int rand16 = rnd.nextInt(4);
			int randTriplet = rnd.nextInt(6);

			if(randPause == 0)
				track.addRest(8);
			else
			{
				if(randDrumElement == 0)
					BreakNoteType(rand16, randTriplet, 38, 5, track);
				if(randDrumElement == 1)
					BreakNoteType(rand16, randTriplet, 43, 4, track);
				if(randDrumElement == 2)
					BreakNoteType(rand16, randTriplet, 45, 4, track);
				if(randDrumElement == 3)
					BreakNoteType(rand16, randTriplet, 47, 4, track);
				if(randDrumElement == 4)
					BreakNoteType(rand16, randTriplet, 41, 4, track);
				if(randDrumElement == 5)
					BreakNoteType(rand16, randTriplet, 36, 6, track);			
			}
		}
	}

	/**
	 * Chooses drum break note type
	 * @param rand16 random integer, that reports note duration 
	 * @param randTriplet random integer, that reports is note triplet 
	 * @param drumElement random integer, that choose drum element 
	 * @param string Guitar string that is using to write drum elements
	 * @param track Percussion track on which we write a drums
	 */
	private static void BreakNoteType(int rand16, int randTriplet, int drumElement, int string, TWPercussionTrack track)
	{
		if(rand16 == 0)
			BreakTripletOr16Note(drumElement, string, randTriplet, track);
		else
			track.addNoteNew(drumElement, string, 8);
	}

	/**
	 * Writes a drum break note duration (Triplet or 16th)
	 * @param drumElement random integer, that choose drum element 
	 * @param string Guitar string that is using to write drum elements
	 * @param randTriplet random integer, that reports is note triplet 
	 * @param track Percussion track on which we write a drums
	 */
	private static void BreakTripletOr16Note(int drumElement, int string, int randTriplet, TWPercussionTrack track)
	{
		if(randTriplet == 0)
			addBreakTriplets(drumElement, string, track);
		else
			addBreak16Notes(drumElement, string, track);
	}

	/**
	 * Adds drum break Triplet note 
	 * @param drumElement random integer, that choose drum element 
	 * @param string Guitar string that is using to write drum elements
	 * @param track Percussion track on which we write a drums
	 */
	private static void addBreakTriplets(int drumElement, int string, TWPercussionTrack track)
	{
		for(int i = 0; i < 3; i++)
		{
			track.addNoteNew(drumElement, string, 16);
			track.getLastBeat().getDuration().setTriplet(true);
		}
	}

	/**
	 * Adds drum break 16th note 
	 * @param drumElement random integer, that choose drum element 
	 * @param string Guitar string that is using to write drum elements
	 * @param track Percussion track on which we write a drums
	 */
	private static void addBreak16Notes(int drumElement, int string, TWPercussionTrack track)
	{
		for(int i = 0; i < 2; i++)
			track.addNoteNew(drumElement, string, 16);
	}
}
