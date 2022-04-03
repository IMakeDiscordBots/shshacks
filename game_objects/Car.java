package game_objects;
import java.util.*;
public class Car extends GameObject {
    private int a, b;
    //private boolean visible = false;
    private boolean visible;
    private int pathPositionIndex = 0;
    private ArrayList<Road> path;
    public Car(int x, int y){
        super(x, y);
        a = x;
        b = y;
        visible = true;
        path = new ArrayList<Road>();
    }

    public Car(int x, int y, ArrayList<Road> path){
        super(x, y);
        a = x;
        b = y;
        visible = true;
        this.path = path;
    }
     
    public void moveL(){
        a-=30;
        setX(a);
        if (a<0 || a>1140 || b<0 || b>600){
        visible = false;
    }
    }

    public void moveR(){
        a += 30;
        setX(a);
        if (a<0 || a>1140 || b<0 || b>600){
        visible = false;
    }
    }

    public void moveU(){
        b += 30;
        setY(b);
        if (a<0 || a>1140 || b<0 || b>600){
        visible = false;
    }
    }

    public void moveD(){
        b -= 30;
        setY(b);
        if (a<0 || a>1140 || b<0 || b>600){
        visible = false;
    }
    }

    public void moved() {
        pathPositionIndex++;
    }
    public int getIndex() {
        return pathPositionIndex;
    }

    public void setPath(ArrayList<Road> path) {
        this.path = path;
    }
    public ArrayList<Road> getPath() {
        return path;
    }
}