package simori.Tests;

import static org.junit.Assert.*;

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
