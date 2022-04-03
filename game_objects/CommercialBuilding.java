package game_objects;

public class CommercialBuilding extends GameObject{ //pollution, population
    private int a, b;
    private static int numBuild;
    private static int pollution;
    private int income;
    private State state;
    private boolean high;
    public CommercialBuilding(int x, int y, boolean high){
        super(x, y);
        a = x;
        b = y;
        numBuild++;
        pollution+=20;
        state = new State(0);
        this.high = high;
    }
    public CommercialBuilding(int x, int y, boolean high, State s){
        super(x, y);
        a = x;
        b = y;
        numBuild++;
        pollution+=20;
        state = s;
        this.high = high;
    }

    public boolean isHigh() {
        return high;
    }
    public int getIndex() {
        return state.getIndex();
    }

    public static int getPollution(){
        return pollution;
    }

    public static int getBuilt(){
        return numBuild;
    }

    public int getIncome() {
        return income;
    }
    
    public void setIndex(int i) {
        state.setIndex(i);
    }
}