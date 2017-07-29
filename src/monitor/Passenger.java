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
		Driver.numOfBoarded++;
		notifyCToRun();
	}
	
	public void unboard(){
		tryUnboard();
		System.out.println("Passenger " + passengerID + " UNBOARDING");
		Driver.numOfBoarded--;
		notifyCToLoad();
	}
	
	public void tryBoard(){
		Driver.lock.lock();
		try{
			while(Driver.readyToBoard == false)
				Driver.passenger.await();
		}catch(InterruptedException e){
			System.out.println("Error in tryBoard - monitor");
		}finally{
			Driver.lock.unlock();
		}
	}
	
	public void notifyCToRun(){
		if(Driver.numOfBoarded == Driver.numSeat)
			Driver.run.signal();
	}
	
	public void tryUnboard(){
		Driver.lock.lock();
		try{
			while(Driver.running)
				Driver.board.await();
		}catch(InterruptedException e){
			System.out.println("Error in tryUnboard - monitor");
		}finally{
			Driver.lock.unlock();
		}
	}
	
	public void notifyCToLoad(){
		if(Driver.numOfBoarded == 0)
			Driver.car.signal();
	}
}
