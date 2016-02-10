package simori.Tests;

<<<<<<< HEAD:softwareeng/src/simori/Tests/TestSuite.java
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

=======

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite to run all our unit tests
 * @author Josh
 * @author Matt
 * @version 2.0.1
 */
>>>>>>> 501428cb076196c94b530b17bd55beb6cfb3a889:softwaredev/src/simori/Tests/TestSuite.java
@RunWith(Suite.class)
@Suite.SuiteClasses({
   TestClock.class,
   TestLayer.class,
   TestMatrixModel.class,
   TestMIDISoundPlayer.class,
   TestPerformanceMode.class,
<<<<<<< HEAD:softwareeng/src/simori/Tests/TestSuite.java
})
public class TestSuite {}  
=======
   TestLed.class,
   TestSimoriGui.class,
   TestSimoriGuiEvents.class,
   TestSimori.class
})
public class TestSuite {}
>>>>>>> 501428cb076196c94b530b17bd55beb6cfb3a889:softwaredev/src/simori/Tests/TestSuite.java
