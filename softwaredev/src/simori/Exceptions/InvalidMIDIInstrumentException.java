package simori.Exceptions;

public class InvalidMIDIInstrumentException extends Exception {
    
	public InvalidMIDIInstrumentException() {}

    //Constructor that accepts a message
    public InvalidMIDIInstrumentException(String message)
    {
       super(message);
    }
}
