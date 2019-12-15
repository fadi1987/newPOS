import java.util.HashMap;
import java.util.Scanner;
import java.io.IOException;
import java.lang.NumberFormatException;

public class Menu {

	Register register1 = new Register(1);
	Register register2 = new Register(2);
	Register operatingRegister;
	private Scanner input = new Scanner(System.in); // Just to save some typing

	private static boolean exit;

	public Menu() {
		PrintHeader();

	}

	public void PrintHeader() {
		// TODO Auto-generated method stub
		System.out.println(" +------------------------------------------------+");
		System.out.println();
		System.out.println();
		System.out.println("        Welcome to our          ");
		System.out.println("         POS SYSTEM  ");
		System.out.println();
		System.out.println();
		System.out.println(" +------------------------------------------------+ ");
		System.out.println("Please enter the register number you are on.  1 or 2.");
		RegisterSelection();

	}

	private void RegisterSelection() {
		Scanner input = new Scanner(System.in);
		String selection = input.nextLine();
		int registerSelection = 0;
		try {
			boolean validSelection = false;
			while (validSelection == false) {
				registerSelection = Integer.parseInt(selection);
				if (registerSelection >= 1 && registerSelection <= 2)
					validSelection = true;
			}
		} catch (Exception E) {
			System.out.println("Unable to process answer.  Please run the program and try again!");
		}

		switch (registerSelection) {
		case 1:
			operatingRegister = register1;
			break;
		case 2:
			operatingRegister = register2;
			break;
		}

	}

	private void PrintMenu() {
		// TODO Auto-generated method stub

		Scanner kb = new Scanner(System.in);

		String Username = "";
		String Password = "";

		System.out.println();
		System.out.println();
		System.out.println();

		Login newLoginSystem = new Login();

		Register register1 = new Register(1);

		while (newLoginSystem.isLoggedIn() == false) {
			System.out.println("Please Enter your UserName  ");
			Username = kb.nextLine();
			System.out.println("Please Enter your password");
			Password = kb.nextLine();
			newLoginSystem.checkLogin(Username, Password);

		}

		String userType = newLoginSystem.getUserLookup().get(Username).getRole();

		if (userType.equalsIgnoreCase("Cashier")) {
			CashierMenu();
		} else {
			managerMenu();
		}
	}

	private void CashierMenu() {
		double Sale;
		double Return;

		Scanner Input = new Scanner(System.in);

		System.out.println(" 1 Sale");
		System.out.println(" 2 Return");
		System.out.println(" 3 Log out");
		System.out.println(" x Exit");

		int choice = -1;
		while (choice <= 0 || choice >= 4) {
			try {
				System.out.println(" Enter Your Selection");
				choice = Integer.parseInt(Input.nextLine());
			}

			catch (NumberFormatException e) {
				System.out.println("Invalied Selection");
			}
		}
		switch (choice) {

		case 1:
			SalesMenu();
			break;

		case 2:
			ReturnsMenu();
			break;

		case 3:
			PrintMenu();
			break;

		default:
			return;

		}

	}

	public void SalesMenu() {

		Sales newSale = new Sales(operatingRegister);
		Scanner kep1 = new Scanner(System.in);
		int selection = -1;

		while (selection != 3) {
			System.out.println(" 1 Add Item ");

			System.out.println(" 2 Remove Item ");
			System.out.println(" 3 finalize the sale ");

			selection = SelectionChecker(1, 3);

			switch (selection) {
			case 1:
				System.out.println("Please enter the Item ID");
				int itemID = SelectionChecker();
				System.out.println("Please enter the associated quantity");
				Number quan = formatChecker();
				newSale.addItem(itemID, quan);
				break;
			case 2:
				System.out.println("Please enter the Item ID");
				int removalID = SelectionChecker();
				System.out.println("Please enter the associated quantity");
				Number removalQuan = formatChecker();
				newSale.removeItem(removalID, removalQuan);
				break;
			case 3:
				newSale.processPayment();
				CashierMenu();
				break;

			}
		}
	}

