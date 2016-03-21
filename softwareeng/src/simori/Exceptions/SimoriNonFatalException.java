package simori.Exceptions;

/**
 * General Exception for all non fatal problems in the simori.
 * Extends RuntimeExcpetion so that it can go unchecked and get
 * caught by the universal exception handler.
 * @author Adam
 */
public class SimoriNonFatalException extends RuntimeException {
	
	//Parameterless Constructor
    public SimoriNonFatalException() {}

    //Constructor that accepts a message
    public SimoriNonFatalException(String message) {
       super(message);
    }
}