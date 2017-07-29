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
			while(Driver.readyToBoard == false || Driver.numOfBoarded == Driver.numSeat)
				Driver.passengerBoard.await();
		}catch(InterruptedException e){
			System.out.println("Error in tryBoard - monitor");
		}
		Driver.numOfBoarded++;
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Driver.lock.unlock();
	}
	
	public void notifyCToRun(){
		Driver.lock.lock();
		if(Driver.numOfBoarded == Driver.numSeat){			
			Driver.carRun.signal();
		}
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Driver.lock.unlock();
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
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Driver.lock.unlock();
	}
	
	public void notifyCToLoad(){
		Driver.lock.lock();
		if(Driver.numOfBoarded == 0)
			Driver.carLoad.signal();
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Driver.lock.unlock();
	}
}
