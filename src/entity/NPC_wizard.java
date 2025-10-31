package entity;


import java.util.Random;

import main.GamePanel;

public class NPC_wizard extends Entity{

    public NPC_wizard(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }
    public void getImage() {
        up1 = setup("/npc/buddha_up1");
        up2 = setup("/npc/buddha_up2");
        down1 = setup("/npc/buddha_down1");
        down2 = setup("/npc/buddha_down2");
        left1 = setup("/npc/buddha_left1");
        left2 = setup("/npc/buddha_left2");
        right1 = setup("/npc/buddha_right1");
        right2 = setup("/npc/buddha_right2");
    }
    public void setDialogue() {

        dialogues[0] = "Your health and inventory are protected from direct access.";
        dialogues[1] = "This is encapsulation!!!!";
        dialogues[2] = "it hides internal data and only exposes necessary functionality.";
    }  
    public void setAction() {

        actionLockCounter++;
        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1; // Pick random btw 1 to 100
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0; 
        }
    }
    public void speak() {
        super.speak();
    }      
}
