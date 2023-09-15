package com.example.route_finding_algorithm;

public class Station {
	private int id;
	private double latitude;
	private double longitude;
	private String name;
	private String displayName;
	private double zone;
	private int totalLines;
	private int rail;

	public Station(int id, double latitude, double longitude, String name, String displayName, Double zone, int totalLines, int rail) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
		this.displayName = displayName;
		this.zone = zone;
		this.totalLines = totalLines;
		this.rail = rail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public double getZone() {
		return zone;
	}

	public void setZone(int zone) {
		this.zone = zone;
	}

	public int getTotalLines() {
		return totalLines;
	}

	public void setTotalLines(int totalLines) {
		this.totalLines = totalLines;
	}

	public int getRail() {
		return rail;
	}

	public void setRail(int rail) {
		this.rail = rail;
	}
}
