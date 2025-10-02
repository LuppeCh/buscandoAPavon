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
        tile = new Tile[100];

        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/Mapas/MAPAMPYTO.txt",0);
        loadMap("/Mapas/MAPAAILA2.txt",1);
        loadMap("/Mapas/PLANETARIO.txt",2);
    }

public void getTileImage() {

    setup(0, "00", true);
    setup(1, "01", false);
    setup(2, "02", true);
    setup(3, "03", true);
    setup(4, "04", true);
    setup(5, "05", true);
    setup(6, "06", true);
    setup(7, "07", true);
    setup(8, "08", true);
    setup(9, "09", true);
    setup(10, "10", true);
    setup(11, "11", false);
    setup(12, "12", true);
    setup(13, "13", true);
    setup(14, "14", true);
    setup(15, "15", true);
    setup(16, "16", true);
    setup(17, "17", true);
    setup(18, "18", true);
    setup(19, "19", true);
    setup(20, "20", true);
    setup(21, "21", true);
    setup(22, "22", true);
    setup(23, "23", true);
    setup(24, "24", true);
    setup(25, "25", true);
    setup(26, "26", true);
    setup(27, "27", true);
    setup(28, "28", true);
    setup(29, "29", true);
    setup(30, "30", true);
    setup(31, "31", true);
    setup(32, "32", true);
    setup(33, "33", true);
    setup(34, "34", true);
    setup(35, "35", true);
    setup(36, "36", true);
    setup(37, "37", true);
    setup(38, "38", true);
    setup(39, "39", true);
    setup(40, "40", true);
    setup(41, "41", true);
    setup(42, "42", true);
    setup(43, "43", true);
    setup(44, "44", false);
    setup(45, "45", false);
    setup(46, "46", true);
    setup(47, "47", true);
    setup(48, "48", true);
    setup(49, "49", true);
    setup(50, "50", true);
    setup(51, "51", true);
    setup(52, "52", true);
    setup(53, "53", true);
    setup(54, "54", true);
    setup(55, "55", true);
    setup(56, "56", true);
    setup(57, "57", false);
    setup(58, "58", false);
    setup(59, "59", false);
    setup(60, "60", false);
    setup(61, "61", false);
    setup(62, "62", false);
    setup(63, "63", false);
    setup(64, "64", true);
    setup(65, "65", true);
    setup(66, "66", false);
    setup(67, "67", false);
    setup(68, "68", true);
    setup(69, "69", true);
    setup(70, "70", false);
    setup(71, "71", false);
    setup(72, "72", true);
    setup(73, "73", true);
    setup(74, "74", false);
    setup(75, "75", true);
    setup(76, "76", true);
    setup(77, "77", false);
    setup(78, "78", false);
    setup(79, "79", false);
    setup(80, "80", true);
    setup(81, "81", false);
    setup(82, "82", false);
    setup(83, "83", false);
    setup(84, "84", true);
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
        }  catch(Exception e) {
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