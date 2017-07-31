/*Kyle Matthew C. Chua
 * Mary Louise M. Paragas
 * Janz Aeinstein F. Villamayor
 * S11
 */

package main;

import java.util.Scanner;

public class MainDriver {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int numPassenger;
		System.out.println("1 - Semaphores");
		System.out.println("2 - Monitors");
		System.out.println("3 - Sempahores with time limit.");
		System.out.println("4 - Monitors with time limit.");
		System.out.print("\nChoice: ");
		
		int choice = sc.nextInt();
		
		System.out.print("Enter number of seats: ");
		int numSeat = sc.nextInt();
		
				
		switch(choice){
		
		case 1: 
		case 2: System.out.print("Enter total number of passengers: ");
				numPassenger = sc.nextInt();
			
				if(!(numSeat < numPassenger))
					System.out.println("ERROR: The number of seats should be less than the number of passengers.\n");
		
				else if(choice == 1)
					new semaphore.Driver(numSeat, numPassenger).execute();
				else 
					new monitor.Driver(numSeat, numPassenger).execute();
				break;
		case 3:	
		case 4:	System.out.print("Enter the time limit (in seconds): ");
				int timeLimit = sc.nextInt();
			 	System.out.print("Enter number of passengers per second: ");
			 	int numPassengerPerTime = sc.nextInt();
			 		
			 	if(choice == 3)
			 		new semaphore.Driver(numSeat, numPassengerPerTime, timeLimit).execute();
			 	else
					new monitor.Driver(numSeat, numPassengerPerTime, timeLimit).execute();
		}
		sc.close();
	}
}