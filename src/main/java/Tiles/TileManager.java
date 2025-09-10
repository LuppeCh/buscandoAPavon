package Tiles;

import main.gamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
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
        try {
            tile[0] = new Tile();
            tile[0].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/water.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/earth.png"));

            tile[4] = new Tile();
            tile[4].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/tree.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/sand.png"));
        } catch (IOException e) {
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

                 g2.drawImage(tile[tileNum].Image, screenX, screenY, gp.tileSize, gp.tileSize, null);
             }


             worldCol++;

             if (worldCol == gp.maxWorldCol) {
                 worldCol = 0;

                 worldRow ++;

             }
        }
    }
}