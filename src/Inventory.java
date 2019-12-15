/*
 * POS System
 * Zhou Fang, Anh Nguyen, Fadi Bchara
 * 12/13/2019
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
	private static String name;
	private static ArrayList<Item> inventoryList;
	private static HashMap<Integer, Item> itemMap;
	private static int itemCount;
	private static String path;
	
	public Inventory(String storeName) {
		this.name = name;
		inventoryList = new ArrayList<Item>();
		itemCount=0;
		path = "inventoryReport.csv";
		itemMap = new HashMap<Integer, Item>();
		
	}
	
	public static void addToInventory(Item item) {
		inventoryList.add(item);
		itemMap.put(item.getItemID(), item);
		itemCount++;
	}
	
	public static void removeItem(Item item) {
		inventoryList.remove(item);
		itemMap.remove(item.getItemID());
		itemCount--;
	}
	
	public static void orderItems() { //Loop through the list and check the health on all items, if it is below the threshhold, order the item.
		for (Item i : inventoryList) {
			if ((double) i.getQuantity() < (double)i.getThreshhold()) {
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
	
//	private int findIndex(Item item) {
//		int index = inventoryList.indexOf(item);
//		return index;
//	}
	
	public static String printReports() {
		String message = "";
		
		try {
			FileWriter csvWriter = new FileWriter(path, false);
			BufferedWriter buffWriter = new BufferedWriter(csvWriter);
			PrintWriter pw = new PrintWriter(buffWriter);
			
			pw.println("Item Name" + "," + "Current Item Quantity" + "," + "Threshhold" + "," + "Supplier" + "," + "Pending Item Quantity");
			
			for (int i = 0; i<itemCount; i++) {
				pw.println(inventoryList.get(i).getName() + "," +
						inventoryList.get(i).getQuantity().toString() + "," +
						inventoryList.get(i).getThreshhold().toString() + "," +
						inventoryList.get(i).getSupplier() + "," +
						inventoryList.get(i).getOrderingQuantity().toString()
						);
			}
			pw.flush();
			pw.close();
			message = "Report saved.  Please check the file titled \"inventoryReport.csv\".";
		} catch (IOException e) {
			message = "I/O Exception occured.  Report not generated. Please make sure the file is closed";
		} finally {
			return message;
		}
	}

}
