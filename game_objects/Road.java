package game_objects;

//import javax.swing.ImageIcon;
//import java.awt.*;

public class Road extends GameObject {
    public static final int roadRotation = 0;
	private static int pollution = 10;
    private State state;
    private Node node;
    
    public Road(int x, int y) {
        super(x, y);
        state = new State(0);
        node = new Node(this);
    }
    public Road(int x, int y, int i) {
        super(x, y);
        state = new State(i);
        node = new Node(this);
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
        if(coordX - 1 >= 0) {
            try {
                ((Road)g[(coordX - 1)][coordY]).setIndex(determine(coordX - 1, coordY, g));
            }
            catch(Exception e) {}
        }
        if(coordX + 1 < 38) {
            try {
                ((Road)g[coordX + 1][coordY]).setIndex(determine(coordX + 1, coordY, g));
            }
            catch(Exception e) {}
        }
        if(coordY - 1 >= 0) {
            try {
                ((Road)g[coordX][coordY - 1]).setIndex(determine(coordX, coordY - 1, g));
            }
            catch(Exception e) {}
        }
        if(coordY + 1 < 20) {
            try {
                ((Road)g[coordX][coordY + 1]).setIndex(determine(coordX, coordY + 1, g));
            }
            catch(Exception e) {}
        }
    }
    public int determine(int coordX, int coordY, GameObject[][] g) {
        if(!(g[coordX][coordY] instanceof Road)) {
            return 0;
        }
        int index = 0;
        int connections = 0;
        boolean left = false;
        boolean right = false;
        boolean up = false;
        boolean down = false;
        if(coordX - 1 >= 0) {
            if(g[coordX - 1][coordY] instanceof Road) {
                connections++;
                left = true;
                ((Road)g[coordX][coordY]).getNode().setLeft(((Road)g[coordX - 1][coordY]).getNode());
                ((Road)g[coordX - 1][coordY]).getNode().setRight(((Road)g[coordX][coordY]).getNode());
                if (left){
                    System.out.print("Left");
                }
            }
        }
        if(coordX + 1 < 38) {
            if(g[coordX + 1][coordY] instanceof Road) {
                connections++;
                right = true;
                ((Road)g[coordX][coordY]).getNode().setRight(((Road)g[coordX + 1][coordY]).getNode());
                ((Road)g[coordX + 1][coordY]).getNode().setLeft(((Road)g[coordX][coordY]).getNode());
                if (right) {
                    System.out.print("Right");
                }
            }
        }
        if(coordY - 1 >= 0) {
            if(g[coordX][coordY - 1] instanceof Road) {
                connections++;
                up = true;
                ((Road)g[coordX][coordY]).getNode().setTop(((Road)g[coordX][coordY - 1]).getNode());
                ((Road)g[coordX][coordY - 1]).getNode().setDown(((Road)g[coordX][coordY]).getNode());
                if (up){
                    System.out.print("Up");
                }
            }
        }
        if(coordY + 1 < 20) {
            if(g[coordX][coordY + 1] instanceof Road) {
                connections++;
                down = true;
                ((Road)g[coordX][coordY]).getNode().setDown(((Road)g[coordX][coordY + 1]).getNode());
                ((Road)g[coordX][coordY + 1]).getNode().setTop(((Road)g[coordX][coordY]).getNode());
                if (down){
                    System.out.print("Down");
                }
            }
        }
        if(connections == 1) {
            System.out.print("1 connection");
            if(up || down) {
                index = 0;
            }
            else if(left || right) {
                index = 1;
            }
        }
        else if(connections == 2) {
            System.out.print("2 connection");
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
            System.out.print("3 connection");
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
            System.out.print("4 connection");
            index = 10;
        }
        // 0 - up, 1 - side, 2 - leftUp, 3 - rightUp, 4 - rightDown, 5 - leftDown, 6 -
	    // threeDown, 7 - threeUp, 8 - threeRight, 9 - threeLeft, 10 - fourway
        return index;
    }

    public Node getNode() {
        return node;
    }

    public static int getPollution(){
        return pollution;
    }
    public boolean equals(Road r) {
        if(getX() == r.getX() && getY() == r.getY()) {
            return true;
        }
        return false;
    }
    public String toString() {
        return getX() + " " + getY();
    }
}