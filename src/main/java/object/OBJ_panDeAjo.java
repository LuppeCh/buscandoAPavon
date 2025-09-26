package object;

import entity.Entity;
import main.gamePanel;

public class OBJ_panDeAjo extends Entity {


    public OBJ_panDeAjo(gamePanel gp) {
        super(gp);
        name = "Pan de Ajo";
        down1 = setup("/Objects/panDeAjo");
    }
}
