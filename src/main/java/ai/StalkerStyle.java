package ai;

import entity.Grid;
import utils.Settings;

public class StalkerStyle extends Style {

    public static final String NAME = "Stalker";

    @Override
    public int evaluateGrid(Grid grid, int relativeToken, boolean max) {
        int important = super.evaluateGrid(grid, relativeToken, max);

        if(important != 0){
            return important;
        }

        int evaluation = 0;

        for(int i = 2; i < Settings.getInstance().getVictoryCount(); i++){
            int addition = (Settings.getInstance().getVictoryCount() - i)*grid.countTokens(i,relativeToken);
            evaluation += (max ? 1 : -1)*addition;
        }

        if(max){
            return Math.min(MAX, evaluation);
        } else {
            return Math.max(MIN, evaluation);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}
