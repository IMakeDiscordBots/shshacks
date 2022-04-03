package game_objects;

public class ResidentialBuilding extends GameObject{ //pollution, population
    
    
    private static int population=0;
    private static int numBuild=0;
    private static int pollution=0;
    private State state;
    private boolean high;

    public ResidentialBuilding(int x, int y, boolean high) {
        super(x, y); 
        
        
        numBuild++;
        
        state = new State(0);
        this.high = high;
        if (high){
            //tree
            pollution-=15;
        }
        else{
            pollution+=15;
            population+=10;
        }
    }
    public ResidentialBuilding(int x, int y, boolean high, State s) {
        super(x, y);
       
       
        numBuild++;
        
        state = s;
        this.high = high;
        if (high){
            pollution-=15;//tree btw
        }
        else{
            pollution+=15;
            population+=10;
        }
    }

    public boolean isHigh() {
        return high;
    }

    public void incPop(int x){
        population+=x*numBuild;
    }

    public static int getPop(){
        return population;
    }

    public static int getPollution(){
        return pollution;
    }

    public static int getBuilt(){
        return numBuild;
    }

    public int getIndex() {
        return state.getIndex();
    }
    
    public void setIndex(int i) {
        state.setIndex(i);
    }
}