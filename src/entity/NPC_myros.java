package entity;


import java.util.Random;

import main.GamePanel;

public class NPC_myros extends Entity{

    public NPC_myros(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }
    public void getImage() {
        up1 = setup("/npc/myros_up");
        up2 = setup("/npc/myros_up");
        down1 = setup("/npc/myros_down");
        down2 = setup("/npc/myros_down");
        left1 = setup("/npc/myros_left");
        left2 = setup("/npc/myros_left");
        right1 = setup("/npc/myros_right");
        right2 = setup("/npc/myros_right");
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

