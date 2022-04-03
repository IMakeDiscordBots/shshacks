package game_objects; //make spawn randomly  in places with no buildings
public class Tree extends GameObject{ //pollution
    private static int numBuild=0;
    private static int pollution=0;
    private State state;

    public Tree(int x, int y){
        super(x, y);
        numBuild++;
        state = new State(0);
        pollution -= 15;
    }
    public Tree(int x, int y, State s) {
        super(x, y);
       
       
        numBuild++;
        
        state = s;
        pollution-=15;
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