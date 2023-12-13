package uta.cse.cse3310.JSBSimEdit.ui.components;

import generated.Direction;
import generated.FdmConfig;
import generated.Force;
import generated.Location;
import uta.cse.cse3310.JSBSimEdit.ui.windows.CommanderWindow;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class ExternalReaction extends JPanel {
  private final List<String> property;
  private final List<Force> force;
  private final String file;

  private int selectedIndex;
  private final JList<String> itemList;
  public ExternalReaction(List<String> property, List<Force> force, String file) {
    this.property = property;
    this.force = force;
    this.file = file;

    setLayout(new BorderLayout());

    // Create a JList with the listModel
    DefaultListModel<String> stringModels = generateList();
    itemList = new JList<>(stringModels);

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
    addButton.addActionListener(event -> onAddClick());

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

  protected void onDetailClick(int index) {
    JFrame detailsFrame = new JFrame("Item Details");
    detailsFrame.setSize(600, 400);

    /*Create MAIN PANEL */
    JPanel mainPanel = new JPanel();
    // mainPanel.setBorder(new TitledBorder());
    mainPanel.setLayout(new GridLayout(4,1));

    ////////////////////////////////////////////////////////////////
    /*SECOND SECTION */
    // Create a JPanels to hold the Forces characteristics
    JPanel forcePanel = new JPanel(new FlowLayout());
    forcePanel.setBorder(new TitledBorder("Reaction:"));

    // Create JLabels for displaying labels next to the text fields
    JLabel nameLabel = new JLabel("Name:");
    JLabel frameLabel = new JLabel("Frame:");
    // Create JTextFields for displaying and editing name and age
    JTextField nameField = new JTextField(force.get(index).getName().toUpperCase(), 20);
    JTextField frameField = new JTextField("BODY", 15);

    forcePanel.add(nameLabel);
    forcePanel.add(nameField);
    forcePanel.add(frameLabel);
    forcePanel.add(frameField);

    mainPanel.add(forcePanel);

    ////////////////////////////////////////////////////////////////
    /*THIRD SECTION */
    // Create a panel to hold the Details
    JPanel locationPanel = new JPanel();
    locationPanel.setBorder(new TitledBorder("Location:"));
    locationPanel.setLayout(new FlowLayout());

    // Create JLabels and JTextFields for additional details
    JLabel location_x = new JLabel("x = ");
    JTextField location_x_text = new JTextField("" + force.get(index).getLocation().getX(), 10);
    JLabel location_y = new JLabel("y = ");
    JTextField location_y_text = new JTextField("" + force.get(index).getLocation().getY(),10);
    JLabel location_z = new JLabel("z = ");
    JTextField location_z_text = new JTextField("" + force.get(index).getLocation().getZ(),10);

    locationPanel.add(location_x);
    locationPanel.add(location_x_text);
    locationPanel.add(location_y);
    locationPanel.add(location_y_text);
    locationPanel.add(location_z);
    locationPanel.add(location_z_text);

    mainPanel.add(locationPanel);

    ////////////////////////////////////////////////////////////////
    /*LAST SECTION */
    // Create a panel to hold the Direction
    JPanel directionPanel = new JPanel();
    directionPanel.setBorder(new TitledBorder("Direction:"));
    directionPanel.setLayout(new FlowLayout());

    // Create JLabels and JTextFields for additional details
    JLabel dir_x = new JLabel("x = ");
    JTextField dir_x_text = new JTextField("" + force.get(index).getDirection().getX(),10);
    JLabel dir_y = new JLabel("y = ");
    JTextField dir_y_text = new JTextField("" + force.get(index).getDirection().getY(), 10);
    JLabel dir_z = new JLabel("z = ");
    JTextField dir_z_text = new JTextField("" + force.get(index).getDirection().getZ(), 10);

    directionPanel.add(dir_x);
    directionPanel.add(dir_x_text);
    directionPanel.add(dir_y);
    directionPanel.add(dir_y_text);
    directionPanel.add(dir_z);
    directionPanel.add(dir_z_text);

    mainPanel.add(directionPanel);

    ////////// BUTTons Panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());

    JButton editButton = new JButton("Edit");
    editButton.setActionCommand("Edit Details");
    editButton.addActionListener(event -> {
      // update the XML class
      force.get(index).setName(nameField.getText().toLowerCase());
      force.get(index).getLocation().setX(Double.parseDouble(location_x_text.getText()));
      force.get(index).getLocation().setY(Double.parseDouble(location_y_text.getText()));
      force.get(index).getLocation().setZ(Double.parseDouble(location_z_text.getText()));
      force.get(index).getDirection().setX(Double.parseDouble(dir_x_text.getText()));
      force.get(index).getDirection().setY(Double.parseDouble(dir_y_text.getText()));
      force.get(index).getDirection().setZ(Double.parseDouble(dir_z_text.getText()));

      // update the JTextFields
      nameField.setText("" + force.get(index).getName().toUpperCase());
      frameField.setText("" + frameField.getText());
      location_x_text.setText(""+ force.get(index).getLocation().getX());
      location_y_text.setText(""+ force.get(index).getLocation().getY());
      location_z_text.setText(""+ force.get(index).getLocation().getZ());
      dir_x_text.setText(""+ force.get(index).getDirection().getX());
      dir_y_text.setText(""+ force.get(index).getDirection().getY());
      dir_z_text.setText(""+ force.get(index).getDirection().getZ());

      // update the JList
      updateJList();

      // update cfg
//      cfg.getExternalReactions().getForce().set(index, force.get(index));
//      mainframe.refreshCfg(cfg);

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


  protected void onDeleteClick(int index) {
    this.force.remove(index);

    // update cfg
//    cfg.getExternalReactions().getForce().remove(index);

    // update JList
    updateJList();
  }


  protected void onAddClick() {
    JLabel name = new JLabel("Name");
    String[] nameOptions = {"pushback", "hook"};
    JComboBox<String> names = new JComboBox<>(nameOptions);

    JLabel location = new JLabel("<HTML><br/>Location:</HTML>");
    JLabel location_x = new JLabel("<HTML><br/>x = </HTML>");
    JTextField location_x_texts = new JTextField(10);
    location_x_texts.setText("0");
    JLabel location_y = new JLabel("<HTML><br/>y = </HTML>");
    JTextField location_y_texts = new JTextField(10);
    location_y_texts.setText("0");
    JLabel location_z = new JLabel("<HTML><br/>z = </HTML>");
    JTextField location_z_texts = new JTextField(10);
    location_z_texts.setText("0");

    JLabel direction = new JLabel("<HTML><br/>Direction:</HTML>");
    JLabel direction_x = new JLabel("<HTML><br/>x = </HTML>");
    JTextField direction_x_texts = new JTextField(10);
    direction_x_texts.setText("0");
    JLabel direction_y = new JLabel("<HTML><br/>y = </HTML>");
    JTextField direction_y_texts = new JTextField(10);
    direction_y_texts.setText("0");
    JLabel direction_z = new JLabel("<HTML><br/>z = </HTML>");
    JTextField direction_z_texts = new JTextField(10);
    direction_z_texts.setText("0");

    Object[] objects = { // Array of widgets to display
            name, names,
            location, location_x, location_x_texts, location_y, location_y_texts, location_z, location_z_texts,
            direction, direction_x, direction_x_texts, direction_y, direction_y_texts, direction_z, direction_z_texts
    };

    ImageIcon engineIcon = new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/car.png");
    int button = JOptionPane.showConfirmDialog( // Show the dialog
            this,
            objects,
            "New Force",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            engineIcon);
    if (button == JOptionPane.OK_OPTION) { // If OK clicked
      // Add the new force to the list of forces
      Force newForce = new Force();
      newForce.setName((String) names.getSelectedItem());

      Direction newDirection = new Direction();
      newDirection.setX(Double.parseDouble(direction_x_texts.getText()));
      newDirection.setY(Double.parseDouble(direction_y_texts.getText()));
      newDirection.setZ(Double.parseDouble(direction_z_texts.getText()));
      newForce.setDirection(newDirection);

      Location newLocation = new Location();
      newLocation.setX(Double.parseDouble(location_x_texts.getText()));
      newLocation.setY(Double.parseDouble(location_y_texts.getText()));
      newLocation.setZ(Double.parseDouble(location_z_texts.getText()));
      newForce.setLocation(newLocation);

      newForce.setFrame("BODY");

      this.force.add(newForce);

      // update cfg
//      cfg.getExternalReactions().getForce().add(newForce);

      // update the JList
      updateJList();
    }
  }

  private DefaultListModel<String> generateList() {
    DefaultListModel<String> listModel = new DefaultListModel<>();
    // Add some items to the list
    for(Force f : force) {
      listModel.addElement(f.getName() + " at point [" + f.getLocation().getX() + ", " + f.getLocation().getY() + ", "
              + f.getLocation().getZ() + "] in " + f.getLocation().getUnit() + " (in BODY frame)");
    }

    return listModel;
  }

  private void updateJList() {
    DefaultListModel<String> stringModels = generateList();
    String[] stringArray = new String[stringModels.size()];
    for (int i = 0; i < stringModels.size(); i++) {
      stringArray[i] = stringModels.getElementAt(i);
    }
    itemList.setListData(stringArray);
  }

  public List<String> getProperty() {
    return property;
  }

  public List<Force> getForce() {
    return force;
  }

  public String getFile() {
    return file;
  }

}
