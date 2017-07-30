package semaphore;

import java.util.concurrent.Semaphore;

public class Passenger implements Runnable{
	private int passengerID;
	
	public Passenger(int ID){
		passengerID = ID;
	}
	
	@Override
	public void run(){
		while(!Driver.exit){
			board();
			unboard();
		}
	}
	
	public void board(){
		try {
			Driver.availableSeat.acquire(1);
			System.out.println("Passenger " + passengerID + " is BOARDING");
			Driver.starveList[passengerID - 1] = false;
			Driver.mutex.acquire(1);
				Driver.numBoarded++;
				if(Driver.numBoarded == Driver.numSeat) 
					Driver.cartFull.release();
			Driver.mutex.release(1);
			
			Thread.sleep(500);
			
		} catch (InterruptedException e) {
			System.out.println("Error in board - semaphore");
			e.printStackTrace();
		}
	}
	
	public void unboard(){
		try {
			Driver.canUnboard.acquire(1);
			System.out.println("Passenger " + passengerID + " is UNBOARDING FROM seat");
			Driver.mutex.acquire(1);
				Driver.numBoarded--;
				if(Driver.numBoarded == 0)
					Driver.canLoad.release(1);
			Driver.mutex.release(1);
			
			Thread.sleep(500);
			
		} catch (InterruptedException e) {
			System.out.println("Error in unboard - semaphore");
			e.printStackTrace();
		}
	}
}


