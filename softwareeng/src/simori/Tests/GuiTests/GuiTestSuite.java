package simori.Tests.GuiTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import simori.Tests.GuiTests.TestGuiProperties;
import simori.Tests.GuiTests.TestLcd;
import simori.Tests.GuiTests.TestLed;
import simori.Tests.GuiTests.TestLedPanel;
import simori.Tests.GuiTests.TestOnPressListenerMaker;
import simori.Tests.GuiTests.TestPressableCircle;
import simori.Tests.GuiTests.TestSimoriEdgeBar;

/**
 * Test suite which runs tests from the GuiTests package
 * @author Matt
 * @version 1.0.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestGuiProperties.class, // Must be first (see TestGuiProperties)
	TestLcd.class,
	TestLed.class,
	TestLedPanel.class,
	TestOnPressListenerMaker.class,
	TestPressableCircle.class,
	TestSimoriEdgeBar.class,
	TestSimoriJFrame.class,
	TestSimoriPanel.class
})
public class GuiTestSuite {}
