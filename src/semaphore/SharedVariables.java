package semaphore;

import java.util.concurrent.Semaphore;

public class SharedVariables {
	protected static volatile int numSeat;
	protected static volatile int numBoarded = 0;
	
	protected static Semaphore availableSeat; //changed numberofSeats to availableSeats
	protected static Semaphore cartFull;
	protected static Semaphore canLoad;
	protected static Semaphore canUnboard;
	protected static Semaphore mutex;
	
	public SharedVariables(int seats){
		numSeat = seats;
		
		availableSeat = new Semaphore(0, true);
		cartFull = new Semaphore(0, true);
		canLoad = new Semaphore(1, true);
		canUnboard = new Semaphore(0, true);
		mutex = new Semaphore(1, true);
	}
}
