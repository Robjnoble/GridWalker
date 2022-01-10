package noble.game;

/**
 * An enum to represent the Direction of the Piece.
 * Based on the assumption that Direction will be a key entity for the Game.
 */
public enum Direction {
    N, E, S, W;

    Direction rotateRight() {
        switch (this) {
            case N:
                return E;
            case E:
                return S;
            case S:
                return W;
            case W:
                return N;
            default:
                throw new RuntimeException("Impossible rotateRight Direction: " + this);
        }
    }

    Direction rotateLeft() {
        switch (this) {
            case N:
                return W;
            case E:
                return N;
            case S:
                return E;
            case W:
                return S;
            default:
                throw new RuntimeException("Impossible rotateLeft Direction: " + this);
        }
    }
}

    // Note I consciously went for explicit albeit more verbose rotate right and left implementations
    // as they are safer and less prone to errors if enums are added or moved in initial order.
    // An alternative approach based on the enum ordinals would have looked like this:
//    private static Direction[] vals = values();
//    public Direction rotateRight() {
//        return vals[(this.ordinal()+1) % vals.length];
//    }
