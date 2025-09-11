package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_gpsNave extends SuperObject {
    public OBJ_gpsNave() {
        name = "GPS nave";
        try{

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Objects/GPS.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
