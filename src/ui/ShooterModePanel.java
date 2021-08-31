package ui;

import model.EnemyHead;
import model.Missile;
import model.PlayerHead;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShooterModePanel extends JPanel implements ActionListener {

    // unit size 50, player delay 75, enemy delay 500!
    // small size: unit size 10, player del 50, enemy del 100
    public static int UNIT_SIZE = 50;
    public static int SCREEN_WIDTH = 600;
    public static int SCREEN_HEIGHT = 600;
    public static int PLAYER_DELAY = 25;
    public static int ENEMY_DELAY = 650;
    private LeaderBoardData data;
    private boolean isGameOver;
    private int drawOnce = 0;
    private Timer playerTimer;
    private Timer enemyTimer;
    private EnemyListener enemyListener;
    private PlayerHead player;
    private JFrame gameFrame;
    private List<Missile> missiles;
    private List<EnemyHead> enemies;

    public ShooterModePanel(JFrame gameFrame) {
        data = new LeaderBoardData();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.gameFrame = gameFrame;
        startGame();
    }

    public void startGame() {
        isGameOver = false;
        enemies = new ArrayList<>();
        missiles = new ArrayList<>();
        enemyListener = new EnemyListener();
        generateEnemy();
        generatePlayer();
        playerTimer = new Timer(PLAYER_DELAY,this);
        enemyTimer = new Timer(ENEMY_DELAY, enemyListener);
        playerTimer.start();
        enemyTimer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.green);
        g.fillOval(0, player.getHeight(), UNIT_SIZE, UNIT_SIZE);
        for (EnemyHead e : enemies) {
            g.setColor(Color.CYAN);
            g.fillOval(e.getX(), e.getY(), UNIT_SIZE, UNIT_SIZE);
        }
        for (Missile m : missiles) {
            g.setColor(Color.WHITE);
            g.fillOval(m.getX(), m.getY(), UNIT_SIZE/3, UNIT_SIZE/3);
        }
        if (isGameOver && drawOnce == 0) {
            gameOverText();
            drawOnce++;
        }
    }

    private void gameOverText() {
        JLabel label = new JLabel();
        JLabel pressR = new JLabel("Press R To Play Again");
        pressR.setFont(new Font("MV Boli", Font.BOLD, 20));
        pressR.setForeground(Color.WHITE);
        pressR.setLocation(SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
        label.setText("GAME OVER LOSER!");
        label.setFont(new Font("MV Boli", Font.BOLD, 20));
        label.setForeground(Color.BLACK);
        //   label.setLocation(SCREEN_WIDTH/2, SCREEN_HEIGHT/6);
        add(label);
        add(pressR);
        label.setVisible(true);
        revalidate();
    }

    public void generateEnemy() {
        EnemyHead newEnemy = new EnemyHead();
        enemies.add(newEnemy);
    }

    public void generatePlayer() {
        player = new PlayerHead();
    }

    public void move() {
        if (player.getDirection()) {
            player.moveUp();
        } else {
            player.moveDown();
        }
    }

    public void shoot() {
        Missile missile = new Missile(player);
        missiles.add(missile);
    }

    public void moveEnemies() {
        for (EnemyHead e : enemies) {
            e.move();
        }
    }

    public void moveMissiles() {
        for (Missile m : missiles) {
            m.move();
        }
    }

    public void removeEnemies() {
        Iterator<EnemyHead> it = enemies.iterator();
        while (it.hasNext()) {
            if (it.next().getX() < -UNIT_SIZE) {
                it.remove();
            }
        }
    }

    public void removeMissiles() {
        Iterator<Missile> it = missiles.iterator();
        while (it.hasNext()) {
            if (it.next().getX() == SCREEN_WIDTH + UNIT_SIZE) {
                it.remove();
            }
        }
    }

    public void checkCollisions() {
        for (EnemyHead e : enemies) {
            if (playerCollided(e)) {
                isGameOver = true;
            }
        }
//        Iterator<Missile> missileIt = missiles.iterator();
//        while (missileIt.hasNext()) {
//            Iterator<EnemyHead> enemyIt = missileIt.next().getPotentialTargets().iterator();
//            while (enemyIt.hasNext()) {
//                if (missileHit(missileIt.next(), enemyIt.next())) {
//                    enemyIt.remove();
//                    missileIt.remove();
//                }
//            }
//        }
    }

    public boolean missileHit(Missile m, EnemyHead e) {
        Boolean xEquals = e.getX() - UNIT_SIZE == m.getX();
        Boolean yEquals = e.getY() - UNIT_SIZE <= m.getY() && m.getY() <= e.getY() + UNIT_SIZE;
        return xEquals && yEquals;
    }

    public boolean missileInRange(Missile m, EnemyHead e) {
        return e.getY() - UNIT_SIZE <= m.getY() && m.getY() <= e.getY() + UNIT_SIZE;
    }

    public boolean playerCollided(EnemyHead e) {
        if (e.getX() < UNIT_SIZE) {
            if ((player.getHeight() - UNIT_SIZE < e.getY()) && (e.getY() < player.getHeight() + UNIT_SIZE)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            move();
            checkCollisions();
            moveEnemies();
            moveMissiles();
            removeEnemies();
            removeMissiles();
        } else {
            playerTimer.stop();
            enemyTimer.stop();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (!player.getDirection()) {
                        player.directionUp();
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (player.getDirection()) {
                        player.directionDown();
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    shoot();
                    break;
                case KeyEvent.VK_R:
                    if (isGameOver) {
                        gameFrame.dispose();
                        new ShooterModeFrame();
                    }
                    break;
                case KeyEvent.VK_B:
                    if (isGameOver) {
                        gameFrame.dispose();
                        new MainMenu(data);
                    }
                    break;
            }
        }
    }

    public class EnemyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isGameOver) {
                generateEnemy();
            }
        }
    }
}
