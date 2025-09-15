package main;

import object.OBJ_gpsNave;
import object.OBJ_panDeAjo;
import object.OBJ_valePorComida;

public class AssetSetter {
    gamePanel gp;
    public AssetSetter(gamePanel gp){
        this.gp =gp;
    }
    public void setObject() {

        gp.obj[0] = new OBJ_panDeAjo(gp);// se crea el objeto
        gp.obj[0].worldX = 31 * gp.tileSize; // se coloca el objeto en la posicion 23 del eje X.A modificar. y con el tamano de tileSize
        gp.obj[0].worldY = 11 * gp.tileSize; // se coloca el objeto en la posicion 7 del eje Y. A modifica. y con el tamano de tileSize.

        gp.obj[1] = new OBJ_gpsNave(gp);// se crea el objeto
        gp.obj[1].worldX = 33 * gp.tileSize; // se coloca el objeto en la posicion 23 del eje X.A modificar. y con el tamano de tileSize
        gp.obj[1].worldY = 11 * gp.tileSize; // se coloca el objeto en la posicion 7 del eje Y. A modifica. y con el tamano de tileSize.

        gp.obj[2] = new OBJ_valePorComida(gp);// se crea el objeto
        gp.obj[2].worldX = 39 * gp.tileSize; // se coloca el objeto en la posicion 23 del eje X.A modificar. y con el tamano de tileSize
        gp.obj[2].worldY = 24 * gp.tileSize; // se coloca el objeto en la posicion 7 del eje Y. A modifica. y con el tamano de tileSize.

    }
}
