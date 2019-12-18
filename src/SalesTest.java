import static org.junit.Assert.*;

import org.junit.Test;

public class SalesTest {

	@Test
	public void testUpdateInventory() {
		Inventory whatever = new Inventory("afa");
		Item laptop = new Item("HP Envy 17.3 Laptop", "HP", 20, 2, 899.99, 100, 30);
		Item apple = new Item("Apple", "Fuji", 70.1, 40.0, 2.99, 101, 300.4);
		User whoever = new User("Zhou", "Fang", "Zhou", "Fang", "ah", "well", "random", 1);
		Register one = new Register(4);
		one.setCurrentUser(whoever);
		Sales hello = new Sales(one);
		hello.addItem(100, 2);
		hello.processPayment();
		assertEquals(18, laptop.getQuantity());
		
	}

	@Test
	public void testProcessPayment() {
		Inventory whatever = new Inventory("afa");
		Item laptop = new Item("HP Envy 17.3 Laptop", "HP", 20, 2, 899.99, 100, 30);
		Item apple = new Item("Apple", "Fuji", 70.1, 40.0, 2.99, 101, 300.4);
		User whoever = new User("Zhou", "Fang", "Zhou", "Fang", "ah", "well", "random", 1);
		Register one = new Register(4, 300.0);
		one.setCurrentUser(whoever);
		Sales hello = new Sales(one);
		hello.addItem(100, 2);
		hello.processPayment();
		assertTrue(one.getCashAmount()*1.01>2212.48 && one.getCashAmount()*0.99<2212.48);
	}

}
