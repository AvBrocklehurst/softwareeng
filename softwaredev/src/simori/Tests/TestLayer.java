package simori.Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.Exceptions.InvalidCoordinatesException;
import simori.Layer;

public class TestLayer {
	Layer testlayer;

	@Before
	public void setUp() throws Exception {
		testlayer = new Layer();
	}

	@After
	public void tearDown() {
		testlayer = null;
	}

	@Test
	public void testInstrument() {
		testlayer.setInstrument((short) 3);
		assertEquals(3, testlayer.getInstrument());
	}
	
	@Test
	public void testVelocity(){
		testlayer.setVelocity((byte) 30);
		assertEquals(30, testlayer.getVelocity());
	}
	
	@Test
	public void testChannel(){
		testlayer.setChannel((byte) 8);
		assertEquals(8, testlayer.getChannel());
	}
	
	@Test (expected=InvalidCoordinatesException.class)
	public void buttonInvalidCoordsTest() throws InvalidCoordinatesException {
		testlayer.updateButton((byte)-1, (byte)0);
	}
	
	@Test
	public void testCol() throws InvalidCoordinatesException{
		testlayer.updateButton((byte)0, (byte)0);
		testlayer.updateButton((byte)0, (byte)2);
		testlayer.updateButton((byte)0, (byte)4);
		testlayer.updateButton((byte)0, (byte)6);
		testlayer.updateButton((byte)0, (byte)8);
		testlayer.updateButton((byte)0, (byte)10);
		testlayer.updateButton((byte)0, (byte)12);
		testlayer.updateButton((byte)0, (byte)14);
		boolean[] col = testlayer.getCol((byte) 0);
		for(int x = 0; x < 16; x++){
			if(x % 2 == 0){
				assert(col[x]);
			} else {
				assert(!col[x]);
			}
		}
	}
	
	@Test
	public void testGrid() throws InvalidCoordinatesException{
		testlayer.updateButton((byte)0, (byte)0);
		testlayer.updateButton((byte)2, (byte)2);
		testlayer.updateButton((byte)4, (byte)4);
		testlayer.updateButton((byte)6, (byte)6);
		testlayer.updateButton((byte)8, (byte)8);
		testlayer.updateButton((byte)10,(byte) 10);
		testlayer.updateButton((byte)12,(byte) 12);
		testlayer.updateButton((byte)14, (byte)14);
		boolean[][] col = testlayer.getGrid();
		for(int x = 0; x < 16; x++){
			if(x % 2 == 0){
				assert(col[x][x]);
			} else {
				assert(!col[x][x]);
			}
		}
	}
	

}
