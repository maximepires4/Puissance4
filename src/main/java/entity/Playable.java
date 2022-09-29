package entity;

public abstract class Playable implements Comparable<Playable>{

    protected int tokenValueInGrid;

    protected String username;

    public abstract String getUsername();

    public void setTokenValueInGrid(int value){
        this.tokenValueInGrid = value;
    }

    public abstract boolean placeToken(Grid grid, int column, int row);
}
