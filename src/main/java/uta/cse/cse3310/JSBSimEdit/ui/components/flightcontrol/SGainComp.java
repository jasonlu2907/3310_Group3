package uta.cse.cse3310.JSBSimEdit.ui.components.flightcontrol;

import generated.Clipto;
import generated.ScheduledGain;
import generated.Table;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Arrays;

public class SGainComp extends JLabel {
    private ScheduledGain sGain;
    private String description;
    private String input;
    private Table table;
    private String gain;
    private Clipto clipto;
    private Object output;
    private String name;
    private int offsetX, offsetY;

    public SGainComp(ScheduledGain sGain, String file) {
        super(new ImageIcon(file));

        this.sGain = sGain;
        this.description = sGain.getDescription();
        this.input = sGain.getInput();
        this.table = sGain.getTable();
        this.gain = sGain.getGain();
        this.clipto = sGain.getClipto();
        this.output = sGain.getOutput();
        this.name = sGain.getName();


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

    protected void showIconDetailsDialog() {
        JFrame detailsFrame = new JFrame("Gain Component");
        detailsFrame.setSize(400, 400);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Basic", createBasicPane());
//        tabbedPane.add("Input", createInputPane(sGain));

        detailsFrame.add(tabbedPane);

        detailsFrame.setLocationRelativeTo(this);
        detailsFrame.setVisible(true);
    }

//    private JPanel createInputPane(SGainComp sGain) {
//        JPanel panel = new JPanel();
//
//        String[] columnNames = {"IndependentVar", "Value"};
//        List<TableData> tableDataList = sGain.table.getTableData();
//        String[][] data = parseString(tableDataList.get(0).getValue());
//
//        for (String[] d : data) {
//            for (String a : d) {
//                System.out.println(a);
//            }
//        }
//
//        Object[][] rowData = new Object[data.length][];
//        for (int i = 0; i < data.length; i++) {
//            rowData[i] = data[i];
//        }
//
//        JTable table = new JTable(rowData, columnNames);
//        table.setPreferredScrollableViewportSize(new Dimension(300, 300));
//        table.setFillsViewportHeight(true);
//
//        JScrollPane scrollPane = new JScrollPane(table);
//
//        panel.add(scrollPane);
//
//        return panel;
//    }

    private String[][] parseString(String input) {
        String[] lines = input.split("\n");
        String[][] data = new String[lines.length][2];

        for (int i = 0; i < lines.length; i++) {
            String[] elements = lines[i].split("\\s+");
            // Filter out empty strings
            elements = Arrays.stream(elements)
                    .filter(e -> !e.isEmpty())
                    .toArray(String[]::new);
            data[i] = elements;
        }

        return data;
    }

    protected JPanel createBasicPane() {
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
        JLabel type_field = new JLabel("SCHEDULED_GAIN");
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

        JPanel gainPanel = new JPanel();
        gainPanel.setLayout(new FlowLayout());
        JLabel gainLabel = new JLabel("Independent Var: ");
        String gain_value = String.valueOf(this.gain == null ? 0 : this.gain);
        JTextField gain_field = new JTextField(gain_value == null ? "0" : gain_value, 10);
        gainPanel.add(gainLabel);
        gainPanel.add(gain_field);

        JButton save = new JButton("Save changes");
        save.setActionCommand("Save changes");
        save.setToolTipText("Save changes");
        save.addActionListener(event -> {
            this.name = name_field.getText();
            this.sGain.setName(name_field.getText());

            if(this.clipto == null) {
                this.clipto = new Clipto();
            }
            this.clipto.setMax(Double.parseDouble(max_field.getText()));
            this.clipto.setMin(Double.parseDouble(min_field.getText()));
            this.sGain.setClipto(this.clipto);

            this.gain = gain_field.getText();
            this.sGain.setGain(this.gain);

        });

        panel.add(infoPanel);
        panel.add(clipperPanel);
        panel.add(gainPanel);
        panel.add(save);

        return panel;
    }
}
