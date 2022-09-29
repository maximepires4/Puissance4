package entity;

import utils.Permissions;

public class Player extends Playable implements Comparable<Playable>{
    private int id;
    private String username;
    private String email;
    private String password;
    private Permissions permission;

    public Player(int id, String username, String email, String password, Permissions permission) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.permission = permission;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Permissions getPermission() {return permission;}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPermission(Permissions permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", permission=" + permission +
                '}';
    }

    @Override
    public int compareTo(Playable playable) {
        if(playable instanceof Player){
            Player p = (Player) playable;
            return Integer.compare(getId(), p.getId());
        } else {
            return 1;
        }
    }

    @Override
    public boolean placeToken(Grid grid, int colum, int row) {
        return grid.setValue(colum, row, tokenValueInGrid);
    }
}
