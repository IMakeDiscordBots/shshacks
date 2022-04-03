package game_objects;

public class ResidentialBuilding extends GameObject{ //pollution, population
    
    private int a, b;
    private static int population;
    private static int numBuild;
    private static int pollution;
    private State state;
    private boolean high;

    public ResidentialBuilding(int x, int y, boolean high) {
        super(x, y); 
        a = x;
        b = y;
        population+=10;
        numBuild++;
        pollution+=20;
        state = new State(0);
        this.high = high;
    }
    public ResidentialBuilding(int x, int y, boolean high, State s) {
        super(x, y);
        a = x;
        b = y;
        population+=10;
        numBuild++;
        pollution+=20;
        state = s;
        this.high = high;
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