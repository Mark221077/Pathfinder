package maze;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private int x;
    private int y;

    private boolean sourceNode = false, targetNode = false;

    private double distFromStart = Double.MAX_VALUE;        //the distance from the start node, default infinite
    private double heuristicDist = Double.MAX_VALUE;        //the heuristic distance from the target node, default infinite
    private Node parentNode = null;                     //the parent node on the path, if no path, no parent node

    //map of neighbors with weight
    //could be a simple list in this case
    //left as a map for possible improvement in the future
    private Map<Node, Integer> neighbors = new HashMap<>();


    public Node(int x, int y) {     //every node must have coordinates set
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {         //a node is equal to another node, if its in the same place
        if(obj == null) return false;

        if(! (obj instanceof Node)) return false;

        Node n = (Node) obj;
        return x == n.x && y == n.y;
    }

    public void connect(Node n) {               //the default weight of a connection/edge is 1
        connect(n, 1);
    }       //the default weight is 1

    public void connect(Node n, int weight) {
        neighbors.put(n, weight);               //add the connection to this map
        n.neighbors.put(this, weight);          //connection is present both ways
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void calcHeuristicDist(Node n) {         //diagonal distance of the two points, the closest possible distance IRL
        heuristicDist = Math.sqrt(Math.pow(x - n.x, 2) + Math.pow(y - n.y, 2));
    }

    public double getDistFromStart() {
        return distFromStart;
    }

    public void setDistFromStart(double distFromStart) {
        this.distFromStart = distFromStart;
    }

    public double getHeuristicDist() {
        return heuristicDist;
    }

    public double getFinalDist() {      //the nodes are evaluated based on this distance, roughly the distance from the source + from the target
        return distFromStart + heuristicDist;
    }

    public boolean isSourceNode() {
        return sourceNode;
    }

    public void setSourceNode(boolean sourceNode) {
        this.sourceNode = sourceNode;
    }

    public boolean isTargetNode() {
        return targetNode;
    }

    public void setTargetNode(boolean targetNode) {
        this.targetNode = targetNode;
    }

    public Map<Node, Integer> getNeighbors() {
        return neighbors;
    }

    public Node getParentNode() {
        //the neighbor node through which the source and targets are connected
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }
}
