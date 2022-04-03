package game_objects;
public class Tree extends GameObject{ //pollution
    private boolean visible = false;
    private int health;
    private int appearance;
    private static int pollution;

    public Tree(int x, int y){
        super(x, y);
        health = 100;
        visible = true;
        appearance = 0;
        pollution -= 10;
    }
    
    public boolean chop(int b){
        health -= 50;
        if (health <= 0){
            visible = false;
            pollution+=10;
            // make if u chop a tree 50$ u get
        }
        else{
            appearance = 1;
        }
        return visible;
    }
}