package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import main.GamePanel;
import main.KeyHandler;
import object.SuperObject;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    boolean moving = false;
    int pixelCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 1;
        solidArea.y = 1;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.height = 46;
        solidArea.width = 46;

        setDefaultValues();
        getImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 23;
        speed = 4;
        direction = "down";
    }

    public void getImage() {
        up1 = setup("/player/Player_up1");
        up2 = setup("/player/Player_up2");
        down1 = setup("/player/Player_down1");
        down2 = setup("/player/Player_down2");
        left1 = setup("/player/Player_left1");
        left2 = setup("/player/Player_left2");
        right1 = setup("/player/Player_right1");
        right2 = setup("/player/Player_right2");
    }

    public void update() {
        if (!moving) {
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

                if (keyH.upPressed) direction = "up";
                else if (keyH.downPressed) direction = "down";
                else if (keyH.leftPressed) direction = "left";
                else if (keyH.rightPressed) direction = "right";

                moving = true;

                // Collision Checks
                collisionOn = false;
                gp.cChecker.checkTile(this);

                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpObject(objIndex);

                int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                interactNPC(npcIndex);

            } else {
                standCounter++;
                if (standCounter > 20) {
                    spriteNum = 1;
                    standCounter = 0;
                }
            }
        }

        if (moving) {
            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            // Animation toggle
            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }

            pixelCounter += speed;
            if (pixelCounter >= 48) {
                moving = false;
                pixelCounter = 0;
            }
        }
    }

    public void pickUpObject(int index) {
        if (index != 999) {
            if (keyH.enterPressed) {
                SuperObject object = gp.obj[index];
                System.out.println("Picked up: " + object.name);
                gp.obj[index] = null; // remove the object from the world
                keyH.enterPressed = false; // prevent repeated pickup
            }
        }
    }

    public void interactNPC(int i) {
        if (i != 999) {
            if (keyH.enterPressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
            keyH.enterPressed = false;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up": image = (spriteNum == 1) ? up1 : up2; break;
            case "down": image = (spriteNum == 1) ? down1 : down2; break;
            case "left": image = (spriteNum == 1) ? left1 : left2; break;
            case "right": image = (spriteNum == 1) ? right1 : right2; break;
        }

        g2.drawImage(image, screenX, screenY, null);
    }
}