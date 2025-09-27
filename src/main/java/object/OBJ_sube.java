package object;

import entity.Entity;
import main.gamePanel;


public class OBJ_sube extends Entity {

    public OBJ_sube(gamePanel gp) {
        super(gp);
        name = "SUBE";
        // descripcion = "[" + name + "] \nUna comida no muy sabrosa... \nQuien sabe... \nQuisas no sos el unico que la odia."
        down1 = setup("/Objects/SUBE");
    }

}
