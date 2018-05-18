package Tetris.v5.v4;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

class CreateShapes extends Thread implements Serializable {
    private Main frame;
    private int shapesCount;
    private volatile boolean stop;
    private Drops drops;
    private int nextStyle = -1;
    private Color nextColor = null;

    public CreateShapes(Main frame, Drops drops) {
        this.frame = frame;
        shapesCount = 0;
        this.drops = drops;
        stop = false;
    }

    public void run() {
        while (true) {
            if (stop) {

                /**
                 * 创建一个shape放在Shapelist里
                 */
                boolean creatNew = true;
                for (int i = 0; i < frame.getShapeList().size(); i++) {
                    Shapes shape = frame.getShapeList().get(i);
                    if (shape.isDroping()) creatNew = false;
                }
                if (creatNew) {
                    /**
                     * 先判断消除
                     */
                    int cnt = Eliminate();
                    if (cnt != 0) {
                        drops.setSleepTime((int) (drops.getSleepTime() * 0.9));
                        frame.setScroe(frame.getScroe() + 100 * cnt);
//                        System.out.println("line count = " + cnt);
                    }
                    /**
                     * 再创建新的
                     */
                    Random random = new Random();
                    if (nextStyle == -1) {
                        nextStyle = random.nextInt(5);
                        int ColorCnt = random.nextInt(5);
                        if (ColorCnt == 0) nextColor = Color.blue;
                        else if (ColorCnt == 1) nextColor = Color.cyan;
                        else if (ColorCnt == 2) nextColor = Color.PINK;
                        else if (ColorCnt == 3) nextColor = Color.yellow;
                        else nextColor = Color.MAGENTA;
                    }
                    int x = random.nextInt(frame.getXRange() - 2);
                    int y = 0;
                    int style = nextStyle;
                    nextStyle = random.nextInt(5);
                    Color color = nextColor;
                    int ColorCnt = random.nextInt(5);
                    if (ColorCnt == 0) nextColor = Color.blue;
                    else if (ColorCnt == 1) nextColor = Color.cyan;
                    else if (ColorCnt == 2) nextColor = Color.PINK;
                    else if (ColorCnt == 3) nextColor = Color.yellow;
                    else nextColor = Color.MAGENTA;
                    shapesCount++;
                    Shapes shapes = new Shapes(frame, x, y, style, shapesCount);
                    frame.getColorMap().put(shapesCount, color);
                    frame.getShapeList().add(shapes);
                    //等
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private int Eliminate() {
        ArrayList<Integer> EliminateLine = new ArrayList<>();
        for (int y = 0; y < frame.getYRange(); y++) {
            boolean deletable = true;
            for (int x = 0; x < frame.getXRange() ; x++) {
                if (frame.getIDBlocks()[x][y] == 0) {
                    deletable = false;
                    break;
                }
            }
            if (deletable) EliminateLine.add(y);
        }
        int shapesCount = frame.getShapeList().size();
        for (int i = 0; i < shapesCount; i++) {
            Shapes shapes = frame.getShapeList().get(i);
            if (shapes.Eliminate(EliminateLine) == 0) {
                frame.getShapeList().remove(i);
                i -= 1;
                shapesCount -= 1;
            }
        }
        return EliminateLine.size();
    }


    public void setStop(boolean bool) {
        this.stop = bool;
    }

    public int getNextStyle() {
        return nextStyle;
    }

    public Color getNextColor() {
        return nextColor;
    }

    public void setNextColor(Color nextColor) {
        this.nextColor = nextColor;
    }

    public void setNextStyle(int nextStyle) {
        this.nextStyle = nextStyle;
    }
}
