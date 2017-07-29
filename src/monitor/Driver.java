package monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Driver {
	protected static int numSeat;
	private int numPassenger;
	protected static Condition car, passenger, board, run;
	protected static Lock lock;
	protected static boolean readyToBoard;
	protected static boolean readyToUnboard;
	protected static boolean running;
	protected static int numOfBoarded;
	
	public Driver(int numSeat, int numPassenger){
		this.numPassenger = numPassenger;
		this.numSeat = numSeat;
	}
	
	public void execute(){
		initialize();
	}
	
	public void initialize(){
		lock = new ReentrantLock(true);
		readyToBoard = false;
		readyToUnboard = false;
		running = false;
	}
}