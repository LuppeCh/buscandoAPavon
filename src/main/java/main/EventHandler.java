package main;

import entity.*;
import varios.Direccion;

import java.awt.*;

public class EventHandler {

    gamePanel gp;

    // Estructura de datos tridimensional para las áreas de evento: [mapa][columna][fila].
    // Permite asignar una zona interactiva a CADA tile de CADA mapa.
    EventRect eventRect [][][];

    // Banderas de estado (flags) para controlar la repetición de eventos únicos:
    private boolean llamo = false;    // Controla si el evento de llamada ya ocurrió.
    private  boolean piensa =false;   // Controla si el pensamiento del lugar ya ocurrió.
    private  boolean vioGPS =false;   // Controla si el mensaje del GPS ya se mostró.

    // Coordenadas del jugador al momento de la activación del último evento.
    // Utilizadas para calcular la distancia de "cooldown".
    int previousEventX, previousEventY;

    // Bandera de "cooldown": Es 'true' si el jugador ha salido del área del último evento.
    boolean canTouchEvent = true;


    /**
     * Constructor: Inicializa la matriz EventRect.
     */
    public EventHandler (gamePanel gp) {
        this.gp = gp;

        // Inicializa la matriz basada en las dimensiones del mundo definidas en gamePanel.
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;

        // Bucle de inicialización que recorre todos los tiles de todos los mapas:
        while(map < gp.maxMap && col < gp.maxWorldCol &&  row < gp.maxWorldRow){

            // Crea un nuevo EventRect para el tile (map, col, row).
            eventRect [map][col][row] = new EventRect();

            // Define el offset y el tamaño del área de colisión DENTRO del tile (16x16 por defecto).
            // Usar gp.tileSize/2 centraliza el área de interacción al 50% del tile.
            eventRect [map][col][row].x = 0;
            eventRect [map][col][row].y = 0;
            eventRect [map][col][row].width = gp.tileSize/2;
            eventRect [map][col][row].height = gp.tileSize/2;

            // Guarda los offsets por defecto para la restauración en el método hit().
            eventRect [map][col][row].eventRectDefaultX = eventRect [map][col][row].x;
            eventRect [map][col][row].eventRectDefaultY = eventRect [map][col][row].y;

            // Lógica para iterar de columna a columna, luego a fila, y finalmente a mapa.
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

    /**
     * MÉTODO DE LÓGICA PRINCIPAL: checkEvent()
     * Se llama en cada frame desde gamePanel.update(). Determina si se debe activar un evento.
     */
    public void checkEvent() {

        // =========================================================================
        // 1. CONTROL DE COOLDOWN
        // =========================================================================

        // Calcula la distancia absoluta en X e Y desde el punto donde se activó el último evento.
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        // Si la distancia es mayor a un tile completo, se permite un nuevo evento.
        // Esto evita que un evento se active múltiples veces por un pequeño movimiento.
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        // Solo ejecuta la lógica de colisión si no está en cooldown.
        if (canTouchEvent) {

            // =========================================================================
            // 2. LÓGICA DE EVENTOS (Activación por Colisión y Banderas)
            // =========================================================================

            // --- EVENTO VIDEO 1 (Salida de la tienda): Varias tiles en la Columna 24 del Mapa 0 ---
            if(hit(0, 24, 2, Direccion.Izquierda)||hit(0, 24, 3, Direccion.Izquierda)||hit(0, 24, 4, Direccion.Izquierda)||hit(0, 24, 27, Direccion.Izquierda)||hit(0, 24, 28, Direccion.Izquierda)||hit(0, 24, 29, Direccion.Izquierda)){
                if(!gp.videoMostrado){ // Condición: solo la primera vez.
                    gp.videoMostrado = true;
                    gp.showVideo("pavon"); // Inicia la reproducción del video.
                    // Lógica para que el NPC Pavón (asumiendo que es npc[0][1]) desaparezca tras el video.
                    if(gp.videoMostrado && gp.npc[0][1] instanceof NPC_Pavon) {
                        gp.npc[0][1] = null;
                    }
                }
            }

            // --- EVENTO VIDEO FINAL (Monumento): Tile (2, 8, 28) ---
            else if(hit(2, 8, 28, Direccion.Izquierda)) {
                // Chequeo de que los NPCs existan y sean del tipo correcto para acceder a sus banderas.
                if (gp.npc[1][2] instanceof NPC_Aila && gp.npc[2][3] instanceof NPC_LupeMarciana) {

                    NPC_Aila aila = (NPC_Aila) gp.npc[1][2];
                    NPC_LupeMarciana marciana = (NPC_LupeMarciana) gp.npc[2][3];

                    // REQUERIMIENTOS DE FINAL DE JUEGO:
                    // 1. El video final NO se ha mostrado (!gp.videoMostrado2).
                    // 2. La bandera de condición de Aila se cumplió (aila.activarFinal).
                    // 3. La NPC Marciana ya tiene el GPS (marciana.tieneGPS).
                    if (!gp.videoMostrado2 && aila.activarFinal && marciana.tieneGPS) {

                        // Activación del Final
                        gp.videoMostrado2 = true;
                        gp.showVideo("monu");

                        // Reproducción del audio final.
                        gp.playSE(6);

                        // La transición a la pantalla de créditos (gameFinished) se delega
                        // al callback del video en gamePanel.showVideo() para sincronizar el fin.
                    }
                }
            }

            // --- INTERACCIÓN SIMPLE (Map 0, 5, 7) ---
            else if (hit(0, 5, 7, Direccion.Arriba)) {
                interactuarEntorno(0, 5, 7, gp.dialogueState);
            }

            // --- TELEPORTS (Cambio de mapa/escena) ---
            // Si Direccion.Any está activo, la colisión se detecta desde cualquier lado.
            else if (hit(0, 20, 26, Direccion.Any)) { teleport(1, 38, 32); }
            else if (hit(1, 38, 32, Direccion.Any)) { teleport(2, 4, 48); }
            else if (hit(2, 9, 28, Direccion.Any)) { teleport(2, 40, 9); }
            else if (hit(2, 40, 9, Direccion.Any)) { teleport(2, 9, 28); }

            // --- EVENTO LLAMADA (Map 0, 19, 14-15) ---
            else if (hit(0, 19, 14, Direccion.Derecha)|| hit(0, 19, 15, Direccion.Derecha) ) {
                if(llamo == false){ // Condición: solo la primera vez.
                    llamada(gp.dialogueState);
                    llamo = true;
                }
            }

            // --- EVENTO PENSAMIENTO (Map 0, 20, 14-15) ---
            else if (hit(0,20,14,Direccion.Derecha)||hit(0,20,15, Direccion.Derecha)){
                if(!piensa){ // Condición: solo la primera vez.
                    mensajeLugar(gp.dialogueState);
                    mensajeLugar(gp.dialogueState); // NOTA: Esto hará que el diálogo se muestre dos veces seguidas o parpadee.
                    piensa =true;
                }
            }

            // --- EVENTO MENSAJE GPS (Map 2, 41, 9) ---
            else if (hit(2,41,9,Direccion.Any)){
                if(gp.npc[2][3]instanceof NPC_LupeMarciana){
                    NPC_LupeMarciana marciana = (NPC_LupeMarciana) gp.npc[2][3];
                    // Condición: Se necesita el GPS Y no se ha mostrado el mensaje.
                    if(marciana.tieneGPS && !vioGPS){
                        mensajeGPS(gp.dialogueState);
                        vioGPS =true;
                    }
                }
            }
        }
    }

    /**
     * Lógica de Colisión (hit-detection) de Eventos.
     * Mueve temporalmente las áreas de colisión para verificar la intersección.
     */
    public boolean hit(int map, int col, int row, Direccion reqDirection){
        boolean hit = false;

        // Solo procesa si estamos en el mapa correcto.
        if (map == gp.currentMap) {

            // 1. Cálculo de la posición global de los Rectángulos (Player y Event):
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            // 2. Verificación de Intersección:
            if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {

                // 3. Verificación de Dirección:
                if(gp.player.direction == reqDirection || reqDirection == Direccion.Any){
                    hit = true;

                    // Guarda la posición para el cooldown.
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }

            // 4. RESTAURACIÓN CRÍTICA:
            // Es vital resetear los rectángulos a sus offsets por defecto. Si no se hace,
            // las colisiones futuras se calcularían incorrectamente.
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }

    // --- MÉTODOS DE ACCIÓN ---

    // Cambia el estado a diálogo, establece el mensaje y activa el cooldown.
    public void mensajeLugar(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "*PENSANDO*\nPavon dijo que se iba a la galeria...\nAlla voy Pavon";
        canTouchEvent = false;
    }

    // Activa el diálogo solo si se presiona ENTER (interacción forzada).
    public void interactuarEntorno(int map, int col, int row, int gameState) {
        if(gp.keyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "No dice nada";
            eventRect[map][col][row].eventDone = true; // Marca el evento como completado.
        }
    }

    // Lógica de cambio de mapa y reubicación del jugador (teleport).
    public void teleport(int map, int col, int row) {
        gp.currentMap = map;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canTouchEvent = false; // Cooldown forzado después de un teleport.
        gp.ui.currentDialogue = "Teleport";
    }

    // Reproduce un efecto de sonido (playSE) y muestra el diálogo asociado.
    public void llamada(int gameState) {
        gp.gameState = gameState;
        gp.playSE(4); // Reproduce el sonido de la llamada (índice 4).
        gp.ui.currentDialogue = "\"Volpin! Soy Sebas, \n secuestraron a Pavon... \n Resolve\"";
    }

    // Muestra un diálogo de pensamiento específico sobre el GPS.
    public void mensajeGPS(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "*PENSANDO*\nEl GPS dice qque esta en el monumento";
        canTouchEvent = false;
    }
}