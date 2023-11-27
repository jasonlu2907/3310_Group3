package uta.cse.cse3310.JSBSimEdit.ui.components.system;

import generated.System;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class SystemGenerator extends JPanel {

    private final List<String> property;
    private final System system;
    private final List<Object> systemProp;
    private final String file;

    private int selectedIndex;
    private final JList<String> itemList;

    public SystemGenerator(List<String> property, System sys, String file) {
        this.property = property;
        this.system = sys;
        this.file = file;

        systemProp = sys.getPropertyOrSensorOrChannel();

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
        detailsFrame.setSize(250, 200);

        /*Create MAIN PANEL */
        JPanel mainPanel = new JPanel();
        // mainPanel.setBorder(new TitledBorder());
        mainPanel.setLayout(new GridLayout(2,1));

        ////////////////////////////////////////////////////////////////
        /*SECOND SECTION */
        // Create a JPanels to hold the Forces characteristics
        JPanel forcePanel = new JPanel(new FlowLayout());
        forcePanel.setBorder(new TitledBorder("Property:"));

        // Create JLabels for displaying labels next to the text fields
        JLabel nameLabel = new JLabel("Name:");
        // Create JTextFields for displaying and editing name and age
        JTextField nameField = new JTextField(property.get(index).toLowerCase(), 20);

        forcePanel.add(nameLabel);
        forcePanel.add(nameField);

        mainPanel.add(forcePanel);

                ////////// BUTTons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton editButton = new JButton("Edit");
        editButton.setActionCommand("Edit Details");
        editButton.addActionListener(event -> {
            // update the String list (property) and the XML class
            property.set(index, nameField.getText().toLowerCase());
            systemProp.set(index, nameField.getText().toLowerCase());

            // update the JTextFields
            nameField.setText(property.get(index).toLowerCase());

            // update the JList
            updateJList();

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
        this.property.remove(index);
        // remove from cfg
        this.systemProp.remove(index);

        // update JList
        updateJList();
    }


    protected void onAddClick() {
        JLabel name = new JLabel("Name");
        JTextField name_texts = new JTextField(10);

        Object[] objects = { // Array of widgets to display
                name, name_texts,
        };

        ImageIcon engineIcon = new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/car.png");
        int button = JOptionPane.showConfirmDialog( // Show the dialog
                this,
                objects,
                "New Property",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                engineIcon);
        if (button == JOptionPane.OK_OPTION) { // If OK clicked
            // Add the new property to the list of properties
            String newProp = name_texts.getText();
            this.property.add(newProp);
            // Add to cfg
            this.systemProp.add(newProp);

            // update the JList
            updateJList();
        }
    }

    private DefaultListModel<String> generateList() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        // Add some items to the list
        for(String prop : property) {
            listModel.addElement(prop);
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
}
