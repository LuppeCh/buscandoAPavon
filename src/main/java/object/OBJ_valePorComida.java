package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_valePorComida extends SuperObject{

    public OBJ_valePorComida() {
        name = "Vale por comida";
        try{

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Objects/valeComida.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
