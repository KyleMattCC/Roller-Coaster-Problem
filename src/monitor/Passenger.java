package monitor;

public class Passenger implements Runnable{
	
	private int passengerID;
	
	public Passenger(int ID){
		this.passengerID = ID;
	}
	
	@Override
	public void run(){
		while(true){
			board();
			unboard();			
		}
	}
	
	public void board(){
		tryBoard();
		System.out.println("Passenger " + passengerID + " BOARDING");
		notifyCToRun();
	}
	
	public void unboard(){
		tryUnboard();
		System.out.println("Passenger " + passengerID + " UNBOARDING");
		notifyCToLoad();
	}
	
	public void tryBoard(){
		Driver.lock.lock();
		try{
			while(Driver.readyToBoard == false && Driver.numOfBoarded > Driver.numSeat)
				Driver.passengerBoard.await();
		}catch(InterruptedException e){
			System.out.println("Error in tryBoard - monitor");
		}
		Driver.numOfBoarded++;
		Driver.lock.unlock();
	}
	
	public void notifyCToRun(){
		if(Driver.numOfBoarded == Driver.numSeat)
			Driver.carRun.signal();
	}
	
	public void tryUnboard(){
		Driver.lock.lock();
		try{
			while(Driver.readyToUnboard == false)
				Driver.passengerUnboard.await();
		}catch(InterruptedException e){
			System.out.println("Error in tryUnboard - monitor");
		}
		Driver.numOfBoarded--;
		Driver.lock.unlock();
	}
	
	public void notifyCToLoad(){
		if(Driver.numOfBoarded == 0)
			Driver.carLoad.signal();
	}
}
