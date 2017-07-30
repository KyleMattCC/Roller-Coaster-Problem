package semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import deadlockmanager.DeadlockConsoleHandler;
import deadlockmanager.DeadlockDetector;
import timer.fixedTimer;

public class Driver {
	
	protected static volatile int numSeat;
	protected static volatile int numBoarded = 0;
	protected static int numPassenger;
	private static int passengerPerTime;
	
	protected static Semaphore availableSeat; //changed numberofSeats to availableSeats
	protected static Semaphore cartFull;
	protected static Semaphore canLoad;
	protected static Semaphore canUnboard;
	protected static Semaphore mutex;
	
	private Thread car;
	private Thread passenger;
	private static List<Thread> passengerList = new ArrayList<Thread>();
	protected static volatile List<Boolean> starveList = new ArrayList<Boolean>();
	protected static volatile boolean exit;
	private fixedTimer timer;
	
	public Driver(int numSeat, int numPassenger){
		
		Driver.numPassenger = numPassenger;
		Driver.numSeat = numSeat;
		timer = new fixedTimer(1);
	}
	
	public Driver(int numSeat, int numPassengerPerTime, int timeLimit){
		
		Driver.numSeat = numSeat;
		Driver.numPassenger = numPassengerPerTime;
		Driver.passengerPerTime = numPassengerPerTime;
		
		timer = new fixedTimer(3, timeLimit);
	}
	
	public void execute(){
		
		DeadlockDetector deadlockDetector = new DeadlockDetector(new DeadlockConsoleHandler(), 5, TimeUnit.SECONDS);
		deadlockDetector.start();
		timer.start();
		
		rollerCoasterInitialize();
		startThreads();
		while(!Driver.exit);
		try{
			Thread.sleep(1250);
			System.out.println("---------------Program has ended!---------------");
			System.out.println("Total passenger: " + semaphore.Driver.numPassenger);
			System.out.println("Starvations: " + getStarvations());
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void rollerCoasterInitialize(){
		
		availableSeat = new Semaphore(0, true);
		cartFull = new Semaphore(0, true);
		canLoad = new Semaphore(1, true);
		canUnboard = new Semaphore(0, true);
		mutex = new Semaphore(1, true);
		car = new Thread(new Car(Driver.numSeat));
		
		for(int ctr = 1; ctr <= numPassenger; ctr++){
			passenger = new Thread(new Passenger(ctr));
			passengerList.add(passenger);
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
		
		car.start();
		
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