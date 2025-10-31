package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Chains extends SuperObject {
    GamePanel gp;

    public OBJ_Chains(GamePanel gp) {

        name = "Chains";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/chains1.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
