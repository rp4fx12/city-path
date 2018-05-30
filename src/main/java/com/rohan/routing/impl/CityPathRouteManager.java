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
	
	public boolean connected(String city1, String city2) {
		LOGGER.info("Verifying if route exists between cities: {}, {}", city1, city2);
		if (city1.equals(city2)) {
			return true;
		}
		if (this.citiesInMap(city1, city2)) {
			return this.traverseCityGraph(city1, city2).size() > 0; 
		}
		return false;
	}

	public List<String> getRoute(String city1, String city2) {
		LOGGER.info("Getting Route for cities: {}, {}", city1, city2);
		if (city1.equals(city2)) {
			return Arrays.asList(city1);
		}
		return this.citiesInMap(city1, city2) ? this.traverseCityGraph(city1, city2) : new LinkedList<>();		
	}

	public void addConnection(String city1, String city2) {
		LOGGER.debug("Adding cities to map: {}, {}", city1, city2);
		this.addCityToMap(city1, city2);
		this.addCityToMap(city2, city1);
	}
	
	private boolean cityInMap(String city) {
		return this.cityRouteMap.get(city) != null;
	}

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
	
	private void addCityToMap(String sourceCity, String destCity) {
		if (cityInMap(sourceCity)) {
			this.cityRouteMap.get(sourceCity).add(destCity);
		} else {
			this.cityRouteMap.put(sourceCity, Sets.newHashSet(destCity));
		}
	}
	
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
