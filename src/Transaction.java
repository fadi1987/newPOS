
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.LocalTime;
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.Map.Entry;

public abstract class Transaction {

	public User handler; //Who is doing the transaction
	protected LocalDateTime time; //Time of transaction
	public ArrayList<Item> items; //What are the involves involved in this transaction
	protected int transactionID;
	static int transactionCounter = 0; //A way to count the number of transaction that took place at the store
	protected double total; //Total amount of the transaction
	protected double subTotal;
	public Register register; //The register that is used to perform transaction.
	protected double taxRate = 0.0625; //Current tax rate.  Feel free to set it differently.
	protected TransactionType transType; //This is an enum, transaction can be either sale or return
	protected boolean TransectionStatus;
	String receipt; //Receipt string;
	String path; //Where we save the receipt
	Shifts cashierShift; //Again, this is an enum, used to set cashier shifts.
	int dayAfternoonBoundary = 12; //12'o as the boundary between morning and afternoon shifts.
	int afternoonEveningBoundary = 16; //4pm as the boundary between afternoon and evening shifts.

	public Transaction(Register register) {
		this.register = register;
		this.handler = register.getCurrentUser(); //Set it as the same user as the user who logged on to the register.
		time = LocalDateTime.now(); //Happening now.. obviously.
		items = new ArrayList<Item>();
		transactionID = transactionCounter++;
		path = "Receipt" + transactionID + ".txt";

		if (time.toLocalTime().getHour() <= 12) {
			cashierShift = Shifts.Morning;
		} else if (time.toLocalTime().getHour() <= 16) {
			cashierShift = Shifts.Afternoon;
		} else {
			cashierShift = Shifts.Evening;
		}
	}

	public double getSubTotal() {
		return subTotal;
	}

//	public void addPayment(Payment payment) {
//		payments.add(payment);
//	}
//	
	public double getTotal() {
		return total;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

//	public ArrayList<Payment> getPayments() {
//		return payments;
//	}

	public int getId() {
		return transactionID;
	}

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public void setTotal(float total) {
		this.total = total;
	}

//	public void setPayments(ArrayList<Payment> payments) {
//		this.payments = payments;
//	}

	public User getUser() {
		return handler;
	}

	public static int getTransactionCounter() {
		return transactionCounter;
	}

	public TransactionType getTransType() {
		return transType;
	}

	public void setTransType(TransactionType transType) {
		this.transType = transType;
	}

	public int getDayAfternoonBoundary() {
		return dayAfternoonBoundary;
	}

	public void setDayAfternoonBoundary(int dayAfternoonBoundary) {
		this.dayAfternoonBoundary = dayAfternoonBoundary;
	}

	public int getAfternoonEveningBoundary() {
		return afternoonEveningBoundary;
	}

	public void setAfternoonEveningBoundary(int afternoonEveningBoundary) {
		this.afternoonEveningBoundary = afternoonEveningBoundary;
	}

	public void setUser(User person) {
		this.handler = person;
	}

	public String printTotals() {
		String output = "";

		// Calculate tax and total

		// Set up ability to format print statements right so everything aligns
		int digits = Double.toString(total).length();
		String format = "%" + digits + ".2f";
		output += String.format("Subtotal: $" + format + "\t", subTotal);
		output += String.format("Tax: $" + format + "\t", Math.ceil(taxRate * subTotal * 100) / 100.0);
		output += String.format("Total: $" + format + "\t", total);

		return output;
	}

	@Override
	public String toString() {
		return receipt;
	};

	public LocalDateTime getTime() {
		return time;
	}

	public Shifts getCashierShift() {
		return cashierShift;
	}

	public void updateRegister() {
		register.addTransaction(this);
	}

	protected double calculateSubtotal(HashMap<Item, Number> _ItemList) { //Calculating subtotal of the entire transaction.
		double itemCost;
		double _subTotal = 0.0;
		for (Entry<Item, Number> pair : _ItemList.entrySet()) {
			itemCost = pair.getKey().getPricePerUnit() * (pair.getValue().doubleValue());
			_subTotal += itemCost;
		}
		int _subtotal1 = (int) (_subTotal * 100.0);
		_subTotal = _subtotal1 / 100.0;

		return _subTotal;
	}

	protected double calculateTotal(double _subttoTal) {
		int totalInt;
		double _total;
		_total = subTotal * (1 + taxRate);
		totalInt = (int) Math.ceil(_total * 100);
		_total = totalInt / 100.00;
		return _total;
	}

	protected void FinishTransaction() { 
		updateRegister(); //Update Register will involve the amount of money being transacted, adding this particular transaction to the list of transactions and update the current amount
		updateInventory(); //Update Inventory upon finishing transaction
		printReceipts(); 
		System.out.println("Transaction complete!");
	}

	public abstract void updateInventory();

	protected abstract void processPayment();

	public abstract String printReceipts();

}
