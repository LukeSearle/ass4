import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

/**
 * Heuristic solution to the travelling sales person problem.
 *
 * @author Kathryn Merrick && ??
 * @version 05/05/2014
 *
 */
public class TSP_Heuristic1 extends TSP {
    protected int numCities;

    /**
     * Heuristic method to solve TSP
     *
     * @param filename The name of the file with city coords
     * @param n        The number of cities to read
     */
    public TSP_Heuristic1(String filename, int n) {
        super(filename, n);
        numCities = n;
        preProcess();
    }


    /**
     * Pre-process the linked list of cities to aid path generation.
     */
    protected void preProcess() {
        // Task 2 (Optional).
    }

    /**
     * Finds a (near) shortest path that starts at the first city, visits
     * all the other city, then returns to the first city; and stores
     * this information in the appropriate instance variables.
     */
    public LinkedList<Path> findMinPaths() {
        // Initialize population
        Population pop = new Population(100, true);

        GA g = new GA();
        // Evolve population for 100 generations
        pop = g.evolvePopulation(pop);
        for (int i = 0; i < 1000; i++) {
            pop = g.evolvePopulation(pop);
        }

        // Print final results
        minPaths.add(pop.getFittest().getAsPath());
        return minPaths;
    }


    /**
     * Code I will use to test your Task 2.
     */
    public static void main(String[] args) {
        // Test and time the preprocessing component
        Date dateBefore = new Date();
        TSP bruteForce = new TSP_Heuristic1("cities.txt", 5);
        Date dateAfter = new Date();
        long preprocessingTime =
                dateAfter.getTime() - dateBefore.getTime();
        System.out.println("Time to preprocess is: " + preprocessingTime +
                " milliseconds.");

        // Test and time the solution generation component
        dateBefore = new Date();
        LinkedList<Path> minPaths = bruteForce.findMinPaths();
        dateAfter = new Date();
        long pathGenTime = dateAfter.getTime() - dateBefore.getTime();
        System.out.println("Time to compute paths is: " + pathGenTime +
                " milliseconds.");

        System.out.println(
                "Total time is: " + (pathGenTime + preprocessingTime) +
                        " milliseconds.");

        // Display the length of the min path(s) found
        if (!minPaths.isEmpty()) {
            double minPathLength = minPaths.getFirst().computeDistance();
            System.out.println("The length of the minimum path is: " +
                    minPathLength);

            // Display the actual min path(s).
            System.out.println("The minimum path(s): ");
            for (int j = 0; j < minPaths.size(); j++) {
                Path path = minPaths.get(j);
                System.out.println(path.toString());
            }
        } else {
            System.out.println("Error: no paths generated.");
        }

    }

    public class Population {

        // Holds population of tours
        Tour[] tours;

        // Construct a population
        public Population(int populationSize, boolean initialise) {
            tours = new Tour[populationSize];
            // If we need to initialise a population of tours do so
            if (initialise) {
                // Loop and create individuals
                for (int i = 0; i < populationSize(); i++) {
                    Tour newTour = new Tour();
                    newTour.generateIndividual();
                    saveTour(i, newTour);
                }
            }
        }

        // Saves a tour
        public void saveTour(int index, Tour tour) {
            tours[index] = tour;
        }

        // Gets a tour from population
        public Tour getTour(int index) {
            return tours[index];
        }

        // Gets the best tour in the population
        public Tour getFittest() {
            Tour fittest = tours[0];
            // Loop through individuals to find fittest
            for (int i = 1; i < populationSize(); i++) {
                if (fittest.getFitness() <= getTour(i).getFitness()) {
                    fittest = getTour(i);
                }
            }
            return fittest;
        }

        // Gets population size
        public int populationSize() {
            return tours.length;
        }
    }

    public class Tour {

        // Holds our tour of cities
        private ArrayList tour = new ArrayList<City>();
        // Cache
        private double fitness = 0;
        private int distance = 0;

        // Constructs a blank tour
        public Tour() {
            for (int i = 0; i < numCities; i++) {
                tour.add(null);
            }
        }

        /**
         * Adds the first city to the end of the tour
         */
        public void loop() {
            City firstCity = getCity(0);
            tour.add(firstCity);
        }

        public Tour(ArrayList tour) {
            this.tour = tour;
        }

        // Creates a random individual
        public void generateIndividual() {
            // Loop through all our destination cities and add them to our tour
            for (int cityIndex = 0; cityIndex < numCities; cityIndex++) {
                setCity(cityIndex, cities.get(cityIndex));
            }
            // Randomly reorder the tour
            Collections.shuffle(tour);
            loop();
        }

        // Gets a city from the tour
        public City getCity(int tourPosition) {
            return (City) tour.get(tourPosition);
        }

        // Sets a city in a certain position within a tour
        public void setCity(int tourPosition, City city) {
            tour.set(tourPosition, city);
            // If the tours been altered we need to reset the fitness and distance
            fitness = 0;
            distance = 0;
        }

        // Gets the tours fitness
        public double getFitness() {
            if (fitness == 0) {
                fitness = 1 / (double) getDistance();
            }
            return fitness;
        }

