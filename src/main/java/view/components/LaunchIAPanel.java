package view.components;

import ai.*;
import controller.MenuController;
import entity.CPULevel;
import utils.DialogMessage;
import utils.MenuManager;
import utils.Menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel permettant l'initialisation de l'IA
 * On choisira ici sa difficulté et son style
 */
public class LaunchIAPanel extends JPanel implements MenuManager, ActionListener {

    private final MenuController menuController;

    private final JButton launchButton;
    private final JButton returnButton;

    private final JComboBox<Object> cpuLevels;
    private final JComboBox<String> cpuStyle;

    private final java.util.List<CPULevel> cpuLevelList;
    private final String[] styles = new String[]{TurtleStyle.NAME, AggressiveStyle.NAME, StalkerStyle.NAME, KamikazeStyle.NAME};

    public LaunchIAPanel(MenuController menuController){
        this.menuController = menuController;

        // JPanel

        JPanel buttonsPanel = new JPanel();

        // Background

        buttonsPanel.setOpaque(true);
        buttonsPanel.setBackground(Color.BLUE);

        // Layout

        this.setLayout(new GridBagLayout());

        // Elements

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;

        launchButton = new JButton("Launch game");
        returnButton = new JButton("Return");

        launchButton.addActionListener(this);
        returnButton.addActionListener(this);

        cpuLevelList = menuController.getCpuLevels();

        cpuLevels = new JComboBox<>(cpuLevelList.stream().map(CPULevel::getDescription).toArray());

        cpuStyle = new JComboBox<>(styles);

        // Add

        buttonsPanel.add(launchButton);
        buttonsPanel.add(returnButton);

        this.add(new JLabel("<html><font face='arial' size='5' color='orange'>Select IA level</font></html>"), gridBagConstraints);
        this.add(cpuLevels, gridBagConstraints);
        this.add(new JLabel("<html><font face='arial' size='5' color='orange'>Select IA style</font></html>"), gridBagConstraints);
        this.add(cpuStyle, gridBagConstraints);
        this.add(buttonsPanel, gridBagConstraints);
    }

    @Override
    public Menus getMenu() {
        return Menus.GAME_IA;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        DialogMessage dialogMessage = null;
        
        if(actionEvent.getSource().equals(launchButton)){
            // Récupère le CPU sélectionné
            CPULevel cpuLevel = cpuLevelList.stream().filter(c -> c.getDescription().equals(cpuLevels.getSelectedItem())).findFirst().get();

            // Ajoute le bon style au CPU
            cpuLevel.setStyle(mapStringToStyle(styles[cpuStyle.getSelectedIndex()]));

            // On lance le jeu contre l'IA
            dialogMessage = menuController.launchGame(cpuLevel);

        } else if(actionEvent.getSource().equals(returnButton)){
            menuController.changeMenu(Menus.CONNEXION);
        }
        
        if(dialogMessage != null){
            JOptionPane.showMessageDialog(this, dialogMessage.getMessage(), dialogMessage.getTitle(), dialogMessage.getType());
        }
    }

    private Style mapStringToStyle(String style) {
        if(style.equals(TurtleStyle.NAME)){
            return new TurtleStyle();
        } else if(style.equals(AggressiveStyle.NAME)){
            return new AggressiveStyle();
        } else if(style.equals(StalkerStyle.NAME)){
            return new StalkerStyle();
        } else if(style.equals(KamikazeStyle.NAME)){
            return new KamikazeStyle();
        } else {
            return new Style();
        }
    }
}
