package com.rohan.routing;

import org.junit.Test;

import com.rohan.routing.impl.CityPathRouteManager;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;

public class TestCityPathRouteManager {
	
	private RouteManager cityGraph;
	private static final String NON_CITY_1 = "London";
	private static final String NON_CITY_2 = "Honolulu";
	
	@Before
	public void setup() {
		cityGraph = new CityPathRouteManager();
		cityGraph.addConnection("Washington", "Philadelphia");
		cityGraph.addConnection("Philadelphia", "Albany");
		cityGraph.addConnection("Philadelphia", "Chicago");
		cityGraph.addConnection("Chicago", "Detroit");
		cityGraph.addConnection("Newark", "New York");
		cityGraph.addConnection("Philadelphia", "Newark");
		cityGraph.addConnection("New York", "Albany");
		cityGraph.addConnection("Tokyo", "Hong Kong");
	}
	
	@Test
	public void testCityPath() {
		List<String> expectedHappyPath = Arrays.asList("Washington", "Philadelphia", "Newark", "New York");
		assertEquals("Washington to NYC Path", expectedHappyPath, cityGraph.getRoute("Washington", "New York"));
		List<String> expectedMissingPath = new LinkedList<String>();
		assertEquals("Washington to Hong Kong - Empty List Path", expectedMissingPath, cityGraph.getRoute("Washington", "Hong Kong"));
		
	}

	@Test
	public void testEmptyMap() {
		RouteManager cityGraphEmpty = new CityPathRouteManager();
		assertFalse("No entries in map should result in false route found", cityGraphEmpty.connected("Washington", "Philidelphia"));
		List<String> expectedMissingPath = new LinkedList<String>();
		assertEquals("No entries in map should result in empty list", expectedMissingPath, cityGraphEmpty.getRoute("Washington", "Hong Kong"));
		
	}

	@Test
	public void testMissingCity1() {
		assertFalse("Missing city1 in map should result in false route found", cityGraph.connected(NON_CITY_1, "Philidelphia"));
		List<String> expectedMissingPath = new LinkedList<String>();
		assertEquals("Missing city1 in map should result in empty list", expectedMissingPath, cityGraph.getRoute(NON_CITY_1, "Hong Kong"));
	}

	@Test
	public void testMissingCity2() {
		assertFalse("Missing city1 in map should result in false route found", cityGraph.connected("Tokyo", NON_CITY_1));
		List<String> expectedMissingPath = new LinkedList<String>();
		assertEquals("Missing city1 in map should result in empty list", expectedMissingPath, cityGraph.getRoute("Tokyo", NON_CITY_1));
	}

	@Test
	public void testMissingBothCities() {
		assertFalse("Missing cities in map should result in false route found", cityGraph.connected(NON_CITY_1, NON_CITY_2));
		List<String> expectedMissingPath = new LinkedList<String>();
		assertEquals("Missing cities in map should result in empty list", expectedMissingPath, cityGraph.getRoute(NON_CITY_1, NON_CITY_2));
	}

	@Test
	public void testSameCity() {
		assertTrue("Same city is connected", cityGraph.connected("Washington", "Washington"));
		List<String> expectedList = Arrays.asList("Washington");
		assertEquals("Same city should result in list with single element", expectedList, cityGraph.getRoute("Washington", "Washington"));
	}

	@Test
	public void testCityConnectivity() {
		assertTrue("Washington is connected to New York", cityGraph.connected("Washington", "New York"));
		assertFalse("Washington is not connected to Hong Kong", cityGraph.connected("Washington", "Hong Kong"));
	}
}