
import java.util.ArrayList;
import java.util.HashMap;

public class User {

	private String UserName;
	private String Password;
	private String FirstName;
	private String LastName;
	private String EmailAddress;
	private String PhoneNumber;
	private String role; // Enum did not work properly in the UI... so swapping back to string for the time being.
	public Login login;
	private int UserID; // Users have to manually set this value. Have to be unique

	public User(String userName, String password, String firstName, String lastName, String emailAddress,
			String phoneNumber, String role, int UserID) {

		this.UserName = userName;
		this.Password = password;
		this.FirstName = firstName;
		this.LastName = lastName;
		this.EmailAddress = emailAddress;
		this.PhoneNumber = phoneNumber;
		this.role = role;
		this.UserID = UserID;

		addtoCollections();

	}

	private void addtoCollections() {
		if (login == null) {
			login = new Login();
		}

		Login.addUser(this);
	}

	public static void removeUser(int _UserID) {
		Login.removeUser(_UserID);
	}

//	public void UserDictionary() {
//		Dictionary<String, Integer> UserDictionary = new Hashtable<String, Integer>();
//
//		UserDictionary.put(UserName, UserId);
//		UserDictionary.put(Password, UserId);
//		UserDictionary.put(FirstName, UserId);
//		UserDictionary.put(LastName, UserId);
//		UserDictionary.put(PhoneNumber, UserId);
//		UserDictionary.put(EmailAddress, UserId);
//
//	}

	public int getUserID() {
		return this.UserID;
	}

	public String GetUserName() {
		return UserName;
	}

	public String GetPassword() {
		return Password;
	}

	public void setPassword(String password) {
		this.Password = password;
	}

	public String GetFirstName() {
		return this.FirstName;
	}

	public String GetLastName() {
		return this.LastName;
	}

	public String toString() {
		return FirstName + " " + LastName + " " + PhoneNumber + " " + EmailAddress;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		this.UserName = userName;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		this.FirstName = firstName;
	}

	public String getEmailAddress() {
		return EmailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.EmailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.PhoneNumber = phoneNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public String getPassword() {
		return Password;
	}

	public void setUserID(int userID) {
		this.UserID = userID;
	}
}
