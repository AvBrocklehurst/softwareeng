package simori.Exceptions;

public class SimoriFatalException extends RuntimeException {
	//Parameterless Constructor
    public SimoriFatalException() {}

    //Constructor that accepts a message
    public SimoriFatalException(String message) {
       super(message);
    }
}