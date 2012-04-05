package gui;
import gp5.GP5Saver;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import music.TWBassLine;
import music.TWDrums;
import music.TWGuitar;
import music.TWHarmony;
import music.TWHarmonyGenerator;
import music.TWHarmonyManager;
import music.TWMelody;
import music.TWRiffs;
import music.TWScaleManager;

import datastruct.TWColor;
import datastruct.TWDataException;
import datastruct.TWInstrumentTrack;
import datastruct.TWInstruments;
import datastruct.TWPercussionTrack;
import datastruct.TWSong;
import datastruct.TWSongPart;
import datastruct.TWStringsTunning;
import datastruct.TWTrackHeader;

public class Main
{
	static public MainFrame mainframe = new MainFrame();
	static public FrameCreateScale createscale = new FrameCreateScale();
	static public SomeSuggestionFrame somesuggestionframe = new SomeSuggestionFrame();
	static public NewTrackFrame newtrackframe;
	static public FragmentTypeFrame fragmenttypeframe = new FragmentTypeFrame();
	static public FrameScaleManager scalemanager;
	//static public FragmentList fragmentlist = new FragmentList();
	
	
	public static void main(String[] args) throws IOException
	{
		newtrackframe = new NewTrackFrame();
		scalemanager = new FrameScaleManager();
		
		TWScaleManager.loadScales("data//Scales.twd");
			
	}
		
	public static void Generate() throws IOException 
	{
		try 
		{				
			TWSong song = new TWSong(150);

			song.addTrack( new TWTrackHeader( "Harmony", (short)80, (short)10, new TWColor(12,123,32), new TWStringsTunning(TWStringsTunning.STANDARD), (short)48));
			song.addTrack( new TWTrackHeader( "Lead", TWInstruments.DISTORTION_GUITAR) );
			song.addTrack( new TWTrackHeader( "Bass", TWInstruments.BASS_GUITAR) );
			song.addTrack( new TWTrackHeader( "AcousticGuitar", (short) 25) );
			song.addTrack( new TWTrackHeader( "DistortionGuitar", TWInstruments.DISTORTION_GUITAR) );
			song.addTrack( new TWTrackHeader( "Drums", TWInstruments.DRUMS) );
			
			
			TWSongPart part = song.createSongPart("Test");
			
			TWInstrumentTrack track1 = part.getInstrumentTrack(0);
			TWInstrumentTrack track2 = part.getInstrumentTrack(1);
			TWInstrumentTrack track3 = part.getInstrumentTrack(2);
			TWInstrumentTrack track4 = part.getInstrumentTrack(3);
			TWInstrumentTrack track5 = part.getInstrumentTrack(4);
			TWPercussionTrack track6 = part.getPercussionTrack();	
			


			
			Random rn = new Random();

			TWHarmony newSimpleHarmony = TWHarmonyGenerator.generateSimpleHarmony();

			for(int i = 0; i < 8; i++)
			{
				int randChord = rn.nextInt(6);
				TWHarmonyManager.writeHarmony(newSimpleHarmony.getChord(randChord), track1);
				TWGuitar.AcousticArpeggio(newSimpleHarmony.getChord(randChord), track4);
				TWBassLine.WriteBassLine(newSimpleHarmony.getChord(randChord), newSimpleHarmony, track3);	
				TWDrums.WriteSimpleDrums_1_5(track6);
				TWMelody.WriteSimpleMelody(newSimpleHarmony, newSimpleHarmony.getChord(randChord), track2);
				//TWGuitar.writePauseSoundWall(newSimpleHarmony.getChord(randChord), track5);
			}
			
			
			TWSongPart part2 = song.createSongPart("Test2");
			track3 = part2.getInstrumentTrack(2);
			track5 = part2.getInstrumentTrack(4);
			track6 = part2.getPercussionTrack();
			


			for(int i = 0; i < 4; i++)
			{
				TWRiffs.writePowerRiff(TWScaleManager.getScaleByName("Blues Minor Scale"), track5, track3);
				TWDrums.WriteOpenHatDrums_1_5(track6);
			}
			
			
			song.addSongPart(part, 0);
			song.addSongPart(part2, 1);
			//song.addSongPart(part, 2);

			GP5Saver writer = new GP5Saver();
			writer.saveSong(song, "Test.gp5");
		}		
		catch (TWDataException e)
		{
			System.err.println(e.getMessage());
		} catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
		Desktop.getDesktop().open(new File("Test.gp5"));
	}
}