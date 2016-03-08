package simori.Exceptions;

/**
 * Thrown by {@link simori.Modes.QwertyKeyboard} to indicate that the requested
 * dimensions do not produce enough buttons to mimic a QWERTY-style keyboard.
 * @author Matt
 * @version 1.0.0
 */
public class KeyboardException extends Exception {
	
	//Parameterless Constructor
    public KeyboardException() {}

    //Constructor that accepts a message
    public KeyboardException(String message) {
       super(message);
    }
}