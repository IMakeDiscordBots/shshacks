package game_objects;
public class GameObject { 
    private int[] pos = new int[2];
    public GameObject(int x, int y){
        pos[0] = x;
        pos[1] = y;
    }

    public int getX(){
        return pos[0];
    }
    public int getY(){
        return pos[1];
    }

    public void setX(int x){
        pos[0] = x;
    }
    public void setY(int y){
        pos[1] = y;
    }
}
