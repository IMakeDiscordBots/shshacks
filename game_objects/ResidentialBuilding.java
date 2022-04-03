package game_objects;
public class ResidentialBuilding extends GameObject{ 
    
    private int a, b;
    private static int population;
    private static int numBuild;
    private static int pollution;

    public ResidentialBuilding(int x, int y){
        super(x, y);
        a = x;
        b = y;
        population+=10;
        numBuild++;
        pollution+=20;
    }

    public void incPop(int x){
        population+=x*numBuild;
    }

    public int getPop(){
        return population;
    }

    public int getPollution(){
        return pollution;
    }

    public static int getBuilt(){
        return numBuild;
    }
}