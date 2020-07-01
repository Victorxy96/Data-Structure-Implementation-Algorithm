package edu.cmu.andrew.yanxing;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * This class construct the merkle tree based on the user input file.
 */
public class MerkleTree {

    /**
     * Add the data to the linked list
     * @param singlyLinkedList the linked list
     * @param data the string data
     * @return the linked list added the data
     */
    public SinglyLinkedList addList(SinglyLinkedList singlyLinkedList, String data) {
        singlyLinkedList.addAtEndNode(data);
        return singlyLinkedList;
    }

    /**
     * Get the linked list as even number nodes. If odd number of nodes is detected,
     * copy and append the last node to the linked list.
     * @param singlyLinkedList the linked list
     */
    public void getList(SinglyLinkedList singlyLinkedList) {
        int numOfNodes = singlyLinkedList.countNodes();
        if(numOfNodes % 2 != 0) {
            Object last = singlyLinkedList.getLast();
            singlyLinkedList.addAtEndNode(last);
        }
    }

    /**
     * Merge the linked list by pair.
     * @param singlyLinkedList the linked list
     * @return the linked list merged by pair
     */
    public SinglyLinkedList merge(SinglyLinkedList singlyLinkedList) {
        SinglyLinkedList mergeResult = new SinglyLinkedList();
        for(int i = 0;i<singlyLinkedList.countNodes();i+=2) {
            String stringA = (String) singlyLinkedList.getObjectAt(i);
            String stringB = (String) singlyLinkedList.getObjectAt(i+1);
            String newString = stringA+stringB;
            mergeResult.addAtEndNode(newString);
        }
        return mergeResult;
    }

    /**
     * Hashing each nodes using SHA256.
     * @param singlyLinkedList the linked list
     * @return the hashed linked list
     * @throws NoSuchAlgorithmException exception
     */
    public SinglyLinkedList hash(SinglyLinkedList singlyLinkedList) throws NoSuchAlgorithmException {
        // create a new linked list to save the hashing result
        SinglyLinkedList hashResult = new SinglyLinkedList();
        // hashing each node and append it to the new created linked list
        for(int i=0;i<singlyLinkedList.countNodes();i++) {
            String curNodeString = (String) singlyLinkedList.getObjectAt(i);
            String curNodeHash = h(curNodeString);
            hashResult.addAtEndNode(curNodeHash);
        }
        return hashResult;
    }

    /**
     * Hashing.
     * @param text
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String h(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= 31; i++) {
            byte b = hash[i];
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    /**
     * Main method to construct the merkle tree.
     * @param args input args
     * @throws IOException exception
     * @throws NoSuchAlgorithmException exception
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        // let the user input the file
        System.out.println("Please enter the file name:");
        Scanner scan=new Scanner(System.in);
        String fileName = scan.nextLine();
        scan.close();

        // read the file content line by line
        // and add each line of the string to the linked list
        File file=new File(fileName);

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String content = null;
        MerkleTree merkleTree = new MerkleTree();
        SinglyLinkedList singlyLinkedList = new SinglyLinkedList();

        while((content = bufferedReader.readLine())!= null) {
            merkleTree.addList(singlyLinkedList,content);
        }

        // building the tree
        System.out.println("Start buiding tree!");

        // get the tree as even node
        merkleTree.getList(singlyLinkedList);
        // hashing each node of the tree
        SinglyLinkedList hashList = merkleTree.hash(singlyLinkedList);
        // the linked list to save the merge result by pair
        SinglyLinkedList mergeList = null;

        // continue until there is only two nodes left
        while (hashList.countNodes() != 2) {
            // merge the hashed list
            mergeList = merkleTree.merge(hashList);
            // hash each node of the tree
            hashList = merkleTree.hash(mergeList);
            // get the tree as even node
            merkleTree.getList(hashList);
        }

        // merge the hashed list
        mergeList = merkleTree.merge(hashList);
        // hash each node of the tree
        hashList = merkleTree.hash(mergeList);

        // show the result
        // The CrimeLatLonXY.csv has the required hashed result
        // A5A74A770E0C3922362202DAD62A97655F8652064CCCBE7D3EA2B588C7E07B58
        System.out.println("The tree root is:");
        System.out.println(hashList.getObjectAt(0));
    }
}
