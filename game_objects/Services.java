package game_objects;
public class Services extends GameObject{  //happy, pollution
    

    private static int happiness=0;
    private static int numBuild=0;
    private static int pollution=0;

    public Services(int x, int y, int c){
        super(x, y);
        happiness+=10;
        numBuild++;
        pollution+=c;
    }

    public static int getHap(){
        return happiness;
    }

    public static int getPollution(){
        return pollution;
    }

    public static int getBuilt(){
        return numBuild;
    }
}