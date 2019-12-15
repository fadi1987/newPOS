
import java.io.IOException;
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.Map.Entry;

public abstract class Transaction {

	public User handler;
	protected LocalDateTime time;
	public ArrayList<Item> items;
	protected int transactionID;
	static int transactionCounter = 0;
	protected double total;
	protected double subTotal;
	//protected ArrayList<Payment> payments; // for when we need to do returns
	public Register register;
	protected double taxRate = 0.0625;
	protected TransactionType transType;
	protected boolean TransectionStatus;
	String receipt;
	String path;

	public Transaction(Register register) {
		this.register = register;
		this.handler = register.getCurrentUser();
		time = LocalDateTime.now();
		items = new ArrayList<Item>();
		transactionID = transactionCounter++;
		path = "Receipt" + transactionID + ".txt";
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
		output += String.format("Tax: $" + format + "\t", Math.ceil(taxRate*subTotal*100)/100.0);
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


	public void updateRegister() {
		register.addTransaction(this);
	}
	
    protected double calculateSubtotal(HashMap<Item,Number> _ItemList) {
    	double itemCost;
    	double _subTotal = 0.0;
    	for (Entry<Item, Number> pair : _ItemList.entrySet()) {
    		itemCost = pair.getKey().getPricePerUnit()*(pair.getValue().doubleValue());
    		_subTotal += itemCost;
    	}
    	int _subtotal1 = (int) (_subTotal*100.0);
    	_subTotal = _subtotal1/100.0;
    	
    	return _subTotal;
    }
    
    protected double calculateTotal(double _subttoTal) {
    	int totalInt;
    	double _total;
    	_total = subTotal*(1+taxRate);
    	totalInt = (int) Math.ceil(_total*100);
    	_total = totalInt/100.00;
    	return _total;
    }

	protected void FinishTransaction() {
		updateRegister();
		updateInventory();
		printReceipts();
	}
	
	public abstract void updateInventory();

	protected abstract void processPayment();

	public abstract String printReceipts();

}