	public Number formatChecker() {
		Number quan = -1;
		String _input = input.nextLine();
		try {
			quan = Integer.parseInt(_input);
		} catch (NumberFormatException e) {
			quan = Double.parseDouble(_input);
		}

		return quan;
	}

	public int SelectionChecker() {
		boolean ValidSelection = false;
		int Selection = -1;
		while (ValidSelection == false) {
			String _input = input.nextLine();
			try {
				Selection = Integer.parseInt(_input);
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid number!");
				continue;
			}
		}
		return Selection;
	}

	public int SelectionChecker(int minValue, int MaxValue) {
		boolean ValidSelection = false;
		int Selection = -1;
		while (ValidSelection == false) {
			String _input = input.nextLine();
			try {
				Selection = Integer.parseInt(_input);
				if (Selection >= minValue && Selection <= MaxValue) {
					ValidSelection = true;
				} else {
					throw new NumberFormatException();
				}

			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid number!");
				continue;
			}
		}
		return Selection;
	}

	public void ReturnsMenu() {
		System.out.println("Please enter the sale ID");
		int salesID = -10; // Just initialize it to a random variable

		boolean validSelection = false;
		while (validSelection == false) {
			try {
				salesID = Integer.parseInt(input.nextLine());
				validSelection = true;
			} catch (NumberFormatException e) {
				System.out.println("Please enter a salesID Number!");
				continue;
			}
		}

		Return newReturn = new Return(operatingRegister, salesID);

		System.out.println("Please selection the following option");
		System.out.println("1.  Return all items.");
		System.out.println("2.  Return particular Item");
		int Selection = SelectionChecker(1, 2);

		switch (Selection) {
		case 1:
			newReturn.returnAllItems();
			break;
		case 2:
			boolean finishedAddingItem = false;

			while (finishedAddingItem == false) {
				System.out.println("Please enter the Item ID");
				int removalID = SelectionChecker();
				System.out.println("Please enter the associated quantity");
				Number removalQuan = formatChecker();
				newReturn.addReturningItem(removalID, removalQuan);
				System.out.println("All items added? Enter \"Y\"/\"N\"");
				String finished = input.nextLine();

				if (finished.equalsIgnoreCase("y")) {
					finishedAddingItem = true;
				} else if (finished.equalsIgnoreCase("n")) {
					continue;
				} else {
					System.out.println("Invalid selection.  Please try adding the item again!");
				}
			}

			newReturn.processPayment();
			CashierMenu();

		}

	}

	private void managerMenu() {
		double Sale;
		double Return;
		double Invintory;
		double report;

		Scanner Input = new Scanner(System.in);

		System.out.println(" 1 Sale");
		System.out.println(" 2 Return");
		System.out.println(" 3 Invintory");
		System.out.println(" 4 X Report");
		System.out.println(" 5 Z Report");
		System.out.println(" X Log Out");
		int choice = -1;
		while (choice < 0 || choice > 5) {
			try {
				System.out.println(" Enter Your Selection");
				choice = Integer.parseInt(Input.nextLine());
			}

			catch (NumberFormatException e) {
				System.out.println("Invalied Selection");
			}
		}
		switch (choice) {

		case 1:

			SalesMenu();

		case 2:

			ReturnsMenu();

			break;

		case 3:
			System.out.println(" 4 InvintoryList");
			System.out.println(" 5 Order Item");
			System.out.println(" 6 Order Received");
			System.out.println(" 7 X Report");
			System.out.println(" 8 Z Report");
			System.out.println(" 9 Remove Item");

		case 4:

			Inventory.getInventoryList();

		case 5:
			Inventory.orderItems();

		case 6:
			Inventory.orderReceived(choice);

		case 7:

			// X Report
		case 8:
			// Z Report
		case 9:
			// Inventory.removeItem(choice);
			break;
		}
		return;

	}

}
