package gp5;

import datastruct.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/**
 * The GP5Saver class represents tool for
 * exporting songs in *.gp5 file format. This class is the only public class in GP5 packet. 
 * 
 * @author Deniss Paltovs (KACAH)
 * @author Julian (TuxGuitar)
 */
public class GP5Saver {	
	private GP5FileOutputStream out;
	
	/**
	 * Save the song in gp5 format. 
	 * This method can be used many times for one GP5Saver.
	 * @param song {@link datastruct.TWSong} for saving
	 * @param fileName output file name (full or relative path)
	 */
	public void saveSong(TWSong song, String fileName) throws FileNotFoundException, IOException {
		out = new GP5FileOutputStream(fileName);
		
		writeVersion();
		writeTags(song);
		writeLyrics();
		writePageSetup();
		out.writeInt(song.getTempo());
		
		out.writeInt(0); //key
		out.write( (byte)0 ); //octave
		
		writeChannels(song);
		
		for(int i = 0; i < 42; i ++) { //unknown 42 bytes...
			out.write((byte)0xff);
		}
		
		out.writeInt(song.getMeasuresNum());
		out.writeInt(song.getTracksNum());
		
		writeMeasureHeaders(song);
		writeTracks(song);
		out.writeNulls(2); //gtp reserved bytes
		writeMeasures(song);
		
		out.flush();
		out.close();
	}
	
	//write GP5 HEADER - GTP Version
	private void writeVersion() throws IOException {
		out.writeStringByte(GP5Defaults.VERSION, 30); //30 bytes for version
	}
	
	//write song tags only few will be used
	private void writeTags(TWSong song) throws IOException {
		out.writeStringInt(""); //song name
		out.writeStringInt(""); //Subtitle
		out.writeStringInt(""); //Artist
		out.writeStringInt(""); //Album
		out.writeStringInt(""); //Words Author
		out.writeStringInt(song.getAuthor()); //Music Author
		out.writeStringInt(song.getAuthor()); //Copyright
		out.writeStringInt(song.getAuthor()); //Writer
		out.writeStringInt(""); //instructons
		out.writeInt( 0 ); //Comments - must be List.size + List
	}
	
	//don't need for this project, but is a part of gp5 file
	private void writeLyrics() throws IOException {
		out.writeInt( 0 ); //lyric track number
		//5 lines of
		for (int i = 0; i < 5; i++) {
			out.writeInt( 0 ); //empty 
			out.writeStringInt(""); //lyrics
		}
	}
	
	//don't need for this project, but is a part of gp5 file
	private void writePageSetup() throws IOException {
		out.writeInt( 210 ); // Page width
		out.writeInt( 297 ); // Page height
		out.writeInt( 10 );  // Margin left
		out.writeInt( 10 );  // Margin right
		out.writeInt( 15 );  // Margin top
		out.writeInt( 10 );  // Margin bottom
		out.writeInt( 100 ); // Score size percent
		
		out.write( ( byte )0xff ); // View flags
		out.write( ( byte )0x01 ); // View flags
		
		for (int i = 0; i < GP5Defaults.PAGE_SETUP_LINES.length; i++) {
			out.writeInt( (GP5Defaults.PAGE_SETUP_LINES[i].length() + 1) );
			out.writeStringByte(GP5Defaults.PAGE_SETUP_LINES[i]);
		}
	}
	
	private void writeChannels(TWSong song) throws IOException {
		GP5Channel[] channels = makeChannels(song);
		for (int i = 0; i < channels.length; i++) {
			out.writeInt(channels[i].instrument);
			out.write(toChannelByte(channels[i].volume));
			out.write(toChannelByte(channels[i].balance));
			out.write(toChannelByte(channels[i].chorus));
			out.write(toChannelByte(channels[i].reverb));
			out.write(toChannelByte(channels[i].phaser));
			out.write(toChannelByte(channels[i].tremolo));
			out.writeNulls(2); //Two blank bytes. Backward compatibility with GTP version 3.0
		}
	}
	
	private byte toChannelByte(short s) {
		return  (byte) ((s + 1) / 8);
	}
	
