package Bank;// Bank.Account.java

import Bank.Bank;

/*
 Simple, thread-safe Bank.Account class encapsulates
 a balance and a transaction count.
*/
public class Account {
	private int id;
	private int balance;
	private int numTransactions;
	
	// It may work out to be handy for the account to
	// have a pointer to its Bank.Bank.
	// (a suggestion, not a requirement)
	private Bank bank;  
	
	public Account(Bank bank, int id, int balance) {
		this.bank = bank;
		this.id = id;
		this.balance = balance;
		numTransactions = 0;
	}

	public void sendAmount(int amount) {
		balance -= amount;
		numTransactions++;
	}

	public void receiveAmount(int amount){
		balance += amount;
		numTransactions++;
	}

	/* Getter methods */

	public int getTransactionsCount(){
		return numTransactions;
	}

	public int getID(){
		return id;
	}

	public int getBalance(){
		return balance;
	}

	@Override
	public String toString(){
		return "acct:" + getID() + " bal:" + getBalance() + " trans:" + getTransactionsCount();
	}
}