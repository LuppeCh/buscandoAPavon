package object;

import entity.Entity;
import main.gamePanel;


public class OBJ_sube extends Entity {

    public OBJ_sube(gamePanel gp) {
        super(gp);
        name = "SUBE";
        descripcion = "[" + name + "] \nLa mejor compañera de viaje. \nEvita que tengas que caminar \nlargas distancias.";
        down1 = setup("/Objects/SUBE");
    }

}
