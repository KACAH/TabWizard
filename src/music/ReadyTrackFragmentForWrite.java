package music;
import datastruct.TWInstrumentTrack;
import datastruct.TWPercussionTrack;
import datastruct.TWStringsTunning;
import datastruct.TWTrackHeader;


public class ReadyTrackFragmentForWrite {

	private TWInstrumentTrack track;
	private TWPercussionTrack percTrack;
	private TWTrackHeader header;
	private String method;
	
	
	public void setInstrumentTrack(TWInstrumentTrack t)
	{
		track = t;
	}
	
	public void setPercussionTrack(TWPercussionTrack t)
	{
		percTrack = t;
	}
	
	public void setTrackHeader(TWTrackHeader h)
	{
		header = h;
	}
	
	public void setMethod(String m)
	{
		method = m;
	}
	
	public TWInstrumentTrack getTrack()
	{
		return track;
	}
	
	public TWPercussionTrack getPercussionTrack()
	{
		return percTrack;
	}
	
	public TWTrackHeader getHeader()
	{
		return header;
	}
	
	
	public String getMethod()
	{
		return method;
	}
	
	public String getHeaderName()
	{
		return header.getName();
	}
	
	public TWStringsTunning getTuning()
	{
		return header.getStringsTunning();
	}
}
