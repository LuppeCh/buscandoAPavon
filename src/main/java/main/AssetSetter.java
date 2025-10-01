package main;

import entity.NPC_Pavon;
import object.OBJ_gpsNave;
import object.OBJ_panDeAjo;
import object.OBJ_valePorComida;
import main.gamePanel;

public class AssetSetter {
    gamePanel gp;
    public AssetSetter(gamePanel gp) {
        this.gp =gp;
    }

    public void setObject() {

        int mapNum = 0; // cambiamosmas abajo el numero de mapa y agregamos los objetos/npcs
        gp.obj[mapNum][0] = new OBJ_panDeAjo(gp);// se crea el objeto
        gp.obj[mapNum][0].worldX = 31 * gp.tileSize; // se coloca el objeto en la posicion 31 del eje X.A modificar.
        gp.obj[mapNum][0].worldY = 31 * gp.tileSize; // se coloca el objeto en la posicion 11 del eje Y. A modifica.
        gp.obj[mapNum][0].mapIndex = mapNum;

//        gp.obj[mapNum][1] = new OBJ_gpsNave(gp);// se crea el objeto
//        gp.obj[mapNum][1].worldX = 33 * gp.tileSize; // se coloca el objeto en la posicion 33 del eje X.A modificar.
//        gp.obj[mapNum][1].worldY = 11 * gp.tileSize; // se coloca el objeto en la posicion 11 del eje Y. A modifica.
//
//        gp.obj[mapNum][2] = new OBJ_valePorComida(gp);// se crea el objeto
//        gp.obj[mapNum][2].worldX = 39 * gp.tileSize; // se coloca el objeto en la posicion 39 del eje X.A modificar.
//        gp.obj[mapNum][2].worldY = 24 * gp.tileSize; // se coloca el objeto en la posicion 24 del eje Y. A modifica.
    }
    public void setNPC() {

        int mapNum = 0;
//        gp.npc[mapNum][0] = new NPC_Pavon(gp); // se crea el npc
//        gp.npc[mapNum][0].worldX = 24 * gp.tileSize; // se coloca el NPC en la posicion 32 del eje X.A modificar.
//        gp.npc[mapNum][0].worldY = 24 * gp.tileSize; // se coloca el NPC en la posicion 11 del eje Y.A modificar.
//
//        gp.npc[mapNum][1] = new NPC_Pavon(gp); // se crea el npc
//        gp.npc[mapNum][1].worldX = 22 * gp.tileSize; // se coloca el NPC en la posicion 32 del eje X.A modificar.
//        gp.npc[mapNum][1].worldY = 24 * gp.tileSize; // se coloca el NPC en la posicion 11 del eje Y.A modificar.
//
//        gp.npc[mapNum][2] = new NPC_Pavon(gp); // se crea el npc
//        gp.npc[mapNum][2].worldX = 23 * gp.tileSize; // se coloca el NPC en la posicion 32 del eje X.A modificar.
//        gp.npc[mapNum][2].worldY = 24 * gp.tileSize; // se coloca el NPC en la posicion 11 del eje Y.A modificar.

        mapNum = 1;
        gp.npc[mapNum][0] = new NPC_Pavon(gp); // se crea el npc
        gp.npc[mapNum][0].worldX = 45 * gp.tileSize; // se coloca el NPC en la posicion 32 del eje X.A modificar.
        gp.npc[mapNum][0].worldY = 4 * gp.tileSize; // se coloca el NPC en la posicion 11 del eje Y.A modificar.
        gp.npc[mapNum][0].mapIndex = mapNum;
    }
}
