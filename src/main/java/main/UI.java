package main;


import entity.Player;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    gamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B, minecraft;

    public int commandNum = 0;
    public int titleScreenState = 0;

    public void drawTitleScreen (){
        if(titleScreenState == 0){
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

            // TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
            String text = "La busqueda de Peivon";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;

            // SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x+5, y+5);

            // MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y);


            // PEIVON IMAGEN
            x = gp.screenWidth/2 - (gp.tileSize*2)/2;
            y += gp.tileSize*2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

            // MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            text = "Nuevo Juego";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3.5;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Salir";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

        }

    }
    // Mensajes
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    // Estado del juego
    public boolean gameFinished = false;

    //Dialogo
    public String currentDialogue = "";
    public void setDialogue(String dialogue){
        this.currentDialogue = dialogue;
    }
//    // Tiempo de juego
//    Reloj reloj = new Reloj(gp, this);
    //Llamar a derrota
    public boolean gameOver = false;
    //variables cursor
    public int slotCol = 0;
    public int slotRow = 0;

    public UI(gamePanel gp) {
        this.gp = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/Fonts/Minecraft.ttf");
            minecraft = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        arial_40 = new Font("Arial", Font.PLAIN, 20);
        arial_80B = new Font("Arial", Font.BOLD, 40); // fuente más grande para victoria

    }

    // Mostrar mensaje temporal
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        // -------------------
        // HUD siempre visible
        // -------------------
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

//        //Llamamos los tiempos del juego
//        reloj.actualizarTiempo();
//        int min = reloj.getMinutos();
//        int seg = reloj.getSegundos();
//        int ms = reloj.getMilisegundos();

