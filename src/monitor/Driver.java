package monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Driver {
	protected static volatile int numSeat;
	protected static volatile int numOfBoarded = 0;
	private int numPassenger;

	protected static Condition car, passenger, board, run;
	protected static ReentrantLock lock;
	protected static boolean readyToBoard;
	protected static boolean readyToUnboard;
	protected static boolean running;

	private Thread cars;
	private Thread passengers;
	private List<Thread> passengerList = new ArrayList<Thread>();
	
	public Driver(int numSeat, int numPassenger){
		this.numPassenger = numPassenger;
		SharedVariables.numSeat = numSeat;
		numSeat = numSeat;
		lock = new ReentrantLock(true);
		readyToBoard = false;
		readyToUnboard = false;
		running = false;
	}
	
	public void execute(){
		rollercoasterInitialize();
		startThreads();
	}
	
	public void rollercoasterInitialize(){
		cars = new Thread(new Car());
		
		for(int ctr = 1; ctr <= numPassenger; ctr++){
			passengers = new Thread(new Passenger(ctr));
			passengerList.add(passengers);
		}
	}
	
	public void startThreads(){
		cars.start();
		
		for(int ctr = 0; ctr < numPassenger; ctr++){
			passengerList.get(ctr).start();
		}
	}
}