package com.example.route_finding_algorithm;

import com.example.route_finding_algorithm.Algorithms;
import com.example.route_finding_algorithm.Station;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
	private Map<Integer, Station> stations;
	private List<Edge> edges;
	private Map<Integer, List<Station>> stationsByLine;

	public Graph() {
		stations = new HashMap<>();
		edges = new ArrayList<>();
		stationsByLine = new HashMap<>();
	}

	public void addStation(Station station) {
		stations.put(station.getId(), station);
	}

	public void addEdge(Edge edge) {
		edges.add(edge);
	}

	public void readConnectionsFromCSV(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			boolean firstLine = true;
			while ((line = br.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
					continue; // Skip the header line
				}

				String[] data = line.split(",");
				int station1Id = Integer.parseInt(data[0]);
				int station2Id = Integer.parseInt(data[1]);

				Station station1 = stations.get(station1Id);
				Station station2 = stations.get(station2Id);

				if (station1 != null && station2 != null) {
					// Calculate distance and create an edge
					double distanceBetweenStations = calculateDistance(station1, station2);
					Edge edge = new Edge(station1, station2, distanceBetweenStations);
					addEdge(edge);
				} else {
					System.out.println("Invalid station IDs: " + station1Id + ", " + station2Id);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// After reading all stations, create connections between stations on the same line
	private void connectStationsOnSameLine() {
		for (List<Station> stationsOnLine : stationsByLine.values()) {
			for (Station station1 : stationsOnLine) {
				for (Station station2 : stationsOnLine) {
					if (station1 != station2) {
						// Calculate distance and create an edge
						double distanceBetweenStations = calculateDistance(station1, station2);

						Edge edge = new Edge(station1, station2, distanceBetweenStations);
						addEdge(edge);
					}
				}
			}
		}
	}

	public void readFromCSV(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String lineBreak;
			boolean firstLine = true;
			while ((lineBreak = br.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
					continue; // Skip the header line
				}

				String[] data = lineBreak.split(",");
				int id = Integer.parseInt(data[0]);
				double latitude = Double.parseDouble(data[1]);
				double longitude = Double.parseDouble(data[2]);
				String name = data[3];
				String displayName = data[4];
				Double zone = Double.parseDouble(data[5]);
				int totalLines = Integer.parseInt(data[6]);
				int rail = Integer.parseInt(data[7]);

				Station station = new Station(id, latitude, longitude, name, displayName, zone, totalLines, rail);
				addStation(station);

				// Group stations by line
				for (int i = 8; i < data.length; i++) {
					int lineId = Integer.parseInt(data[i]);
					stationsByLine.computeIfAbsent(lineId, k -> new ArrayList<>()).add(station);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Calculate the distance between two stations
	private double calculateDistance(Station station1, Station station2) {
		// Radius of the Earth in kilometers
		double earthRadiusKm = 6371.0;

		double lat1 = Math.toRadians(station1.getLatitude());
		double lon1 = Math.toRadians(station1.getLongitude());
		double lat2 = Math.toRadians(station2.getLatitude());
		double lon2 = Math.toRadians(station2.getLongitude());

		// Haversine formula
		double dLat = lat2 - lat1;
		double dLon = lon2 - lon1;

		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLon / 2), 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		// Calculate the distance
		double distance = earthRadiusKm * c;

		return distance;
	}
	public void printGraph() {
		for (Station station : stations.values()) {
			System.out.println(station);
		}
		for (Edge edge : edges) {
			System.out.println(edge);
		}
	}

	public void printConnectedNodes(Station station) {
		List<Station> connectedStations = new ArrayList<>();

		// Iterate through the edges to find connected stations
		for (Edge edge : edges) {
			if (edge.getSource() == station) {
				connectedStations.add(edge.getDestination());
			} else if (edge.getDestination() == station) {
				connectedStations.add(edge.getSource());
			}
		}

		// Print station name and its connected stations
		System.out.println("Connected stations for " + station.getName() + ":");
		for (Station connectedStation : connectedStations) {
			System.out.println("- " + connectedStation.getName());
		}

		// Debug: Print out the station's ID and name
		System.out.println("Station ID: " + station.getId() + ", Station Name: " + station.getName());
	}

	public ObservableList<Station> findShortestRoute(Station start, Station end) {
		Algorithms algorithms = new Algorithms();
		List<Station> result = algorithms.executeShortestRouteSearch(this, start, end, stations.values(), edges);
		for (Station station : result) {
			System.out.println(station.getName());
		}
		// Convert the result list to an ObservableList
		return FXCollections.observableArrayList(result);
	}

	public static void generateGraph() {
		Graph graph = new Graph();
		graph.readFromCSV("src/main/java/com/example/route_finding_algorithm/StationDataFile");
		graph.readConnectionsFromCSV("src/main/java/com/example/route_finding_algorithm/LineDataFile"); // Call readConnectionsFromCSV
		graph.connectStationsOnSameLine(); // Add this line to connect stations on the same line
		graph.printGraph();
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public Map<Integer, Station> getStations() {
		return stations;
	}

	public List<Edge> getEdgesFromStation(Station station) {
		List<Edge> edgesFromStation = new ArrayList<>();
		for (Edge edge : edges) {
			if (edge.getSource() == station || edge.getDestination() == station) {
				edgesFromStation.add(edge);
			}
		}
		return edgesFromStation;
	}

	public Map<Integer, List<Station>> getStationsByLine() {
		return stationsByLine;
	}

	public class Edge {
		private Station source;
		private Station destination;
		private double weight;

		public Edge(Station source, Station destination, double weight) {
			this.source = source;
			this.destination = destination;
			this.weight = weight;
		}

		public Station getSource() {
			return source;
		}

		public void setSource(Station source) {
			this.source = source;
		}

		public Station getDestination() {
			return destination;
		}

		public void setDestination(Station destination) {
			this.destination = destination;
		}

		public double getWeight() {
			return weight;
		}

		public void setWeight(double weight) {
			this.weight = weight;
		}

		@Override
		public String toString() {
			return "Edge [source=" + source + ", destination=" + destination + ", weight=" + weight + "]";
		}
	}
}
