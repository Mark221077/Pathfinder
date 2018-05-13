package maze;

import java.util.*;

public class MazeAstarSolver {

    //contains the newly found and not yet visited nodes
    //Priority Queue is ordered by the final distance of the node
    //the distance from the start node plus the heuristic distance to the target node
    private PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(Node::getFinalDist));

    //contains the visited nodes
    private ArrayList<Node> closedList= new ArrayList<>();

    //the path to the solution
    private ArrayList<Node> path = null;

    private Node sourceNode, targetNode;

    public MazeAstarSolver(Node sourceNode, Node targetNode) {
        if(sourceNode == null || targetNode == null)
            throw new IllegalArgumentException("Argument was null");
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
    }

    public ArrayList<Node> solve() {
        openList.add(sourceNode);       //add the source to the openList
                                        //we know this node exists

        sourceNode.setDistFromStart(0);         //the distance from itself is 0
        sourceNode.calcHeuristicDist(targetNode);       //the heuristic distance from the target node

        while (!openList.isEmpty()) {           //we have nodes to check
            Node current = openList.poll();     //get the node with the best finalDistance
            closedList.add(current);            //mark this node as evaluated

            if(current.equals(targetNode)) {        //if this is the target, finish
                path = reconstructPath();           //path will contain the nodes of the path
                return path;
            }

            for(var neighbor : current.getNeighbors().entrySet()) {     //get the entries of the neighbor map
                Node node = neighbor.getKey();              //the key is the node
                int weight = neighbor.getValue();           //the weight is the cost of getting from the current node to the neighbor
                if(closedList.contains(node)) continue;     //this node was already evaluated
                node.calcHeuristicDist(targetNode);         //calculate the heuristic distance to the target node for the neighbor

                if(!openList.contains(node)) openList.add(node);        //found a new node

                double score = current.getDistFromStart() + weight;     //the new length of the path to this node through current

                if(score < node.getDistFromStart()) {               //if better than its current length from start
                    node.setParentNode(current);                    //make its parent node the current node
                    node.setDistFromStart(score);                   //set the distance from start
                }
            }
        }
        path = null;        //maze could not be solved, couldn't get to target node
        return null;        //indicating error

    }

    private ArrayList<Node> reconstructPath() {
        var list = new ArrayList<Node>();
        var currNode = targetNode;              //we start from the target node
        list.add(currNode);                     //add this node
        do  {
            currNode = currNode.getParentNode();        //get its parent node
            list.add(currNode);
        } while (currNode != sourceNode && currNode.getParentNode() != null);       //while not at source node, or not on null(error)
        Collections.reverse(list);              //path should go from source to target
        return list;
    }




    public String getDirections() {
        StringBuilder builder = new StringBuilder(path.size());
        for(var node : path) {
            if(node == sourceNode) continue;                //skip the source node, we are already there
            if(node.getParentNode().getY() < node.getY())
                builder.append('d');                                //parent node is above this node
            else if(node.getParentNode().getY() > node.getY())
                builder.append('u');                                //parent node is below this
            else if(node.getParentNode().getX() < node.getX())
                builder.append('r');                                //parent node is on the left side of this
            else if(node.getParentNode().getX() > node.getX())
                builder.append('l');                                //parent node is on the right side of this
        }

        return builder.toString();
    }

}