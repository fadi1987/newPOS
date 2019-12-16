import java.io.IOException;

public class MainMethod1 {
	public static void main(String[] args) throws IOException {
		Inventory myStore = new Inventory("MyStore");
		Item apple = new Item("Apple", "Fadi's Fruits", 789.1, 100.0, 2.99, 1, 400.0);
		Item Bag  = new Item("Bag", "Fadi's bag", 30, 5, 2.99, 2, 10);
		
		UserReaderClass reader = new UserReaderClass();

		Menu menu1 = new Menu();
		menu1.PrintHeader();

	}

}