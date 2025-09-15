package Tiles;

import main.UtilityTool;
import main.gamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    gamePanel gp;

    public Tile[] tile;
    public int[][] mapTileNum;


    public TileManager(gamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];

        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];


        getTileImage();
        loadMap();
    }

    public void getTileImage() {

            setup(0, "grass", false);
            setup(1, "wall", true);
            setup(2, "water", true);
            setup(3, "earth", false);
            setup(4, "tree", true);
            setup(5, "sand", false);
    }
    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try{
            tile[index] = new Tile();
            tile[index].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/" + imageName +".png"));
            tile[index].Image = uTool.scaleImage(tile[index].Image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap() {
        try {

            InputStream is = getClass().getResourceAsStream("/Mapas/world01.txt");

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;


            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col <  gp.maxWorldCol) {

                    String[] numbers = line.split(" ");
                    int num  = Integer.parseInt(numbers[col]);
                    mapTileNum[row][col] = num;
                    col ++;
                }

                if (col == gp.maxWorldCol) {

                    col = 0;
                    row ++;
                }
            }
            br.close();


        }   catch(Exception e) {


        }
    }

    public void draw(Graphics2D g2) {

         int worldCol = 0;
         int worldRow = 0;

         while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

             int tileNum = mapTileNum[worldCol][worldRow];

             int worldX = worldCol * gp.tileSize;
             int worldY = worldRow * gp.tileSize;
             int screenX = worldX - gp.player.worldX + gp.player.screenX;
             int screenY = worldY - gp.player.worldY + gp.player.screenY;

             // por rendimiento, vamos a dibujar solo las tiles visibles y una mas
             if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                 g2.drawImage(tile[tileNum].Image, screenX, screenY, null);
             }


             worldCol++;

             if (worldCol == gp.maxWorldCol) {
                 worldCol = 0;

                 worldRow ++;


             }
        }
    }
}