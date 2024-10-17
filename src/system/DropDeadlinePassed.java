package system;

public class DropDeadlinePassed extends RuntimeException {
    public DropDeadlinePassed(String message) {
        super(message);
    }
}
