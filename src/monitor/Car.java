/*Kyle Matthew C. Chua
 * Mary Louise M. Paragas
 * Janz Aeinstein F. Villamayor
 * S11
 */

package monitor;

public class Car implements Runnable{

	@Override
	public void run(){
		while(!Driver.exit){
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
		
		try {
			Driver.lock.lock();
			try {
				while(Driver.numOfBoarded > 0)
					Driver.carLoad.await();
				
				Driver.readyToBoard = true;
				Driver.readyToUnboard = false;
				System.out.println("Car is LOADING");
			} catch(InterruptedException e){
				System.out.println("Error in tryLoad - monitor");
			} finally{
				Driver.lock.unlock();
			}
			
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void notifyPToBoard(){
		try {
			Driver.lock.lock();
			Driver.passengerBoard.signalAll();
			Driver.passengerWait.signalAll();
				
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			Driver.lock.unlock();
		}
	}
	
	public void tryCarRun(){
		
		try {
			Driver.lock.lock();
			try {
				while(Driver.numOfBoarded < Driver.numSeat)
					Driver.carRun.await();
				
				Driver.readyToBoard = false;
				System.out.println("Car is RUNNING");
			} catch(InterruptedException e){
				System.out.println("Error in tryCarRun - monitor");
			} finally{
				Driver.lock.unlock();
			}
			
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void notifyPToUnboard(){
			
		try {
			Driver.lock.lock();
			Driver.readyToUnboard = true;	
			Driver.passengerUnboard.signalAll();
			System.out.println("Car is UNLOADING");

			
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			Driver.lock.unlock();
		}
	}
}
