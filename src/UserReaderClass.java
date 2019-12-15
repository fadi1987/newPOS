import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UserReaderClass {

	/*
	 * public static void main(String[] args) throws IOException { // create
	 * credentials storage file if does not exist CreateStorageFile();
	 * ReadLoginData(); }
	 */
	private static String _pathToStorageFileUserInfo = "Userinfo.csv";

	private static void CreateStorageFile() throws IOException {
		File storageFile = new File(_pathToStorageFileUserInfo);

		// Create the file
		if (storageFile.createNewFile()) {
			System.out.println("Storage File is created!");
		} else {
			System.out.println("Storage File already exists.");

			return;
		}
		
		// Write Content
				FileWriter storageFileWriter = new FileWriter(storageFile);
				storageFileWriter.append("FirstName");
				storageFileWriter.append(",");
				storageFileWriter.append("LastName");
				storageFileWriter.append(",");
				storageFileWriter.append("UserName");
				storageFileWriter.append(",");
				storageFileWriter.append("Password");
				storageFileWriter.append(",");
				storageFileWriter.append("EmailAddress");
				storageFileWriter.append(",");
				storageFileWriter.append("PhoneNumber");
				storageFileWriter.append(",");
				storageFileWriter.append("UserID");
				storageFileWriter.append(",");
				storageFileWriter.append("Role");
				storageFileWriter.append(",");
				

				storageFileWriter.flush();
				storageFileWriter.close();
	}
	
	private static void ReadLoginData() throws IOException {
		String row;

		//ArrayList<User> storedUsers = new ArrayList<User>();

		BufferedReader csvReader = new BufferedReader(new FileReader(_pathToStorageFileUserInfo));

		while ((row = csvReader.readLine()) != null) {
			String[] userData = row.split(",");

		

			User rowUser = new User(userData[0], userData[1], userData[2], userData[3], userData[4], userData[5], userData[6],Integer.parseInt(userData[7]));

			//storedUsers.add(rowUser);
		}

		csvReader.close();

		//return storedUsers;
	}
}