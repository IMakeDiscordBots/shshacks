package game_objects;
public class Services extends GameObject{  //happy, pollution
    
    private int a, b;
    private static int happiness;
    private static int numBuild;
    private static int pollution;

    public Services(int x, int y, int c){
        super(x, y);
        a = x;
        b = y;
        happiness+=10;
        numBuild++;
        pollution+=c;
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
}