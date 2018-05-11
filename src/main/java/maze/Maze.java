package maze;

import java.io.*;
import java.util.ArrayList;

public class Maze {
    private ArrayList<Node> nodes = new ArrayList<>();

    private Node beginNode;
    private Node targetNode;

    public ArrayList<Node> getNodesList() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public Node getBeginNode() {
        return beginNode;
    }

    public void setBeginNode(Node beginNode) {
        this.beginNode = beginNode;
    }

    public Node getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(Node targetNode) {
        this.targetNode = targetNode;
    }
}
