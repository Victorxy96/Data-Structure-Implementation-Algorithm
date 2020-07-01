import java.io.*;
import java.util.Scanner;

/**
 * The RedBlackTreeTester is a spell checking program used to test the re black tree.
 *
 * @author: Yan Xing
 * @create: 2020-02-10 20:32
 **/
public class RedBlackTreeSpellChecker {

    /**
     * Show the meue.
     */
    public static void showMeue() {
        System.out.println("Legal commands are: ");
        System.out.println("d    display the entire word tree with a level order traversal.");
        System.out.println("s    print the words of the tree in sorted order (using an inorder traversal).");
        System.out.println("r    print the words of the tree in reverse sorted order (reverse inorder traversal). ");
        System.out.println("c   <word> to spell check this word.");
        System.out.println("a   <word> add this word to tree.");
        System.out.println("f   <fileName> to check this text file for spelling errors.");
        System.out.println("i   display the diameter of the tree.");
        System.out.println("m  view this menu.");
        System.out.println("!   to quit.");
    }

    /**
     * Check the spell.
     * @param args args[0] - The file name of the word list (to be used as a dictionary).
     * The main method interacts with a user at the command line. A menu of commands
     * is provided as well as some computations regarding the size of the word list
     * used as a dictionary. The dictionary is case sensitive. That is, "Pittsburgh"
     * does not equal "pittsburgh". The commands entered by the user are NOT case sensitive.
     *  That is, "p" == "P".
     * @throws IOException io exception
     */
    public static void main(String[] args) throws IOException {
        RedBlackTree redBlackTree = new RedBlackTree();

        String fileName = "shortwords.txt";
//        String fileName = "words.txt";

        File file=new File(fileName);

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        System.out.println("Loading a tree of English words from "+fileName);

        String content = null;

        while ((content = bufferedReader.readLine()) != null) {
            redBlackTree.insert(content);
        }
        bufferedReader.close();

        System.out.println("Red Black Tree is loaded with " + redBlackTree.getSize()
                + " words");
        System.out.println("Initial tree height is " + redBlackTree.height());
        System.out.println("Never worse than 2 * Lg(n+1) = " + 2
                * (Math.log(redBlackTree.getSize() + 1) / Math.log(2)));

        showMeue();

        while (true) {
            Scanner userInput = new Scanner(System.in);
            String inputString = userInput.nextLine();
            String[] strings = inputString.split(" ");
            if(strings[0].equals(">d")) {
                System.out.println("Level order traversal");
                redBlackTree.levelOrderTraversal();
            } else if(strings[0].equals(">s")) {
                redBlackTree.inOrderTraversal();
            } else if(strings[0].equals(">r")) {
                redBlackTree.reverseOrderTraversal();
            } else if(strings[0].equals(">c")) {
                if(redBlackTree.contains(strings[1])) {
                    System.out.println("Found " + strings[1] + " after "
                            + redBlackTree.getRecentCompares() + " comparisons.");
                } else {
                    System.out.println(strings[1]+ " Not in the dictionary. Perhaps you mean");
                    System.out.println(redBlackTree.closeBy(strings[1]));
                }
            } else if(strings[0].equals(">a")) {
                redBlackTree.insert(strings[1]);
                System.out.println(strings[1]+" was added to dictionary.");
            } else if(strings[0].equals(">f")) {
                String toCheck = strings[1];
                File toCheckFile = new File(toCheck);
                BufferedReader bufferedReaderCheck = new BufferedReader(new FileReader(toCheckFile));
                String lineCheck = null;
                boolean findError = false;
                while ((lineCheck = bufferedReaderCheck.readLine()) != null) {
                    String[] curLine = lineCheck.replace(".","").split(" ");

                    for(String s : curLine){
                        if (!redBlackTree.contains(s)) {
                            findError = !findError;
                            System.out.println("'"+s+"'" + " was not found in the dictionary.");
                        }
                    }
                }

                if(!findError) {
                    System.out.println("No spelling errors found.");
                }

                bufferedReaderCheck.close();
            } else if(strings[0].equals(">i")) {
                System.out.println("Diameter is :"+ redBlackTree.getDiameter());
            } else if(strings[0].equals(">m")) {
                showMeue();
            } else if(strings[0].equals(">!")) {
                System.out.println("Bye");
                System.exit(0);
            }
        }

    }
}
