package simori.Tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite to run all our unit tests
 * @author Josh
 * @author Matt
 * @version 2.0.1
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestChangerMode.class,
	TestClock.class,
	TestInstrumentNamer.class,
	TestLayer.class,
	TestLed.class,
	TestMatrixModel.class,
	TestMIDISoundPlayer.class,
	TestPerformanceMode.class,
	TestSimori.class,
	TestSimoriGui.class,
	TestSimoriGuiEvents.class

})

public class TestSuite {}  
