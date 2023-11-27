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

//    DefaultListModel<String> listModel = new DefaultListModel<>();
//
//    // Add some items to the list
//    listModel.addElement("/sim/model/pushback/kp");
//    listModel.addElement("/sim/model/pushback/ki");
//    listModel.addElement("/sim/model/pushback/kd");
//
//    // Create a JList with the listModel
//    JList<String> itemList = new JList<>(listModel);
//
//    // Create a JButton to perform an action when selected item is clicked
//    JButton selectButton = new JButton("Add");
//    selectButton.setActionCommand("Add Item");
//    selectButton.setToolTipText("Add new item");
//    // selectButton.addActionListener(event -> onAddClick());
//
//    JButton deleteButton = new JButton("Delete");
//    deleteButton.setActionCommand("Delete Item");
//    deleteButton.setToolTipText("Delete the item");
//    // deleteButton.addActionListener(event -> onDeleteClick());
//
//    JButton detailButton = new JButton("Detail");
//    detailButton.setActionCommand("More Info");
//    detailButton.setToolTipText("See detail of the item");
//    // detailButton.addActionListener(event -> onDetailClick());
//
//    JPanel buttonPanel = new JPanel();
//    buttonPanel.add(selectButton);
//    buttonPanel.add(deleteButton);
//    buttonPanel.add(detailButton);
//
//    JPanel panel = new JPanel(new BorderLayout());
//    panel.add(new JScrollPane(itemList), BorderLayout.CENTER);
//    panel.add(buttonPanel, BorderLayout.SOUTH);

    // Add the panel to the frame

    add(systemTabbedPane, BorderLayout.CENTER);

    setVisible(true);
  }
}
