package noble.game;

import static noble.game.Command.*;
import static noble.game.Direction.*;

/**
 * The game Board.
 *
 * Responsible for movement of the Piece.
 *
 * The Set of Commands (CommandSet) is arguably an entity, with more time I'd create a class to represent the CommandSet,
 * The current use case is sufficiently trivial that an array of Commands is more practical for this implementation.
 */
public class Board {
    /**
     * The board dimensions.
     * The board being square was strongly asserted in the requirements.
     */
    public static final int SIZE = 5;

    // Made the decision that the Board owns its boundaries not the PiecePosition
    private final int minBoundary = 0;
    private final int maxBoundary = SIZE-1;

    /**
     * The Piece Position on the Board. Defaulted to the starting values specified.
     */
    private PiecePosition currentPosition = new PiecePosition(0, 0, Direction.N);


    public PiecePosition move(Command[] commandSet) {
        if (commandSet == null || commandSet.length == 0) {
            return currentPosition;
        }

        for (Command command : commandSet) {
            moveOnce(command);
        }
        return currentPosition;
    }

    public PiecePosition moveOnce(Command command) {
        if (command == null) {
            return currentPosition;
        }

        switch (command) {
            case M:
                if (!currentPosition.onEdgeOfBoundary(minBoundary, maxBoundary, minBoundary, maxBoundary)) {
                    currentPosition = currentPosition.moveForward();
                }
                break;
            case L:
                currentPosition = currentPosition.rotateLeft();
                break;
            case R:
                currentPosition = currentPosition.rotateRight();
                break;
            default:
                throw new RuntimeException("Unknown Command request");
        }
        return currentPosition;
    }

    public PiecePosition getCurrentPosition() {
        return currentPosition;
    }
}
