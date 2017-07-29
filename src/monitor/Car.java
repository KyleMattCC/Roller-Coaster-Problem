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
		Driver.readyToBoard = true;
		//Driver.running = false;
		Driver.readyToUnboard = false;
		System.out.println("Car is LOADING");
		notifyPToBoard();
	}

	public void carRun(){
		tryCarRun();
		Driver.readyToBoard = false;
		//Driver.running = true;
		System.out.println("Car is RUNNING");
	}
	
	public void unload(){
		//Driver.running = false;
		Driver.readyToUnboard = true;
		System.out.println("Car is UNLOADING");
		notifyPToUnboard();
	}
	
	public void tryLoad(){
		Driver.lock.lock();
		try{
			while(Driver.numOfBoarded > 0)
				Driver.carLoad.await();
		}catch(InterruptedException e){
			System.out.println("Error in tryLoad - monitor");
		}
		Driver.lock.unlock();
	}
	
	public void notifyPToBoard(){
		Driver.passengerBoard.signalAll();
	}
	
	public void tryCarRun(){
		Driver.lock.lock();
		try{
			while(Driver.numOfBoarded < Driver.numSeat)
				Driver.carRun.await();
		}catch(InterruptedException e){
			System.out.println("Error in tryCarRun - monitor");
		}
		Driver.lock.unlock();
	}
	
	public void notifyPToUnboard(){
			Driver.passengerUnboard.signalAll();
	}
	

}
