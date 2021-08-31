package model;

import ui.DodgeModePanel;

import javax.swing.*;

public class PlayerHead {

    public static int PLAYER_SPEED = 10;
    private boolean direction;
    private int height;
    private ImageIcon image;

    public PlayerHead() {
        this.direction = true;
        this.height = DodgeModePanel.SCREEN_HEIGHT/2;
        this.image = new ImageIcon("CircleDesign.jpg");
    }

    public int getHeight() {
        return height;
    }

    public void moveUp() {
        if (height != 0) {
            height = height - DodgeModePanel.UNIT_SIZE;
        }
    }

    public void moveDown() {
        if (height != DodgeModePanel.SCREEN_HEIGHT - DodgeModePanel.UNIT_SIZE) {
            height = height + DodgeModePanel.UNIT_SIZE;
        }
    }

    public boolean getDirection() {
        return direction;
    }

    public void directionUp() {
        this.direction = true;
    }

    public void directionDown() {
        this.direction = false;
    }

    public void shoot() {}

}
