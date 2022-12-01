# Enigma WWII Cipher Replication and Improvements

This project is a modern replication and improvement of the Enigma cipher used during WWII. The program replicates the original design, 
and expands on it by integrating the original model with a more modern encryption cipher, the Rivest-Shamir-Adleman (RSA) algorithm. 
The RSA algorithm works by generating a public and private key. When the user wants to encrypt the message, the algorithm uses the 
public key to transform the String into an array of bytes, and then back to a String. 
Decryption is a similar process, as the algorithm will decode the encrypted String into a byte array that represents the original message. The private key 
is then used to decrypt the byte array into a String, returning the user's message.

The program also includes user input functionality so a user can have their own message encrypted and see the results for themselves.

In the future, I am looking to implement a decryption of the enigma cipher, so that the user can see their original message after it
has been encrypted by two different algorithms.
