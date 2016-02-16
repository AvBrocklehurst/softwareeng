package simori;

import simori.ChangerMode.Changer;
import simori.SimoriGuiEvents.FunctionButtonEvent;
import simori.SimoriGuiEvents.FunctionButtonListener;
import simori.SimoriGuiEvents.GridButtonListener;
import simori.Exceptions.InvalidCoordinatesException;

/**
 * An abstract class defining methods for general
 * use in the Mode subclasses. Mode handles the logic
 * of a given Function Button press.
 * 
 * Mode is an abstract class and so is tested through its concrete
 * implementations.
 * 
 * @author James
 * @version 1.1.0
 */
public abstract class Mode implements FunctionButtonListener, GridButtonListener {
	
	private ModeController controller;
	
	public Mode(ModeController controller){
		this.controller = controller;
	}
	
	protected ModeController getModeController() {
		return controller;
	}
	
	protected SimoriGui getGui() {
		return controller.getGui();
	}
	
	protected MatrixModel getModel() {
		return controller.getModel();
	}
	
	protected byte getDisplayLayer() {
		return controller.getDisplayLayer();
	}
	
	/**
	 * Gets the function button pressed and the source Gui and then
	 * changes the current mode based on a specified FunctionButton.
	 * 
	 * @author James
	 * @version 1.0.1
	 * @see FunctionButton.getFunctionButton(), SimoriGui.getSource(), SimoriGui.setMode()
	 */
	public void onFunctionButtonPress(FunctionButtonEvent e){
		switch (e.getFunctionButton()) {
		case L1 : //TODO(next sprint) mode to change voice
			break;
		case L2 : //TODO(next sprint) mode to change velocity
			break;
		case L3 : //TODO(next sprint) mode to loop speed
			break;
		case L4 : 
			controller.setMode(new ChangerMode(controller, makePointChanger(), true, false));
			break;
		case R1 :
			controller.setMode(new ChangerMode(controller, makeLayerChanger(), false, true));
			break;
		case R2 :
			//TODO(next sprint) mode to save configuration mode
			break;
		case R3 :
			//TODO(next sprint) mode to load configuration mode
			break;
		case R4 :
			//TODO(next sprint) mode to Master/Slave mode
			break;
		case OK:
			controller.setMode(new PerformanceMode(controller));
			break;
		case POWER:
			controller.setOn(!controller.isOn());
			break;
		}
	}
	
	public void tickerLight(byte col) throws InvalidCoordinatesException {};
	
	private Changer makeLayerChanger() {
		return new Changer() {
			
			private int selectedLayer;
			
			@Override
			public String getText(int x, int y) {
				selectedLayer = y;
				return String.valueOf(y);
			}
			
			@Override
			public boolean doThingTo(ModeController controller) {
				controller.setDisplayLayer((byte) selectedLayer); //TODO make x and y bytes anyway
				return true;
			}
		};
	}
	
	private Changer makePointChanger() {
		return new Changer() {
			
			private int selectedColumn;
			
			@Override
			public String getText(int x, int y) {
				selectedColumn = x;
				return String.valueOf(x);
			}
			
			@Override
			public boolean doThingTo(ModeController controller) {
				//TODO simori.getModel().setLoop(simori.getDisplayLayer(), selectedColumn);
				return true;
			}
		};
	}
}
