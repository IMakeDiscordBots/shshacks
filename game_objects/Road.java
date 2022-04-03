package game_objects;

import javax.swing.ImageIcon;
import java.awt.*;

public class Road extends GameObject {
    public static final int roadRotation = 0;
	private static int pollution = 10;
    private RoadState state;
    
    public Road(int x, int y) {
        super(x, y);
        state = new RoadState(0);
    }
    public Road(int x, int y, int i) {
        super(x, y);
        state = new RoadState(i);
    }
    public int getIndex() {
        return state.getIndex();
    }
    public void setIndex(int i) {
        state.setIndex(i);
    }
    // updating road type of placed and neighboring road
    public void changeIndexes(int coordX, int coordY, GameObject[][] g) {
        ((Road)g[coordX][coordY]).setIndex(determine(coordX, coordY, g));
        if(coordX - 30 >= 0) {
            try {
                ((Road)g[coordX - 30][coordY]).setIndex(determine(coordX - 30, coordY, g));
            }
            catch(Exception e) {}
        }
        if(coordX + 30 <= 1140) {
            try {
                ((Road)g[coordX + 30][coordY]).setIndex(determine(coordX + 30, coordY, g));
            }
            catch(Exception e) {}
        }
        if(coordY - 30 >= 0) {
            try {
                ((Road)g[coordX][coordY - 30]).setIndex(determine(coordX, coordY - 30, g));
            }
            catch(Exception e) {}
        }
        if(coordY + 30 <= 600) {
            try {
                ((Road)g[coordX][coordY + 30]).setIndex(determine(coordX, coordY - 30, g));
            }
            catch(Exception e) {}
        }
    }
    public int determine(int coordX, int coordY, GameObject[][] g) {
        int index = 0;
        int connections = 0;
        boolean left = false;
        boolean right = false;
        boolean up = false;
        boolean down = false;
        if(coordX - 30 >= 0) {
            if(g[coordX - 30 - (coordX % 30)][coordY] instanceof Road) {
                connections++;
                left = true;
            }
        }
        if(coordX + 30 <= 1140) {
            if(g[coordX + 30 - (coordX % 30)][coordY] instanceof Road) {
                connections++;
                right = true;
            }
        }
        if(coordY - 30 >= 0) {
            if(g[coordX][coordY - 30 - (coordY % 30)] instanceof Road) {
                connections++;
                up = true;
            }
        }
        if(coordY + 30 <= 600) {
            if(g[coordX][coordY + 30 - (coordY % 30)] instanceof Road) {
                connections++;
                down = true;
            }
        }
        if(connections == 1) {
            if(up || down) {
                index = 0;
            }
            else if(left || right) {
                index = 1;
            }
        }
        else if(connections == 2) {
            if(up && right) {
                index = 3;
            } else if(left && up) {
                index = 2;
            } else if(down && right) {
                index = 4;
            } else if(down && left) {
                index = 5;
            } else {
                if(up && down) {
                    index = 0;
                } else if(left && right) {
                    index = 1;
                }
            }
        }
        else if(connections == 3) {
            if(!up) {
                index = 6;
            } else if(!left) {
                index = 8;
            } else if(!down) {
                index = 7;
            } else {
                index = 9;
            }
        }
        else if(connections == 4) {
            index = 10;
        }
        // 0 - up, 1 - side, 2 - leftUp, 3 - rightUp, 4 - rightDown, 5 - leftDown, 6 -
	    // threeDown, 7 - threeUp, 8 - threeRight, 9 - threeLeft, 10 - fourway
        return index;
    }
}