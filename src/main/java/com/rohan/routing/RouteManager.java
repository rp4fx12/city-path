package com.rohan.routing;

import java.util.List;

public interface RouteManager {
	 boolean connected(String city1, String city2);

	 List<String> getRoute(String city1, String city2);

	 void addConnection(String city1, String city2);
}
