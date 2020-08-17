package Bank;

import org.junit.Test;
import org.junit.jupiter.params.converter.ArgumentConversionException;

import java.io.FileNotFoundException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class BankTest {

    private static final String SMALL_FILE_NAME = "/Users/gigakhizanishvili/Programming/GitHub/oop-2020-hw-04/OOP_HW_4/src/Bank/small.txt";
    private static final String MEDIUM_FILE_NAME = "/Users/gigakhizanishvili/Programming/GitHub/oop-2020-hw-04/OOP_HW_4/src/Bank/5k.txt";
    private static final String LARGE_FILE_NAME = "/Users/gigakhizanishvili/Programming/GitHub/oop-2020-hw-04/OOP_HW_4/src/Bank/100k.txt";

    @Test
    public void testSmallFile() throws FileNotFoundException, InterruptedException {
        String[] args = new String[2];
        args[0] = SMALL_FILE_NAME;
        args[1] = "5"; // numTests
        Bank.main(args);
    }

    @Test
    public void testOneArg() throws FileNotFoundException, InterruptedException {
        String[] args = new String[1];
        args[0] = SMALL_FILE_NAME;
        Bank.main(args);
    }

    @Test
    public void testNoArgs() throws FileNotFoundException, InterruptedException {
        String[] args = new String[0];
        Bank.main(args);
    }

    @Test
    public void testMiddleFile() throws FileNotFoundException, InterruptedException {
        String[] args = new String[2];
        args[0] = MEDIUM_FILE_NAME;
        args[1] = "3"; // numTests
        Bank.main(args);
    }

    @Test
    public void testLargeFile() throws FileNotFoundException, InterruptedException {
        String[] args = new String[2];
        args[0] = LARGE_FILE_NAME;
        args[1] = "10"; // numTests
        Bank.main(args);
    }

    @Test
    public void testSmallFileOnAccountsInfo() throws InterruptedException {
        Bank bank = new Bank();
        bank.processFile(SMALL_FILE_NAME, 4);
        List<Account> accounts = bank.getAccounts();
        for(Account account : accounts){
            assertEquals(1, account.getTransactionsCount());
            assertTrue(account.getBalance() == 1001 || account.getBalance() == 999);
        }
    }

    @Test
    public void testMediumFileOnAccoutsInfo() throws InterruptedException {
        Bank bank = new Bank();
        bank.processFile(MEDIUM_FILE_NAME, 400);
        List<Account> accounts = bank.getAccounts();

        for(Account account : accounts)
            assertEquals(1000, account.getBalance());

        assertEquals(20, accounts.size());
        assertEquals(518, accounts.get(0).getTransactionsCount());
        assertEquals(444, accounts.get(1).getTransactionsCount());
        assertEquals(522, accounts.get(2).getTransactionsCount());
        assertEquals(492, accounts.get(3).getTransactionsCount());
        assertEquals(526, accounts.get(4).getTransactionsCount());
        assertEquals(526, accounts.get(5).getTransactionsCount());
        assertEquals(474, accounts.get(6).getTransactionsCount());
        assertEquals(472, accounts.get(7).getTransactionsCount());
        assertEquals(436, accounts.get(8).getTransactionsCount());
        assertEquals(450, accounts.get(9).getTransactionsCount());
        assertEquals(498, accounts.get(10).getTransactionsCount());
        assertEquals(526, accounts.get(11).getTransactionsCount());
        assertEquals(488, accounts.get(12).getTransactionsCount());
        assertEquals(482, accounts.get(13).getTransactionsCount());
        assertEquals(516, accounts.get(14).getTransactionsCount());
        assertEquals(492, accounts.get(15).getTransactionsCount());
        assertEquals(520, accounts.get(16).getTransactionsCount());
        assertEquals(528, accounts.get(17).getTransactionsCount());
        assertEquals(586, accounts.get(18).getTransactionsCount());
        assertEquals(504, accounts.get(19).getTransactionsCount());
    }

    @Test
    public void testLargeFileOnAccounts() throws InterruptedException {
        Bank bank = new Bank();
        bank.processFile(LARGE_FILE_NAME, 4);
        List<Account> accounts = bank.getAccounts();

        for(int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            assertEquals(1000, account.getBalance());
        }

        assertEquals(10360, accounts.get(0).getTransactionsCount());
        assertEquals(8880, accounts.get(1).getTransactionsCount());
        assertEquals(10440, accounts.get(2).getTransactionsCount());
        assertEquals(9840, accounts.get(3).getTransactionsCount());
        assertEquals(10520, accounts.get(4).getTransactionsCount());
        assertEquals(10520, accounts.get(5).getTransactionsCount());
        assertEquals(9480, accounts.get(6).getTransactionsCount());
        assertEquals(9440, accounts.get(7).getTransactionsCount());
        assertEquals(8720, accounts.get(8).getTransactionsCount());
        assertEquals(9000, accounts.get(9).getTransactionsCount());
        assertEquals(9960, accounts.get(10).getTransactionsCount());
        assertEquals(10520, accounts.get(11).getTransactionsCount());
        assertEquals(9760, accounts.get(12).getTransactionsCount());
        assertEquals(9640, accounts.get(13).getTransactionsCount());
        assertEquals(10320, accounts.get(14).getTransactionsCount());
        assertEquals(9840, accounts.get(15).getTransactionsCount());
        assertEquals(10400, accounts.get(16).getTransactionsCount());
        assertEquals(10560, accounts.get(17).getTransactionsCount());
        assertEquals(11720, accounts.get(18).getTransactionsCount());
        assertEquals(10080, accounts.get(19).getTransactionsCount());
    }
}