import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * The graph records the crime record decided by file and the date input by the user. The graph contains the 1D crime record
 * array and 2D distance matrix. Also, this class contains the method to perform the prim to find the MST nodes and the
 * array to save the MST nodes after running prim. A 2D boolean array - tree, is used to transfer the MST tree node
 * from pointing to parent to pointing to child node. It also contains the method to find the optimal route through
 * iterate all route permutations. It contains the field minLen and minPermuatation to save the length and route
 * information of the optimal route.
 */
public class CrimeGraph {

    /**
     * Graph size
     */
    private int size = 0;
    /**
     * The crime records array
     */
    private CrimeRecord[] graph = null;
    /**
     * Distance Matrix
     */
    private double[][] dist = null;

    /**
     * File name
     */
    private String fileName = null;
    /**
     * Content of the reading input
     */
    private List<String> contents = null;

    /**
     * The MST tree node
     */
    private TreeNode[] mst;
    /**
     * The current nodes recorded iterated through Hamilton Cycle
     */
    private int[] tsp;
    /**
     * The graph records the relationship of MST tree with each node pointed from parent to child
     */
    private boolean[][] tree;

    /**
     * The length of the optimal route
     */
    private double minLen = Double.MAX_VALUE;
    /**
     * The route of the optimal route
     */
    private String minPermuatation = null;

    /**
     * Read through the graph
     * @param fileName file name
     * @throws IOException io exception
     * @throws ParseException exception
     */
    public void readGraph(String fileName) throws IOException, ParseException {

        Scanner scanner = new Scanner(System.in);

        // get the date
        System.out.println("Enter start date");
        String start = scanner.nextLine();
        System.out.println("Enter end date");
        String end = scanner.nextLine();

        scanner.close();

        System.out.println();
        System.out.println("Crime records between "+start+" and "+end);

        // read the file content based on the date input
        File file=new File(fileName);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");

        Date startDate = format.parse(start);
        Date endDate = format.parse(end);
        String content = bufferedReader.readLine();

        while ((content = bufferedReader.readLine())!= null) {
            String[] fields = content.split(",");
            Date curDate = format.parse(fields[5]);
            // match the content based on the input date
            if(fields[5].equals(start) || fields[5].equals(end) ||
                    curDate.after(startDate) && curDate.before(endDate)) {
                System.out.println(content);
                contents.add(content);
            }
        }
        bufferedReader.close();
    }

    /**
     * Constructor method
     * @param fileName file name
     * @throws IOException io exception
     * @throws ParseException exception
     */
    public CrimeGraph(String fileName) throws IOException, ParseException {
        this.fileName = fileName;
        this.contents = new ArrayList<>();
        // read the graph
        readGraph(fileName);
        this.size = contents.size();
        this.graph = new CrimeRecord[contents.size()];
        this.dist = new double[contents.size()][contents.size()];
        // process the read content and establish the graph
        process(contents);

        this.mst = new TreeNode[contents.size()];
        this.tsp = new int[contents.size()+1];
        this.tree = new boolean[contents.size()][contents.size()];

        // initial the MST tree array
        for(int i=0;i<contents.size();i++) {
            mst[i] = new TreeNode();
        }
        mst[0].setWeight(0);
    }

