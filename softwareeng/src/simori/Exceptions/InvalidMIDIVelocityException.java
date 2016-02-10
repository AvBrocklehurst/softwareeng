package simori.Exceptions;

public class InvalidMIDIVelocityException extends Exception {
    
	public InvalidMIDIVelocityException() {}

    //Constructor that accepts a message
    public InvalidMIDIVelocityException(String message)
    {
       super(message);
    }
}
