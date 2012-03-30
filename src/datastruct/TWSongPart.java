package datastruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class TWSongPart {
	private String name;
	
	private List<TWMeasureHeader> measureHeaders;
	
	private List<TWInstrumentTrack> instrumentTracks;
	private TWPercussionTrack percussionTrack;
	
	TWSongPart(String name) {	
		this.name = name;
		
		measureHeaders = new ArrayList<TWMeasureHeader>();
		instrumentTracks = new ArrayList<TWInstrumentTrack>();
		percussionTrack = null;
	}
	
	//Measure Methods
	/**
	 * Add new measure in song part
	 * @param measureHeader settings of new measure
	 */
	public void addMeasure(TWMeasureHeader measureHeader) {
		measureHeaders.add(measureHeader);
		
		Iterator<TWInstrumentTrack> itr = instrumentTracks.iterator(); 
		while( itr.hasNext() ) {
			TWInstrumentTrack track = itr.next();
			track.addMeasure( new TWMeasure(measureHeader, track) );
		}
		if (hasPercussionTrack())
			percussionTrack.addMeasure(new TWMeasure(measureHeader, percussionTrack));
	}
	/**
	 * Get number of measures
	 * @return number of measures
	 */
	public int getMeasuresNum() {
		return measureHeaders.size();
	}
	/**
	 * Get settings of the measure
	 * @param index number of measure
	 * @return measure header (settings)
	 */
	public TWMeasureHeader getMeasureHeader(int index) {
		return measureHeaders.get(index);
	}
	/**
	 * Get all tracks number of the song, including percussion track
	 * @return number of tracks 
	 */
	public int getTracksNum() {
		int res = instrumentTracks.size();
		if (hasPercussionTrack())
			res++;
		return res;
	}
	/**
	 * Get track by index
	 * @param index index of track
	 * @return Instrument track if track with such index exists, and percussion track otherwise
	 */
	public TWTrack getTrack(int index) {
		if (instrumentTracks.size() > index)
			return instrumentTracks.get(index);
		return percussionTrack;
	}
	
	void addTrack(TWTrackHeader header) throws TWDataException {
		if (header.isPercussionTrack())
			addPercussionTrack( new TWPercussionTrack(this, header) ); 
		else
			addInstrumentTrack( new TWInstrumentTrack(this, header) );
			
	}
	//Instrument Tracks Methods
	private void addInstrumentTrack(TWInstrumentTrack track) throws TWDataException {
		if (instrumentTracks.size() >= TWDefaults.MAX_TRACKS_NUM)
			throw new TWDataException("Too many tracks. Max available instrumental tracks number is "+TWDefaults.MAX_TRACKS_NUM);
	
		for (int i = 0; i < getMeasuresNum(); i++)
			track.addMeasure( new TWMeasure(getMeasureHeader(i), track) );
		instrumentTracks.add(track);
	}
	/**
	 * Get number of instrument tracks
	 * @return number of instrument tracks
	 */
	public int getInstrumentTracksNum() {
		return instrumentTracks.size();
	}
	/**
	 * Get instrument track
	 * @param index index of track
	 * @return instrument track
	 */
	public TWInstrumentTrack getInstrumentTrack(int index) {
		return instrumentTracks.get(index);
	}
	
	//Percussion Tracks Methods
	private void addPercussionTrack(TWPercussionTrack track) {
		for (int i = 0; i < getMeasuresNum(); i++)
			percussionTrack.addMeasure( new TWMeasure(getMeasureHeader(i), percussionTrack) );
		percussionTrack = track;
	}
	/**
	 * Return if percussion track exists
	 * @return if percussion track exists
	 */
	public boolean hasPercussionTrack() {
		return percussionTrack != null;
	}
	/**
	 * Get percussion track
	 * @return Percussion track or null
	 */
	public TWPercussionTrack getPercussionTrack() {
		return percussionTrack;
	}
	/**
	 * Get part's name
	 * @return part's name
	 */
	public String getName() { return name; }
}
