package semaphore;

import java.util.concurrent.Semaphore;

public class Passenger implements Runnable{
	private Semaphore mutex = new Semaphore(0, true);
	private int passengerID;
	
	public Passenger(int ID){
		passengerID = ID;
	}
	
	public void board(){
		try {
			SharedVariables.availableSeat.acquire(1);
			mutex.acquire(1);
				//numBoarded++;
				//if(numBoarded == this.numSeat) SharedVariables.cartFull.release();
			mutex.release(1);
			//System.out.println("Passenger " + passengerID + "is BOARDING ON seat" + numSeat);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in board - semaphore");
			e.printStackTrace();
		}
	}
	
	public void unboard(){
		try {
			SharedVariables.canUnboard.acquire(1);
			mutex.acquire(1);
			//SharedVariables.numBoarded--;
				//if(SharedVariables.numBoarded == 0) SharedVariables.canLoad.release(1);
			//mutex.release(1);
			//System.out.println("Passenger " + passengerID + "is UNBOARDING FROM seat" + numSeat);
			
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
