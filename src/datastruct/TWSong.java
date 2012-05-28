package datastruct;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The TWSong class represents whole song with all headers, measures and tracks. 
 * This is the main class to work with.
 * 
 * 
 */
public class TWSong
{
	private String author;
	private int tempo;
	
	private List <TWTrackHeader> trackHeaders;
	
	private List<TWSongPart> parts;
	
	/**
	 * Create song with default settings
	 */
	public TWSong() {
		author = TWDefaults.AUTHOR;
		tempo = TWDefaults.TEMPO;
			
		trackHeaders = new ArrayList<TWTrackHeader>();
		parts = new LinkedList<TWSongPart>();
	}
	
	/**
	 * Extended constructor with parameters
	 * @param tempo The tempo of the song
	 */
	public TWSong(int tempo) {
		author = TWDefaults.AUTHOR;
		this.tempo = tempo;
		
		trackHeaders = new ArrayList<TWTrackHeader>();
		parts = new LinkedList<TWSongPart>();
	}
	/**
	 * Get song's author. In TabsWizard author's name is known and generated automatically,
	 * so it cann't be changed.
	 * @return The author of the song.
	 */
	public String getAuthor() { return author; }
	/**
	 * Get song's tempo. Song's tempo can be changed by {@link #setTempo(int)} 
	 * @return The tempo of the song. 
	 */
	public int getTempo() { return tempo; }
	/**
	 * Change song's tempo. Song's tempo can be received by {@link #getTempo()} 
	 * @param tempo New tempo of the song
	 * @throws TWDataException if tempo is <= 0
	 */
	public void setTempo(int tempo) throws TWDataException {
		if (tempo <= 0)
			throw new TWDataException("Song tempo must be > 0"); 
		
		this.tempo = tempo;
	}
	/**
	 * Get all tracks number of the song, including percussion track
	 * @return number of tracks 
	 */
	public int getTracksNum() {
		return trackHeaders.size();
	}
	/**
	 * Summ of all song's parts measures
	 * @return measures number
	 */
	public int getMeasuresNum() {
		int res = 0;
		Iterator<TWSongPart> itr = parts.iterator(); 
		while( itr.hasNext() ) {
		    TWSongPart cur = itr.next();
		    res += cur.getMeasuresNum();
		}
		return res;
	}
	/**
	 * Get track header by index
	 * @param index index of track header
	 * @return track header
	 */
	public TWTrackHeader getTrackHeader(int index) {
		return trackHeaders.get(index);
	}
	/**
	 * Check if this song have percussion track
	 * @return if this song have percussion track
	 */
	public boolean hasPercussionTrack() {
		Iterator<TWTrackHeader> itr = trackHeaders.iterator(); 
		while( itr.hasNext() ) {
			if (itr.next().isPercussionTrack())
				return true;
		}
		return false;
	}
	/**
	 * Add new track to the song
	 * @param header previosly created track header
	 * @throws TWDataException
	 */
	public void addTrack(TWTrackHeader header) throws TWDataException {
		if (header.isPercussionTrack() && hasPercussionTrack())
			throw new TWDataException("This song just has percussion track");
		
		trackHeaders.add(header);
		
		Iterator<TWSongPart> itr = parts.iterator(); 
		while( itr.hasNext() ) {
		    TWSongPart cur = itr.next();
		    cur.addTrack(header);
		}
	}
	/** 
	 * Check if tracks of the part and the song match
	 * @param part song part to check
	 * @return if tracks of the part and the song match
	 */
	public boolean isValidSongPart(TWSongPart part) {
		if (getTracksNum() != part.getTracksNum())
			return false;
		
		boolean usedPercussion = false; //if true instrument track will be -1
		for (int i = 0; i < getTracksNum(); i++) {
			TWTrackHeader curHeader = getTrackHeader(i);
			TWTrackHeader toCompare = null;
			if (curHeader.isPercussionTrack()) {
				usedPercussion = true;
				if (!part.hasPercussionTrack())
					return false;
				toCompare = part.getPercussionTrack().getHeader();
			} else {
				int instIndex = 0;
				if (usedPercussion)
					instIndex = i-1;
				else
					instIndex = i;
				
				if (instIndex >= part.getInstrumentTracksNum())
					return false;
				toCompare = part.getInstrumentTrack(instIndex).getHeader();
			}
			if (toCompare != null && !curHeader.isLike(toCompare))
				return false;
		}
		return true;
	}
	/**
	 * Create new song part using tracks headers. Guaranteed to be valid for adding 
	 * @param name name of the part
	 * @return created song part
	 * @throws TWDataException
	 */
	public TWSongPart createSongPart(String name) throws TWDataException {
		TWSongPart part = new TWSongPart(name);
		
		Iterator<TWTrackHeader> itr1 = trackHeaders.iterator(); 
		while( itr1.hasNext() ) {
			part.addTrack(itr1.next());
		}
		return part;
	}
	/**
	 * Add created song part to this song at given position
	 * @param part created song part. Tracks of the part and the song must match
	 * @param index position in list
	 * @throws TWDataException if part is invalid for this song
	 */
	public void addSongPart(TWSongPart part, int index) throws TWDataException {
		if (isValidSongPart(part))
			parts.add(index, part);
		else
			 throw new TWDataException("Song part isn't valid for this song");
	}
	/**
	 * Remove existing song part
	 * @param index position in list
	 */
	public void removeSongPart(int index) { parts.remove(index); }
	/**
	 * Get list of song parts
	 * @return list of song parts
	 */
	public List<TWSongPart> getParts() { return parts; }
}
