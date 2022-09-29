package entity;

import utils.Settings;

import java.util.Arrays;

public class Grid {
    private final int[][] values;

    public static final int EMPTY = -1;
    public static final int PLAYER1 = 0;
    public static final int PLAYER2 = 1;

    public Grid(int columns, int rows){
        values = new int[columns][rows];

        for (int[] value : values) {
            Arrays.fill(value, EMPTY);
        }
    }

    public boolean setValue(int column, int row, int value){
        if(!isGridFull()) {
            values[column][row] = value;
            return true;
        } else {
            return false;
        }
    }

    public boolean isGridFull(){
        for (int[] value : values) {
            for (int i : value) {
                if (i == EMPTY) return false;
            }
        }

        return true;
    }

    /*
     * TODO: Améliorer, pas obliger de tester les deux dernieres colonnes ???
     */
    public int checkForWin(){
        return countTokens(Settings.getInstance().getVictoryCount(), EMPTY);
    }

    public int countTokens(int countTarget, int relativeToken){

        int count = EMPTY;
        boolean victory = countTarget == Settings.getInstance().getVictoryCount();

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                if (values[i][j] == EMPTY || (!victory && values[i][j] != relativeToken)) continue;

                for (int k = 0; k < countTarget; k++) {
                    if (j + k >= values[i].length || values[i][j + k] != values[i][j]) {
                        break;
                    }

                    if (k == countTarget-1){
                        if(victory) {
                            return values[i][j];
                        } else {
                            count++;
                        }
                    }
                }

                for (int k = 0; k < countTarget; k++) {
                    if (i + k >= values.length || values[i + k][j] != values[i][j]) {
                        break;
                    }

                    if (k == countTarget-1){
                        if(victory) {
                            return values[i][j];
                        } else {
                            count++;
                        }
                    }
                }

                for (int k = 0; k < countTarget; k++) {
                    if (j + k >= values[i].length || i + k >= values.length || values[i + k][j + k] != values[i][j]) {
                        break;
                    }

                    if (k == countTarget-1){
                        if(victory) {
                            return values[i][j];
                        } else {
                            count++;
                        }
                    }
                }

                for (int k = 0; k < countTarget; k++) {
                    if (j - k < 0 || i + k >= values.length || values[i + k][j - k] != values[i][j]) {
                        break;
                    }

                    if (k == countTarget-1){
                        if(victory) {
                            return values[i][j];
                        } else {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }

    public int[][] getValues() {
        return values;
    }

    public int getColumns(){
        return values.length;
    }

    public int getRows(){
        return values[0].length;
    }

    public boolean isColumnFull(int column) {
        for(int value : values[column]){
            if(value == EMPTY) return false;
        }

        return true;
    }

    private int getValue(int column, int row) {
        return values[column][row];
    }

    @Override
    protected Grid clone() {
        Grid newGrid = new Grid(this.getColumns(), this.getRows());
        for(int i = 0; i < this.getColumns(); i++){
            for(int j = 0; j < this.getRows(); j++){
                newGrid.setValue(i,j,this.getValue(i,j));
            }
        }

        return newGrid;
    }
}
