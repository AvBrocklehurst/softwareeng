package simori;

import simori.ChangerMode.Changer;
import simori.ChangerMode.Setting;
import simori.SimoriGui.FunctionButtonEvent;
import simori.SimoriGui.FunctionButtonListener;
import simori.SimoriGui.GridButtonListener;
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
 * @author Jurek
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
	
	public void setInitialGrid() {
		controller.getGui().clearGrid();
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
		case L1 : 
			controller.setMode(new ChangerMode(controller, makeVoiceChanger(), true, true)); 
			break; //above: change mode first boolean is vertical lights, second is horizontal lights
		case L2 : 
			controller.setMode(new ChangerMode(controller, makeVelocityChanger(), true, true));
			break;
		case L3 : 
			controller.setMode(new ChangerMode(controller, makeSpeedChanger(), true, true));
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
		case ON:
			controller.setOn(!controller.isOn());
			break;
		}
	}
	
	public void tickerLight(byte col) throws InvalidCoordinatesException {};
	
	/**
	 * This implementation of the Changer interface changes the current layer
	 * to be displayed on the simori. 
	 * 
	 * @author James
	 * @return Changer
	 * @see ChangerMode.Changer
	 * @version 1.2.1
	 */
	private Changer makeLayerChanger() {
		return new Changer() {
			
			private int selectedLayer;
			
			/**
			 * A method which overrides the interface method in order to
			 * display the selected layer to the LCD screen.
			 * 
			 * @param x
			 * @param y
			 * @author James
			 * @see Changer.getText(), java.lang.String.valueOf()
			 * @version 1.0.0
			 */
			@Override
			public String getText(int x, int y) {
				selectedLayer = y;
				return String.valueOf(y);   //return the selected layer as a String
			}
			
			/**
			 * A method which overrides the interface method in order to
			 * set the current layer to the layer selected.
			 * 
			 * @param controller
			 * @author James
			 * @see Changer.doThingTo(), ModeController.setDisplayLayer()
			 * @version 1.1.0
			 */
			@Override
			public boolean doThingTo(ModeController controller) {
				controller.setDisplayLayer((byte) selectedLayer); //TODO make x and y bytes anyway
				return true; //set current layer
			}

			@Override
			public Setting getCurrentSetting() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
	
	/**
	 * This implementation of the Changer interface changes the column
	 * at which the loop restarts
	 * 
	 * @author Jurek
	 * @version 1.0.0
	 * @return Changer
	 */
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
				controller.getModel().setLoop((byte)selectedColumn);
				return true;
			}

			@Override
			public Setting getCurrentSetting() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
	
	/**
	 * This implementation of the Changer interface changes the current instrument
	 * used for the current layer on the simori.
	 * 
	 * @author James
	 * @return Changer
	 * @see ChangerMode.Changer
	 * @version 1.2.1
	 */
	private Changer makeVoiceChanger(){
		return new Changer(){
			
			private short instrumentNumber;
			
			/**
			 * A method which overrides the interface method in order to
			 * display the selected instrument to the LCD screen.
			 * 
			 * @param x
			 * @param y
			 * @author James
			 * @see Changer.getText(), InstrumentNamer.getInstance(), coordsConverter(), InstrumentNamer.getName()
			 * @version 1.0.1
			 */
			@Override
			public String getText(int x, int y) {
				InstrumentNamer in = InstrumentNamer.getInstance();  //singleton class
				instrumentNumber = coordsConverter(x, y); //translate coordinates to short
				return in.getName(instrumentNumber);
			}
			
			/**
			 * A method which overrides the interface method in order to
			 * set the current instrument to the instrument selected.
			 * 
			 * @param controller
			 * @author James
			 * @see Changer.doThingTo(), ModeController.getModel(), MatrixModel.setInstrument(), Mode.getDisplayLayer()
			 * @version 1.1.0
			 */
			@Override
			public boolean doThingTo(ModeController controller) {
				controller.getModel().setInstrument(getDisplayLayer(), instrumentNumber); 
				return true;
			}

			@Override
			public Setting getCurrentSetting() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
	}
	
	/**
	 * This implementation of the Changer interface changes the current velocity
	 * used for the current layer on the simori.
	 * 
	 * @author James
	 * @return Changer
	 * @see ChangerMode.Changer
	 * @version 1.0.0
	 */
	private Changer makeVelocityChanger(){
		return new Changer(){

			private short selectedVelocity;
			
			/**
			 * A method which overrides the interface method in order to
			 * display the selected velocity to the LCD screen.
			 * 
			 * @param x
			 * @param y
			 * @author James
			 * @see Changer.getText(), coordsConverter(), java.lang.String.valueOf()
			 * @version 1.0.0
			 */
			@Override
			public String getText(int x, int y) {
				selectedVelocity = coordsConverter(x, y);
				return String.valueOf(selectedVelocity);
			}
			
			/**
			 * A method which overrides the interface method in order to
			 * set the current velocity to the velocity selected.
			 * 
			 * @param controller
			 * @author James
			 * @see Changer.doThingTo(), ModeController.getModel(), MatrixModel.setVelocity(), Mode.getDisplayLayer()
			 * @version 1.0.0
			 */
			@Override
			public boolean doThingTo(ModeController controller) {
				controller.getModel().setVelocity(getDisplayLayer(), (byte)selectedVelocity); 
				return true;
			}

			@Override
			public Setting getCurrentSetting() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
	}
	
	/**
	 * This implementation of the Changer interface changes the current tempo
	 * 
	 * @author Jurek
	 * @version 1.0.0
	 * @return Changer
	 */
	private Changer makeSpeedChanger(){
		return new Changer(){
			
			private int selectedTempo;
			
			@Override
			public String getText(int x, int y) {
				if(y==0) {
					selectedTempo = x;
					return String.valueOf(x);
				} else {
					//TODO need to figure how to make it 0-160 exactly. currently it matches the picture example(what with 57 where it was)
					selectedTempo = 15 + 16*(y-1) + x;
					return String.valueOf(selectedTempo);
				}
			}

			@Override
			public boolean doThingTo(ModeController controller) {
				controller.getModel().setBPM((short)selectedTempo);
				return true;
			}

			@Override
			public Setting getCurrentSetting() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
	}
	
	/**
	 * This method, given two integer coordinates of a button press,
	 * translates input into a short representing an instrument to be passed
	 * into appropriate setter methods upon changing mode.
	 * 
	 * @param x
	 * @param y
	 * @author James
	 * @return short
	 * @version 1.1.0
	 */
	private short coordsConverter(int x, int y){
		
		short counter = 1;
		
		while(y != 0){
			y--;
			counter = (short) (counter + 16); //from moving row to row we add 16 to the short, each row is 0-15 buttons
		}
		
		while(x != 0){
			x--;
			counter = (short) (counter + 1); //from moving column we add 1 to the short as each new button is one new instrument
		}
		
		return counter;
		
	}
}
