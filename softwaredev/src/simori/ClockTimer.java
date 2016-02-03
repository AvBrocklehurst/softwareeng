package simori;
import java.util.Timer;
import java.util.TimerTask;

public class ClockTimer implements Runnable{
	private Clock clock;
	private int period;
	private 
	
	ClockTimer(Clock clock, int beatsperminute){
		this.clock = clock;
		period = (1/(beatsperminute/60)) * 1000;
	}
	@Override
	public void run() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() {
			    clock.notify();
			  }
			}, period, period);
	}
	
}
