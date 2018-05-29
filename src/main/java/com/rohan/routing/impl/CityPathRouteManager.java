package com.rohan.routing.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

import com.google.common.collect.Sets;
import com.rohan.routing.RouteManager;

public class CityPathRouteManager implements RouteManager {
	
	private Map<String, Set<String>> cityRouteMap = new HashMap<String, Set<String>>();

	public CityPathRouteManager() {
	}
	
	public CityPathRouteManager(Map<String, Set<String>> cityRouteMap) {
		this.cityRouteMap = cityRouteMap;
	}
	
	public boolean connected(String city1, String city2) {
		return this.traverseCityGraph(city1, city2).size() > 0;
	}

	public List<String> getRoute(String city1, String city2) {		
		return traverseCityGraph(city1, city2);
	}

	public void addConnection(String city1, String city2) {
		this.addCityToMap(city1, city2);
		this.addCityToMap(city2, city1);
	}
	
	private boolean cityInMap(String city) {
		return this.cityRouteMap.get(city) != null;
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
			Set<String> adjCities = this.cityRouteMap.get(path.peek());			
			Optional<String> potentialCity = adjCities.stream()
												.filter(c -> !path.contains(c) && !visited.contains(c))
												.findFirst();
			
			if (potentialCity.isPresent()) {
				path.push(potentialCity.get());
				if (path.peek().equals(endCity)) {
					return new LinkedList<String>(path);
				}
				continue;
			} else {
				String visitedCity = path.pop();
				visited.add(visitedCity);
			}	
			
			if (visited.contains(startCity)) {
				break;
			}
			
		}
		return new LinkedList<>();
	}

}
