/**
 * 
 */
package simori.Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.MatrixModel;
import simori.Modes.SaveAndLoad;

/**
 * @author adam
 *
 */
public class TestSaveAndLoad {

	/**
	 * Test method for {@link simori.Modes.SaveAndLoad#save(simori.MatrixModel, java.lang.String)}.
	 */
	@Test
	public void testSave() {
		MatrixModel model = new MatrixModel(16,16);
		SaveAndLoad.save(model, "test.song");
	}
	
	
	/**
	 * Test method for {@link simori.Modes.SaveAndLoad#save(simori.MatrixModel, java.lang.String)}.
	 * Tests extreme parameters.
	 */
	@Test
	public void testSaveExtreme() {
		MatrixModel model = new MatrixModel(16,16);
		SaveAndLoad.save(model, "test__ 1231!@~Sadasd2141414nd||QE.song");
	}
	
	/**
	 * Test method for {@link simori.Modes.SaveAndLoad#load(simori.MatrixModel, java.lang.String)}.
	 */
	@Test
	public void testLoad() {
		MatrixModel model = new MatrixModel(16,16);
		SaveAndLoad.load(model, "test.song");
	}
	
	/**
	 * Test method for {@link simori.Modes.SaveAndLoad#load(simori.MatrixModel, java.lang.String)}.
	 * Tests extreme parameters.
	 */
	@Test
	public void testLoadExtreme() {
		MatrixModel model = new MatrixModel(16,16);
		SaveAndLoad.load(model, "test__ 1231!@~Sadasd2141414nd||QE.song");
	}

}