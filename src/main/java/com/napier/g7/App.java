/**
 * @author Htoo Myat Linn, Kyaw Ko Ko San, Bhone Myat, Wai Yan Moe, Zayar Phyo, Pyae Sone
 * @version 0.1-alpha-3
 * @since 2024-01-23
 */

package com.napier.g7;
import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * The `App` class contains the main method and serves as the entry point for the application.
 * It demonstrates connecting to the database, querying and displaying country and city information,
 * and performing specific queries based on continent and region.
 */
public class App
{
    /**
     * Application entry point. Demonstrates connecting to a MySQL database,
     * querying and displaying country and city information, and performing
     * specific queries based on continent and region.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        //Declaring Variables
        String targetContinent = "Europe";  // Replace "Asia" with the desired continent
        String targetRegion = "Southeast Asia";  // Replace "Southeast Asia" with the desired region
        int numberOfCountries = 10; // Replace "10" with the desired number



        // Extract country population information
        ArrayList<Country> countries = a.getAllCountries();
        System.out.println("\n**********Countries********\n");
        // Print the count of countries
        int countOfCountries = countries.size()-1;
        System.out.println("Number of countries: " + countOfCountries +"\n");
        a.printCountries(countries);

        // Get all countries in the specified continent
        ArrayList<Country> countriesByContinent = a.countriesByContinent(targetContinent);
        System.out.println("\n**********Countries in " + targetContinent + "********\n");
        // Print the count of countries
        int countCountriesByContinent = countriesByContinent.size()-1;
        System.out.println("Number of countries: " + countCountriesByContinent +"\n");
        a.printCountries(countriesByContinent);

        // Get all countries in the specified region
        ArrayList<Country> countriesByRegion = a.countriesByRegion("Southeast Asia");
        System.out.println("\n**********Countries in " + targetRegion + "********\n");
        // Print the count of countries
        int countCountriesByRegion = countriesByRegion.size()-1;
        System.out.println("Number of countries: " + countCountriesByRegion+"\n");
        a.printCountries(countriesByRegion);

        // Extract top N country population information
        System.out.println("\n**********Top "+ numberOfCountries +" Countries********\n");
        a.displayTopPopulatedCountries(numberOfCountries);

        // Extract top N country population information on specific continent
        System.out.println("\n**********Top "+ numberOfCountries +" Countries in " + targetContinent+" ********\n");
        a.displayTopPopulatedCountriesInContinent(10,targetContinent);

        // Extract top N country population information on specific region
        System.out.println("\n**********Top "+ numberOfCountries +" Countries in " + targetRegion+" ********\n");
        a.displayTopPopulatedCountriesInRegion(10,targetRegion);

        // Extract city population information
        ArrayList<City> cities = a.getAllCities();
        System.out.println("\n**********Cities********\n");
        // Print the count of cities
        int countOfCities = cities.size()-1;
        System.out.println("Number of cities: " + countOfCities+"\n");
        a.printCities(cities);

        // Get all cities in the specified continent
        ArrayList<City> citiesByContinent = a.getCitiesByContinent(targetContinent);
        System.out.println("\n**********Cities in " + targetContinent + "********\n");
        // Print the count of cities
        int countCitiesByContinent = citiesByContinent.size()-1;
        System.out.println("Number of cities: " + countCitiesByContinent+"\n");
        a.printCities(citiesByContinent);


        // Disconnect from database
        a.disconnect();
    }

    private Connection con = null;

    /**
     * Connects to the MySQL database.
     * The method loads the MySQL JDBC driver, attempts to connect to the database,
     * and handles connection retries in case of failure.
     *
     * @throws ClassNotFoundException If the MySQL JDBC driver is not found.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnects from the MySQL database.
     * Closes the database connection if it is open.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Prints the details of a list of countries in a formatted table.
     *
     * @param countries The list of Country objects to print.
     */
    public void printCountries(ArrayList<Country> countries) {
        // Print header
        System.out.println(String.format("%-5s %-50s %-15s %-40s %-15s %-10s",
                "Code", "Name", "Continent", "Region", "Population", "Capital"));

        // Loop over all countries in the list
        for (Country country : countries) {
            // Format population with commas
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
            String formattedPopulation = numberFormat.format(country.getPopulation());
            String countryString = String.format("%-5s %-50s %-15s %-40s %-15s %-10s",
                    country.getCode(), country.getName(), country.getContinent(),
                    country.getRegion(), formattedPopulation, country.getCapital());
            System.out.println(countryString);
        }
    }

