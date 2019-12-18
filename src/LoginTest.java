import static org.junit.Assert.*;

import org.junit.Test;

public class LoginTest {

	@Test
	public void testAddUser() {
		Login hello = new Login();
		User zhou = new User("zhou", "zhou", "Zhou", "Fang", "zhou.fang@stthomas.edu", "55555555555", "Cashier", 1);
		hello.addUser(new User("ali", "ali", "Syed", "Naqvi", "lalala@stthomas.edu", "55555555555", "Manager", 2));
		assertEquals(3,hello.getUserList().size());
	}
	@Test
	public void testCheckLogin() {
		Login hello = new Login();
		
		User zhou = new User("zhou", "zhou", "Zhou", "Fang", "zhou.fang@stthomas.edu", "55555555555", "Cashier", 1);
		hello.addUser(new User("ali", "ali", "Syed", "Naqvi", "lalala@stthomas.edu", "55555555555", "Manager", 2));
		hello.checkLogin("ali", "ali");
		assertTrue(hello.getIsLoggedIn());
		
	}

}
