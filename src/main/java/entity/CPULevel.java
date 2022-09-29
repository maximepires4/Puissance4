package entity;

import ai.Style;

import java.util.ArrayList;
import java.util.List;

public class CPULevel extends Playable implements Comparable<Playable> {

    private final int id;
    private final String description;
    private final int depth;

    private Style style;

    public CPULevel(int id, String description, int depth) {
        this.id = id;
        this.description = description;
        this.depth = depth;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    @Override
    public String toString() {
        return "CPULevel{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", depth=" + depth +
                '}';
    }

    @Override
    public String getUsername() {
        return "IA " + getDescription() + (style != null ? " " + style.getName() : "");
    }

    @Override
    public boolean placeToken(Grid grid, int column, int row) {
        int value = Integer.MIN_VALUE;

        List<Integer[]> possibilities = new ArrayList<>();

        for(int i = 0; i < grid.getColumns(); i++){
            for(int j = grid.getRows()-1; j >= 0; j--){
                if(grid.isColumnFull(i)) continue;
                if(grid.getValues()[i][j] == Grid.EMPTY){
                    Grid newGrid = grid.clone();
                    newGrid.setValue(i,j,tokenValueInGrid);
                    int newValue = minimaxAlphaBeta(newGrid,this.depth, false, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if(newValue == value){
                        possibilities.add(new Integer[]{i,j,newValue});
                    } else if(newValue > value){
                        possibilities.clear();
                        value = newValue;
                        possibilities.add(new Integer[]{i,j,newValue});
                    }
                    break;
                }
            }
        }

        int rand = (int)(Math.random()*possibilities.size());
        return grid.setValue(possibilities.get(rand)[0],possibilities.get(rand)[1], tokenValueInGrid);
    }

    private int minimaxAlphaBeta(Grid grid, int depth, boolean max, boolean iaPlay, int alpha, int beta){
        if(depth == 0 || grid.isGridFull() || grid.checkForWin() != Grid.EMPTY){
            return (depth+1)*style.evaluateGrid(grid, tokenValueInGrid, max);
        }

        int value = 0;

        if(max) value = Integer.MIN_VALUE;
        else value = Integer.MAX_VALUE;

        for(int i = 0; i < grid.getColumns(); i++){
            for(int j = grid.getRows()-1; j >= 0; j--){
                if(grid.isColumnFull(i)) continue;
                if(grid.getValues()[i][j] == Grid.EMPTY){
                    Grid newGrid = grid.clone();
                    newGrid.setValue(i,j,iaPlay ? tokenValueInGrid : (tokenValueInGrid == 0 ? 1 : 0));
                    if(max){
                        value = Math.max(value, minimaxAlphaBeta(newGrid,depth-1, false, !iaPlay, alpha, beta));
                        if(value >= beta){
                            return value;
                        }
                        alpha = Math.max(alpha, value);
                    } else {
                        value = Math.min(value, minimaxAlphaBeta(newGrid,depth-1, true, !iaPlay, alpha, beta));
                        if(alpha >= value){
                            return value;
                        }
                        beta = Math.min(beta, value);

                    }
                    break;
                }
            }
        }

        return value;
    }

    private int evaluate(Grid grid, int relatives) {
        int winner = grid.checkForWin();

        if(winner == relatives){
            return 100;
        } else if(winner == Grid.EMPTY){
            return 0;
        } else {
            return -100;
        }
    }

    @Override
    public int compareTo(Playable playable) {
        if(playable instanceof CPULevel){
            CPULevel c = (CPULevel) playable;
            return Integer.compare(getId(), c.getId());
        } else {
            return 1;
        }
    }

    public static List<CPULevel> getDefaultCpuLevels(){
        List<CPULevel> cpuLevelList = new ArrayList<>();

        cpuLevelList.add(new CPULevel(1,"Very easy",1));
        cpuLevelList.add(new CPULevel(2,"Easy",3));
        cpuLevelList.add(new CPULevel(3,"Normal",5));
        cpuLevelList.add(new CPULevel(4,"Hard",7));
        cpuLevelList.add(new CPULevel(5,"Insane",9));
        cpuLevelList.add(new CPULevel(6,"Mr.Mauti",11));

        return cpuLevelList;
    }
}

