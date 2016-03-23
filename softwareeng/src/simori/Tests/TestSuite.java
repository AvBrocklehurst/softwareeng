package simori.Tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import simori.Tests.GuiTests.GuiTestSuite;
import simori.Tests.ModeTests.ModeTestSuite;

/**
 * Test suite to run all our unit tests
 * @author Josh
 * @version 4.0.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	
	TestInstrumentNamer.class,
	GuiTestSuite.class,
	ModeTestSuite.class,
	TestLayer.class,
	TestMatrixModel.class,
	TestSimoriGui.class,
	TestModeController.class,
	TestResourceManager.class,
	TestSimoriGui.class,
	TestInstrumentNamer.class,
	TestNoteProcessor.class,
	TestMidiSoundSystem.class,
	TestMIDIMessengerSystem.class,
	TestAudioFeedbackSystem.class,
	TestSimoriSoundSystem.class,
	TestExceptionManager.class,
	TestMain.class
	
})
public class TestSuite {}
