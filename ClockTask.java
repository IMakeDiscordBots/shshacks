//import java.text.DecimalFormat;
import java.util.TimerTask;

class ClockTask extends TimerTask {
    public void run() {
        if(Main.money < 100000) {
            Main.money += 50;
        } else {
            Main.money = 100000;
        }
        
        System.out.println(Main.money);
    }
} 