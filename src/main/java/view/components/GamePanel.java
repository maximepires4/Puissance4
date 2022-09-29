package view.components;

import controller.*;
import entity.CPULevel;
import entity.Grid;
import entity.Playable;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Menu de jeu entre deux joueurs ou l'ia
 */
public class GamePanel extends JPanel implements MenuManager, KeyListener {

    private final GameController gameController;

    private final GridPanel gridPanel; // Panel de la grille

    private final JPanel infoGamePanel;
    private final JPanel infoEndPanel;

    private final JLabel player1;
    private final JLabel player2;
    private final JLabel playerTurn;
    private final JLabel infoLabel;
    private final JLabel endLabel;

    private boolean gameFinished = false;

    public GamePanel(GameController gameController) {
        this.gameController = gameController;

        // Panel
        gridPanel = new GridPanel();
        JPanel infoPanel = new JPanel();
        infoGamePanel = new JPanel();
        infoEndPanel = new JPanel();
        JPanel versusPanel = new JPanel();
        JPanel turnPanel = new JPanel();

        infoEndPanel.setVisible(false);

        // Background

        infoPanel.setOpaque(true);
        infoPanel.setBackground(Color.BLUE);
        infoGamePanel.setOpaque(true);
        infoGamePanel.setBackground(Color.BLUE);
        infoEndPanel.setOpaque(true);
        infoEndPanel.setBackground(Color.BLUE);
        versusPanel.setOpaque(true);
        versusPanel.setBackground(Color.BLUE);
        turnPanel.setOpaque(true);
        turnPanel.setBackground(Color.BLUE);

        // Layout

        this.setLayout(new BorderLayout());
        infoGamePanel.setLayout(new GridBagLayout());
        infoEndPanel.setLayout(new GridBagLayout());

        // Elements

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;

        Font police = new Font("Arial",Font.BOLD,13);

        player1 = new JLabel("Player 1");
        player1.setFont(police);
        player1.setHorizontalAlignment(JLabel.CENTER);
        player1.setVerticalAlignment(JLabel.CENTER);

        player2 = new JLabel("Player 2");
        player2.setFont(police);
        player2.setHorizontalAlignment(JLabel.CENTER);
        player2.setVerticalAlignment(JLabel.CENTER);

        playerTurn = new JLabel("Player");
        playerTurn.setFont(police);
        playerTurn.setHorizontalAlignment(JLabel.CENTER);
        playerTurn.setVerticalAlignment(JLabel.CENTER);

        player1.setForeground(Color.WHITE);
        player2.setForeground(Color.WHITE);
        playerTurn.setForeground(Color.WHITE);

        infoLabel = new JLabel("Move the ghost with arrows, press Enter to place your piece");
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setAlignmentX(CENTER_ALIGNMENT);

        endLabel = new JLabel();
        endLabel.setForeground(Color.WHITE);

        // Add

        versusPanel.add(player1);
        versusPanel.add(new JLabel("<html><font face='arial' size='8' color='orange'> vs </font></html>"));
        versusPanel.add(player2);

        turnPanel.add(new JLabel("<html><font face='arial' size='8' color='orange'>Player's turn : </font></html>"));
        turnPanel.add(playerTurn);

        infoGamePanel.add(versusPanel, gridBagConstraints);
        infoGamePanel.add(turnPanel, gridBagConstraints);

        infoEndPanel.add(endLabel);

        infoPanel.add(infoGamePanel);
        infoPanel.add(infoEndPanel);

        this.add(infoPanel, BorderLayout.NORTH);
        this.add(gridPanel, BorderLayout.CENTER);
        this.add(infoLabel, BorderLayout.SOUTH);

        // Listener

        this.addKeyListener(this);
    }

    public void updatePanel(int[] currentPlacement, Grid grid, Playable[] playable, Playable currentPlayable, boolean gameFinished){
        this.requestFocus();

        this.gameFinished = gameFinished;

        Color[] colors = Settings.getInstance().getColors();

        player1.setText("<html><font face='arial' size='8'>" + playable[0].getUsername() + " </font></html>");
        player1.setForeground(colors[0]);

        player2.setText("<html><font face='arial' size='8'> " + playable[1].getUsername() + "</font></html>");
        player2.setForeground(colors[1]);

        playerTurn.setText("<html><font face='arial' size='8'> " + currentPlayable.getUsername() + "</font></html>");
        playerTurn.setForeground(playable[0].compareTo(currentPlayable) == 0 ? colors[0] : colors[1]);

        gridPanel.setSelectedPlacement(currentPlacement);
        gridPanel.setGrid(grid.getValues());
        gridPanel.setColors(colors);

        infoGamePanel.setVisible(!gameFinished);
        infoEndPanel.setVisible(gameFinished);

        if(grid.isGridFull()){
            endLabel.setText("<html><font face='arial' size='8'>Draw!</font></html>");
        } else {
            endLabel.setText("<html><font face='arial' size='8'>" + currentPlayable.getUsername() + " won the match!</font></html>");
        }

        infoLabel.setText("<html><font face='arial' size='8'>" + (gameFinished ? "Press Enter to return to connection menu" : "Move the ghost with arrows, press Enter to place your piece") + "</font></html>");

        if(!gameFinished && currentPlayable instanceof CPULevel){
            gameController.endTurn();
        }
    }

    @Override
    public Menus getMenu() {
        return Menus.GAME;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(gameController.getMenu() == getMenu()){
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    gameController.moveLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    gameController.moveRight();
                    break;
                case KeyEvent.VK_ENTER:
                    if(gameFinished){
                        gameController.changeMenu(Menus.CONNEXION);
                    } else {
                        DialogMessage dialogMessage = gameController.endTurn();
                        if(dialogMessage != null){
                            JOptionPane.showMessageDialog(this, dialogMessage.getMessage(), "End of the game", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    break;
            }
            this.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
