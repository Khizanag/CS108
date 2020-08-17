package HashCracker;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CrackerTest {

    private Cracker cracker;

    @Before
    public void setUp(){
        cracker = new Cracker();
    }

    @Test
    public void testHashing(){
        assertEquals("4181eecbd7a755d19fdf73887c54837cbecf63fd",
                cracker.getHashValueAsString("molly"));
        assertEquals("886ffd41c568469795a19f52486bdde64f5f5bcc",
                cracker.getHashValueAsString("flomo"));
        assertEquals("34800e15707fae815d7c90d49de44aca97e2d759",
                cracker.getHashValueAsString("a!"));
        assertEquals("66b27417d37e024c46526c2f6d358a754fc552f3",
                cracker.getHashValueAsString("xyz"));
    }

    @Test
    public void testCracking1(){
        cracker.tryProcessCracking("914d61e816b0bcae6a411366eee1c7d0b91078f7", 3, 5);
        List<String> passwords = cracker.getPasswords();
        assertEquals(1, passwords.size());
        assertEquals("oop", passwords.get(0));
    }

    @Test
    public void testCracking2(){
        cracker.tryProcessCracking("34800e15707fae815d7c90d49de44aca97e2d759", 3, 5);
        List<String> passwords = cracker.getPasswords();
        assertEquals(1, passwords.size());
        assertEquals("a!", passwords.get(0));
    }

    @Test
    public void testCracking3(){
        cracker.tryProcessCracking("66b27417d37e024c46526c2f6d358a754fc552f3", 3, 5);
        List<String> passwords = cracker.getPasswords();
        assertEquals(1, passwords.size());
        assertEquals("xyz", passwords.get(0));
    }

    @Test
    public void TestRange1(){
        IntegerPair ip = cracker.getRangeIndexes(10, 0);
        assertEquals(0, ip.getFirst());
        assertEquals(3, ip.getSecond());
    }

    @Test
    public void TestRange2(){
        IntegerPair ip = cracker.getRangeIndexes(10, 1);
        assertEquals(4, ip.getFirst());
        assertEquals(7, ip.getSecond());
    }

    @Test
    public void TestRange3(){
        IntegerPair ip = cracker.getRangeIndexes(10, 8);
        assertEquals(32, ip.getFirst());
        assertEquals(35, ip.getSecond());
    }

    @Test
    public void TestRange4(){
        IntegerPair ip = cracker.getRangeIndexes(10, 9);
        assertEquals(36, ip.getFirst());
        assertEquals(39, ip.getSecond());
    }

    @Test
    public void TestRange5(){
        IntegerPair ip = cracker.getRangeIndexes(40, 15);
        assertEquals(15, ip.getFirst());
        assertEquals(15, ip.getSecond());
    }

    @Test
    public void TestRange6(){
        IntegerPair ip = cracker.getRangeIndexes(2, 0);
        assertEquals(0, ip.getFirst());
        assertEquals(19, ip.getSecond());
    }

    @Test
    public void TestRange7(){
        IntegerPair ip = cracker.getRangeIndexes(2, 0);
        assertEquals(0, ip.getFirst());
        assertEquals(19, ip.getSecond());
    }

    @Test
    public void TestRange8(){
        IntegerPair ip = cracker.getRangeIndexes(2, 1);
        assertEquals(20, ip.getFirst());
        assertEquals(39, ip.getSecond());
    }

    @Test
    public void TestRange9(){
        IntegerPair ip = cracker.getRangeIndexes(21, 20);
        assertEquals(20, ip.getFirst());
        assertEquals(39, ip.getSecond());
    }

    @Test
    public void testHexToArray(){
        // just check that method hexToArray exists
        Cracker.hexToArray("24a26f");
    }

    @Test
    public void testMainWithZeroArgs(){
        String[] args = new String[0];
        Cracker.main(args);
    }

    @Test
    public void testMainWithTwoArgs(){
        String[] args = new String[2];
        args[0] = "34800e15707fae815d7c90d49de44aca97e2d759";
        args[1] = "3";
        Cracker.main(args);
    }

    @Test
    public void testMainWithMultiThreads(){
        String[] args = new String[3];
        args[0] = "66b27417d37e024c46526c2f6d358a754fc552f3";
        args[1] = "3";
        args[2] = "10";
        Cracker.main(args);
    }
}