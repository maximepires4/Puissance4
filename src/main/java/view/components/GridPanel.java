package view.components;

import entity.Grid;

import javax.swing.*;
import java.awt.*;

/**
 * Panel plus spécial créant une grille de puissance 4
 */
public class GridPanel extends JPanel {

    private int[][] grid;
    private int[] selectedPlacement = new int[2];

    private Color[] colors = new Color[]{Color.YELLOW, Color.RED};

    public GridPanel(){

    }

    /**
     * Fonction redessinant la grille dynamiquement en fonction de la taille de la fenêtre
     * @param g
     */
    public void paintComponent(Graphics g){
        int width = this.getWidth();
        int height = this.getHeight();
        int posX = 0;
        int posY = 0;

        // Si la grille n'est pas encore initialisée on ne dessine rien
        if(grid == null) return;

        int columns = grid.length;
        int rows = grid[0].length;

        // Le rayon des jetons est calculé dynamiquement en fonction du rapport largeur/colonnes et hauteur/lignes
        // Prendre la valeur minimum permettra le plus grand rayon des jetons sans qu'ils ne se chevauchent
        int radius = Math.min((width / columns), (height / rows));

        // Ces calculs d'écarts permettent de centrer la grille
        int xOffset = (width - radius*columns)/(2*columns);
        int yOffset = (height - radius*rows)/(2*rows);

        // Rectangle bleu
        g.setColor(Color.BLUE);
        g.fillRect(posX, posY, width, height);

        // Dessin des jetons
        // Blanc pour un emplacement vide
        // Coloré pour un joueur
        for(int i = 0; i < columns; i++){
            for(int j = 0; j < rows; j++){
                g.setColor(this.getColor(i,j)); // On définit la couleur en fonction de la valeur de la grille, puisque chaque joueur donne son index comme jeton

                // La sélection actuelle du joueur est dessinée comme un blanc foncé
                if(i == selectedPlacement[0] && j == selectedPlacement[1] && g.getColor() == Color.WHITE){
                    g.setColor(g.getColor().darker());
                }

                g.fillOval(posX + i* width /columns + xOffset, posY + j* height /rows + yOffset, radius, radius);
            }
        }
    }

    /**
     * Renvoi la couleur en fonction de la valeur de la grille à un point donné
     *
     * @param posX
     * @param posY
     * @return
     */
    private Color getColor(int posX, int posY) {
        switch (grid[posX][posY]) {
            case Grid.EMPTY:
                return Color.WHITE;
            case Grid.PLAYER1:
                return colors[0];
            case Grid.PLAYER2:
                return colors[1];
            default:
                return Color.BLACK; // Pour s'assurer qu'il n'y a pas de soucis
        }
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public void setSelectedPlacement(int[] selectedPlacement){
        this.selectedPlacement = selectedPlacement;
    }

    public void setColors(Color[] colors) {
        this.colors = colors;
    }
}