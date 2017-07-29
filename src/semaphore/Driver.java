package semaphore;

import java.util.ArrayList;
import java.util.List;

public class Driver {
	protected int numSeat; //int C
	private int numPassenger;
	
	private SharedVariables variables;
	private Thread Car;
	private Thread Passenger0;
	List<Thread> passengerList = new ArrayList<Thread>();
	
	public Driver(int numSeat, int numPassenger){
		this.numPassenger = numPassenger;
		this.numSeat = numSeat;
		variables = new SharedVariables(numSeat);
	}
	
	class Cart implements Runnable{
		private int numSeat;
		
		public Cart(int numSeat){
			this.numSeat = numSeat;
		}
		
		public void load(){
			try {
				SharedVariables.canLoad.acquire(1);
				SharedVariables.availableSeat.release(numSeat);
				System.out.println("Car is loading...");
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				
				System.out.println("Error in load - semaphore");
				e.printStackTrace();
			}
		}
		
		public void carRun(){
			try {
				SharedVariables.cartFull.acquire(1);
				System.out.println("Car is running");
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Error in carRun - semaphore");
				e.printStackTrace();
			}

		}
		
		public void unload(){
			System.out.println("Car is unloading");
			SharedVariables.canUnboard.release(numSeat);
		}
		
		@Override
		public void run(){
			while(true){
				load();
				carRun();
				unload();
			}
		}
	}
	
	class Passengers implements Runnable{
		private int passengerID;
		
		public Passengers(int ID){
			passengerID = ID;
		}
		
		public void board(){
			try {
				//System.out.println("Passenger" + passengerID + "is trying to board");
				SharedVariables.availableSeat.acquire(1);
				SharedVariables.mutex.acquire(1);
					//System.out.println("Num BEFORE boarding: " + variables.numBoarded);
					variables.numBoarded++;
					//System.out.println("Num AFTER boarding: " + variables.numBoarded);
					
					if(variables.numBoarded == variables.numSeat) SharedVariables.cartFull.release();
				SharedVariables.mutex.release(1);
				Thread.sleep(10);
				System.out.println("Passenger " + passengerID + " is BOARDING");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Error in board - semaphore");
				e.printStackTrace();
			}
		}
		
		public void unboard(){
			try {
				//System.out.println("Passenger " + passengerID + " is waiting to unboard");
				SharedVariables.canUnboard.acquire(1);
				//System.out.println("Passenger " + passengerID + "is trying to unboard FROM seat");
				SharedVariables.mutex.acquire(1);
					//System.out.println("Num BEFORE UNBOARDING: " + variables.numBoarded);
					variables.numBoarded--;
					//System.out.println("Num AFTER UNBOARDING: " + variables.numBoarded);
						if(variables.numBoarded == 0) SharedVariables.canLoad.release(1);
				SharedVariables.mutex.release(1);
				Thread.sleep(10);
				System.out.println("Passenger " + passengerID + " is UNBOARDING FROM seat");
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Error in unboard - semaphore");
				e.printStackTrace();
			}
		}
		
		@Override
		public void run(){
			while(true){
				board();
				unboard();
			}
		}
	}
	
	public void execute(){
		rollercoasterInitialize();
		startThreads();
	}
	
	public void rollercoasterInitialize(){
		Car = new Thread(new Cart(this.numSeat));
		
		for(int ctr = 1; ctr <= numPassenger; ctr++){
			Passenger0 = new Thread(new Passengers(ctr));
			passengerList.add(Passenger0);
		}
	}
	
	public void startThreads(){
		Car.start();
		
		for(int ctr = 0; ctr < numPassenger; ctr++){
			passengerList.get(ctr).start();
		}
	}
}