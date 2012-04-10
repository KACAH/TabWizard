package music;
import java.util.Random;

import datastruct.TWChord;
import datastruct.TWChordManager;
import datastruct.TWDataException;


public class TWHarmonyGenerator {

	static TWChord mainChord;

	/**
	 * Get main chord from harmony
	 * @return main chord from harmony
	 */
	static public TWChord getMainChord(){
		return mainChord;
	}

	/**
	 * Generates a simple harmony, using simple chords
	 * @return new harmony
	 * @throws TWDataException
	 */
	public static TWHarmony generateSimpleHarmony()throws TWDataException
	{
		Random rn = new Random();

		int randChord = rn.nextInt(24);
		int randHarmonyVariant = rn.nextInt(2); //Minor Or Major Chord

		TWChordManager.loadChords("data//Chords.twd");
		TWChord[] allChords = new TWChord[24];

		String myChords[] = {"Am", "A", "A#m", "A#", "Bm", "B", "Cm",
				"C", "C#m", "C#", "Dm",	"D", "D#m", "D#", "Em", "E", "Fm",
				"F", "F#m", "F#", "Gm", "G", "G#m", "G#"};

		for (int i = 0; i < myChords.length; i++)
			allChords[i] = TWChordManager.getChordByName(myChords[i]);


		TWHarmony simpleHarmony = new TWHarmony();
		FillHarmonyWithChord(simpleHarmony, myChords, randChord);


		boolean MinorScheme = false;
		MinorScheme = isMinorScheme(myChords, simpleHarmony, MinorScheme);
		
		
		if(MinorScheme)
			setHarmonyByMinorChord(myChords, simpleHarmony, randHarmonyVariant);
		else
			setHarmonyByMajorChord(myChords, simpleHarmony, randHarmonyVariant);

		return simpleHarmony;
	}
	
	/**
	 * Checks created harmony for minor key
	 * @param myChords array with chord names
	 * @param simpleHarmony created new harmony
	 * @param MinorScheme flag, that specifies selection of chords
	 * @return flag
	 * @throws TWDataException
	 */
	private static boolean isMinorScheme(String[] myChords, TWHarmony simpleHarmony, boolean MinorScheme)
	{
		for (int i = 0; i < myChords.length; i=i+2)
		{
			if(simpleHarmony.getChord(0).getName().equals(myChords[i]))
			{
				MinorScheme = true;
				mainChord = simpleHarmony.getChord(0);
				return MinorScheme;
			}
		}
		return MinorScheme;
	}

	/**
	 * Add chords if MinorScheme was minor
	 * @param myChords array with chord names
	 * @param simpleHarmony created new harmony
	 * @param randHarmonyVariant random integer, that make a little change in harmony 
	 */
	private static void setHarmonyByMinorChord(String[] myChords, TWHarmony simpleHarmony, int randHarmonyVariant)
	{
		for(int j = 0; j < myChords.length; j++)
		{
			if(simpleHarmony.getChord(0).getName().equals(myChords[j]))
			{
				addChordToHarmony(j, 7, simpleHarmony, myChords, -17, 7);
				addChordToHarmony(j, 10, simpleHarmony, myChords, -14, 10);

				if(randHarmonyVariant == 0)
					addChordToHarmony(j, 14, simpleHarmony, myChords, -10, 15);
				else
					addChordToHarmony(j, 14, simpleHarmony, myChords, -9, 14);

				addChordToHarmony(j, 17, simpleHarmony, myChords, -7, 17);
				addChordToHarmony(j, 21, simpleHarmony, myChords, -3, 21);
			}
		}
	}

	/**
	 * Add chords if MinorScheme was major
	 * @param myChords array with chord names
	 * @param simpleHarmony created new harmony
	 * @param randHarmonyVariant random integer, that make a little change in harmony 
	 */
	private static void setHarmonyByMajorChord(String[] myChords, TWHarmony simpleHarmony, int randHarmonyVariant)
	{
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
				
				addChordToHarmony(j, 3, simpleHarmony, myChords, -21, 3);

				if(randHarmonyVariant == 0)
					addChordToHarmony(j, 8, simpleHarmony, myChords, -17, 7);
				else
					addChordToHarmony(j, 8, simpleHarmony, myChords, -16, 8);

				addChordToHarmony(j, 10, simpleHarmony, myChords, -14, 10);
				addChordToHarmony(j, 14, simpleHarmony, myChords, -10, 14);
			}
		}
	}
	/**
	 * Add chords to harmony
	 * @param curPosition current position in array
	 * @param reqChordPosition required chord position in array 
	 * @param myChords array with chord names
	 * @param simpleHarmony created new harmony
	 * @param shift1 required chord position in array with shift
	 * @param shift2 required chord position in array with shift
	 * @param randHarmonyVariant random integer, that make a little change in harmony 
	 */
	private static void addChordToHarmony(int curPosition, int reqChordPosition, TWHarmony simpleHarmony, String[] myChords, int shift1, int shift2)
	{
		if(curPosition + reqChordPosition >= 24)
			simpleHarmony.addChord(TWChordManager.getChordByName(myChords[curPosition + shift1]));
		else
			simpleHarmony.addChord(TWChordManager.getChordByName(myChords[curPosition + shift2]));
	}

	/**
	 * Add chords to harmony
	 * @param simpleHarmony created new harmony
	 * @param myChords array with chord names
	 * @param randChord random integer, that choose first chord from chord array
	 */
	private static void FillHarmonyWithChord(TWHarmony simpleHarmony, String[] myChords, int randChord)
	{
		for(int i = 0; i < myChords.length; i++)
			simpleHarmony.addChord(TWChordManager.getChordByName(myChords[randChord]));
	}

}
