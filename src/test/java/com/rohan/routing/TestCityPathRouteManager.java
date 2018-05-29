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
	public void testCityConnectivity() {
		assertTrue("Washington is connected to New York", cityGraph.connected("Washington", "New York"));
		assertFalse("Washington is not connected to Hong Kong", cityGraph.connected("Washington", "Hong Kong"));
	}

}