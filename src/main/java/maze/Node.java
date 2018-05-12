package maze;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private int x;
    private int y;

    private boolean sourceNode = false, targetNode = false;

    private double distFromStart = Double.MAX_VALUE;
    private double heuristicDist = Double.MAX_VALUE;

    private Node parentNode = this;

    //map of neighbors with weight
    private Map<Node, Integer> neighbors = new HashMap<>();
    
    


    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;

        if(! (obj instanceof Node)) return false;

        Node n = (Node) obj;

        return x == n.x && y == n.y;
    }

    public void connect(Node n) {
        connect(n, 1);
    }

    public void connect(Node n, int weight) {
        neighbors.put(n, weight);               //add the connection to the map
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void calcHeuristicDist(Node n) {
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

    public double getFinalDist() {
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
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }
}