//        String tiempoTexto = String.format("%2d:%2d:%03d", min, seg, ms);

        // TITLE STATE
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        //State de juego
        if(gp.gameState == gp.playState){
            gp.reloj.actualizarTiempo();  // ✅ SOLO acá

            int min = gp.reloj.getMinutos();
            int seg = gp.reloj.getSegundos();
            int ms = gp.reloj.getMilisegundos();

            String tiempoTexto = String.format("%2d:%2d:%03d", min, seg, ms);
            drawPlayScreen(tiempoTexto);
        }


        //State de pausa
        if(gp.gameState == gp.pauseState){
            //Nada
        }
        //State de dialogo
        if(gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
        // State de derrota
        if(gp.gameState == gp.gameOverState){
            // accion de fin de juego
            int min = gp.reloj.getMinutos();
            int seg = gp.reloj.getSegundos();
            int ms = gp.reloj.getMilisegundos();
            String tiempoTexto = String.format("%2d:%2d:%03d", min, seg, ms);

            drawGameOverScreen();
        }
        // State Character
        if(gp.gameState == gp.characterState) {

            drawInventory();
        }




        // -------------------
        // Mensaje temporal
        // -------------------
        if (messageOn) {
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.setColor(Color.YELLOW);
            g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

            messageCounter++;
            if (messageCounter > 120) { // 2 segundos aprox
                messageCounter = 0;
                messageOn = false;
            }
        }

        // UI.java (Dentro del método draw)

// -------------------
// Mensaje de victoria (Créditos)
// -------------------
        if (gameFinished) {

            // 1. DIBUJAR FONDO NEGRO COMPLETO
            g2.setColor(Color.black);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            // 2. CONFIGURAR FUENTE PRINCIPAL
            g2.setFont(arial_80B); // 40f
            g2.setColor(Color.YELLOW);

            // Calcular tiempo
            int min = gp.reloj.getMinutos();
            int seg = gp.reloj.getSegundos();
            int ms = gp.reloj.getMilisegundos();
            String tiempoTexto = String.format("%2d:%2d:%03d", min, seg, ms);

            int y = gp.screenHeight / 2; // Punto central vertical

            // 3. TEXTO: TIEMPO (Arriba)
            String text = "Tu tiempo es: " + tiempoTexto;
            int x = getXforCenteredText(text);
            y = gp.screenHeight / 2 - (gp.tileSize * 3); // 3 Tiles arriba del centro
            g2.drawString(text, x, y);

            // 4. TEXTO: CONTINUARÁ... (Centro)
            text = "¡Continuará!";
            x = getXforCenteredText(text);
            y = gp.screenHeight / 2; // En el centro
            g2.drawString(text, x, y);

            // 5. TEXTO: CRÉDITOS (Abajo)
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F)); // Fuente más pequeña para los créditos
            g2.setColor(Color.WHITE);

            text = "Hecho por: Juli, Lupe, Ailu y Fifi.";
            x = getXforCenteredText(text);
            y = gp.screenHeight / 2 + (gp.tileSize * 4); // 4 Tiles abajo del centro
            g2.drawString(text, x, y);

            // NO detener el gameThread automáticamente
        }

        // -------------------
        // Mensaje de derrota
        // -------------------
        if (gameOver) {
            g2.setColor(Color.black);
            g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

            g2.setFont(arial_80B);
            g2.setColor(Color.RED);


            String text = "ir al monumento de la bandera...";
            int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x = gp.screenWidth / 2 - textLength / 2;
            int y = gp.screenHeight / 2;
            g2.drawString(text, x, y);

            text = "Luego de revisar el GPS e ";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 2);
            g2.drawString(text, x, y);

            text = "Continuará...";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 4);
            g2.drawString(text, x, y);

            text = "Hecho por: Juli, Lupe, Ailu y Fifi.";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 4);
            g2.drawString(text, x, y);
            // NO detener el gameThread automáticamente
        }

    }
    //dibujo de la pantalla de dialogo
    public void drawDialogueScreen(){
        // Ventana del mensaje
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height= gp.tileSize * 5;
        drawSubWindow(x, y, width, height);
        g2.setFont(minecraft);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }

    }
    // dibujo de pantalla 2
    public void drawSubWindow(int x, int y, int width, int heigth){
        Color c = new Color( 0, 0 , 0,210); // el 4 valor es para la opacidad de la ventana
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, heigth, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, heigth-10, 25, 25);
    }

    //dibujo de la pantalla por ensima del juego
    public void drawPlayScreen(String tiempoTexto){
        // Mostrar cantidad de llaves / objetos
        g2.drawString("GPS: " + gp.player.gpsCount, 25, 50);
        g2.drawString("Pan de Ajo: " + gp.player.panDeAjoCount, 25, 70);
        g2.drawString("Vale por comida: " + gp.player.valePorComidaCount, 25, 90);
        g2.drawString("Time: " + tiempoTexto, gp.tileSize * 12, 65);
    }

    //dibujo de la pantalla del inventario
    public void drawInventory() {
        //frame
        int frameX = gp.tileSize * 9;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        //Dibujar los items del jugador
        for(int i =0; i < gp.player.inventory.size();i++) {

            g2.drawImage(gp.player.inventory.get(i).down1,slotX,slotY, null);

            slotX += slotSize;

            if(i == 4 || i == 9 || i == 14){
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        //Cursor
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        //drawCursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10 ,10);

        //Frame de la descripcion
        int dframeX = frameX;
        int dframeY = frameY + frameHeight;
        int dframeWidth = frameWidth;
        int dframeHeight = gp.tileSize * 3;
        drawSubWindow(dframeX, dframeY, dframeWidth, dframeHeight);

        //Dibujamos la descripcion
        int textX = dframeX + 20;
        int textY = dframeY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(20F));

        int itemIndex = getItemIndexOnSlot();

        if(itemIndex < gp.player.inventory.size()){

           for(String line: gp.player.inventory.get(itemIndex).descripcion.split("\n")) {
                g2.drawString(line, textX, textY);
               textY += 32;
           }
        }

    }

    public int getItemIndexOnSlot() {
        int itemIndex = slotCol + (slotRow*5);
        return itemIndex;
    }
    //dibujo de la pantalla para el game over
    public void drawGameOverScreen() {
        //configuracion de la pantalla
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // configuracion del texto Game Over
        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90f));
        // texto principa
        text = "Game Over";
        g2.setColor(Color.black);
        x =getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text,x,y);

        // sombra por abajo del texto
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y -4);

        // volver a jugar
        g2.setFont(g2.getFont().deriveFont(30f));
//        text = "Volver a jugar";
//        x = getXforCenteredText(text);
//        y += gp.tileSize * 4;
//        g2.drawString(text, x, y);
//
//        if(commandNum == 0){
//            g2.drawString(">", x-40, y);
//        }

        //Volver al titulo principal
        text = "Salir";
        x = getXforCenteredText(text);
        y += 60;
        g2.drawString(text, x, y);

        if(commandNum == 0){
            g2.drawString(">", x-40, y);
        }

    }

    public int getXforCenteredText(String text) {
        int lenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - lenght/2;
        return x;
    }
}