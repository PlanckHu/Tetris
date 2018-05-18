package Tetris.v5.v4;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class NextBlock extends JPanel implements KeyListener, Serializable {
    private Main frame;

    public NextBlock(Main frame) {
        this.frame = frame;
    }

    public void paint(Graphics g) {
        Image newImage = this.createImage(this.getWidth(), this.getHeight());
        Graphics newG = newImage.getGraphics();
        super.paint(newG);


        newG.setColor(Color.lightGray);
        newG.drawString("得分: " + frame.getScroe(), 5, 20);

        newG.drawString("下一个: ", 5, 40);
        int style = frame.getCreate().getNextStyle();
        int x = 20;
        int y = 70;
        newG.setColor(frame.getCreate().getNextColor());
        switch (style) {
            case 0:
                drawRect(newG, this.getWidth() / 2 - frame.getBlockSize(), y);
                break;
            case 1:
                drawT(newG, this.getWidth() / 2 - 3 * frame.getBlockSize() / 2, y);
                break;
            case 2:
                drawZ(newG, this.getWidth() / 2 - 3 * frame.getBlockSize() / 2, y);
                break;
            case 3:
                drawL(newG, this.getWidth() / 2 - frame.getBlockSize(), y);
                break;
            case 4:
                drawI(newG, this.getWidth() / 2 - frame.getBlockSize() / 2, y);
                break;
        }
        newG.setColor(Color.lightGray);
        newG.drawString("****************", 5, 200);
        newG.drawString("旋转      ↑", 5, 220);
        newG.drawString("左移      ←", 5, 240);
        newG.drawString("右移      →", 5, 260);
        newG.drawString("下移      ↓", 5, 280);
        newG.drawString("暂停  space", 5, 300);
        newG.drawString("开始  space", 5, 320);
        newG.drawString("保存      S", 5, 340);
        newG.drawString("载入      L", 5, 360);
        g.drawImage(newImage, 0, 0, null);
    }

    private void drawRect(Graphics g, int StartX, int StartY) {
        g.fillRect(StartX, StartY, frame.getBlockSize() * 2, frame.getBlockSize() * 2);
    }

    private void drawT(Graphics g, int StartX, int StartY) {
        g.fillRect(StartX + frame.getBlockSize(), StartY, frame.getBlockSize(), frame.getBlockSize());
        g.fillRect(StartX, StartY + frame.getBlockSize(), frame.getBlockSize() * 3, frame.getBlockSize());
    }

    private void drawZ(Graphics g, int StartX, int StartY) {
        g.fillRect(StartX, StartY, frame.getBlockSize() * 2, frame.getBlockSize());
        g.fillRect(StartX + frame.getBlockSize(), StartY + frame.getBlockSize(), frame.getBlockSize() * 2, frame.getBlockSize());
    }

    private void drawL(Graphics g, int StartX, int StartY) {
        g.fillRect(StartX, StartY, frame.getBlockSize(), frame.getBlockSize() * 3);
        g.fillRect(StartX + frame.getBlockSize(), StartY + frame.getBlockSize() * 2, frame.getBlockSize(), frame.getBlockSize());
    }

    private void drawI(Graphics g, int StartX, int StartY) {
        g.fillRect(StartX, StartY, frame.getBlockSize(), frame.getBlockSize() * 4);
    }

    private void SaveGame(Main frame) {
        /**
         *
         * 输入保存文件
         * 检测是否已存在
         * 若已经存在，提示是否覆盖
         * 不覆盖则重新输入
         * 覆盖就覆盖吧
         * 把主函数那个对象(就是frame)保存都文件里
         */
        WriteToDocument(frame);

    }

    private boolean LoadGame() {
        /**
         * 加一个读取按钮
         * 选择读取文件
         * 把主函数那个对象(就是frame)从文件读取，然后重绘
         * return false就是读取不成功
         */
        return false;
    }

    private void WriteToDocument(Object object) {
        File file = new File("F:/save.dat");
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(object);
            objOut.flush();
            objOut.close();
            System.out.println("write successful!");
        } catch (IOException e) {
            System.out.println("write failed");
            System.out.println(e.fillInStackTrace());
        }
    }

    private Object ReadFromFile() {
        Object temp = null;
        File file = new File("F:/save.dat");
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(in);
            temp = objIn.readObject();
            objIn.close();
            System.out.println("Read successful!");
        } catch (IOException e) {
            System.out.println("Read fail!");
        } catch (ClassNotFoundException e) {

        }
        return temp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();
        if (keycode == 83) {
            WriteToDocument(frame);
        } else if (keycode == 76) {
            Main temp = (Main) ReadFromFile();
            frame.getCreate().setStop(false);
            frame.getDrops().setStop(false);
            frame.getCreate().setNextColor(temp.getCreate().getNextColor());
            frame.getCreate().setNextStyle(temp.getCreate().getNextStyle());
            frame.setScroe(temp.getScroe());
            frame.setColorMap(temp.getColorMap());
            frame.setIDBlocks(temp.getIDBlocks());
            frame.setShapeList(temp.getShapeList());
            frame.getBtnStart().setText("Resume");
            frame.paint(frame.getGraphics());
            paint(this.getGraphics());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
