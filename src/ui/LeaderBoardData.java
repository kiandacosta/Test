package ui;

import java.util.HashMap;
import java.util.Map;

public class LeaderBoardData {

    private Object[][] dodgerScores;
    private Object[][] shooterScores;

    public LeaderBoardData() {
        dodgerScores = new Object[][]{
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
        };
        shooterScores = new Object[][]{
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
        };
    }

    public Object[][] getDodgerData() {
        return dodgerScores;
    }

    public Object[][] getShooterData() { return shooterScores;}

    public void insertDodgerScore(String name, int score) {
        Map<String, Integer> scoreMap = new HashMap<>();
        if (dodgerScores[0][0] == null) {
            dodgerScores[0] = new Object[]{name, Integer.toString(score)};
        } else {
            for (int i = 0; i <= 4; i++) {
                if (dodgerScores[i][0] != null) {
                    scoreMap.put((String)dodgerScores[i][0], (Integer)dodgerScores[i][1]);
                }
            }
        }
    }

}
