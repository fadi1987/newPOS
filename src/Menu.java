import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Scanner;
import java.io.IOException;
import java.lang.NumberFormatException;

public class Menu {

	Register register1 = new Register(1, 1000.0);
	Register register2 = new Register(2, 500.0);
	Register operatingRegister;
	String userType;
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
		System.out.println("Please enter the register number you are on. 1 or 2. ");
		RegisterSelection();

	}

	private void RegisterSelection() {
		int registerSelection = SelectionChecker(1, 2);

		switch (registerSelection) {
		case 1:
			operatingRegister = register1;
			break;
		case 2:
			operatingRegister = register2;
			break;
		}

		PrintMenu();

	}

	private void PrintMenu() {
		String Username = "";
		String Password = "";

		System.out.println();
		System.out.println();
		System.out.println();

		System.out.println("Please Enter your UserName  ");
		Username = input.nextLine();
		System.out.println("Please Enter your password");
		Password = input.nextLine();
		operatingRegister.initiateLogin(Username, Password);

		userType = Login.getUserLookup().get(Username).getRole();

		if (userType.equalsIgnoreCase("Cashier")) {
			CashierMenu();
		} else {
			managerMenu();
		}
	}

	private void CashierMenu() {
		System.out.println("1. Sale");
		System.out.println("2. Return");
		System.out.println("3. Log out");

		int choice = SelectionChecker(1, 3);

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
		}
	}

	public void SalesMenu() {

		Sales newSale = new Sales(operatingRegister);
		int selection = -1;
		
		System.out.println("Please enter the Item ID");
		int itemID1 = integerChecker();
		System.out.println("Please enter the associated quantity");
		Number quan1 = formatChecker();
		newSale.addItem(itemID1, quan1);

		while (selection != 3) {
			System.out.println("1. Add more Item ");
			System.out.println("2. Remove Item ");
			System.out.println("3. finalize the sale ");

			selection = SelectionChecker(1, 3);

			switch (selection) {
			case 1:
				System.out.println("Please enter the Item ID");
				int itemID = integerChecker();
				System.out.println("Please enter the associated quantity");
				Number quan = formatChecker();
				newSale.addItem(itemID, quan);
				break;
			case 2:
				System.out.println("Please enter the Item ID");
				int removalID = integerChecker();
				System.out.println("Please enter the associated quantity");
				Number removalQuan = formatChecker();
				newSale.removeItem(removalID, removalQuan);
				break;
			case 3:
				newSale.processPayment();
				if (userType.equalsIgnoreCase("cashier")) {
					CashierMenu();
				} else {
					managerMenu();
				}

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
			try {
				quan = Double.parseDouble(_input);
			} catch (NumberFormatException e1) {
				System.out.println("Please enter a valid number!");
				formatChecker();
			}
		}

		return quan;
	}

	public int integerChecker() {
		boolean ValidSelection = false;
		int numberInt = -1;
		while (ValidSelection == false) {
			String _input = input.nextLine();
			try {
				numberInt = Integer.parseInt(_input);
				ValidSelection = true;
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid number!");
				continue;
			}
		}
		return numberInt;
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
				System.out.println("Please enter a valid salesID Number!");
				continue;
			}
		}

		Return newReturn = new Return(operatingRegister, salesID);

		System.out.println("Please selection the following option");
		System.out.println("1. Return all items from the sales.");
		System.out.println("2. Return particular Item");
		int Selection = SelectionChecker(1, 2);

		switch (Selection) {
		case 1:
			newReturn.returnAllItems();
			break;
		case 2:
			boolean finishedAddingItem = false;

			while (finishedAddingItem == false) {
				System.out.println("Please enter the Item ID");
				int removalID = integerChecker();
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
			if (userType.equalsIgnoreCase("cashier")) {
				CashierMenu();
			} else if (userType.equalsIgnoreCase("manager")) {
				managerMenu();
			} else {
				System.out.println("Unknown role.");
			}

		}

	}

	private boolean goBackToInventoryMenu() {
		Boolean goback = false;
		System.out.println("Go back to Inventory menu? Enter \"Y\"/\"N\"");
		String selection = input.nextLine();
		if (selection.equalsIgnoreCase("y")) {
			goback = true;
		} else if (selection.equalsIgnoreCase("n")) {
			goback = false;
		} else {
			System.out.println("Invalid entry. Please try again!");
			goBackToInventoryMenu();
		}

		return goback;

	}

	private void Inventory() {

		System.out.println("Please Select one of the following: ");
		System.out.println("1. Remove item from inventory");
		System.out.println("2. Print inventory report");
		System.out.println("3. Order existing items");
		System.out.println("4. Create Items for sale");
		System.out.println("5. Change order quantity");
		System.out.println("6. Updating inventory from order received");
		System.out.println("0. Go back to main menu");

		int Selection = SelectionChecker(0, 6);

		switch (Selection) {
		
		case 0:
			managerMenu();
			break;

		case 1:
			System.out.println("Please enter the Item ID");
			int itemID = integerChecker();
			Item toBeRemoved = Inventory.getItemMap().get(itemID);
			if (toBeRemoved != null)
				Inventory.removeItem(toBeRemoved);
			else
				System.out.println("No such item exist in the inventory.");

			Inventory();
			break;

		case 2:
			// System.out.println(Inventory.getInventoryList());
			Inventory.printReports();
			System.out.println("Printed, please check \"inventoryReport.csv\".");
			Inventory();
			break;

		case 3:
			System.out.println("Please Select one of the following:");
			System.out.println("1. Order All Items below threshhold");
			System.out.println("2. Order specific item by item number");
			System.out.println("3. Order Item by specifying item ID and overriding default order quantity");

			int selection = SelectionChecker(1, 3);
			
			switch(selection) {
			case 1:
				Inventory.orderItems();
				System.out.print("All items below threshhold ordered!");
				break;
			case 2:
				System.out.println("Please enter the item ID");
				int itemNumber = integerChecker();
				
				Item toBeOrdered = Inventory.getItemByID(itemNumber);
				if (toBeOrdered != null) {
					Inventory.orderItems(itemNumber);
				} else {
					System.out.println("No such item found.  Please create the item first.");
				}
				
				System.out.println("Item ordered.");
				break;
			case 3:
				System.out.println("Please enter the item ID");
				int _itemNumber = integerChecker();
				System.out.println("Please specify ordering quantity.");
				Number orderQuantity = formatChecker();
				
				Item _toBeOrdered = Inventory.getItemByID(_itemNumber);
				if (_toBeOrdered != null) {
					Inventory.orderItems(_itemNumber, orderQuantity);
				} else {
					System.out.println("No such item found.  Please create the item first.");
				}
				System.out.println("Item ordered.");
				break;
			}
			
			Inventory();
			break;

		case 4:
			System.out.println("Please enter the Item Name");
			String _name = input.nextLine();
			System.out.println("Please enter the supplier name");
			String _supplier = input.nextLine();
			System.out.println("Please enter the quantity, if it is a sold by weight, please enter a double instead of integer.");
			Number _quantity = formatChecker();
			System.out.println("Please set the threshhold for this item");
			Number _thre = formatChecker();
			System.out.println("Please set the price");
			double _price = input.nextDouble();
			System.out.println("Please enter the item ID. Be sure this ID is NOT given to some other item");
			int thisItemID = integerChecker();
			System.out.println("Specify the order quantity.  The default quantity that the you would order should the item quantity fall below this threshhold.");
			Number _orderQuantity = formatChecker();
			
			Item whatever = new Item(_name, _supplier, _quantity, _thre, _price, thisItemID, _orderQuantity);
			System.out.println("Item created!");
			
			break;

		case 5:
			System.out.println(" Please Enter your Item ID");
			int itemID1 = integerChecker();
			System.out.println("Please set the new orderQuantity");
			Number orderQuantity = formatChecker();
			System.out.println();
			Inventory.orderItems(itemID1, orderQuantity);
			break;
		}

	}

	private void managerMenu() {

		System.out.println("1. Sale");
		System.out.println("2. Return");
		System.out.println("3. Inventory");
		System.out.println("4. Print X Report ");
		System.out.println("5. Print Z Report ");
		System.out.println("0. Log Out");
		int choice = SelectionChecker(0, 5);
		switch (choice) {

		case 0:
			PrintMenu();
			break;
			
		case 1:
			SalesMenu();
			break;

		case 2:
			ReturnsMenu();
			break;

		case 3:
			Inventory();
			break;
		case 4:
			try {
				Register.getXReport();
			} catch (IOException e1) {
				System.out.println("Something went wrong.  Please make sure the file isn't already open and try again.");
			}
			managerMenu();

		case 5:
			try {
				Register.getZReport();
			} catch (IOException e) {
				System.out.println("Something went wrong.  Please make sure the file isn't already open and try again.");
			}
			managerMenu();
			break;

		case 6:
			try {
				Register.getZReport();
			} catch (IOException e) {
				System.out.println("Something went wrong.  Please make sure the file isn't already open and try again.");
			}
			managerMenu();
			break;
		}
	}

}