package maze;


import java.util.ArrayList;

public class Maze {
    private ArrayList<Node> nodes = new ArrayList<>();

    private Node sourceNode;
    private Node targetNode;

    private boolean solved = false;

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


    public String stringFormat() {
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

    public void solve() {
        makeUnSolved();
        MazeAstarSolver solver = new MazeAstarSolver(this);
        solution = solver.solve();      //try to solve the maze, null if cant
        if(solution != null) {
            solved = true;
            directions = solver.getDirections();
        }
        else {
            throw new InvalidMazeException("Maze cannot be solved");
        }
    }

    public String getDirections() {
        if(solved) return directions;
        else return "Maze not yet solved";
    }
}
