package game_objects;
public class Energy extends GameObject{ //pollution, happy, energy
    
    private int a, b;
    private static int energy;
    private static int numBuild;
    private static int pollution;
    private static int happiness;

    public Energy(int x, int y, int e, int c, int d){
        super(x, y);
        a = x;
        b = y;
        energy+=e;
        numBuild++;
        pollution+=c;
        happiness+=d;
    }

    public int getEnergy(int x){
        return energy;
    }

    

    public int getPollution(){
        return pollution;
    }
    public int getHappy(){
        return happiness;
    }

    public static int getBuilt(){
        return numBuild;
    }
}