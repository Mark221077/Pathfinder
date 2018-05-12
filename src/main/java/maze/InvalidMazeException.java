package maze;

public class InvalidMazeException extends RuntimeException {

    public InvalidMazeException() {
    }

    public InvalidMazeException(String message) {
        super(message);
    }
}
