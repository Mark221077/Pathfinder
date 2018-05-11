package maze;


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

    private ArrayList<ArrayList<Node> > nodeMatrix = new ArrayList<>();

    public void setNodeMatrix(ArrayList<ArrayList<Node>> nodeMatrix) {
        this.nodeMatrix = nodeMatrix;
    }

    public void drawMaze() {
        if(nodeMatrix == null) {
            System.out.println("Node matrix was null");
        }
    }

    public String stringFormat() {
        StringBuilder builder = new StringBuilder();
        for(var list : nodeMatrix) {
            for(var node : list) {
                if(node == null)
                    builder.append('#');
                else if(node.isSourceNode())
                    builder.append('S');
                else if(node.isTargetNode())
                    builder.append('X');
                else
                    builder.append('.');
            }
            builder.append('\n');
        }

        return builder.toString();
    }
}
