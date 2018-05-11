package maze;

import java.util.ArrayList;

public class Node {
    private int x;
    private int y;

    boolean sourceNode = false, targetNode = false;

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

    private ArrayList<Edge> edges = new ArrayList<>();

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double directDistance(Node n) {
        return Math.sqrt(Math.pow(x - n.x, 2) + Math.pow(y - n.y, 2));
    }

    public Edge connect(Node n) {
        Edge edge = new Edge(this, n);
        edges.add(edge);        //add this edge to both nodes
        n.edges.add(edge);
        return edge;            //return reference to the new edge
    }


}
