package model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessinAround implements ActionListener {

    private Timer scoreTimer;
    private int initialScore = 0;
    private JFrame frame;
    private JPanel panel;
    private JLabel score;

    public MessinAround() {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(1);
        list.add(15);
        list.add(14);
        Collections.sort(list);
        Collections.reverse(list);
        System.out.println(list);


    }

    public static void main(String[] args) {
        new MessinAround();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        score.setText(Integer.toString(initialScore));
        panel.add(score);
        initialScore += 10;


    }
}
