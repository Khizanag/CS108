package Bank;// Bank.Bank.java

/*
 Creates a bunch of accounts and uses threads
 to post transactions to the accounts concurrently.
*/

import Bank.*;
import org.junit.jupiter.params.converter.ArgumentConversionException;

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Bank {
	public static final int ACCOUNTS = 20;	 // number of accounts
	private static final int NUM_MAX_TRANSACTIONS = 50;
	private static final int DEFAULT_BALANCE = 1000;

	/* private instance variables */
	private List<Account> accounts;
	private BlockingQueue<Transaction> queue;
	private CountDownLatch latch;
	private static final Transaction nullTrans = new Transaction(-1,-2,0);

	public Bank() {
		initAccounts();
		queue = new ArrayBlockingQueue<>(NUM_MAX_TRANSACTIONS);
	}

	private void initAccounts(){
		accounts = new ArrayList<>();
		for(int i = 0; i < ACCOUNTS; i++){
			accounts.add(new Account(this, i, DEFAULT_BALANCE));
		}
	}


	/*
	 Reads transaction data (from/to/amt) from a file for processing.
	 (provided code)
	 */
	public void readFile(String file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			// Use stream tokenizer to get successive words from file
			StreamTokenizer tokenizer = new StreamTokenizer(reader);
			
			while (true) {
				int read = tokenizer.nextToken();
				if (read == StreamTokenizer.TT_EOF) break;  // detect EOF
				int from = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int to = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int amount = (int)tokenizer.nval;
				
				// Use the from/to/amount
				
				Transaction transaction = new Transaction(from, to, amount);
				queue.put(transaction);
			}
			reader.close();
		}
		catch (Exception e) {  e.printStackTrace(); System.exit(1);  }
	}

	private void pushNullTrans(int numWorkers) throws InterruptedException {
		for(int i = 0; i < numWorkers; i++){
			queue.put(nullTrans);
		}
	}

	/*
	 Processes one file of transaction data
	 -fork off workers
	 -read file into the buffer
	 -wait for the workers to finish
	*/

	// this method has changed return type from void to "List<account>" for testing
	public void processFile(String file, int numWorkers) throws InterruptedException {
		latch = new CountDownLatch(numWorkers);

		Worker[] workers = new Worker[numWorkers];
		for(int i = 0; i < numWorkers; i++){
			workers[i] = new Worker();
			workers[i].start();
		}

		readFile(file);
		pushNullTrans(numWorkers);

		latch.await();

		printAccounts();
	}

	public List<Account> getAccounts(){
		return accounts;
	}

	private void printAccounts(){
		for(int i = 0; i < ACCOUNTS; i++){
			System.out.println(accounts.get(i).toString());
		}
	}

	private void processTransaction(Transaction transaction) {
		int fromID = transaction.from;
		int toID = transaction.to;
		Account fromAccount = accounts.get(fromID);
		Account toAccount = accounts.get(toID);
		synchronized (fromAccount){
			fromAccount.sendAmount(transaction.amount);
		}

		synchronized (toAccount){
			toAccount.receiveAmount(transaction.amount);
		}

	}

	private class Worker extends Thread{
		public Worker(){
			super();
		}

		@Override
		public void run(){
			while(true){
				try {
					Transaction transaction = queue.take();
					if(transaction == nullTrans) break; // we ca use == because of static
					processTransaction(transaction);
				} catch (InterruptedException unused) { }
			}
			latch.countDown();
		}
	}
	
	/*
	 Looks at commandline args and calls Bank.Bank processing.
	*/
	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		// deal with command-lines args
		if (args.length == 0) {
			System.out.println("Args: transaction-file [num-workers [limit]]");
			return;
		}

		String file = args[0];

		int numWorkers = 1;
		if (args.length >= 2) {
			numWorkers = Integer.parseInt(args[1]);
		}

		Bank bank = new Bank();
		bank.processFile(file, numWorkers);
	}
}

