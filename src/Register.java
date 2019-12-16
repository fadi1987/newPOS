
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
		this.RegisterID = RegisterID;
		registerTransactions = new ArrayList<Transaction>();
		registerTransactionAmount = 0.00;
		cashAmount = 0.00;
		cashierSales = new HashMap<Integer, Double>();
	}
	
	public Register(int RegisterID, double existingCash) {
		this.RegisterID = RegisterID;
		registerTransactions = new ArrayList<Transaction>();
		registerTransactionAmount = 0.00;
		cashAmount = existingCash;
		cashierSales = new HashMap<Integer, Double>();
	}
	
	public void initiateLogin(String userName, String password) {
		loginModule = new Login();
		loginModule.checkLogin(userName, password);
		currentUser = loginModule.getLoggedInUser();
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
		
		System.out.println("Please enter Cashier ID.");
		Scanner input = new Scanner(System.in);
		int ID = input.nextInt();
		
		System.out.println("Please enter A specific day in the format of yyyy-MM-dd");
		String saidDay = input.nextLine();
		DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		
		String path = "XReport" + ID + saidDay +".csv";
		FileWriter csvWriter = new FileWriter(path, false);
		BufferedWriter buffWriter = new BufferedWriter(csvWriter);
		PrintWriter pw = new PrintWriter(buffWriter);

		
		pw.println("Cashier Name" + "," + "Trsansaction Type" + "," + "Transaction Amount" + "," + "Date" + "," + "Time");
		for (Transaction x : overallTransactions) {
			
			if (x.getUser().getUserID() == ID && x.getTime().format(dayFormatter).equals(saidDay)) {
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
