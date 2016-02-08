	package simori.Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.MatrixModel;

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

}
