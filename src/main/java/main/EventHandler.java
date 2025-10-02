package main;

import varios.Dados;
import varios.Reloj;
import varios.Direccion;
import entity.NPC_Aila;
import entity.NPC_Panadera;

import java.awt.*;

public class EventHandler {
    gamePanel gp;
    NPC_Aila npcAila;
    NPC_Panadera npcPan;
    EventRect eventRect [][][];
    private boolean llamo = false;
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int numDados = 1; //numero de dados en el array
    byte numCaras = 6; // numero de caras del dado

    private Reloj reloj;

    public EventHandler (gamePanel gp) {
        this.gp = gp;
        npcAila = new NPC_Aila(this.gp);
        npcPan = new NPC_Panadera(this.gp);

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;

        while(map < gp.maxMap && col < gp.maxWorldCol &&  row < gp.maxWorldRow){
            eventRect [map][col][row] = new EventRect();
            eventRect [map][col][row].x = 0;
            eventRect [map][col][row].y = 0;
            eventRect [map][col][row].width = gp.tileSize/2;
            eventRect [map][col][row].height = gp.tileSize/2;
            eventRect [map][col][row].eventRectDefaultX = eventRect [map][col][row].x;
            eventRect [map][col][row].eventRectDefaultY = eventRect [map][col][row].y;

            col++;
            if(col == gp.maxWorldCol) {
                col = 0;
                row++;

                if(row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent() {
        // Comprobar distancia del jugador con el evento
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }
        if (canTouchEvent) {

            // Entrar tienda izquierda
            if (hit(0, 15, 14, Direccion.Arriba)) {
                System.out.println("Entrar tienda izquierda");
            }

            // Entrar tienda derecha
            else if (hit(0, 16, 14, Direccion.Arriba)) {
                System.out.println("Entrar tienda derecha");
            }

            // Esquina
            else if (hit(0, 1, 15, Direccion.Arriba)) {
                System.out.println("Esquina");
            }

            // Saliste de la tienda
            else if (hit(0, 16, 15, Direccion.Abajo)) {
                System.out.println("Saliste de la tienda");
                mensajeLugar(gp.gameState);
            }

            // Interactuar con entorno
            else if (hit(0, 11, 15, Direccion.Arriba)) {
                interactuarEntorno(0, 11, 15, gp.gameState);
            }
            // Teleport Aila
            else if (hit(0, 20, 26, Direccion.Any)) {
                teleport(1, 37, 33);
            }
            // Teleport Planetario
            else if (hit(1, 37, 33, Direccion.Any)) {
                    teleport(2, 4, 47);
            }
            // Teleport Planetario2
            else if (hit(2, 9, 28, Direccion.Any)) {
                teleport(2, 40, 9);
            }
            // Teleport exitPlanetario2
            else if (hit(2, 40, 9, Direccion.Any)) {
                teleport(2, 9, 28);
            }
            else if (llamo == false) {
                if (hit(0, 20, 14, Direccion.Derecha)) {
                    llamada(gp.gameState);
                    llamo = true;
                    System.out.println("Aaaaaaaa");
                }
                else if (hit(0, 20, 15, Direccion.Derecha)) {
                    llamada(gp.gameState);
                    llamo = true;
                    System.out.println("Aaaaaaaa2");
                }


            }
            else if (npcAila.pelea) {
                iniciarCombate(reloj, npcPan);
            }
        }
    }

    public boolean hit(int map, int col, int row, Direccion reqDirection){
        boolean hit = false;
        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
                if(gp.player.direction == reqDirection || reqDirection == Direccion.Any){
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }


            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }

    public void mensajeLugar(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Saliste de la tienda";
        canTouchEvent = false;
    }
    public void interactuarEntorno(int map, int col, int row, int gameState) {
        if(gp.keyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "No dice nada";
            eventRect[map][col][row].eventDone = true;
        }
    }

    public void teleport(int map, int col, int row) {

        gp.currentMap = map;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canTouchEvent = false;
        gp.ui.currentDialogue = "Teleport";
    }

    public void llamada(int gameState) {
        gp.gameState = gp.dialogueState;
        gp.playSE(6);
        gp.ui.currentDialogue = "\"Volpin! Soy Sebas, \n secuestraron a Pavon... \n Resolve\"";
    }

    public void iniciarCombate(Reloj reloj, NPC_Panadera npcPan){
        this.reloj = reloj;

        // Primero, revisamos si el jugador tiene pan
        if (!npcPan.tienePan) {
            // No tiene pan -> muere
            gp.gameState = gp.gameOverState;
            System.out.println("No tenías pan de ajo, perdiste.");
            return;
        }

        // Tirada de dado en el momento
        Dados tiradas = new Dados(numCaras, numDados);
        int[] resultado = tiradas.tirarDados();
        int tirada = resultado[0];

        if (tirada <= 4){ // 1 a 4 -> sumas tiempo
            reloj.agregarTiempo(180);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "\"Ah! Está bien! \n Te diré todo lo que sé\"";
        } else { // 5 o 6 -> acepta por las buenas
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "\"Bueno, solo por que me das pena\"";
        }
    }

    }

