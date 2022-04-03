package game_objects;

public class CommercialBuilding extends GameObject{ //pollution, population

    private static int numBuild=0;
    private static int pollution=0;
    private static int revenue=0;
    private static int happy=0;
    private State state;
    private boolean high;
    public CommercialBuilding(int x, int y, boolean high){
        super(x, y);

        numBuild++;
       
        state = new State(0);
        this.high = high;
        if (high){
            pollution+=40;
            revenue+=10;
        }
        else{
            pollution+=15;
            revenue+=5;
        }
    }
    public CommercialBuilding(int x, int y, boolean high, State s){
        super(x, y);

        numBuild++;
        happy+=15;
        state = s;
        this.high = high;
        if (high){
            pollution+=40;
            revenue+=10;
        }
        else{
            pollution+=15;
            revenue+=5;
        }
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

    public static int getRevenue() {
        return revenue;
    }
    
    public static int getHap(){
        return happy;
    }

    public void setIndex(int i) {
        state.setIndex(i);
    }
}