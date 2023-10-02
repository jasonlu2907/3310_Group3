package uta.cse.cse3310.JSBSimEdit.components;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class BuoyantForce extends JPanel {
  public BuoyantForce() {
    setLayout(new BorderLayout());
    DefaultListModel<String> listModel = new DefaultListModel<>();

    // Add some items to the list
    listModel.addElement("Item 1");
    listModel.addElement("Item 2");
    listModel.addElement("Item 3");

    // Create a JList with the listModel
    JList<String> itemList = new JList<>(listModel);

    // Create a JButton to perform an action when selected item is clicked
    JButton selectButton = new JButton("Add");
    selectButton.setActionCommand("Add Item");
    selectButton.setToolTipText("Add new item");
    // selectButton.addActionListener(event -> onAddClick());

    JButton deleteButton = new JButton("Delete");
    deleteButton.setActionCommand("Delete Item");
    deleteButton.setToolTipText("Delete the item");
    // deleteButton.addActionListener(event -> onDeleteClick());

    JButton detailButton = new JButton("Detail");
    detailButton.setActionCommand("More Info");
    detailButton.setToolTipText("See detail of the item");
    // detailButton.addActionListener(event -> onDetailClick());

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(selectButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(detailButton);

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(new JScrollPane(itemList), BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);

    // Add the panel to the frame
    add(panel, BorderLayout.CENTER);
  }
}
