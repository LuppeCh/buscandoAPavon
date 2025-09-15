package object;

import main.gamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_valePorComida extends SuperObject{

    gamePanel gp;

    public OBJ_valePorComida(gamePanel gp) {
        this.gp = gp;
        name = "Vale por comida";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Objects/valeComida.png")));
            uTool.scaleImage (image, gp.tileSize, gp.tileSize);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
