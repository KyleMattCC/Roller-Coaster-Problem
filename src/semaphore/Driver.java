package semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Driver {
	
	protected static volatile int numSeat;
	protected static volatile int numBoarded = 0;
	protected static int numPassenger;
	
	protected static Semaphore availableSeat; //changed numberofSeats to availableSeats
	protected static Semaphore cartFull;
	protected static Semaphore canLoad;
	protected static Semaphore canUnboard;
	protected static Semaphore mutex;
	
	private Thread car;
	private Thread passenger;
	List<Thread> passengerList = new ArrayList<Thread>();
	
	public Driver(int numSeat, int numPassenger){
		this.numPassenger = numPassenger;
		this.numSeat = numSeat;
	}
	
	public void execute(){
		rollercoasterInitialize();
		startThreads();
	}
	
	public void rollercoasterInitialize(){
		
		availableSeat = new Semaphore(0, true);
		cartFull = new Semaphore(0, true);
		canLoad = new Semaphore(1, true);
		canUnboard = new Semaphore(0, true);
		mutex = new Semaphore(1, true);
		car = new Thread(new Car(this.numSeat));
		
		for(int ctr = 1; ctr <= numPassenger; ctr++){
			passenger = new Thread(new Passenger(ctr));
			passengerList.add(passenger);
		}
	}
	
	public void startThreads(){
		car.start();	
		for(int ctr = 0; ctr < numPassenger; ctr++){
			passengerList.get(ctr).start();
		}
	}
}