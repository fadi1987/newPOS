package old;/*
 * POS System
 * Zhou Fang, Anh Nguyen, Fadi Bchara
 * 12/13/2019
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Inventory {

	private static String name;
	private static ArrayList<Item> inventoryList;
	private static HashMap<Integer, Item> itemMap;
	private static int itemCount;
	private static String path;

//	int choice = 0;
//        do {
//		choice = Inventory.promptUser("1. Add item\n2. List all items\n3. Search for item\n4. Sell item\n5. Delete item\n6. Edit item\n7. Save to file\n8. Load from file\n9. Exit").nextInt();
//		switch(choice) {
//			case 1:
//				Inventory.addItem();
//				break;
//			case 2:
//				Inventory.listAllItems();
//				break;
//			case 3:
//				System.out.println(Inventory.searchForItemById());
//				break;
//			case 4:
//				Inventory.deleteItem();
//				break;
//			case 5:
//				Inventory.editItem();
//				break;
//			case 6:
//				Inventory.saveItemToDisk();
//				break;
//			case 7:
//				Inventory.loadItemFromDisk();
//				break;
//			case 8:
//				//let the do-while loop do all the work
//				break;
//		}
//	}while(choice != 8);
//}

	public Inventory(String storeName) {
		this.name = name;
		inventoryList = new ArrayList<Item>();
		itemCount=0;
		path = "inventoryReport.csv";
		itemMap = new HashMap<Integer, Item>();
		
	}

	public static Scanner promptUser(String message) {
		System.out.println(message);
		Scanner scan;
		return scan = new Scanner(System.in);
	}
	
	public static void addToInventory(Item i) {
//		inventoryList.add(item);
//		itemMap.put(item.getItemID(), item);
//		itemCount++;
			inventoryList.add( i );
//		String name, String supplier, Number Quantity, Number Threshold, double pricePerUnit, int ItemID, Number orderQuantity
			i.setName(promptUser("Enter item name").next());
			i.setSupplier(promptUser("Enter Supplier Name").next());
			i.setQuantity(promptUser("Enter Quantity").nextInt());
			i.setThreshold(promptUser("Enter Threshold").nextInt());
			i.setPricePerUnit(promptUser("Enter Item Price").nextDouble());
			i.setItemID( promptUser( "Enter item ID" ).nextInt());
			i.setOrderQuantity(promptUser("Enter order Quantity").nextInt());

	}
	
	public static void removeItem(Item item) {
		inventoryList.remove(item);
		itemMap.remove(item.getItemID());
		itemCount--;
	}

	public void listAllItems(){
		for(Item i: inventoryList){
			System.out.println(i);
		}
	}

	public Item searchForItemById() {
		int id = promptUser("Enter item ID").nextInt();

		for(Item i: inventoryList) {
			if(i.getItemID() == id) {
				return i;
			}
		}
		return null;
	}

	public void editItem(){
		Item i = searchForItemById();
		inventoryList.remove( i );
//		String name, String supplier, Number Quantity, Number Threshold, double pricePerUnit, int ItemID, Number orderQuantity
		i.setName(promptUser("Enter item name").next());
		i.setSupplier(promptUser("Enter Supplier Name").next());
		i.setQuantity(promptUser("Enter Quantity").nextInt());
		i.setThreshold(promptUser("Enter Threshold").nextInt());
		i.setPricePerUnit(promptUser("Enter Item Price").nextDouble());
		i.setItemID( promptUser( "Enter item ID" ).nextInt());
		i.setOrderQuantity(promptUser("Enter order Quantity").nextInt());
	}

	
	public static void orderItems() { //Loop through the list and check the health on all items, if it is below the threshhold, order the item.
		for (Item i : inventoryList) {
			if ((double) i.getQuantity() < (double)i.getThreshold()) {
				i.setOrderingQuantity(i.getOrderQuantity());
			}
		}
	}
	
	public static void orderItems(int itemID) { //Manually order item, automatically orders the amount of items originally specified.
		Item item = itemMap.get(itemID);
		item.setOrderingQuantity(item.getOrderQuantity());
	}
	
	public static void orderItems(int itemID, Number orderQuantity) { //
		Item item = itemMap.get(itemID);
		item.setOrderingQuantity(orderQuantity);
	}
	
	public static void orderReceived(int itemID) {
		Item item = itemMap.get(itemID);
		item.addQuantity(item.getOrderingQuantity());
		item.setOrderingQuantity(0);
	}
	
	public static ArrayList<Item> getInventoryList() {
		return inventoryList;
	}
	
	public static HashMap<Integer, Item> getItemMap() {
		return itemMap;
	}


	public void saveItemToFile() {
		try {
			ObjectOutput objectOutput = new ObjectOutputStream(new FileOutputStream("src/ItemList.csv"));
			objectOutput.writeObject(inventoryList);
			objectOutput.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void loadItemList() {
		try {
			File file = new File("src/ItemList.csv");
			ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
			inventoryList = (ArrayList<Item>)objectInputStream.readObject();
			objectInputStream.close();
		} catch (IOException ex) {
			Logger.getLogger(Inventory.class.getName()).log( Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static String printReports() {
		String message = "";

		try {
			FileWriter csvWriter = new FileWriter(path, false);
			BufferedWriter buffWriter = new BufferedWriter(csvWriter);
			PrintWriter pw = new PrintWriter(buffWriter);

			pw.println("Item Name" + "," + "Current Item Quantity" + "," + "Threshold" + "," + "Supplier" + "," + "Pending Item Quantity");

			for (int i = 0; i<itemCount; i++) {
				pw.println(inventoryList.get(i).getName() + "," +
						inventoryList.get(i).getQuantity().toString() + "," +
						inventoryList.get(i).getThreshold().toString() + "," +
						inventoryList.get(i).getSupplier() + "," +
						inventoryList.get(i).getOrderingQuantity().toString()
				);
			}
			pw.flush();
			pw.close();
			message = "Report saved.  Please check the file titled \"inventoryReport.csv\".";
		} catch (IOException e) {
			message = "I/O Exception occurred.  Report not generated. Please make sure the file is closed";
		} finally {
			return message;
		}
	}

}