    /**
     * Process the content of the record information in the csv file based on the user input date. Construct a
     * graph based on the record information.
     * @param list the record information saved in the csv
     */
    public void process(List<String> list) {
        // the whole record string of the current row in csv file
        String[] curString;
        // location x of current record
        double curX;
        // location y of current record
        double curY;
        // time of current record
        int curTime;
        // street of current record
        String curStreet;
        // type of the offense of current record
        String curOffense;
        // date of current record
        String curDate;
        // tract of current record
        String curTract;
        // latitude of current record
        double curLat;
        // longitude of current record
        double curLon;

        // used to calculate the distance
        double xMatrix;
        double yMatrix;

        // build array
        for(int i=0;i<list.size();i++) {
            curString = list.get(i).split(",");
            curX = Double.parseDouble(curString[0]);
            curY = Double.parseDouble(curString[1]);
            curTime= Integer.parseInt(curString[2]);
            curStreet = curString[3];
            curOffense = curString[4];
            curDate = curString[5];
            curTract = curString[6];
            curLat = Double.parseDouble(curString[7]);
            curLon = Double.parseDouble(curString[8]);
            graph[i] = new CrimeRecord(curX,curY,curTime,curStreet,curOffense,curDate,curTract,curLat,curLon);
        }

        // build dist matrix
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                xMatrix = graph[j].getX() - graph[i].getX();
                yMatrix = graph[j].getY() - graph[i].getY();
                dist[i][j] = Math.sqrt(Math.pow(xMatrix, 2) + Math.pow(yMatrix, 2));
            }
        }
    }

    /**
     * Run the prim method to get the mst nodes array.
     */
    public void runPrim() {

        while(!checkVisited()) {
            TreeNode minNode = null;
            int minIndex = 0;

            // find the min from the array
            for(int i=0;i<size;i++) {
                if(!mst[i].isVisited()) {
                    minNode = mst[i];
                    minIndex = i;
                    break;
                }
            }

            for(int i=0;i<size;i++) {
                if(!mst[i].isVisited() && minNode.getWeight() > mst[i].getWeight()) {
                    minNode = mst[i];
                    minIndex = i;
                }
            }
            minNode.setVisited(true);

            // update the mst array
            for(int i=0;i<size;i++) {
                if(!mst[i].isVisited() && dist[minIndex][i]<mst[i].getWeight()) {
                    mst[i].setParentIndex(minIndex);
                    mst[i].setWeight(dist[minIndex][i]);
                }
            }

        }
    }

    /**
     * Check whether the tree node is visited.
     * @return whether the tree node is visited
     */
    private boolean checkVisited() {
        for(TreeNode node:mst) {
            if(!node.isVisited()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Fill the 2D boolean matrix array that saves the relationship of the MST Nodes
     * from parent point to the child node.
     */
    public void getMSTTree() {
        for(int i=1;i<size;i++) {
            int j = mst[i].getParentIndex();
            tree[j][i] = true;
        }
    }

    /**
     * Run the pre-order traversal of the MST tree based on the 2D boolean matrix array that
     * saves the relationship of the MST Nodes from parent point to the child node. After the
     * pre-order traversal we can get the route of the Hamilton Cycle.
     */
    public void preOrderTraversal() {
        StringBuilder sb = new StringBuilder();
        // start from node 0
        sb.append("0 ");

        // dfs the graph to get the pre-order traversal result
        for(int i=0;i<size;i++) {
            if(tree[0][i]!=false) {
                helper(i,1,sb);
            }
        }

        // end at node 0
        sb.append("0");
        // print the route
        System.out.println(sb.toString());
        // the route
        getTSPArray(sb.toString());
    }

    /**
     * Dfs the graph to get the pre-order traversal result.
     * @param curNode the current node
     * @param index the node index
     * @param sb the string builder to record the route
     */
    private void helper(int curNode, int index, StringBuilder sb) {
        sb.append(curNode).append(" ");
        for(int i=0;i<size;i++) {
            if(tree[curNode][i] != false) {
                helper(i,index+1,sb);
            }
        }
    }

    /**
     * Update the tsp array based on the string builder which record the nodes iterated through the Hamilton Cycle.
     * @param string string which record the nodes iterated through the Hamilton Cycle
     */
    private void getTSPArray(String string) {
        String[] routes = string.split(" ");
        for(int i=0;i<tsp.length;i++) {
            tsp[i] = Integer.parseInt(routes[i]);
        }
    }

    /**
     * Get the distance of the calculated the Hamilton Cycle.
     * @return distance of the calculated the Hamilton Cycle
     */
    public double getCycleLength() {
        double len = 0;
        int from;
        int to;
        for(int i=0;i<tsp.length-1;i++) {
            from = tsp[i];
            to = tsp[i+1];
            len += dist[from][to];
        }
        return len*0.00018939;
    }

    /**
     * Using dfs to get all the permutation from the start node to the end node(exclusively).
     * Example: 0 1 2 3 0. Take 1 2 3, the permutation should be 1 2 3, 1 3 2, 2 1 3, 2 3 1,
     * 3 1 2 and 3 2 1.
     * @return the array list contains all permutation
     */
    private List<String> getPermutation() {
        // exclude the first and last node of 0
        List<String> ans = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        for(int i=1;i<tsp.length-1;i++) {
            sb.append(tsp[i]);
        }

        char[] nums = sb.toString().toCharArray();
        // run dfs
        permutations(nums, ans, 0);
        return ans;
    }

    /**
     * Get the optimal route and the length of the route from calculating the length of all the permutations.
     */
    public void getOptimalTSP() {
        // get all permutations
        List<String> permutations = new ArrayList<>();
        permutations = getPermutation();
        char[] curChar = null;

        double curLen = 0;

        // find the optimal route
        for(String str: permutations) {
            StringBuilder sb = new StringBuilder();
            // append the start node 0
            sb.append("0 ");
            curChar = str.toCharArray();
            for(int i=1;i<tsp.length-1;i++) {
                tsp[i] = Character.getNumericValue(curChar[i-1]);
                sb.append(curChar[i-1]).append(" ");
            }
            // append the end node 0
            sb.append("0");
            // calculate the length
            curLen = getCycleLength();
            // update the route and length of the optimal route
            if(curLen < minLen) {
                minLen = curLen;
                minPermuatation = sb.toString();
            }
        }
    }

    /**
     * Using dfs to get all the permutation from the start node to the end node(exclusively).
     * @param nums the char array
     * @param ans the permutation
     * @param index the index
     */
    private void permutations(char[] nums, List<String> ans, int index) {
        // base case
        // reach the end of array
        if(index == nums.length) {
            ans.add(new String(nums));
            return;
        }

        // swap the current node and each nodes after the current node
        for(int i=index;i<nums.length;i++) {
            swap(index, i, nums);
            permutations(nums,ans,index+1);
            swap(index, i, nums);
        }
    }

    /**
     * Swap the element in the array.
     * @param i index i
     * @param j index j
     * @param nums the array
     */
    private void swap(int i, int j, char[] nums) {
        char tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    /**
     * Get the length of the optimal route.
     * @return The length of the optimal route
     */
    public double getMinLen() {
        return minLen;
    }

    /**
     * Set the route of the optimal route
     * @return The route of the optimal route
     */
    public String getMinPermuatation() {
        return minPermuatation;
    }

    /**
     * Get the current nodes recorded iterated through Hamilton Cycle
     * @return The current nodes recorded iterated through Hamilton Cycle
     */
    public int[] getTsp() {
        return tsp;
    }

    /**
     * get the crime records array
     * @return The crime records array
     */
    public CrimeRecord[] getGraph() {
        return graph;
    }

    /**
     * Get graph size
     * @return Graph size
     */
    public int getSize() {
        return size;
    }

    /**
     * Get distance Matrix
     * @return Distance Matrix
     */
    public double[][] getDist() {
        return dist;
    }

    /**
     * Get file name
     * @return File name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Get content of the reading input
     * @return Content of the reading input
     */
    public List<String> getContents() {
        return contents;
    }

    /**
     * Get the MST tree node
     * @return The MST tree node
     */
    public TreeNode[] getMst() {
        return mst;
    }

    /**
     * Get the graph records the relationship of MST tree with each node pointed from parent to child
     * @return The graph records the relationship of MST tree with each node pointed from parent to child
     */
    public boolean[][] getTree() {
        return tree;
    }

    /**
     * Set graph size
     * @param size Graph size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Set the crime records array
     * @param graph The crime records array
     */
    public void setGraph(CrimeRecord[] graph) {
        this.graph = graph;
    }

    /**
     * Set Distance Matrix
     * @param dist Distance Matrix
     */
    public void setDist(double[][] dist) {
        this.dist = dist;
    }

    /**
     * Set file name
     * @param fileName File name
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Set content of the reading input
     * @param contents Content of the reading input
     */
    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    /**
     * Set MST tree node
     * @param mst The MST tree node
     */
    public void setMst(TreeNode[] mst) {
        this.mst = mst;
    }

    /**
     * Set the current nodes recorded iterated through Hamilton Cycle
     * @param tsp the current nodes recorded iterated through Hamilton Cycle
     */
    public void setTsp(int[] tsp) {
        this.tsp = tsp;
    }

    /**
     * Set the graph records the relationship of MST tree with each node pointed from parent to child
     * @param tree The graph records the relationship of MST tree with each node pointed from parent to child
     */
    public void setTree(boolean[][] tree) {
        this.tree = tree;
    }

    /**
     * Set the length of the optimal route
     * @param minLen The length of the optimal route
     */
    public void setMinLen(double minLen) {
        this.minLen = minLen;
    }

    /**
     * Set the route of the optimal route
     * @param minPermuatation The route of the optimal route
     */
    public void setMinPermuatation(String minPermuatation) {
        this.minPermuatation = minPermuatation;
    }
}
