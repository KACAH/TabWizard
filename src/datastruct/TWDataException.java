package datastruct;

/**
 * The TWDataException class is exception that can be thrown trying to add incorrect data to TabsWizard data structure
 * 
 * @author Deniss Paltovs (KACAH)
 */
public class TWDataException extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The only constructor
	 * @param message message of the exception
	 */
	public TWDataException(String message) {
		super(message);
	}
}