	private GP5Channel[] makeChannels(TWSong song) {
		//in gp5 format must be 4 ports and 16 channels on each. So 64 channels in summ
		GP5Channel[] channels = new GP5Channel[64];
		
		//gtp normally uses 2 channels per track. One for notes, and one for effects. 
		//However for percussion they are both DEFAULT_PERCUSSION_CHANNEL
		//And also it can be one channel for both notes and effects. 
		int curChannel = 0;
		boolean percCreated = false;
		for (int i = 0; i < song.getTracksNum(); i++) {
			if (curChannel == GP5Defaults.PERCUSSION_CHANNEL)
				curChannel++;
			
			//notes channel
			TWTrackHeader track = song.getTrackHeader(i);
			if (track.isPercussionTrack()) {
				channels[GP5Defaults.PERCUSSION_CHANNEL] = new GP5Channel();
				channels[GP5Defaults.PERCUSSION_CHANNEL].volume = track.getVolume();
				channels[GP5Defaults.PERCUSSION_CHANNEL].balance = track.getBalance();
				percCreated = true;
			} else {
				channels[curChannel] = new GP5Channel();
				channels[curChannel].volume = track.getVolume();
				channels[curChannel].balance = track.getBalance();
				channels[curChannel].instrument = track.getInstrument();
				curChannel++;
			}
		}
		
		//other channels will be empty
		for (int i = curChannel; i < 64; i++)
			if (i!=GP5Defaults.PERCUSSION_CHANNEL || !percCreated)
				channels[i] = new GP5Channel();
		
		return channels;
	}
	
	private void writeMeasureHeaders(TWSong song) throws IOException {
		TWTimeSignature timeSignature = new TWTimeSignature();
		
		Iterator<TWSongPart> itr = song.getParts().iterator(); 
		boolean firstMeasure = true;
		while( itr.hasNext() ) {
		    TWSongPart curPart = itr.next();
			for (int i = 0; i < curPart.getMeasuresNum(); i++) {
				if(!firstMeasure) {
					out.writeNulls(1);
				}
				
				TWMeasureHeader measure = curPart.getMeasureHeader(i);
				writeMeasureHeader(measure, i, timeSignature);
					
				timeSignature.setNumerator(measure.getTimeSignature().getNumerator());
				timeSignature.setDenominator(measure.getTimeSignature().getDenominator());
				firstMeasure = false;
			}
		}
	}
	
	private void writeMeasureHeader(TWMeasureHeader measure, int mesNum, TWTimeSignature timeSignature) throws IOException {
		int flags = 0;
		if(mesNum == 0) {
			flags |= 0x40; //first measure flag
		}
		if (mesNum == 0 || !measure.getTimeSignature().isEqual(timeSignature)) {
			flags |= 0x01; //need numerator flag
			flags |= 0x02; //need denumerator flag
		}
		//other flags not need now
		out.write(flags);
		
		if ((flags & 0x01) != 0) { //write numerator
			out.write( (byte) measure.getTimeSignature().getNumerator() );
		}
		if ((flags & 0x02) != 0) { //write denominator
			out.write( (byte) measure.getTimeSignature().getDenominator() );
		}
		if ((flags & 0x40) != 0) { //first measure reserved bytes
			out.writeNulls(2);
		}
		if ((flags & 0x01) != 0) {
			out.write( makeBeamEighthNoteBytes( measure.getTimeSignature() ));
		}
		if((flags & 0x10) == 0) {
			out.write((byte)0);
		}
		out.write((byte)0); //Triplet Feel - not used
	}
	
	private byte[] makeBeamEighthNoteBytes(TWTimeSignature ts) {
		byte[] bytes = new byte[]{0,0,0,0};
		if( ts.getDenominator() <= 8 ) {
			int eighthsInDenominator = (8 / ts.getDenominator() );
			int total = (eighthsInDenominator * ts.getNumerator());
			int byteValue = ( total / 4 );
			int missingValue = ( total - (4 * byteValue) );
			for( int i = 0 ; i < bytes.length; i ++ ) {
				bytes[i] = (byte)byteValue;
			}
			if( missingValue > 0 ) {
				bytes[0] += missingValue;
			}
		}
		return bytes;
	}
	
	private void writeTracks(TWSong song) throws IOException {
		int curChannel = 0;
		for (int i = 0; i < song.getTracksNum(); i++) {
			if (curChannel == GP5Defaults.PERCUSSION_CHANNEL)
				curChannel++;
			TWTrackHeader track = song.getTrackHeader(i);
			if (track.isPercussionTrack())
				writeTrackHeader(track, true, GP5Defaults.PERCUSSION_CHANNEL, GP5Defaults.PERCUSSION_CHANNEL);
			else {
				writeTrackHeader(track, false, curChannel, curChannel);
				curChannel++;
			}
		}
	}
	
	private void writeTrackHeader(TWTrackHeader track, boolean percussionTrack, int mainChannel, int effChannel) throws IOException {
		int flags = 0;
		if (percussionTrack) {
			flags |= 0x01;
		}
		out.write(flags);
		out.write((byte)(8|flags)); //unknown reserved byre
		out.writeStringByte(track.getName(), 40); //name string 40 bytes MAX
		out.writeInt(track.getStringsTunning().getNumStringsUsed());
		for (int i = 0; i < 7; i++) { //realy need 7 strings, but less can be used
			int value = 0;
			if (track.getStringsTunning().getNumStringsUsed() > i) {
				value = track.getStringsTunning().getStringInt(i);
			}
			out.writeInt(value);
		}
		out.writeInt(1); //port
		out.writeInt(mainChannel + 1);
		out.writeInt(effChannel + 1);
		out.writeInt(24); //number of frets
		out.writeInt(0); //offset
		writeColor(track.getColor());
		//something strange Oo
		out.write(new byte[]{ 67, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, -1, 3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1});
	}
	
