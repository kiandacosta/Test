package ui;

import javax.swing.*;

public class DodgeModeFrame extends JPanel {


    public DodgeModeFrame(LeaderBoardData d) {
        JFrame gameFrame = new JFrame();
        JPanel game = new DodgeModePanel(gameFrame, d);
        gameFrame.add(game);
        gameFrame.setTitle("Head Shooter");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.setIconImage(new ImageIcon("src/CircleDesign.jpg").getImage());
        gameFrame.pack();
        gameFrame.setVisible(true);
        gameFrame.setLocationRelativeTo(null);
    }
}
