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
	GuiTestSuite.class,
	TestLayer.class,
	TestMatrixModel.class,
	TestMIDISoundPlayer.class,
	TestPerformanceMode.class,
	TestInstrumentNamer.class,
	TestSimoriGui.class,
	TestModeController.class,
	TestResourceManager.class,
	TestSaveAndLoad.class,
	TestSimori.class,
	TestSaveAndLoad.class,
	TestSimoriGui.class
	/*, TestNoteProcessor.class TODO add back in when it works */
})

public class TestSuite {}
