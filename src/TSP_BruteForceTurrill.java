import java.util.*;

/***********************************************************************
 * Brute force solution to the travelling sales person problem.
 *
 * @author Kathryn Merrick
 * @author Aaron Turrill
 * @version 03JUN2014
 *
 *********************************************************************/
public class TSP_BruteForceTurrill extends TSP
{
    /**
     * The number of cities
     */
    private int numCities;
    /**
     * The distances between each city and all others
     */
    private double[][] cityDistances;

    /**
     * The current path
     */
    private Path currentPath;

    /**
     * ArrayList of Integers representing cities
     */
    private ArrayList<Integer> cityIntArray;

    /**
     * Current path as an array of ints representing cities
     */
    private int[] pathIntArray;

    /**
     * The shortest path as an array of ints representing cities
     */
    private int[] shortestPathIntArray;

    /**
     * The shortest path's length
     */
    double shortestPathDistance = Double.MAX_VALUE;

    /**
     * Brute force method to solve TSP for the first
     * n cities in the given file.
     * @param filename The name of the file with the city coordinates.
     * @param n The number of cities to read in.
     */
    public TSP_BruteForceTurrill(String filename, int n)
    {
        super(filename, n);

        currentPath = new Path();
        cityDistances = new double[n][n];
        pathIntArray = new int[n+1];
        shortestPathIntArray = new int[n+1];
        numCities = n;
        preProcess();
    }

    /**
     * Pre-process the linked list of cities to aid path generation.
     */
    protected void preProcess()
    {
        cityIntArray = new ArrayList<Integer>();

        pathIntArray[numCities] = 0;

        // Fill cityIntArray with appropriate ints
        for(int i = 0; i<numCities; i++){
            cityIntArray.add(i);
        }

        // Fill a temporary city array from cities (faster than
        // accessing a LinkedList
        City[] tempCityArray = new City[numCities];
        for(int i = 0; i < tempCityArray.length; i++){
            tempCityArray[i] = cities.get(i);
        }

        for(int x = 0; x < tempCityArray.length; x++){
            for(int y = 0; y  <tempCityArray.length; y++){
                cityDistances[x][y] = tempCityArray[x].
                        computeDistanceTo(tempCityArray[y]);
            }
        }
    }

    /**
     * Find the shortest paths
     */
    public LinkedList<Path> findMinPaths()
    {
        findSmallest(0);
        createPaths();
        return minPaths;
    }

    /**
     * Finds the smallest paths possible.
     *
     * @param level The level that the recursion is on. Should be
     * passed 1 to start properly
     */
    private void findSmallest(int level){

        if(cityIntArray.size()==1){ // Last city
            pathIntArray[level] = cityIntArray.remove(0);
            double currentDistance = 0;

            for(int i = 0; i<(pathIntArray.length-1); i++){
                currentDistance+=
                        cityDistances[pathIntArray[i]][pathIntArray[i+1]];
            }

            if(currentDistance < shortestPathDistance){
                shortestPathDistance = currentDistance;
                shortestPathIntArray = null;
                shortestPathIntArray = Arrays.copyOf(pathIntArray,numCities+1);
            }
            cityIntArray.add(pathIntArray[level]);
            pathIntArray[level] = 0;
        }
        else if(level!=0){ // Other cities
            for(int i = 0; i < cityIntArray.size(); i++){
                pathIntArray[level] = cityIntArray.remove(i);
                findSmallest(level + 1);
                cityIntArray.add(i, pathIntArray[level]);
                pathIntArray[level] = 0;
            }
        }
        else{ // First city
            pathIntArray[level] = cityIntArray.remove(0);
            findSmallest(level + 1);
            cityIntArray.add(0, pathIntArray[level]);
            pathIntArray[level] = 0;
        }
    }

    /**
     * Transforms the integer array that has the shortest path to a
     * path and adds it to minPaths
     */
    private void createPaths(){
        for(int i = 0; i<numCities+1; i++){
            currentPath.add(cities.get(shortestPathIntArray[i]));
        }
        minPaths.add((Path)currentPath.clone());
        currentPath.clear();

        //Does the same as above, but in reverse
        for(int i = (numCities); i>=0; i--){
            currentPath.add(cities.get(shortestPathIntArray[i]));
        }
        minPaths.add((Path)currentPath.clone());
        currentPath.clear();
    }

    /**
     * Actual code I will use to test your class.   
     */
    public static void main(String[] args)
    {
        // Test and time the preprocessing component 
        Date dateBefore = new Date();
        TSP bruteForce = new TSP_BruteForceTurrill("cities.txt", 12);
        Date dateAfter = new Date();
        long preprocessingTime = dateAfter.getTime() - dateBefore.getTime();
        System.out.println("Time to preprocess is: " + preprocessingTime + " milliseconds.");

        // Test and time the solution generation component
        dateBefore = new Date();
        LinkedList<Path> minPaths = bruteForce.findMinPaths();
        dateAfter = new Date();
        long pathGenTime = dateAfter.getTime() - dateBefore.getTime();
        System.out.println("Time to compute paths is: " + pathGenTime + " milliseconds.");

        System.out.println("Total time us: " + (pathGenTime+preprocessingTime) + " milliseconds.");

        // Display the length of the min path(s) found
        if(!minPaths.isEmpty())
        {
            double minPathLength = minPaths.getFirst().computeDistance();
            System.out.println("The length of the minimum path is: " + minPathLength);

            // Display the actual min path(s).
            System.out.println("The minimum path(s): ");
            for (Path path : minPaths) {
                System.out.println(path.toString());
            }
        }
        else
        {
            System.out.println("Error: no paths generated.");
        }
    }

}