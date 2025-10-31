package main;

import entity.*;
import object.OBJ_Key;
public class AssetHandler {
    GamePanel gp;
    public AssetHandler(GamePanel gp) {
        this.gp = gp;
    }
    public void SetObject() {
        
    }
    public void SetNPC() {
        gp.npc[0] = new NPC_wizard(gp);
        gp.npc[0].worldX = gp.tileSize*59;
        gp.npc[0].worldY = gp.tileSize*59; 

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = gp.tileSize*23;
        gp.obj[1].worldY = gp.tileSize*23;

        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = gp.tileSize*1;
        gp.obj[2].worldY = gp.tileSize*1;

        gp.obj[3] = new OBJ_Key(gp);
        gp.obj[3].worldX = gp.tileSize*62;
        gp.obj[3].worldY = gp.tileSize*62;

        gp.obj[4] = new OBJ_Key(gp);
        gp.obj[4].worldX = gp.tileSize*1;
        gp.obj[4].worldY = gp.tileSize*62;

        gp.obj[5] = new OBJ_Key(gp);
        gp.obj[5].worldX = gp.tileSize*62;
        gp.obj[5].worldY = gp.tileSize*1;

        gp.npc[6] = new NPC_jorin(gp);
        gp.npc[6].worldX = gp.tileSize*3;
        gp.npc[6].worldY = gp.tileSize*3;

        gp.npc[7] = new NPC_myros(gp);
        gp.npc[7].worldX = gp.tileSize*3;
        gp.npc[7].worldY = gp.tileSize*59;

        gp.npc[8] = new NPC_summer(gp);
        gp.npc[8].worldX = gp.tileSize*59;
        gp.npc[8].worldY = gp.tileSize*3;
        

    }
}
    