package semaphore;

import java.util.concurrent.Semaphore;

public class Passenger implements Runnable{
	private Semaphore mutex = new Semaphore(0, true);
	private int numBoarded = 0;
	private int numSeat;
	
	public Passenger(int numSeat){
		this.numSeat = numSeat;
	}
	
	public void board(){
		try {
			Driver.availableSeat.acquire(1);
			mutex.acquire(1);
				numBoarded++;
				if(numBoarded == this.numSeat) Driver.cartFull.release();
			mutex.release(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in board - semaphore");
			e.printStackTrace();
		}
	}
	
	public void unboard(){
		try {
			Driver.canUnboard.acquire(1);
			mutex.acquire(1);
				numBoarded--;
				if(numBoarded == 0) Driver.canLoad.release(1);
			mutex.release(1);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in unboard - semaphore");
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		
	}
}
