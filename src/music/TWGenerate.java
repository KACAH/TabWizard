package music;
import gp5.GP5Saver;
import gui.Main;
import gui.NewTrackFrame;
import gui.PlayingMethodsFrame;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import datastruct.TWColor;
import datastruct.TWDataException;
import datastruct.TWInstrumentTrack;
import datastruct.TWInstruments;
import datastruct.TWPercussionTrack;
import datastruct.TWSong;
import datastruct.TWSongPart;
import datastruct.TWStringsTunning;
import datastruct.TWTrackHeader;


public class TWGenerate {

	public static ArrayList<ReadyTrackFragmentForWrite> readyTracks = new ArrayList<ReadyTrackFragmentForWrite>();
	public static ReadyTrackFragmentForWrite readyPercussionTrack = new ReadyTrackFragmentForWrite();

	public static ArrayList<TWSongPart> parts = new ArrayList<TWSongPart>();

	public static TWPercussionTrack PercTrack;

	public static TWSongPart newPart;

	private static Random rn = new Random();

	static public TWHarmony Harmony;
	static public int MeasureCount;



	static public void createParamSongPart(String name) throws TWDataException, FileNotFoundException, IOException
	{
		TWScaleManager.loadScales("data/Scales.twd");

		newPart = NewTrackFrame.song.createSongPart(name);

		//Add all tracks to new song part
		for(int i = 0; i < readyTracks.size(); i++)	
			readyTracks.get(i).setInstrumentTrack(newPart.getInstrumentTrack(i));

		readyPercussionTrack.setPercussionTrack(newPart.getPercussionTrack());

		//Writing on track considering measure count
		for(int i = 0; i < MeasureCount; i++)
		{
			int randChord = rn.nextInt(6);
			for(int j = 0; j < readyTracks.size(); j++)
			{
				boolean RiffIsWritten = false;
				
				//Writes methods if fragment type is harmonic
				if(Main.fragmenttypeframe.isHarmonyFragment())
				{
					writeHarmonyTrack(readyTracks.get(j).getTrack(), readyTracks.get(j).getMethod(), Harmony, randChord);
					writeBassTrack(readyTracks.get(j).getTrack(), readyTracks.get(j).getMethod(), Harmony, randChord);
				}
				//Writes methods if fragment type is riff
				if(Main.fragmenttypeframe.isRiffFragment())
				{
					for(int k = 0; k < readyTracks.size(); k++)	
						if(readyTracks.get(k).getTuning().getNumStringsUsed() == 4)
						{					
							for(int l = 0; l < readyTracks.size(); l++)						
								if(!readyTracks.get(l).getMethod().equals(""))
								{
									//Writes riff on normal and bass track
									writeRiffTrack(readyTracks.get(l).getTrack(), 
											readyTracks.get(k).getTrack(), 
											readyTracks.get(l).getMethod(), 
											PlayingMethodsFrame.getSelectedScale());
									RiffIsWritten = true;
									break;
								}
							break;
						}
				}
				if(RiffIsWritten)
					break;
			}
			//Writes drums
			writeDrumTrack(readyPercussionTrack.getPercussionTrack(), readyPercussionTrack.getMethod());
		}

		NewTrackFrame.song.addSongPart(newPart, 0);

		GP5Saver writer = new GP5Saver();
		writer.saveSong(NewTrackFrame.song, "Fragment.gp5");
		Desktop.getDesktop().open(new File("Fragment.gp5"));
	}


	static public void GenerateNewParamSong(String name, ArrayList<TWSongPart> parts) throws TWDataException, FileNotFoundException, IOException
	{		
		for(int i = 0; i < NewTrackFrame.song.getParts().size(); i++)
			NewTrackFrame.song.removeSongPart(i);

		for(int i = 0; i < parts.size(); i++)
			NewTrackFrame.song.addSongPart(parts.get(i), i);

		GP5Saver writer = new GP5Saver();
		writer.saveSong(NewTrackFrame.song, name + ".gp5");
		Desktop.getDesktop().open(new File(name + ".gp5"));
	}

