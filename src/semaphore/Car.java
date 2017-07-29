package semaphore;


public class Car implements Runnable{
	
	private int numSeat;
	
	public Car(int numSeat){
		this.numSeat = numSeat;
	}
	
	public void load(){
		try {
			SharedVariables.canLoad.acquire(1);
			SharedVariables.availableSeat.release(numSeat);
			System.out.println("Car is loading...");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
			System.out.println("Error in load - semaphore");
			e.printStackTrace();
		}
	}
	
	public void carRun(){
		try {
			SharedVariables.cartFull.acquire(1);
			System.out.println("Car is running");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in carRun - semaphore");
			e.printStackTrace();
		}

	}
	
	public void unload(){
		System.out.println("Car is unloading");
		SharedVariables.canUnboard.release(numSeat);
	}
	
	@Override
	public void run(){
		while(true){
			load();
			carRun();
			unload();
		}
	}
	


}
