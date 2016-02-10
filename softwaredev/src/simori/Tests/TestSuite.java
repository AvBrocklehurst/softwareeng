package simori.Tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   TestClock.class,
   TestLayer.class,
   TestMatrixModel.class,
   TestMIDISoundPlayer.class,
   TestPerformanceMode.class,
   TestLed.class
})
public class TestSuite {}  