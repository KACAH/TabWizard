package datastruct;

/**
 * The TWNote class represents song's note with effects
 * 
 * @author Deniss Paltovs (KACAH)
 */
public class TWNote {
	private int velocity;
	private int value;
	
	private TWNoteEffects effects;
	/**
	 * @param value this value is guitar mood number 
	 */
	public TWNote(int value) {
		this.value = value;
		velocity = TWVelocities.DEFAULT;
		
		effects = new TWNoteEffects();
	}
	/**
	 * @param value this value is guitar mood number 
	 * @param velocity see {@link TWVelocities}
	 */
	public TWNote(int value, int velocity) {
		this.value = value;
		this.velocity = velocity;
		
		effects = new TWNoteEffects();
	}
	/**
	 * Get note velocity
	 * @return velocity
	 */
	public int getVelocity() { return velocity; }
	/**
	 * Get note guitar mood
	 * @return note guitar mood
	 */
	public int getValue() { return value; }
	/**
	 * Get note effects
	 * @return note effects
	 */
	public TWNoteEffects getEffects() { return effects; }
	/**
	 * More comfortable way to add simple effect to note
	 * @param effect effect ID
	 * @param state effect state
	 */
	public void setSimpleEffect(int effect, boolean state) { effects.setSimpleEffect(effect, state); }
}
