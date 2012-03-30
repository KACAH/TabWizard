package music;
import gp5.GP5Saver;
import gui.NewTrackFrame;
import gui.PlayingMethodsFrame;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import datastruct.TWDataException;
import datastruct.TWInstrumentTrack;
import datastruct.TWPercussionTrack;
import datastruct.TWSongPart;
import datastruct.TWTrackHeader;


public class TWGenerate {

	public static ArrayList<ReadyTrackFragmentForWrite> readyTracks = new ArrayList<ReadyTrackFragmentForWrite>();
	public static ReadyTrackFragmentForWrite readyPercussionTrack = new ReadyTrackFragmentForWrite();

	public static ArrayList<TWSongPart> parts = new ArrayList<TWSongPart>();

	public static TWPercussionTrack PercTrack;

	private static Random rn = new Random();
	private static int randChord = rn.nextInt(6);

	static public TWHarmony Harmony;
	static public int MeasureCount;

	static public void createSongPart(String name) throws TWDataException, FileNotFoundException, IOException
	{
		TWScaleManager.loadScales("data/Scales.twd");

		TWSongPart newPart = NewTrackFrame.song.createSongPart(name);


		for(int i = 0; i < readyTracks.size(); i++)	
			readyTracks.get(i).setInstrumentTrack(newPart.getInstrumentTrack(i));

		readyPercussionTrack.setPercussionTrack(newPart.getPercussionTrack());


		for(int i = 0; i < MeasureCount; i++)
		{
			randChord = rn.nextInt(6);
			for(int j = 0; j < readyTracks.size(); j++)
			{
				writeHarmonyTrack(readyTracks.get(j).getTrack(), readyTracks.get(j).getMethod());
				//writeRiffTrack(readyTracks.get(j).getTrack(), readyTracks.get(j).getMethod());
				writeBassTrack(readyTracks.get(j).getTrack(), readyTracks.get(j).getMethod());
			}
			writeDrumTrack(readyPercussionTrack.getPercussionTrack(), readyPercussionTrack.getMethod());
		}

		NewTrackFrame.song.addSongPart(newPart, 0);

		GP5Saver writer = new GP5Saver();
		writer.saveSong(NewTrackFrame.song, "Test.gp5");
		Desktop.getDesktop().open(new File("Test.gp5"));

	}


	static public void GenerateNewSong() throws TWDataException
	{

	}

	private static void writeHarmonyTrack(TWInstrumentTrack track, String method) throws TWDataException
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

	private static void writeRiffTrack(TWInstrumentTrack track, TWInstrumentTrack bassTrack, String method) throws TWDataException
	{
		if(method.equals("Simple Riff"))
			TWRiffs.writeSimpleRiff(TWScaleManager.getScaleByName(PlayingMethodsFrame.getSelectedScale()), track, bassTrack);
		if(method.equals("Simple Power Riff"))
			TWRiffs.writeSimplePowerRiff(TWScaleManager.getScaleByName(PlayingMethodsFrame.getSelectedScale()), track, bassTrack);
		if(method.equals("Power Riff"))
			TWRiffs.writePowerRiff(TWScaleManager.getScaleByName(PlayingMethodsFrame.getSelectedScale()), track, bassTrack);
	}

	private static void writeBassTrack(TWInstrumentTrack bassTrack, String method) throws TWDataException
	{
		if(method.equals("Simple Bass Line"))
			TWBassLine.WriteSimpleBassLine(Harmony.getChord(randChord), bassTrack);
		if(method.equals("Pause Bass Line"))
			TWBassLine.WritePauseBassLine(Harmony.getChord(randChord), bassTrack);
		if(method.equals("Bass Line"))
			TWBassLine.WriteBassLine(Harmony.getChord(randChord), Harmony, bassTrack);
	}

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
