package simori.Tests;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.startsWith;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import simori.MatrixModel;
import simori.PerformanceMode;
import simori.Simori;
import simori.Exceptions.InvalidCoordinatesException;
import simori.SwingGui.SimoriGui;



/**
 * The class for testing Performance Mode
 * 
 * The Mode class itself is abstract and cannot be instantiated,
 * therefore it is tested through its implementation classes.
 * 
 * Coverage = 100%
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
	SimoriGui testgui;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setUp(){
		
		testsimori = new Simori();
		testmodel = new MatrixModel();
		testsimori.setModel(testmodel);
		testpm = new PerformanceMode(testsimori, 0, 0, (byte)0);
		mockgb = new MockGridButtonEvent(3, 2);
		testsimori.setGui(testgui);
	
	}
	
	@After
	public void tearDown(){
		
		testsimori = null;
		testmodel = null;
		testpm = null;
		mockgb = null;
		testgui = null;
		
	}
	
	@Test
	public void test_onGridButtonPress() throws InvalidCoordinatesException{
		
		testpm.onGridButtonPress(mockgb);
		boolean changedgridcoords = testpm.getModifiedGrid()[2][3];
		assertEquals("The grid button was not inverted!", true, changedgridcoords);
		
		
	}
	
	@Test
	public void test_onGridButtonPress_false() throws InvalidCoordinatesException{
		
		testpm.onGridButtonPress(mockgb); //invert to true
		testpm.onGridButtonPress(mockgb); //invert to false
		boolean changedgridcoords = testpm.getModifiedGrid()[2][3];
		assertEquals("The grid button was not inverted back to false!", false, changedgridcoords);
	}
	
	@Test
	public void test_onGridButtonPress_notInverted() throws InvalidCoordinatesException{
		
		testpm.onGridButtonPress(mockgb);
		boolean changedgridcoords = testpm.getModifiedGrid()[5][6];
		assertEquals("The grid button should not be inverted!", false, changedgridcoords);
		
	}
	
	@Test
	public void test_tickerLight() throws InvalidCoordinatesException{
		
		testpm.tickerLight((byte)0); 
		boolean tickeredgridcoords = testpm.getModifiedGrid()[5][0];
		assertEquals("The values are grid index grid[5][0] were not set to true by the ticker", true, tickeredgridcoords);
	}
	
	@Test
	public void test_makeGridCopy(){
		
		boolean[][] initialgrid = testpm.getModifiedGrid();
		testpm.makeGridCopy((byte)0);
		boolean[][] finalgrid = testpm.getModifiedGrid();
		assertEquals("The grid was not copied correctly!", false, finalgrid[2][3]);
		
	}
}
