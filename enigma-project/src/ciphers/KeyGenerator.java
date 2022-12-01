package ciphers;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class KeyGenerator extends Enigma {
    // declaring private fields
    private PublicKey publicKey; 
	private PrivateKey privateKey; 
	private String ciphertextMessage; 
	private byte [] encryptedText;
	private byte [] decryptedText;
    private Cipher encryptCipher;
    private Cipher decryptCipher;

    public KeyGenerator() {
        // create instance of KeyGenerator class, pass encryption algorithm to use
        KeyPairGenerator gen = null;
        try {
            gen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // initialize key size
		gen.initialize(2048);
        //generate key pair
		KeyPair keyPair = gen.generateKeyPair();
        
        // store references to public and private key
		this.publicKey = keyPair.getPublic();
		this.privateKey = keyPair.getPrivate();
    }
    // getter for the public key
    public PublicKey getPublic() {
        return publicKey;
    }

    // getter for the private key
    public PrivateKey getPrivate() {
        return privateKey;
    }

    // rsa encryption function
    // uses public key to create a byte array from already encrypted enigma message
    // makes it even more secure by using a modern encryption algorithm
    public String encrypt() {
        try {
            // get instance of Cipher class that implements RSA encryption
            encryptCipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // initialize this cipher object with the given key
        // in this case, pass the public key
        try {
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            // encrypts the enciphered enigma message by transforming message to byte array
            // this creates the encrypted byte array
            encryptedText = encryptCipher.doFinal(this.ciphertextMessage.getBytes());
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Base64.getEncoder returns Base64.getEncoder object that encodes byte data
        // encodeToString encodes the byte array to a string to be returned as encrypted string
        return Base64.getEncoder().encodeToString(encryptedText);
    }
    // rsa decryption function (if needed, can pass back to enigma decryption function)
    public String decrypt() {
        // use base64 decoder to decode string into byte array
        decryptedText = Base64.getDecoder().decode(ciphertextMessage);
        try {
            decryptCipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try {
            // initialize this cipher object into decryption mode to perform decryption of byte array
            decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // return final decrypted string by performing RSA algorithm on decoded byte array
        try {
            decryptedText = decryptCipher.doFinal(decryptedText);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new String(decryptedText, StandardCharsets.UTF_8);
    }

    public static KeyGenerator invoker(String[] args) {
        // declare new class instance of Enigma class
        // call start method for Enigma class to perform enigma cipher encryption
        Enigma e2 = new Enigma();
        e2 = e2.start();
        // initialize new KeyGenerator instance
        KeyGenerator gen = new KeyGenerator();
        gen.ciphertextMessage = e2.getCiphertext();
        System.out.println("Before RSA Algorithm runs: " + gen.ciphertextMessage);
        // get public and private keys
        gen.getPublic();
        gen.getPrivate();

        // encrypt and decrypt the message using RSA algorithm
        gen.ciphertextMessage = gen.encrypt();
        System.out.println("After RSA Encryption: " + gen.ciphertextMessage);
        gen.ciphertextMessage = gen.decrypt();
        System.out.println("After RSA Decryption: " + gen.ciphertextMessage);
        return gen;
    }

    public String getKeyGenCipherString() {
        return ciphertextMessage;
    }
}
