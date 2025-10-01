package object;

import entity.Entity;
import main.gamePanel;

public class OBJ_panDeAjo extends Entity {

    public OBJ_panDeAjo(gamePanel gp) {
        super(gp);
        name = "Pan de Ajo";
        descripcion = "[" + name + "] \nUna comida no muy sabrosa... \nQuién sabe... \nQuizás no sos el unico que la odia.";
        down1 = setup("/Objects/panDeAjo");

    }
}
