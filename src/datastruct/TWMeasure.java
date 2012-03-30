package datastruct;

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * The TWMeasure class represents song's measure data: Beats, Notes and s.o.
 * Objects of this class are created automatically
 * 
 * @author Deniss Paltovs (KACAH)
 */
public class TWMeasure {
	//3 for normal note (1 normal = 3/3 normal)
	private static final int NORMAL_NOTES_MULTIPLIER = 3;
	//2 for 3-tulet (1 tuplet = 2/3 of normal note)
	private static final int THREE_TUPLET_MULTIPLIER = 2;
	//64 for 64-notes for 4/4
	private static final int TICKS_COUNT = 64;
	
	private TWMeasureHeader header;
	private TWTrack track;
	
	private List<TWBeat> beats;
	
	TWMeasure(TWMeasureHeader header, TWTrack track) {
		this.header = header;
		this.track = track;
		
		beats = new ArrayList<TWBeat>();		
	}
	/**
	 * Get measure header
	 * @return measure header
	 */
	public TWMeasureHeader getHeader() { return header; }
	/**
	 * Get track-owner of measure
	 * @return track
	 */
	public TWTrack getTrack() { return track; }
	/**
	 * Add previously created beat to this measure 
	 * @param beat
	 */
	void addBeat(TWBeat beat) {
		beats.add(beat);
	}
	/**
	 * Get beat with given index
	 * @param index number of beat 
	 * @return beat
	 */
	public TWBeat getBeat(int index) {
		return beats.get(index);
	}
	/**
	 * Get number of beats in this measure
	 * @return number of beats
	 */
	public int getNumBeats() {
		return beats.size();
	}
	/**
	 * Get last added beat to this measure
	 * @return last added beat
	 */
	public TWBeat getLastBeat() { 
		if (beats.size() > 0)
			return beats.get(beats.size()-1);
		return null;
	}
	/**
	 * Get max meaure's capacity
	 * @return measure's maximal capacity
	 */
	public int getMaxCapacity() {
		return (TICKS_COUNT*NORMAL_NOTES_MULTIPLIER) 
				/ header.getTimeSignature().getDenominator()
				* header.getTimeSignature().getNumerator();
	}
	/**
	 * Get current measure's free part
	 * @return measure's free part
	 */
	public int getCurrentCapacity() {
		int curCapacity = getMaxCapacity();
		
		//- all beats lenght
		ListIterator<TWBeat> iter = beats.listIterator(); 
		while(iter.hasNext()) {
		    TWBeat curBeat = iter.next(); 
		    
		    int beatMultiplier = 0;
			if (curBeat.getDuration().isTriplet())
				beatMultiplier = THREE_TUPLET_MULTIPLIER;
			else
				beatMultiplier = NORMAL_NOTES_MULTIPLIER;
			
			curCapacity -= (TICKS_COUNT*beatMultiplier)/curBeat.getDuration().getValue();
		} 
		
		return curCapacity; 
	}
	/**
	 * Check if measure if full, or overflowed
	 * @return  if measure if full, or overflowed
	 */
	public boolean isFull() { return getCurrentCapacity() <= 0; }
}
