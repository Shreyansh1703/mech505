package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
	boolean checkDrawTime;

	// Flags for showing abstraction message only once
	private boolean abstractionMessageShown = false;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		// Title State
		if (gp.gameState == gp.titleState) {
			if (code == KeyEvent.VK_ENTER) {
				if (!gp.introComplete) {
					gp.advanceTitleLine();  // Progress through intro
				} else {
					gp.setupGame();         // Start game after intro is fully done
					gp.gameState = gp.playState;
				}
			}
			return;
		}

		// Dialogue State
		if (gp.gameState == gp.dialogueState) {
			if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
				gp.gameState = gp.playState;
			}
			return;
		}

		// Play State
		if (gp.gameState == gp.playState) {

			if ((code == KeyEvent.VK_W || code == KeyEvent.VK_UP) ||
				(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) ||
				(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) ||
				(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT)) {

				// Show abstraction message only the first time a direction key is pressed
				if (!abstractionMessageShown) {
					gp.ui.currentDialogue = "This is an example of ABSTRACTION.\n" +
						"You pressed a direction key, but all the underlying\n" +
						"details like coordinates and graphics are hidden from you!";
					gp.gameState = gp.dialogueState;
					abstractionMessageShown = true;
					return;
				}
			}

			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				upPressed = true;
			}
			if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				downPressed = true;
			}
			if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}
			if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
			if (code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.pauseState;
			}
			if (code == KeyEvent.VK_ENTER) {
				enterPressed = true;
			}

			// DEBUG: Toggle draw time
			if (code == KeyEvent.VK_BACK_QUOTE) {
				checkDrawTime = !checkDrawTime;
			}
		}

		// Pause State
		else if (gp.gameState == gp.pauseState) {
			if (code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.playState;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
	}
}