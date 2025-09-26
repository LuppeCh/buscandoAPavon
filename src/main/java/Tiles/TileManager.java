package Tiles;

import main.UtilityTool;
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
    public int[][][] mapTileNum; //array de tres dimensiones


    public TileManager(gamePanel gp) {
        this.gp = gp;
        tile = new Tile[50];

        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];



        getTileImage();
        loadMap("/Mapas/world01.txt",0);
        loadMap("/Mapas/interior01.txt",1);
    }

    public void getTileImage() {

            setup(0, "grass", false);
            setup(1, "wall", true);
            setup(2, "water", true);
            setup(3, "earth", false);
            setup(4, "tree", true);
            setup(5, "sand", false);
            setup(10, "grass", false);
            setup(40, "wall", true);
            setup(41, "tree", true);
            setup(42, "hut", false);
            setup(43, "floor01", false);
            setup(44, "table", true);
    }
    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try{
            tile[index] = new Tile();

            InputStream is = getClass().getResourceAsStream("/Tiles/" + imageName + ".png");
            System.out.println("Cargando: " + imageName + " -> " + is);
            tile[index].Image = ImageIO.read(is);

            tile[index].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/" + imageName +".png"));
            tile[index].Image = uTool.scaleImage(tile[index].Image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int map) { // (String filePath, int map)
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col <  gp.maxWorldCol) {

                    String[] numbers = line.split(" ");

                    int num  = Integer.parseInt(numbers[col]);

                    // mapTileNum[row][col] = num;

                    mapTileNum[map][col][row] = num;
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

             int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

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