import java.util.*;
public class Enigma {


    //TODO
    //plugboard function
    // decryption algorithm
    // will require some work as the elimination of design flaws limits decryption techniques used by Allies


    public static void main(String[] args) {
        Random rand = new Random();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter message to be encrypted: ");
        String message = input.nextLine();
        message = message.toUpperCase();
        String letterBank = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String ciphertext = "";
        int[] a = setFirst(letterBank, rand);
        int[] b = setSecond(letterBank, rand);
        int[] c = setThird(letterBank, rand);
        System.out.println("First Rotor: " + Arrays.toString(a));
        System.out.println("Second Rotor: " + Arrays.toString(b));
        System.out.println("Third Rotor: " + Arrays.toString(c));
        //System.out.println("rotated first rotor: " + Arrays.toString(rotate(a)));
        String reflectedStr = setReflector(letterBank, rand);
        for (int i = 0; i < message.length(); i++) {
            char d = message.charAt(i);
            if (letterBank.indexOf(d) == -1) {
                ciphertext += d;
                continue;
            }
            int rToL = encryptRightToLeft(a, b, c, d, letterBank);
            //System.out.println("Right to left Result: " + rToL);
            int reflected = reflector(reflectedStr, rToL, rand);
            ciphertext += encryptLeftToRight(a, b, c, reflected, letterBank);
        }
        
        // print final ciphertext
        System.out.println("Ciphertext: " + ciphertext);

        // close user input stream
        input.close();

    }
    // set initial configuration of first rotor
    public static int[] setFirst(String bank, Random rand) {
        int[] firstRotor = new int[26];
        for (int i = 0; i < bank.length(); i++) {
            if (bank.charAt(i) == ' ') {
                continue;
            }
            firstRotor[i] = (int) (bank.charAt(i) - 65);
        }

        
        scrambleRotors(firstRotor, rand);
        return firstRotor;
    }
    // set initial configuration of second rotor
    public static int[] setSecond(String bank, Random rand) {
        int[] secondRotor = new int[26];
        for (int i = 0; i < bank.length(); i++) {
            if (bank.charAt(i) == ' ') {
                continue;
            }
            secondRotor[i] = (int) (bank.charAt(i) - 65);
        }

        
        scrambleRotors(secondRotor, rand);
        return secondRotor;
    }
    // set initial configuration of third rotor
    public static int[] setThird(String bank, Random rand) {
        int[] thirdRotor = new int[26];
        for (int i = 0; i < bank.length(); i++) {
            if (bank.charAt(i) == ' ') {
                continue;
            }
            thirdRotor[i] = (int) (bank.charAt(i) - 65);
        }

        
        scrambleRotors(thirdRotor, rand);
        return thirdRotor;
    }

    // swaps elements in the rotor array randomly, generating random initial rotor setting
    public static int[] scrambleRotors(int[] rotor, Random rand) {
        for (int i = rotor.length - 1; i > 1; i--) {
            int swapIndex = rand.nextInt(i);
            int temp = rotor[swapIndex];
            rotor[swapIndex] = rotor[i];
            rotor[i] = temp;
        }
        return rotor;
    }

    // function to rotate rotors for encryption
    public static int[] rotate(int[] rotor) {
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

    public static String setReflector(String bank, Random rand) {
        String reflector = "";
        char[] array = bank.toCharArray();
        int index = rand.nextInt(bank.length());
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
    public static int encryptRightToLeft(int[] r1, int[] r2, int[] r3, char d, String bank) {
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

    public static char encryptLeftToRight(int[] r1, int[] r2, int[] r3, int reflected, String bank) {
        char ciphertext;
        int rightIndex, middleIndex, leftIndex = 0;
        leftIndex = r3[reflected];
        middleIndex = r2[leftIndex];
        rightIndex = r1[middleIndex];
        ciphertext = (char) (rightIndex + 65);
        return ciphertext;
    }

    public static int reflector(String reflString, int rToL, Random rand) {
        int reflectedIndex = 0;
        int[] reflectorStrToIntArray = setFirst(reflString, rand);
        int randIndex = reflectorStrToIntArray[rand.nextInt(reflectorStrToIntArray.length)];
        rToL = randIndex;
        reflectedIndex = randIndex;
        return reflectedIndex;
    }

}
