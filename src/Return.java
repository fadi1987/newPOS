
import javax.swing.*;
import java.text.DateFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.Map.Entry;

public class Return extends Transaction {
	private int saleId;
	private Sales associatedSale;
	private String message;
	private HashMap<Item, Number> returnMap;

	public Return(Register register, int saleId) {
		super(register);
		total = 0;
		subTotal = 0;
		this.saleId = saleId;
		associatedSale = (Sales) register.findTransaction(saleId);
		returnMap = new HashMap<Item, Number>();
		transType = TransactionType.Returns;

		if (associatedSale == null) {
			message = "Cannot find the specified sale.";
			register.getRegisterTransactions().remove(this);
			Register.getOverallTransactions().remove(this);
		}
		// items = new ArrayList<>();
	}

	public int getSaleId() {
		return saleId;
	}

	public void returnAllItems() {
		returnMap = associatedSale.getSaleList();
	}

	public void addReturningItem(int itemID, Number quantity) {
		HashMap<Item, Number> saleCopy = associatedSale.getSaleList();
		String message;

		if (Inventory.getItemMap().containsKey(itemID)) {
			Item obj = Inventory.getItemMap().get(itemID);

			if (saleCopy.containsKey(obj) && quantity.doubleValue() <= saleCopy.get(obj).doubleValue()) {
				message = "Successfully added";
				returnMap.put(obj, quantity);
				message = "Added to returns";
			} else {
				message = "The original sale does not contain this object or that you have entered too many quantities.";
			}
		} else {
			message = "Invalid Item ID";
		}

//        LineItem item = user.getInstance().getSaleById( saleId ).getLineItemByCode( product.getItemID() );
//
//        if (item == null) {
//            return false; //means can't return item because not found
//        }
//
//        LineItem retItem = getLineItemByCode( product.getItemID() );
//        if (retItem == null) {
//            retItem = (new LineItem( product ));
//        } else {
//            subTotal += product.getPrice();
//        }
//        product.increaseQuantity();
//        items.add( retItem );
//        return true;
//
//        if(retItem.getQuantity() < item.getQuantity())
//            {
//            retItem.increaseQuantity();
//            } else{
//                subTotal += product.getPrice();
//            } return true;
//
//        return false; //already returned all of that item
	}

//    public boolean removeReturningItem(Item product){
//        boolean found = false;
//        for(int i = 0; i < items.size(); i++){
//            if(items.get(i).getItem().getItemID() == product.getItemID()){
//                if(items.get(i).getQuantity() == 1){
//                    items.remove(i);
//                }
//                else{
//                    items.get(i).decreaseQuantity();
//                }
//                subTotal-= product.getPrice();
//                product.decreaseQuantity();
//                found = true;
//                break;
//            }
//        }
//        if(!found){//item not in Sale
//            JOptionPane.showMessageDialog (null, "Item not found", "Invalid Input", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
//        return true;
//    }

//    public LineItem getLineItemByCode(int code){
//        for(int i = 0; i< items.size(); i++){
//            if(items.get(i).getItem().getItemID() == code)
//                return items.get(i);
//        }
//        return null;
//    }

	@Override
	public String toString() {
		String output = "****** Return ******* \nSale ID: " + saleId + "\n Return ID: " + transactionID + "\n";
		for (int i = 0; i < items.size(); i++) {
			output += items.get(i).toString();
		}
		return output;
	}

	@Override
	public void updateInventory() {
		for (Entry<Item, Number> x : returnMap.entrySet()) {
			x.getKey().addQuantity(x.getValue());
		}

	}

	@Override
	public void processPayment() {
		subTotal = calculateSubtotal(returnMap);
		total = calculateTotal(subTotal);
		returnPayment();
		FinishTransaction();

	}

	private void returnPayment() {
		register.removeCash(total);

	}

	@Override
	public String printReceipts() {

		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd\tHH:mm:ss");
		String formattedDateTime = myFormatObj.format(time);

		String returns = String.format("%20s %20s %20s \r\n", "Item Name", "Quantity", "Unit Price");
		for (Entry<Item, Number> x : returnMap.entrySet()) {
			returns += String.format("%20s %20s %20s \r\n", x.getKey().getName(), x.getValue(),
					x.getKey().getPricePerUnit());
		}

		receipt = "****** Return ******* \n" + formattedDateTime + "\nSale ID: " + saleId + "\nReturn ID: "
				+ transactionID + "\n" + "\n" + returns + "\n\n\n" + printTotals();

		return receipt;
	}
}