package main;

import entity.NPC_Aila;
import entity.NPC_LupeMarciana;
import entity.NPC_Panadera;
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
    }


    public void setNPC() {

        int mapNum = 0;
        gp.npc[mapNum][0] = new NPC_Panadera(gp); // se crea el npc
        gp.npc[mapNum][0].worldX = 13 * gp.tileSize; // se coloca el NPC en la posicion 32 del eje X.A modificar.
        gp.npc[mapNum][0].worldY = 5 * gp.tileSize; // se coloca el NPC en la posicion 11 del eje Y.A modificar.
        gp.npc[mapNum][0].mapIndex = mapNum;
        if(!gp.videoMostrado){
            gp.npc[mapNum][1] = new NPC_Pavon(gp); // se crea el npc
            gp.npc[mapNum][1].worldX = 44 * gp.tileSize; // se coloca el NPC en la posicion 32 del eje X.A modificar.
            gp.npc[mapNum][1].worldY = 7 * gp.tileSize; // se coloca el NPC en la posicion 11 del eje Y.A modificar.
            gp.npc[mapNum][1].mapIndex = mapNum;
        }

        mapNum = 1;
        gp.npc[mapNum][2] = new NPC_Aila(gp); // se crea el npc
        gp.npc[mapNum][2].worldX = 18 * gp.tileSize; // se coloca el NPC en la posicion 32 del eje X.A modificar.
        gp.npc[mapNum][2].worldY = 23 * gp.tileSize; // se coloca el NPC en la posicion 11 del eje Y.A modificar.
        gp.npc[mapNum][2].mapIndex = mapNum;
//
        mapNum = 2;
        gp.npc[mapNum][3] = new NPC_LupeMarciana(gp); // se crea el npc
        gp.npc[mapNum][3].worldX = 45 * gp.tileSize; // se coloca el NPC en la posicion 32 del eje X.A modificar.
        gp.npc[mapNum][3].worldY = 5 * gp.tileSize; // se coloca el NPC en la posicion 11 del eje Y.A modificar.
        gp.npc[mapNum][3].mapIndex = mapNum;
//            mapNum = 1;
//            gp.npc[mapNum][0] = new NPC_Pavon(gp); // se crea el npc
//            gp.npc[mapNum][0].worldX = 45 * gp.tileSize; // se coloca el NPC en la posicion 32 del eje X.A modificar.
//            gp.npc[mapNum][0].worldY = 4 * gp.tileSize; // se coloca el NPC en la posicion 11 del eje Y.A modificar.
//            gp.npc[mapNum][0].mapIndex = mapNum;
        }
}