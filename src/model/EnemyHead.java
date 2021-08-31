package model;

import ui.DodgeModePanel;

import java.util.Random;

public class EnemyHead {

    private int speed;
    private int y;
    private int x;

    public EnemyHead() {
        this.speed = new Random().nextInt(20) + 1;
        this.y = new Random().nextInt(DodgeModePanel.SCREEN_WIDTH/ DodgeModePanel.UNIT_SIZE) * DodgeModePanel.UNIT_SIZE;
        this.x = DodgeModePanel.SCREEN_WIDTH- DodgeModePanel.UNIT_SIZE;
    }

    public void move() {
        x = x - speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
