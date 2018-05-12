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
        try {
            Maze maze = new MazeBuilder().build(file);
            maze.solve();
            System.out.println(maze.getDirections());
        } catch (InvalidMazeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void showHelp() {
        String help = "\nHELP.....\n\n" +
                "Usage: include a path to a file containing a maze as the first command line argument\n" +
                "The output will be the directions from the Source node to the Target Node\n" +
                "If no solution is possible the error will be shown";
        System.out.println(help);
    }
}
