package game_objects;

import javax.swing.ImageIcon;
import java.awt.*;

public class Road extends GameObject {
    private static int pollution = 20;
    private RoadState state;
    
    public Road(int x, int y) {
        super(x, y);
    }
    public Road(int x, int y, RoadState r) {
        super(x, y);
        state = r;
    }
    public int getIndex() {
        return state.getIndex();
    }
    public void setIndex(int i) {
        state.setIndex(i);
    }
}