

import java.util.*;

public class Login {
	private static ArrayList<User> userList;
	private static HashMap<String, String> loginInfoLookup; //UserName and Password.  Key and Value
	private static HashMap<String, User> userLookup; //User Name and User Object.  
	private static HashMap<Integer, User> userIdLookUp; //UserID and User Object.  
	private User loggedInUser;
	private boolean loggedIn;
	
	
	public Login() {
		
		if (userList == null) {
			userList = new ArrayList<User>();
			loginInfoLookup = new HashMap<String, String>();
			userLookup = new HashMap<String, User>();
			userIdLookUp = new HashMap<Integer, User>();
		}
	}

	public static void addUser(User person) {
		userList.add(person);
		loginInfoLookup.put(person.GetUserName(),person.GetPassword());
		userLookup.put(person.GetUserName(),person);
		userIdLookUp.put(person.getUserID(), person);
	}
	
	public static String removeUser(int ID) {
		
		String message = "";
		if (userIdLookUp.containsKey(ID)) {
			User person = userIdLookUp.get(ID);
			String userName = person.GetUserName();
			
			userList.remove(person);
			loginInfoLookup.remove(userName);
			userLookup.remove(userName);
			userIdLookUp.remove(ID);
			message = "User Removed";
		} else {
			message = "User not found. Check User ID and try again.";
		}
		return message;
	}
	
	
	public String checkLogin(String _userName, String _password) {
		String message = "";
		loggedIn = false;
			if (loginInfoLookup.containsKey(_userName)) {
				if (loginInfoLookup.get(_userName).equals(_password)) {
					message = "login successful";
					System.out.println(message);
					loggedInUser = userLookup.get(_userName);
					loggedIn = true;
				}
			} else {
				message = "UserName or password is incorrect, please try again";
				System.out.println(message);
				loggedIn = false;
			}
		return message;
	}
	
	public String resetPassword(String _userName, String resetPassword, String resetPassword_1) {
		String message = "";
		if (loginInfoLookup.containsKey(_userName) && resetPassword.equals(resetPassword_1)) {
			userLookup.get(_userName).setPassword(resetPassword);
			message = "Password set successfully.";
		} else {
			message = "Password reset failed!";
		}
		
		return message;
	}
	
	public ArrayList<User> getUserList() {
		return userList;
	}
	public static HashMap<String, String> getLoginInfo() {
		return loginInfoLookup;
	}
	public static HashMap<String, User> getUserLookup() {
		return userLookup;
	}
	public User getLoggedInUser() {
		return loggedInUser;
	}
	public boolean getIsLoggedIn() {
		return loggedIn;
	}

}
