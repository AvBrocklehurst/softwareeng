package simori.Modes;

import simori.FunctionButton;
import simori.InstrumentNamer;
import simori.ModeController;
import simori.Exceptions.SimoriNonFatalException;
import simori.Modes.ChangerMode.Changer;
import simori.Modes.ChangerMode.Setting;

/**
 * The ChangerModeFactory provides a centralised method which defines which mode to
 * change to on press of a Function button. The methods which facilitate the change and
 * send data to the lcd are also included.
 * 
 *@author James
 *@version 1.3.1
 */

public class ChangerModeFactory {
	
	public static ChangerMode getChanger(FunctionButton fb, ModeController controller){
		switch(fb) {
		case L1 : 
			return new ChangerMode(controller, makeVoiceChanger(controller), true, true); //appropriate mode
		case L2 : 
			return new ChangerMode(controller, makeVelocityChanger(controller), true, true);
		case L3 : 
			return new ChangerMode(controller, makeSpeedChanger(controller), true, true);
		case L4 : 
			return new ChangerMode(controller, makePointChanger(controller), true, false);
		case R1 :
			return new ChangerMode(controller, makeLayerChanger(controller), false, true);
		case R2 :
			return new ChangerMode(controller, SaveAndLoad.makeSaveChanger(controller), false, false);
		case R3 :
			return new ChangerMode(controller, SaveAndLoad.makeLoadChanger(controller), false, false);
		default: 
			return null;
		}
	}
	
	/**
	 * This implementation of the Changer interface changes the current layer
	 * to be displayed on the simori. 
	 * 
	 * @author James
	 * @return Changer
	 * @see ChangerMode.Changer
	 * @version 1.2.1
	 */
	private static Changer makeLayerChanger(final ModeController controller) {
		return new Changer() {
			
			private byte selectedLayer;    //the layer to change to
			
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
				controller.happySound();
				return true; //set current layer
			}
			
			/**
			 * Displays the current setting on entering the different mode.
			 * 
			 * @author James
			 * @return Setting
			 * @version 1.1.0
			 * @see Setting, ModeController.getDisplayLayer()
			 */
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
	private static Changer makePointChanger(final ModeController controller) {
		return new Changer() {
			
			private int selectedColumn;   //the column to loop to
			
			@Override
			public String getText(Setting s) {
				selectedColumn = s.x;
				return String.valueOf(s.x);
			}
			
			@Override
			public boolean doThingTo(ModeController controller) {
				controller.getModel().setLoopPoint((byte)selectedColumn);
				controller.happySound();
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
	private static Changer makeVoiceChanger(final ModeController controller) {
		return new Changer() {
			
			private Short instrumNum;  //the instrument to change to
			
			/**
			 * A method which overrides the interface method in order to
			 * display the selected instrument to the LCD screen.
			 * 
			 * @param s  coordinates of the button press
			 * @author James
			 * @author Adam
			 * @see Changer.getText(), InstrumentNamer.getInstance(), coordsConverter(), InstrumentNamer.getName()
			 * @version 1.0.1
			 * @throws SimoriNonFatalException 
			 */
			@Override
			public String getText(Setting s) throws SimoriNonFatalException {
				InstrumentNamer in = InstrumentNamer.getInstance();
				instrumNum = coordsConverter(s.x, s.y); //translate coordinates to short
				String name = in.getName(instrumNum);
				if (name == null) instrumNum = null;
				return name;
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
				if (instrumNum == null) return false;
				controller.getModel().setInstrument(controller.getDisplayLayer(), instrumNum); 
				controller.happySound();
				return true;
			}
			
			/**
			 * Displays the current setting on entering the different mode.
			 * 
			 * @author James
			 * @return Setting
			 * @version 1.1.0
			 * @see Setting, ModeController.getDisplayLayer(), ModeController.getModel(), MatrixModel.getInstrument(), convertBack()
			 */
			@Override
			public Setting getCurrentSetting() {
				short instrumentNumber = controller.getModel().getInstrument(controller.getDisplayLayer()); //get instrument as this method is called before others
				byte x = convertBack(instrumentNumber)[0]; //access the Array
				byte y = convertBack(instrumentNumber)[1];
				return new Setting(x, y);
				
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
	private static Changer makeVelocityChanger(final ModeController controller){
		return new Changer(){

			private Short selectedVelocity;   //the velocity to change to
			
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
				controller.getModel().setVelocity(controller.getDisplayLayer(), selectedVelocity.byteValue()); 
				controller.happySound();
				return true;
			}
			
			/**
			 * Displays the current setting on entering the different mode.
			 * 
			 * @author James
			 * @return Setting
			 * @version 1.1.0
			 * @see Setting, ModeController.getDisplayLayer(), ModeController.getModel(), MatrixModel.getInstrument(), convertBack()
			 */
			@Override
			public Setting getCurrentSetting() {
				short selectedVelocity = controller.getModel().getVelocity(controller.getDisplayLayer());
				byte x = convertBack(selectedVelocity)[0];
				byte y = convertBack(selectedVelocity)[1];
				return new Setting(x, y);
			}
		};
	}
	
	/**
	 * This implementation of the Changer interface changes the current tempo
	 * 
	 * @author Jurek
	 * @version 1.0.1
	 * @return Changer
	 */
	private static Changer makeSpeedChanger(final ModeController controller){
		return new Changer(){
			
			private Short selectedTempo;   //the speed of the ticker in BPM
			
			/**
			 * @author Jurek
			 */
			@Override
			public String getText(Setting s) {
				selectedTempo = coordsConverter(s.x, s.y);
				selectedTempo = (selectedTempo < 161 ? selectedTempo : null);
				return selectedTempo == null ? null : String.valueOf(selectedTempo);
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
				controller.happySound();
				return true;
			}
			
			/**
			 * @author Jurek
			 */
			@Override
			public Setting getCurrentSetting() {
				short tempo = controller.getModel().getBPM();
				byte[] point = convertBack(tempo);
				return new Setting(point[0], point[1]);
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
	private static short coordsConverter(int x, int y){
		short counter = 1;  //the short eventually output when incremented
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
	
	/**
	 * A method to convert a short value (typically an instrument or velocity)
	 * into a respective pair of x and y coordinates of a grid press.
	 * This is required for the getCurrentSetting() method in the Changer
	 * implementations.
	 * 
	 * @author James
	 * @param s  A short to convert into respective x and y coordinates
	 * @return byte[]
	 * @version 1.1.0
	 */
	private static byte[] convertBack(short s){
		byte x = 0;  //coordinates
		byte y = 0;
		s--;  //account for margin
		while(!(s % 16 == 0)){    //subtract while the short is not divisible by 16
			s = (short) (s - 1);
			x++;
		}
		while(s != 0){       //subtract to 0
			s = (short) (s - 16);
			y++;
		}
		return new byte[]{x,y}; 
	}
}