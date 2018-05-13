import maze.InvalidMazeException;
import maze.Maze;
import maze.MazeBuilder;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("No command line arguments specified");
            showHelp();
            return;
        }

        File file = new File(args[0]);
        long solveTime;             //for verbose info
        Maze maze;
        try {
            maze = new MazeBuilder().build(file);           //try to build from the argument
            long beginTime = System.currentTimeMillis();
            maze.solve();
            solveTime = System.currentTimeMillis() - beginTime;     //calculate the solve time
            System.out.println(maze.getDirections());               //print the directions
        } catch (InvalidMazeException ex) {
            System.out.println(ex.getMessage());                //thrown if error with the file or maze not solvable
            return;                                     //nothing else to do
        }


        for(var argument : args) {
            if(argument.equals("-v")) {             //if verbose
                System.out.println();
                if(maze.isSolved()) {               //if the maze was solved
                    System.out.println("Path length: " + maze.getDirections().length());        //the length of the path
                    System.out.println("Solve took " + solveTime + "ms");           //the time it took to solve the maze
                    System.out.println(maze.getSolutionMatrix());                       //the string representation of the solution
                }

            }
        }
    }

    private static void showHelp() {
        String help = "\nHELP.....\n\n" +
                "Usage: include a path to a file containing a maze as the first command line argument\n" +
                "The output will be the directions from the Source node to the Target Node\n" +
                "If no solution is possible the error will be shown\n\n" +
                "If the -v flag is specified additional information will be shown: \n" +
                "The length of the path, the time it took to solve the maze, and the solution\n" +
                "with '*' characters showing the optimal path between S and X";
        System.out.println(help);
    }
}
