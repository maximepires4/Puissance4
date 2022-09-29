package view.components;

import controller.MenuController;
import utils.DialogMessage;
import utils.MenuManager;
import utils.Menus;
import utils.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel implements MenuManager, ActionListener {

    private final MenuController menuController;

    private final JButton returnButton;

    private final JComboBox<Integer> columnComboBox;
    private final JComboBox<Integer> rowComboBox;
    private final JComboBox<String> resolutionsCombobox;
    private final JComboBox<String> colors1Combobox;
    private final JComboBox<String> colors2Combobox;
    private final JComboBox<Integer> victoryCountCombobox;

    private final Integer[] rowAndColumCount = {5, 6, 7, 8, 9, 10};
    private final String[] resolutions = {"1920x1080", "1280x720", "900x750"};
    private final String[] colorsArray = {"Yellow", "Red", "Green", "Magenta", "Orange", "Pink"};
    private final Integer[] victoryCounts = {3,4,5};
    private Font police = new Font("Arial",Font.BOLD,12);

    public SettingsPanel(MenuController menuController) {
        this.menuController = menuController;

        JPanel rowsAndColumnsPanel = new JPanel();
        JPanel resolutionSettingsPanel = new JPanel();
        JPanel colorsPanel = new JPanel();
        JPanel victoryCountPanel = new JPanel();

        // Background

        rowsAndColumnsPanel.setOpaque(true);
        rowsAndColumnsPanel.setBackground(Color.blue);
        resolutionSettingsPanel.setOpaque(true);
        resolutionSettingsPanel.setBackground(Color.blue);
        colorsPanel.setOpaque(true);
        colorsPanel.setBackground(Color.blue);
        victoryCountPanel.setOpaque(true);
        victoryCountPanel.setBackground(Color.blue);


        // Layout

        this.setLayout(new GridBagLayout());
        rowsAndColumnsPanel.setLayout(new GridLayout(0, 2));
        colorsPanel.setLayout(new GridLayout(0, 1));
        victoryCountPanel.setLayout(new GridLayout(0,1));

        // Border

        rowsAndColumnsPanel.setBorder(BorderFactory.createTitledBorder("<html><font face='arial' size='5' color='orange'>Rows and Columns</font></html>"));
        resolutionSettingsPanel.setBorder(BorderFactory.createTitledBorder("<html><font face='arial' size='5' color='orange'>Resolution</font></html>"));
        colorsPanel.setBorder(BorderFactory.createTitledBorder("<html><font face='arial' size='5' color='orange'>Colors</font></html>"));
        victoryCountPanel.setBorder(BorderFactory.createTitledBorder("<html><font face='arial' size='5' color='orange'> </font></html>"));

        // Elements

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;

        JLabel title = new JLabel("<html><font face='arial' size='8' color='orange'>Settings</font></html>");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);

        columnComboBox = new JComboBox<>(rowAndColumCount);

        rowComboBox = new JComboBox<>(rowAndColumCount);

        colors1Combobox = new JComboBox<>(colorsArray);
        colors2Combobox = new JComboBox<>(colorsArray);

        victoryCountCombobox = new JComboBox<>(victoryCounts);

        returnButton = new JButton("Save & Return to main menu");
        returnButton.addActionListener(this);

        resolutionsCombobox = new JComboBox<>(resolutions);

        // Add

        rowsAndColumnsPanel.add(new JLabel("<html><font face='arial' size='3' color='orange'>Number of columns : </font></html>"));
        rowsAndColumnsPanel.add(columnComboBox);

        rowsAndColumnsPanel.add(new JLabel("<html><font face='arial' size='3' color='orange'>Number of rows : </font></html>"));
        rowsAndColumnsPanel.add(rowComboBox);

        victoryCountPanel.add(new JLabel("<html><font face='arial' size='5' color='orange'>Victory count : </font></html>"));
        victoryCountPanel.add(victoryCountCombobox);

        resolutionSettingsPanel.add(new JLabel("<html><font face='arial' size='3' color='orange'>Resolution (restart needed) : </font></html>"));
        resolutionSettingsPanel.add(resolutionsCombobox);

        colorsPanel.add(colors1Combobox);
        colorsPanel.add(colors2Combobox);

        this.add(title, gridBagConstraints);
        this.add(rowsAndColumnsPanel, gridBagConstraints);
        this.add(victoryCountPanel, gridBagConstraints);
        this.add(colorsPanel, gridBagConstraints);
        this.add(resolutionSettingsPanel, gridBagConstraints);
        this.add(returnButton, gridBagConstraints);
    }

    public void updatePanel(){

        columnComboBox.setSelectedItem(Settings.getInstance().getColumns());
        rowComboBox.setSelectedItem(Settings.getInstance().getRows());
        Color[] colors = Settings.getInstance().getColors();
        colors1Combobox.setSelectedItem(mapColorToString(colors[0]));
        colors2Combobox.setSelectedItem(mapColorToString(colors[1]));
        victoryCountCombobox.setSelectedItem(Settings.getInstance().getVictoryCount());
        resolutionsCombobox.setSelectedItem(Settings.getInstance().getWidth() + "x" + Settings.getInstance().getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(returnButton)) {
            if (updateSettings()) {
                menuController.changeMenu(Menus.MAINMENU);
            }
        }
    }

    private boolean updateSettings() {
        int columns = rowAndColumCount[columnComboBox.getSelectedIndex()];
        int rows = rowAndColumCount[rowComboBox.getSelectedIndex()];
        String resolution = resolutions[resolutionsCombobox.getSelectedIndex()];
        Color color1 = mapStringToColor(colorsArray[colors1Combobox.getSelectedIndex()]);
        Color color2 = mapStringToColor(colorsArray[colors2Combobox.getSelectedIndex()]);
        int victoryCount = victoryCounts[victoryCountCombobox.getSelectedIndex()];
        if (color1 != color2) {
            Settings.getInstance().setColumns(columns);
            Settings.getInstance().setRows(rows);
            Settings.getInstance().setWidth(Integer.parseInt(resolution.substring(0, resolution.indexOf("x"))));
            Settings.getInstance().setHeight(Integer.parseInt(resolution.substring(resolution.indexOf("x")+1)));
            Settings.getInstance().setPlayer1Color(color1);
            Settings.getInstance().setPlayer2Color(color2);
            Settings.getInstance().setVictoryCount(victoryCount);
            return true;
        } else {
            JOptionPane.showMessageDialog(this, DialogMessage.COLORS_SAME.getMessage(), DialogMessage.COLORS_SAME.getTitle(), DialogMessage.ERROR.getType());
            return false;
        }
    }

    private Color mapStringToColor(String color) {
        if (color.equals("Yellow")) {
            return Color.YELLOW;
        } else if (color.equals("Red")) {
            return Color.RED;
        } else if (color.equals("Green")) {
            return Color.GREEN;
        } else if (color.equals("Magenta")) {
            return Color.MAGENTA;
        } else if (color.equals("Orange")) {
            return new Color(255, 128, 2);
        } else if (color.equals("Pink")) {
            return new Color(236, 36, 177);
        } else {
            return Color.BLACK;
        }

    }

    private String mapColorToString(Color color) {
        if (Color.YELLOW.equals(color)) {
            return "Yellow";
        } else if (Color.RED.equals(color)) {
            return "Red";
        } else if (Color.GREEN.equals(color)) {
            return "Green";
        } else if (Color.MAGENTA.equals(color)) {
            return "Magenta";
        } else if (color.getRed()==255 && color.getGreen()==128 && color.getBlue()==2) {
            return "Orange";
        } else if (color.getRed()==236 && color.getGreen()==36 && color.getBlue()==177) {
            return "Pink";
        } else {
            return "Black";
        }
    }

    @Override
    public Menus getMenu() {
        return Menus.SETTINGS;
    }
}
