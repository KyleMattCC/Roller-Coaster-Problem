package semaphore;

import java.util.concurrent.Semaphore;

public class Driver {
	protected int numSeat; //int C
	private int numPassenger;
	
	protected static Semaphore availableSeat; //changed numberofSeats to availableSeats
	protected static Semaphore cartFull;
	protected static Semaphore canLoad;
	protected static Semaphore canUnboard;
	
	private Thread Car;
	
	public Driver(int numSeat, int numPassenger){
		this.numPassenger = numPassenger;
		this.numSeat = numSeat;
		
	}
	
	public void execute(){
		carInitialize();
		startCar();
		
	}
	
	public void carInitialize(){
		availableSeat = new Semaphore(0, true);
		cartFull = new Semaphore(0, true);
		canLoad = new Semaphore(1, true);
		canUnboard = new Semaphore(0, true);
		
		Car = new Thread(new Car(this.numSeat));
	}
	
	public void startCar(){
		Car.start();
	}
}