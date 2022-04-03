package game_objects;
public class CommBuild extends GameObject{ //happy, pollution
    
    private int a, b;
    private static int happiness;
    private static int numBuild;
    private static int pollution;
    private static int revenue;
    private boolean visible = false;

    public CommBuild(int x, int y){
        super(x, y);
        a = x;
        b = y;
        happiness+=15;
        numBuild++;
        pollution+=30;
        visible = true;
        revenue+=5;
    }

    public int getHap(int x){
        return happiness;
    }

    

    public static int getPollution(){
        return pollution;
    }

    public static int getBuilt(){
        return numBuild;
    }

    public void remove(){
        visible = false;
    }
    public static int getRev(){
        return revenue;
    }

}