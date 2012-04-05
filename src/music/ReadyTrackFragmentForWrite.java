package music;
import datastruct.TWInstrumentTrack;
import datastruct.TWPercussionTrack;
import datastruct.TWStringsTunning;
import datastruct.TWTrackHeader;

/**
 * The ReadyTrackFragmentForWrite class. Contains tracks, headers and methods
 * 
 */

public class ReadyTrackFragmentForWrite {

	private TWInstrumentTrack track;
	private TWPercussionTrack percTrack;
	private TWTrackHeader header;
	private String method;
	
	/**
	 * Sets instrument track
	 * @param t instrument track
	 */
	public void setInstrumentTrack(TWInstrumentTrack t){
		track = t;
	}
	
	/**
	 * Sets percussion track
	 * @param t percussion track
	 */
	public void setPercussionTrack(TWPercussionTrack t){
		percTrack = t;
	}
	
	/**
	 * Sets track header
	 * @param h header
	 */
	public void setTrackHeader(TWTrackHeader h){
		header = h;
	}
	
	/**
	 * Sets track playing method
	 * @param m playing method name
	 */
	public void setMethod(String m){
		method = m;
	}
	
	/**
	 * Get instrument track
	 * @return instrument track
	 */
	public TWInstrumentTrack getTrack(){
		return track;
	}
	
	/**
	 * Get Percussion track
	 * @return Percussion track
	 */
	public TWPercussionTrack getPercussionTrack(){
		return percTrack;
	}
	
	/**
	 * Get track header
	 * @return track header
	 */
	public TWTrackHeader getHeader(){
		return header;
	}
	
	/**
	 * Get track playing method
	 * @return playing method
	 */
	public String getMethod(){
		return method;
	}
	
	/**
	 * Get track header name
	 * @return track header name
	 */
	public String getHeaderName(){
		return header.getName();
	}
	
	/**
	 * Get track string tuning
	 * @return string tuning
	 */
	public TWStringsTunning getTuning(){
		return header.getStringsTunning();
	}
}
