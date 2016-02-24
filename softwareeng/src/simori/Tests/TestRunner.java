package simori.Tests;



import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


/**
 * @author Josh
 * @author Matt
 * @version 1.0.1
 * class that is able to run the test suite with meaningful output
 */
public class TestRunner {
	
	public static void main(String[] args) {
		System.out.println();
		Result result = JUnitCore.runClasses(TestSuite.class);
		
		for(Failure failure : result.getFailures()){
			System.out.println(failure.toString());
		}

		if(result.wasSuccessful()){
			System.out.println("All tests were sucessful");
		}
	}

}
