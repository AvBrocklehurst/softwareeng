package simori.Exceptions;

/**
 * Thrown by {@link simori.Modes.QwertyKeyboard} to indicate that the requested
 * dimensions do not produce enough buttons to mimic a QWERTY-style keyboard.
 * @author Matt
 * @version 1.0.0
 */
public class SimoriNonFatalException extends Exception {
	
	//Parameterless Constructor
    public SimoriNonFatalException() {}

    //Constructor that accepts a message
    public SimoriNonFatalException(String message) {
       super(message);
    }
}