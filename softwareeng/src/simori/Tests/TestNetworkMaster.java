<<<<<<< HEAD
/**
 * 
 */
=======
>>>>>>> 7a3ef3cb2a09c4ec16c8758fa30e6a852bb5dcfe
package simori.Tests;

import static org.junit.Assert.*;

<<<<<<< HEAD
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simori.MatrixModel;
import simori.Modes.NetworkMaster;


/**
 * 
 * @author Adam
 *
 */
public class TestNetworkMaster {
	private NetworkMaster nw;

	NetworkMaster nw; 

	@Before
	public void setUp() throws Exception {
		nw = new NetworkMaster(20160, new MatrixModel(16,16));
	}

	@After
	public void tearDown() throws Exception {
		nw = null;
	}

}
