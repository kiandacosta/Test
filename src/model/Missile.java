package model;

import java.util.ArrayList;
import java.util.List;

public class Missile {

    public static int ATTACK_POWER = 10;
    public static int MISSILE_SPEED = 10;
    public int y;
    public int x;

    public Missile(PlayerHead p) {
        this.x = 0;
        this.y = p.getHeight();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move() {
        this.x = x + MISSILE_SPEED;
    }
}
