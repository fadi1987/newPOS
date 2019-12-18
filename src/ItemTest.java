import static org.junit.Assert.*;

import org.junit.Test;

public class ItemTest {

	@Test
	public void testItem() {
		Inventory myInventory = new Inventory("myStore");
		Item one = new Item("HP Envy 17.3 Laptop", "HP", 20, 2, 899.99, 100, 30);
		Item two = new Item("Apple", "Fuji", 70.1, 40.0, 2.99, 101, 300.4);
		assertEquals(20, one.getQuantity());
		assertEquals(300.4, two.getOrderQuantity());
	}
	
	@Test
	public void testDeleteItem() {
		Inventory hello = new Inventory("myStore");
		Item one = new Item("HP Envy 17.3 Laptop", "HP", 20, 2, 899.99, 100, 30);
		Item two = new Item("Apple", "Fuji", 70.1, 40.0, 2.99, 101, 300.4);
		one.deleteItem();
		assertFalse(hello.getInventoryList().contains(one));
	}


	@Test
	public void testSetQuantity() {
		Item one = new Item("HP Envy 17.3 Laptop", "HP", 20, 2, 899.99, 100, 30);
		one.setQuantity(5);
		assertEquals(5, one.getQuantity());
	}

	@Test
	public void testAddQuantity() {
		Item one = new Item("HP Envy 17.3 Laptop", "HP", 20, 2, 899.99, 100, 30);
		one.addQuantity(24);
		assertEquals(44, one.getQuantity());
	}
	
	@Test
	public void testRemoveQuantity() {
		Item one = new Item("HP Envy 17.3 Laptop", "HP", 20, 2, 899.99, 100, 30);
		one.removeQuantity(20);
		assertEquals(0, one.getQuantity());
	}

}
