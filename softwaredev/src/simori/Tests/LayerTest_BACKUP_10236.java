<<<<<<< HEAD
package simori.Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.InvalidCoordinatesException;
import simori.Layer;

public class LayerTest {
	Layer testlayer;

	@Before
	public void setUp() throws Exception {
		testlayer = new Layer();
	}

	@After
	public void tearDown() throws Exception {
		testlayer = null;
	}

	@Test
	public void testInstrument() {
		testlayer.setInstrument((short) 3);
		assertEquals(3, testlayer.getInstrument());
	}
	
	@Test (expected=InvalidCoordinatesException.class)
	public void buttonTest() throws InvalidCoordinatesException {
		testlayer.updateButton(-1, 0);
	}
	
	@Test
	public void testCol() throws InvalidCoordinatesException{
		testlayer.updateButton(0, 0);
		testlayer.updateButton(0, 2);
		testlayer.updateButton(0, 4);
		testlayer.updateButton(0, 6);
		testlayer.updateButton(0, 8);
		testlayer.updateButton(0, 10);
		testlayer.updateButton(0, 12);
		testlayer.updateButton(0, 14);
		boolean[] col = testlayer.getCol(0);
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
		testlayer.updateButton(0, 0);
		testlayer.updateButton(2, 2);
		testlayer.updateButton(4, 4);
		testlayer.updateButton(6, 6);
		testlayer.updateButton(8, 8);
		testlayer.updateButton(10, 10);
		testlayer.updateButton(12, 12);
		testlayer.updateButton(14, 14);
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
=======
package simori.Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.InvalidCoordinatesException;
import simori.Layer;

public class LayerTest {
	Layer testlayer;

	@Before
	public void setUp() throws Exception {
		testlayer = new Layer();
	}

	@After
	public void tearDown() throws Exception {
		testlayer = null;
	}

	@Test
	public void testInstrument() {
		testlayer.setInstrument(3);
		assertEquals(3, testlayer.getInstrument());
	}
	
	@Test (expected=InvalidCoordinatesException.class)
	public void buttonTest() throws InvalidCoordinatesException {
		testlayer.updateButton(-1, 0);
	}
	
	@Test
	public void testCol() throws InvalidCoordinatesException{
		testlayer.updateButton(0, 0);
		testlayer.updateButton(0, 2);
		testlayer.updateButton(0, 4);
		testlayer.updateButton(0, 6);
		testlayer.updateButton(0, 8);
		testlayer.updateButton(0, 10);
		testlayer.updateButton(0, 12);
		testlayer.updateButton(0, 14);
		boolean[] col = testlayer.getCol(0);
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
		testlayer.updateButton(0, 0);
		testlayer.updateButton(2, 2);
		testlayer.updateButton(4, 4);
		testlayer.updateButton(6, 6);
		testlayer.updateButton(8, 8);
		testlayer.updateButton(10, 10);
		testlayer.updateButton(12, 12);
		testlayer.updateButton(14, 14);
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
>>>>>>> cdb71e08450ab1bb69722d9d76ffae4a74bf1975
