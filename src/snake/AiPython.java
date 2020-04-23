package snake;

import engine.GameContainer;
import engine.Renderer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AiPython extends GameObject {

    private Board board;
    private SnakePart head;
    ProcessBuilder pb;
    Process p;
    BufferedReader in;
    boolean working = false;

    public AiPython(Board board, SnakePart head) {
        this.board = board;
        this.head = head;
    }

    double tick = 0;
    @Override
    public void update(GameContainer gc, float dt) {
            try {
                if (!working) {
                    // BUILD STATE STRING
                    working = true;
                    try {
                        pb = new ProcessBuilder("python", "src/aipy/snake-ai.py");
                    } catch (Exception k) {
                    }
                    p = pb.start();
                    in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String next = in.readLine();
                    // PARSE OUTPUT
                    // TURN SNAKE
                    p.destroy();
                    working = false;
                }
            } catch (Exception e) {
                System.out.println("error" + e);
            }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {

    }
}
