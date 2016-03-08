package simori.Tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import simori.Tests.GuiTests.TestButton;
import simori.Tests.GuiTests.TestGuiProperties;
import simori.Tests.GuiTests.TestLcd;
import simori.Tests.GuiTests.TestLed;
import simori.Tests.GuiTests.TestLedPanel;
import simori.Tests.GuiTests.TestOnPressListenerMaker;
import simori.Tests.GuiTests.TestPressableCircle;
import simori.Tests.GuiTests.TestSimoriEdgeBar;

/**
 * Test suite to run all our unit tests
 * @author Josh
 * @author Matt
 * @version 2.0.1
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
   /*TestNoteProcessor.class,*/ //TODO add back in when it works
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
   TestGuiProperties.class,
   TestLcd.class,
   TestLed.class,
   TestButton.class,
   TestLedPanel.class,
   TestOnPressListenerMaker.class,
   TestPressableCircle.class,
   TestSimoriEdgeBar.class,
   TestSaveAndLoad.class,
   TestSimoriGui.class  
})

public class TestSuite {}
