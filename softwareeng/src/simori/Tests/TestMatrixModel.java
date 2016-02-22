	package simori.Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.MatrixModel;
import simori.Exceptions.InvalidCoordinatesException;
/**
 * 
 * @author Adam
 *
 */
public class TestMatrixModel {
	MatrixModel model;

	@Before
	public void setUp() {
		model = new MatrixModel(16, 16);
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
	public void testUpdateButton() throws InvalidCoordinatesException{
		model.updateButton((byte) 0, (byte)0, (byte)0);
		assertEquals(true, model.getCol((byte)0)[0]);
	}
	
	@Test
	public void testColumn(){
		model.incrementColumn();
		model.incrementColumn();
		assertEquals(2, model.getCurrentColumn());
	}
	
	@Test
	public void testLoopPoint(){
		model.setLoopPoint((byte) 5);
		assertEquals(5, model.getLoopPoint());
		
	}
	
	@Test
	public void testLoopColumn(){
		model.setLoopPoint((byte) 1);
		model.incrementColumn();
		model.incrementColumn();
		assertEquals(0,model.getCurrentColumn());
	}
	
	@Test
	public void testGetLayers(){
		model.setChannel((byte) 1, (byte)12);
		List<Byte> layers = model.getLayers();
		assertEquals(0, layers.get(0).byteValue());
		assertEquals(1, layers.get(1).byteValue());
	}
	
	@Test
	public void testGrid() throws InvalidCoordinatesException{
		model.updateButton((byte) 0,(byte)0, (byte)0);
		model.updateButton((byte) 0,(byte)2, (byte)2);
		model.updateButton((byte) 0,(byte)4, (byte)4);
		model.updateButton((byte) 0,(byte)6, (byte)6);
		model.updateButton((byte) 0,(byte)8, (byte)8);
		model.updateButton((byte) 0,(byte)10,(byte) 10);
		model.updateButton((byte) 0,(byte)12,(byte) 12);
		model.updateButton((byte) 0,(byte)14, (byte)14);
		boolean[][] col = model.getGrid((byte)0);
		for(int x = 0; x < 16; x++){
			if(x % 2 == 0){
				assert(col[x][x]);
			} else {
				assert(!col[x][x]);
			}
		}
	}
	
	@Test
	public void testSwitchOff(){
		model.setBPM((byte)100);
		model.setLoopPoint((byte)12);
		assertEquals(100,model.getBPM());
		assertEquals(12, model.getLoopPoint());
		model.switchOff();
		assertEquals(88,model.getBPM()); //default values
		assertEquals(15, model.getLoopPoint());
	}
	
	@Test	
	public void testSwitchOn(){
		model.switchOff();
		model.switchOn();
		List<Byte> layers = model.getLayers();
		assertEquals(0, layers.get(0).byteValue());
	}

}
