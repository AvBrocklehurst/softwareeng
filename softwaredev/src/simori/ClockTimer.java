package simori;
import java.util.Timer;
import java.util.TimerTask;

	/**
	 * 
	 * @author Jurek
	 * @version 1.0.1
	 * 
	 */

public class ClockTimer implements Runnable{
	private Thread clock;
	private int period;
	private TimerTask timerTask;
	
	/**
	 * Constructs the ClockTimer
	 * @author Jurek
	 * @version 1.0.1
	 * @param clock Reference to the Clock object
	 * @param beatsperminute
	 */
	ClockTimer(Thread clock, int beatsperminute){
		this.clock = clock;
		period = (1/(beatsperminute/60)) * 1000;
	}
	
	/**
	 * Runs every a set amount of time and notify the Clock to do stuff
	 */
	@Override
	public void run() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(timerTask = new TimerTask() {
			  @Override
			  public void run() {
				  System.out.println("Notifying");
				  Thread.currentThread().interrupt();
			    
			  }
			}, period, period);
	}
	
	/**
	 * Kills the thread
	 * @author Jurek
	 * @version 1.0.2
	 */
	public void off() {
		timerTask.cancel();
	}
}
