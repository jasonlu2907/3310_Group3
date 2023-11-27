package uta.cse.cse3310.JSBSimEdit.ui.components;

import generated.Clipto;
import generated.Test;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

public class PidComp extends JLabel {
    private String description;
    private String input;
    private Float kp;
    private Float ki;
    private Float kd;
    private String trigger;
    private Clipto clipto;
    private String output;
    private String name;
    private int offsetX, offsetY;

    public PidComp(String desc, String input, Float kp, Float ki, Float kd, String trigger, Clipto clipto, String output, String name, String file) {
        super(new ImageIcon(file));
        this.description = desc;
        this.input = input;
        this.kp = kp;
        this.ki = ki;
        this.trigger = trigger;
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
                showIconDetailsDialog(PidComp.this);
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

    private void showIconDetailsDialog(PidComp pid) {
        JFrame detailsFrame = new JFrame("Pid Component");
        detailsFrame.setSize(400, 400);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Basic", createBasicPane(pid));

        detailsFrame.add(tabbedPane);

        detailsFrame.setLocationRelativeTo(this);
        detailsFrame.setVisible(true);
    }

    private JPanel createBasicPane(PidComp pid) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /*Components inside the Basic Pane*/
        // Information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        JLabel name = new JLabel("Name: ");
        JTextField name_field = new JTextField(pid.name, 15);
        namePanel.add(name);
        namePanel.add(name_field);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        JLabel input = new JLabel("Input: ");
        JTextField input_field = new JTextField(pid.input, 15);
        inputPanel.add(input);
        inputPanel.add(input_field);

        infoPanel.add(namePanel);
        infoPanel.add(inputPanel);

        // Clipper
        JPanel clipperPanel = new JPanel();
        clipperPanel.setBorder(new TitledBorder("Clipper:"));
        clipperPanel.setLayout(new BoxLayout(clipperPanel, BoxLayout.Y_AXIS));

        DefaultListModel<String> KDKIKPListModel = new DefaultListModel<>();
        KDKIKPListModel.addElement(pid.kp == 0 ? "0" : String.valueOf(pid.kp));
        KDKIKPListModel.addElement(pid.ki == 0 ? "0" : String.valueOf(pid.ki));
        KDKIKPListModel.addElement(pid.kd == 0 ? "0" : String.valueOf(pid.kd));
        JList<String> KDKIKPList = new JList<>(KDKIKPListModel);

        clipperPanel.add(new JScrollPane(KDKIKPList));

        JPanel triggerPanel = new JPanel();
        triggerPanel.setLayout(new FlowLayout());
        JLabel triggerLabel = new JLabel("Trigger: ");
        JTextField trigger_field = new JTextField(String.valueOf(pid.trigger), 20);
        triggerPanel.add(triggerLabel);
        triggerPanel.add(trigger_field);

        panel.add(infoPanel);
        panel.add(clipperPanel);
        panel.add(triggerPanel);

        return panel;
    }

}
