package main;

import entity.NPC_Pavon;
import object.OBJ_gpsNave;
import object.OBJ_panDeAjo;
import object.OBJ_valePorComida;

public class AssetSetter {
    gamePanel gp;
    public AssetSetter(gamePanel gp) {
        this.gp =gp;
    }

    public void setObject() {

//        gp.obj[0] = new OBJ_panDeAjo(gp);// se crea el objeto
//        gp.obj[0].worldX = 5 * gp.tileSize; // se coloca el objeto en la posicion 31 del eje X.A modificar.
//        gp.obj[0].worldY = 5 * gp.tileSize; // se coloca el objeto en la posicion 11 del eje Y. A modifica.
//
//        gp.obj[1] = new OBJ_gpsNave(gp);// se crea el objeto
//        gp.obj[1].worldX = 6 * gp.tileSize; // se coloca el objeto en la posicion 33 del eje X.A modificar.
//        gp.obj[1].worldY = 6 * gp.tileSize; // se coloca el objeto en la posicion 11 del eje Y. A modifica.
//
//        gp.obj[2] = new OBJ_valePorComida(gp);// se crea el objeto
//        gp.obj[2].worldX = 7 * gp.tileSize; // se coloca el objeto en la posicion 39 del eje X.A modificar.
//        gp.obj[2].worldY = 7 * gp.tileSize; // se coloca el objeto en la posicion 24 del eje Y. A modifica.
    }
    public void setNPC() {

        gp.npc[0] = new NPC_Pavon(gp); // se crea el npc
        gp.npc[0].worldX = 10 * gp.tileSize; // se coloca el NPC en la posicion 32 del eje X.A modificar.
        gp.npc[0].worldY = 18 * gp.tileSize; // se coloca el NPC en la posicion 11 del eje Y.A modificar.

        gp.npc[1] = new NPC_Pavon(gp); // se crea el npc
        gp.npc[1].worldX = 12 * gp.tileSize; // se coloca el NPC en la posicion 32 del eje X.A modificar.
        gp.npc[1].worldY = 18 * gp.tileSize; // se coloca el NPC en la posicion 11 del eje Y.A modificar.
    }
}
