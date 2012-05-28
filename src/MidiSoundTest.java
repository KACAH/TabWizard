
import java.io.*;
import javax.sound.midi.*;
   
public class MidiSoundTest {
   private static Sequencer midiPlayer;
   
   // testing main method
   public static void main(String[] args) {
      startMidi("C:/Users/Wad/Desktop/movie-12monkeys.mid");     // start the midi player
      try {
         Thread.sleep(10000);   // delay
      } catch (InterruptedException e) { }
      System.out.println("faster");
      midiPlayer.setTempoFactor(2.0F);   // >1 to speed up the tempo
      try {
         Thread.sleep(10000);   // delay
      } catch (InterruptedException e) { }
   
      // Do this on every move step, you could change to another song
      if (!midiPlayer.isRunning()) {  // previous song finished
         // reset midi player and start a new song
         midiPlayer.stop();
         midiPlayer.close();
         startMidi("song2.mid");
      }
   }
   
   public static void startMidi(String midFilename) {
      try {
         File midiFile = new File(midFilename);
         Sequence song = MidiSystem.getSequence(midiFile);
         midiPlayer = MidiSystem.getSequencer();
         midiPlayer.open();
         midiPlayer.setSequence(song);
         midiPlayer.setLoopCount(0); // repeat 0 times (play once)
         midiPlayer.start();
      } catch (MidiUnavailableException e) {
         e.printStackTrace();
      } catch (InvalidMidiDataException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}