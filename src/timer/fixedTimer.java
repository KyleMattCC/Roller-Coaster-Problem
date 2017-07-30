package timer;

import java.util.Timer;
import java.util.TimerTask;


public class fixedTimer {
	
	private int driverChoice;
	private long time;
	private Timer myTimer = new Timer();
	private TimerTask task = new TimerTask(){
		public void run(){
			switch(driverChoice){
			case 1:
				semaphore.Driver.exitProgram();
				break;
			case 2:
				monitor.Driver.exitProgram();
			}
			
		}
	};
	
	public fixedTimer(int choice){
		this.driverChoice = choice;
		time = 10000;
	}
	
	public fixedTimer(int choice, long timeInSeconds){
		this.driverChoice = choice;
		this.time = timeInSeconds * 1000;
	}
	
	public void start(){
		myTimer.schedule(task, time);
	}
}
