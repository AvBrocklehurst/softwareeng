	package simori.Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.MatrixModel;
import simori.Exceptions.InvalidCoordinatesException;

public class TestMatrixModel {
	MatrixModel model;

	@Before
	public void setUp() {
		model = new MatrixModel();
	}

	@After
	public void tearDown() {
		model = null;
	}

	@Test
	public void testBPM(){
		model.setBPM((short) 100);
		assertEquals(100, model.getBPM());
	}
	
	@Test
	public void testVelocity(){
		model.setVelocity((byte)0, (byte)1);
		assertEquals(1, model.getVelocity((byte) 0));
	}
	
	@Test
	public void testChannel(){
		model.setChannel((byte)1, (byte)10);
		assertEquals(10, model.getChannel((byte)1));
	}
	
	@Test
	public void testInstruemnt(){
		model.setInstrument((byte)0, (short)12);
		assertEquals(12, model.getInstrument((byte)0));
	}
	
	@Test
	public void updateButtonTest() throws InvalidCoordinatesException{
		model.updateButton((byte) 0, (byte)0, (byte)0);
		assertEquals(true, model.getCol((byte)0, (byte)0)[0]);
	}

}
