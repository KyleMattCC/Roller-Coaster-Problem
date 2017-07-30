package monitor;

public class Passenger implements Runnable{
	
	private int passengerID;
	
	public Passenger(int ID){
		this.passengerID = ID;
	}
	
	@Override
	public void run(){
		while(!Driver.exit){
			board();
			unboard();			
		}
	}
	
	public void board(){
		tryBoard();
		notifyCToRun();
	}
	
	public void unboard(){
		tryUnboard();
		notifyCToLoad();
	}
	
	public void tryBoard(){
		
		try {
			Driver.lock.lock();
			try {
				while(Driver.readyToBoard == false || Driver.numOfBoarded == Driver.numSeat)
					Driver.passengerBoard.await();
			} catch(InterruptedException e){
				System.out.println("Error in tryBoard - monitor");
			} finally{
				Driver.numOfBoarded++;
				Driver.starveList.set(passengerID-1, false);
				System.out.println("Passenger " + passengerID + " BOARDING");
				Driver.lock.unlock();
			}
			
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void notifyCToRun(){
		
		try {
			Driver.lock.lock();
			if(Driver.numOfBoarded == Driver.numSeat)
				Driver.carRun.signal();
			
			
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			Driver.lock.unlock();
		}
	}
	
	public void tryUnboard(){
		
		try {
			Driver.lock.lock();
			try {
				while(Driver.readyToUnboard == false)
					Driver.passengerUnboard.await();
			} catch(InterruptedException e){
				System.out.println("Error in tryUnboard - monitor");
			} finally{
				Driver.numOfBoarded--;
				System.out.println("Passenger " + passengerID + " UNBOARDING");
				Driver.lock.unlock();
			}
			
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void notifyCToLoad(){
		try {
			Driver.lock.lock();
			if(Driver.numOfBoarded == 0)
				Driver.carLoad.signal();
			
			
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			Driver.lock.unlock();
		}
	}
}
