import static org.junit.Assert.*;

import noble.game.Command;
import noble.game.PiecePosition;
import org.junit.Test;
import noble.game.Board;

import java.util.Arrays;
import java.util.stream.IntStream;

import static noble.game.Command.*;
import static noble.game.Direction.*;

/**
 * Set of tests for the Board and various Command based moves of the Piece on the Board.
 */
public class BoardMovesTest {

    private PiecePosition defaultPosition = new PiecePosition(0, 0, N);

    @Test
    public void defaultValuesTest() {
        Board testBoard = new Board();
        assertEquals(defaultPosition, testBoard.getCurrentPosition());
        assertEquals(5, Board.SIZE);
    }

    @Test
    public void noMovesNoChangeInPositionTest() {
        Board testBoard = new Board();
        assertEquals(defaultPosition, testBoard.moveOnce(null));
        assertEquals(defaultPosition, testBoard.move(null));
        assertEquals(defaultPosition, testBoard.move(new Command[0]));
    }

    @Test
    public void verticalMovesTest() {
        // Given the default board
        Board testBoard = new Board();
        {
            // When we move forward once
            Command[] moveForwardOnce = { M };
            PiecePosition actualPosition = testBoard.move(moveForwardOnce);
            // We expect to be at 0, 1, N.
            PiecePosition expectedPosition = new PiecePosition(0, 1, N);
            assertEquals(expectedPosition, actualPosition);
        }
        {
            // When we move forward 3 more places
            Command[] moveForward3Times = { M, M, M };
            PiecePosition actualPosition = testBoard.move(moveForward3Times);
            // We expect to be at 0, 5, N.
            PiecePosition expectedPosition = new PiecePosition(0, 4, N);
            assertEquals(expectedPosition, actualPosition);
        }
        {
            // When we move forward 2 more places
            Command[] moveForwardTwice = { M, M };
            PiecePosition actualPosition = testBoard.move(moveForwardTwice);
            // We expect still to be at 0, 5, N, because we are already at the edge of the board
            PiecePosition expectedPosition = new PiecePosition(0, 4, N);
            assertEquals(expectedPosition, actualPosition);
        }
        {
            // Rotate 180 degress with 2 rights, so we face South
            Command[] rotateRight = { R, R };
            PiecePosition actualPosition = testBoard.move(rotateRight);
            assertEquals(S, actualPosition.getDirection());
        }
        {
            // When we move forward 10 more places and rotate R twice
            Command[] moveForward10Times = { M, M, M, M, M, M, M, M, M, M, R, R };
            PiecePosition actualPosition = testBoard.move(moveForward10Times);
            // We expect to be back at the start
            // because we don't move off the board
            assertEquals(defaultPosition, actualPosition);
        }
    }

    @Test
    public void rotationTest() {
        // Given the default board
        Board testBoard = new Board();
        {
            // Rotate once to the right
            Command[] rotateRight = { R };
            PiecePosition actualPosition = testBoard.move(rotateRight);
            // We expect to be at 0, 0, E
            PiecePosition expectedPosition = new PiecePosition(0, 0, E);
            assertEquals(expectedPosition, actualPosition);
        }
        {
            // Rotate once to the left, that is rotate back to where we were
            Command[] rotateLeft = { L };
            PiecePosition actualPosition = testBoard.move(rotateLeft);
            // We expect to be at the starting position
            assertEquals(defaultPosition, actualPosition);
        }
        {
            // Rotate once more to the left
            Command[] rotateLeft = { L };
            PiecePosition actualPosition = testBoard.move(rotateLeft);
            // We expect to be at 0, 0, W
            PiecePosition expectedPosition = new PiecePosition(0, 0, W);
            assertEquals(expectedPosition, actualPosition);
        }
        {
            // Rotate 4 times left
            Command[] rotateLeft4Times = { L, L, L, L };
            PiecePosition actualPosition = testBoard.move(rotateLeft4Times);
            // We expect to be at 0, 0, W
            PiecePosition expectedPosition = new PiecePosition(0, 0, W);
            assertEquals(expectedPosition, actualPosition);
        }
        {
            // Rotate 4 times right
            Command[] rotateRight4Times = { R, R, R, R };
            PiecePosition actualPosition = testBoard.move(rotateRight4Times);
            // We expect to be at 0, 0, W
            PiecePosition expectedPosition = new PiecePosition(0, 0, W);
            assertEquals(expectedPosition, actualPosition);
        }
    }

