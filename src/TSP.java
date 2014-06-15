import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Abstract class for a travelling salesperson solution.
 * Given a number of cities and travelling distances between those cities, find the 
 * shortest roundtrip route that visits each city once and returns to the starting city. 
 *
 * @author Kathryn Merrick
 * @version 26/05/2011
 *
 */
public abstract class TSP 
{
	/** The cities to be visited. First element is the start-end city. */
	protected LinkedList<City> cities; 

	
	/** A list of all minimum paths, themselves represented as lists of cities. */
	protected LinkedList<Path> minPaths; 
	
	/**
	 * Create a new travelling salesperson solution that finds the
	 * minimum length path that starts at the first city in the given file
	 * then visits cities 2 up to n exactly once and returns to the 
	 * first city.
	 * @param filename The file to read cities from.
	 * @param n The number of cities to read.
	 */
	public TSP(String filename, int n)
	{
		readCities(filename, n);
		minPaths = new LinkedList<Path>();
	}
	
	/**
	 * Read a list of n cities from a text file.
	 * @param filename The file to read cities from
	 * @param n The number of cities to read from the file.
	 */
	protected void readCities(String filename, int n)
	{
		cities = new LinkedList<City>();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(filename));
			for(int i=0; i<n; i++)
			{
				String line = br.readLine();
				String[] coords = line.split("\t");
				cities.add(new City(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));	
			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Pre-process the cities list to aid path generation.
	 */
	protected abstract void preProcess();
	
	/**
	 * Finds a list of shortest paths that start at the first city, visit
	 * all the other cities, then returns to the first city. 
	 * 
	 * @return A linked list of shortest paths.
	 */
	public abstract LinkedList<Path> findMinPaths();


}


