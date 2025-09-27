package main;

import varios.Reloj;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    gamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B, minecraft;
    // Mensajes
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    // Estado del juego
    public boolean gameFinished = false;

    //Dialogo
    public String currentDialogue = "";
    // Tiempo de juego
    Reloj reloj = new Reloj(gp, this);
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

        //Llamamos los tiempos del juego
        reloj.actualizarTiempo();
        int min = reloj.getMinutos();
        int seg = reloj.getSegundos();
        int ms = reloj.getMilisegundos();

        String tiempoTexto = String.format("%2d:%2d:%03d", min, seg, ms);

        //State de juego
        if(gp.gameState == gp.playState){
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

        // -------------------
        // Mensaje de victoria
        // -------------------
        if (gameFinished) {
            g2.setFont(arial_80B);
            g2.setColor(Color.YELLOW);

            String text = "Felicidades, has ganado!!";
            int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x = gp.screenWidth / 2 - textLength / 2;
            int y = gp.screenHeight / 2;
            g2.drawString(text, x, y);

            text = "Has encontrado a Peivon!!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);

            text = "Tu tiempo es: " + tiempoTexto;
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 4);
            g2.drawString(text, x, y);

            // NO detener el gameThread automáticamente
        }
        //--------------------
        // Condicion de perdida
        // -------------------

        if(min >=15){ //cambiar segun el tiempo querido (en caso de querer ver en segundos, cambiar la variable min a seg)
            gameOver =true;
        }

        // -------------------
        // Mensaje de derrota
        // -------------------
        if (gameOver) {
            g2.setColor(Color.black);
            g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

            g2.setFont(arial_80B);
            g2.setColor(Color.RED);

            String text = "Que malo, has perdido!!!";
            int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x = gp.screenWidth / 2 - textLength / 2;
            int y = gp.screenHeight / 2;
            g2.drawString(text, x, y);

            text = "No has encontrado a Peivon!!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);

            text = "Pasaron los: " +tiempoTexto;
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 4);
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

//            for(String line: gp.player.inventory.get(itemIndex).descripcion.split("\n"){
//                g2.drawString(line, textX, textY);
//                textY = += 32;
//            }
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
        text = "Volver a jugar";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);

//        if(commandNum == 0){
//            g2.drawString(">", x-40, y);
//        }

        //Volver al titulo principal
        text = "Volver al menu";
        x = getXforCenteredText(text);
        y += 35;
        g2.drawString(text, x, y);

//        if(commandNum == 0){
//            g2.drawString(">", x-40, y);
//        }

    }

    public int getXforCenteredText(String text) {
        int lenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - lenght/2;
        return x;
    }
}
