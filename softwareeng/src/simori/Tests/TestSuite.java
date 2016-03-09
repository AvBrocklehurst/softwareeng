package simori.Tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import simori.Tests.GuiTests.GuiTestSuite;

/**
 * Test suite to run all our unit tests
 * @author Josh
 * @version 3.0.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	GuiTestSuite.class, //Must be first (see TestGuiProperties)
	TestLayer.class,
	TestMatrixModel.class,
	TestMIDISoundPlayer.class,
	TestPerformanceMode.class,
	TestInstrumentNamer.class,
	TestSimoriGui.class,
	TestModeController.class,
	TestResourceManager.class,
	TestSaveAndLoad.class,
	TestSaveAndLoad.class,
	TestSimoriGui.class,
	TestNoteProcessor.class
})
public class TestSuite {}
