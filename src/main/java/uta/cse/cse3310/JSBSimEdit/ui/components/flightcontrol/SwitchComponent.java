package uta.cse.cse3310.JSBSimEdit.ui.components.flightcontrol;

import generated.Clipto;
import generated.Default;
import generated.Switch;
import generated.Test;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

public class SwitchComponent extends JLabel {
    private Switch iSwitch;
    private String description;
    private String input;
    private final Default _default;
    private final List<Test> test;
    private Clipto clipto;
    private Object output;
    private String name;
    private int offsetX, offsetY;

    public SwitchComponent(Switch iSwitch, String file) {
        super(new ImageIcon(file));

        this.iSwitch = iSwitch;
        this.description = iSwitch.getDescription();
        this.input = iSwitch.getInput();
        this._default = iSwitch.getDefault();
        this.test = iSwitch.getTest();
        this.clipto = iSwitch.getClipto();
        this.output = iSwitch.getOutput();
        this.name = iSwitch.getName();

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
        JFrame detailsFrame = new JFrame("Switch Component");
        detailsFrame.setSize(400, 400);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Basic", createBasicPane());
        tabbedPane.add("Input", createInputPane());

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

        JPanel typePanel = new JPanel();
        typePanel.setLayout(new FlowLayout());
        JLabel type = new JLabel("Type: ");
        JLabel type_field = new JLabel("SWITCH");
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
        String min_value = (this.clipto != null ? String.valueOf(this.clipto.getMin()) : "0");
        JTextField min_field = new JTextField(min_value, 15);
        minPanel.add(min);
        minPanel.add(min_field);

        JPanel maxPanel = new JPanel();
        maxPanel.setLayout(new FlowLayout());
        JLabel max = new JLabel("Max: ");
        String max_value = (this.clipto != null ? String.valueOf(this.clipto.getMax()) : "0");
        JTextField max_field = new JTextField(max_value, 15);
        maxPanel.add(max);
        maxPanel.add(max_field);

        clipperPanel.add(minPanel);
        clipperPanel.add(maxPanel);

        JPanel defaultPanel = new JPanel();
        defaultPanel.setLayout(new FlowLayout());
        JLabel defaultLabel = new JLabel("Default Value: ");
        String default_value = String.valueOf(this._default.getValue());
        JTextField default_field = new JTextField(default_value, 10);
        defaultPanel.add(defaultLabel);
        defaultPanel.add(default_field);

        JButton save = new JButton("Save changes");
        save.setActionCommand("Save changes");
        save.setToolTipText("Save changes");
        save.addActionListener(event -> {
            this.name = name_field.getText();
            this.iSwitch.setName(name_field.getText());

            if(this.clipto == null) {
                this.clipto = new Clipto();
            }
            this.clipto.setMax(Double.parseDouble(max_field.getText()));
            this.clipto.setMin(Double.parseDouble(min_field.getText()));
            this.iSwitch.setClipto(this.clipto);

            this._default.setValue(default_field.getText());
            this.iSwitch.setDefault(this._default);

        });

        panel.add(infoPanel);
        panel.add(clipperPanel);
        panel.add(defaultPanel);
        panel.add(save);

        return panel;
    }

    private JPanel createInputPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        List<Test> tests = this.test;
        DefaultListModel<String> inputListModel = new DefaultListModel<>();
        for (Test test : tests) {
            String[] temp = test.getValue().split("\\s+");
//            System.out.println(temp[temp.length - 1]);
            double doubt = Double.parseDouble(temp[temp.length - 1]);
            inputListModel.addElement( doubt < 0 ? "negative" : "positive");
        }

        JList<String> inputList = new JList<>(inputListModel);

        panel.add(new JScrollPane(inputList), BorderLayout.CENTER);

        return panel;
    }
}
