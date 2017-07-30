package monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import deadlockmanager.DeadlockConsoleHandler;
import deadlockmanager.DeadlockDetector;
import timer.fixedTimer;

public class Driver {
	protected static int numSeat;
	protected static volatile int numOfBoarded = 0;
	private final int numPassenger;

	protected static Condition carLoad, carRun, passengerBoard, passengerUnboard;
	protected static ReentrantLock lock;
	protected static boolean readyToBoard;
	protected static boolean readyToUnboard;

	private Thread cars;
	private Thread passengers;
	private List<Thread> passengerList = new ArrayList<Thread>();
	protected static volatile boolean [] starveList;
	protected static volatile boolean exit;
	private fixedTimer timer;
	
	public Driver(int numSeat, int numPassenger){
		this.numPassenger = numPassenger;
		Driver.numSeat = numSeat;
		lock = new ReentrantLock(true);
		readyToBoard = false;
		readyToUnboard = false;
		carLoad = lock.newCondition();
		carRun = lock.newCondition();
		passengerBoard = lock.newCondition();
		passengerUnboard = lock.newCondition();
		starveList = new boolean[numPassenger];
		for(int ctr = 0; ctr< numPassenger; ctr++){
			starveList[ctr] = true;
		}
		exit = false;
		timer = new fixedTimer(2);
	}
	
	public void execute(){
		DeadlockDetector deadlockDetector = new DeadlockDetector(new DeadlockConsoleHandler(), 5, TimeUnit.SECONDS);
		deadlockDetector.start();
		timer.start();
		
		rollercoasterInitialize();
		startThreads();
		while(!Driver.exit);
		try{
			Thread.sleep(1250);
			System.out.println("Program has ended!");
			System.out.println("Starvations: " + semaphore.Driver.getStarvations());
		}catch (InterruptedException e){
			e.printStackTrace();
		}
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
	
	public int getStarvations(){
		int starve = 0;
		
		for(int ctr = 0; ctr < numPassenger; ctr++){
			if(starveList[ctr])
				starve++;
		}
		
		return starve;
	}
	
	public static void exitProgram(){
		exit = true;
	}
}