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
    Tile[] tile;
    int[][] mapTileNum;

    public TileManager(gamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/earth.png"));

            tile[1] = new Tile();
            tile[1].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/grass00.png"));

            tile[2] = new Tile();
            tile[2].Image = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall (1).png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/map/map01.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String line = br.readLine();

                while (col <  gp.maxScreenCol) {
                    String[] numbers = line.split(" ");
                    int num  = Integer.parseInt(numbers[col]);
                    mapTileNum[row][col] = num;
                    col ++;
                }
                if (col == gp.maxScreenCol) {
                    col = 0;
                    row ++;
                }
            }
            br.close();
        }   catch(Exception e) {
        }
    }

    public void draw(Graphics2D g2) {
         int col = 0;
         int row = 0;
         int x = 0;
         int y = 0;

         while (col < gp.maxScreenCol && row < gp.maxScreenRow) {

             int tileNum = mapTileNum[col][row];
             g2.drawImage(tile[tileNum].Image, x, y, gp.tileSize, gp.tileSize, null);
             col++;
             x += gp.tileSize;
             if (col == gp.maxScreenCol) {
                 col = 0;
                 x = 0;
                 row ++;
                 y +=  gp.tileSize;
             }
        }
    }
}