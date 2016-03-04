package simori.Tests;

import simori.ResourceManager;
import static org.junit.Assert.*;

import java.io.File;
import org.junit.Test;

/**
 * @author Josh
 * @version 1.0.0
 * 
 * Junit tests for resource manager
 */
public class TestResourceManager {
	
	/**
	 * @author Josh
	 * @verison 1.0.0
	 * 
	 * Test to see if resource manager cannot get a file that exists
	 */
	@Test
	public void testGetResourceBad() {
		File file = ResourceManager.getResource("test.bad");
		assertFalse(file.exists());
	}
	

	/**
	 * @author josh
	 * @verison 1.0.0
	 * 
	 * Test to see if resource manager can get a file that exists
	 */
	@Test
	public void testGetResourceGood() {
		File file = ResourceManager.getResource("instruments.csv");
		assertNotNull(file);
		assertTrue(file.exists());
	}

}
