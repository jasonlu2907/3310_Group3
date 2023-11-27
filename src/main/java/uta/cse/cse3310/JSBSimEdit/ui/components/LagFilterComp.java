package uta.cse.cse3310.JSBSimEdit.ui.components;

import generated.Clipto;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class LagFilterComp extends JLabel {
    protected String description;
    protected String input;
    protected double c1;
    protected Clipto clipto;
    protected String output;
    protected String name;
    protected int offsetX, offsetY;

    public LagFilterComp(String desc, String in, double c1, Clipto clipto, String output, String name, String file) {
        super(new ImageIcon(file));
        this.description = desc;
        this.input = in;
        this.c1 = c1;
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
                showIconDetailsDialog(LagFilterComp.this);
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

    protected void showIconDetailsDialog(LagFilterComp filter) {
        JFrame detailsFrame = new JFrame("LagFilter Component");
        detailsFrame.setSize(400, 400);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Basic", createBasicPane(filter));

        detailsFrame.add(tabbedPane);

        detailsFrame.setLocationRelativeTo(this);
        detailsFrame.setVisible(true);
    }

    protected JPanel createBasicPane(LagFilterComp filter) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /*Components inside the Basic Pane*/
        // Information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        JLabel name = new JLabel("Name: ");
        JTextField name_field = new JTextField(filter.name, 15);
        namePanel.add(name);
        namePanel.add(name_field);

        JPanel typePanel = new JPanel();
        typePanel.setLayout(new FlowLayout());
        JLabel type = new JLabel("Type: ");
        JLabel type_field = new JLabel("LAG_FILTER");
        typePanel.add(type);
        typePanel.add(type_field);

        infoPanel.add(namePanel);
        infoPanel.add(typePanel);

        // Clipper
        JPanel clipperPanel = new JPanel();
        clipperPanel.setBorder(new TitledBorder("Clipper:"));
        clipperPanel.setLayout(new BoxLayout(clipperPanel, BoxLayout.Y_AXIS));

        JPanel minPanel = new JPanel();
        minPanel.setLayout(new FlowLayout());
        JLabel min = new JLabel("Min: ");
        String min_value = (filter.clipto != null ? String.valueOf(filter.clipto.getMin()) : "0");
        JTextField min_field = new JTextField(min_value, 15);
        minPanel.add(min);
        minPanel.add(min_field);

        JPanel maxPanel = new JPanel();
        maxPanel.setLayout(new FlowLayout());
        JLabel max = new JLabel("Max: ");
        String max_value = (filter.clipto != null ? String.valueOf(filter.clipto.getMax()) : "0");
        JTextField max_field = new JTextField(max_value, 15);
        maxPanel.add(max);
        maxPanel.add(max_field);

        clipperPanel.add(minPanel);
        clipperPanel.add(maxPanel);

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout());
        JLabel filterLabel = new JLabel("C1 Value: ");
        String filter_value = String.valueOf(filter.c1);
        JTextField filter_field = new JTextField(filter_value, 10);
        filterPanel.add(filterLabel);
        filterPanel.add(filter_field);

        panel.add(infoPanel);
        panel.add(clipperPanel);
        panel.add(filterPanel);

        return panel;
    }
}
