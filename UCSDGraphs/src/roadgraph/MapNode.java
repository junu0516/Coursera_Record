package roadgraph;

import java.util.*;

import geography.GeographicPoint;

public class MapNode implements Comparable<MapNode> {
	private GeographicPoint location;
	private double length;
	private double predictedLength;
	private HashSet<MapEdge> edgeList;
	
	public MapNode(GeographicPoint location) {
		this.location = location;
		edgeList = new HashSet<>();
	}
	
	public GeographicPoint getLocation() {
		return location;
	}
	
	public HashSet<MapEdge> getEdgeList(){
		return edgeList;
	}
	
	public void addEdge(MapEdge newEdge) {
		edgeList.add(newEdge);
	}
	
	//connecting this to other node
	public void addEdge(GeographicPoint endloc, String street, String roadtype, double distance) {
		MapEdge newEdge = new MapEdge(location,endloc,street,roadtype,distance);
		edgeList.add(newEdge);
	}
	
	public List<GeographicPoint> getNeighbors(){
		List<GeographicPoint> neighbors = new ArrayList<>();
		for(MapEdge e : edgeList) {
			neighbors.add(e.getEnd());
		}
		return neighbors;
	}
	
	public int compareTo(MapNode node) {
		return Double.compare(this.getLength(), node.getLength());
	}
	
	public double getLength() {
		return length;
	}
	
	public void setLength(double len) {
		this.length = len;
	}
	
	public double getpredictedLength() {
		return predictedLength;
	}
	
	public void setPredictedLength(double predictLength) {
		this.predictedLength = predictedLength;
	}
}