        // Gets the total distance of the tour
        public int getDistance() {
            if (distance == 0) {
                int tourDistance = 0;
                // Loop through our tour's cities
                for (int cityIndex = 0; cityIndex < tourSize();
                     cityIndex++) {
                    // Get city we're travelling from
                    City fromCity = getCity(cityIndex);
                    // City we're travelling to
                    City destinationCity = getCity(0);
                    // Check we're not on our tour's last city, if we are set our
                    // tour's final destination city to our starting city
                    if (cityIndex + 1 < tourSize()) {
                        destinationCity = getCity(cityIndex + 1);
                    }
                    // Get the distance between the two cities
                    if (fromCity != null && destinationCity != null)
                    tourDistance += fromCity.computeDistanceTo(destinationCity);
                }
                distance = tourDistance;
            }
            return distance;
        }

        public Path getAsPath(){
            Path p = new Path();

            p.addAll(tour);

            return p;
        }

        // Get number of cities on our tour
        public int tourSize() {
            return tour.size();
        }

        // Check if the tour contains a city
        public boolean containsCity(City city) {
            return tour.contains(city);
        }

        @Override
        public String toString() {
            String geneString = "|";
            for (int i = 0; i < tourSize(); i++) {
                geneString += getCity(i) + "|";
            }
            return geneString;
        }
    }

    public class GA {

        /* GA parameters */
        private static final double mutationRate = 0.015;
        private static final int tournamentSize = 5;
        private static final boolean elitism = true;

        // Evolves a population over one generation
        public Population evolvePopulation(Population pop) {
            Population newPopulation = new Population(pop.populationSize(), false);

            // Keep our best individual if elitism is enabled
            int elitismOffset = 0;
            if (elitism) {
                newPopulation.saveTour(0, pop.getFittest());
                elitismOffset = 1;
            }

            // Crossover population
            // Loop over the new population's size and create individuals from
            // Current population
            for (int i = elitismOffset;
                 i < newPopulation.populationSize(); i++) {
                // Select parents
                Tour parent1 = tournamentSelection(pop);
                Tour parent2 = tournamentSelection(pop);
                // Crossover parents
                Tour child = crossover(parent1, parent2);
                // Add child to new population
                newPopulation.saveTour(i, child);
            }

            // Mutate the new population a bit to add some new genetic material
            for (int i = elitismOffset;
                 i < newPopulation.populationSize(); i++) {
                mutate(newPopulation.getTour(i));
            }

            return newPopulation;
        }

        // Applies crossover to a set of parents and creates offspring
        public Tour crossover(Tour parent1, Tour parent2) {
            // Create new child tour
            Tour child = new Tour();

            // Get start and end sub tour positions for parent1's tour
            int startPos = (int) (Math.random() * parent1.tourSize());
            int endPos = (int) (Math.random() * parent1.tourSize());

            // Loop and add the sub tour from parent1 to our child
            for (int i = 0; i < child.tourSize(); i++) {
                // If our start position is less than the end position
                if (startPos < endPos && i > startPos && i < endPos) {
                    child.setCity(i, parent1.getCity(i));
                } // If our start position is larger
                else if (startPos > endPos) {
                    if (!(i < startPos && i > endPos)) {
                        child.setCity(i, parent1.getCity(i));
                    }
                }
            }

            // Loop through parent2's city tour
            for (int i = 0; i < parent2.tourSize(); i++) {
                // If child doesn't have the city add it
                if (!child.containsCity(parent2.getCity(i))) {
                    // Loop to find a spare position in the child's tour
                    for (int ii = 0; ii < child.tourSize(); ii++) {
                        // Spare position found, add city
                        if (child.getCity(ii) == null) {
                            child.setCity(ii, parent2.getCity(i));
                            break;
                        }
                    }
                }
            }
            child.loop();
            return child;
        }

        // Mutate a tour using swap mutation
        private void mutate(Tour tour) {
            // Loop through tour cities
            for (int tourPos1 = 0; tourPos1 < tour.tourSize();
                 tourPos1++) {
                // Apply mutation rate
                if (Math.random() < mutationRate) {
                    // Get a second random position in the tour
                    int tourPos2 = (int) (tour.tourSize() * Math.random());

                    // Get the cities at target position in tour
                    City city1 = tour.getCity(tourPos1);
                    City city2 = tour.getCity(tourPos2);

                    // Swap them around
                    tour.setCity(tourPos2, city1);
                    tour.setCity(tourPos1, city2);
                }
            }
        }

        // Selects candidate tour for crossover
        private Tour tournamentSelection(Population pop) {
            // Create a tournament population
            Population tournament = new Population(tournamentSize, false);
            // For each place in the tournament get a random candidate tour and
            // add it
            for (int i = 0; i < tournamentSize; i++) {
                int randomId = (int) (Math.random() * pop.populationSize());
                tournament.saveTour(i, pop.getTour(randomId));
            }
            // Get the fittest tour
            Tour fittest = tournament.getFittest();
            return fittest;
        }
    }
}
