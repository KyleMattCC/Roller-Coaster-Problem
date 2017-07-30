package timer;

import java.util.Timer;
import java.util.TimerTask;


public class fixedTimer {
	
	private int driverChoice;
	private long time;
	private long currIterations = 0;
	private long numIterations;
	private Timer myTimer = new Timer();
	private TimerTask task = new TimerTask(){
		public void run(){
			switch(driverChoice){
			case 1:
				semaphore.Driver.exitProgram();
				break;
				
			case 2:
				monitor.Driver.exitProgram();
				break;
				
			case 3:
				currIterations++;
				if(currIterations < numIterations)
					semaphore.Driver.createPassengers();

				else{
					semaphore.Driver.exitProgram();
					this.cancel();
				}
				break;
				
			case 4:
				currIterations++;
				if(currIterations < numIterations)
					monitor.Driver.createPassengers();
				
				else{
					monitor.Driver.exitProgram();
					this.cancel();
				}
				break;
			}
			
		}
	};
	
	public fixedTimer(int choice){
		this.driverChoice = choice;
		time = 30000;
	}
	
	public fixedTimer(int choice, long timeInSeconds){
		this.driverChoice = choice;
		this.time = timeInSeconds * 1000;
	}
	
	public void start(){
		if(driverChoice == 1 || driverChoice == 2)
			myTimer.schedule(task, time);
		else{
			myTimer.scheduleAtFixedRate(task, 1000, 1000);
			numIterations = time/1000;
		}
	}
}
