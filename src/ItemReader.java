import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ItemReader {

	/*
	 * public static void main(String[] args) throws IOException { // create
	 * credentials storage file if does not exist CreateStorageFile();
	 * ReadLoginData(); }
	 */
	private static String ItemList = "ItemList.csv";

	public ItemReader() throws IOException {
		ReadLoginData();
	}

	private void ReadLoginData() throws IOException {
		String row;

		BufferedReader csvReader = new BufferedReader(new FileReader(ItemList));
		
		row = csvReader.readLine(); //Skip the first line as it is the title. Don't perform any action on the first line.

		while ((row = csvReader.readLine()) != null) {
			String[] ItemData = row.split(",");
			
			Number quantity = checkNumber(ItemData[2]);
			Number threshhold = checkNumber(ItemData[3]);
			double pricePerUnit = Double.parseDouble(ItemData[4]);
			int itemID = Integer.parseInt(ItemData[5]);
			Number orderQuantity = checkNumber(ItemData[3]);
			
			Item newItem = new Item(ItemData[0], ItemData[1], quantity, threshhold, pricePerUnit, itemID,
					orderQuantity); //Creating users from file.

		}

		csvReader.close();
		// return storedUsers;
	}
	
	private Number checkNumber(String data) {
		Number conversion;
		
		try {
			conversion = Integer.parseInt(data);
		} catch (NumberFormatException e) {
			conversion = Double.parseDouble(data);
		}
		
		return conversion;
		
	}
}