package com.example.route_finding_algorithm;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Controller {
	@FXML
	private TextField startStationTextField;
	@FXML
	private TextField destinationStationTextField;
	@FXML
	private ComboBox<String> algorithmComboBox;
	@FXML
	private CheckBox avoidStationsCheckBox;
	@FXML
	private CheckBox waypointsCheckBox;
	@FXML
	private Button findRoutesButton;
	@FXML
	private TreeView<String> routesTreeView;
	@FXML
	private AnchorPane mapPane;
	@FXML
	private Text startingStationText;
	@FXML
	private Text destinationStationText;

	@FXML
	private TableView<Station> startStationTableView;
	@FXML
	private TableView<Station> destinationStationTabelView;

	@FXML
	private TableView<Station> routeTable;

	@FXML
	private RadioButton dfsRouteRadio;
	@FXML
	private RadioButton shortestRouteRadio;
	@FXML
	private RadioButton fewestStationRadio;
	@FXML
	private RadioButton shortestRouteCostRadio;

	private Station startStation;
	private Station destination;
	private Graph graph = new Graph();

	@FXML
	private void initialize(){
		readFromCSV("src/main/java/com/example/route_finding_algorithm/StationDataFile");

		Graph.generateGraph();
		startStationTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Station>() {
			public void changed(ObservableValue<? extends Station> observable, Station oldValue, Station newValue) {
						startingStationText.setText(newValue.getName());
						startStation = newValue;
			}
		});

		destinationStationTabelView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Station>() {
			public void changed(ObservableValue<? extends Station> observable, Station oldValue, Station newValue) {
				destinationStationText.setText(newValue.getName());
				destination = newValue;
			}
		});
	}

	ObservableList<Station> stationList = FXCollections.observableArrayList();
	Algorithms algorithm = new Algorithms();



	@FXML
	public void calculateRoute(){
		if (dfsRouteRadio.isSelected()) {
			// Execute the DFS route search algorithm
			algorithm.executeDFSRouteSearch(graph, startStation, destination);
			graph.printConnectedNodes(startStation);
			System.out.println("Executing DFS");
		} else if (shortestRouteRadio.isSelected()) {
			// Execute the shortest route search algorithm
			routeTable.setItems(graph.findShortestRoute(startStation, destination));
			System.out.println("Executing Shortest Route");
		} else if (fewestStationRadio.isSelected()) {
			// Execute the fewest station search algorithm
			algorithm.executeFewestStationSearch(graph, startStation, destination);
			System.out.println("Executing Fewest Stations");
		} else if (shortestRouteCostRadio.isSelected()) {
			// Execute the shortest route cost search algorithm
			algorithm.executeShortestRouteCostSearch(graph, startStation, destination);
			System.out.println("Executing Fewest Stations with cost");
		}
	}
	public void readFromCSV(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			boolean firstLine = true;
			while ((line = br.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
					continue; // Skip the header line
				}

				String[] data = line.split(",");
				int id = Integer.parseInt(data[0]);
				double latitude = Double.parseDouble(data[1]);
				double longitude = Double.parseDouble(data[2]);
				String name = data[3];
				String displayName = data[4];
				Double zone = Double.parseDouble(data[5]);
				int totalLines = Integer.parseInt(data[6]);
				int rail = Integer.parseInt(data[7]);

				Station station = new Station(id, latitude, longitude, name, displayName, zone, totalLines, rail);
				stationList.add(station); // Add station to the list
			}

			// Populate the TableView with the stationList
			startStationTableView.setItems(stationList);
			destinationStationTabelView.setItems(stationList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}