package tile;

import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import main.GamePanel;
import main.UtilTool;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadmap("/res/maps/world03.txt");

    }

    public void getTileImage() {
        try {
            setUp(0, "grass3", false);
            setUp(1, "wall2", true);
            setUp(2, "water2", true);
            setUp(3, "earth2", false);
            setUp(4, "tree2", true);
            setUp(5, "sand2", false);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setUp(int index, String ImageName, boolean collision) {
        UtilTool uTool = new UtilTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/"+ ImageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadmap(String filepath) {
        try {
            InputStream is = getClass().getResourceAsStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for(int row = 0; row < gp.maxWorldRow; row++) {
                String line = br.readLine();
                String numbers[] = line.split(" ");
                for(int col = 0; col < gp.maxWorldCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                }
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        for(int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {
            for(int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
                int tilenum = mapTileNum[worldCol][worldRow];

                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow *gp.tileSize;

                int screenX = worldX - gp.player.worldX + gp.player.screenX; 
                int screenY = worldY - gp.player.worldY + gp.player.screenY;
                
                if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                        g2.drawImage(tile[tilenum].image, screenX, screenY, null);
                    }
            }
        }
    }
}

