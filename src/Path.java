import java.util.LinkedList;

/**
 * A path between cities. A path starts and ends with the same city.
 * 
 * @author Kathryn Merrick
 * @version 05/05/2014
 *
 */
public class Path extends LinkedList<City>
{
	/**
	 * Compute the total distance of this path.
	 * 
	 * @return The sum of the distances between all cities on this path.
	 */
	public double computeDistance()
	{
		double distance = 0;
		for(int i=0; i<size()-1; i++)
		{
			City city = get(i);
            try {
                distance += city.computeDistanceTo(get(i + 1));
            } catch (NullPointerException e) {

            }
		}	
		return distance;
	}
	
	/**
	 * Return a string representation of the cities in this path.
	 * 
	 * @return a string representation of the cities in this path.
	 */
	public String toString()
	{
        String pathStr = "";
		for(int i=0; i<size(); i++)
            try {
                pathStr += get(i).toString();
            } catch (NullPointerException e) {}
		return pathStr;
	}
}
