import java.util.TimerTask;
import game_objects.*;

class ClockTask extends TimerTask {
    public void run() {

        if(Main.money < 100000) {
            Main.money += 50+CommercialBuilding.getRevenue();
        } else {
            Main.money = 100000;
        }
        
        

        //incrementing values per tick
        Main.pollution = Services.getPollution() + Road.getPollution() + ResidentialBuilding.getPollution() + Tree.getPollution() + Energy.getPollution() + CommercialBuilding.getPollution();
        

        if (ResidentialBuilding.getBuilt()/10!=0){
            Main.population*=(1+(double)(ResidentialBuilding.getBuilt())/4);
        }
        
        Main.population-=(1+ResidentialBuilding.getBuilt()/5);


        Main.happy = 100+CommercialBuilding.getHap()+Energy.getHap()+Services.getHap()-(Main.pollution/3);
        

        //end conditions
        if (Main.pollution>500){
            Main.end=true;
        }
        if (Main.population<=0){
            Main.end=true;
        }
        

    }
} 