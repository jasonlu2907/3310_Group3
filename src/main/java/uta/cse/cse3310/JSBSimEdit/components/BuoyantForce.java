package uta.cse.cse3310.JSBSimEdit.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

public class BuoyantForce extends JPanel {
  public BuoyantForce() {
    setLayout(new BorderLayout());

    ////////////////LISTs////////////////
    DefaultListModel<String> available_engine = new DefaultListModel<String>();

    // Add some items to the lists
    available_engine.addElement("AJ26-33A");
    available_engine.addElement("CF6-80C2");
    available_engine.addElement("CFM56");
    available_engine.addElement("CFM56_5");
    available_engine.addElement("electric_1mw");
    available_engine.addElement("eng_io320");

    String[] column2Data = {"AJ26-33_nozzle", "direct"};
    String[] column3Data = {"F100-PW-229"};
    String[] column4Data = {"FUEL weights 1500 LBS at [-174.4, 65.0, 5.0]",
                          "FUEL weights 1500 LBS at [-174.4, -65.0, 5.0]", 
                          "FUEL weights 0 LBS at [-174.4, 65.0, -15.0]",
                          "FUEL weights 0 LBS at [-174.4, -65.0, -15.0]"};

    // Create JLists with the available_engine
    JList<String> available_engine_list = new JList<>(available_engine);
    JList<String> column2List = new JList<>(column2Data);
    JList<String> column3List = new JList<>(column3Data);
    JList<String> column4List = new JList<>(column4Data);

    /////////////////////BUTTONs/////////////////////////////
    // Create a JButton to perform an action when selected item is clicked
    JButton newPair = new JButton("Add Pair");
    newPair.setActionCommand("Add Item");
    newPair.setToolTipText("Add new item");
    newPair.addActionListener(event -> onAddPairClick());

    JButton deletePair = new JButton("Delete Pair");
    deletePair.setActionCommand("Delete Item");
    deletePair.setToolTipText("Delete the item");
    deletePair.addActionListener(event -> onDeletePairClick());

    JButton detailPair = new JButton("Detail Pair");
    detailPair.setActionCommand("More Info");
    detailPair.setToolTipText("See detail of the item");
    detailPair.addActionListener(event -> onDetailPairClick());

    JButton newTank = new JButton("Add Tank");
    newTank.setActionCommand("Add Item");
    newTank.setToolTipText("Add new item");
    newTank.addActionListener(event -> onAddTankClick());

    JButton deleteTank = new JButton("Delete Tank");
    deleteTank.setActionCommand("Delete Item");
    deleteTank.setToolTipText("Delete the item");
    deleteTank.addActionListener(event -> onDeleteTankClick());

    JButton detailTank = new JButton("Detail Tank");
    detailTank.setActionCommand("More Info");
    detailTank.setToolTipText("See detail of the item");
    detailTank.addActionListener(event -> onDetailTankClick());


    //////////////////PANELs///////////////////////////////////
    /* Available Engines Panel */
    JPanel AE_panel = new JPanel(new BorderLayout());
    AE_panel.setBorder(new TitledBorder("Available Engines:"));
    AE_panel.add(new JScrollPane(available_engine_list), BorderLayout.CENTER);

    /* Available Thrusters Panel */
    JPanel AT_panel = new JPanel(new BorderLayout());
    AT_panel.setBorder(new TitledBorder("Available Thrusters:"));
    AT_panel.add(new JScrollPane(column2List), BorderLayout.CENTER);

    /* Subscribed Engine Panel */
    JPanel SE_panel = new JPanel(new BorderLayout());
    SE_panel.setBorder(new TitledBorder("Subscribed Engines:"));
    SE_panel.add(new JScrollPane(column3List));
    
    JPanel SE_buttons = new JPanel(new GridLayout(3, 1));
    SE_buttons.add(newPair);
    SE_buttons.add(deletePair);
    SE_buttons.add(detailPair);
    
    SE_panel.add(SE_buttons, BorderLayout.SOUTH);

    /* Tanks Panel */
    JPanel Tank_panel = new JPanel(new BorderLayout());
    Tank_panel.setBorder(new TitledBorder("Tanks:"));
    Tank_panel.add(new JScrollPane(column4List), BorderLayout.CENTER);
    
    JPanel Tank_buttons = new JPanel(new GridLayout(3, 1));
    Tank_buttons.add(newTank);
    Tank_buttons.add(deleteTank);
    Tank_buttons.add(detailTank);

    Tank_panel.add(Tank_buttons, BorderLayout.SOUTH);

    // Add the panels to the main panel
    JPanel mainPanel = new JPanel(new GridLayout(1, 4));
    mainPanel.add(AE_panel);
    mainPanel.add(AT_panel);
    mainPanel.add(SE_panel);
    mainPanel.add(Tank_panel);

    add(mainPanel);
  }

  protected void onDetailTankClick() {
    
  }

  protected void onDeleteTankClick() {
    
  }

  protected void onAddTankClick() {
    
  }

  protected void onDetailPairClick() {
    
  }

  protected void onDeletePairClick() {
    
  }

  protected void onAddPairClick() {
    
  }


}
