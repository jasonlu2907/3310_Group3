package uta.cse.cse3310.JSBSimEdit.ui.components;

import java.math.BigInteger;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.awt.*;

import java.util.List;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.*;

public class GroundReaction extends JPanel {

  private DefaultListModel<String> listModel;
  private DefaultListModel<generated.Contact> contactList;
  private generated.GroundReactions groundReactions;
  private int selectedIndex;
  private BigInteger grretractable;
  private JTextField mxstr_text;
  private JTextField dmpngcoeffrbnd_text;
  private JTextField stcfrctn_text;
  private JTextField dynmcfrctn_text;
  private JTextField rlngfrctn_text;

  public GroundReaction(generated.GroundReactions groundReactions) {
    setLayout(new BorderLayout());
    listModel = new DefaultListModel<>();
    contactList = new DefaultListModel<>();

    processGroundReactions(groundReactions);

    createListModel(contactList);


    // Create a JList with the listModel
    // TODO: Fix type of JList, not String
    JList<String> itemList = new JList<>(listModel);
    // Automatically select the first item
    if (listModel.getSize() > 0) {
      itemList.setSelectedIndex(0);
    }

    // Automatically select the first item
    itemList.setSelectedIndex(0);
    selectedIndex = itemList.getSelectedIndex();

    itemList.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) { // Detect double-click
          selectedIndex = itemList.getSelectedIndex();
          onDetailClick(selectedIndex);
        }
      }
    });

    // Create a JButton to perform an action when selected item is clicked
    JButton addButton = new JButton("Add");
    addButton.setActionCommand("Add Item");
    addButton.setToolTipText("Add new item");
    //TODO: implement button action
    addButton.addActionListener(event -> {onAddClick();});

    JButton deleteButton = new JButton("Delete");
    deleteButton.setActionCommand("Delete Item");
    deleteButton.setToolTipText("Delete the item");
    //TODO: implement button action
    deleteButton.addActionListener(event -> {
      selectedIndex = itemList.getSelectedIndex();
      onDeleteClick(selectedIndex);
    });

    JButton detailButton = new JButton("Detail");
    detailButton.setActionCommand("More Info");
    detailButton.setToolTipText("See detail of the item");

    detailButton.addActionListener(event -> {
      selectedIndex = itemList.getSelectedIndex();
      onDetailClick(selectedIndex);
    });


    // Create a JPanel to hold the buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(detailButton);

    // Create a JPanel to hold the JList and the button
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(new JScrollPane(itemList), BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);

    // Add the panel to the frame
    add(panel, BorderLayout.CENTER);
  }

  public void processGroundReactions(generated.GroundReactions groundReactions) {
    List<Object> content = groundReactions.getContent();
    for (Object item : content) {
      if (item instanceof generated.Contact) {
        generated.Contact contact = (generated.Contact) item;
        contactList.addElement(contact);
      }
    }
  }

  public void createListModel(DefaultListModel<generated.Contact> contactList) {
    for (int i = 0; i < contactList.size(); i++) {
      generated.Contact listcontact = contactList.getElementAt(i);
      generated.Location listlocation = listcontact.getLocation();
      String listname = listcontact.getName();
      String listbrakeGroup = listcontact.getBrakeGroup();
      if (listbrakeGroup == null) {
        listbrakeGroup = "NONE";
      }
      String formattedContact = String.format("(%s) at point [%s, %s, %s] in %s (in %s brake group)", listname, listlocation.getX(), listlocation.getY(), listlocation.getZ(), listlocation.getUnit(), listbrakeGroup);
      listModel.addElement(formattedContact);
    }
  }


  // TODO: String type is not compatible
  protected void onDetailClick(int idx) {
    JFrame detailsFrame = new JFrame("Item Details");
    detailsFrame.setSize(700, 700);

    generated.Contact grcontact = contactList.getElementAt(idx);
    generated.Location grlocation = grcontact.getLocation();
    String grX = Double.toString(grlocation.getX());
    String grY = Double.toString(grlocation.getY());
    String grZ = Double.toString(grlocation.getZ());
    String grname = grcontact.getName();
    String grtype = grcontact.getType();
    String grstaticFriction = Double.toString(grcontact.getStaticFriction());
    String grdynamicFriction = Double.toString(grcontact.getDynamicFriction());
    generated.Contact.SpringCoeff grspringCoeff = grcontact.getSpringCoeff();
    String grspringCoeffValue = Double.toString(grspringCoeff.getValue());
    generated.Contact.DampingCoeff grdampingCoeff = grcontact.getDampingCoeff();
    String grdampingCoeffValue = Double.toString(grdampingCoeff.getValue());
    generated.Contact.DampingCoeffRebound grdampingCoeffRebound = grcontact.getDampingCoeffRebound();
    String grrollingFriction = Double.toString(grcontact.getRollingFriction());
    generated.Contact.MaxSteer grmaxSteer = grcontact.getMaxSteer();
    String grbrakeGroup = grcontact.getBrakeGroup();
    if (grbrakeGroup == null) {
      grbrakeGroup = "NONE";
    }
    grretractable = grcontact.getRetractable();

    generated.DampingCoeffType[] dampingCoeffArray = generated.DampingCoeffType.values();
    generated.AngleType[] maxSteerArray = generated.AngleType.values();

    String[] brakegroups = {"NOSE", "TAIL", "NONE", "RIGHT", "LEFT", "CENTER"};
    String[] locationunits = {"M", "IN", "FT"};

    /*Create MAIN PANEL */
    JPanel mainPanel = new JPanel();
    // mainPanel.setBorder(new TitledBorder());
    mainPanel.setLayout(new GridLayout(6,1));


    ////////////////////////////////////////////////////////////////
    /*SECOND SECTION */
    // Create a JPanels to hold the contacts characteristics
    JPanel forcePanel = new JPanel(new FlowLayout());
    forcePanel.setBorder(new TitledBorder("Contact:"));

    // Create JLabels for displaying labels next to the text fields
    JLabel nameLabel = new JLabel("Name:");
    JLabel typeLabel = new JLabel("Type:");
    // Create JTextFields for displaying and editing name and age
    JTextField nameField = new JTextField(grname, 20);
    JTextField typeField = new JTextField(grtype, 15);

    forcePanel.add(nameLabel);
    forcePanel.add(nameField);
    forcePanel.add(typeLabel);
    forcePanel.add(typeField);

    mainPanel.add(forcePanel);

    ////////////////////////////////////////////////////////////////
    /*THIRD SECTION */
    // Create a panel to hold the location
    JPanel locationPanel = new JPanel();
    locationPanel.setBorder(new TitledBorder("Location:"));
    locationPanel.setLayout(new FlowLayout());

    // Create JLabels and JTextFields for additional details
    JLabel location_x = new JLabel("x = ");
    JTextField location_x_text = new JTextField(grX,10);

    JLabel location_y = new JLabel("y = ");
    JTextField location_y_text = new JTextField(grY,10);

    JLabel location_z = new JLabel("z = ");
    JTextField location_z_text = new JTextField(grZ,10);

    JComboBox<String> locationComboBox = new JComboBox<>(locationunits);
    locationComboBox.setSelectedItem(grlocation.getUnit());

    locationPanel.add(location_x);
    locationPanel.add(location_x_text);
    locationPanel.add(location_y);
    locationPanel.add(location_y_text);
    locationPanel.add(location_z);
    locationPanel.add(location_z_text);
    locationPanel.add(locationComboBox);

    mainPanel.add(locationPanel);

    ////////////////////////////////////////////////////////////////
    /*FOURTH SECTION */
    // Create a panel to hold the coefficients
    JPanel coeffPanel = new JPanel();
    coeffPanel.setLayout(new FlowLayout());

    // Create JLabels and JTextFields for additional details
    JLabel sprngcoeff = new JLabel("Spring Coeffecient = ");
    JTextField sprngcoeff_text = new JTextField(grspringCoeffValue, 8);
    JComboBox<generated.SpringCoeffType> springCoeffComboBox = new JComboBox<>(generated.SpringCoeffType.values());
    springCoeffComboBox.setSelectedItem(grspringCoeff.getUnit());

    JLabel dmpngcoeff = new JLabel("Damping Coefficient = ");
    JTextField dmpngcoeff_text = new JTextField(grdampingCoeffValue, 8);
    JComboBox<generated.DampingCoeffType> dampingCoeffComboBox = new JComboBox<>(dampingCoeffArray);
    dampingCoeffComboBox.setSelectedItem(grdampingCoeff.getUnit());

    coeffPanel.add(sprngcoeff);
    coeffPanel.add(sprngcoeff_text);
    coeffPanel.add(springCoeffComboBox);
    coeffPanel.add(dmpngcoeff);
    coeffPanel.add(dmpngcoeff_text);
    coeffPanel.add(dampingCoeffComboBox);

    dmpngcoeffrbnd_text = new JTextField(" ");
    String grdampingCoeffReboundValue;
    if (grdampingCoeffRebound == null) {
      grdampingCoeffReboundValue = "0";
    } else {
      grdampingCoeffReboundValue = Double.toString(grdampingCoeffRebound.getValue());
    }
    JLabel dmpngcoeffrbnd = new JLabel("Damping Coefficient Rebound = ");
    dmpngcoeffrbnd_text = new JTextField(grdampingCoeffReboundValue, 8);
    coeffPanel.add(dmpngcoeffrbnd);
    coeffPanel.add(dmpngcoeffrbnd_text);

    mainPanel.add(coeffPanel);

    ////////////////////////////////////////////////////////////////
    /*FIFTH SECTION */
    // Create a JPanels to hold the friction details
    JPanel frictionPanel = new JPanel();
    frictionPanel.setLayout(new FlowLayout());

    stcfrctn_text = new JTextField(" ");
    // Create JLabels and JTextFields for additional details
    if (grstaticFriction != null) {
      JLabel stcfrctn = new JLabel("Static Friction = ");
      stcfrctn_text = new JTextField(grstaticFriction,10);
      frictionPanel.add(stcfrctn);
      frictionPanel.add(stcfrctn_text);
    }

    dynmcfrctn_text = new JTextField(" ");
    if (grdynamicFriction != null) {
      JLabel dynmcfrctn = new JLabel("Dynamic Friction = ");
      dynmcfrctn_text = new JTextField(grdynamicFriction,10);
      frictionPanel.add(dynmcfrctn);
      frictionPanel.add(dynmcfrctn_text);
    }

    rlngfrctn_text = new JTextField(" ");
    if (grrollingFriction != null) {
      JLabel rlngfrctn = new JLabel("Rolling Friction = ");
      rlngfrctn_text = new JTextField(grrollingFriction,10);
      frictionPanel.add(rlngfrctn);
      frictionPanel.add(rlngfrctn_text);
    }

    mainPanel.add(frictionPanel);

    ////////////////////////////////////////////////////////////////
    /*FIFTH SECTION */
    // Create a JPanels to hold the rest of the details
    JPanel restPanel = new JPanel();
    restPanel.setLayout(new FlowLayout());

    mxstr_text = new JTextField(" ");
    String MaxSteerValue;
    JComboBox<generated.AngleType> maxSteerComboBox = new JComboBox<>(maxSteerArray);
    if (grmaxSteer == null) {
      MaxSteerValue = "0";
    } else {
      MaxSteerValue = Double.toString(grmaxSteer.getValue());
      maxSteerComboBox.setSelectedItem(grmaxSteer.getUnit());
    }
    JLabel mxstr = new JLabel("Max Steer = ");
    mxstr_text = new JTextField(MaxSteerValue, 10);
    restPanel.add(mxstr);
    restPanel.add(mxstr_text);
    restPanel.add(maxSteerComboBox);

    JLabel brkgrp = new JLabel("Brake Group = ");
    JComboBox<String> brkgrpComboBox = new JComboBox<>(brakegroups);
    brkgrpComboBox.setSelectedItem(grbrakeGroup);
    restPanel.add(brkgrp);
    restPanel.add(brkgrpComboBox);

    boolean retract = true;
    if (grretractable != null) {
      if (grretractable.signum() == 0) {
        retract = false;
      } else {
        retract = true;
      }
    }
    JCheckBox rtrct = new JCheckBox("Retractable ", retract);
    restPanel.add(rtrct);

    mainPanel.add(restPanel);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());

    JButton editButton = new JButton("Edit");
    editButton.setActionCommand("Edit Details");
    editButton.addActionListener(event -> {
      // update the XML class
      grcontact.setName(nameField.getText().toUpperCase());

      grcontact.getLocation().setX(Double.parseDouble(location_x_text.getText()));
      grcontact.getLocation().setY(Double.parseDouble(location_y_text.getText()));
      grcontact.getLocation().setZ(Double.parseDouble(location_z_text.getText()));
      grcontact.getLocation().setUnit(locationComboBox.getSelectedItem().toString());

      grcontact.setType(typeField.getText().toUpperCase());
      if (rtrct.isSelected() == false) {
        grretractable = BigInteger.valueOf(0);
      } else {
        grretractable = BigInteger.valueOf(1);
      }
      grcontact.setRetractable(grretractable);
      grcontact.setBrakeGroup(brkgrpComboBox.getSelectedItem().toString());

      double springCoeffValue = Double.parseDouble(sprngcoeff_text.getText());
      generated.Contact.SpringCoeff springCoeff = new generated.Contact.SpringCoeff();
      springCoeff.setValue(springCoeffValue);
      generated.SpringCoeffType selectedSpringCoeffType = (generated.SpringCoeffType) springCoeffComboBox.getSelectedItem();
      springCoeff.setUnit(selectedSpringCoeffType);
      grcontact.setSpringCoeff(springCoeff);

      // Update Damping Coefficient
      double dampingCoeffValue = Double.parseDouble(dmpngcoeff_text.getText());
      generated.Contact.DampingCoeff dampingCoeff = new generated.Contact.DampingCoeff();
      dampingCoeff.setValue(dampingCoeffValue);
      generated.DampingCoeffType selectedDampingCoeffType = (generated.DampingCoeffType) dampingCoeffComboBox.getSelectedItem();
      dampingCoeff.setUnit(selectedDampingCoeffType);
      grcontact.setDampingCoeff(dampingCoeff);

      double dampingCoeffValueRebound = Double.parseDouble(dmpngcoeffrbnd_text.getText());
      generated.Contact.DampingCoeffRebound dampingCoeffRebound = new generated.Contact.DampingCoeffRebound();
      dampingCoeffRebound.setValue(dampingCoeffValueRebound);
      grcontact.setDampingCoeffRebound(dampingCoeffRebound);

      double maxSteerValue = Double.parseDouble(mxstr_text.getText());
      generated.Contact.MaxSteer maxSteer = new generated.Contact.MaxSteer();
      maxSteer.setValue(maxSteerValue);
      generated.AngleType selectedAngleType = (generated.AngleType) maxSteerComboBox.getSelectedItem();
      maxSteer.setUnit(selectedAngleType);
      grcontact.setMaxSteer(maxSteer);

      double staticFrictionValue = Double.parseDouble(stcfrctn_text.getText());
      grcontact.setStaticFriction(staticFrictionValue);

      double dynamicFrictionValue = Double.parseDouble(dynmcfrctn_text.getText());
      grcontact.setDynamicFriction(dynamicFrictionValue);

      double rollingFrictionValue = Double.parseDouble(rlngfrctn_text.getText());
      grcontact.setRollingFriction(rollingFrictionValue);

      listModel.clear();
      createListModel(contactList);
      JList<String> itemList = new JList<>(listModel);
      detailsFrame.dispose();
    });

    JButton closeButton = new JButton("Close");
    closeButton.setActionCommand("Close Details");
    closeButton.addActionListener(event -> {
      detailsFrame.dispose();
    });

    buttonPanel.add(editButton);
    buttonPanel.add(closeButton);

    mainPanel.add(buttonPanel);


    ////////////////////////////////
    detailsFrame.add(mainPanel);

    detailsFrame.setLocationRelativeTo(this);
    detailsFrame.setVisible(true);
  }


  protected void onDeleteClick(int idx) {
    contactList.remove(idx);

    listModel.clear();
    createListModel(contactList);
    JList<String> itemList = new JList<>(listModel);
  }

  protected void onAddClick() {
    JFrame LGSFrame = new JFrame("Landing Gear Setup");
    LGSFrame.setSize(700, 700);

    generated.DampingCoeffType[] dampingCoeffArray = generated.DampingCoeffType.values();
    generated.AngleType[] maxSteerArray = generated.AngleType.values();

    String[] brakegroups = {"NOSE", "TAIL", "NONE", "RIGHT", "LEFT", "CENTER"};
    String[] locationunits = {"M", "IN", "FT"};

    /*Create MAIN PANEL */
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(6,1));


    ////////////////////////////////////////////////////////////////
    /*SECOND SECTION */
    // Create a JPanels to hold the contacts characteristics
    JPanel forcePanel = new JPanel(new FlowLayout());
    forcePanel.setBorder(new TitledBorder("Contact:"));

    // Create JLabels for displaying labels next to the text fields
    JLabel nameLabel = new JLabel("Name:");
    JLabel typeLabel = new JLabel("Type:");
    // Create JTextFields for displaying and editing name and age
    JTextField nameField = new JTextField("NEW LGEAR", 20);
    JTextField typeField = new JTextField("BOGEY", 15);

    forcePanel.add(nameLabel);
    forcePanel.add(nameField);
    forcePanel.add(typeLabel);
    forcePanel.add(typeField);

    mainPanel.add(forcePanel);

    ////////////////////////////////////////////////////////////////
    /*THIRD SECTION */
    // Create a panel to hold the location
    JPanel locationPanel = new JPanel();
    locationPanel.setBorder(new TitledBorder("Location:"));
    locationPanel.setLayout(new FlowLayout());

    // Create JLabels and JTextFields for additional details
    JLabel location_x = new JLabel("x = ");
    JTextField location_x_text = new JTextField("0",10);

    JLabel location_y = new JLabel("y = ");
    JTextField location_y_text = new JTextField("0",10);

    JLabel location_z = new JLabel("z = ");
    JTextField location_z_text = new JTextField("0",10);

    JComboBox<String> locationComboBox = new JComboBox<>(locationunits);

    locationPanel.add(location_x);
    locationPanel.add(location_x_text);
    locationPanel.add(location_y);
    locationPanel.add(location_y_text);
    locationPanel.add(location_z);
    locationPanel.add(location_z_text);
    locationPanel.add(locationComboBox);

    mainPanel.add(locationPanel);

    ////////////////////////////////////////////////////////////////
    /*FOURTH SECTION */
    // Create a panel to hold the coefficients
    JPanel coeffPanel = new JPanel();
    coeffPanel.setLayout(new FlowLayout());

    JLabel sprngcoeff = new JLabel("Spring Coeffecient = ");
    JTextField sprngcoeff_text = new JTextField("0", 8);
    JComboBox<generated.SpringCoeffType> springCoeffComboBox = new JComboBox<>(generated.SpringCoeffType.values());

    JLabel dmpngcoeff = new JLabel("Damping Coefficient = ");
    JTextField dmpngcoeff_text = new JTextField("0", 8);
    JComboBox<generated.DampingCoeffType> dampingCoeffComboBox = new JComboBox<>(dampingCoeffArray);

    coeffPanel.add(sprngcoeff);
    coeffPanel.add(sprngcoeff_text);
    coeffPanel.add(springCoeffComboBox);
    coeffPanel.add(dmpngcoeff);
    coeffPanel.add(dmpngcoeff_text);
    coeffPanel.add(dampingCoeffComboBox);

    String grdampingCoeffReboundValue;
    JLabel dmpngcoeffrbnd = new JLabel("Damping Coefficient Rebound = ");
    dmpngcoeffrbnd_text = new JTextField("0", 8);
    coeffPanel.add(dmpngcoeffrbnd);
    coeffPanel.add(dmpngcoeffrbnd_text);

    mainPanel.add(coeffPanel);

    ////////////////////////////////////////////////////////////////
    /*FIFTH SECTION */
    // Create a JPanels to hold the friction details
    JPanel frictionPanel = new JPanel();
    frictionPanel.setLayout(new FlowLayout());

    JLabel stcfrctn = new JLabel("Static Friction = ");
    stcfrctn_text = new JTextField("0",10);
    frictionPanel.add(stcfrctn);
    frictionPanel.add(stcfrctn_text);

    JLabel dynmcfrctn = new JLabel("Dynamic Friction = ");
    dynmcfrctn_text = new JTextField("0",10);
    frictionPanel.add(dynmcfrctn);
    frictionPanel.add(dynmcfrctn_text);

    JLabel rlngfrctn = new JLabel("Rolling Friction = ");
    rlngfrctn_text = new JTextField("0",10);
    frictionPanel.add(rlngfrctn);
    frictionPanel.add(rlngfrctn_text);

    mainPanel.add(frictionPanel);

    ////////////////////////////////////////////////////////////////
    /*SIXTH SECTION */
    JPanel restPanel = new JPanel();
    restPanel.setLayout(new FlowLayout());

    JComboBox<generated.AngleType> maxSteerComboBox = new JComboBox<>(maxSteerArray);
    JLabel mxstr = new JLabel("Max Steer = ");
    mxstr_text = new JTextField("0", 10);
    restPanel.add(mxstr);
    restPanel.add(mxstr_text);
    restPanel.add(maxSteerComboBox);

    JLabel brkgrp = new JLabel("Brake Group = ");
    JComboBox<String> brkgrpComboBox = new JComboBox<>(brakegroups);
    restPanel.add(brkgrp);
    restPanel.add(brkgrpComboBox);

    boolean retract = false;
    JCheckBox rtrct = new JCheckBox("Retractable ", retract);
    restPanel.add(rtrct);

    mainPanel.add(restPanel);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());

    JButton createButton = new JButton("Create");
    createButton.setActionCommand("Create LGS");
    createButton.addActionListener(event -> {
      // create the XML class
      generated.Contact newContact = new generated.Contact();

      newContact.setName(nameField.getText().toUpperCase());

      generated.Location newLocation = new generated.Location();
      newLocation.setX(Double.parseDouble(location_x_text.getText()));
      newLocation.setY(Double.parseDouble(location_y_text.getText()));
      newLocation.setZ(Double.parseDouble(location_z_text.getText()));
      newLocation.setUnit(locationComboBox.getSelectedItem().toString());
      newContact.setLocation(newLocation);

      newContact.setType(typeField.getText().toUpperCase());
      if (rtrct.isSelected() == false) {
        grretractable = BigInteger.valueOf(0);
      } else {
        grretractable = BigInteger.valueOf(1);
      }
      newContact.setRetractable(grretractable);
      newContact.setBrakeGroup(brkgrpComboBox.getSelectedItem().toString());

      double springCoeffValue = Double.parseDouble(sprngcoeff_text.getText());
      generated.Contact.SpringCoeff springCoeff = new generated.Contact.SpringCoeff();
      springCoeff.setValue(springCoeffValue);
      generated.SpringCoeffType selectedSpringCoeffType = (generated.SpringCoeffType) springCoeffComboBox.getSelectedItem();
      springCoeff.setUnit(selectedSpringCoeffType);
      newContact.setSpringCoeff(springCoeff);

      double dampingCoeffValue = Double.parseDouble(dmpngcoeff_text.getText());
      generated.Contact.DampingCoeff dampingCoeff = new generated.Contact.DampingCoeff();
      dampingCoeff.setValue(dampingCoeffValue);
      generated.DampingCoeffType selectedDampingCoeffType = (generated.DampingCoeffType) dampingCoeffComboBox.getSelectedItem();
      dampingCoeff.setUnit(selectedDampingCoeffType);
      newContact.setDampingCoeff(dampingCoeff);

      double dampingCoeffValueRebound = Double.parseDouble(dmpngcoeffrbnd_text.getText());
      generated.Contact.DampingCoeffRebound dampingCoeffRebound = new generated.Contact.DampingCoeffRebound();
      dampingCoeffRebound.setValue(dampingCoeffValueRebound);
      newContact.setDampingCoeffRebound(dampingCoeffRebound);

      double maxSteerValue = Double.parseDouble(mxstr_text.getText());
      generated.Contact.MaxSteer maxSteer = new generated.Contact.MaxSteer();
      maxSteer.setValue(maxSteerValue);
      generated.AngleType selectedAngleType = (generated.AngleType) maxSteerComboBox.getSelectedItem();
      maxSteer.setUnit(selectedAngleType);
      newContact.setMaxSteer(maxSteer);

      double staticFrictionValue = Double.parseDouble(stcfrctn_text.getText());
      newContact.setStaticFriction(staticFrictionValue);

      double dynamicFrictionValue = Double.parseDouble(dynmcfrctn_text.getText());
      newContact.setDynamicFriction(dynamicFrictionValue);

      double rollingFrictionValue = Double.parseDouble(rlngfrctn_text.getText());
      newContact.setRollingFriction(rollingFrictionValue);

      contactList.addElement(newContact);
      listModel.clear();
      createListModel(contactList);
      JList<String> itemList = new JList<>(listModel);
      LGSFrame.dispose();
    });

    JButton closeButton = new JButton("Close");
    closeButton.setActionCommand("Close Details");
    closeButton.addActionListener(event -> {
      LGSFrame.dispose();
    });

    buttonPanel.add(createButton);
    buttonPanel.add(closeButton);

    mainPanel.add(buttonPanel);


    LGSFrame.add(mainPanel);

    LGSFrame.setLocationRelativeTo(this);
    LGSFrame.setVisible(true);
  }
}
