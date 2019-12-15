import java.util.HashMap;
import java.util.Scanner;
import java.lang.NumberFormatException;

public class Menu {
	
	private static boolean exit;

	public static void main(String[] args) {
		PrintHeader();
		UserReaderClass reader = new UserReaderClass();

		while (!exit) {
			PrintMenu();
			int choice = 0;
			selectAction(choice);
			
		}
	}

	public static void PrintHeader() {
		// TODO Auto-generated method stub
		System.out.println(" +------------------------------------------------+");
		System.out.println();
		System.out.println();
		System.out.println("        Welcome to our          ");
		System.out.println("         POS SYSTEM  ");
		System.out.println();
		System.out.println();
		System.out.println(" +------------------------------------------------+ ");
	}

	private static void PrintMenu() {
		// TODO Auto-generated method stub
		
		Scanner kb = new Scanner(System.in);
		
		String Username;
		String Password;
		

		System.out.println();
		System.out.println();
		System.out.println();

		Login newLoginSystem = new Login();
		
		while (newLoginSystem.isLoggedIn() == false) {
			System.out.println(" Please Enter your UserName  ");
			Username = kb.nextLine();			
			System.out.println(" Please Enter your password");
			Password = kb.nextLine();
			newLoginSystem.checkLogin(Username, Password);
		
			}
		
		
	}
		
		
		/*
		 * }
		 * 
		 * private static int getInput() { // TODO Auto-generated method stub
		 */

	private static void selectAction(int choice) {
		switch (choice) {

		case 0:
			exit = true;
			System.out.println("Thank you for useing our PSO System");
			break;

		
		}

		return;
	}
}
