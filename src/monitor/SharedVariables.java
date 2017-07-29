package monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SharedVariables {
	protected static volatile int numSeat;
	protected static volatile int numOfBoarded = 0;

	protected static Condition car, passenger, board, run;
	protected static ReentrantLock lock;
	protected static boolean readyToBoard;
	protected static boolean readyToUnboard;
	protected static boolean running;
	
	public SharedVariables(int seats){
		numSeat = seats;
		lock = new ReentrantLock(true);
		readyToBoard = false;
		readyToUnboard = false;
		running = false;
	}
}
