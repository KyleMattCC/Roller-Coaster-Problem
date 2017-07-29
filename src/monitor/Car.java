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
		notifyPToBoard();
	}

	public void carRun(){
		tryCarRun();
		Driver.readyToBoard = true;
		Driver.running = true;
	}
	
	public void unload(){
		notifyPToUnboard();
	}
	
	public void tryLoad(){
		Driver.lock.lock();
		
		try{
			while(Driver.numOfBoarded > 0)
				Driver.car.await();
		}catch(InterruptedException e){
			System.out.println("Error in tryLoad - monitor");
		}finally{
			Driver.lock.unlock();
		}
	}
	
	public void notifyPToBoard(){
		Driver.passenger.signalAll();
	}
	
	public void tryCarRun(){
		Driver.lock.lock();
		try{
			while(Driver.numOfBoarded < Driver.numSeat)
				Driver.run.await();
		}catch(InterruptedException e){
			System.out.println("Error in tryCarRun - monitor");
		}finally{
			Driver.lock.unlock();
		}
	}
	
	public void notifyPToUnboard(){
			Driver.board.signalAll();
	}
	

}
