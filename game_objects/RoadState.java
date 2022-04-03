package game_objects;

public class RoadState {
    private int index;
    public RoadState() {}
    public RoadState(int index) {
        this.index = index;
    }
    public void setIndex(int i) {
        index = i;
    }
    public int getIndex() {
        return index;
    }
}