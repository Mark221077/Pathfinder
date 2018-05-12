package maze;

import java.util.*;

public class MazeAstarSolver {

    private Maze maze;
    private ArrayList<Node> nodeList;

    PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(Node::getFinalDist));

    ArrayList<Node> closedList= new ArrayList<>();

    ArrayList<Node> path = null;

    private Node sourceNode, targetNode;

    public MazeAstarSolver(Maze maze) {
        this.maze = maze;
        nodeList = new ArrayList<>(maze.getNodesList().size());
        sourceNode = maze.getBeginNode();
        targetNode = maze.getTargetNode();
    }

    public ArrayList<Node> solve() {
        openList.add(sourceNode);

        sourceNode.setDistFromStart(0);
        sourceNode.calcHeuristicDist(targetNode);

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            closedList.add(current);

            if(current.equals(targetNode)) {
                path = reconstructPath();
                return path;
            }

            for(var negihbor : current.getNeighbors().entrySet()) {
                Node node = negihbor.getKey();
                int weight = negihbor.getValue();
                node.calcHeuristicDist(targetNode);
                if(closedList.contains(node)) continue;     //this node was already evaluated

                if(!openList.contains(node)) openList.add(node);        //found a new node

                double score = current.getDistFromStart() + weight;

                if(score < node.getDistFromStart()) {
                    node.setParentNode(current);
                    node.setDistFromStart(score);
                }
            }
        }
        path = null;
        return null;

    }

    private ArrayList<Node> reconstructPath() {
        var nodeStack = new Stack<Node>();
        var currNode = targetNode;
        nodeStack.add(currNode);
        do  {
            currNode = currNode.getParentNode();
            nodeStack.add(currNode);
        } while (currNode != sourceNode);

        var list = new ArrayList<Node>(nodeStack.size());

        while(nodeStack.size() > 0)
            list.add(nodeStack.pop());
        return list;
    }


    public String showSolution() {
        String str = maze.stringFormat();
        String[] rows = str.split("\n");

        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < rows.length; ++i) {
            StringBuilder row = new StringBuilder(rows[i]);
            for(var node : path) {
                if(node.getY() == i && !node.isSourceNode() && !node.isTargetNode()) {
                    row.setCharAt(node.getX() , '*');
                }
            }
            builder.append(row).append("\n");
        }

        return builder.toString();
    }

}