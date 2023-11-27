package uta.cse.cse3310.JSBSimEdit.ui.components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class BuoyantForce extends JPanel {
  public BuoyantForce() {
    setLayout(new BorderLayout());

    DefaultListModel<String> listModel = new DefaultListModel<>();

    //TODO: Add some items to the list
    listModel.addElement("HYDROGEN at point [2.11, 0.0, -1.432] in M");
    listModel.addElement("HELIUM at point [32.276, 0.0, 22.334] in M");

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

  protected void onDetailClick(String detail) {
    System.out.println(detail);

    JFrame detailsFrame = new JFrame("Gas Cell Information");
    detailsFrame.setSize(540, 700);

    //////////////////////////////// MAIN PANEL////////////////////////////////
    /*Create MAIN PANEL */
    JPanel mainPanel = new JPanel();
    // mainPanel.setBorder(new TitledBorder());
    mainPanel.setLayout(new GridLayout(4,1));

    ////////////////////////////////////////////////////////////////
    /*FIRST SECTION */
    // Create a panel to hold the Details
    JPanel locationPanel = new JPanel();
    locationPanel.setBorder(new TitledBorder("Location:"));
    locationPanel.setLayout(new FlowLayout());

    // Create JLabels and JTextFields for additional details
    JLabel location_x = new JLabel("x = ");
    JTextField location_x_text = new JTextField("2.11", 10);
    
    JLabel location_y = new JLabel("y = ");
    JTextField location_y_text = new JTextField("0.0",10);

    JLabel location_z = new JLabel("z = ");
    JTextField location_z_text = new JTextField("-1.432",10);

    String[] unitStrings = { "M", "IN" };
    JComboBox<String> unitList = new JComboBox<String>(unitStrings);
    unitList.setSelectedIndex(0);
    // unitList.addActionListener(this);

    locationPanel.add(location_x);
    locationPanel.add(location_x_text);
    locationPanel.add(location_y);
    locationPanel.add(location_y_text);
    locationPanel.add(location_z);
    locationPanel.add(location_z_text);
    locationPanel.add(unitList);

    mainPanel.add(locationPanel);

    ////////////////////////////////////////////////////////////////
    /*SECOND PANEL WIDTH */
    JPanel detailsPanel = new JPanel(new FlowLayout());
    detailsPanel.setBorder(new TitledBorder("Width:"));

    // Create JLabels and JTextFields for additional details
    JLabel width_x = new JLabel("x = ");
    JTextField width_x_text = new JTextField("5.0", 10);
    JComboBox<String> x_Width = new JComboBox<String>(unitStrings);
    x_Width.setSelectedIndex(0);

    JLabel width_y = new JLabel("y = ");
    JTextField width_y_text = new JTextField("1.7",10);
    JComboBox<String> y_Width = new JComboBox<String>(unitStrings);
    y_Width.setSelectedIndex(0);

    JLabel width_z = new JLabel("z = ");
    JTextField width_z_text = new JTextField("3.2",10);
    JComboBox<String> z_Width = new JComboBox<String>(unitStrings);
    z_Width.setSelectedIndex(0);

    detailsPanel.add(width_x);
    detailsPanel.add(width_x_text);
    detailsPanel.add(x_Width);
    detailsPanel.add(width_y);
    detailsPanel.add(width_y_text);
    detailsPanel.add(y_Width);
    detailsPanel.add(width_z);
    detailsPanel.add(width_z_text);
    detailsPanel.add(z_Width);

    mainPanel.add(detailsPanel);

    ////////////////////////////////////////////////////////////////
    /*THIRD SECTION Radius*/
    // Create a JPanels to hold the Forces characteristics
    JPanel forcePanel = new JPanel(new FlowLayout());
    forcePanel.setBorder(new TitledBorder("Radius:"));

    // Create JLabels and JTextFields for additional details
    JLabel radius_x = new JLabel("x = ");
    JTextField radius_x_text = new JTextField("1.5", 10);
    JComboBox<String> x_Radius = new JComboBox<String>(unitStrings);
    x_Radius.setSelectedIndex(0);

    JLabel radius_y = new JLabel("y = ");
    JTextField radius_y_text = new JTextField("1.0",10);
    JComboBox<String> y_Radius = new JComboBox<String>(unitStrings);
    y_Radius.setSelectedIndex(0);

    JLabel radius_z = new JLabel("z = ");
    JTextField radius_z_text = new JTextField("0.75",10);
    JComboBox<String> z_Radius = new JComboBox<String>(unitStrings);
    z_Radius.setSelectedIndex(0);

    forcePanel.add(radius_x);
    forcePanel.add(radius_x_text);
    forcePanel.add(x_Radius);
    forcePanel.add(radius_y);
    forcePanel.add(radius_y_text);
    forcePanel.add(y_Radius);
    forcePanel.add(radius_z);
    forcePanel.add(radius_z_text);
    forcePanel.add(z_Radius);

    mainPanel.add(forcePanel);

    ////////////////////////////////////////////////////////////////
    /*LAST SECTION */
    // Create a panel to hold the Direction
    JPanel morePanel = new JPanel(new FlowLayout());
    morePanel.setBorder(new TitledBorder("More:"));
    String[] pressureStrings = { "PA", "PSI" };
    String[] valcoStrings = { "M4*SEC/KG", "FT4*SEC/SLUG" };

    // Create JLabels and JTextFields for additional details
    JLabel overpressure = new JLabel("Max Overpressure: ");
    JTextField overpressure_text = new JTextField("33",5);
    JComboBox<String> pressureBox = new JComboBox<String>(pressureStrings);
    pressureBox.setSelectedIndex(0);

    JLabel valve_coef = new JLabel("Valve Coefficient: ");
    JTextField valve_coef_text = new JTextField("145",5);
    JComboBox<String> valcoBox = new JComboBox<String>(valcoStrings);

    JLabel fullness = new JLabel("Fullness: ");
    JTextField fullness_text = new JTextField("21",3);

    JLabel heat = new JLabel("Heat Transfer: ");
    JTextField heat_text = new JTextField("coefficient functions " + " (lbs*ft/sec)",20);

    morePanel.add(overpressure);
    morePanel.add(overpressure_text);
    morePanel.add(pressureBox);
    morePanel.add(valve_coef);
    morePanel.add(valve_coef_text);
    morePanel.add(valcoBox);
    morePanel.add(fullness);
    morePanel.add(fullness_text);
    morePanel.add(heat);
    morePanel.add(heat_text);

    mainPanel.add(morePanel);

    //////////////////////////////BALLONET PANEL//////////////////////////////////
    //////////////////////////// REPEATED PANEL
    /*Create MAIN PANEL */
    JPanel main2Panel = new JPanel();
    // main2Panel.setBorder(new TitledBorder());
    main2Panel.setLayout(new GridLayout(4,1));

    ////////////////////////////////////////////////////////////////
    /*FIRST SECTION */
    // Create a panel to hold the Details
    JPanel helloPanel = new JPanel();
    helloPanel.setBorder(new TitledBorder("Location:"));
    helloPanel.setLayout(new FlowLayout());

    // Create JLabels and JTextFields for additional details
    JLabel hellox = new JLabel("x = ");
    JTextField hellox_text = new JTextField("2.11", 10);
    
    JLabel helloy = new JLabel("y = ");
    JTextField helloy_text = new JTextField("0.0",10);

    JLabel helloz = new JLabel("z = ");
    JTextField helloz_text = new JTextField("-1.432",10);

    String[] helloStrings = { "M", "IN" };
    JComboBox<String> helloList = new JComboBox<String>(helloStrings);
    helloList.setSelectedIndex(1);
    // unitList.addActionListener(this);

    helloPanel.add(hellox);
    helloPanel.add(hellox_text);
    helloPanel.add(helloy);
    helloPanel.add(helloy_text);
    helloPanel.add(helloz);
    helloPanel.add(helloz_text);
    helloPanel.add(helloList);

    main2Panel.add(helloPanel);

    
    ////////////////////////////////
    /*TABBED PANE */
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Main", mainPanel);
    tabbedPane.addTab("Ballonet", main2Panel);

    detailsFrame.add(tabbedPane);

    detailsFrame.setLocationRelativeTo(this);
    detailsFrame.setVisible(true);
  }

  // public void actionPerformed(ActionEvent e) {
  //   JComboBox cb = (JComboBox)e.getSource();
  //   String petName = (String)cb.getSelectedItem();
  //   updateLabel(petName);
  // }

  protected void onDeleteClick() {
  }


  protected void onAddClick() {
  }

}
