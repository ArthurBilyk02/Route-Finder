package com.example.route_finding_algorithm;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Data {
		public static List<Station> readCSV(String filePath) throws IOException {
			List<Station> stations = new ArrayList<>();

			try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
				String line;
				// Read the header line and skip it
				br.readLine();

				while ((line = br.readLine()) != null) {
					String[] parts = line.split(",");
					if (parts.length == 8) {
						int id = Integer.parseInt(parts[0]);
						double latitude = Double.parseDouble(parts[1]);
						double longitude = Double.parseDouble(parts[2]);
						String name = parts[3].replaceAll("\"", "");
						String displayName = parts[4].replaceAll("\"", "");
						Double zone = Double.parseDouble(parts[5]);
						int totalLines = Integer.parseInt(parts[6]);
						int rail = Integer.parseInt(parts[7]);
						Station station = new Station(id, latitude, longitude, name, displayName, zone, totalLines, rail);
						stations.add(station);
					}
				}
			}

			return stations;
		}
	}
