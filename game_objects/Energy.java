package game_objects; //unused in final project, placeholder
public class Energy extends GameObject{ //pollution, happy, energy
    

    private static int energy;
    private static int numBuild;
    private static int pollution;
    private static int happiness;

    public Energy(int x, int y, int e, int c, int d){
        super(x, y);

        energy+=e;
        numBuild++;
        pollution+=c;
        happiness+=d;
    }

    public int getEnergy(int x){
        return energy;
    }

    

    public static int getPollution(){
        return pollution;
    }
    public static int getHap(){
        return happiness;
    }

    public static int getBuilt(){
        return numBuild;
    }
}