	private void writeColor(TWColor color) throws IOException {
		out.write(color.getR());
		out.write(color.getG());
		out.write(color.getB());
		out.write((byte)0);
	}
	
	private void writeMeasures(TWSong song) throws IOException {
		Iterator<TWSongPart> itr = song.getParts().iterator(); 
		while( itr.hasNext() ) {
		    TWSongPart curPart = itr.next();
			for (int i = 0; i < curPart.getMeasuresNum(); i++) {
				for (int j = 0; j < curPart.getTracksNum(); j++) {
					TWTrack track = curPart.getTrack(j);
					TWMeasure measure = track.getMeasure(i);
					writeMeasure(measure);
					out.writeNulls(1); //gtp reserved byte
				}
			}
		}
	}
	
	private void writeMeasure(TWMeasure measure) throws IOException {
		int numBeats = measure.getNumBeats();
		for(int v = 0; v < 2 ; v ++) {
			if (v == 1) //second voice will be empty
				out.writeInt(0);
			else {
				out.writeInt(numBeats);
				for (int i = 0; i < numBeats; i++) {
					writeBeat(measure.getBeat(i));
				}
			}
		}
	}
	
	private void writeBeat(TWBeat beat) throws IOException {
		int flags = 0;
		if ( beat.isDotted() ) {
			flags |= 0x01;
		}
		if ( beat.getDuration().isTriplet() ) {
			flags |= 0x20;
		}
		if  ( beat.isEmpty() || beat.isRest() ) {
			flags |= 0x40;
		}
		if ( beat.isFadeIn() || beat.hasStroke() ) {
			flags |= 0x08;
		}
		out.write(flags);
		
		if ((flags & 0x40) != 0) {
			/*if (beat.isEmpty())
				out.write( 0x00 );
			else*/
			//in this case empty beat = pause beat
			out.write( 0x02 );
		}
		out.write(parseDuration(beat.getDuration()));
		
		if ((flags & 0x20) != 0) {
			out.writeInt(3); //Triplet - others aren't needed
		}
		
		if ( (flags & 0x08) != 0 ) {
			if (beat.isFadeIn()) {
				flags |= 0x10;
			}
			if (beat.hasStroke()) {
				flags |= 0x40;
			}
			out.write(flags);
			out.writeNulls(1); //second flag will be empty
			if ((flags & 0x40) != 0) {
				out.write( (beat.getStroke().getDirection() == TWStroke.UP ? toStrokeValue(beat.getStroke()) : 0 ) );
				out.write( (beat.getStroke().getDirection() == TWStroke.DOWN ? toStrokeValue(beat.getStroke()) : 0 ) );
			}
		}
		
		if (!beat.isRest() && !beat.isEmpty())
		{
			int stringFlags = 0;
			//All strings from 1 to how many they are ( 1, 2, 3, 4, 5, 6, (7) )
			for (int i = 1; i <= beat.getNumStrings(); i++) {
				//if beat hes note on this string
				if (beat.noteExists(i)) {
					stringFlags |= (1 << 7-i); //string flag will be true. 7-1 = 6 to 0 because gtp format has reverse sequence
				}
			}
			out.write(stringFlags);
			for (int i = 7; i >= 1; i--) //all strings in reverse sequence
				if ((stringFlags & (1 << i-1)) != 0 ) //if has this flag
							writeNote(beat.getNote(7-i+1)); //back to normal string numbers
		} else
			out.writeNulls(1);
		
		out.writeNulls(2);
	}
	
	private int toStrokeValue( TWStroke stroke ){
		if( stroke.getDuration().getValue() == TWDuration.SIXTY_FOURTH ){
			return 2;
		}
		if( stroke.getDuration().getValue() == TWDuration.THIRTY_SECOND ){
			return 3;
		}
		if( stroke.getDuration().getValue() == TWDuration.SIXTEENTH ){
			return 4;
		}
		if( stroke.getDuration().getValue() == TWDuration.EIGHTH ){
			return 5;
		}
		if( stroke.getDuration().getValue() == TWDuration.QUARTER ){
			return 6;
		}
		return 2; //if value is incorrect default will be SIXTY_FOURTH stroke
	}
	
