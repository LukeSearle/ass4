import java.util.Date;
import java.util.LinkedList;

/**
 * Heuristic solution to the travelling sales person problem.
 *
 * @author Kathryn Merrick && ??
 * @version 05/05/2014
 *
 */
public class TSP_Heuristic2 extends TSP
{
	
	/**
	 * Heuristic method to solve TSP
	 * @param filename The name of the file with city coords
	 * @param n The number of cities to read
	 */
	public TSP_Heuristic2(String filename, int n)
	{
		super(filename, n);
		preProcess();
	}
	
	
	/**
	 * Pre-process the linked list of cities to aid path generation.
	 */
	protected void preProcess()
	{
		// Task 2 (Optional).
	}
	
	/**
	 * Finds a (near) shortest path that starts at the first city, visits
	 * all the other city, then returns to the first city; and stores
	 * this information in the appropriate instance variables.
	 */
	public LinkedList<Path> findMinPaths()
	{
		// Implement for Task 2.
		return minPaths;
	}
	
	
	
	/** Code I will use to test your Task 2. */
	public static void main(String[] args) 
	{
		// Test and time the preprocessing component 
		Date dateBefore = new Date();
		TSP bruteForce = new TSP_Heuristic2("cities.txt", 100);
		Date dateAfter = new Date();
		long preprocessingTime = dateAfter.getTime() - dateBefore.getTime();
		System.out.println("Time to preprocess is: " + preprocessingTime + " milliseconds.");
		
		// Test and time the solution generation component
		dateBefore = new Date();
		LinkedList<Path> minPaths = bruteForce.findMinPaths();
		dateAfter = new Date();
		long pathGenTime = dateAfter.getTime() - dateBefore.getTime();
		System.out.println("Time to compute paths is: " + pathGenTime + " milliseconds.");
		
		System.out.println("Total time is: " + (pathGenTime+preprocessingTime) + " milliseconds.");
		
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