    /**
     * Prints the details of a list of cities in a formatted table.
     *
     * @param cities The list of City objects to print.
     */
    public void printCities(ArrayList<City> cities) {

        // Print header
        System.out.println(String.format("%-40s %-40s %-30s %-15s",
                "Name", "CountryName", "District", "Population"));

        // Loop over all cities in the list
        for (City city : cities) {
            // Format population with commas
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
            String formattedPopulation = numberFormat.format(city.getPopulation());
            String cityString = String.format("%-40s %-40s %-30s %-15s",
                    city.getName(), city.getCountryCode(), city.getDistrict(), formattedPopulation);
            System.out.println(cityString);
        }
    }

    /**
     * Retrieves a list of all countries ordered by population in descending
     * order.
     *
     * @return An ArrayList of Country objects representing all countries,
     *         ordered by population in descending order.
     */
    public ArrayList<Country> getAllCountries() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT code, name, continent, region, surfaceArea, indepYear, " +
                            "population, lifeExpectancy, gnp, gnpOld, localName, " +
                            "governmentForm, headOfState, capital " +
                            "FROM country " +
                            "ORDER BY population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> countries = new ArrayList<>();
            while (rset.next()) {
                // Create a new Country object
                Country country = new Country();
                // Set country attributes from the result set
                country.setCode(rset.getString("code"));
                country.setName(rset.getString("name"));
                country.setContinent(rset.getString("continent"));
                country.setRegion(rset.getString("region"));
                country.setSurfaceArea(rset.getFloat("surfaceArea"));
                country.setIndepYear(rset.getInt("indepYear"));
                country.setPopulation(rset.getInt("population"));
                country.setLifeExpectancy(rset.getFloat("lifeExpectancy"));
                country.setGnp(rset.getFloat("gnp"));
                country.setGnpOld(rset.getFloat("gnpOld"));
                country.setLocalName(rset.getString("localName"));
                country.setGovernmentForm(rset.getString("governmentForm"));
                country.setHeadOfState(rset.getString("headOfState"));
                country.setCapital(rset.getInt("capital"));
                // Add the Country object to the ArrayList
                countries.add(country);
            }
            // Return the list of countries
            return countries;
        } catch (Exception e) {
            // Print error messages in case of an exception
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            // Return null in case of an exception
            return null;
        }
    }

    /**
     * Retrieves a list of countries based on the specified continent, ordered
     * by population in descending order.
     *
     * @param continent The continent to filter countries.
     * @return An ArrayList of Country objects representing countries in the
     *         specified continent, ordered by population in descending order.
     */
    public ArrayList<Country> countriesByContinent(String continent) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT code, name, continent, region, surfaceArea, indepYear, " +
                            "population, lifeExpectancy, gnp, gnpOld, localName, " +
                            "governmentForm, headOfState, capital " +
                            "FROM country " +
                            "WHERE continent = '" + continent + "' " +
                            "ORDER BY population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> countries = new ArrayList<>();
            while (rset.next()) {
                // Create a new Country object
                Country country = new Country();
                // Set country attributes from the result set
                country.setCode(rset.getString("code"));
                country.setName(rset.getString("name"));
                country.setContinent(rset.getString("continent"));
                country.setRegion(rset.getString("region"));
                country.setSurfaceArea(rset.getFloat("surfaceArea"));
                country.setIndepYear(rset.getInt("indepYear"));
                country.setPopulation(rset.getInt("population"));
                country.setLifeExpectancy(rset.getFloat("lifeExpectancy"));
                country.setGnp(rset.getFloat("gnp"));
                country.setGnpOld(rset.getFloat("gnpOld"));
                country.setLocalName(rset.getString("localName"));
                country.setGovernmentForm(rset.getString("governmentForm"));
                country.setHeadOfState(rset.getString("headOfState"));
                country.setCapital(rset.getInt("capital"));
                // Add the Country object to the ArrayList
                countries.add(country);
            }
            // Return the list of countries
            return countries;
        } catch (Exception e) {
            // Print error messages in case of an exception
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details by continent");
            // Return null in case of an exception
            return null;
        }
    }

    /**
     * Retrieves a list of countries based on the specified region, ordered by
     * population in descending order.
     *
     * @param region The region to filter countries.
     * @return An ArrayList of Country objects representing countries in the
     *         specified region, ordered by population in descending order.
     */
    public ArrayList<Country> countriesByRegion(String region) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT code, name, continent, region, surfaceArea, indepYear, " +
                            "population, lifeExpectancy, gnp, gnpOld, localName, " +
                            "governmentForm, headOfState, capital " +
                            "FROM country " +
                            "WHERE region = '" + region + "' " +
                            "ORDER BY population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> countries = new ArrayList<>();
            while (rset.next()) {
                // Create a new Country object
                Country country = new Country();
                // Set country attributes from the result set
                country.setCode(rset.getString("code"));
                country.setName(rset.getString("name"));
                country.setContinent(rset.getString("continent"));
                country.setRegion(rset.getString("region"));
                country.setSurfaceArea(rset.getFloat("surfaceArea"));
                country.setIndepYear(rset.getInt("indepYear"));
                country.setPopulation(rset.getInt("population"));
                country.setLifeExpectancy(rset.getFloat("lifeExpectancy"));
                country.setGnp(rset.getFloat("gnp"));
                country.setGnpOld(rset.getFloat("gnpOld"));
                country.setLocalName(rset.getString("localName"));
                country.setGovernmentForm(rset.getString("governmentForm"));
                country.setHeadOfState(rset.getString("headOfState"));
                country.setCapital(rset.getInt("capital"));
                // Add the Country object to the ArrayList
                countries.add(country);
            }
            // Return the list of countries
            return countries;
        } catch (Exception e) {
            // Print error messages in case of an exception
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details by region");
            // Return null in case of an exception
            return null;
        }
    }

    /**
     * Displays the top N populated countries based on population in
     * descending order.
     *
     * @param topN The number of top populated countries to display.
     */
    public void displayTopPopulatedCountries(int topN) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT code, name, continent, region, surfaceArea, indepYear, " +
                            "population, lifeExpectancy, gnp, gnpOld, localName, " +
                            "governmentForm, headOfState, capital " +
                            "FROM country " +
                            "ORDER BY population DESC " +
                            "LIMIT " + topN;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> countries = new ArrayList<>();
            while (rset.next()) {
                // Create a new Country object
                Country country = new Country();
                // Set country attributes from the result set
                country.setCode(rset.getString("code"));
                country.setName(rset.getString("name"));
                country.setContinent(rset.getString("continent"));
                country.setRegion(rset.getString("region"));
                country.setSurfaceArea(rset.getFloat("surfaceArea"));
                country.setIndepYear(rset.getInt("indepYear"));
                country.setPopulation(rset.getInt("population"));
                country.setLifeExpectancy(rset.getFloat("lifeExpectancy"));
                country.setGnp(rset.getFloat("gnp"));
                country.setGnpOld(rset.getFloat("gnpOld"));
                country.setLocalName(rset.getString("localName"));
                country.setGovernmentForm(rset.getString("governmentForm"));
                country.setHeadOfState(rset.getString("headOfState"));
                country.setCapital(rset.getInt("capital"));
                // Add the Country object to the ArrayList
                countries.add(country);
            }

            // Print the top N populated countries
            printCountries(countries);

        } catch (Exception e) {
            // Print error messages in case of an exception
            System.out.println(e.getMessage());
            System.out.println("Failed to get top populated countries");
        }
    }

    /**
     * Displays the top N populated countries in the specified continent based
     * on population in descending order.
     *
     * @param topN      The number of top populated countries to display.
     * @param continent The continent to filter countries.
     */
    public void displayTopPopulatedCountriesInContinent(int topN, String continent) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT code, name, continent, region, surfaceArea, indepYear, " +
                            "population, lifeExpectancy, gnp, gnpOld, localName, " +
                            "governmentForm, headOfState, capital " +
                            "FROM country " +
                            "WHERE continent = '" + continent + "' " +
                            "ORDER BY population DESC " +
                            "LIMIT " + topN;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> countries = new ArrayList<>();
            while (rset.next()) {
                // Create a new Country object
                Country country = new Country();
                // Set country attributes from the result set
                country.setCode(rset.getString("code"));
                country.setName(rset.getString("name"));
                country.setContinent(rset.getString("continent"));
                country.setRegion(rset.getString("region"));
                country.setSurfaceArea(rset.getFloat("surfaceArea"));
                country.setIndepYear(rset.getInt("indepYear"));
                country.setPopulation(rset.getInt("population"));
                country.setLifeExpectancy(rset.getFloat("lifeExpectancy"));
                country.setGnp(rset.getFloat("gnp"));
                country.setGnpOld(rset.getFloat("gnpOld"));
                country.setLocalName(rset.getString("localName"));
                country.setGovernmentForm(rset.getString("governmentForm"));
                country.setHeadOfState(rset.getString("headOfState"));
                country.setCapital(rset.getInt("capital"));
                // Add the Country object to the ArrayList
                countries.add(country);
            }

            // Print the top N populated countries in the specified continent
            printCountries(countries);

        } catch (Exception e) {
            // Print error messages in case of an exception
            System.out.println(e.getMessage());
            System.out.println("Failed to get top populated countries in the continent");
        }
    }

    /**
     * Displays the top N populated countries in the specified region based on
     * population in descending order.
     *
     * @param topN   The number of top populated countries to display.
     * @param region The region to filter countries.
     */
    public void displayTopPopulatedCountriesInRegion(int topN, String region) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT code, name, continent, region, surfaceArea, indepYear, " +
                            "population, lifeExpectancy, gnp, gnpOld, localName, " +
                            "governmentForm, headOfState, capital " +
                            "FROM country " +
                            "WHERE region = '" + region + "' " +
                            "ORDER BY population DESC " +
                            "LIMIT " + topN;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> countries = new ArrayList<>();
            while (rset.next()) {
                // Create a new Country object
                Country country = new Country();
                country.setCode(rset.getString("code"));
                country.setName(rset.getString("name"));
                country.setContinent(rset.getString("continent"));
                country.setRegion(rset.getString("region"));
                country.setSurfaceArea(rset.getFloat("surfaceArea"));
                country.setIndepYear(rset.getInt("indepYear"));
                country.setPopulation(rset.getInt("population"));
                country.setLifeExpectancy(rset.getFloat("lifeExpectancy"));
                country.setGnp(rset.getFloat("gnp"));
                country.setGnpOld(rset.getFloat("gnpOld"));
                country.setLocalName(rset.getString("localName"));
                country.setGovernmentForm(rset.getString("governmentForm"));
                country.setHeadOfState(rset.getString("headOfState"));
                country.setCapital(rset.getInt("capital"));
                // Add the Country object to the ArrayList
                countries.add(country);
            }

            // Print the top N populated countries in the specified region
            printCountries(countries);

        } catch (Exception e) {
            // Print error messages in case of an exception
            System.out.println(e.getMessage());
            System.out.println("Failed to get top populated countries in the region");
        }
    }

    /**
     * Retrieves a list of all cities ordered by population in descending
     * order, along with their respective country names.
     *
     * @return An ArrayList of City objects representing all cities,
     *         ordered by population in descending order.
     */
    public ArrayList<City> getAllCities() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID, city.Name AS cityName, country.Name AS countryName, city.District, city.Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "ORDER BY city.Population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract city information
            ArrayList<City> cities = new ArrayList<>();
            while (rset.next()) {
                // Create a new City object
                City city = new City();
                // Set city attributes from the result set
                city.setId(rset.getInt("ID"));
                city.setName(rset.getString("cityName"));
                city.setCountryCode(rset.getString("countryName"));
                city.setDistrict(rset.getString("District"));
                city.setPopulation(rset.getInt("Population"));
                // Add the City object to the ArrayList
                cities.add(city);
            }
            return cities;
        } catch (Exception e) {
            // Print error messages in case of an exception
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    /**
     * Retrieves a list of cities based on the specified continent, along with
     * their respective country names, ordered by population in descending
     * order.
     *
     * @param continent The continent to filter cities.
     * @return An ArrayList of City objects representing cities in the specified
     *         continent, ordered by population in descending order.
     */
    public ArrayList<City> getCitiesByContinent(String continent) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID, city.Name AS cityName, country.Name AS countryName, city.District, city.Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "WHERE country.Continent = '" + continent + "' " +
                            "ORDER BY city.Population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract city information
            ArrayList<City> cities = new ArrayList<>();
            while (rset.next()) {
                // Create a new City object
                City city = new City();
                // Set city attributes from the result set
                city.setId(rset.getInt("ID"));
                city.setName(rset.getString("cityName"));
                city.setCountryCode(rset.getString("countryName"));
                city.setDistrict(rset.getString("District"));
                city.setPopulation(rset.getInt("Population"));
                // Add the City object to the ArrayList
                cities.add(city);
            }
            return cities;
        } catch (Exception e) {
            // Print error messages in case of an exception
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details by continent");
            return null;
        }
    }

}