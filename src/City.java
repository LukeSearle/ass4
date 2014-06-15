/**
 * This class represents a city that needs to be visited.
 *
 * @author Kathryn Merrick
 * @version 26/05/2011
 *
 */
public class City
{
	/** The x co-ordinate of this city. */
	private double xPos;
	/** The y co-ordinate of this city. */
	private double yPos;
	
	/**
	 * Create a new city with given coordinates
	 * @param xPos The x co-ordinate
	 * @param yPos The y co-ordinate
	 */
	public City(double xPos, double yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	/**
	 * Get the x coordinate of this city.
	 * @return The x-coordinate of this city.
	 */
	public double getXPos()
	{
		return xPos;
	}
	
	/**
	 * Get the y coordinate of this city.
	 * @return The y-coordinate of this city.
	 */
	public double getYPos()
	{
		return yPos;
	}
	
	/**
	 * Return a string representation of this city.
	 * @return The description.
	 */
	public String toString()
	{
		return "(" + xPos + ", " + yPos + ")";
	}
	
	/**
	 * Compute the distance from this city to given city
	 * @param city City to get distance to.
	 * @return The distance
	 */
	public double computeDistanceTo(City city)
	{
        double ans = 0.0;
        try{
            return Math.sqrt((this.xPos - city.xPos)*(this.xPos - city.xPos) + (this.yPos - city.yPos)*(this.yPos - city.yPos));
        } catch (Exception e) {
            System.out.println("ERROR");
        }
        return ans;
	}
}