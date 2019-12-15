

/*
 * POS System
 * Zhou Fang, Anh Nguyen, Fadi Bchara
 * 12/13/2019
 */
public class Item {
	private String name;
	private Number Quantity; //Using a number property to indicate it can be either be a whole number or be of the unit for a weight such as lb.
	private Number Threshhold; //The threshhold number that if fallen below of, will require additional order.
	private double pricePerUnit;
	private int ItemID;
	private Number orderQuantity; //The number to be ordered should the quantity falls below threshold
	private Number orderingQuantity; //The quantity of which this particular item is currently getting ordered.
	private String supplier;
	private boolean isInt; //Determines weather the quantity is in the unit of weight or per unit.
	
	public Item(String name, String supplier, Number Quantity, Number Threshhold, double pricePerUnit, int ItemID, Number orderQuantity) {
		
		this.name = name;
		this.supplier = supplier;
		this.Quantity = Quantity;
		this.Threshhold = Threshhold;
		this.pricePerUnit = pricePerUnit;
		this.ItemID = ItemID;
		this.orderQuantity = orderQuantity;
		this.orderingQuantity = 0;
		addItems(this);
		
		if (Quantity instanceof Integer)
			isInt = true;
		else
			isInt = false;
		
	}	
	
	private void addItems(Item item) {
		Inventory.addToInventory(item);
	}
	
	public void deleteItem() {
		Inventory.removeItem(this);
	}

	public String getName() {
		return name;
	}

	public int getItemID() {
		return ItemID;
	}

	public Number getQuantity() {
		if (isInt == true) {
			int _Quantity = Quantity.intValue();
			return _Quantity;
		} else {
			double _Quantity2 = Quantity.doubleValue();
			return _Quantity2;
		}
	}

	public void setQuantity(Number quantity) {
		Quantity = quantity;
	}
	
	public void addQuantity(Number addingquantity) {
		if (isInt == true) {
			Quantity = Quantity.intValue() + addingquantity.intValue();
		} else {
			Quantity = Quantity.doubleValue() + addingquantity.doubleValue();
		}
	}
	
	public void removeQuantity(Number removeQuantity) {
		if (isInt == true) {
			Quantity = Quantity.intValue() - removeQuantity.intValue();
		} else {
			Quantity = Quantity.doubleValue() - removeQuantity.doubleValue();
		}
	}


	public Number getThreshhold() {
		return Threshhold;
	}

	public void setThreshhold(Number threshhold) {
		Threshhold = threshhold;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public Number getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Number orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public Number getOrderingQuantity() {
		return orderingQuantity;
	}

	public void setOrderingQuantity(Number orderingQuantity) {
		this.orderingQuantity = orderingQuantity;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
}
