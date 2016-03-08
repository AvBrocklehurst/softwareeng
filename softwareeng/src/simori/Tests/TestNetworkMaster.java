/**
 * 
 */
package simori.Tests;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.model.TestTimedOutException;

import simori.MatrixModel;
import simori.Exceptions.InvalidPortException;
import simori.Modes.NetworkMaster;

/**
 * @author Adam
 *
 */
public class TestNetworkMaster {
	private NetworkMaster nw;

	
	/**
	 * @throws UnknownHostException 
	 * @throws InvalidPortException 
	 */
	@Before
	public void setUp() throws UnknownHostException, InvalidPortException {
		nw = new NetworkMaster(20160, new MatrixModel(16,16));
	}

	/**
	 */
	@After  
	public void tearDown() {
		nw = null;
	}

	/**
	 * Test method for {@link simori.Modes.NetworkMaster#NetworkMaster(int, simori.MatrixModel)}.
	 * @throws InvalidPortException 
	 * @throws UnknownHostException 
	 */
	@Test (expected=InvalidPortException.class )
	public final void testNetworkMaster() throws UnknownHostException, InvalidPortException {
		nw = new NetworkMaster(-3, new MatrixModel(16,16));
	}

	/**
	 * Test method for {@link simori.Modes.NetworkMaster#findSlave()}.
	 */
	@Test (timeout=40000, expected=TestTimedOutException.class)
	public final void testFindSlave() {
		nw.findSlave();
		
	}

}
