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
	
	protected static Semaphore availableSeat; //changed numberofSeats to availableSeats
	protected static Semaphore cartFull;
	protected static Semaphore canLoad;
	protected static Semaphore canUnboard;
	protected static Semaphore mutex;
	
	private Thread car;
	private Thread passenger;
	private List<Thread> passengerList = new ArrayList<Thread>();
	protected static volatile boolean [] starveList;
	protected static volatile boolean exit;
	private fixedTimer timer;
	
	public Driver(int numSeat, int numPassenger){
		
		Driver.numPassenger = numPassenger;
		Driver.numSeat = numSeat;
		starveList = new boolean[numPassenger];
		for(int ctr = 0; ctr< numPassenger; ctr++){
			starveList[ctr] = true;
		}
		exit = false;
		timer = new fixedTimer(1);
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
		
		availableSeat = new Semaphore(0, true);
		cartFull = new Semaphore(0, true);
		canLoad = new Semaphore(1, true);
		canUnboard = new Semaphore(0, true);
		mutex = new Semaphore(1, true);
		car = new Thread(new Car(Driver.numSeat));
		
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
	
	public static int getStarvations(){
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