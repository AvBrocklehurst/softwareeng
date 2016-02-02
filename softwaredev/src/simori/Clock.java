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
