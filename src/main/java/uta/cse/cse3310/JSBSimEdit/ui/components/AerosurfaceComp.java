package uta.cse.cse3310.JSBSimEdit.ui.components;

import generated.Clipto;
import generated.Domain;
import generated.Range;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class AerosurfaceComp extends JLabel {
    protected String description;
    protected String input;
    protected Domain domain;
    protected Range range;
    protected Object gain;
    protected Clipto clipto;
    protected String output;
    protected String name;
    private int offsetX, offsetY;

    public AerosurfaceComp(String desc, String in, Domain domain, Range range, Object gain, Clipto clipto, String output, String name, String file) {
        super(new ImageIcon(file));
        this.description = desc;
        this.input = in;
        this.domain = domain;
        this.range = range;
        this.gain = gain;
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
                showIconDetailsDialog(AerosurfaceComp.this);
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

    protected void showIconDetailsDialog(AerosurfaceComp gain) {
        JFrame detailsFrame = new JFrame("Gain Component");
        detailsFrame.setSize(400, 500);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Basic", createBasicPane(gain));

        detailsFrame.add(tabbedPane);

        detailsFrame.setLocationRelativeTo(this);
        detailsFrame.setVisible(true);
    }

    protected JPanel createBasicPane(AerosurfaceComp gain) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /*Components inside the Basic Pane*/
        // Information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        JLabel name = new JLabel("Name: ");
        JTextField name_field = new JTextField(gain.name, 15);
        namePanel.add(name);
        namePanel.add(name_field);

        JPanel typePanel = new JPanel();
        typePanel.setLayout(new FlowLayout());
        JLabel type = new JLabel("Type: ");
        JLabel type_field = new JLabel("AeroSurface Scale");
        typePanel.add(type);
        typePanel.add(type_field);

        infoPanel.add(namePanel);
        infoPanel.add(typePanel);

        // Domain
        JPanel domainPanel = minMaxPanel(this.domain);

        // Range
        JPanel clipperPanel = minMaxPanel(this.range);

        // Clipper
        JPanel rangePanel = minMaxPanel(this.clipto);


        // Gain
        JPanel gainPanel = new JPanel();
        gainPanel.setLayout(new FlowLayout());
        JLabel gainLabel = new JLabel("Default Value: ");
        String gain_value = String.valueOf(gain.gain);
        JTextField gain_field = new JTextField(gain_value, 10);
        gainPanel.add(gainLabel);
        gainPanel.add(gain_field);

        panel.add(infoPanel);
        panel.add(domainPanel);
        panel.add(rangePanel);
        panel.add(clipperPanel);
        panel.add(gainPanel);

        return panel;
    }

    private JPanel minMaxPanel(Clipto myClip) {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Clipper:"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel minPanel = new JPanel();
        minPanel.setLayout(new FlowLayout());
        JLabel min = new JLabel("Min: ");
        String min_value = (myClip != null ? String.valueOf(myClip.getMin()) : "0");
        JTextField min_field = new JTextField(min_value, 15);
        minPanel.add(min);
        minPanel.add(min_field);

        JPanel maxPanel = new JPanel();
        maxPanel.setLayout(new FlowLayout());
        JLabel max = new JLabel("Max: ");
        String max_value = (myClip != null ? String.valueOf(myClip.getMax()) : "0");
        JTextField max_field = new JTextField(max_value, 15);
        maxPanel.add(max);
        maxPanel.add(max_field);

        panel.add(minPanel);
        panel.add(maxPanel);

        return panel;
    }

    private JPanel minMaxPanel(Range myRange) {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Range:"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel minPanel = new JPanel();
        minPanel.setLayout(new FlowLayout());
        JLabel min = new JLabel("Min: ");
        String min_value = (myRange != null ? String.valueOf(myRange.getMin()) : "0");
        JTextField min_field = new JTextField(min_value, 15);
        minPanel.add(min);
        minPanel.add(min_field);

        JPanel maxPanel = new JPanel();
        maxPanel.setLayout(new FlowLayout());
        JLabel max = new JLabel("Max: ");
        String max_value = (myRange != null ? String.valueOf(myRange.getMax()) : "0");
        JTextField max_field = new JTextField(max_value, 15);
        maxPanel.add(max);
        maxPanel.add(max_field);

        panel.add(minPanel);
        panel.add(maxPanel);

        return panel;
    }

    private JPanel minMaxPanel(Domain myDomain) {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Domain:"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel minPanel = new JPanel();
        minPanel.setLayout(new FlowLayout());
        JLabel min = new JLabel("Min: ");
        String min_value = (myDomain != null ? String.valueOf(myDomain.getMin()) : "0");
        JTextField min_field = new JTextField(min_value, 15);
        minPanel.add(min);
        minPanel.add(min_field);

        JPanel maxPanel = new JPanel();
        maxPanel.setLayout(new FlowLayout());
        JLabel max = new JLabel("Max: ");
        String max_value = (myDomain != null ? String.valueOf(myDomain.getMax()) : "0");
        JTextField max_field = new JTextField(max_value, 15);
        maxPanel.add(max);
        maxPanel.add(max_field);

        panel.add(minPanel);
        panel.add(maxPanel);

        return panel;
    }
}
