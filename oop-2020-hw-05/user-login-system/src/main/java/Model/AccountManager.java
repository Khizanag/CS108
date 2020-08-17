package Model;

import java.util.HashMap;
import java.util.Map;

public class AccountManager {

    private Map<String, Account> accounts = new HashMap<>();

    public void AccountManager(){ }

    public boolean isUserNameHeld(String username){
        return accounts.containsKey(username);
    }

    public boolean register(String username, String password){
        if(isUserNameHeld(username)) {
            return false;   // unsuccessful registration
        }

        Account newAcc = new Account(username, password);
        accounts.put(username, newAcc);
        return true;    // successful registration
    }

    public boolean isCorrectLogin(String username, String password){
        Account acc = accounts.get(username);
        boolean result = (acc != null && acc.getPassword().equals(password));
        return result;
    }
}
