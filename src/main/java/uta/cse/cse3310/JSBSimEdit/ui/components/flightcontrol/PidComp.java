package uta.cse.cse3310.JSBSimEdit.ui.components.flightcontrol;

import generated.Clipto;
import generated.Pid;
import generated.Test;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

public class PidComp extends JLabel {
    private final Pid pid;
    private String description;
    private final String input;
    private final Float kp;
    private final Float ki;
    private final Float kd;
    private String trigger;
    private Clipto clipto;
    private String output;
    private String name;
    private int offsetX, offsetY;

    public PidComp(Pid pid, String file) {
        super(new ImageIcon(file));

        this.pid = pid;
        this.description = pid.getDescription();
        this.input = pid.getInput();
        this.kp = pid.getKp();
        this.ki = pid.getKi();
        this.kd = pid.getKd();
        this.trigger = pid.getTrigger();
        this.clipto = pid.getClipto();
        this.output = pid.getOutput();
        this.name = pid.getName();

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
                showIconDetailsDialog();
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

    private void showIconDetailsDialog() {
        JFrame detailsFrame = new JFrame("Pid Component");
        detailsFrame.setSize(400, 400);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Basic", createBasicPane());

        detailsFrame.add(tabbedPane);

        detailsFrame.setLocationRelativeTo(this);
        detailsFrame.setVisible(true);
    }

    private JPanel createBasicPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /*Components inside the Basic Pane*/
        // Information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        JLabel name = new JLabel("Name: ");
        JTextField name_field = new JTextField(this.name, 15);
        namePanel.add(name);
        namePanel.add(name_field);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        JLabel input = new JLabel("Input: ");
        JTextField input_field = new JTextField(this.input, 15);
        inputPanel.add(input);
        inputPanel.add(input_field);

        infoPanel.add(namePanel);
        infoPanel.add(inputPanel);

        // Clipper
        JPanel clipperPanel = new JPanel();
        clipperPanel.setBorder(new TitledBorder("Clipper:"));
        clipperPanel.setLayout(new BoxLayout(clipperPanel, BoxLayout.Y_AXIS));

        DefaultListModel<String> KDKIKPListModel = new DefaultListModel<>();
        KDKIKPListModel.addElement(this.kp == 0 ? "0" : String.valueOf(this.kp));
        KDKIKPListModel.addElement(this.ki == 0 ? "0" : String.valueOf(this.ki));
        KDKIKPListModel.addElement(this.kd == 0 ? "0" : String.valueOf(this.kd));
        JList<String> KDKIKPList = new JList<>(KDKIKPListModel);

        clipperPanel.add(new JScrollPane(KDKIKPList));

        JPanel triggerPanel = new JPanel();
        triggerPanel.setLayout(new FlowLayout());
        JLabel triggerLabel = new JLabel("Trigger: ");
        JTextField trigger_field = new JTextField(String.valueOf(this.trigger), 20);
        triggerPanel.add(triggerLabel);
        triggerPanel.add(trigger_field);

        JButton save = new JButton("Save changes");
        save.setActionCommand("Save changes");
        save.setToolTipText("Save changes");
        save.addActionListener(event -> {
            this.name = name_field.getText();
            this.pid.setName(name_field.getText());

            this.pid.setInput(input_field.getText());

            if(this.clipto == null) {
                this.clipto = new Clipto();
            }
            this.clipto.setMax(0);
            this.clipto.setMin(0);
            this.pid.setClipto(this.clipto);

            this.pid.setKp(Float.valueOf(KDKIKPListModel.get(0)));
            KDKIKPListModel.set(0, String.valueOf(this.pid.getKp()));
            this.pid.setKi(Float.valueOf(KDKIKPListModel.get(1)));
            KDKIKPListModel.set(1, String.valueOf(this.pid.getKi()));
            this.pid.setKd(Float.valueOf(KDKIKPListModel.get(2)));
            KDKIKPListModel.set(2, String.valueOf(this.pid.getKd()));

            this.trigger = trigger_field.getText();
            this.pid.setTrigger(this.trigger);

        });

        panel.add(infoPanel);
        panel.add(clipperPanel);
        panel.add(triggerPanel);
        panel.add(save);

        return panel;
    }

}
