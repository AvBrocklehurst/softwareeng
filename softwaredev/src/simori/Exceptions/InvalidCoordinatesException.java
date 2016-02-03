package simori;

public class InvalidCoordinatesException extends Exception{
	 //Parameterless Constructor
    public InvalidCoordinatesException() {}

    //Constructor that accepts a message
    public InvalidCoordinatesException(String message)
    {
       super(message);
    }
}
