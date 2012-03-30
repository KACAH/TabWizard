package gp5;

/**
 * The interface GP5Defaults is container of Guitar Pro file format used constants
 * 
 * @author Deniss Paltovs (KACAH)
 * @author Julian (TuxGuitar)
 */
interface GP5Defaults  {
	public static final String CHARSET = "UTF-8"; 
	public static final String VERSION = "FICHIER GUITAR PRO v5.00";
	
	public static final String[] PAGE_SETUP_LINES = {
		"%TITLE%",
		"%SUBTITLE%",
		"%ARTIST%",
		"%ALBUM%",
		"Words by %WORDS%",
		"Music by %MUSIC%",
		"Words & Music by %WORDSMUSIC%",
		"Copyright %COPYRIGHT%",
		"All Rights Reserved - International Copyright Secured",
		"Page %N%/%P%",
		"Moderate",
	};
	
	public static final short PERCUSSION_CHANNEL = 9;
	
	public static final int GP_BEND_SEMITONE = 25;
	public static final int GP_BEND_POSITION = 60;
	public static final int BEND_SEMITONE_LENGTH = 1;
	public static final int BEND_MAX_POSITION_LENGTH = 12;
	public static final int BEND_MAX_VALUE_LENGTH = (BEND_SEMITONE_LENGTH * 12);
}
