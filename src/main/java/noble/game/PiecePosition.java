package noble.game;

import javax.validation.constraints.NotNull;
import java.util.Objects;

import static noble.game.Direction.*;

/**
 * Immutable class to represent the Position and Direction of the Piece on the Board.
 */
public final class PiecePosition {
    private final int xPos;
    private final int yPos;
    @NotNull
    private final Direction direction;

    public PiecePosition(int xPos, int yPos, @NotNull Direction direction) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.direction = direction;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public Direction getDirection() {
        return direction;
    }

    public PiecePosition moveForward() {
        switch (direction) {
            case N:
                return new PiecePosition(xPos, yPos+1, direction);
            case S:
                return new PiecePosition(xPos, yPos-1, direction);
            case E:
                return new PiecePosition(xPos+1, yPos, direction);
            case W:
                return new PiecePosition(xPos-1, yPos, direction);
            default:
                throw new RuntimeException("Unknown direction " + this);
        }
    }

    public PiecePosition rotateLeft() {
        return new PiecePosition(xPos, yPos, direction.rotateLeft());
    }

    public PiecePosition rotateRight() {
        return new PiecePosition(xPos, yPos, direction.rotateRight());
    }

    /**
     * Determines whether the Piece is at the end of a Board's boundary
     * This method takes into account the Direction the Piece is facing.
     * For example if it's facing South and on the minimum y-axis (row) then it'll return true.
     * @param minXPos The minimum x-axis position or first column for a board
     * @param maxXPos The maximum x-axis position or last column for a board
     * @param minYPos The minimum y-axis position or first row for a board
     * @param maxYPos The maximum y-axis position or last row for a board
     * @return true if on edge of board relative to direction facing.
     */
    public boolean onEdgeOfBoundary(int minXPos, int maxXPos, int minYPos, int maxYPos) {
        return     (direction == N && yPos == maxYPos)
                || (direction == S && yPos == minYPos)
                || (direction == E && xPos == maxXPos)
                || (direction == W && xPos == minXPos);
    }

    @Override
    public String toString() {
        return "(" + xPos + "," + yPos + " " + direction + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PiecePosition position = (PiecePosition) o;
        return xPos == position.xPos &&
                yPos == position.yPos &&
                direction == position.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xPos, yPos, direction);
    }
}
