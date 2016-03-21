package simori.Tests;

import simori.Modes.ChangerMode;
import simori.Modes.ChangerModeFactory;
import simori.Modes.ChangerMode.Changer;

/**
 * A class to Mock ChangerModeFactory, allowing the changer inner methods to
 * be tested.
 * 
 * @author James
 * @version 1.0.0
 * @see ChangerMode.java
 *
 */
public class MockChangerModeFactory extends ChangerModeFactory {
	
	public Changer makeLayerChanger(MockModeController controller){
		return super.makeLayerChanger(controller);
	}
	
	public Changer makePointChanger(MockModeController controller){
		return super.makePointChanger(controller);
	}
	
	public Changer makeVoiceChanger(MockModeController controller){
		return super.makeVoiceChanger(controller);
	}
	
	public Changer makeVelocityChanger(MockModeController controller){
		return super.makeVelocityChanger(controller);
	}
	
	public Changer makeSpeedChanger(MockModeController controller){
		return super.makeSpeedChanger(controller);
	}
}
