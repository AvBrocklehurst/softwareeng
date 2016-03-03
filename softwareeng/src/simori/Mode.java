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
 * @author Adam
 * @version 1.1.0
 */
public abstract class Mode implements FunctionButtonListener, GridButtonListener {
	
	private ModeController controller;
	private char[] symbols = {
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N',
			'O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b',
			'c','d','e','f','g','h','i','j','k','l','m','n','o','p',
			'q','r','s','t','u','v','w','x','y','z','_','-','+','=',
			'!','£','$','%','^','(',')','{','}','[',']',';','@','#',
			'~','.', ','
		};
	
	
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
		getGui().clearGrid();
		getGui().setText(null);
	}
	
	/**
	 * Gets the function button pressed and the source Gui and then
	 * changes the current mode based on a specified FunctionButton.
	 * 
	 * @param e  A FunctionButtonEvent representing the press of a function button to switch
	 * mode or switch it on
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
			controller.setMode(new ChangerMode(controller, saveConfig(), true, true));
			break;
		case R3 :
			controller.setMode(new ChangerMode(controller, loadConfig(), true, true));
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
			
			private byte selectedLayer;
			
			/**
			 * A method which overrides the interface method in order to
			 * display the selected layer to the LCD screen.
			 * 
			 * @param x  x coordinate of a button press
			 * @param y  y coordinate of a button press
			 * @author James
			 * @see Changer.getText(), java.lang.String.valueOf()
			 * @version 1.0.0
			 */
			@Override
			public String getText(Setting s) {
				selectedLayer = s.y;
				return String.valueOf(s.y);   //return the selected layer as a String
			}
			
			/**
			 * A method which overrides the interface method in order to
			 * set the current layer to the layer selected.
			 * 
			 * @param controller   The current ModeController
			 * @author James
			 * @see Changer.doThingTo(), ModeController.setDisplayLayer()
			 * @version 1.1.0
			 */
			@Override
			public boolean doThingTo(ModeController controller) {
				controller.setDisplayLayer(selectedLayer);
				return true; //set current layer
			}

			@Override
			public Setting getCurrentSetting() {
				return new Setting(null, controller.getDisplayLayer());
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
			public String getText(Setting s) {
				selectedColumn = s.x;
				return String.valueOf(s.x);
			}
			
			@Override
			public boolean doThingTo(ModeController controller) {
				controller.getModel().setLoopPoint((byte)selectedColumn);
				return true;
			}

			@Override
			public Setting getCurrentSetting() {
				return new Setting(controller.getModel().getLoopPoint(), null);
			}
		};
	}
	
	/**
	 * This implementation of the Changer interface changes the current instrument
	 * used for the current layer on the simori.
	 * 
	 * @author James
	 * @author Adam
	 * @return Changer
	 * @see ChangerMode.Changer
	 * @version 1.2.1
	 */
	private Changer makeVoiceChanger() {
		return new Changer() {
			
			private Short instrumentNumber;
			
			/**
			 * A method which overrides the interface method in order to
			 * display the selected instrument to the LCD screen.
			 * 
			 * @param s  coordinates of the button press
			 * @author James
			 * @author Adam
			 * @see Changer.getText(), InstrumentNamer.getInstance(), coordsConverter(), InstrumentNamer.getName()
			 * @version 1.0.1
			 */
			@Override
			public String getText(Setting s) {
				InstrumentNamer in = InstrumentNamer.getInstance();     //singleton class
				instrumentNumber = coordsConverter(s.x, s.y); //translate coordinates to short
				instrumentNumber = (instrumentNumber.shortValue() > 189 ? null : instrumentNumber);
				return (instrumentNumber == null ? null : in.getName(instrumentNumber));
			}
			
			/**
			 * A method which overrides the interface method in order to
			 * set the current instrument to the instrument selected.
			 * 
			 * @param controller  The current ModeController
			 * @author James
			 * @author Adam
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
				//TODO inverse coordsconvert instrumentNumber back into an (x, y) location
				//return new Setting(x, y);
				return null;
			}
		};
	}
	
	/**
	 * This implementation of the Changer interface changes the current velocity
	 * used for the current layer on the simori.
	 * 
	 * @author James
	 * @author Adam
	 * @return Changer
	 * @see ChangerMode.Changer
	 * @version 1.0.0
	 */
	private Changer makeVelocityChanger(){
		return new Changer(){

			private Short selectedVelocity;
			
			/**
			 * A method which overrides the interface method in order to
			 * display the selected velocity to the LCD screen.
			 * 
			 * @param s  coordinates of the button press
			 * @author James
			 * @author Adam
			 * @see Changer.getText(), coordsConverter(), java.lang.String.valueOf()
			 * @version 1.0.0
			 */
			@Override
			public String getText(Setting s) {
				short cords = coordsConverter(s.x, s.y);
				selectedVelocity = (cords > 127 ? null : cords);
				return (selectedVelocity == null ? null : String.valueOf(selectedVelocity));
			}
			
			/**
			 * A method which overrides the interface method in order to
			 * set the current velocity to the velocity selected.
			 * 
			 * @param controller  The current ModeController
			 * @author James
			 * @author Adam
			 * @see Changer.doThingTo(), ModeController.getModel(), MatrixModel.setVelocity(), Mode.getDisplayLayer()
			 * @version 1.0.0
			 */
			@Override
			public boolean doThingTo(ModeController controller) {
				if(selectedVelocity == null){
					return false;
				}
				controller.getModel().setVelocity(getDisplayLayer(), selectedVelocity.byteValue()); 
				return true;
			}

			@Override
			public Setting getCurrentSetting() {
				return null; //TODO convert back into (x, y) for initial display
			}
		};
	}
	
	/**
	 * This implementation of the Changer interface changes the current tempo
	 * 
	 * @author Jurek
	 * @author Adam
	 * @version 1.0.0
	 * @return Changer
	 */
	private Changer makeSpeedChanger(){
		return new Changer(){
			
			private Short selectedTempo;
			
			/**
			 * @author Jurek
			 * @author Adam
			 */
			@Override
			public String getText(Setting s) {
				if(s.y==0) {
					selectedTempo = (short) s.x;
					return String.valueOf(s.x);
				} else {
					selectedTempo = (short) (15 + 16*(s.y-1) + s.x);
					selectedTempo = (selectedTempo.shortValue() < (short)161 ? selectedTempo : null);
					return (selectedTempo == null ? null : String.valueOf(selectedTempo));
				}
			}
			
			/**
			 * @author Jurek
			 * @author Adam
			 */
			@Override
			public boolean doThingTo(ModeController controller) {
				if(selectedTempo == null){
					return false;
				}
				controller.getModel().setBPM((short)selectedTempo);
				return true;
			}

			@Override
			public Setting getCurrentSetting() {
				return null; //TODO convert back into coords for initial display
			}
		};
	}
	
	private Changer saveConfig(){
		return new Changer(){

			private String letters = "";
				
			@Override
			public String getText(Setting s) {
				short coords = coordsConverter(s.x, s.y);
				
				if(coords <= 73){
					char letter = symbols[coords-1];
					letters += letter;
				}
				
				else if(coords == 241){
					letters = letters.substring(0, letters.length()-1);
				}
				
				else{
					letters += "";
				}
				
				return letters;
			}

			@Override
			public boolean doThingTo(ModeController controller) {
				if(letters == null){
					return false;
				}
				
				letters += ".song";
				SaveAndLoad.save(controller.getModel(), letters);
				return true;
			}

			@Override
			public Setting getCurrentSetting() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
		
	}
	
	private Changer loadConfig(){
		return new Changer(){
			
			private String letters = "";

			@Override
			public String getText(Setting s) {
				short coords = coordsConverter(s.x, s.y);
				
				if(coords <= 73){
					char letter = symbols[coords-1];
					letters += letter;
				}
				
				else if(coords == 241){
					letters = letters.substring(0, letters.length()-1);
				}
				
				else{
					letters += "";
				}
				
				return letters;
			}

			@Override
			public boolean doThingTo(ModeController controller) {
				if(letters == null){
					return false;
				}
				
				letters += ".song";
				SaveAndLoad.load(controller.getModel(), letters);
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
	 * @param x  x coordinates of the button press
	 * @param y  y coordinates of the button press
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
