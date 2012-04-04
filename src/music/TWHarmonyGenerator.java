package music;
import java.util.Random;

import datastruct.TWChord;
import datastruct.TWChordManager;
import datastruct.TWDataException;


public class TWHarmonyGenerator {

	static TWChord mainChord;

	static public TWChord getMainChord()
	{
		return mainChord;
	}

	public static TWHarmony generateSimpleHarmony()throws TWDataException
	{
		Random rn = new Random();

		int randChord = rn.nextInt(24);
		int randHarmonyVariant = rn.nextInt(2); //MinorOrMajor7Chord

		TWChordManager.loadChords("data//Chords.twd");
		TWChord[] allChords = new TWChord[24];
		
		String myChords[] = {"Am", "A", "A#m", "A#", "Bm", "B", "Cm",
				"C", "C#m", "C#", "Dm",	"D", "D#m", "D#", "Em", "E", "Fm",
				"F", "F#m", "F#", "Gm", "G", "G#m", "G#"};
		
		for (int i = 0; i < myChords.length; i++)
			allChords[i] = TWChordManager.getChordByName(myChords[i]);

		
		TWHarmony simpleHarmony = new TWHarmony();
		for(int i = 0; i < myChords.length; i++)
			simpleHarmony.addChord(TWChordManager.getChordByName(myChords[randChord]));


		boolean MinorScheme = false;
		for (int i = 0; i < myChords.length; i=i+2)
		{
			if(simpleHarmony.getChord(0).getName().equals(myChords[i]))
			{
				MinorScheme = true;
				mainChord = simpleHarmony.getChord(0);
				break;
			}
		}

		if(MinorScheme)
		{
			for(int j = 0; j < myChords.length; j++)
			{
				if(simpleHarmony.getChord(0).getName().equals(myChords[j]))
				{
					if(j + 7 >= 24)
						simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j-17]));
					else
						simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j+7]));

					if(j + 10 >= 24)
						simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j-14]));
					else
						simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j+10]));

					if(randHarmonyVariant == 0)
					{
						if(j + 14 >= 24)
							simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j-10]));
						else
							simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j+15]));
					}
					else
					{
						if(j + 14 >= 24)
							simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j-9]));
						else
							simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j+14]));
					}

					if(j + 17 >= 24)
						simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j-7]));
					else
						simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j+17]));

					if(j + 21 >= 24)
						simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j-3]));
					else
						simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j+21]));
				}
			}
		}

		else
			for(int j = 0; j < myChords.length; j++)
			{
				if(simpleHarmony.getChord(0).getName().equals(myChords[j]))
				{
					if(j - 7 < 0)
					{
						simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j+17]));
						mainChord = TWChordManager.getChordByName(myChords[j+17]);
					}
					else
					{
						simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j-7]));
						mainChord = TWChordManager.getChordByName(myChords[j-7]);
					}					
					if(j + 3 >= 24)
						simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j-21]));
					else
						simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j+3]));


					if(randHarmonyVariant == 0)
					{
						if(j + 8 >= 24)
							simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j-17]));
						else
							simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j+7]));
					}
					else
					{
						if(j + 8 >= 24)
							simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j-16]));
						else
							simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j+8]));
					}



					if(j + 10 >= 24)
						simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j-14]));
					else
						simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j+10]));

					if(j + 14 >= 24)
						simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j-10]));
					else
						simpleHarmony.addChord(TWChordManager.getChordByName(myChords[j+14]));
				}
			}
		return simpleHarmony;
	}

	
	
/*
	static public TWChord[] generateEastHarmony(int randVal)throws TWDataException
	{
		TWChordManager.loadChords("Chords.twd");
		TWChord[] allChords;
		allChords = new TWChord[24];
		String myChords[] = {"Am", "A", "A#m", "A#", "Bm", "B", "Cm",
				"C", "C#m", "C#", "Dm",	"D", "D#m", "D#", "Em", "E", "Fm",
				"F", "F#m", "F#", "Gm", "G", "G#m", "G#"};
		for (int i = 0; i < myChords.length; i++)
		{
			allChords[i] = TWChordManager.getChordByName(myChords[i]);
		}

		TWChord[] eastHarmony;
		eastHarmony = new TWChord[3];

		for(int i = 0; i < myChords.length; i++)
		{
			eastHarmony[0] = TWChordManager.getChordByName(myChords[randVal-1]);
		}

		boolean MinorScheme = false;
		for (int i = 0; i < myChords.length; i=i+2)
		{
			if(eastHarmony[0].getName().equals(myChords[i]))
			{
				MinorScheme = true;
				break;
			}
		}
		if(MinorScheme)
		{
			for(int j = 0; j < myChords.length; j++)
			{
				if(eastHarmony[0].getName().equals(myChords[j]))
				{
					if(j + 5 >= 24)
						eastHarmony[1] = TWChordManager.getChordByName(myChords[j-19]);
					else
						eastHarmony[1] = TWChordManager.getChordByName(myChords[j+5]);

					if(j + 7 >= 24)
						eastHarmony[2] = TWChordManager.getChordByName(myChords[j-17]);
					else
						eastHarmony[2] = TWChordManager.getChordByName(myChords[j+7]);									
				}
			}
		}

		else
			for(int j = 0; j < myChords.length; j++)
			{
				if(eastHarmony[0].getName().equals(myChords[j]))
				{
					if(j - 5 < 0)
						eastHarmony[1] = TWChordManager.getChordByName(myChords[j+19]);
					else
						eastHarmony[1] = TWChordManager.getChordByName(myChords[j-5]);

					if(j + 2 >= 24)
						eastHarmony[2] = TWChordManager.getChordByName(myChords[j-21]);
					else
						eastHarmony[2] = TWChordManager.getChordByName(myChords[j+2]);						

				}
			}
		return eastHarmony;
	}
	*/
}
