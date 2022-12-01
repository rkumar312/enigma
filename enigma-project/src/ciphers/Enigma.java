package ciphers;
import java.util.*;

public class Enigma {

    private String ciphertext = "";
    private String message;
    private final String letterBank = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int[] first = new int[26];
    private int[] second = new int[26];
    private int[] third = new int[26];
    private String reflected;

    public Enigma() {
        // empty constructor :)
    }
    
    public Enigma start() {
        // declare Random, Scanner, and Enigma instances 
        Random rand = new Random();
        Scanner input = new Scanner(System.in);
        Enigma e = new Enigma();
        // ask user for message to encrypt
        System.out.print("Enter message to be encrypted: ");
        String inputMessage = input.nextLine();
        // convert message to all uppercase letters
        e.message = inputMessage.toUpperCase();

        // set the initial rotor positions
        // setting after declaring instance so that test cases can remain separate
        first = setFirst(rand);
        second = setSecond(rand);
        third = setThird(rand);
        // System.out.println("First Rotor: " + Arrays.toString(first));
        // System.out.println("Second Rotor: " + Arrays.toString(second));
        // System.out.println("Third Rotor: " + Arrays.toString(third));

        e.reflected = e.setReflector(rand);
        for (int i = 0; i < e.message.length(); i++) {
            char d = e.message.charAt(i);
            if (letterBank.indexOf(d) == -1) {
                e.ciphertext += d;
                continue;
            }

            int rToL = e.encryptRightToLeft(e.first, e.second, e.third, d, e.letterBank);
            //System.out.println("Right to left Result: " + rToL);

            int reflectedInt = e.reflector(e.reflected, rToL, rand);

            e.ciphertext += e.encryptLeftToRight(e.first, e.second, e.third, reflectedInt, e.letterBank);
        }
        
        // print final ciphertext
        System.out.println("Ciphertext: " + e.ciphertext);
        // close user input stream
        input.close();

        // return Enigma object
        return e;

    }

    // function to get ciphertext string to use in RSA encryption
    public String getCiphertext() {
        return ciphertext;
    }
    // set initial configuration of first rotor
    public int[] setFirst(Random rand) {
        //first = new int[26];
        for (int i = 0; i < letterBank.length(); i++) {
            if (letterBank.charAt(i) == ' ') {
                continue;
            }
            first[i] = (int) (letterBank.charAt(i) - 65);
        }

        
        scrambleRotors(first, rand);
        return first;
    }
    // set initial configuration of second rotor
    public int[] setSecond(Random rand) {
        //second = new int[26];
        for (int i = 0; i < letterBank.length(); i++) {
            if (letterBank.charAt(i) == ' ') {
                continue;
            }
            second[i] = (int) (letterBank.charAt(i) - 65);
        }

        
        scrambleRotors(second, rand);
        return second;
    }
    // set initial configuration of third rotor
    public int[] setThird(Random rand) {
        //third = new int[26];
        for (int i = 0; i < letterBank.length(); i++) {
            if (letterBank.charAt(i) == ' ') {
                continue;
            }
            third[i] = (int) (letterBank.charAt(i) - 65);
        }

        
        scrambleRotors(third, rand);
        return third;
    }
    // getter for first rotor
    public int[] getFirst() {
        return first;
    }
    // getter for second rotor
    public int[] getSecond() {
        return second;
    }
    // getter for third rotor
    public int[] getThird() {
        return third;
    }

    // swaps elements in the rotor array randomly, generating random initial rotor setting
    public int[] scrambleRotors(int[] rotor, Random rand) {
        for (int i = rotor.length - 1; i > 1; i--) {
            int swapIndex = rand.nextInt(i);
            int temp = rotor[swapIndex];
            rotor[swapIndex] = rotor[i];
            rotor[i] = temp;
        }
        return rotor;
    }

    // function to rotate rotors for encryption
    public int[] rotate(int[] rotor) {
        // store last element 
        int temp = rotor[rotor.length - 1];
        for (int j = rotor.length - 1; j > 0; j--) {
            // shift all values to the right by one
            rotor[j] = rotor[j - 1];
        }
        //append last element on to the front of the array
        rotor[0] = temp;

        return rotor;
    }

    public String setReflector(Random rand) {
        String reflector = "";
        char[] array = letterBank.toCharArray();
        // making reflector more random is something the original design did not contain
        // an introduction of more randomeness into the design 
        //can provide additional cryptographic security
        int index = rand.nextInt(letterBank.length());
        for (int i = array.length - 1; i > 0; i--) {
            char temp = array[index];
            array[index] = array[i];
            array[i] = temp;

        }
        for (char c : array) {
            reflector += c;
        }
        return reflector;
    }
    // encryption function for right to left current
    public int encryptRightToLeft(int[] r1, int[] r2, int[] r3, char d, String bank) {
        int firstCount = 0, secondCount = 0, thirdCount = 0;
        int rightToLeftResult = 0;
        int rightIndex, middleIndex, leftIndex = 0;
        rotate(r1);
        firstCount++;
        //check if first rotor has rotated 26 times
        if (firstCount == 26) {
            firstCount = 0;
            rotate(r2);
            secondCount++;
        }
        //check if second rotor has rotated 26 times
        if (secondCount == 26) {
            secondCount = 0;
            rotate(r3);
        }
        //check if third rotor has rotated 26 times
        if (thirdCount == 26) {
            thirdCount = 0;
        }

        int bankIndex = bank.indexOf(d);
        rightIndex = r1[bankIndex];
        middleIndex = r2[rightIndex];
        leftIndex = r3[middleIndex];
        rightToLeftResult = leftIndex;

        return rightToLeftResult;
    }

    public char encryptLeftToRight(int[] r1, int[] r2, int[] r3, int reflected, String bank) {
        char ciphertext;
        int rightIndex, middleIndex, leftIndex = 0;
        leftIndex = r3[reflected];
        middleIndex = r2[leftIndex];
        rightIndex = r1[middleIndex];
        ciphertext = (char) (rightIndex + 65);
        return ciphertext;
    }

    public int reflector(String reflString, int rToL, Random rand) {
        int reflectedIndex = 0;
        int[] reflectorStrToIntArray = setFirst(rand);
        int randIndex = reflectorStrToIntArray[rand.nextInt(reflectorStrToIntArray.length)];
        rToL = randIndex;
        reflectedIndex = randIndex;
        return reflectedIndex;
    }

    public String enigmaDecrypt(Random rand) {

        return ciphertext;
    }

}