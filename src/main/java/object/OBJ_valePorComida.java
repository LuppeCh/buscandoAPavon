package object;

import entity.Entity;
import main.gamePanel;

public class OBJ_valePorComida extends Entity {

    public OBJ_valePorComida(gamePanel gp) {
        super(gp);
        name = "Vale por comida";
        descripcion = "[" + name + "] \nCupón intercambiable con \n el vendedor del mercado.";
        down1 = setup("/Objects/valeComida");
    }
}
