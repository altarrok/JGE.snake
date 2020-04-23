package snake;

import engine.GameContainer;
import engine.Renderer;

public class SnakePart extends GameObject implements Block {
    enum Part {
        HEAD, BODY
    }

    private Part part;
    private Board board;
    public SnakePart next;
    private int dirX;
    private int dirY;
    private int oldX;
    private int oldY;

    public SnakePart(Part part, int x, int y, Board board) {
        this.part = part;
        this.tag = "SNAKE" + part;
        this.posX = x;
        this.posY = y;
        this.oldX = x - 1;
        this.oldY = y;
        this.board = board;
        this.dirX = 1;
        this.dirY = 0;
    }

    public void turn(String dir) {
        switch (dir){
            case "U":
                dirY = -1;
                dirX = 0;
                break;
            case "L":
                dirY = 0;
                dirX = -1;
                break;
            case "D":
                dirY = 1;
                dirX = 0;
                break;
            case "R":
                dirY = 0;
                dirX = 1;
                break;
        }
    }

    public void move(int x, int y) {
        try {
            board.move((int) posX, (int) posY, x, y);
            this.oldX = (int) posX;
            this.oldY = (int) posY;
            this.posX = x;
            this.posY = y;
        } catch (AteApple ateApple) {
            // Nice +points
            stretch();
            this.oldX = (int) posX;
            this.oldY = (int) posY;
            this.posX = x;
            this.posY = y;
        } catch (AteSnake ateSnake) {
            // damn, dead
            die();
        } catch (OutOfBounds outOfBounds) {
            die();
        }
    }

    public void stretch() {
        SnakePart curr = this;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = new SnakePart(Part.BODY, curr.oldX, curr.oldY, board);
        board.addBlock(curr.oldX, curr.oldY, curr.next);

    }

    public void print() {
        System.out.println(this.posX + " " + this.posY);
        if (next != null) next.print();
    }

    public void moveSnake(SnakePart before) {
        this.move((int) posX + dirX, (int) posY + dirY);
        if (this.next != null) this.next.moveSnake(this);
        if (before != null) {
            this.dirX = before.dirX;
            this.dirY = before.dirY;
        }
    }

    public void die() {
        if (this.next != null) this.next.die();
        this.dead = true;
    }

    @Override
    public void update(GameContainer gc, float dt) {
//        print();
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.fillRect((int) posX * gc.getTileSize() + 2, (int) posY * gc.getTileSize() + 2, gc.getTileSize() - 4, gc.getTileSize() - 4, 0xFF00FF00);
        if (this.next != null) this.next.render(gc, r);
    }
}
