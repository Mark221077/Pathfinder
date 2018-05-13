package maze;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class MazeBuilder {


    public Maze build(File file) {
        var input = new ArrayList<String>();        //will contain the rows as string
        try (BufferedReader r = new BufferedReader(new FileReader(file))){
            String line;
            while((line = r.readLine()) != null) {  //is null when EOF
                line = line.trim();                 //remove whitespaces
                if(line.length() > 0)               //line is not empty
                    input.add(line);
            }
            return build(input);
        } catch (IOException ex) {
            throw new InvalidMazeException("Invalid input file");
        }

    }


    public Maze build(ArrayList<String> input) {
        var maze = new Maze();
        if (input.size() == 0)
            throw new InvalidMazeException("Invalid maze");

        //check if the input has a rectangular shape, all rows are the same
        var width = input.get(0).length();
        for (var row : input) {
            if (width != row.length())
                throw new InvalidMazeException("Maze is not rectangular");             //one row doesnt have the same length as the first one
        }

        //convert the array list to 2D Node array/matrix,
        var matrix = new ArrayList<ArrayList<Node>>(input.size());
        //initialize the row lists
        for (int i = 0; i < input.size(); ++i)
            matrix.add(new ArrayList<>(width));

        //count the source and target nodes for error checking
        int beginNodeCount = 0, targetNodeCount = 0;

        //search for these nodes during iteration
        Node sourceNode = null, targetNode = null;

        //fill the matrix
        for (int row = 0; row < input.size(); ++row) {
            var str = input.get(row);                     //a line with a row
            for (int col = 0; col < str.length(); ++col) {   //iterate through the row
                Node n = new Node(col, row);                //a new node, with the coordinates
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

        if (beginNodeCount != 1 || targetNodeCount != 1)           //more than one source/target node
            throw new InvalidMazeException("Zero or more than 1 source/target nodes");

        //now we iterate through the matrix, connecting all the not-null nodes

        for (int row = 0; row < matrix.size(); ++row) {
            var nodes = matrix.get(row);                        //get a row

            for (int col = 0; col < nodes.size(); ++col) {
                var currNode = nodes.get(col);
                if(currNode == null) continue;                      //if this is null, do nothing
                if(col + 1 < nodes.size() && nodes.get(col + 1) != null)        //if the node is not in the last column
                    currNode.connect(nodes.get(col + 1));                   //and the node next to it is not null connect

                if(row + 1 < matrix.size() && matrix.get(row + 1).get(col) != null)     //if not the last row
                    currNode.connect(matrix.get(row + 1).get(col));         //if there is a node below, connect it

            }


        }

        //all the nodes are connected in the matrix
        //so now we can store them in a simple list
        //the connections remain the same
        //we add them to the maze
        //the null/blocked Nodes are not included
        for (var list : matrix) {
            for(var node : list) {
                if(node != null)
                    maze.getNodesList().add(node);
            }
        }

        //set the source and target nodes
        maze.setSourceNode(sourceNode);
        maze.setTargetNode(targetNode);

        //everything is done, return the maze

        maze.setNodeMatrix(matrix);

        return maze;
    }
}
