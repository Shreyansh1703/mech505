package main;

import javax.swing.JPanel;
import java.awt.*;
import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // Screen Settings
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // World Settings
    public final int maxWorldCol = 64;
    public final int maxWorldRow = 64;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // System
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetHandler aHandler = new AssetHandler(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // Entity and Object
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[8];
    public Entity npc[] = new Entity[10];

    // Game State
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 0;
    public final int titleState = 2;
    public final int dialogueState = 3;

    // For tracking which NPC is currently in dialogue
    public int currentNPC = -1;

    // FPS
    int FPS = 60;

    // Title Phase Enum
    enum TitlePhase { BLACK_SCREEN, TYPING_SCREEN }
    TitlePhase titlePhase = TitlePhase.BLACK_SCREEN;

    // Title Intro
    String[] titleLines = {
        "GAME_NAME",
        "by MECH505",
        "An adventure game that makes learning Java easier",
        "— and simplifies Object-Oriented Programming for learners.",
        "",
        "Long, long ago, in a magical land far beyond time…",
        "There lay a peaceful forest, nestled between emerald hills and silver rivers.",
        "But peace does not last forever.",
        "A strange curse began to spread...", 
        "slowly turning the forest cold, twisted, and hollow.",
        "No traveler ever returned.",
        "Until now.",
        "You are the adventurer — a warrior guided not just by courage, but by code.",
        "You have journeyed far and wide to reach the edge of this cursed land.",
        "Little do you know... the curse is deeper than shadow.",
        "It lives in every forest dweller. Every enemy. And it wears more than one face.",
        "But before swords are drawn, you must first learn the world.",
        "Welcome to the Tutorial Grounds.",
        " Here, you will begin to understand how this world moves — and how Java brings it to life.",
        ">> Press ENTER to begin <<"
    };

    public int currentTitleLine = 0;  // Start with first line (0 index)
    public boolean introComplete = false;

    // Typing effect
    public String currentDisplayedLine = "";
    public int charIndex = 0;
    public long typingSpeed = 50;
    public long lastCharTime = System.currentTimeMillis();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        gameState = titleState;
    }

    public void setupGame() {
        aHandler.SetObject();
        aHandler.SetNPC();
        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void advanceTitleLine() {
        if (titlePhase == TitlePhase.BLACK_SCREEN) {
            currentTitleLine++;
            if (currentTitleLine >= 4) {
                titlePhase = TitlePhase.TYPING_SCREEN;
                currentDisplayedLine = "";
                charIndex = 0;
                lastCharTime = System.currentTimeMillis();
            }
        } else if (titlePhase == TitlePhase.TYPING_SCREEN) {
            if (charIndex < titleLines[currentTitleLine].length()) {
                currentDisplayedLine = titleLines[currentTitleLine];
                charIndex = titleLines[currentTitleLine].length();
            } else {
                currentTitleLine++;
                if (currentTitleLine >= titleLines.length) {
                    introComplete = true; // Set flag — actual game transition handled in KeyHandler
                } else {
                    currentDisplayedLine = "";
                    charIndex = 0;
                    lastCharTime = System.currentTimeMillis();
                }
            }
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }

            if (timer >= 1000000000) {
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameState == titleState && titlePhase == TitlePhase.TYPING_SCREEN) {
            if (currentTitleLine < titleLines.length) {
                long now = System.currentTimeMillis();
                if (charIndex < titleLines[currentTitleLine].length() && now - lastCharTime >= typingSpeed) {
                    currentDisplayedLine += titleLines[currentTitleLine].charAt(charIndex);
                    charIndex++;
                    lastCharTime = now;
                }
            }
        }

        if (gameState == playState) {
            player.update();
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) npc[i].update();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == titleState) {
            if (titlePhase == TitlePhase.BLACK_SCREEN) {
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, screenWidth, screenHeight);
                g2.setFont(new Font("Arial", Font.BOLD, 28));
                g2.setColor(Color.WHITE);
                int y = 200;
                for (int i = 0; i < currentTitleLine; i++) {
                    g2.drawString(titleLines[i], 50, y);
                    y += 40;
                }
                g2.setFont(new Font("Arial", Font.PLAIN, 18));
                g2.drawString("Press ENTER to continue...", 50, y + 40);
            } else if (titlePhase == TitlePhase.TYPING_SCREEN) {
                tileM.draw(g2);  // Draw tiles behind typing text
                g2.setFont(new Font("Arial", Font.PLAIN, 24));
                g2.setColor(Color.WHITE);
                int x = 50;
                int y = 100;
                if (currentTitleLine < titleLines.length) {
                    g2.drawString(currentDisplayedLine, x, y);
                }
            }
            return;
        }

        // Draw game world and entities
        tileM.draw(g2);

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) obj[i].draw(g2, this);
        }
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) npc[i].draw(g2);
        }
        player.draw(g2);

        // Draw UI (including dialogue box)
        ui.draw(g2);

        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}