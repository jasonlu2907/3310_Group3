package uta.cse.cse3310.JSBSimEdit.ui.components;

import generated.Channel;
import generated.Sensor;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.*;

public class Autopilot extends JPanel{
    protected final List<String> property;
    protected final List<Sensor> sensor;
    protected final List<Channel> channel;
    protected String name;
    protected String file;

    public Autopilot(List<String> property, List<Sensor> sensor, List<Channel> channel, String name, String file){
        this.property = property;
        this.sensor = sensor;
        this.channel = channel;
        this.name = name;
        this.file = file;

        setLayout(new BorderLayout());

        //////////////////CHANNELs/////////////////////////////////
        JTabbedPane channelTabbedPane = createTabbedPane();

        add(channelTabbedPane, BorderLayout.CENTER);

        setVisible(true);

    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        for(Channel cha : this.channel) {
            tabbedPane.addTab(cha.getName(), new ChannelPanel(cha.getActuatorOrAerosurfaceScaleOrIntegrator()));
        }

        return tabbedPane;
    }
}
