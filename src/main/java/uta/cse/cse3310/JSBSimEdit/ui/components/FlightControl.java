package uta.cse.cse3310.JSBSimEdit.ui.components;

import generated.Channel;
import generated.Sensor;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class FlightControl extends JPanel {
  private final List<String> property;
  private final List<Sensor> sensor;
  private final List<Channel> channel;
  private String name;
  private String file;

  public FlightControl(List<String> property, List<Sensor> sensor, List<Channel> channel, String name, String file) {
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
