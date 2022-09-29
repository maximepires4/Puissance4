package ai;

import entity.Grid;

public class Style {

    protected static final int MAX = 1000;
    protected static final int MIN = -1000;

    public static final String NAME = "Normal";

    public int evaluateGrid(Grid grid, int relativeToken, boolean max){
        int winner = grid.checkForWin();

        if(winner == relativeToken){
            return MAX;
        } else if(winner == Grid.EMPTY){
            return 0;
        } else {
            return MIN;
        }
    }

    public String getName(){
        return NAME;
    }
}
