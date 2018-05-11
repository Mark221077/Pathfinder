package maze;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class MazeBuilder {


    public Maze build(File file) {
        var input = new ArrayList<String>();
        try (BufferedReader r = new BufferedReader(new FileReader(file))){
            String line;
            while((line = r.readLine()) != null) {
                if(line.length() > 0)
                    input.add(line);
            }
        } catch (IOException ex) {
            System.out.println("Couldn't read the file");
        }

        return build(input);
    }


    public Maze build(ArrayList<String> input) {
        var maze = new Maze();
        if (input == null || input.size() == 0)
            return null;


        //check if the input has a rectangular shape, all rows are the same
        var width = input.get(0).length();
        for (var row : input) {
            if (width != row.length()) return maze;      //one row doesnt have the same length as the first one
        }

        //convert the array list to 2D Node array/matrix,
        var matrix = new ArrayList<ArrayList<Node>>(input.size());

        //initialize the row lists
        for (int i = 0; i < input.size(); ++i)
            matrix.add(new ArrayList<>(width));


        int beginNodeCount = 0, targetNodeCount = 0;

        Node sourceNode = null, targetNode = null;

        //TODO: optimize this by converting to one iteration

        //fill the matrix
        for (int row = 0; row < input.size(); ++row) {
            var str = input.get(row);                     //a line with a row
            for (int col = 0; col < str.length(); ++col) {   //iterate through the row
                Node n = new Node(row, col);                //a new node, with the coordinates
                char c = str.charAt(col);                   //the char in the current column
                if (c == 'S') {
                    n.setSourceNode(true);         //source
                    ++beginNodeCount;               //test for multiple source nodes
                    sourceNode = n;
                } else if (c == 'X') {
                    n.setTargetNode(true);    //target
                    ++targetNodeCount;              //test for multiple target nodes
                    targetNode = n;
                }

                if (c != '#')                            //not blocked
                    matrix.get(row).add(col, n);
                else matrix.get(row).add(col, null);           //if blocked, it means no node is there

            }
        }

        if (beginNodeCount > 1 || targetNodeCount > 1)           //more than one source/target node
            return null;                        //return null for error

        //now we iterate through the matrix, connecting all the not-null nodes


        for (int row = 0; row < matrix.size() - 1; ++row) {
            var nodes = matrix.get(row);

            for (int col = 0; col < nodes.size() - 1; ++col) {
                var currNode = nodes.get(col);
                var rightNode = nodes.get(col + 1);
                var belowNode = matrix.get(row + 1).get(col);

                if (currNode != null) {
                    if (rightNode != null)
                        currNode.connect(rightNode);
                    if (belowNode != null)
                        currNode.connect(belowNode);
                }
            }
        }

        //all the nodes are connected in the matrix
        //so now we can store them in a simple list
        //the connections remain the same
        //we add them to the maze
        //the null Nodes are not included
        for (var list : matrix) {
            for(var node : list) {
                if(node != null)
                    maze.getNodesList().add(node);
            }
        }

        //set the source and target nodes
        maze.setBeginNode(sourceNode);
        maze.setTargetNode(targetNode);

        //everything is done, return the maze

        maze.setNodeMatrix(matrix);

        return maze;
    }
}
