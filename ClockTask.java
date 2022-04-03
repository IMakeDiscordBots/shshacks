import java.util.TimerTask;
import game_objects.*;

class ClockTask extends TimerTask {
    public void run() {

        if(Main.money < 100000) {
            Main.money += 50+CommBuild.getRev();
        } else {
            Main.money = 100000;
        }
        
        System.out.println(Main.money);

        
        Main.pollution = Services.getPollution() + Road.getPollution() + ResidentialBuilding.getPollution() + Tree.getPollution() + Energy.getPollution() + CommBuild.getPollution();
        Main.population = ResidentialBuilding.getPop();

        if (ResidentialBuilding.getBuilt()/10!=0){
            Main.population*=(1+(double)(ResidentialBuilding.getBuilt())/10);
        }
        
        Main.population-=(1+ResidentialBuilding.getBuilt()/5);
    

        


        if (Main.pollution>500){
            Main.end=true;
        }
        if (Main.population<=0){
            Main.end=true;
        }
    }
} 