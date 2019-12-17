
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;
import java.time.format.DateTimeFormatter;

public class Register {
	private double registerTransactionAmount;
	private final int RegisterID;
	ArrayList<Transaction> registerTransactions;
	static ArrayList<Transaction> overallTransactions = new ArrayList<Transaction>();
	static HashMap<Integer, Double> cashierSales; //Cashier ID and the Amount of sales
	private User currentUser;
	private double cashAmount;
	private Login loginModule;
	
	
	public Register(int RegisterID) {
		loginModule = new Login();
		this.RegisterID = RegisterID;
		registerTransactions = new ArrayList<Transaction>();
		registerTransactionAmount = 0.00;
		cashAmount = 0.00;
		cashierSales = new HashMap<Integer, Double>();
	}
	
	public Register(int RegisterID, double existingCash) {
		loginModule = new Login();
		this.RegisterID = RegisterID;
		registerTransactions = new ArrayList<Transaction>();
		registerTransactionAmount = 0.00;
		cashAmount = existingCash;
		cashierSales = new HashMap<Integer, Double>();
	}
	
	public void initiateLogin(String userName, String password) {
		loginModule.checkLogin(userName, password);
		if (loginModule.getIsLoggedIn() == true) {
			currentUser = loginModule.getLoggedInUser();
		} else {
			Scanner input = new Scanner(System.in);
			System.out.println("Please Enter your UserName  ");
			String _Username = input.nextLine();
			System.out.println("Please Enter your password");
			String _Password = input.nextLine();
			initiateLogin(_Username, _Password);
		}
	}
	
	public void addTransaction(Transaction transaction) {
		registerTransactions.add(transaction);
		overallTransactions.add(transaction);
		updateAmount(transaction);
		updateCashierReport(transaction);
	}
	
	public void addCash(double amount) {
		cashAmount += amount;
	}
	
	public void removeCash(double amount) {
		cashAmount -= amount;
	}
	
	public void updateAmount(Transaction toBeUpdated) {
		if (toBeUpdated instanceof Sales) {
			registerTransactionAmount = registerTransactionAmount + toBeUpdated.getTotal();
		} else {
			registerTransactionAmount = registerTransactionAmount - toBeUpdated.getTotal();
		}
	}

	public void updateCashierReport (Transaction trans) {
		if (trans.getTransType() == TransactionType.Sales) {
			double saleAmount = trans.getTotal();
			int userID = trans.getUser().getUserID();
			
			if (cashierSales.containsKey(userID)) {
				double previousAmount = cashierSales.get(userID);
				cashierSales.replace(userID, previousAmount + saleAmount);
			} else {
				cashierSales.put(userID, saleAmount);
			}
		}
	}
	
	public Transaction findTransaction(int TransactionID) {
		int index = 0;
		int actualIndex=999999999;
		for (Transaction x : overallTransactions) {
			if(x.getTransactionID() == TransactionID) {
				actualIndex=index;
				break;
			} else {
				index++;
			}
		}
		if (actualIndex != 999999999)
			return overallTransactions.get(actualIndex);
		else
			return null;
		
	}
	
	public double getTotalSaleAmountByCashier(int userID) {
		double saleAmount = cashierSales.getOrDefault(userID, 0.00); //Default to make sure that a new Cashier who have not made sales will have a chance to do so.
		return saleAmount;
	}
	
	public static void printSalesReport() throws IOException {
		String path = "salesReportByCashier.csv";
		FileWriter csvWriter = new FileWriter(path, false);
		BufferedWriter buffWriter = new BufferedWriter(csvWriter);
		PrintWriter pw = new PrintWriter(buffWriter);
		DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		
		pw.println("Cashier Name" + "," + "Trsansaction Type" + "," + "Transaction Amount" + "," + "Date" + "," + "Time");
		for (Transaction x : overallTransactions) {
			pw.println(x.getUser().GetFirstName() + " " + x.getUser().GetLastName() + "," +
					x.getTransType().toString() + "," +
					x.getTotal() + "," +
					x.getTime().format(dayFormatter) + "," +
					x.getTime().format(timeFormatter)
					);
		}
		
		pw.flush();
		pw.close();
		
	}
	
