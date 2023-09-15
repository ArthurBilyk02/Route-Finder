package com.example.route_finding_algorithm;

import java.util.*;

public class Algorithms {


	public void executeDFSRouteSearch(Graph graph, Station start, Station end) {
	}

	public List<Station> executeShortestRouteSearch(Graph graph, Station start, Station end, Collection<Station> allStations, List<Graph.Edge> allEdges) {
		// Create data structures for Dijkstra's algorithm
		Map<Station, Double> distances = new HashMap<>();
		Map<Station, Station> previousStations = new HashMap<>();
		PriorityQueue<Station> priorityQueue = new PriorityQueue<>(
				Comparator.comparingDouble(distances::get)
		);

		// Initialize distances and priority queue
		for (Station station : allStations) {
			distances.put(station, Double.MAX_VALUE);
			previousStations.put(station, null);
		}
		distances.put(start, 0.0);
		priorityQueue.add(start);

		// Dijkstra's algorithm
		while (!priorityQueue.isEmpty()) {
			Station current = priorityQueue.poll();

			for (Graph.Edge edge : allEdges) {
				if (edge.getSource().equals(current)) {
					Station neighbor = edge.getDestination();
					double distanceThroughCurrent = distances.get(current) + edge.getWeight();

					if (distanceThroughCurrent < distances.get(neighbor)) {
						distances.put(neighbor, distanceThroughCurrent);
						previousStations.put(neighbor, current);
						priorityQueue.add(neighbor);
					}
				}
			}
		}

		// Reconstruct the path
		List<Station> shortestPath = new ArrayList<>();
		Station current = end;
		while (current != null) {
			shortestPath.add(current);
			current = previousStations.get(current);
		}
		Collections.reverse(shortestPath);

		return shortestPath;
	}


	public List<Graph.Edge> getEdgesFromStation(Station station, List<Graph.Edge> edges) {
		List<Graph.Edge> edgesFromStation = new ArrayList<>();
		for (Graph.Edge edge : edges) {
			if (edge.getSource().equals(station)) {
				edgesFromStation.add(edge);
			}
		}
		return edgesFromStation;
	}

	public List<Station> executeFewestStationSearch(Graph graph, Station start, Station end) {
		// Implement an algorithm to find the route with the fewest stations
		Queue<Station> queue = new LinkedList<>();
		Map<Station, Station> parentMap = new HashMap<>();
		Set<Station> visited = new HashSet<>();

		queue.offer(start);
		visited.add(start);

		while (!queue.isEmpty()) {
			Station current = queue.poll();

			if (current.equals(end)) {
				// Found the destination, reconstruct the route
				List<Station> route = new ArrayList<>();
				Station station = end;

				while (station != null) {
					route.add(station);
					station = parentMap.get(station);
				}

				Collections.reverse(route);
				return route;
			}

			List<Graph.Edge> edgesFromCurrent = getEdgesFromStation(current, graph.getEdges());
			for (Graph.Edge edge : edgesFromCurrent) {
				Station neighbor = edge.getDestination();
				if (!visited.contains(neighbor)) {
					queue.offer(neighbor);
					visited.add(neighbor);
					parentMap.put(neighbor, current);
				}
			}
		}

		// No route found
		return Collections.emptyList();
	}


	public List<Station> executeShortestRouteCostSearch(Graph graph, Station start, Station end) {
		List<Station> shortestCostRoute = findShortestRouteCost(graph, start, end);
		return shortestCostRoute;
	}

	public List<Station> findShortestRouteCost(Graph graph, Station start, Station end) {
		// Implement an algorithm to find the shortest route based on cost

		// Create data structures for Dijkstra's algorithm
		Map<Station, Double> distances = new HashMap<>();
		Map<Station, Station> previousStations = new HashMap<>();
		PriorityQueue<Station> priorityQueue = new PriorityQueue<>(
				Comparator.comparingDouble(distances::get)
		);

		// Initialize distances and priority queue
		for (Station station : graph.getStations().values()) {
			distances.put(station, Double.MAX_VALUE);
			previousStations.put(station, null);
		}
		distances.put(start, 0.0);
		priorityQueue.add(start);

		// Dijkstra's algorithm
		while (!priorityQueue.isEmpty()) {
			Station current = priorityQueue.poll();

			for (Graph.Edge edge : graph.getEdgesFromStation(current)) {
				Station neighbor = edge.getDestination();
				double distanceThroughCurrent = distances.get(current) + edge.getWeight();

				if (distanceThroughCurrent < distances.get(neighbor)) {
					distances.put(neighbor, distanceThroughCurrent);
					previousStations.put(neighbor, current);
					priorityQueue.add(neighbor);
				}
			}
		}

		// Reconstruct the path
		List<Station> shortestCostRoute = new ArrayList<>();
		Station current = end;
		while (current != null) {
			shortestCostRoute.add(current);
			current = previousStations.get(current);
		}
		Collections.reverse(shortestCostRoute);

		return shortestCostRoute;
	}

}
