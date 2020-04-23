package snake;

import engine.GameContainer;
import engine.Renderer;

import java.util.Random;

public class Apple extends GameObject implements Block {
    private Random random;
    private Board board;

    public Apple(int x, int y, int width, int height, Board board) {
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.board = board;
        random = new Random();
    }

    public void eaten() {
        int newPos = random.nextInt(width * height);
        int oldX = (int) this.posX;
        int oldY = (int) this.posY;
        this.posX = newPos % width;
        this.posY = newPos / width;
        board.addBlock((int) posX, (int) posY, this);
    }

    @Override
    public void update(GameContainer gc, float dt) {

    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.fillRect((int) posX * gc.getTileSize() + 1, (int) posY * gc.getTileSize() + 1, gc.getTileSize() - 2, gc.getTileSize() - 2, 0xFFFF0000 );
    }
}
