import java.util.*;

/***********************************************************************
 * Brute force solution to the travelling sales person problem.
 *
 * @author Kathryn Merrick
 * @author Aaron Turrill
 * @version 03JUN2014
 *
 *********************************************************************/
public class TSP_BruteForce extends TSP
{
    private int numCities;

    //private int numReps = 0;

    private double[][] cityDistances;

    private Path shortestPath;

    private double shortestDistance;

    private Path currentPath;

    private ArrayList<Integer> cityInt;

    private int[] pathInt;

    private int[] shortestPathInt;

    /**
     * Brute force method to solve TSP for the first
     * n cities in the given file.
     * @param filename The name of the file with the city coordinates.
     * @param n The number of cities to read in.
     */
    public TSP_BruteForce(String filename, int n)
    {
        super(filename, n);
        shortestDistance = Double.MAX_VALUE;
        shortestPath = new Path();
        currentPath = new Path();
        cityDistances = new double[n][n];
        pathInt = new int[n+1];
        shortestPathInt = new int[n+1];
        numCities = n;
        preProcess();
    }

    /**
     * Pre-process the linked list of cities to aid path generation.
     */
    protected void preProcess()
    {
        cityInt = new ArrayList<Integer>();

        pathInt[numCities] = 0;

        //This fills cityInt with representative integers for each
        //city
        for(int i = 0; i<numCities; i++){
            cityInt.add(i);
        }

        //This fills the cityDistances array
        City[] tempCityArray = new City[numCities];
        for(int i = 0; i<tempCityArray.length; i++){
            tempCityArray[i] = cities.get(i);
        }

        for(int x = 0; x<tempCityArray.length; x++){
            for(int y = 0; y<tempCityArray.length; y++){
                cityDistances[x][y] = tempCityArray[x].
                        computeDistanceTo(tempCityArray[y]);
            }
        }
    }

    /**
     * Finds the shortest path that starts at the first city, visits
     * all the other city, then returns to the first city; and stores
     * this information in the appropriate instance variables.
     */
    public LinkedList<Path> findMinPaths()
    {
        pathSearch(0);
        compilePath();
        //System.out.println("Num. of reps: " + numReps);
        return minPaths;
    }

    /**
     * This method will perform the brute force path search. It does
     * this with an Integer list that has representations of each city
     * and a 2-d array that has the distances between all of the
     * cities.
     *
     * @param location The level that the recursion is on. Should be
     * passed 1 to start properly
     */
    private void pathSearch(int location){
        /*
        if(cities.size()==1){
            currentPath.add(cities.pop());
            double currentDistance = currentPath.computeDistance();
            if(currentDistance<shortestPathDistance){
                shortestPathDistance = currentDistance;
                shortestPath.clear();
                shortestPath.addAll(currentPath);
            }
            cities.add(currentPath.pop());
        }
        else{
            for(int i = 0; i < cities.size(); i++){
                currentPath.add(cities.remove(i));
                pathSearch();
                cities.add(i, currentPath.pop());
            }
        }
        */
        if(cityInt.size()==1){ //last iteration goes here
            pathInt[location] = (cityInt.remove(0)).intValue();
            double currentDistance = 0;
            //currentDistance+=
            //cityDistances[pathInt[numCities]][pathInt[0]];
            for(int i = 0; i<(pathInt.length-1); i++){
                currentDistance+=
                        cityDistances[pathInt[i]][pathInt[i+1]];
            }

            if(currentDistance<shortestDistance){
                shortestDistance = currentDistance;
                shortestPathInt = null;
                shortestPathInt = Arrays.copyOf(pathInt,numCities+1);
            }
            cityInt.add(pathInt[location]);
            pathInt[location] = 0;
        }
        else if(location!=0){ //all other iterations go here
            for(int i = 0; i < cityInt.size(); i++){
                pathInt[location] = cityInt.remove(i);
                pathSearch(location+1);
                cityInt.add(i, pathInt[location]);
                pathInt[location] = 0;
            }
        }
        else{ //First call to pathsearch goes here
            pathInt[location] = cityInt.remove(0);
            pathSearch(location+1);
            cityInt.add(0, pathInt[location]);
            pathInt[location] = 0;
        }
    }

    /**
     * This is a private helper method that constructs paths from
     * the output of pathSearch.
     */
    private void compilePath(){
        for(int i = 0; i<numCities+1; i++){
            currentPath.add(cities.get(shortestPathInt[i]));
        }
        minPaths.add((Path)currentPath.clone());
        currentPath.clear();

        //Does the same as above, but in reverse
        for(int i = (numCities); i>=0; i--){
            currentPath.add(cities.get(shortestPathInt[i]));
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
        TSP bruteForce = new TSP_BruteForce("cities.txt", 12);
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
            for(int j=0; j<minPaths.size(); j++)
            {
                Path path = minPaths.get(j);
                System.out.println(path.toString());
            }
        }
        else
        {
            System.out.println("Error: no paths generated.");
        }
    }

}