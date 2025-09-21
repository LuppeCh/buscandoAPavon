package object;

import entity.Entity;
import main.gamePanel;

public class OBJ_gpsNave extends Entity {


    public OBJ_gpsNave(gamePanel gp) {
        super(gp);
        name = "GPS nave";
        down1 = setup("/Objects/GPS");
    }
}