	public static void getXReport() throws IOException {
		
		System.out.println("Please enter User ID for the user you want to see.");
		Scanner input = new Scanner(System.in);
		int ID = input.nextInt();
		
		System.out.println("Please enter A specific day in the format of yyyy-MM-dd");
		Scanner try2 = new Scanner(System.in); //Somehow this line got skipped if I don't create another scanner... Not really sure why.
		String saidDay = try2.nextLine();
		
		
		
		Shifts shift = getShiftEnum();
		
		
		
		DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		
		String path = "XReport" + ID + saidDay +".csv";
		
		FileWriter csvWriter = new FileWriter(path, false);
		BufferedWriter buffWriter = new BufferedWriter(csvWriter);
		PrintWriter pw = new PrintWriter(buffWriter);

		
		pw.println("Cashier Name" + "," + "Trsansaction Type" + "," + "Transaction Amount" + "," + "Date" + "," + "Time");
		for (Transaction x : overallTransactions) {
			
			if (x.getUser().getUserID() == ID && x.getTime().format(dayFormatter).equals(saidDay) && x.getCashierShift() == shift) {
				pw.println(x.getUser().GetFirstName() + " " + x.getUser().GetLastName() + "," +
					x.getTransType().toString() + "," +
					x.getTotal() + "," +
					x.getTime().format(dayFormatter) + "," +
					x.getTime().format(timeFormatter)
						);
			} 
		}
		
		System.out.println("The report has been saved to local folder with the file name titled \"" + path + "\"");
		
		pw.flush();
		pw.close();
		
	}
	
	private static Shifts getShiftEnum() {
		Scanner ShiftINput = new Scanner(System.in);
		System.out.println("Please enter the shift in question.  Please only enter \"Morning\", \"Afternoon\", or \"Evening\"");
		Shifts one = Shifts.Morning;
		boolean validEntry = false;
		while (validEntry == false) {
			String input = ShiftINput.nextLine();
			if (input.equalsIgnoreCase("morning")) {
				one = Shifts.Morning;
				validEntry = true;
			} else if (input.equalsIgnoreCase("afternoon")) {
				one = Shifts.Afternoon;
				validEntry = true;
			} else if (input.equalsIgnoreCase("Evening")) {
				one = Shifts.Evening;
				validEntry = true;
			} else {
				System.out.println("Invalid entry, please only enter Morning, Afternoon or Evening. Please type again.");
				validEntry = false;
			}
		}
		
		return one;
		
	}
	
	public static void getZReport() throws IOException {

		Scanner input = new Scanner(System.in);
		System.out.println("Please enter A specific day in the format of <yyyy-MM-dd>.");
		String saidDay = input.nextLine();
		
		DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		
		String path = "ZReport" + saidDay +".csv";
		FileWriter csvWriter = new FileWriter(path, false);
		BufferedWriter buffWriter = new BufferedWriter(csvWriter);
		PrintWriter pw = new PrintWriter(buffWriter);

		
		pw.println("Cashier Name" + "," + "Trsansaction Type" + "," + "Transaction Amount" + "," + "Date" + "," + "Time");
		for (Transaction x : overallTransactions) {
			
			if (x.getTime().format(dayFormatter).equals(saidDay)) {
				pw.println(x.getUser().GetFirstName() + " " + x.getUser().GetLastName() + "," +
					x.getTransType().toString() + "," +
					x.getTotal() + "," +
					x.getTime().format(dayFormatter) + "," +
					x.getTime().format(timeFormatter)
						);
			} 
		}
		
		System.out.println("The report has been saved to local folder titled \"" + path + "\"");
		
		pw.flush();
		pw.close();
		
	}
	
	
	public User getCurrentUser() {	
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	public double getRegisterTransactionAmount() {
		return registerTransactionAmount;
	}
	
	public int getRegisterID() {
		return RegisterID;
	}

	public ArrayList<Transaction> getRegisterTransactions() {
		return registerTransactions;
	}

	public static ArrayList<Transaction> getOverallTransactions() {
		return overallTransactions;
	}

	public double getCashAmount() {
		return cashAmount;
	}

}
