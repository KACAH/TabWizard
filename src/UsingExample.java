import gp5.GP5Saver;
import datastruct.*;

import java.awt.Desktop;
import java.io.*;
import java.util.List;

public class UsingExample
{
	public static void main(String[] args)
	{
		try 
		{
			TWChordManager.loadChords("data//Chords.twd");
			
			TWChord chord = TWChordManager.getChordByName("Am");
			System.out.println(chord.getName());
			
			List<TWChord> res = TWChordManager.getChordsByNotes("E", "B");
			for (int i = 0; i < res.size(); i++)
				System.out.println(res.get(i).getName());
			
			TWSong song = new TWSong(100);	
			//song.addTrack( new TWTrackHeader( "Rithm", TWDefaults.VOLUME, TWDefaults.BALANCE, 
			//		new TWColor(12,123,32), new TWStringsTunning(TWStringsTunning.STANDARD), TWInstruments.DISTORTION_GUITAR) );
			song.addTrack( new TWTrackHeader( "Rithm", TWInstruments.DISTORTION_GUITAR) );
			song.addTrack( new TWTrackHeader( "Lead", TWInstruments.DISTORTION_GUITAR) );
			song.addTrack( new TWTrackHeader( "Bass", TWInstruments.BASS_GUITAR) );
			song.addTrack( new TWTrackHeader( "Drums", TWInstruments.DRUMS) );
			
			TWSongPart part = song.createSongPart("Test");
			TWInstrumentTrack track1 = part.getInstrumentTrack(0);
			
			int[] frets = track1.getFretsByNoteAndString(TWSimpleNote.F, 1); 
			for (int i = 0; i < frets.length; i++)
				System.out.println(frets[i]);
			
			track1.addNoteNew(4, 1, 8);
			track1.addNoteMore(4, 2);
			track1.addNoteMore(3, 3);
			track1.addNoteMore(3, 4);
			track1.getLastBeat().addStroke( new TWStroke(TWStroke.UP, new TWDuration(TWDuration.SIXTEENTH)) );
			track1.getLastBeat().setDotted(true);
			track1.addRest(8);
			track1.addNoteNew(0, 3, 4);
			
			track1.addNoteNew(0, 3, 4);
			track1.getLastNote().setSimpleEffect(TWNoteEffects.LETRING, true);
			
			track1.addNoteNew(0, 1, 4);
			track1.getLastBeat().setFadeIn(true);
			
			track1.addNoteNew(0, 1, 4);
			TWNoteEffects.TWBendEffect bend = new TWNoteEffects.TWBendEffect(TWNoteEffects.TWBendEffect.BEND_RELEASE_BEND_FULL);
			track1.getLastNote().getEffects().addBendEffect(bend);
			
			TWSongPart part2 = song.createSongPart("Test2");
			track1 = part2.getInstrumentTrack(0);
			part2.addMeasure(new TWMeasureHeader());
			for (int i = 1; i <= 12; i++)
			{
				track1.addNoteNew(0, 1, 4);
				track1.getLastBeat().getDuration().setTriplet(true);
			}
	
			song.addSongPart(part2, 0);
			song.addSongPart(part, 1);
			song.addSongPart(part2, 2);
			
			GP5Saver writer = new GP5Saver();
			writer.saveSong(song, "Test.gp5");
			
			Desktop.getDesktop().open(new File("Test.gp5"));
		} catch (TWDataException e)
		{
			System.err.println(e.getMessage());
		} catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
	}
}
