import java.io.IOException;
import java.text.ParseException;

/**
 * The Main Route for the TSP project.
 */
public class TSPProject {

    /**
     * Main method.
     * @param args args
     * @throws IOException io exception
     * @throws ParseException exception
     */
    public static void main(String[] args) throws IOException, ParseException {
        // Create the graph
        String fileLocation = "CrimeLatLonXY1990.csv";
        CrimeGraph graph = new CrimeGraph(fileLocation);
        System.out.println();

        // Run prim
        graph.runPrim();

        // Get the MST tree and run the pre-order traversal to get the non optimal route info
        System.out.println("Hamiltonian Cycle (not necessarily optimum):");
        graph.getMSTTree();
        graph.preOrderTraversal();

        System.out.println();
        System.out.print("Length Of cycle:  "+graph.getCycleLength()+" miles");

        // get all permutations and find the optimal one
        // and get the info of the optimal route
        System.out.println();
        System.out.println();
        System.out.println("Looking at every permutation to find the optimal solution");
        graph.getOptimalTSP();
        System.out.println("The best permutation");
        System.out.println(graph.getMinPermuatation());

        System.out.println();
        System.out.print("Optimal Cycle length = "+graph.getMinLen()+" miles");
    }
}