	private void writeNote(TWNote note) throws IOException {
		int flags = ( 0x20 | 0x10 );
		if (note.getEffects().hasMainEffects()) {
			flags |= 0x08;
		}
		if( note.getEffects().hasSimpleEffect(TWNoteEffects.GHOST) ) {
			flags |= 0x04;
		}
		if( note.getEffects().hasSimpleEffect(TWNoteEffects.HEAVYACCENTUATED) ){
			flags |= 0x02;
		}
		if( note.getEffects().hasSimpleEffect(TWNoteEffects.ACCENTUATED) ){
			flags |= 0x40;
		}
		out.write(flags);
		
		if ((flags & 0x20) != 0) {
			int typeHeader = 0x01;
			if ( note.getEffects().hasSimpleEffect(TWNoteEffects.TIE) ) {
				typeHeader = 0x02;
			} else 
			
			if( note.getEffects().hasSimpleEffect(TWNoteEffects.DEAD) ) {
				typeHeader = 0x03;
			}
			out.write(typeHeader);
		}
		if ((flags & 0x10) != 0) {
			out.write((byte)(((note.getVelocity() - TWVelocities.MIN_VELOCITY) / TWVelocities.VELOCITY_INCREMENT) + 1));
		}
		if ((flags & 0x20) != 0) {
			out.write((byte) note.getValue());
		}
		out.writeNulls(1);
		if ((flags & 0x08) != 0) {
			writeNoteEffects(note.getEffects());
		}
	}
	
	private byte parseDuration(TWDuration duration) {
		byte value = 0;
		switch (duration.getValue()) {
			case TWDuration.WHOLE:
				value = -2;
				break;
			case TWDuration.HALF:
				value = -1;
				break;
			case TWDuration.QUARTER:
				value = 0;
				break;
			case TWDuration.EIGHTH:
				value = 1;
				break;
			case TWDuration.SIXTEENTH:
				value = 2;
				break;
			case TWDuration.THIRTY_SECOND:
				value = 3;
				break;
			case TWDuration.SIXTY_FOURTH:
				value = 4;
				break;
		}
		return value;
	}
	
	private void writeNoteEffects(TWNoteEffects effects) throws IOException {
		int flags1 = 0;
		int flags2 = 0;
		if ( effects.hasBendEffect() ) {
			flags1 |= 0x01;
		}
		if ( effects.hasSimpleEffect(TWNoteEffects.HAMMER) ) {
			flags1 |= 0x02;
		}
		if ( effects.hasSimpleEffect(TWNoteEffects.LETRING) ) {
			flags1 |= 0x08;
		}
		/*if (effect.isGrace()) {
			flags1 |= 0x10;
		}*/
		if ( effects.hasSimpleEffect(TWNoteEffects.STACCATO) ) {
			flags2 |= 0x01;
		}
		if ( effects.hasSimpleEffect(TWNoteEffects.PALMMUTE) ) {
			flags2 |= 0x02;
		}
		if ( effects.hasSimpleEffect(TWNoteEffects.TREMOLO) ) {
			flags2 |= 0x04;
		}
		if ( effects.hasSimpleEffect(TWNoteEffects.SLIDE) ) {
			flags2 |= 0x08;
		}
		if ( effects.hasSimpleEffect(TWNoteEffects.HARMONIC) ) {
			flags2 |= 0x10;
		}
		/*if (effect.isTrill()) {
			flags2 |= 0x20;
		}*/
		if ( effects.hasSimpleEffect(TWNoteEffects.VIBRATO) ) {
			flags2 |= 0x40;
		}
		out.write(flags1);
		out.write(flags2);
		if ((flags1 & 0x01) != 0) {
			writeBend(effects.getBendEffect());
		}
		
		/*if ((flags1 & 0x10) != 0) {
			writeGrace(effect.getGrace());
		}*/
		
		if ((flags2 & 0x04) != 0) {
			out.write( (byte)3 ); //Thirty second tremolo, 16 and 8 are not interesting
		}
		
		if ((flags2 & 0x08) != 0) {
			out.write( (byte)1 );
		}
		
		if ((flags2 & 0x10) != 0) {
			out.write( (byte)1 );
		}
		
		/*if ((flags2 & 0x20) != 0) {
			writeTrill(effect.getTrill());
		}*/
	}
	
	private void writeBend(TWNoteEffects.TWBendEffect bend) throws IOException {
		int pointNum = bend.getPoints().length;
		out.write( (byte)1 ); //unknown byte
		out.writeNulls(4); //unknown bytes
		out.writeInt(pointNum);
		for (int i = 0; i < pointNum; i++) {
			TWNoteEffects.TWBendEffect.TWBendPoint point = bend.getPoints()[i];
			out.writeInt( ((point.position-1) * GP5Defaults.GP_BEND_POSITION / GP5Defaults.BEND_MAX_POSITION_LENGTH) );
			out.writeInt( ((point.value-1) * GP5Defaults.GP_BEND_SEMITONE / GP5Defaults.BEND_SEMITONE_LENGTH) );
			out.writeNulls(1); //unknown byte
		}
	}
}
