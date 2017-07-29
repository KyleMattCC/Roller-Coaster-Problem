package main;

import java.util.Scanner;

public class MainDriver {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int numSeat, numPassenger;
		System.out.println("1 - Semaphores");
		System.out.println("2 - Monitors");
		
		System.out.print("Choice: ");
		int choice = sc.nextInt();
		
		
		do{
		System.out.print("Enter number of seats: ");
		numSeat = sc.nextInt();
		System.out.print("Enter number of passengers: ");
		numPassenger = sc.nextInt();
		
		if(!(numSeat < numPassenger))
			System.out.println("The number of seats should be less than the number of passengers.\n");
		}while(!(numSeat < numPassenger));
		
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
