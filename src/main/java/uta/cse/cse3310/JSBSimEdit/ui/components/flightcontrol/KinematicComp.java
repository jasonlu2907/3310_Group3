package uta.cse.cse3310.JSBSimEdit.ui.components.flightcontrol;

import generated.Clipto;
import generated.Kinematic;
import generated.Setting;
import generated.Traverse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

public class KinematicComp extends JLabel {
    private final Kinematic kinematic;
    private String description;
    private String input;
    private final Traverse traverse;
    private Clipto clipto;
    private String output;
    private String name;
    private int offsetX, offsetY;

    public KinematicComp(Kinematic kinematic, String file) {
        super(new ImageIcon(file));

        this.kinematic = kinematic;
        this.description = kinematic.getDescription();
        this.input = kinematic.getInput();
        this.traverse = kinematic.getTraverse();
        this.clipto = kinematic.getClipto();
        this.output = kinematic.getOutput();
        this.name = kinematic.getName();

        ClickListener clickListener = new ClickListener();
        this.addMouseListener(clickListener);

        DragListener dragListener = new DragListener();
        this.addMouseMotionListener(dragListener);

    }

    private class ClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                // for now only print the kinematic details
                showIconDetailsDialog(KinematicComp.this);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // If the mouse is pressed on a label, set the offsets for dragging
            offsetX = e.getX();
            offsetY = e.getY();
        }
    }

    private class DragListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            int newX = getX() + e.getX() - offsetX;
            int newY = getY() + e.getY() - offsetY;
            setLocation(newX, newY);

            // Repaint the panel (optional, as the layout is set to null)
//            repaint();
        }
    }

    private void showIconDetailsDialog(KinematicComp kine) {
        JFrame detailsFrame = new JFrame("Kinemat Component");
        detailsFrame.setSize(400, 600);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Basic", createBasicPane(kine));
        tabbedPane.add("Kinemat", createKinematPane());

        detailsFrame.add(tabbedPane);

        detailsFrame.setLocationRelativeTo(this);
        detailsFrame.setVisible(true);
    }

    private JPanel createBasicPane(KinematicComp kine) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        /*Components inside the Basic Pane*/
        JLabel name = new JLabel("Name: ", JLabel.TRAILING);
        JTextField name_field = new JTextField(kine.name, 15);
        JLabel type = new JLabel("Type: ", JLabel.TRAILING);
        JLabel type_field = new JLabel("KINEMAT");

        panel.add(name);
        panel.add(name_field);
        panel.add(type);
        panel.add(type_field);

        JButton save = new JButton("Save changes");
        save.setActionCommand("Save changes");
        save.setToolTipText("Save changes");
        save.addActionListener(event -> {
            this.name = name_field.getText();
            kinematic.setName(name_field.getText());
        });

        panel.add(save);

        return panel;
    }

    private JPanel createKinematPane() {
        JPanel panel = new JPanel();

        /*Components inside the Basic Pane*/
        String[] columnNames = {"Position", "Time"};
        List<Setting> settingList =  this.traverse.getSetting();

        //convert ArrayList<String[]> to Object[][]
        Object[][] rowData = generateListItems();

        JTable table = new JTable(rowData, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(300, 450));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);

        JButton save = new JButton("Save changes");
        save.setActionCommand("Save changes");
        save.setToolTipText("Save changes");
        save.addActionListener(event -> {
            for (int i = 0; i < table.getRowCount(); i++) {
                double position = Double.parseDouble(table.getValueAt(i, 0).toString());
                double time = Double.parseDouble(table.getValueAt(i, 1).toString());

                // Update the corresponding Setting object with the new values
                Setting setting = settingList.get(i);
                setting.setPosition(position);
                setting.setTime(time);

                // Update the traverse prop
                this.traverse.getSetting().set(i, setting);

                // Update the table data
                table.setValueAt(String.valueOf(position), i, 0);
                table.setValueAt(String.valueOf(time), i, 1);
            }
            this.kinematic.setTraverse(this.traverse);
        });

        panel.add(scrollPane);
        panel.add(save);

        return panel;
    }

    private Object[][] generateListItems() {
        List<Setting> settingList =  this.traverse.getSetting();
        ArrayList<String[]> data = new ArrayList<>();

        for(Setting setting : settingList) {
            String[] temp = {String.valueOf(setting.getPosition()), String.valueOf(setting.getTime())};
            data.add(temp);
        }

        //convert ArrayList<String[]> to Object[][]
        Object[][] rowData = new Object[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            rowData[i] = data.get(i);
        }

        return rowData;
    }

}
