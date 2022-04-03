import java.util.TimerTask;
import game_objects.*;

public class Tick extends TimerTask {
    public void run() {
        if(Math.random() > 0.5) {
            Car testc = new Car((int)(Math.random()*40)*30, (int)(Math.random()*22)*30);
            if (Main.grid[testc.getX()/30][testc.getY()/30] instanceof GameObject){
                //spawn
                Main.carsOnRoad.add(testc);
            }
        }
        //Tree t1 = new Tree((int)(Math.random()*40)*30, (int)(Math.random()*22)*30);
        //if (!(Main.grid[testc.getX()/30][testc.getY()/30] instanceof GameObject)){
            //Main.treesInCity.add(t1);
        //}
        
        for(int i = 0; i < Main.carsOnRoad.size(); i++) {
            Car car = Main.carsOnRoad.get(i);
            if(car.getPath().size() == 0) {
				int x = (int)(Math.random()*40)*30;
                int y = (int)(Math.random()*22)*30;
                if(x != car.getX() || y != car.getY() && Main.grid[x/30][y/30] instanceof GameObject) {
				    car.setPath(Main.pathfind2(car, Main.grid[car.getX()/30][car.getY()/30], Main.grid[x/30][y/30]));
				    if(car.getPath().size() == 0) {
					    Main.carsOnRoad.remove(i);
                        i--;
                        continue;
				    }
                }
                else {
                    Main.carsOnRoad.remove(i);
                    i--;
                    continue;
                }
			}
            //Moving to the right
            if(car.getPath().get(car.getIndex() + 1).getX() > car.getX()) {
                car.setX(car.getX() + 5);
            }
            //Moving left
            if(car.getPath().get(car.getIndex() + 1).getX() < car.getX()) {
                car.setX(car.getX() - 5);
            }
            //Moving up
            if(car.getPath().get(car.getIndex() + 1).getY() < car.getY()) {
                car.setY(car.getY() - 5);
            }
            //Moving down
            if(car.getPath().get(car.getIndex() + 1).getY() > car.getY()) {
                car.setY(car.getY() + 5);
            }

            if(car.getPath().get(car.getIndex() + 1).getY() == car.getY() && car.getPath().get(car.getIndex() + 1).getX() == car.getX()) {
                car.moved();
            }
            if(car.getIndex() == car.getPath().size()) {
                Main.carsOnRoad.remove(car);
            }
        }
    }
}