import java.io.IOException;

public class MainMethod1 {
	public static void main(String[] args) throws IOException {

		
		UserReaderClass reader = new UserReaderClass(); //Reading and creating users from Userinfo.csv.  Please go there to find a created list of user name and password.
		Inventory hello = new Inventory("MyInventory"); //Instantiating an inventory, otherwise, no items can be added.
		ItemReader one = new ItemReader(); //creating some item for tests.  It is reading from 
		
		Menu menu1 = new Menu();
		


	}

}