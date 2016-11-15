package org.elasticsearch.configurator.UI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import org.elasticsearch.configurator.Configurator;

/**
 * Created by kmala on 15-Nov-16.
 */
public class ConfiguratorUI  extends JFrame implements java.awt.event.ActionListener, ItemListener{


    private JCheckBox c1,c2,c3, c4;
    private JPanel pane;
    private JButton bt;
    Configurator config;

    public ConfiguratorUI() {

    initialize();
    }

    private void initialize(){

        config = Configurator.getInstance();
        c1 = new JCheckBox("GeoKMeans Feature");
        c2 = new JCheckBox("LateParsingQuery Feature");
        c3 = new JCheckBox("UsageStatistics Feature");
        c4 = new JCheckBox("XMLShit Feature");
        pane = new JPanel();
        bt = new JButton("Apply");
        c1.addItemListener(this);
        c2.addItemListener(this);
        c3.addItemListener(this);
        c4.addItemListener(this);
        bt.addActionListener(this);


        pane.add(c1);
        pane.add(c2);
        pane.add(c3);
        pane.add(c4);
        pane.add(bt);
        this.add(pane);
        pane.setPreferredSize(new Dimension(800,400));

        c1.setSelected(config.getisGeoKMeansActivated());
        c2.setSelected(config.getisLateParsingQueryActivated());
        c3.setSelected(config.getisUsageStatisticsActivated());
        c4.setSelected(config.getisXMLActivated());

    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==bt)
        {
            config.setGeoKMeansActivated(c1.isSelected());
            config.setLateParsingQueryActivated(c2.isSelected());
            config.setUsageStatisticsActivated(c3.isSelected());
            config.setXMLActivated(c4.isSelected());
            config.saveConfiguration();
        }

    }
    public  void itemStateChanged(ItemEvent a){}



}
