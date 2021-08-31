package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class LeaderBoard  {

//    private Map<String, Integer> dodgerScores;
    private MyTable dodgerBoard;
    private MyTable shooterBoard;
    private LeaderBoardData data;
    private JFrame leaderFrame;
    private JButton backButton;
    private JScrollPane dodgerPane;
    private JScrollPane shooterPane;
    private ButtonListener buttonListener;
    private String[] columnNames;
    private Object[][] dodgerScores;
    private Object[][] shooterScores;

    public LeaderBoard(LeaderBoardData d) {
        data = d;
        buttonListener = new ButtonListener();
        columnNames = new String[]{"Name", "Score"};
        dodgerScores = d.getDodgerData();
        shooterScores = d.getShooterData();
        initFrame();
        initDodgerBoard();
        initShooterBoard();
        addComponents();
        leaderFrame.setVisible(true);

    }


    public void initFrame() {
        leaderFrame = new JFrame();
        backButton = new JButton("Back");
        backButton.addActionListener(buttonListener);
        ImageIcon image = new ImageIcon("src/CircleDesign.jpg");
        leaderFrame.setLayout(new GridLayout(6,1,0,10));
        leaderFrame.setTitle("Leaderboard");
        leaderFrame.setSize(new Dimension(600,600));
        leaderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        leaderFrame.setResizable(false);
        leaderFrame.setIconImage(image.getImage());
        leaderFrame.getContentPane().setBackground(Color.BLACK);
        leaderFrame.setLocationRelativeTo(null);
    }

    public void initDodgerBoard() {
        dodgerBoard = new MyTable(dodgerScores, columnNames);
        dodgerBoard.setFillsViewportHeight(true);
        dodgerPane = new JScrollPane(dodgerBoard);
    }

    public void initShooterBoard() {
        shooterBoard = new MyTable(shooterScores, columnNames);
        shooterBoard.setFillsViewportHeight(true);
        shooterPane = new JScrollPane(shooterBoard);
    }

    public void addComponents() {
        JLabel dodgerLabel = new JLabel();
        JLabel shooterLabel = new JLabel();
        dodgerLabel.setText("Dodger Leaderboard");
        shooterLabel.setText("Shooter Leaderboard");
        shooterLabel.setIconTextGap(-25);
        shooterLabel.setForeground(Color.WHITE);
        shooterLabel.setFont(new Font("MV Boli", Font.PLAIN, 50));
        shooterLabel.setHorizontalAlignment(JLabel.CENTER);
        shooterLabel.setVerticalAlignment(JLabel.CENTER);
        dodgerLabel.setIconTextGap(-25);
        dodgerLabel.setForeground(Color.WHITE);
        dodgerLabel.setFont(new Font("MV Boli", Font.PLAIN, 50));
        dodgerLabel.setHorizontalAlignment(JLabel.CENTER);
        dodgerLabel.setVerticalAlignment(JLabel.CENTER);
        leaderFrame.add(dodgerLabel);
        leaderFrame.add(dodgerPane);
        leaderFrame.add(shooterLabel);
        leaderFrame.add(shooterPane);
        leaderFrame.add(backButton);
    }

    public class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == backButton) {
                leaderFrame.dispose();
                new MainMenu(data);
            }
        }
    }

    public class MyTable extends JTable {

        public MyTable(Object[][] s, String[] o) {
            super(s,o);
        }
        @Override
        public boolean isCellEditable(int a, int b) {
            return false;
        }
    }
}
