package uta.cse.cse3310.JSBSimEdit.ui.components.system;

import generated.Channel;
import generated.Sensor;
import generated.System;
import uta.cse.cse3310.JSBSimEdit.ui.components.ExternalReaction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SystemProperties extends JPanel {
  private List<System> systemList;

  public SystemProperties(List<System> systemList) {
    this.systemList = systemList;

    setLayout(new BorderLayout());

    //////////////////Systems/////////////////////////////////
    JTabbedPane systemTabbedPane = new JTabbedPane();
    for (System sys : this.systemList) {

      // convert List<Object> to List<String>
      List<String> systemStringList = new ArrayList<>();
      for (Object obj : sys.getPropertyOrSensorOrChannel()) {
        if (obj instanceof Sensor) {
          Sensor sensor = (Sensor) obj;
          systemStringList.add(sensor.getName());
        } else if (obj instanceof Channel) {
          Channel channel = (Channel) obj;
          systemStringList.add(channel.getName());
        } else {
          systemStringList.add(String.valueOf(obj));
        }
      }

      systemTabbedPane.add(sys.getFile(), new SystemGenerator(systemStringList, sys, sys.getFile()));
    }

    add(systemTabbedPane, BorderLayout.CENTER);

    setVisible(true);
  }
}
