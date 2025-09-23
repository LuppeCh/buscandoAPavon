package object;

import main.gamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_gpsNave extends SuperObject {

    gamePanel gp;

    public OBJ_gpsNave(gamePanel gp) {
        this.gp = gp;
        name = "GPS nave";

        // descripcion = "[" + name + "] \nUn GPS que localiza la nave alienigena"
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Objects/GPS.png")));
            uTool.scaleImage (image, gp.tileSize, gp.tileSize);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
