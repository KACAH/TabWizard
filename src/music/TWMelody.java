package music;
import java.util.Random;

import datastruct.TWChord;
import datastruct.TWInstrumentTrack;
import datastruct.TWNoteEffects;


public class TWMelody {

	static public void WriteSimpleMelody (TWHarmony harmony, TWChord chord, TWInstrumentTrack track)
	{
		TWScale HarmonyScale = TWScaleManager.constructHarmonyScale(harmony);

		Random rn = new Random();
		int duration[] = {1, 2, 4, 8, 16};
		int frets[];

		boolean is16 = false;
		boolean is16afterDot = false;
		int count16 = 0;

		for(int FullBeat = 64; FullBeat > 0;)
		{
			int randMelodyStep = rn.nextInt(5); // Берёт ноту с аккорда или нет
			int randBaseNote = rn.nextInt(3);
			int randScaleNote = rn.nextInt(HarmonyScale.ScaleSize());
			int randPause = rn.nextInt(14);
			int randDuration;

			if(!is16)
				randDuration = rn. nextInt(duration.length);
			else
			{
				randDuration = rn.nextInt(1)+4; 
				randPause = rn.nextInt(8);
			}

			if(!TWHarmonyManager.onceNoteInHarmony(harmony, HarmonyScale.getNote(randScaleNote)))
			{	
				if(randMelodyStep == 0)
				{
					for(int i = 0; i < HarmonyScale.ScaleSize(); i++)
					{
						if(randScaleNote == i && FullBeat >= 0)
						{
							frets = track.getFretsByNoteAndString(HarmonyScale.getNote(i), 4);

							if(randPause == 0 || randPause == 1 || randPause == 2 || randPause == 3 || randPause == 4 || randPause == 5 || randPause == 6 || randPause == 7)
							{
								if(randDuration == 2 && FullBeat >= 16)
								{
									track.addNoteNew(frets[0], 4, duration[randDuration]);
									FullBeat -= 16;
								}
								if(randDuration == 3 && FullBeat >= 8)
								{
									track.addNoteNew(frets[0], 4, duration[randDuration]);
									FullBeat -= 8;
								}
								if(randDuration == 4 && FullBeat >= 4)
								{
									if(is16afterDot)
									{
										track.addNoteNew(frets[0], 4, duration[randDuration]);							
										FullBeat -= 4;
										is16afterDot = false;
									}
									else
									{
										track.addNoteNew(frets[0], 4, duration[randDuration]);							
										FullBeat -= 4;
										is16 = true;
										count16++;

										if(count16 == 2)
										{
											is16 = false;
											count16 = 0;
										}
									}
								}
							}
							if(randPause == 8)
							{
								if(randDuration == 0 && FullBeat >= 64)
								{
									track.addRest(duration[randDuration]);
									FullBeat -= 64;
								}
								if(randDuration == 1 && FullBeat >= 32)
								{
									track.addRest(duration[randDuration]);
									FullBeat -= 32;
								}
								if(randDuration == 2 && FullBeat >= 16)
								{
									track.addRest(duration[randDuration]);
									FullBeat -= 16;
								}
								if(randDuration == 3 && FullBeat >= 8)
								{
									track.addRest(duration[randDuration]);
									FullBeat -= 8;
								}		
							}
							if(randPause == 9 || randPause == 10 || randPause == 11 || randPause == 12)
							{
								if(randDuration == 2 && FullBeat >= 24)
								{
									track.addNoteNew(frets[0], 4, duration[randDuration]);
									track.getLastBeat().setDotted(true);
									FullBeat -= 24;
								}
								if(randDuration == 3 && FullBeat >= 12)
								{
									track.addNoteNew(frets[0], 4, duration[randDuration]);
									track.getLastBeat().setDotted(true);
									FullBeat -= 12;
									is16afterDot = true;
								}
							}
						}
					}
				}
				else
				{
					for(int i = 0; i < HarmonyScale.ScaleSize(); i++)
					{
						if(TWScaleManager.getNotesIndexFromScale(HarmonyScale, chord)[randBaseNote]  == i && FullBeat >= 0)
						{
							frets = track.getFretsByNoteAndString(HarmonyScale.getNote(i), 4);

							if(randPause == 0 || randPause == 1 || randPause == 2 || randPause == 3 || randPause == 4 || randPause == 5 || randPause == 6 || randPause == 7)
							{
								if(randDuration == 0 && FullBeat >= 64)
								{
									track.addNoteNew(frets[0], 4, duration[randDuration]);
									FullBeat -= 64;
									track.getLastNote().setSimpleEffect(TWNoteEffects.VIBRATO, true);
								}
								if(randDuration == 1 && FullBeat >= 32)
								{
									track.addNoteNew(frets[0], 4, duration[randDuration]);
									FullBeat -= 32;
									track.getLastNote().setSimpleEffect(TWNoteEffects.VIBRATO, true);
								}
								if(randDuration == 2 && FullBeat >= 16)
								{
									track.addNoteNew(frets[0], 4, duration[randDuration]);
									FullBeat -= 16;
								}
							}
							if(randPause == 9 || randPause == 10 || randPause == 11 || randPause == 12)
							{							
								if(randDuration == 1 && FullBeat >= 48)
								{
									track.addNoteNew(frets[0], 4, duration[randDuration]);
									track.getLastBeat().setDotted(true);
									track.getLastNote().setSimpleEffect(TWNoteEffects.VIBRATO, true);
									FullBeat -= 48;
								}
								if(randDuration == 2 && FullBeat >= 24)
								{
									track.addNoteNew(frets[0], 4, duration[randDuration]);
									track.getLastBeat().setDotted(true);
									FullBeat -= 24;
								}
								if(randDuration == 3 && FullBeat >= 12)
								{
									track.addNoteNew(frets[0], 4, duration[randDuration]);
									track.getLastBeat().setDotted(true);
									FullBeat -= 12;
									is16afterDot = true;
								}
							}
						}
					}
				}
			}
		}
	}
}
