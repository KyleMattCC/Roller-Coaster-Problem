package main;

import java.util.Scanner;

public class MainDriver {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		System.out.println("1 - Semaphores");
		System.out.println("2 - Monitors");
		
		int choice = sc.nextInt();
		
		
		System.out.println("Enter number of seats: ");
		int numSeat = sc.nextInt();
		System.out.println("Enter number of passengers: ");
		int numPassenger = sc.nextInt();
		
		if(choice == 1){
			semaphore.Driver semaphoreDriver = new semaphore.Driver(numSeat, numPassenger);
			semaphoreDriver.execute();
		}
		
		else if(choice == 2){
			monitor.Driver monitorDriver = new monitor.Driver(numSeat, numPassenger);
			monitorDriver.execute();
		}
		
		
		sc.close();
		
		
	}
}
