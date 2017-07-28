package semaphore;


public class Car implements Runnable{
	
	private int numSeat;
	
	public Car(int numSeat){
		this.numSeat = numSeat;
	}
	
	public void load(){
		try {
			Driver.canLoad.acquire(1);
			Driver.availableSeat.release(numSeat);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
			System.out.println("Error in load - semaphore");
			e.printStackTrace();
		}
	}
	
	public void carRun(){
		try {
			Driver.cartFull.acquire(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in carRun - semaphore");
			e.printStackTrace();
		}

	}
	
	public void unload(){
		Driver.canUnboard.release(numSeat);
	}
	
	@Override
	public void run(){
		
	}
	


}