	static public void Generate(int Tempo, String scale) throws TWDataException, FileNotFoundException, IOException
	{	
		Random rnd = new Random();
		int rndTrackCount = rnd.nextInt(5)+3;

		ArrayList<ReadyTrackFragmentForWrite> readyTracks = new ArrayList<ReadyTrackFragmentForWrite>();

		TWSong Song = new TWSong(Tempo);
		ArrayList<TWSongPart> SongParts = new ArrayList<TWSongPart>();

		///////////////////
		// Tracks part
		//////////////////
		
		//Header creating. Bass track is last.
		for(int i = 0; i < rndTrackCount; i++)
		{
			int rndVolume = (rnd.nextInt(6)+10)*8-1;
			int rndBalance = rnd.nextInt(128);

			int rndR = rnd.nextInt(255);
			int rndG = rnd.nextInt(255);
			int rndB = rnd.nextInt(255);

			int rndInstr = rnd.nextInt(127);

			if(i == rndTrackCount-1)
			{
				TWTrackHeader BassHeader = new TWTrackHeader("Bass", (short)rndVolume, 
						(short)rndBalance, new TWColor(rndR,rndG,rndB), 
						new TWStringsTunning(TWStringsTunning.BASS), (short)rndInstr);

				ReadyTrackFragmentForWrite newTr = new ReadyTrackFragmentForWrite();
				newTr.setTrackHeader(BassHeader);
				readyTracks.add(newTr);
				Song.addTrack(BassHeader);
				break;
			}

			TWTrackHeader header = new TWTrackHeader("track" + i, (short)rndVolume, (short)rndBalance, new TWColor(rndR,rndG,rndB), new TWStringsTunning(TWStringsTunning.STANDARD), (short)rndInstr);

			ReadyTrackFragmentForWrite newTr = new ReadyTrackFragmentForWrite();
			newTr.setTrackHeader(header);

			readyTracks.add(newTr);

			Song.addTrack(readyTracks.get(i).getHeader());
		}

		//Creating drum header
		TWTrackHeader header = new TWTrackHeader("Drums", TWInstruments.DRUMS);
		ReadyTrackFragmentForWrite PercTrack = new ReadyTrackFragmentForWrite();
		PercTrack.setTrackHeader(header);

		Song.addTrack(PercTrack.getHeader());

		TWSongPart newPart = Song.createSongPart("part");

		for(int i = 0; i < readyTracks.size(); i++)	
			readyTracks.get(i).setInstrumentTrack(newPart.getInstrumentTrack(i));

		TWPercussionTrack p = newPart.getPercussionTrack();
		PercTrack.setPercussionTrack(p);

		///////////////////
		// Generate part
		//////////////////
		
		int rndPartCount = rnd.nextInt(9)+3;

		for(int i = 0; i < rndPartCount; i++)
		{
			TWHarmony newHar = TWHarmonyGenerator.generateSimpleHarmony();
			int rndHarmonyOrRiff = rnd.nextInt(2);
			int[] MeasureCountVar = {2, 4, 8, 16};
			int rndMeasureCount = rnd.nextInt(MeasureCountVar.length);

			int rndBassMethod = rnd.nextInt(PlayingMethodsFrame.BassMethods.length);
			int rndRiffMethod = rnd.nextInt(PlayingMethodsFrame.RiffMethods.length);
			int rndDrumMethod = rnd.nextInt(PlayingMethodsFrame.DrumMethods.length);
		
			
			//Set methods to harmony and bass fragments
			for(int k = 0; k < readyTracks.size(); k++)
			{
				int rndHarmonyMethod = rnd.nextInt(PlayingMethodsFrame.HarmonyMethods.length);

				if(!readyTracks.get(k).getHeaderName().equals("Bass"))
					readyTracks.get(k).setMethod(PlayingMethodsFrame.HarmonyMethods[rndHarmonyMethod]);
			}

			for(int j = 0; j < MeasureCountVar[rndMeasureCount]; j++)
			{
				int rndChord = rnd.nextInt(6);
				
				//Writes harmony or riff. Probability 1/2
				if(rndHarmonyOrRiff == 0)
				{
					//Writes harmony and bass tracks
					for(int k = 0; k < readyTracks.size(); k++)
					{
						if(!readyTracks.get(k).getHeaderName().equals("Bass"))
							writeHarmonyTrack(readyTracks.get(k).getTrack(), readyTracks.get(k).getMethod(), newHar, rndChord);
						else
							writeBassTrack(readyTracks.get(k).getTrack(), PlayingMethodsFrame.BassMethods[rndBassMethod], newHar, rndChord);
					}
				}
				else
				{
					boolean RiffIsWritten = false;
					for(int k = 0; k < readyTracks.size(); k++)
					{
						//Set methods to riff and bass fragments
						if(!RiffIsWritten)
						{
							readyTracks.get(k).setMethod(PlayingMethodsFrame.RiffMethods[rndRiffMethod]);
							for(int l = 0; l < readyTracks.size(); l++)	
							{
								//Writes riff and bass tracks
								if(readyTracks.get(l).getTuning().getNumStringsUsed() == 4)
								{
									writeRiffTrack(readyTracks.get(k).getTrack(), readyTracks.get(l).getTrack(), readyTracks.get(k).getMethod(), scale);
									RiffIsWritten = true;
									break;
								}
							}
						}
					}
				}
				//Writes drums
				writeDrumTrack(PercTrack.getPercussionTrack(), PlayingMethodsFrame.DrumMethods[rndDrumMethod]);
			}
			SongParts.add(newPart);
		}
		GenerateSuggSong(Song, "Song", SongParts);
	}


