package game_objects;
public class CommBuild extends GameObject{ //happy, pollution
    
    private int a, b;
    private static int happiness;
    private static int numBuild;
    private static int pollution;
    private boolean visible = false;

    public CommBuild(int x, int y){
        super(x, y);
        a = x;
        b = y;
        happiness+=15;
        numBuild++;
        pollution+=30;
        visible = true;
    }

    public int getHap(int x){
        return happiness;
    }

    

    public int getPollution(){
        return pollution;
    }

    public static int getBuilt(){
        return numBuild;
    }

    public void remove(){
        visible = false;
    }

}