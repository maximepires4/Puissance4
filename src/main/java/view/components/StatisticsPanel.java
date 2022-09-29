package view.components;

import controller.StatisticsController;
import entity.Player;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import utils.MenuManager;
import utils.Menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * https://www.tutorialspoint.com/jfreechart/jfreechart_pie_chart.htm
 */
public class StatisticsPanel extends JPanel implements MenuManager {

    private final StatisticsController statisticsController;

    private final JPanel statsPanel;

    public StatisticsPanel(StatisticsController statisticsController){
        this.statisticsController = statisticsController;
        this.setLayout(new BorderLayout());

        statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(0,1));

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(e -> statisticsController.changeMenu());

        this.add(statsPanel, BorderLayout.CENTER);
        this.add(returnButton, BorderLayout.SOUTH);
    }

    public void updatePanel(){
        statsPanel.removeAll();

        JFreeChart iaVictoryRate = ChartFactory.createPieChart("IA Victory rate",iaVictoryRateDataset(),true,true,false);
        JFreeChart playerInfo = ChartFactory.createXYLineChart(
                "Players victory rate per game played",
                "Game played" ,
                "Victory rate" ,
                playerInfoDataset() ,
                PlotOrientation.VERTICAL ,
                true , true , false);

        final XYPlot plot = playerInfo.getXYPlot( );

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        for(int i = 0; i < playerInfo.getXYPlot().getDatasetCount(); i++){
            renderer.setSeriesPaint(i, new Color(i/playerInfo.getXYPlot().getDatasetCount()));
            renderer.setSeriesStroke(i, new BasicStroke(4.0f));
        }
        plot.setRenderer( renderer );

        statsPanel.add(new ChartPanel(iaVictoryRate));
        statsPanel.add(new ChartPanel(playerInfo));
    }

    private XYDataset playerInfoDataset() {
        final XYSeriesCollection dataset = new XYSeriesCollection();

        for(Player p : statisticsController.getPlayers()){
            final XYSeries xySeries = new XYSeries(p.getUsername());
            double gamePlayed = statisticsController.getPlayerGameCount(p);
            xySeries.add(gamePlayed,statisticsController.getPlayerVictoryCount(p) / gamePlayed);
            dataset.addSeries(xySeries);
        }

        return dataset;
    }

    private PieDataset iaVictoryRateDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String,Double> victoryPercentagePerIAs = statisticsController.getIAsVictories();

        for(String iaName : victoryPercentagePerIAs.keySet()){
            dataset.setValue(iaName, victoryPercentagePerIAs.get(iaName));
        }
        return dataset;
    }

    @Override
    public Menus getMenu() {
        return Menus.STATISTICS;
    }
}
