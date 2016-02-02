package simori;

	/**
	 * 
	 * @author Jurek
	 * @version 1.0.2
	 *
	 */

public class Clock implements Runnable {
		private boolean running = true;
		private MatrixModel model;
		private int currentColumn;
		
		/**
		 * Constructor for the class
		 * @author Jurek
		 * @version 1.0.2
		 * @param model Holds the reference to the MatrixModel
		 */
		Clock(MatrixModel model){
			this.model = model;
		}
	
		/**
		 * The thread method for running the clock
		 * @author Jurek
		 * @version 1.0.1
		 */
		@Override
		public void run() {
			while(running){
				//TODO GET A LIST OF LISTS FOR THE CURRENT COLUMN FROM MatrixModel
				//TODO SEND LIST OF LISTS TO MidiPlayer
				//TODO WAIT A SET OF TIME == THE TEMPO
				//TODO CHECK IF END OF COLUMN LOOP FOUND, IF SO currentColumn = 0, ELSE currentColumn += 1
			}
			//prepare for running the thread again
			running = true;
		}
		
		/**
		 * Stops the execution of the thread
		 * @author Jurek
		 * @version 1.0.1
		 */
		public void stop() {
			running = false;
		}
	
	
	
}
