package semaphore;

public class Car implements Runnable{
	private int numSeat;
	
	public Car(int numSeat){
		this.numSeat = numSeat;
	}
	
	@Override
	public void run(){
		while(!Driver.exit){
			load();
			carRun();
			unload();
		}
	}
	
	public void load(){
		try {
			Driver.canLoad.acquire(1);
			Driver.availableSeat.release(numSeat);
			System.out.println("Car is loading...");
			
			Thread.sleep(100);
		} catch (InterruptedException e) {		
			System.out.println("Error in load - semaphore");
			e.printStackTrace();
		}
	}
	
	public void carRun(){
		try {
			Driver.cartFull.acquire(1);
			System.out.println("Car is running");
			
			Thread.sleep(100);
		} catch (InterruptedException e) {
			System.out.println("Error in carRun - semaphore");
			e.printStackTrace();
		}

	}
	
	public void unload(){
		try{
			System.out.println("Car is unloading");
			Driver.canUnboard.release(numSeat);
			
			Thread.sleep(100);
		} catch (InterruptedException e) {
			System.out.println("Error in carRun - semaphore");
			e.printStackTrace();
		}	
	}
}

