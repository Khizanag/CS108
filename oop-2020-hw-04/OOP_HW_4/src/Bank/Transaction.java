package Bank;

// Bank.Transaction.java
/*
 (provided code)
 Bank.Transaction is just a dumb struct to hold
 one transaction. Supports toString.
*/
public final class Transaction {

	public int from;
	public int to;
	public int amount;
	
   	public Transaction(int from, int to, int amount) {
		this.from = from;
		this.to = to;
		this.amount = amount;
	}

//	@Override
//	public String toString() {
//		return("from:" + from + " to:" + to + " amt:" + amount);
//	}
}
