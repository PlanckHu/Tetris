package Tetris.v5.v4;



import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends JPanel implements Serializable {
    /**
     * IDBlocks是一个模拟游戏面板格子的充满ID的int[][]
     * ColorMap是把Color和ID联系起来的HashMap
     * ShapeList是存放了所有形状的ArrayList
     */
    private int BlockSize = 20;
    private int StartX = 5, StartY = 5;
    private static int gameRangeWidth = 480, getGameRangeHeight = 600;
    private int XRange = gameRangeWidth / BlockSize, YRange = getGameRangeHeight / BlockSize;
    private int Speed = BlockSize;
    private JPanel TipsPanel = new JPanel();
    private JButton btnStart = new JButton("Start");
    private Drops drops;
    private CreateShapes create;
    private NextBlock nextBlock;
    private JTextField ScoreLabel=new JTextField("Scroe: 0");
    private int Scroe=0;
    private int[][] IDBlocks = new int[XRange][YRange];
    private HashMap<Integer, Color> ColorMap = new HashMap<>();
    private ArrayList<Shapes> ShapeList = new ArrayList<>();

    /**
     * 实现一直往下掉
     * 就是把形状都放进Arraylist里，然后一直检查movable，然后move down
     * 消除的时候是减少一个shape里的block数量，当block数量为0时，从Arraylist里移除这个shape
     */
    public static void main(String[] args) {
        Main m = new Main();
        m.Initial();
    }

    public void Initial() {
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(600, 700));
        frame.setDefaultCloseOperation(3);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setBackground(Color.darkGray);

        TipsPanel.setPreferredSize(new Dimension(100, 600));
        TipsPanel.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(gameRangeWidth, getGameRangeHeight));

        btnStart.setPreferredSize(new Dimension(80, 50));


        frame.add(TipsPanel, BorderLayout.EAST);
        frame.add(this, BorderLayout.CENTER);
        TipsPanel.setBackground(Color.lightGray);
        this.setBackground(Color.gray);
        ScoreLabel.setPreferredSize(new Dimension(100,50));

        nextBlock=new NextBlock(this);
        nextBlock.setPreferredSize(new Dimension(100,400));
        nextBlock.setBackground(Color.DARK_GRAY);
        TipsPanel.add(btnStart,BorderLayout.NORTH);
        TipsPanel.add(nextBlock,BorderLayout.CENTER);
        drops = new Drops(this, XRange, YRange);
        create = new CreateShapes(this,drops);
        Control control=new Control(this);
        btnStart.addActionListener(control);
        this.addKeyListener(drops);
        this.addKeyListener(nextBlock);
        create.start();
        drops.start();
    }

    public void paint(Graphics g) {
        this.requestFocus();
        Image newImage = this.createImage(this.getWidth(), this.getHeight());
        Graphics newG = newImage.getGraphics();
        super.paint(newG);
        /**
         * 在中间用 newG 画东西
         *
         */
        //*****************************
        DrawShapes(newG);
        ScoreLabel.setText("Score: "+this.Scroe);
        newG.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < YRange + 1; i++) {
            newG.drawLine(StartX, StartY + i * BlockSize, StartX + gameRangeWidth, StartY + i * BlockSize);
        }
        for (int i = 0; i < XRange + 1; i++) {
            newG.drawLine(StartX + i * BlockSize, StartY, StartX + i * BlockSize, StartY + getGameRangeHeight);
        }
        g.drawImage(newImage, 0, 0, null);
    }

    private void DrawShapes(Graphics g) {
        for (int i = 0; i < ShapeList.size(); i++) {
            Shapes shapes = ShapeList.get(i);
            for (int cnt = 0; cnt < shapes.getStorage().size(); cnt++) {
                Block block = shapes.getStorage().get(cnt);
                g.setColor(ColorMap.get(block.getId()));
                g.fillRect(StartX + block.getX() * BlockSize, StartY + block.getY() * BlockSize, BlockSize, BlockSize);
            }
        }
    }

    public void btnClick() {
        if (btnStart.getText().equals("Start") || btnStart.getText().equals("Resume")) {
            drops.setStop(true);
            create.setStop(true);
            btnStart.setText("Pause");
        } else if (btnStart.getText().equals("Pause")) {
            drops.setStop(false);
            create.setStop(false);
            btnStart.setText("Resume");
        }
    }

    public ArrayList<Shapes> getShapeList() {
        return ShapeList;
    }

    public int[][] getIDBlocks() {
        return IDBlocks;
    }

    public int getXRange() {
        return XRange;
    }

    public int getYRange() {
        return YRange;
    }

    public HashMap<Integer, Color> getColorMap() {
        return ColorMap;
    }

    public int getBlockSize() {
        return BlockSize;
    }

    public int getScroe(){
        return Scroe;
    }

    public void setScroe(int scroe) {
        Scroe = scroe;
    }

    public CreateShapes getCreate() {
        return create;
    }

    public Drops getDrops() {
        return drops;
    }

    public NextBlock getNextBlock() {
        return nextBlock;
    }

    public void setColorMap(HashMap<Integer, Color> colorMap) {
        ColorMap = colorMap;
    }

    public void setIDBlocks(int[][] IDBlocks) {
        this.IDBlocks = IDBlocks;
    }

    public void setShapeList(ArrayList<Shapes> shapeList) {
        ShapeList = shapeList;
    }

    public JButton getBtnStart() {
        return btnStart;
    }
}
