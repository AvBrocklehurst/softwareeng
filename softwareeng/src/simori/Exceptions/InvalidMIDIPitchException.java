package simori.Exceptions;
/**
 * 
 * @author Josh
 * @version 1.0.0
 */
public class InvalidMIDIPitchException extends Exception {
    
	public InvalidMIDIPitchException() {}

    //Constructor that accepts a message
    public InvalidMIDIPitchException(String message)
    {
       super(message);
    }
}
