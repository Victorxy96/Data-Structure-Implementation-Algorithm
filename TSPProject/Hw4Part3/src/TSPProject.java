import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

/**
 * The Main Route for the TSP project.
 */
public class TSPProject {

    /**
     * The file name
     */
    private static final String FILENAME = "PGHCrimes.kml";

    /**
     * Write the kml file records the optimal and non optimal routes.
     * @param graphRecords the graph records based on the reading input
     * @param nonOptimalRoute non optimal route
     * @param optimalRoute optimal route
     */
    public static void writeFile(CrimeRecord[] graphRecords, int[] nonOptimalRoute, int[] optimalRoute) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILENAME));

            bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
            bufferedWriter.write("<kml xmlns=\"http://earth.google.com/kml/2.2\">\n");
            bufferedWriter.write("<Document>\n");
            bufferedWriter.write("<name>Pittsburgh TSP</name><description>TSP on Crime</description><Style id=\"style6\">\n");
            bufferedWriter.write("<LineStyle>\n");
            bufferedWriter.write("<color>73FF0000</color>\n");
            bufferedWriter.write("<width>5</width>\n");
            bufferedWriter.write("</LineStyle>\n");
            bufferedWriter.write("</Style>\n");
            bufferedWriter.write("<Style id=\"style5\">\n");
            bufferedWriter.write("<LineStyle>\n");
            bufferedWriter.write("<color>507800F0</color>\n");
            bufferedWriter.write("<width>5</width>\n");
            bufferedWriter.write("</LineStyle>\n");
            bufferedWriter.write("</Style>\n");
            bufferedWriter.write("<Placemark>\n");
            bufferedWriter.write("<name>TSP Path</name>\n");
            bufferedWriter.write("<description>TSP Path</description>\n");
            bufferedWriter.write("<styleUrl>#style6</styleUrl>\n");
            bufferedWriter.write("<LineString>\n");
            bufferedWriter.write("<tessellate>1</tessellate>\n");
            bufferedWriter.write("<coordinates>\n");

            // write the location of the non optimal route
            for(int routeIndex:nonOptimalRoute) {
                bufferedWriter.write(graphRecords[nonOptimalRoute[routeIndex]].getLon()+0.001+","+
                        graphRecords[nonOptimalRoute[routeIndex]].getLat()+0.001+","+ 0.000000
                        + "\n");
            }

            bufferedWriter.write("</coordinates>\n");
            bufferedWriter.write("</LineString>\n");
            bufferedWriter.write("</Placemark>\n");
            bufferedWriter.write("<Placemark>\n");
            bufferedWriter.write("<name>Optimal Path</name>\n");
            bufferedWriter.write("<description>Optimal Path</description>\n");
            bufferedWriter.write("<styleUrl>#style5</styleUrl>\n");
            bufferedWriter.write("<LineString>\n");
            bufferedWriter.write("<tessellate>1</tessellate>\n");
            bufferedWriter.write("<coordinates>\n");

            // write the location of the optimal route
            for(int routeIndex:optimalRoute) {
                bufferedWriter.write(graphRecords[optimalRoute[routeIndex]].getLon()+","+
                        graphRecords[optimalRoute[routeIndex]].getLat()+","+ 0.000000
                        + "\n");
            }

            bufferedWriter.write("</coordinates>\n");
            bufferedWriter.write("</LineString>\n");
            bufferedWriter.write("</Placemark>\n");
            bufferedWriter.write("</Document>\n");
            bufferedWriter.write("</kml>\n");

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Writing file error.");
            return;
        }
    }

    /**
     * Main method.
     * @param args args
     * @throws IOException io exception
     * @throws ParseException exception
     */
    public static void main(String[] args) throws IOException, ParseException {
        // the graph established based on the user input, the non optimal
        // and optimal route
        CrimeRecord[] graphRecords = null;
        int[] nonOptimalRoute = null;
        int[] optimalRoute = null;

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

        graphRecords = graph.getGraph();

        System.out.println();
        // record the non optimal route
        nonOptimalRoute = new int[graph.getTsp().length];
        System.arraycopy(graph.getTsp(),0,nonOptimalRoute,0,graph.getTsp().length);

        // get all permutations and find the optimal one
        // and get the info of the optimal route
        System.out.println();
        System.out.println("Looking at every permutation to find the optimal solution");
        graph.getOptimalTSP();
        System.out.println("The best permutation");
        System.out.println(graph.getMinPermuatation());

        System.out.println();
        System.out.print("Optimal Cycle length = "+graph.getMinLen()+" miles");

        // record the optimal route
        System.out.println();
        optimalRoute = new int[graph.getTsp().length];
        System.arraycopy(graph.getTsp(),0,optimalRoute,0,graph.getTsp().length);

        // write the two routes into the kml file
        writeFile(graphRecords,nonOptimalRoute,optimalRoute);
    }
}
