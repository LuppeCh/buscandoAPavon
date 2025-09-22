package main;

import varios.Reloj;

import java.awt.*;

public class UI {

    gamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    // Mensajes
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    // Estado del juego
    public boolean gameFinished = false;

    //Dialogo
    public String currentDialogue = "";
    // Tiempo de juego
    Reloj reloj = new Reloj(this);

    //Llamar a derrota

    public boolean gameOver = false;

    public UI(gamePanel gp) {
        this.gp = gp;

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
        if(min >=25){ //cambiar segun el tiempo querido (en caso de querer ver en segundos, cambiar la variable min a seg)
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

    public int getXforCenteredText(String text) {
        int lenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - lenght/2;
        return x;
    }
}
