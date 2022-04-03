package game_objects;

import javax.swing.ImageIcon;
import java.awt.*;

public class Road extends GameObject {
    public static final int roadRotation = 0;
	private static int pollution = 20;
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
    // updating road type o
    public void changeIndexes(int coordX, int coordY, GameObject[][] g) {
        g[coordX][coordY].sedetermine(coordX, coordY, g);
        determine(coordX - 30, coordY, g);
        determine(coordX + 30, coordY, g);
        determine(coordX, coordY - 30, g);
        determine(coordX, coordY - 30, g);
    }
    public int determine(int coordX, int coordY, GameObject[][] g) {
        int index = 0;
        int connections = 0;
        boolean left = false;
        boolean right = false;
        boolean up = false;
        boolean down = false;
        if(coordX - 30 >= 0) {
            if(g[coordX - 30][coordY] instanceof Road) {
                connections++;
                left = true;
            }
        }
        if(coordY - 30 >= 0) {
            if(g[coordX][coordY - 30] instanceof Road) {
                connections++;
                up = true;
            }
        }
        if(coordX + 30 <= 1140) {
            if(g[coordX + 30][coordY] instanceof Road) {
                connections++;
                right = true;
            }
        }
        if(coordY + 30 <= 600) {
            if(g[coordX][coordY + 30] instanceof Road) {
                connections++;
                down = true;
            }
        }
        if(connections == 4) {
            index = 10;
        }
        else if(connections == 3) {
            if(!up) {
                index = 9;
            } else if(!left) {
                index = 8;
            } else if(!down) {
                index = 7;
            } else {
                index = 6;
            }
        }
        else if(connections == 2) {
            if(!up && !right) {
                index = 2;
            } else if(!left && !up) {
                index = 4;
            } else if(!down && !left) {
                index = 3;
            } else if(!down && !left) {
                index = 5;
            } else {
                if(!up && !down) {
                    index = 1;
                } else {
                    index = 0;
                }
            }
        }
        else {
            if(up || down) {
                index = 0;
            }
            else {
                index = 1;
            }
        }
        return index;
    }
}