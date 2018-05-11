import maze.Maze;
import maze.MazeBuilder;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        File file = new File("C:\\Users\\Mark\\Documents\\JavaProjects\\Pathfinder\\src\\main\\resources\\maze.txt");
        Maze maze = new MazeBuilder().build(file);
        String expected =
                "....................................\n" +
                        "..S...#......................#......\n" +
                        "......#......................#......\n" +
                        ".............................#......\n" +
                        "....................................\n" +
                        "....................................\n" +
                        "..............#.....................\n" +
                        "............#.......................\n" +
                        "..........#.........................\n" +
                        "....................................\n" +
                        ".....................#..........#...\n" +
                        ".....................#....X.....#...\n" +
                        ".....................#..........#...\n" +
                        "....................................";
        System.out.println(maze.stringFormat());
    }
}
