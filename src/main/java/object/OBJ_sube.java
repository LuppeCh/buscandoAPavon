package object;

import main.gamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_sube extends SuperObject {
    gamePanel gp;
    public OBJ_sube(gamePanel gp) {
        this.gp = gp;
        name = "SUBE";

        // descripcion = "[" + name + "] \n Tarjeta para el colectivo"
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Objects/Sube.png")));
            uTool.scaleImage (image, gp.tileSize, gp.tileSize);

        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
