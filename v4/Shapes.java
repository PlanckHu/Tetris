package Tetris.v5.v4;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Shapes implements Serializable {
    private int style;
    private int x, y;
    private int id;
    private boolean Droping;
    private int form;
    private ArrayList<Block> Storage = new ArrayList<>();
    private Main frame;

    public Shapes() {
        id = 0;
    }

    public Shapes(Main frame, int x, int y, int style, int id) {
        this.x = x;
        this.y = y;
        this.style = style;
        this.frame = frame;
        this.id = id;
        Droping = true;
        form = 0;
        CreateShape();
    }
    public Shapes(Shapes another){
        this.x=another.getX();
        this.y=another.getY();
        this.id=another.getId();
        this.Droping=another.isDroping();
        this.Storage.clear();
        this.frame=another.getFrame();
        for(int i=0;i<another.getStorage().size();i++){
           Block block=new Block(another.getStorage().get(i));
           Storage.add(block);
        }
    }

    /**
     * 创建形状
     */
    public void CreateShape() {
        switch (style) {
            case 0:
                CreateRect();
                break;
            case 1:
                CreateT();
                break;
            case 2:
                CreateZ();
                break;
            case 3:
                CreateL();
                break;
            case 4:
                CreateI();
        }
    }

    private void CreateL() {
        for (int Yplus = 0; Yplus < 3; Yplus++) {
            Block block = new Block(x, y + Yplus, id);
            Storage.add(block);
        }
        Block block = new Block(x + 1, y + 2, id);
        Storage.add(block);
    }

    private void CreateI() {
        for (int i = 0; i < 4; i++) {
            Block block = new Block(x, y + i, id);
            Storage.add(block);
        }
    }

    private void CreateRect() {
        for (int Yplus = 0; Yplus < 2; Yplus++) {
            for (int Xplus = 0; Xplus < 2; Xplus++) {
                Block block = new Block(x + Xplus, y + Yplus, id);
                Storage.add(block);
            }
        }
    }

    private void CreateT() {
        Block b1 = new Block(x + 1, y, id);
        Storage.add(b1);
        for (int i = 0; i < 3; i++) {
            Block block = new Block(x + i, y + 1, id);
            Storage.add(block);
        }
    }

    private void CreateZ() {
        for (int Yplus = 0; Yplus < 2; Yplus++) {
            for (int Xplus = 0; Xplus < 2; Xplus++) {
                Block block = new Block(x + Xplus + Yplus, y + Yplus, id);
                Storage.add(block);
            }
        }
    }

    /**
     * 变换形态（旋转）
     */
    public void Transform() {
        switch (style) {
            case 0:
                break;
            case 1:
                form = TransformT(form, frame.getIDBlocks());
                break;
            case 2:
                form = TransformZ(form, frame.getIDBlocks());
                break;
            case 3:
                form = TransformL(form, frame.getIDBlocks());
                break;
            case 4:
                form = TransformI(form, frame.getIDBlocks());
                break;
            default:
                break;
        }
    }

    private int TransformT(int form, int[][] idBlocks) {
        switch (form) {
            case 0://尖尖向上改为尖尖向右//需要检测（1,2）有没有物体
                if (y + 2 > frame.getYRange() - 1) return form;
                if (idBlocks[x + 1][y + 2] != 0) return form;
                Storage.clear();
                x += 1;//修改这个形状的起点x
                Block block1 = new Block(x + 1, y + 1, id);
                Storage.add(block1);
                for (int i = 0; i < 3; i++) {
                    Block block = new Block(x, y + i, id);
                    Storage.add(block);
                }
                break;
            case 1://尖尖向右改为尖尖向下//需要检测（0,1）有没有物体
                if (x - 1 < 0) return form;
                if (idBlocks[x - 1][y + 1] != 0) return form;
                Storage.clear();
                x -= 1;
                y += 1;//修改起点
                Block block2 = new Block(x + 1, y + 1, id);
                Storage.add(block2);
                for (int i = 0; i < 3; i++) {
                    Block block = new Block(x + i, y, id);
                    Storage.add(block);
                }
                break;
            case 2://尖尖向下改为尖尖向左//需要检测（1,0）有没有物体
                if (y - 1 < 0) return form;
                if (idBlocks[x + 1][y - 1] != 0) return form;
                Storage.clear();
                y -= 1;
                Block block3 = new Block(x, y + 1, id);
                Storage.add(block3);
                for (int i = 0; i < 3; i++) {
                    Block block = new Block(x + 1, y + i, id);
                    Storage.add(block);
                }
                break;
            case 3://尖尖向左改为尖尖向上//需要检测（2,1）有没有物体
                if (x + 2 > frame.getXRange() - 1) return form;
                if (idBlocks[x + 2][y + 1] != 0) return form;
                Storage.clear();
                Block block4 = new Block(x + 1, y, id);
                Storage.add(block4);
                for (int i = 0; i < 3; i++) {
                    Block block = new Block(x + i, y + 1, id);
                    Storage.add(block);
                }
                break;
            default:
                break;
        }
        form += 1;
        return (form % 4);
    }

    private int TransformZ(int form, int[][] idBlocks) {
        switch (form) {
            case 0://横向Z改为纵向Z//需要检测（0,1）和（0,2）有没有物体
                if (y + 2 > frame.getYRange() - 1) return form;
                if (idBlocks[x][y + 1] != 0 || idBlocks[x][y + 2] != 0) return form;
                Storage.clear();
                //(1,0)(1,1)(0,1)(0,2)
                for (int i = 0; i < 2; i++) {
                    for (int j = 1; j < 3; j++) {
                        Block block = new Block(x + i, y + j - i, id);
                        Storage.add(block);
                    }
                }
                break;
            case 1://纵向Z-->横向Z//需要检测（0,0）和（1,2）有没有物体
                if (x + 2 > frame.getXRange() - 1) return form;
                if (idBlocks[x][y] != 0 || idBlocks[x + 1][y + 2] != 0) return form;
                Storage.clear();
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        Block block = new Block(x + i + j, y + j, id);
                        Storage.add(block);
                    }
                }
                break;
        }
        form += 1;
        return (form % 2);
    }

    private int TransformL(int form, int[][] idBlocks) {
        switch (form) {
            case 3: {//form3-->0   (1,0)(1,1)(1,2)(2,2)
                if (y - 1 < 0) return form;
                if (idBlocks[x + 1][y - 1] != 0 || idBlocks[x + 1][y + 1] != 0 || idBlocks[x + 2][y + 1] != 0)
                    return form;
                Storage.clear();
                x += 1;
                y -= 1;
                for (int i = 0; i < 3; i++) {
                    Block block = new Block(x, y + i, id);
                    Storage.add(block);
                }
                Block block = new Block(x + 1, y + 2, id);
                Storage.add(block);
                break;
            }
            case 0: {//form0-->1   (0,1)(1,1)(2,1)(2,0)
                if (x - 1 < 0) return form;
                if (idBlocks[x - 1][y + 1] != 0 || idBlocks[x + 1][y + 1] != 0 || idBlocks[x + 1][y] != 0) return form;
                Storage.clear();
                x -= 1;
                for (int i = 0; i < 3; i++) {
                    Block block = new Block(x + i, y + 1, id);
                    Storage.add(block);
                }
                Block block = new Block(x + 2, y, id);
                Storage.add(block);
                break;
            }
            case 1: {//form1-->2   (0,0)(1,0)(1,1)(1,2)
                if (y + 2 > frame.getYRange() - 1) return form;
                if (idBlocks[x][y] != 0 || idBlocks[x + 1][y] != 0 || idBlocks[x + 1][y + 2] != 0) return form;
                Storage.clear();
                Block block1 = new Block(x, y, id);
                Storage.add(block1);
                for (int i = 0; i < 3; i++) {
                    Block block2 = new Block(x + 1, y + i, id);
                    Storage.add(block2);
                }
                break;
            }
            case 2: {//form2-->3   (0,1)(1,1)(2,1)(0,2)
                if (idBlocks[x][y + 1] != 0 || idBlocks[x][y + 2] != 0 || idBlocks[x + 2][y + 1] != 0) return form;
                Storage.clear();
                y += 1;
                for (int i = 0; i < 3; i++) {
                    Block block2 = new Block(x + i, y, id);
                    Storage.add(block2);
                }
                Block block2 = new Block(x, y + 1, id);
                Storage.add(block2);
                break;
            }
        }
        form += 1;
        return (form % 4);
    }

    private int TransformI(int form, int[][] idBlocks) {
        switch (form) {
            case 0: {//from0-->1 x-=1,y+=1 (0,0)(1,0)(2,0)(3,0)
                if (x - 1 < 0 || x + 2 > frame.getXRange() - 1) return form;
                if (idBlocks[x - 1][y + 1] != 0 || idBlocks[x + 1][y + 1] != 0 || idBlocks[x + 2][y + 1] != 0)
                    return form;
                Storage.clear();
                x -= 1;
                y += 1;
                for (int i = 0; i < 4; i++) {
                    Block block = new Block(x + i, y, id);
                    Storage.add(block);
                }
                break;
            }
            case 1: {//form1-->0 x+=1,y-=1 (0,0)(0,1)(0,2)(0,3)
                if (y - 1 < 0 || y + 2 > frame.getYRange() - 1) return form;
                if (idBlocks[x + 1][y - 1] != 0 || idBlocks[x + 1][y + 1] != 0 || idBlocks[x + 1][y + 2] != 0)
                    return form;
                Storage.clear();
                x += 1;
                y -= 1;
                for (int i = 0; i < 4; i++) {
                    Block block = new Block(x, y + i, id);
                    Storage.add(block);
                }
                break;
            }
        }
        form += 1;
        return (form % 2);
    }

    /**
     * New thoughts
     * move down
     */
    public void MoveDown() {
        /**
         * check if blocks are isolated
         * and put them in 2 different arrays
         */
        ArrayList<Block> BoundArray = new ArrayList<>();
        ArrayList<Block> IsolatedArray = new ArrayList<>();
        setBoundIsolate(BoundArray, IsolatedArray);
        /**
         *  for Bound-Blocks, drop them under the consideration of all blocks
         *  for Isolated-Blocks, drop them only by considering themselves
         */
        if (CheckBoundDroable(BoundArray, frame.getIDBlocks())) DropBlocks(BoundArray);
        boolean[] IsolatedDropable;
        IsolatedDropable = CheckIsolatedBlocks(IsolatedArray, frame.getIDBlocks());
        DropBlocks(IsolatedDropable, IsolatedArray);
    }

    private void setBoundIsolate(ArrayList<Block> BoundArray, ArrayList<Block> IsolatedArray) {
        for (int i = 0; i < Storage.size(); i++) {
            Block checking = Storage.get(i);
            boolean bound = false;
            for (int j = 0; j < Storage.size(); j++) {
                if (i == j) continue;
                else {
                    Block refering = Storage.get(j);
                    if (Math.abs(checking.getX() - refering.getX()) + Math.abs(checking.getY() - refering.getY()) < 2) {
                        bound = true;
                    }
                }
            }
            if (bound) BoundArray.add(checking);
            else IsolatedArray.add(checking);
        }
    }

    private boolean CheckBoundDroable(ArrayList<Block> BoundArray, int[][] idGrid) {
        /**
         *  check blocks (with maximum y under the same x) if they can drop
         */
        ArrayList<Integer> xList = new ArrayList<>();
        HashMap<Integer, Integer> MaxY = new HashMap<>();
        for (int i = 0; i < BoundArray.size(); i++) {
            Block block = BoundArray.get(i);
            if (MaxY.containsKey(block.getX())) {
                if (MaxY.get(block.getX()) < block.getY())
                    MaxY.replace(block.getX(), block.getY());
            } else {
                xList.add(block.getX());
                MaxY.put(block.getX(), block.getY());
            }
        }
        for (int i = 0; i < xList.size(); i++) {
            int y = MaxY.get(xList.get(i));
            if (y + 1 > frame.getYRange() - 1) {
                Droping = false;
                return false;
            }
            if (idGrid[xList.get(i)][y + 1] != 0) {
                Droping = false;
                return false;
            }
        }
        return true;
    }

    private boolean[] CheckIsolatedBlocks(ArrayList<Block> IsolatedArray, int[][] idGrid) {
        /**
         *  check each isolated blocks
         */
        boolean[] dropable = new boolean[IsolatedArray.size()];
        for (int i = 0; i < IsolatedArray.size(); i++) {
            Block block = IsolatedArray.get(i);
            if (block.getY() + 1 > frame.getYRange() - 1) dropable[i] = false;
            else if (idGrid[block.getX()][block.getY() + 1] != 0) dropable[i] = false;
            else dropable[i] = true;
        }
        return dropable;
    }

    private void DropBlocks(ArrayList<Block> Array) {
        for (int i = 0; i < Array.size(); i++) {
            Block block = Array.get(i);
            block.DropStep();
        }
        y += 1;
    }

    private void DropBlocks(boolean[] isDropable, ArrayList<Block> Array) {
        for (int i = 0; i < Array.size(); i++) {
            if (isDropable[i]) {
                Block block = Array.get(i);
                block.DropStep();
            }
        }
    }

    /**
     * New thoughts
     * move horizontally
     */
    public void MoveHorizontally(String direction) {
        if (CheckHorizontallyMovable(direction)) {
            MoveHorizontalStep(direction);
        }
    }

    private boolean CheckHorizontallyMovable(String direction) {
        switch (direction) {
            case "left": {
                ArrayList<Integer> ylist = new ArrayList<>();
                HashMap<Integer, Integer> MinX = new HashMap<>();
                for (int i = 0; i < Storage.size(); i++) {
                    Block block = Storage.get(i);
                    if (MinX.containsKey(block.getY())) {
                        if (MinX.get(block.getY()) > block.getX()) {
                            MinX.replace(block.getY(), block.getX());
                        }
                    } else {
                        MinX.put(block.getY(), block.getX());
                        ylist.add(block.getY());
                    }
                }
                /**
                 * 判断最小x值
                 */
                for (int i = 0; i < ylist.size(); i++) {
                    if (MinX.get(ylist.get(i)) - 1 < 0) return false;
                    else if (frame.getIDBlocks()[MinX.get(ylist.get(i)) - 1][ylist.get(i)] != 0) return false;
                }
                return true;
            }
            case "right": {
                ArrayList<Integer> ylist = new ArrayList<>();
                HashMap<Integer, Integer> MaxX = new HashMap<>();
                for (int i = 0; i < Storage.size(); i++) {
                    Block block = Storage.get(i);
                    if (MaxX.containsKey(block.getY())) {
                        if (MaxX.get(block.getY()) < block.getX()) {
                            MaxX.replace(block.getY(), block.getX());
                        }
                    } else {
                        MaxX.put(block.getY(), block.getX());
                        ylist.add(block.getY());
                    }
                }
                for (int i = 0; i < ylist.size(); i++) {
                    int y = ylist.get(i);
                    int x = MaxX.get(y) + 1;
                    if (x > (frame.getXRange() - 1)) {
                        return false;
                    } else if (frame.getIDBlocks()[x][y] != 0) {
                        return false;
                    }
                }
                return true;
            }
            default:
                break;
        }
        return false;
    }

    private void MoveHorizontalStep(String direction) {
        int increment = 1;
        if (direction.equals("left")) {
            x -= 1;
            increment = -1;
        } else if (direction.equals("right")) {
            x += 1;
            increment = 1;
        }
        for (int i = 0; i < Storage.size(); i++) {
            Block block = Storage.get(i);
            block.setX(block.getX() + increment);
        }
    }

    /**
     * New thoughts
     * Eliminate
     */
    public int Eliminate(ArrayList<Integer> LineY) {
        int size = Storage.size();
        for (int i = 0; i < size; i++) {
            Block block = Storage.get(i);
            if (LineY.contains(block.getY())) {
                Storage.remove(i);
                size -= 1;
                i -= 1;
            }
        }
        return Storage.size();
    }

    /**
     *  getters
     */
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<Block> getStorage() {
        return Storage;
    }

    public boolean isDroping() {
        return Droping;
    }

    public int getId() {
        return id;
    }

    public Main getFrame() {
        return frame;
    }
}
