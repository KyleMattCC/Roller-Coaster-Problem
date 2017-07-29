package monitor;

public class Car implements Runnable{

	@Override
	public void run(){
		while(true){
		load();
		carRun();
		unload();
		}

	}
	
	public void load(){
		tryLoad();
		notifyPToBoard();
	}

	public void carRun(){
		tryCarRun();
	}
	
	public void unload(){
		notifyPToUnboard();
	}
	
	public void tryLoad(){
		Driver.lock.lock();
		try{
			while(Driver.numOfBoarded > 0)
				Driver.carLoad.await();
			
			Driver.readyToBoard = true;
			Driver.readyToUnboard = false;
			System.out.println("Car is LOADING");
		}catch(InterruptedException e){
			System.out.println("Error in tryLoad - monitor");
		}
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Driver.lock.unlock();
	}
	
	public void notifyPToBoard(){
		Driver.lock.lock();
			Driver.passengerBoard.signalAll();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		Driver.lock.unlock();
	}
	
	public void tryCarRun(){
		Driver.lock.lock();
		try{
			while(Driver.numOfBoarded < Driver.numSeat)
				Driver.carRun.await();
			
			Driver.readyToBoard = false;
			System.out.println("Car is RUNNING");
		}catch(InterruptedException e){
			System.out.println("Error in tryCarRun - monitor");
		}
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Driver.lock.unlock();
	}
	
	public void notifyPToUnboard(){
		Driver.lock.lock();
			Driver.readyToUnboard = true;	
			Driver.passengerUnboard.signalAll();
			System.out.println("Car is UNLOADING");
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		Driver.lock.unlock();
	}
	

}
