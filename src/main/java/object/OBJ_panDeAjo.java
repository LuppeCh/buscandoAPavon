package object;

import main.gamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_panDeAjo extends SuperObject{

    gamePanel gp;

    public OBJ_panDeAjo(gamePanel gp) {
        this.gp = gp;
        name = "Pan de Ajo";

        // descripcion = "[" + name + "] \nUna comida no muy sabrosa... \nQuien sabe... \nQuisas no sos el unico que la odia."
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/Objects/panDeAjo.png"));
            uTool.scaleImage (image, gp.tileSize, gp.tileSize);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
