package snake;

public class EmptyBlock implements Block {
    private static EmptyBlock emptyBlock;

    private EmptyBlock() {
    }

    public static EmptyBlock getInstance() {
        if (emptyBlock == null) {
            emptyBlock = new EmptyBlock();
        }
        return emptyBlock;
    }
}
