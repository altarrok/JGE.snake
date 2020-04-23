package snake;

import engine.GameContainer;
import engine.Renderer;

import java.util.Arrays;

public class Board extends GameObject {
    private Block[] map;

    public Board(int width, int height) {
        map = new Block[width * height];
        this.width = width;
        this.height = height;
        Arrays.fill(map, EmptyBlock.getInstance());
    }

    public void addBlock(int posX, int posY, Block b) {
        map[posX + posY * width] = b;
    }

    public void move(int posX, int posY, int destX, int destY) throws AteApple, AteSnake, OutOfBounds {
        if (destX >= width || destX < 0 || destY >= height || destY < 0) throw new OutOfBounds();
        Block destBlock = map[destX + destY * width];
        if (destBlock instanceof SnakePart) throw new AteSnake();
        map[destX + destY * width] = map[posX + posY * width];
        map[posX + posY * width] = EmptyBlock.getInstance();
        if (destBlock instanceof Apple) {
            ((Apple) destBlock).eaten();
            throw new AteApple();
        }
    }

    @Override
    public void update(GameContainer gc, float dt) {

    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawRect(0, 1, gc.getWidth() - 1, gc.getHeight() - 2, 0xFF00FFFF);
        for (int i = 0; i < map.length; i++) {
            int rowSize = gc.getWidth() / gc.getTileSize();
            r.drawRect(gc.getTileSize() * (i % rowSize),
                    gc.getTileSize() * (i / rowSize),
                    gc.getTileSize(),
                    gc.getTileSize(),
                    0xFF00FFFF);
        }
    }
}
