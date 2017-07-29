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
		System.out.println("Car is LOADING");
		notifyPToBoard();
	}

	public void carRun(){
		tryCarRun();
		Driver.readyToBoard = true;
		Driver.running = true;
		System.out.println("Car is RUNNING");
	}
	
	public void unload(){
		System.out.println("Car is UNLOADING");
		notifyPToUnboard();
	}
	
	public void tryLoad(){
		try{
			while(Driver.numOfBoarded > 0)
				Driver.car.await();
		}catch(InterruptedException e){
			System.out.println("Error in tryLoad - monitor");
		}
	}
	
	public void notifyPToBoard(){
		Driver.passenger.signalAll();
	}
	
	public void tryCarRun(){
		try{
			while(Driver.numOfBoarded < Driver.numSeat)
				Driver.run.await();
		}catch(InterruptedException e){
			System.out.println("Error in tryCarRun - monitor");
		}
	}
	
	public void notifyPToUnboard(){
			Driver.board.signalAll();
	}
	

}
