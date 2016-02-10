package simori.Exceptions;

public class InvalidMIDIChannelException extends Exception {
    
	public InvalidMIDIChannelException() {}

    //Constructor that accepts a message
    public InvalidMIDIChannelException(String message)
    {
       super(message);
    }
}
