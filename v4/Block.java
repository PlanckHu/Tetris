package Tetris.v5.v4;

import java.io.Serializable;

public class Block implements Serializable {
    private int x, y;
    private int id;

    public Block(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }
    public Block(Block another){
        this.x=another.getX();
        this.y=another.getY();
        this.id=another.getId();
    }

    public void DropStep() {
        y += 1;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int X) {
        this.x = X;
    }
}
