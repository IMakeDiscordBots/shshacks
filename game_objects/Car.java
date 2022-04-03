package game_objects;
public class Car extends GameObject {
    private int a, b;
    //private boolean visible = false;
    private boolean visible;

    public Car(int x, int y){
        super(x, y);
        a = x;
        b = y;
        visible = true;
    }
    
    public void moveL(){
        a-=30;
        if (a<0 || a>1140 || b<0 || b>600){
        visible = false;
    }
    }

    public void moveR(){
        a += 30;
        if (a<0 || a>1140 || b<0 || b>600){
        visible = false;
    }
    }

    public void moveU(){
        b += 30;
        if (a<0 || a>1140 || b<0 || b>600){
        visible = false;
    }
    }

    public void moveD(){
        b -= 30;
        if (a<0 || a>1140 || b<0 || b>600){
        visible = false;
    }
    }

    

}