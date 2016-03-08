package simori.Exceptions;

public class InvalidPortException extends Exception {
	
	//Parameterless Constructor
    public InvalidPortException() {}

    //Constructor that accepts a message
    public InvalidPortException(String message) {
       super(message);
    }
}