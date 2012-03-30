package datastruct;

/**
 * The TWColor class represents color in RGB model 
 * 
 * @author Deniss Paltovs (KACAH)
 */
public class TWColor {
	private int r;
	private int g;
	private int b;
	
	/**
	 * Default constructor. Color will be black
	 */
	public TWColor() {
		r = 0;
		g = 0;
		b = 0;
	}
	/**
	 * RGB constructor
	 * @param r R component of the color
	 * @param g G component of the color
	 * @param b B component of the color
	 */
	public TWColor(int r, int g, int b) {
		setColor(r,g,b);
	}
	/**
	 * Get R component of the color
	 * @return R component of the color
	 */
	public int getR() { return r; }
	/**
	 * Get G component of the color
	 * @return G component of the color
	 */
	public int getG() { return g; }
	/**
	 * Get B component of the color
	 * @return B component of the color
	 */
	public int getB() { return b; }
	/**
	 * Set R component of the color
	 * @param r R component of the color
	 */
	public void setR(int r) { this.r = r; }
	/**
	 * Set G component of the color
	 * @param g G component of the color
	 */
	public void setG(int g) { this.g = g; }
	/**
	 * Set B component of the color
	 * @param b B component of the color
	 */
	public void setB(int b) { this.b = b; }
	/**
	 * Set all components of the color
	 * @param r R component of the color
	 * @param g G component of the color
	 * @param b B component of the color
	 */
	public void setColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
}
