import maze.Maze;
import maze.MazeAstarSolver;
import maze.MazeBuilder;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        File file = new File("C:\\Users\\Mark\\Documents\\JavaProjects\\Pathfinder\\src\\main\\resources\\maze.txt");
        Maze maze = new MazeBuilder().build(file);
        MazeAstarSolver solver = new MazeAstarSolver(maze);
        var solution = solver.solve();
        for(var node : solution) {
            System.out.println(node.getX() + " " + node.getY());
        }

        System.out.println(solver.showSolution());
    }
}
