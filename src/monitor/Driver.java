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
	private static int numPassenger;
	private static int passengerPerTime;

	protected static Condition carLoad, carRun, passengerBoard, passengerUnboard, passengerWait;
	protected static ReentrantLock lock;
	protected static boolean readyToBoard;
	protected static boolean readyToUnboard;

	private Thread cars;
	private Thread passengers;
	private static List<Thread> passengerList = new ArrayList<Thread>();
	protected static volatile List<Boolean> starveList = new ArrayList<Boolean>();
	protected static volatile boolean exit;
	private fixedTimer timer;
	private long deadlockTime;
	
	public Driver(int numSeat, int numPassenger){
		
		Driver.numPassenger = numPassenger;
		Driver.numSeat = numSeat;
		timer = new fixedTimer(2);
		deadlockTime = 30;
	}
	
	public Driver(int numSeat, int numPassengerPerTime, int timeLimit){
		
		Driver.numSeat = numSeat;
		Driver.passengerPerTime = numPassengerPerTime;
		Driver.numPassenger = numPassengerPerTime;
		timer = new fixedTimer(4, timeLimit);
		deadlockTime = timeLimit;
	}
	
	public void execute(){
		
		DeadlockDetector deadlockDetector = new DeadlockDetector(new DeadlockConsoleHandler(), deadlockTime, TimeUnit.SECONDS);
		deadlockDetector.start();
		timer.start();
		
		rollercoasterInitialize();
		startThreads();
		while(!Driver.exit);
		try{
			Thread.sleep(1250);
			System.out.println("---------------Program has ended!---------------");
			System.out.println("Total passenger: " + monitor.Driver.numPassenger);
			System.out.println("Deadlock occurences: " + deadlockDetector.getDeadlockCount());
			System.out.println("Starvations: " + getStarvations());
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void rollercoasterInitialize(){
		lock = new ReentrantLock(true);
		readyToBoard = false;
		readyToUnboard = false;
		carLoad = lock.newCondition();
		carRun = lock.newCondition();
		passengerBoard = lock.newCondition();
		passengerUnboard = lock.newCondition();
		passengerWait = lock.newCondition();
		cars = new Thread(new Car());
		
		for(int ctr = 1; ctr <= numPassenger; ctr++){
			passengers = new Thread(new Passenger(ctr));
			passengerList.add(passengers);
			starveList.add(true);
		}
		
		exit = false;
	}
	
	public static void createPassengers(){
		
		System.out.println("************Creating NEW passengers************");
		for(int ctr = 0; ctr < passengerPerTime; ctr++){
			numPassenger++;
			Thread newPassenger = new Thread();
			newPassenger = new Thread(new Passenger(numPassenger));
			newPassenger.start();
			passengerList.add(newPassenger);
			starveList.add(true);
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
			if(starveList.get(ctr))
				starve++;
		}
		
		return starve;
	}
	
	public static void exitProgram(){
		exit = true;
	}
}