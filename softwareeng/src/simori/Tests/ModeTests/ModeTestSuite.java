package simori.Tests.ModeTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite which runs tests from the ModeTests package
 * @author Matt
 * @version 1.0.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestChangerMode.class,
	TestChangerModeFactory.class,
	//TestMasterSlaveMode.class,
	TestMode.class,
	TestNetworkMaster.class,
	TestPerformanceMode.class,
	TestQwertyKeyboard.class,
	TestSaveAndLoad.class,
	//TestShopBoyMode.class,
	TestTextEntry.class
})
public class ModeTestSuite {}
