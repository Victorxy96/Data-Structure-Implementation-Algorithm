package edu.cmu.andrew.yanxing;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

/**
 * This class work as Merkle–Hellman knapsack cryptosystem. It allows the user to enter a string
 * encrypt it and then decrypt the string using Merkle–Hellman knapsack algorithm.
 */
public class MerkleHellman {
    /**
     * The singly linked list worked as a superincreasing sequence
     */
    private static SinglyLinkedList singlyLinkedList = new SinglyLinkedList();
    /**
     * The singly linked list worked as public key
     */
    private static SinglyLinkedList betaSinglyLinkedList = new SinglyLinkedList();

    /**
     * The sum to calculate the super increasing seq
     */
    private static BigInteger sum = new BigInteger("0");
    /**
     * Q value: random value larger than sum
     */
    private static BigInteger q = null;

    /**
     * R value: in the range [1,q) and is coprime to q
     */
    private static BigInteger r = null;


    /**
     * Get the bytes format of input string.
     * @param data input string
     * @return string in bytes
     */
    public static String getBytesNum(String data) {
        char[] chars = data.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<chars.length;i++) {
            sb.append(Integer.toBinaryString(0x100 | chars[i]).substring(1));
        }
        return sb.toString();
    }

    /**
     * Get the super increasing sequence.
     * @param n the number of nodes
     * @return super increasing sequence in singly linked list format
     */
    public SinglyLinkedList getSuperIncreasingSequenece(int n) {
        // Generate a random number
        Random random = new Random();
        for(int i=0;i<n;i++) {
            BigInteger cur = new BigInteger(String.valueOf(random.nextInt()));
            // add it to the current sum
            cur = cur.abs().add(sum);
            // add it as the node of the super increasing sequence
            singlyLinkedList.addAtEndNode(cur);
            // add it to sum
            sum = sum.add(cur);
        }
        return singlyLinkedList;
    }

    /**
     * Get Q and R
     */
    public void getQandR() {
        // generate a random number and added to the current sum
        // let it be q which can ensure q is larger than the total sum of
        // superincreasing sequence
        Random random = new Random();
        BigInteger radomNum = new BigInteger(String.valueOf(random.nextInt()));
        BigInteger tmp = radomNum.abs();
        q = sum.add(tmp);
        //  take R = Q-1 which can ensure r is in the range [1,q) and is coprime to q
        r = q.add(BigInteger.ONE.negate());
    }

    /**
     * Get the public key.To calculate a public key, generate the sequence β by multiplying
     * each element in w by r mod q
     * @return the singly linked list worked as public key
     */
    public SinglyLinkedList getBetaSinglyLinkedList() {
        int num = singlyLinkedList.countNodes();
        for(int i=0;i<num;i++) {
            BigInteger cur = (BigInteger)(singlyLinkedList.getObjectAt(i));
            betaSinglyLinkedList.addAtEndNode(cur.multiply(r).mod(q));
        }
        return betaSinglyLinkedList;
    }


    /**
     * Main method to encrypt and decrypt the string
     * @param args input args
     * @throws IOException io exception
     */
    public static void main(String[] args) throws IOException {
        // let user enter the string
        System.out.println("Enter a string and I will encrypt it as single large integer.");
        Scanner scan = new Scanner(System.in);
        String data = scan.nextLine();
        scan.close();

        // quit when data length larger than 80
        if(data.length() > 80) {
            System.out.println("Length of String too long");
        }

        // output data and length of bytes of the user input string
        System.out.println("Clear text:");
        System.out.println(data);
        System.out.println("Number of clear text bytes = "+data.length());

        // create the super increasing sequence
        // the super increasing sequence should be 8*Number of String Bytes nodes
        MerkleHellman merkleHellman = new MerkleHellman();
        SinglyLinkedList w = merkleHellman.getSuperIncreasingSequenece(data.length()*8);
        // get q and r
        merkleHellman.getQandR();
        // get public key
        merkleHellman.getBetaSinglyLinkedList();
        // get data format in bits
        String charsInBits = getBytesNum(data);

        // encrypt the data using the public key
        BigInteger sumEncrypt = new BigInteger("0");
        char[] chars = charsInBits.toCharArray();
        for(int i=0;i<chars.length;i++){
            // when meet 1 of the data in bits format
            // add the public key singly list position value to the sum
            if(chars[i]=='1') {
                sumEncrypt = sumEncrypt.add((BigInteger) (betaSinglyLinkedList.getObjectAt(i)));
            }

        }

        // get the encrypt result
        System.out.println(data+" is encrypted as");
        System.out.println(sumEncrypt);

        // decrypt by by multiply r^−1 mod q
        BigInteger decryptNum = sumEncrypt.multiply(r.modInverse(q)).mod(q);

        // create the string to save the decrypt result
        StringBuilder decryptString = new StringBuilder();
        for(int i=0;i<data.length()*8;i++) {
            decryptString.append(0);
        }

        // check the superincreasing sequence from tail
        // selecting the largest element in w which is less than or equal to decrypt result
        // selecting the next largest element less than or equal to the difference,
        // until the difference is 0. The result is in 0-1 format
        int index = w.countNodes()-1;
        while (decryptNum.compareTo(BigInteger.valueOf(0))!=0) {
            BigInteger cur = (BigInteger) w.getObjectAt(index);
            if(decryptNum.compareTo(cur)>=0) {
                decryptNum = decryptNum.subtract(cur);
                decryptString.setCharAt(index,'1');
            }
            index--;
        }

        // output the original string
        System.out.println("Result of decryption:");
        // change 0-1 format string to the char number
        String res = decryptString.toString();
        char[] numbers = res.toCharArray();
        StringBuilder decryptRes = new StringBuilder();
        for(int i=0;i<numbers.length;i+=8) {
            int curChar =  Integer.valueOf(res.substring(i,i+8),2);
            decryptRes.append((char)curChar);
        }
        // out put the decrypted string
        System.out.println(decryptRes);
    }
}
