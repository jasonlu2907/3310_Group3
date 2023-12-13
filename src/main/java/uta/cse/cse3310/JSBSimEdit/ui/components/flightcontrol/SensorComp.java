package uta.cse.cse3310.JSBSimEdit.ui.components.flightcontrol;

import generated.Clipto;
import generated.Noise;
import generated.Quantization;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class SensorComp extends JLabel {
    protected String description;
    protected String input;
    protected float lag;
    protected Noise noise;
    protected Quantization quantization;
    protected float driftRate;
    protected float bias;
    protected Clipto clipto;
    protected Object output;
    protected String name;
    protected int offsetX, offsetY;

    public SensorComp(String desc, String input, float lag, Noise noise, Quantization quantization,
                      float driftRate, float bias, Clipto clipto, Object output, String name, String file) {
        super(new ImageIcon(file));
        this.description = desc;
        this.input = input;
        this.lag = lag;
        this.noise = noise;
        this.driftRate = driftRate;
        this.bias = bias;
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
                showIconDetailsDialog(SensorComp.this);
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

    protected void showIconDetailsDialog(SensorComp sensor) {
        JFrame detailsFrame = new JFrame("Sensor Component");
        detailsFrame.setSize(400, 500);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Basic", createBasicPane(sensor));

        detailsFrame.add(tabbedPane);

        detailsFrame.setLocationRelativeTo(this);
        detailsFrame.setVisible(true);
    }

    protected JPanel createBasicPane(SensorComp sensor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        /*Components inside the Basic Pane*/
        // Information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout());
        JLabel name = new JLabel("Name: ");
        JTextField name_field = new JTextField(sensor.name, 15);
        namePanel.add(name);
        namePanel.add(name_field);

        JPanel typePanel = new JPanel();
        typePanel.setLayout(new FlowLayout());
        JLabel type = new JLabel("Type: ");
        JLabel type_field = new JLabel("Sensor");
        typePanel.add(type);
        typePanel.add(type_field);

        infoPanel.add(namePanel);
        infoPanel.add(typePanel);

        // Lag and Delay
        JPanel lagPanel = new JPanel();
        lagPanel.setLayout(new FlowLayout());
        JLabel lagLabel = new JLabel("Lag: ");
        String lag_value = String.valueOf(sensor.lag);
        JTextField lag_field = new JTextField(lag_value, 10);
        lagPanel.add(lagLabel);
        lagPanel.add(lag_field);

        JPanel noisePanel = new JPanel();
        noisePanel.setBorder(new TitledBorder("Noise:"));
        noisePanel.setLayout(new FlowLayout());
        JLabel noiseVariationLabel = new JLabel("Variation:");
        String noiseVariation_value = String.valueOf(sensor.noise.getVariation());
        JTextField noiseVariation_field = new JTextField(noiseVariation_value, 10);

        JLabel noiseLabel = new JLabel("Value:");
        String noise_value = String.valueOf(sensor.noise.getValue());
        JTextField noise_field = new JTextField(noise_value, 10);

        noisePanel.add(noiseVariationLabel);
        noisePanel.add(noiseVariation_field);
        noisePanel.add(noiseLabel);
        noisePanel.add(noise_field);

        // Clipper
        JPanel clipperPanel = minMaxPanel(sensor.clipto);

        // Quantization
        JPanel quantizationPanel = minMaxPanel(sensor.quantization);

        // Bias
        JPanel biasPanel = new JPanel();
        biasPanel.setLayout(new FlowLayout());
        JLabel biasLabel = new JLabel("Lag: ");
        String bias_value = String.valueOf(sensor.bias);
        JTextField bias_field = new JTextField(bias_value, 10);
        biasPanel.add(biasLabel);
        biasPanel.add(bias_field);

        panel.add(infoPanel);
        panel.add(lagPanel);
        panel.add(clipperPanel);
        panel.add(noisePanel);
        panel.add(quantizationPanel);
        panel.add(biasPanel);

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

    private JPanel minMaxPanel(Quantization myQuan) {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Quantization:"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel bitPanel = new JPanel();
        bitPanel.setLayout(new FlowLayout());
        JLabel bit = new JLabel("Number of Bits: ");
        String bit_value = (myQuan != null ? String.valueOf(myQuan.getBits()) : "0");
        JTextField bit_field = new JTextField(bit_value, 15);
        bitPanel.add(bit);
        bitPanel.add(bit_field);

        JPanel minPanel = new JPanel();
        minPanel.setLayout(new FlowLayout());
        JLabel min = new JLabel("Min: ");
        String min_value = (myQuan != null ? String.valueOf(myQuan.getMin()) : "0");
        JTextField min_field = new JTextField(min_value, 15);
        minPanel.add(min);
        minPanel.add(min_field);

        JPanel maxPanel = new JPanel();
        maxPanel.setLayout(new FlowLayout());
        JLabel max = new JLabel("Max: ");
        String max_value = (myQuan != null ? String.valueOf(myQuan.getMax()) : "0");
        JTextField max_field = new JTextField(max_value, 15);
        maxPanel.add(max);
        maxPanel.add(max_field);

        panel.add(bitPanel);
        panel.add(minPanel);
        panel.add(maxPanel);

        return panel;
    }
}
