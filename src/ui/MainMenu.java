package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu implements ActionListener {

    private LeaderBoardData data;
    private JButton dodgeMode;
    private JButton shooterMode;
    private JButton leaderboard;
    private JFrame mainMenu;
    private String playerName;
    private int score;

    public MainMenu(LeaderBoardData d) {
        data = d;
        mainMenu = new JFrame();
        JLabel label = new JLabel();
        this.dodgeMode = new JButton();
        this.shooterMode = new JButton();
        this.leaderboard = new JButton();
        ImageIcon image = new ImageIcon("src/CircleDesign.jpg");
        Border border = BorderFactory.createLineBorder(Color.WHITE, 3);
        label.setText("Ball Dodger!");
        label.setIconTextGap(-25);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("MV Boli", Font.PLAIN, 50));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        //     label.setBorder(border);
        mainMenu.setLayout(new GridLayout(4,1,0,10));
        mainMenu.setTitle("Ball Dodger");
        mainMenu.setSize(new Dimension(600,600));
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu.setResizable(false);
        mainMenu.setIconImage(image.getImage());
        mainMenu.getContentPane().setBackground(Color.BLACK);
        //     mainMenu.setLayout(null);
        mainMenu.add(label);
        setUpButton(dodgeMode, "Dodge Mode");
        setUpButton(shooterMode, "Shooter Mode");
        setUpButton(leaderboard, "Leaderboard");
        mainMenu.setVisible(true);
        mainMenu.setLocationRelativeTo(null);
    }


    public void setUpButton(JButton b, String title) {
        b.addActionListener(this);
        b.setText(title);
        b.setFocusable(false);
        b.setFont(new Font("Comic Sans", Font.BOLD, 50));
        b.setBackground(Color.LIGHT_GRAY);
        b.setBorder(BorderFactory.createEtchedBorder());
        mainMenu.add(b);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dodgeMode) {
            mainMenu.dispose();
        //    mainMenu.setVisible(false);
            new DodgeModeFrame(data);
        }
        else if (e.getSource() == shooterMode) {
            mainMenu.dispose();
            new ShooterModeFrame();
        }
        else if (e.getSource() == leaderboard) {
            mainMenu.dispose();
            new LeaderBoard(data);
        }
    }
}
