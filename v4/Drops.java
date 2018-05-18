package Tetris.v5.v4;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.ArrayList;

public class Drops extends Thread implements KeyListener , Serializable {
    private Main frame;
    private int[][] IDMaps;
    private int XSize, YSize;
    private volatile boolean stop;
    private int sleepTime;

    public Drops(Main frame, int XSize, int YSize) {
        this.IDMaps = frame.getIDBlocks();
        this.XSize = XSize;
        this.YSize = YSize;
        this.frame = frame;
        stop = false;
        sleepTime = 300;
    }

    public void run() {
        while (true) {
            if (stop) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /***
                 * 被消减的形状可以依然看为一个形状
                 * 只不过里面存放的block的数量减少了
                 */
                DropStep();
                RenewIDGrid();
                /**
                 * 重绘
                 */
                frame.paint(frame.getGraphics());
                frame.getNextBlock().paint(frame.getNextBlock().getGraphics());

            }
        }
    }

    private void DropStep() {
        ArrayList<Shapes> ShapeList = frame.getShapeList();
        for (int i = 0; i < ShapeList.size(); i++) {
            Shapes shapes = ShapeList.get(i);
            shapes.MoveDown();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == 32) {
            frame.btnClick();
            return;
        } else if (keyCode == 17) {
            displayIDGrid();
            return;
        }
        Shapes shape = new Shapes();
        for (int i = 0; i < frame.getShapeList().size(); i++) {
            shape = frame.getShapeList().get(i);
            if (shape.isDroping()) break;
        }
        if (shape.getId() == 0) return;
        switch (keyCode) {
            case 37:
                shape.MoveHorizontally("left");
                break;
            case 39:
                shape.MoveHorizontally("right");
                break;
            case 38:
                shape.Transform();
                break;
            case 40:
                shape.MoveDown();
                break;

            default:
                break;
        }
        RenewIDGrid();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void setStop(boolean bool) {
        this.stop = bool;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void RenewIDGrid() {
        for (int x = 0; x < frame.getXRange(); x++) {
            for (int y = 0; y < frame.getYRange(); y++) {
                frame.getIDBlocks()[x][y] = 0;
            }
        }
        for (int i = 0; i < frame.getShapeList().size(); i++) {
            Shapes shapes = frame.getShapeList().get(i);
            for (int j = 0; j < shapes.getStorage().size(); j++) {
                Block block = shapes.getStorage().get(j);
                frame.getIDBlocks()[block.getX()][block.getY()] = block.getId();
            }
        }
    }

    private void displayIDGrid() {
        for (int y = 0; y < frame.getYRange() ; y++) {
            for (int x = 0; x < frame.getXRange() ; x++)
                System.out.print(frame.getIDBlocks()[x][y] + " ");
            System.out.println();
        }
        System.out.println("*****************");
    }
}
