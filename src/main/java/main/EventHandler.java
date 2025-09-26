package main;

import varios.Direccion;

import java.awt.Rectangle;
import java.util.stream.Gatherer;

public class EventHandler {
    gamePanel gp;
    EventRect eventRect [][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    public EventHandler (gamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;

        while( col < gp.maxWorldCol &&  row < gp.maxWorldRow){
            eventRect [col] [row] = new EventRect();
            eventRect [col] [row].x = 0;
            eventRect [col] [row].y = 0;
            eventRect [col] [row].width = gp.tileSize/2;
            eventRect [col] [row].height = gp.tileSize/2;
            eventRect [col] [row].eventRectDefaultX = eventRect [col] [row].x;
            eventRect [col] [row].eventRectDefaultY = eventRect [col] [row].y;

            col++;
            if(col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }

    }
    public void checkEvent() {
        //comprobar distancia del jugador con el evento
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance,yDistance);
        if(distance > gp.tileSize){
            canTouchEvent = true;
        }

        if(canTouchEvent == true){
            if(hit(15,14, Direccion.Arriba) == true) {
                //Sucede evento
                System.out.println("Entrar tienda izquierda");
            }
            if(hit(16,14, Direccion.Arriba) == true) {
                //Sucede evento
                System.out.println("Entrar tienda derecha");
            }

            if(hit(20,5, Direccion.Arriba) == true) {
                //Sucede evento
                System.out.println("TP");
                teleport(20, 5, gp.dialogueState);
            }
            if(hit(1,15, Direccion.Arriba) == true) {
                //Sucede evento
                System.out.println("Esquina");
            }
            if(hit(16, 15,Direccion.Abajo) == true) {
                //Sucede evento
                System.out.println("Saliste de la tienda");
                mensajeLugar(16, 15, gp.dialogueState);
            }
            if(hit(11, 15,Direccion.Arriba) ==true){
                //Sucede evento
                interactuarEntorno(11, 15, gp.dialogueState);
            }
        }
    }
    public boolean hit(int col, int row, Direccion reqDirection){
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
//        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
//        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;
        eventRect[col][row].x = col * gp.tileSize + gp.tileSize/4;
        eventRect[col][row].y = row * gp.tileSize + gp.tileSize/4;

        if(gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
            if(gp.player.direction == reqDirection || reqDirection == Direccion.Any){
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
        return hit;
    }
    public void mensajeLugar(int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Saliste de la tienda";
        canTouchEvent = false;
    }
    public void interactuarEntorno(int col, int row, int gameState) {
        if(gp.keyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "No dice nada";
            eventRect[col][row].eventDone = true;
        }
    }
    public void teleport(int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Teleport";
        gp.player.worldX = gp.tileSize * 3;
        gp.player.worldY = gp.tileSize * 17;
    }
}
