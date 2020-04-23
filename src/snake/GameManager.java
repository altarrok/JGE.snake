package snake;

import engine.AbstractGame;
import engine.GameContainer;
import engine.Renderer;

import java.util.ArrayList;

public class GameManager extends AbstractGame {
    /**
     * GAME CONSTANTS =========================
     */
    private static final int tileSize = 48;
    private static final int tiledWidth = 24;
    private static final int tiledHeight = 16;
    private static final int width = tileSize*tiledWidth;
    private static final int height = tileSize*tiledHeight;
    private static final float scale = 1f;
    private static final double wantedFps = 60.0;


    private ArrayList<GameObject> objects = new ArrayList<>();
    private AiPython ai;

    public GameManager() {
        reset();
    }

    public void reset() {
        objects.removeAll(objects);

        Board b = new Board(tiledWidth, tiledHeight);
        Apple a = new Apple(16, 5, tiledWidth, tiledHeight, b);

        SnakePart head = new SnakePart(SnakePart.Part.HEAD, 5, 5, b);
        head.stretch();
        head.stretch();
        head.stretch();

        ai = new AiPython(b, head);

        objects.add(b);
        objects.add(a);
        objects.add(head);

        b.addBlock(16, 5, a);
        b.addBlock(5, 5, head);
    }

    double ticks = 0;

    @Override
    public void update(GameContainer gc, float dTime) {
        for (int i = 0; i < objects.size(); i++) {
            // Updating objects
            objects.get(i).update(gc, dTime);
            // Removing objects
            if (objects.get(i).isDead()) {
                objects.remove(i);
                i--;
            }
        }

        ticks += dTime;
        if (ticks >= 0.3) {
            try {
                ((SnakePart) getObject("SNAKEHEAD")).moveSnake(null);
                ai.update(gc, dTime);
            } catch (Exception e) {
                reset();
            }
            ticks = 0;
        }
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) {
        for (GameObject object : objects) {
            object.render(gc, renderer);
        }
    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.setWidth(width);
        gc.setHeight(height);
        gc.setScale(scale);
        gc.setFPS(wantedFps);
        gc.setDrawFPS(false);
        gc.setTileSize(tileSize);
        gc.start();
    }

    public GameObject getObject(String tag) {
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getTag().equals(tag)) {
                return objects.get(i);
            }
        }
        return null;
    }
}
