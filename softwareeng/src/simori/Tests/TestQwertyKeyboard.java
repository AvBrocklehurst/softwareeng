package simori.Tests;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.Exceptions.KeyboardException;
import simori.Modes.QwertyKeyboard;

/**
 * A class to test QwertyKeyboard.
 * 
 * @author James
 * @version 1.0.0
 * @see QwertyKeyboard.java
 *
 */
public class TestQwertyKeyboard {
	
	private QwertyKeyboard keyboard;
	
	@Before
	public void setUp() throws KeyboardException{
		keyboard = new QwertyKeyboard((byte)16,(byte)16);
	}
	
	@After
	public void tearDown(){
		keyboard = null;
	}
	
	@Test
	public void test_constructor(){
		assertThat("A keyboard wasn't created properly!", keyboard, instanceOf(QwertyKeyboard.class));
	}
	
	@Test
	public void test_getRows(){
		assertEquals("The number of rows was not returned correctly!", (byte)16, keyboard.getRows());
	}
	
	@Test
	public void test_getColumns(){
		assertEquals("The number of columns was not returned correctly!", (byte)16, keyboard.getColumns());
	}
	
	@Test
	public void test_getLetterOn(){
		assertEquals("The character should be null correctly!", null, keyboard.getLetterOn((byte)0, (byte)3));
	}

}
