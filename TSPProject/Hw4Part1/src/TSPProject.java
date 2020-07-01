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

        // print the length of the non optimal route
        System.out.println();
        System.out.print("Length Of cycle:  "+graph.getCycleLength()+" miles");
    }
}
