package simori.Exceptions;

/**
 * General Exception for all fatal problems in the simori.
 * Extends RuntimeExcpetion so that it can go unchecked and get
 * caught by the universal exception handler.
 * @author Adam
 */
public class SimoriFatalException extends RuntimeException {
	//Parameterless Constructor
    public SimoriFatalException() {}

    //Constructor that accepts a message
    public SimoriFatalException(String message) {
       super(message);
    }
}