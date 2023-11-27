package uta.cse.cse3310.JSBSimEdit.ui.components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Input extends JPanel {
  
  public Input() {
    setLayout(new BorderLayout());
    DefaultListModel<String> listModel = new DefaultListModel<>();

    //TODO: Add some items to the list
    listModel.addElement("File 1");
    listModel.addElement("File 2");

    // Create a JList with the listModel
    // TODO: Fix type of JList, not String
    JList<String> itemList = new JList<>(listModel);
    // Automatically select the first item
    if (listModel.getSize() > 0) {
      itemList.setSelectedIndex(0);
    }

    itemList.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
          if (e.getClickCount() == 2) { // Detect double-click
              int selectedIndex = itemList.getSelectedIndex();
              if (selectedIndex >= 0) {
                  String selectedItem = listModel.getElementAt(selectedIndex);
                  
                  onDetailClick(selectedItem);
              }
          }
        }

    });

    //FIXME: Thinking a way to resolve redundancy
    String selectedItem = listModel.getElementAt(0);

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
    deleteButton.addActionListener(event -> onDeleteClick());

    JButton detailButton = new JButton("Detail");
    detailButton.setActionCommand("More Info");
    detailButton.setToolTipText("See detail of the item");
    //TODO: implement button action
    detailButton.addActionListener(event -> onDetailClick(selectedItem));


    //TODO: Double-clicked functionality === onDetailClick()
    

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

  // TODO: String type is not compatible
  protected void onDetailClick(String detail) {
    JFrame detailsFrame = new JFrame("Item Details");
    detailsFrame.setSize(600, 400);

    /*Create MAIN PANEL */
    JPanel mainPanel = new JPanel();
    // mainPanel.setBorder(new TitledBorder());
    mainPanel.setLayout(new GridLayout(4,1));

    // Create a JPanels to hold the detail
    JPanel detailsPanel = new JPanel(new FlowLayout());
    JLabel detailsLabel = new JLabel("Details for " + detail );
    detailsPanel.add(detailsLabel);

    mainPanel.add(detailsPanel);

    ////////////////////////////////////////////////////////////////
    /*SECOND SECTION */
    // Create a JPanels to hold the Forces characteristics
    JPanel forcePanel = new JPanel(new FlowLayout());
    forcePanel.setBorder(new TitledBorder("Reaction:"));

    // Create JLabels for displaying labels next to the text fields
    JLabel nameLabel = new JLabel("Name:");
    JLabel frameLabel = new JLabel("Frame:");
    // Create JTextFields for displaying and editing name and age
    JTextField nameField = new JTextField("PUSHBACK", 20);
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
    JTextField location_x_text = new JTextField("-2.98081", 10);
    
    JLabel location_y = new JLabel("y = ");
    JTextField location_y_text = new JTextField("0.0",10);

    JLabel location_z = new JLabel("z = ");
    JTextField location_z_text = new JTextField("-1.9683",10);

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
    JTextField dir_x_text = new JTextField("1",10);
    
    JLabel dir_y = new JLabel("y = ");
    JTextField dir_y_text = new JTextField("0", 10);

    JLabel dir_z = new JLabel("z = ");
    JTextField dir_z_text = new JTextField("0", 10);

    directionPanel.add(dir_x);
    directionPanel.add(dir_x_text);
    directionPanel.add(dir_y);
    directionPanel.add(dir_y_text);
    directionPanel.add(dir_z);
    directionPanel.add(dir_z_text);

    mainPanel.add(directionPanel);


    ////////////////////////////////
    detailsFrame.add(mainPanel);

    detailsFrame.setLocationRelativeTo(this);
    detailsFrame.setVisible(true);
  }


  protected void onDeleteClick() {
  }


  protected void onAddClick() {
  }
}