	private static void GenerateSuggSong(TWSong song, String name, ArrayList<TWSongPart> parts) throws TWDataException, FileNotFoundException, IOException
	{	
		for (int i = 0; i < song.getParts().size(); i++) 
			song.removeSongPart(i);

		for(int i = 0; i < parts.size(); i++)
			song.addSongPart(parts.get(i), i);

		GP5Saver writer = new GP5Saver();
		writer.saveSong(song, name + ".gp5");
		Desktop.getDesktop().open(new File(name + ".gp5"));
	}

	/**
	 * Writes Harmony methods on track
	 * @param track track on which we write a method
	 * @param method Method name
	 * @param Harmony harmony that used to write methods on track
	 * @param randChord, random integer, that choose chord from harmony
	 * @throws TWDataException
	 */
	private static void writeHarmonyTrack(TWInstrumentTrack track, String method, TWHarmony Harmony, int randChord) throws TWDataException
	{
		if(method.equals("Acoustic Arpeggio"))
			TWGuitar.AcousticArpeggio(Harmony.getChord(randChord), track);
		if(method.equals("Sound Wall"))
			TWGuitar.writeSoundWall(Harmony.getChord(randChord), track);
		if(method.equals("Pause Sound Wall"))
			TWGuitar.writePauseSoundWall(Harmony.getChord(randChord), track);
		if(method.equals("Hard Sound Wall"))
			TWGuitar.writeHardSoundWall(Harmony.getChord(randChord), track);
		if(method.equals("Harmony"))
			TWHarmonyManager.writeHarmony(Harmony.getChord(randChord), track);
		if(method.equals("Simple Melody"))
			TWMelody.WriteSimpleMelody(Harmony, Harmony.getChord(randChord), track);		
	}

	/**
	 * Writes riff methods on track
	 * @param track track on which we write an method
	 * @param bassTrack bass track on which we write a method
	 * @param method Method name
	 * @param scale Scale that uses in Riff methods
	 * @throws TWDataException
	 */
	private static void writeRiffTrack(TWInstrumentTrack track, TWInstrumentTrack bassTrack, String method, String scale) throws TWDataException
	{
		if(method.equals("Simple Riff"))
			TWRiffs.writeSimpleRiff(TWScaleManager.getScaleByName(scale), track, bassTrack);
		if(method.equals("Simple Power Riff"))
			TWRiffs.writeSimplePowerRiff(TWScaleManager.getScaleByName(scale), track, bassTrack);
		if(method.equals("Power Riff"))
			TWRiffs.writePowerRiff(TWScaleManager.getScaleByName(scale), track, bassTrack);
	}

	/**
	 * Writes bass methods on track
	 * @param bassTrack bass track on which we write a method
	 * @param method Method name
	 * @param Harmony harmony that used to write methods on track
	 * @param randChord, random integer, that choose chord from harmony
	 * @throws TWDataException
	 */
	private static void writeBassTrack(TWInstrumentTrack bassTrack, String method, TWHarmony Harmony, int randChord) throws TWDataException
	{
		if(method.equals("Simple Bass Line"))
			TWBassLine.WriteSimpleBassLine(Harmony.getChord(randChord), bassTrack);
		if(method.equals("Pause Bass Line"))
			TWBassLine.WritePauseBassLine(Harmony.getChord(randChord), bassTrack);
		if(method.equals("Bass Line"))
			TWBassLine.WriteBassLine(Harmony.getChord(randChord), Harmony, bassTrack);
	}

	/**
	 * Writes Drum methods on track
	 * @param track Percussion track on which we write a method
	 * @param method Method name
	 * @throws TWDataException
	 */
	private static void writeDrumTrack(TWPercussionTrack track, String method) throws TWDataException
	{
		if(method.equals("Simple Drums (slow)"))
			TWDrums.WriteSimpleDrums_1_5(track);
		if(method.equals("Simple Drums (middle)"))
			TWDrums.WriteSimpleDrums_1_3(track);
		if(method.equals("Simple Drums (fast)"))
			TWDrums.WriteSimpleDrums_1_2(track);
		if(method.equals("Double Hat Drums (slow)"))
			TWDrums.WriteDoubleHatDrums_1_5(track);
		if(method.equals("Double Hat Drums (middle)"))
			TWDrums.WriteDoubleHatDrums_1_3(track);
		if(method.equals("Double Bass Drums (middle)"))
			TWDrums.WriteDoubleBassDrums_1_3(track);
		if(method.equals("Double Bass Drums (fast)"))
			TWDrums.WriteDoubleBassDrums_1_2(track);
		if(method.equals("Open Hat Drums (slow)"))
			TWDrums.WriteOpenHatDrums_1_5(track);
		if(method.equals("Open Hat Drums (middle)"))
			TWDrums.WriteOpenHatDrums_1_3(track);
	}
}
