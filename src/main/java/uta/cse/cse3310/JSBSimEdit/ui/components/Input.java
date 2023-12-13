package uta.cse.cse3310.JSBSimEdit.ui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.json.simple.JSONObject;

import generated.FdmConfig;
import uta.cse.cse3310.JSBSimEdit.ui.windows.CommanderWindow;

public class Input extends JPanel {
  private String port;
  private final CommanderWindow mainFrame;
  private FdmConfig cfg;

  public Input(FdmConfig cfg, CommanderWindow mainFrame) {
    this.mainFrame = mainFrame;
    this.cfg = cfg;
    // Ugly but better than unreadable magic.
    if (cfg.getInput() == null) {
      this.port = "";
    } else {
      if (cfg.getInput().getPort() == null) {
        this.port = "";
      } else {
        this.port = cfg.getInput().getPort().toString();
      }
    }

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    topPanel.add(new JLabel("Current port: " + port));

    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel name = new JLabel("Port:");
    JTextField nameField = new JTextField("0000", 10);
    panel.add(name);
    panel.add(nameField);

    JButton addButton = new JButton("Save");
    addButton.setActionCommand("Save port");
    addButton.setToolTipText("Save port");
    addButton.addActionListener(event -> {
      generated.Input newInput = new generated.Input();
      newInput.setPort(new BigInteger(nameField.getText()));
      cfg.setInput(newInput);
      mainFrame.refreshCfg(cfg);
    });
    panel.add(addButton);

    // Add the panel to the frame
    add(topPanel);
    add(panel);
  }

  protected void onDeleteClick() {

  }

  protected void onAddClick() {
  }
}
