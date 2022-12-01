import ciphers.KeyGenerator;
import java.util.Random;

public class Main {
    /*
    TODO:
        1. plugboard function
        2. expansion of encryption to include modern cryptography
        3. decryption algorithm (for enigma cipher, already implemented for RSA KeyGenerator)
        4. will require some work as  elimination of design flaws limits decryption techniques 
        */
    public static void main(String[] args) {
        Random rand = new Random();
        // call KeyGenerator invoker method, which will do the following:
            // 1. Call Enigma start method which performs Enigma encryption
            // 2. This call to Enigma start method returns instance of Enigma class
            // 3. Next, the invoker method for the KeyGenerator class is called
            // 4. This performs RSA encryption and decryption on the already Enigma-encrypted String
        KeyGenerator gen = KeyGenerator.invoker(args);
        System.out.println("Original message: " + gen.enigmaDecrypt(rand));
        //System.out.println("After RSA Decryption: " + gen.getKeyGenCipherString());
    }
}