    @Test
    public void horizontalMovesTest() {
        // Given the default board
        Board testBoard = new Board();
        {
            // Rotate once to the right, so we face East
            Command[] rotateRight = { R };
            PiecePosition actualPosition = testBoard.move(rotateRight);
            assertEquals(E, actualPosition.getDirection());
        }
        {
            // When we move forward once
            Command[] moveForwardOnce = { M };
            PiecePosition actualPosition = testBoard.move(moveForwardOnce);
            // We expect to be at:
            PiecePosition expectedPosition = new PiecePosition(1, 0, E);
            assertEquals(expectedPosition, actualPosition);
        }
        {
            // When we move forward 3 more places
            Command[] moveForward3Times = { M, M, M };
            PiecePosition actualPosition = testBoard.move(moveForward3Times);
            // We expect to be at:
            PiecePosition expectedPosition = new PiecePosition(4, 0, E);
            assertEquals(expectedPosition, actualPosition);
        }
        {
            // When we move forward 2 more places
            Command[] moveForwardTwice = { M, M };
            PiecePosition actualPosition = testBoard.move(moveForwardTwice);
            // We expect to be at:
            PiecePosition expectedPosition = new PiecePosition(4, 0, E);
            // because we are already at the edge of the board
            assertEquals(expectedPosition, actualPosition);
        }
        {
            // Rotate 180 degress with 2 rights, so we face West
            Command[] rotateRight = { R, R };
            PiecePosition actualPosition = testBoard.move(rotateRight);
            assertEquals(W, actualPosition.getDirection());
        }
        {
            // When we move forward 10 more places and rotate R
            Command[] moveForward10Times = { M, M, M, M, M, M, M, M, M, M, R };
            PiecePosition actualPosition = testBoard.move(moveForward10Times);
            // We expect to be back at the start
            // because we don't move off the board
            assertEquals(defaultPosition, actualPosition);
        }
    }


    @Test
    public void traverseTheBoardTest() {
        // Given the default board
        Board testBoard = new Board();

        // walk a spiral path that traverses every square.
        Command[] spiralTraversal = {
                M, M, M, M, R,
                M, M, M, M, R,
                M, M, M, M, R,
                M, M, M, R,
                M, M, M, R,
                M, M, R,
                M, M, R,
                M, R,
                M
        };

        // create an array to record every square of the board we visit.
        int[][] visitingRecord = new int[Board.SIZE][Board.SIZE];
        IntStream.range(0, Board.SIZE)
                .forEach(r -> IntStream.range(0, Board.SIZE)
                        .forEach(c -> visitingRecord[r][c] = 0));

        // traverse each square one at a time recording the default square as well
        Command[] oneElementCommand = new Command[1];
        PiecePosition actualPosition = null;
        visitingRecord[defaultPosition.getXPos()][defaultPosition.getYPos()]++;
        for (Command command : spiralTraversal) {
            oneElementCommand[0] = command;
            actualPosition = testBoard.move(oneElementCommand);
            if (oneElementCommand[0] == M) {
                visitingRecord[actualPosition.getXPos()][actualPosition.getYPos()]++;
            }
        }

        // We expect to end up in the middle of the board:
        PiecePosition expectedPosition = new PiecePosition(2, 2, N);
        assertEquals(expectedPosition, actualPosition);

        // And visited every square exactly once.
        assertTrue(Arrays.stream(visitingRecord).flatMapToInt(Arrays::stream).allMatch(x -> x == 1));
    }
}