package datastruct;

/**
 * The TWSTroke class represents beat stroke effect. Also it contains some constants, witch help to work with strokes
 * 
 * 
 */
public class TWStroke {
	public static final int UP = 1;
	public static final int DOWN = -1;
	
	private int direction;
	private TWDuration duration;
	
	/**
	 * @param direction direction of the stroke. Use constants of this class or 0, 1, -1
	 * @param duration duration of the stroke. Must be < than HALF 
	 */
	public TWStroke(int direction, TWDuration duration) throws TWDataException {
		if (direction != UP && direction != DOWN)
			throw new TWDataException("Unknown stroke direction: "+direction);
		if (duration.getValue() < TWDuration.HALF)
			throw new TWDataException("Too large duration: "+duration.getValue());
		
		this.direction = direction;
		this.duration = duration;
	}
	/**
	 * Get direction of stroke
	 * @return direction of stroke
	 */
	public int getDirection() { return direction; }
	/**
	 * Get duration of stroke
	 * @return duration of stroke
	 */
	public TWDuration getDuration() { return duration; }
}
