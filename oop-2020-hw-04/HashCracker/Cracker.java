package HashCracker;// HashCracker.Cracker.java
/*
 Generates SHA hashes of short strings in parallel.
*/

import java.security.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();

	private CountDownLatch latch;
	private List<String> passwords;
	private MessageDigest digest;

	public Cracker(){
		initDigest();
	}

	private void initDigest(){
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {  e.printStackTrace();  }
	}
	
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}

	private void processCracking(String targetHash, int len, int numWorkers) throws InterruptedException {
		latch = new CountDownLatch(numWorkers);
		passwords = new ArrayList<>();
		runWorkers(targetHash, len, numWorkers);
		latch.await();
		displayPossiblePasswords();
	}

	private void runWorkers(String targetHash, int len, int numWorkers){
		CrackerWorker[] workers = new CrackerWorker[numWorkers];
		for(int i = 0; i < numWorkers; i++){
			workers[i] = new CrackerWorker(targetHash, len, numWorkers, i);
			workers[i].start();
		}
	}

	public void tryProcessCracking(String targetHash, int len, int numWorkers){
		try{
			processCracking(targetHash, len, numWorkers);
		} catch(InterruptedException e){  System.out.println(e.toString()); }
	}

	public IntegerPair getRangeIndexes(int numWorkers, int ID){
		int fromIndex = CHARS.length / numWorkers * ID;
		int toIndex = (ID == numWorkers -1) ? CHARS.length-1 : fromIndex + CHARS.length / numWorkers-1;
		return new IntegerPair(fromIndex, toIndex);
	}

	protected class CrackerWorker extends Thread {

		private String targetHash;
		private int len;
		private int numWorkers;
		private int ID;

		public CrackerWorker(String targetHash, int len, int numWorkers, int ID){
			this.targetHash = targetHash;
			this.len = len;
			this.numWorkers = numWorkers;
			this.ID = ID;
		}

		@Override
		public void run(){
			IntegerPair indexes = getRangeIndexes(numWorkers, ID);
			int fromIndex = indexes.getFirst();
			int toIndex = indexes.getSecond();
			for(int i = fromIndex; i <= toIndex; i++){
				String curPass = "" + CHARS[i];
				recCrackPassword(curPass, len);
			}
			latch.countDown();
		}

		private void recCrackPassword(String curPass, int len){
			tryGuessPassword(curPass);
			if(len == curPass.length()){
				return; // max len is reached
			} else {
				for(char c : CHARS){
					recCrackPassword(curPass + c, len);
				}
			}
		}

		private void tryGuessPassword(String password){
			String thisHash = getHashValueAsString(password);
			if(thisHash.equals(this.targetHash)){
				synchronized (passwords){
					passwords.add(password);
				}
			}
		}
	}

	private void displayPossiblePasswords(){
		for(String password : passwords){
			System.out.println(password);
		}
	}

	// public for testing
	public String getHashValueAsString(String s){
		String result;
		synchronized (digest) {
			digest.update(s.getBytes());
			result = hexToString(digest.digest());
		}
		return result;
	}

	// used for testing
	// should be called after tryCrocessCracking
	public List<String> getPasswords(){
		return passwords;
	}
	
	
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Args: target length [workers]");
			return;
		}
		// args: targ len [num];
		String targetHash = args[0];
		int len = Integer.parseInt(args[1]);
		int numWorkers = 1;
		if (args.length>2) {
			numWorkers = Integer.parseInt(args[2]);
		}

		// a! 34800e15707fae815d7c90d49de44aca97e2d759
		// xyz 66b27417d37e024c46526c2f6d358a754fc552f3

		Cracker cracker = new Cracker();

		if(args.length == 2){
			System.out.println(cracker.getHashValueAsString(targetHash));
		} else {
			cracker.tryProcessCracking(targetHash, len, numWorkers);
		}
		System.out.println("All done");
	}

}
