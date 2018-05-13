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
        long solveTime = 0;
        Maze maze = null;
        try {
            maze = new MazeBuilder().build(file);
            long beginTime = System.currentTimeMillis();
            maze.solve();
            solveTime = System.currentTimeMillis() - beginTime;
            System.out.println(maze.getDirections());
        } catch (InvalidMazeException ex) {
            System.out.println(ex.getMessage());
            return;
        }


        for(var argument : args) {
            if(argument.equals("-v")) {
                System.out.println();
                if(maze.isSolved()) {
                    System.out.println("Path length: " + maze.getDirections().length());
                    System.out.println("Solve took " + solveTime + "ms");
                    System.out.println(maze.getStringSolution());
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
