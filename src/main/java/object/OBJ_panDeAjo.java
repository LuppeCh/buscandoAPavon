package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_panDeAjo extends SuperObject{

    public OBJ_panDeAjo() {
        name = "Pan de Ajo";
        try{

            image = ImageIO.read(getClass().getResourceAsStream("/Objects/panDeAjo.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
