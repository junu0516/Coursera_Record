/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 3
	private HashMap<GeographicPoint, MapNode> nodeMap;
	private HashSet<MapEdge> edgeList;
	private HashMap<MapNode, Double> distanceTo;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 3
		nodeMap = new HashMap<>();
		edgeList = new HashSet<>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3
		return nodeMap.values().size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
		Set<GeographicPoint> vertices = nodeMap.keySet();
		return vertices;
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		return edgeList.size();
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	//adding new node
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 3
		if(nodeMap.containsKey(location)) {
			//if nodeMap already contains the key with given location, return false
			return false;
		}else {
			//if it does not contains the key with given location, make new node and return true.
			nodeMap.put(location, new MapNode(location));
			return true;
		}		
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	//connecting nodes
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 3
		if(length<=0) {
			throw new IllegalArgumentException();
		}
		if(nodeMap.get(from) == null || nodeMap.get(to)==null) {
			throw new IllegalArgumentException();
		}
		//length is for distance which is the member variable in the class MapEdge
		//get the node "from" and connecting this to the node "to"
		nodeMap.get(from).addEdge(to,roadName,roadType,length);
		//put the edge to the edgeList
		edgeList.add(new MapEdge(from,to,roadName,roadType,length));
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		if(start == null || goal == null) {
			throw new IllegalArgumentException();
		}
		HashMap<MapNode,MapNode> map = new HashMap<>();
		
		//노드를 발견한 것과, 방문한 것들을 서로 구분지어야 함
		PriorityQueue<MapNode> nodesToBeVisited = new PriorityQueue<>();
		HashSet<MapNode> nodesVisited = new HashSet<>();
		
		MapNode current = null ;
		MapNode startNode = nodeMap.get(start);
		MapNode goalNode = nodeMap.get(goal);
		MapNode temp = null;
		if(startNode == null || goalNode == null) {
			System.out.println("No edges exits between start and goal");
			return null;
		}		
		
		//일단 큐에 방문할 노드를 넣고 시작
		nodesToBeVisited.add(startNode);
		//방문했다고 간주
		nodeSearched.accept(startNode.getLocation());
		//큐가 비어있지 않은 동안 ...(BFS) 시작
		while(!nodesToBeVisited.isEmpty()) {
			//큐에서 선입선출로 하나씩 노드 빼면서 current 업데이트
			current = nodesToBeVisited.remove();
			nodeSearched.accept(current.getLocation());
			//current와 goalNode와의 일치여부 확인
			if(current.equals(goalNode)) {
				break;
			}
			//모든 인접한 노드들 예비 탐색.. 여기서는 EdgeList 내의 모든 간선을 하나씩 탐색
			for(MapEdge edge : current.getEdgeList()) {
				//end는 간선의 끝이 가리키는 지리적 위치
				//해당 위치는 key로 가지고 있는 노드를 일단 temp 변수에 저장
				temp = nodeMap.get(edge.getEnd());
				//방문한 노드 리스트에 temp가 포함되어 있는지 확인하고, 포함되어 있지 않으면  추가
				if(!nodesVisited.contains(temp)) {
					nodesVisited.add(temp);
					nodesToBeVisited.add(temp);
					map.put(temp,current);
				}
			}
		}
		
		if(!current.equals(goalNode)) return null;
		//creating path
		LinkedList<GeographicPoint> route = new LinkedList<>();
		MapNode currentTemp = goalNode;
		while(!currentTemp.equals(startNode)) {
			route.addFirst(currentTemp.getLocation());
			currentTemp = map.get(currentTemp);
		}
		route.addFirst(startNode.getLocation());
		return route;
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
	    Consumer<GeographicPoint> temp = (x) -> {};
	    return dijkstra(start, goal, temp);
	}

	
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4

		// Hook for visualization.  See writeup.5
		//nodeSearched.accept(next.getLocation());
		PriorityQueue<MapNode> toVisit = new PriorityQueue<>();
		HashSet<MapNode> visited = new HashSet<>();
		HashMap<MapNode,MapNode> parentMap = new HashMap<>();//해당노드, 부모노드 entry
		double infinite = Integer.MAX_VALUE;
		
		for(GeographicPoint n : nodeMap.keySet()) {
			nodeMap.get(n).setLength(infinite);
			nodeMap.get(n).setPredictedLength(infinite);
		}
		
		MapNode currNode = null;
		MapNode tempNode = null;
		MapNode startNode = nodeMap.get(start);
		MapNode goalNode = nodeMap.get(goal);
		startNode.setLength(0);
		toVisit.add(startNode);
		nodeSearched.accept(startNode.getLocation());
		
		while(!toVisit.isEmpty()) {
			currNode = toVisit.poll();
			nodeSearched.accept(currNode.getLocation());
			if(!visited.contains(currNode)) {
				visited.add(currNode);
				if(currNode.equals(goalNode)) {
					//create path
					LinkedList<GeographicPoint> route = new LinkedList<>();
					MapNode currentTemp = goalNode;
					while(!currentTemp.equals(startNode)) {
						route.addFirst(currentTemp.getLocation());
						currentTemp = parentMap.get(currentTemp);
					}
					route.addFirst(startNode.getLocation());
					return route;
				}
			}
			
			for(MapEdge edge:currNode.getEdgeList()) {
				tempNode = nodeMap.get(edge.getEnd());
				if(!visited.contains(tempNode)) {
					if(tempNode.getLength()>(edge.getDistance()+currNode.getLength())) {
						tempNode.setLength(edge.getDistance()+currNode.getLength());
						parentMap.put(tempNode,currNode);
						toVisit.add(tempNode);
					}
				}
			}
		}
		
		return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		PriorityQueue<MapNode> toVisit = new PriorityQueue<>();
		HashSet<MapNode> visited = new HashSet<>();
		HashMap<MapNode,MapNode> parentMap = new HashMap<MapNode,MapNode>();
		double infinite = Integer.MAX_VALUE;
		for(GeographicPoint n : nodeMap.keySet()) {
			nodeMap.get(n).setLength(infinite);
			nodeMap.get(n).setPredictedLength(infinite);
		}
		MapNode currNode = null;
		MapNode tempNode = null;
		MapNode startNode = nodeMap.get(start);
		MapNode goalNode = nodeMap.get(goal);
		startNode.setLength(0);
		toVisit.add(startNode);
		nodeSearched.accept(startNode.getLocation());
	
		while(!toVisit.isEmpty()) {
			currNode = toVisit.poll();
			nodeSearched.accept(currNode.getLocation());
			if(!visited.contains(currNode)) {
				visited.add(currNode);
				if(currNode.equals(goalNode)) {
					LinkedList<GeographicPoint> route = new LinkedList<>();
					MapNode currentTemp = goalNode;
					while(!currentTemp.equals(startNode)) {
						route.addFirst(currentTemp.getLocation());
						currentTemp = parentMap.get(currentTemp);
					}
					route.addFirst(startNode.getLocation());
					return route;
				}
				for(MapEdge edge:currNode.getEdgeList()) {
					tempNode = nodeMap.get(edge.getEnd());
					if(!visited.contains(tempNode)) {
						double distance = Math.abs(tempNode.getLocation().distance(goal));
						if(tempNode.getLength() > (edge.getDistance()+currNode.getLength()+distance)){
							tempNode.setLength(edge.getDistance()+currNode.getLength()+distance);
							parentMap.put(tempNode,currNode);
							toVisit.add(tempNode);
						}
					}
				}
			}
		}		
		return null;
	}

	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint testEnd = new GeographicPoint(32.8660691, -117.217393);

		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		
		/* Use this code in Week 3 End of Week Quiz */
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);		
	}
	
}
