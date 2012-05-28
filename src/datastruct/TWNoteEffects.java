package datastruct;

/**
 * The TWNoteEffects class represents all effects of note (vibrato, slide and s.o.)
 * 
 * 
 */
public class TWNoteEffects {
	private static final int EFFECTS_COUNT = 13;
	
	public static final int VIBRATO = 0;
	public static final int DEAD = 1;
	public static final int SLIDE = 2;
	public static final int HAMMER = 3;
	public static final int GHOST = 4;
	public static final int ACCENTUATED = 5;
	public static final int HEAVYACCENTUATED = 6;
	public static final int PALMMUTE = 7;
	public static final int STACCATO = 8;
	public static final int TIE = 9;
	public static final int HARMONIC = 10;
	public static final int TREMOLO = 11;
	public static final int LETRING = 12;
	
	private boolean[] simpleEffects;
	
	private TWBendEffect bendEffect;
	
	/**
	 * Bend effect class. Part of note effects class.
	 * 
	 * 
	 */
	public static class TWBendEffect {
		
		public static final TWBendPoint[] BEND_FULL = {new TWBendPoint(1,1), new TWBendPoint(4,5), new TWBendPoint(13,5)};
		public static final TWBendPoint[] BEND_HALF = {new TWBendPoint(1,1), new TWBendPoint(4,3), new TWBendPoint(13,3)};
		
		public static final TWBendPoint[] BEND_RELEASE_FULL = {new TWBendPoint(1,1), new TWBendPoint(3,5), new TWBendPoint(5,5), new TWBendPoint(7,1), new TWBendPoint(13,1)};
		public static final TWBendPoint[] BEND_RELEASE_HALF = {new TWBendPoint(1,1), new TWBendPoint(3,3), new TWBendPoint(5,3), new TWBendPoint(7,1), new TWBendPoint(13,1)};
		
		public static final TWBendPoint[] BEND_RELEASE_BEND_FULL = {new TWBendPoint(1,1), new TWBendPoint(3,5), new TWBendPoint(5,5), new TWBendPoint(7,1), new TWBendPoint(9,1), new TWBendPoint(11,5), new TWBendPoint(13,5)};
		public static final TWBendPoint[] BEND_RELEASE_BEND_HALF = {new TWBendPoint(1,1), new TWBendPoint(3,3), new TWBendPoint(5,3), new TWBendPoint(7,1), new TWBendPoint(9,1), new TWBendPoint(11,3), new TWBendPoint(13,3)};
		
		public static final TWBendPoint[] PREBEND_FULL = {new TWBendPoint(1,5), new TWBendPoint(13,5)};
		public static final TWBendPoint[] PREBEND_HALF = {new TWBendPoint(1,3), new TWBendPoint(13,3)};
		
		public static final TWBendPoint[] PREBEND_RELEASE_FULL = {new TWBendPoint(1,5), new TWBendPoint(4,5), new TWBendPoint(7,1), new TWBendPoint(13,1)};
		public static final TWBendPoint[] PREBEND_RELEASE_HALF = {new TWBendPoint(1,3), new TWBendPoint(4,3), new TWBendPoint(7,1), new TWBendPoint(13,1)};
		/**
		 * Class TWBendPoint represents one point of the bend's curve
		 * 
		 * 
		 */
		public static class TWBendPoint {
			public int position;
			public int value;
			
			/**
			 * Creates new bend's curve point
			 * @param position point position from 1 to 13 
			 * @param value point value from 1 to 15
			 */
			public TWBendPoint(int position, int value) {
				this.position = position;
				this.value = value;
			}
		}
		
		private TWBendPoint[] points;
		
		/**
		 * @param points points array. You can use constants declared in this class
		 */
		public TWBendEffect(TWBendPoint[] points) {
			this.points = points;
		}
		/**
		 * Get points of the bend's curve
		 * @return points of the bend's curve
		 */
		public TWBendPoint[] getPoints() { return points; }
	}
	
	TWNoteEffects() {
		simpleEffects = new boolean[EFFECTS_COUNT]; //there are only 13 effects
		for (int i = 0; i < EFFECTS_COUNT; i++) //disable all effects
			simpleEffects[i] = false;
		
		bendEffect = null;
	}
	/**
	 * Sets note simple effect true or false
	 * @param effect effect ID. Use constants declared in this class
	 * @param state effect state
	 */
	public void setSimpleEffect(int effect, boolean state) { simpleEffects[effect] = state; }
	/**
	 * Check if simple effect is enabled
	 * @param effect effect ID
	 * @return if simple effect is enabled
	 */
	public boolean hasSimpleEffect(int effect) { return simpleEffects[effect];}
	/**
	 * Main effects are VIBRATO, SLIDE, HAMMER, PALMMUTE, STACCATO, BEND, HARMONIC, TREMOLO
	 * @return if note has one or more main effects
	 */
	public boolean hasMainEffects() {
		return simpleEffects[VIBRATO] || simpleEffects[SLIDE] || simpleEffects[HAMMER] ||
			   simpleEffects[PALMMUTE] || simpleEffects[STACCATO] || simpleEffects[HARMONIC] ||
			   simpleEffects[TREMOLO] || simpleEffects[LETRING] || hasBendEffect();	
	}
	/**
	 * Add new bend effect to note
	 * @param bendEffect previously created Bend Effect 
	 */
	public void addBendEffect(TWBendEffect bendEffect) { this.bendEffect = bendEffect; }
	/**
	 * Remove bend effect from note
	 */
	public void removeBendEffect() { bendEffect = null; }
	/**
	 * Get bend effect
	 * @return bend effect or null
	 */
	public TWBendEffect getBendEffect() { return bendEffect; }
	/**
	 * Check if bend effect is enabled
	 * @return if bend effect is enabled
	 */
	public boolean hasBendEffect() { return bendEffect != null; }
}
