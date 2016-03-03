package simori.Exceptions;

public class KeyboardException extends Exception {
	
	//Parameterless Constructor
    public KeyboardException() {}

    //Constructor that accepts a message
    public KeyboardException(String message) {
       super(message);
    }
}