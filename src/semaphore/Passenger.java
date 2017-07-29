package semaphore;

import java.util.concurrent.Semaphore;

public class Passenger implements Runnable{
	private int passengerID;
	
	public Passenger(int ID){
		passengerID = ID;
	}
	
	public void board(){
		try {
			//System.out.println("Passenger" + passengerID + "is trying to board");
			Driver.availableSeat.acquire(1);
			System.out.println("Passenger " + passengerID + " is BOARDING");
			Driver.mutex.acquire(1);
				//System.out.println("Num BEFORE boarding: " + variables.numBoarded);
			Driver.numBoarded++;
				//System.out.println("Num AFTER boarding: " + variables.numBoarded);
				if(Driver.numBoarded == Driver.numSeat) Driver.cartFull.release();
				Driver.mutex.release(1);
				Thread.sleep(500);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in board - semaphore");
			e.printStackTrace();
		}
	}
	
	public void unboard(){
		try {
			//System.out.println("Passenger " + passengerID + " is waiting to unboard");
			Driver.canUnboard.acquire(1);
			System.out.println("Passenger " + passengerID + " is UNBOARDING FROM seat");
			//System.out.println("Passenger " + passengerID + "is trying to unboard FROM seat");
			Driver.mutex.acquire(1);
				//System.out.println("Num BEFORE UNBOARDING: " + variables.numBoarded);
			Driver.numBoarded--;
				//System.out.println("Num AFTER UNBOARDING: " + variables.numBoarded);
					if(Driver.numBoarded == 0) Driver.canLoad.release(1);
					Driver.mutex.release(1);
					Thread.sleep(500);
			
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


