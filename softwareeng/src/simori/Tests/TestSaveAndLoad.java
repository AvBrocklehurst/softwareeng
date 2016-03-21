package simori.Tests;

import static org.junit.Assert.fail;

import org.junit.Test;

import simori.MatrixModel;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.SaveAndLoad;

/**
 * @author adam
 * @author Jurek
 */
public class TestSaveAndLoad {

	/**
	 * Test method for {@link simori.Modes.SaveAndLoad#save(simori.MatrixModel, java.lang.String)}.
	 */
	@Test
	public void testSave() {
		try {
			MatrixModel model = new MatrixModel(16,16);
			SaveAndLoad.save(model, "test.song");
		} catch (SimoriNonFatalException e) {
			fail();
		}
	}
	
	
	/**
	 * Test method for {@link simori.Modes.SaveAndLoad#save(simori.MatrixModel, java.lang.String)}.
	 * Tests extreme parameters.
	 */
	@Test
	public void testSaveExtreme() {
		try {
			MatrixModel model = new MatrixModel(16,16);
			SaveAndLoad.save(model, "test__ 1231!@~Sadasd2141414nd||QE.song");
		} catch (SimoriNonFatalException e) {
			fail();
		}
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
