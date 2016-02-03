package simori.Exceptions;

public class InvalidMIDIPitchException extends Exception {
    
	public InvalidMIDIPitchException() {}

    //Constructor that accepts a message
    public InvalidMIDIPitchException(String message)
    {
       super(message);
    }
}
