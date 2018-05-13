package maze;


import java.util.ArrayList;

public class Maze {
    private ArrayList<Node> nodes = new ArrayList<>();

    private Node sourceNode;
    private Node targetNode;

    private boolean solved = false;

    private boolean unSolvable = false;

    public boolean isSolved() {
        return solved;
    }

    private ArrayList<Node> solution = null;
    private String directions = null;

    private void makeUnSolved(){
        solution = null;
        directions = null;
        solved = false;
    }

    private ArrayList<ArrayList<Node> > nodeMatrix = null;

    public ArrayList<Node> getNodesList() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public Node getSourceNode() {
        return sourceNode;
    }

    public void setSourceNode(Node sourceNode) {
        makeUnSolved();    //source node changed, last solution is not valid
        this.sourceNode = sourceNode;
    }

    public Node getTargetNode()   {
        return targetNode;
    }

    public void setTargetNode(Node targetNode) {
        makeUnSolved();    //target node changed, last solution is not valid
        this.targetNode = targetNode;
    }

    public void setNodeMatrix(ArrayList<ArrayList<Node>> nodeMatrix) {
        makeUnSolved();    //node matrix changed, last solution is not valid
        this.nodeMatrix = nodeMatrix;
    }


    public String stringFormat() {      //returns this maze in the same format as the input
        if(nodeMatrix == null)
            return "";              //the node matrix was not set, cant be printed

        StringBuilder builder = new StringBuilder();
        for(var list : nodeMatrix) {
            for(var node : list) {
                if(node == null)
                    builder.append('#');            //nulls represent blocked nodes in the maze
                else if(node.isSourceNode())
                    builder.append('S');            //the source node
                else if(node.isTargetNode())
                    builder.append('X');            //the target node
                else
                    builder.append('.');            //if none of the above, then free space
            }
            builder.append('\n');
        }

        return builder.toString();
    }

    public void solve() {               //solves this maze using A* algorithm
        makeUnSolved();
        MazeAstarSolver solver = new MazeAstarSolver(sourceNode, targetNode);
        solution = solver.solve();      //try to solve the maze, null if cant
        if(solution != null) {          //solution has the nodes of the path
            solved = true;              //the maze is solved
            directions = solver.getDirections();        //the path
        }
        else {                          //the maze is unsolvable
            solved = true;              //it is "solved", but has no solution
            unSolvable = true;          //unsolvable maze
        }
    }

    public String getDirections() {
        if(solved) {
            if (unSolvable) return "Unsolvable";    //unsolvable maze
            else return directions;             //string containing the directions
        }
        else return "";                     //not yet solved
    }

    public String getSolutionMatrix() {     // returns the string representation of this maze with the solution

        if(!solved) return "";              //maze not yet solved

        if(unSolvable) return "Unsolvable"; //unsolvable maze

        String str = stringFormat();                    //the string maze w/o the solution
        String[] rows = str.split("\n");            //split the string into rows

        StringBuilder builder = new StringBuilder();        //builder for the combined solution

        for(int i = 0; i < rows.length; ++i) {
            StringBuilder row = new StringBuilder(rows[i]);     //builder for the row, easier to replace characters
            for(var node : solution) {
                if(node.getY() == i && !node.isSourceNode() && !node.isTargetNode()) {
                    row.setCharAt(node.getX() , '*');           //if this node is path, add to solution
                }
            }
            builder.append(row).append("\n");
        }

        return builder.toString();
    }
}
