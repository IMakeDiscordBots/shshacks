package game_objects;

public class State {
    private int index;
    public State() {}
    public State(int index) {
        this.index = index;
    }
    public void setIndex(int i) {
        index = i;
    }
    public int getIndex() {
        return index;
    }
}