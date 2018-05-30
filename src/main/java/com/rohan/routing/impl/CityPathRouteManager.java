package com.rohan.routing.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

import com.google.common.collect.Sets;
import com.rohan.routing.RouteManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CityPathRouteManager implements RouteManager {

	private static final Logger LOGGER = LogManager.getLogger(CityPathRouteManager.class);
	
	private Map<String, Set<String>> cityRouteMap;

	public CityPathRouteManager() {
		this.cityRouteMap = new HashMap<>();
	}
	
	public CityPathRouteManager(Map<String, Set<String>> cityRouteMap) {
		this.cityRouteMap = cityRouteMap;
	}
	
	/**
	 * This method will return a boolean value that is representative
	 * of whether the cities are connected. It returns false if
	 * the cities are not connected or if the city is not in the map
	 * @param city1 This is the start city to look for a connection 
	 * @param city2 This is the end city to look for a connection
	 * @return boolean If the cities are connection
	 */
	public boolean connected(String city1, String city2) {
		LOGGER.info("Verifying if route exists between cities: {}, {}", city1, city2);
		if (city1.equals(city2)) {
			return true;
		}
		if (this.citiesInMap(city1, city2)) {
			return this.traverseCityGraph(city1, city2).size() > 0; 
		}
		return false; //I would probably throw an error in this case but returned false as it fit the paradigm of the interface better.
	}

	/**
	 * This method returns a List that represents a possible route
	 * from one city to another in the map. It returns an empty list
	 * if either city is not in the map or if a route cannot be found
	 * @param city1 This is the start city to look for the route 
	 * @param city2 This is the end city in the route
	 * @return List<String> Route List
	 */
	public List<String> getRoute(String city1, String city2) {
		LOGGER.info("Getting Route for cities: {}, {}", city1, city2);
		if (city1.equals(city2)) {
			return Arrays.asList(city1);
		}
		return this.citiesInMap(city1, city2) ? this.traverseCityGraph(city1, city2) : new LinkedList<>();
		// I would probably throw an error in the case where the cities are not in the map but 
		// returned false as it fit the paradigm of the interface better.
	}

	/**
	 * This method adds cities to the map
	 * @param city1 This is one of the cities that will be added
	 * @param city2 This is one of the cities that will be added
	 * @return Nothing
	 */
	public void addConnection(String city1, String city2) {
		LOGGER.debug("Adding cities to map: {}, {}", city1, city2);
		this.addCityToMap(city1, city2);
		this.addCityToMap(city2, city1);
	}
	
	/**
	 * This method checks whether a city is in the map
	 * @param city City to be checked in the map
	 * @return boolean Value if city is in the map
	 */
	private boolean cityInMap(String city) {
		return this.cityRouteMap.get(city) != null;
	}

	/**
	 * This method validates whether two cities are in
	 * the map
	 * @param city1 City to be checked in the map
	 * @param city2 City to be checked in the map
	 * @return boolean If both cities are in the map
	 */
	private boolean citiesInMap(String city1, String city2) {
		boolean citiesInMap = true;
		if (!this.cityInMap(city1)) {
			LOGGER.error("Could not find {} in map", city1);
			citiesInMap = false;
		} else if (!this.cityInMap(city2)) {
			LOGGER.error("Could not find {} in map", city2);
			citiesInMap = false;
		}
		return citiesInMap;
	}
	
	/**
	 * This method adds two cities to the map and forms a connection
	 * between both of them
	 * @param sourceCity One of the cities to be added into the map
	 * @param destCity One of the cities to be added into the map
	 * @return Nothing
	 */
	private void addCityToMap(String sourceCity, String destCity) {
		if (cityInMap(sourceCity)) {
			this.cityRouteMap.get(sourceCity).add(destCity);
		} else {
			this.cityRouteMap.put(sourceCity, Sets.newHashSet(destCity));
		}
	}
	
	/**
	 * This method uses a DFS iterative approach to search through the map
	 * and find a potential route between a start city and an end city
	 * It is not guaranteed to find the smallest route.
	 * @param startCity City to start looking at for route to destination
	 * @param destCity Destination city for finding path
	 * @return List<String>
	 */
	private List<String> traverseCityGraph(String startCity, String endCity) {
		
		Stack<String> path = new Stack<>();
		path.push(startCity);
		List<String> visited = new LinkedList<>();
		
		while (path.size() > 0) {
			LOGGER.debug("Finding adjacent cities for city: {}", path.peek());
			Set<String> adjCities = this.cityRouteMap.get(path.peek());
			LOGGER.debug("Adjacent cities: {}", adjCities);			
			Optional<String> potentialCity = adjCities.stream()
												.filter(c -> !path.contains(c) && !visited.contains(c))
												.findFirst();
			
			if (potentialCity.isPresent()) {
				String city = potentialCity.get();
				LOGGER.debug("Found city to check: {}", city);
				path.push(city);
				if (path.peek().equals(endCity)) {
					LOGGER.debug("Found route");
					return new LinkedList<String>(path);
				}
				continue;
			} else {
				String visitedCity = path.pop();
				LOGGER.debug("Explored all options for city: {}", visitedCity);
				visited.add(visitedCity);
			}	
			
			if (visited.contains(startCity)) {
				LOGGER.debug("Could not find route"); //Wasn't sure if I should log this as an error.
				break;
			}
			
		}
		return new LinkedList<>();
	}

}
