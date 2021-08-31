package ui;

import model.EnemyHead;
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

public class DodgeModePanel extends JPanel  {

    // unit size 50, player delay 75, enemy delay 500!
    // small size: unit size 10, player del 50, enemy del 100
    public static int UNIT_SIZE = 10;
    public static int SCREEN_WIDTH = 600;
    public static int SCREEN_HEIGHT = 600;
    public static int PLAYER_DELAY = 10;
    public static int ENEMY_DELAY = 100;
    public static int SCORE_DELAY = 250;
    private LeaderBoardData data;
    private int score;
    private String playerName;
    private boolean isGameOver;
    private int drawOnce = 0;
    private Timer playerTimer;
    private Timer enemyTimer;
    private Timer scoreTimer;
    private PlayerListener playerListener;
    private EnemyListener enemyListener;
    private ScoreListener scoreListener;
    private PlayerHead player;
    private JFrame gameFrame;
    private JLabel scoreText;
    private List<EnemyHead> enemies;

    public DodgeModePanel(JFrame gameFrame, LeaderBoardData d) {
        data = d;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.gameFrame = gameFrame;
        startGame();
    }

    public void startGame() {
        isGameOver = false;
        score = 0;
        enemies = new ArrayList<>();
        enemyListener = new EnemyListener();
        scoreListener = new ScoreListener();
        playerListener = new PlayerListener();
        scoreText = new JLabel();
        scoreText.setFont(new Font("MV Boli", Font.BOLD, 20));
        scoreText.setForeground(Color.WHITE);
        add(scoreText);
        generateEnemy();
        generatePlayer();
        playerTimer = new Timer(PLAYER_DELAY, playerListener);
        enemyTimer = new Timer(ENEMY_DELAY, enemyListener);
        scoreTimer = new Timer(SCORE_DELAY, scoreListener);
        playerTimer.start();
        enemyTimer.start();
        scoreTimer.start();
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
        if (isGameOver && drawOnce == 0) {
            gameOverText();
            leaderBoardText();
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
        this.add(label);
        add(pressR);
        label.setVisible(true);
        revalidate();
    }

    private void leaderBoardText() {
        JLabel label = new JLabel();
        label.setText("Press L to Enter Score");
        label.setFont(new Font("MV Boli", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
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

    public void moveEnemies() {
        for (EnemyHead e : enemies) {
            e.move();
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

    public void checkCollisions() {
        for (EnemyHead e : enemies) {
            if (hasCollided(e)) {
                isGameOver = true;
            }
        }
    }

    public boolean hasCollided(EnemyHead e) {
        if (e.getX() < UNIT_SIZE) {
            if ((player.getHeight() - UNIT_SIZE < e.getY()) && (e.getY() < player.getHeight() + UNIT_SIZE)) {
                return true;
            }
        }
        return false;
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
                case KeyEvent.VK_R:
                    if (isGameOver) {
                        gameFrame.dispose();
                        new DodgeModeFrame(data);
                    }
                    break;
                case KeyEvent.VK_B:
                    if (isGameOver) {
                        gameFrame.dispose();
                        new MainMenu(data);
                    }
                    break;
                case KeyEvent.VK_E:
                    if (isGameOver) {
                        enterName();
                    }
            }
        }
    }

    public void enterName() {
        JFrame frame = new JFrame();
        JLabel label = new JLabel("Enter Name:");
        JButton submit = new JButton("Submit Score!");
        JTextField text = new JTextField();
        class ButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == submit) {
                    playerName = text.getText();
                    score -= 10; // Score delay glitch
                    data.insertDodgerScore(playerName, score);
                    frame.dispose();
                }
            }
        }
        submit.addActionListener(new ButtonListener());
        frame.setTitle("Enter Name");
        frame.setResizable(false);
        frame.setLayout(new GridLayout(3, 1));
        frame.setSize(new Dimension(300, 200));
        frame.add(label);
        frame.add(text);
        frame.add(submit);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public class PlayerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isGameOver) {
                move();
                checkCollisions();
                moveEnemies();
                removeEnemies();
            } else {
                scoreTimer.stop();
                playerTimer.stop();
                enemyTimer.stop();
            //    checkScore();
            }
            repaint();
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

    public class ScoreListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            scoreText.setText("Score: " + score);
            score += 10;
        }
    }
}
