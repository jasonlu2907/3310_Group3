package uta.cse.cse3310.JSBSimEdit.ui.components;

import generated.Clipto;
import generated.Setting;
import generated.Traverse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class KinematicComp extends JLabel {
    private String description;
    private String input;
    private final Traverse traverse;
    private Clipto clipto;
    private String output;
    private final String name;
    private int offsetX, offsetY;

    public KinematicComp(String description, String input, Traverse traverse, Clipto clipto, String output,
                         String name, String file, int x, int y) {
        super(new ImageIcon(file));
//        setOpaque(true);

        this.description = description;
        this.input = input;
        this.traverse = traverse;
        this.clipto = clipto;
        this.output = output;
        this.name = name;

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

        return panel;
    }

    private JPanel createKinematPane() {
        JPanel panel = new JPanel();

        /*Components inside the Basic Pane*/
        String[] columnNames = {"Position", "Time"};
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

        JTable table = new JTable(rowData, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(300, 500));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane);

        return panel;
    }

}
