/**
 * 
 */
package simori.Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.MatrixModel;
import simori.Modes.NetworkSlave;

/**
 * @author Adam
 *
 */
public class TestNetworkSlave {
	private NetworkSlave ns;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ns = new NetworkSlave(20160, new MatrixModel(16,16));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		ns = null;
	}


}
