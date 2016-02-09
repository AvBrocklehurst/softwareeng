package simori.Tests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import simori.MatrixModel;
import simori.PerformanceMode;
import simori.Simori;
import simori.Exceptions.InvalidCoordinatesException;



/**
 * The class for testing Performance Mode.
 * 
 * @author James
 * @version 1.0.0
 * @see simori.PerformanceMode
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPerformanceMode {
	
	PerformanceMode testpm;
	Simori testsimori;
	MockGridButtonEvent mockgb;
	MatrixModel testmodel;
	
	@Before
	public void setUp(){
		
		testsimori = new Simori();
		testmodel = new MatrixModel();
		testsimori.setModel(testmodel);
		testpm = new PerformanceMode(testsimori, 0, 0, (byte)0);
		mockgb = new MockGridButtonEvent(3, 2);
	
	}
	
	@After
	public void tearDown(){
		
		testsimori = null;
		testmodel = null;
		testpm = null;
		mockgb = null;
		
	}
	
	@Test
	public void test_onGridButtonPress() throws InvalidCoordinatesException{
		
		boolean gridcoords = testpm.getModifiedGrid()[2][3];
		testpm.onGridButtonPress(mockgb);
		boolean changedgridcoords = testpm.getModifiedGrid()[2][3];
		assertEquals("The grid button was not inverted!", true, changedgridcoords);
		
		
	}
	
	@Test
	public void test_onGridButtonPress_notInverted() throws InvalidCoordinatesException{
		
		boolean gridcoords = testpm.getModifiedGrid()[5][6];
		testpm.onGridButtonPress(mockgb);
		boolean changedgridcoords = testpm.getModifiedGrid()[5][6];
		assertEquals("The grid button should not be inverted!", false, changedgridcoords);
		
	}
	

	/*@Test
	public void test_tickerLight() throws InvalidCoordinatesException{
		
		boolean[][] grid = testpm.getModifiedGrid();
	
		for(int i = 0 ; i<grid)
		
		testpm.tickerLight((byte)0); 
		assertEquals("The values are grid index grid[5][0] were not inverted", true, grid[5][0]);
	}
	
	@Test
	public void test_makeGridCopy(){
		
	}*/
}
