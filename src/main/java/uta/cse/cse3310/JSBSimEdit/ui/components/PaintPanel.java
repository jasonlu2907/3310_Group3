package uta.cse.cse3310.JSBSimEdit.ui.components;


import generated.*;
import uta.cse.cse3310.JSBSimEdit.ui.components.flightcontrol.*;

import javax.swing.*;
import java.awt.*;
import java.lang.Integer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PaintPanel extends JPanel {
    private final List<Object> children;
    private Actuator actuator;
    private AerosurfaceScale aeroSurface; // gain comp
    private Integrator integrator;
    private LagFilter lagFilter; // filter
    private LeadLagFilter leadLagFilter;
    private PureGain pureGain; // gain
    private ScheduledGain scheduledGain; // gain
    private SecondOrderFilter secondOrderFilter;
    private Sensor sensor; // sensor
    private WashoutFilter washoutFilter;
    private Deadband deadband; // autopilot only
    private FcsFunction fcsFunction; // fcs

    /*Drawing Lines*/
    private HashMap<Integer, Line> lines = new HashMap<>();
    private Point lineStart, lineEnd;
    private int x_startPosition = 120, y_startPosition = 40, maxY = 70;

    PaintPanel(List<Object> children) {
        super();
        this.children = children;
        setLayout(null);

        generatingComponents();

        setVisible(true);
    }

    private void generatingComponents() {
        StringBuilder imageFile = new StringBuilder("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/");
        String curObject;

        for (Object o : this.children) {
            curObject = String.valueOf(o.getClass()).toLowerCase();

            if (x_startPosition > 1140) {
                y_startPosition += Math.max(y_startPosition, maxY);
                maxY = 70;
                x_startPosition = 120;
            }
            revealComponent(imageFile, curObject, o, x_startPosition, y_startPosition);
            x_startPosition += 310;

            imageFile = new StringBuilder("src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/");
        }

    }

    private void revealComponent(StringBuilder imageFile, String name, Object o, int x_startPosition, int y_startPosition) {
        String returnStr;

        if (name.indexOf("kinematic") > 0) {
            returnStr = imageFile.append("kinemat.png").toString();
            Kinematic tempKinematic = (Kinematic) o;
            // initialize the component and add to the lists
            initKinematic(tempKinematic, returnStr, x_startPosition, y_startPosition);

        } else if (name.indexOf("gain") > 0) {
            returnStr = imageFile.append("gain.png").toString();
            if (Objects.equals(name.split("generated\\.")[1], "puregain")) {
                PureGain tempGain = (PureGain) o;
                initGain(tempGain, returnStr);
            } else {
                ScheduledGain tempGain = (ScheduledGain) o;
                initGain(tempGain, returnStr);
            }
        } else if (name.indexOf("filter") > 0) {
            returnStr = imageFile.append("filter.png").toString();
            LagFilter tempFilter = (LagFilter) o;
            initFilter(tempFilter, returnStr);

        } else if (name.indexOf("summer") > 0) {
            returnStr = imageFile.append("summer.png").toString();
            Summer tempSummer = (Summer) o;
            initSummer(tempSummer, returnStr);

        } else if (name.indexOf("switch") > 0) {
            returnStr = imageFile.append("switch.png").toString();
            Switch tempSwitch = (Switch) o;
            initSwitch(tempSwitch, returnStr, x_startPosition, y_startPosition);

        } else if (name.indexOf("deadband") > 0) {
            returnStr = imageFile.append("deadband.png").toString();
            Deadband tempDead = (Deadband) o;
            initDeadBand(tempDead, returnStr, x_startPosition, y_startPosition);

        } else if (name.indexOf("fcsfunction") > 0) {
            returnStr = imageFile.append("FCSfunction.png").toString();
            FcsFunction tempFCS = (FcsFunction) o;
            initFCS(tempFCS, returnStr, x_startPosition, y_startPosition);

        } else if (name.indexOf("pid") > 0) {
            returnStr = imageFile.append("pid.png").toString();
            Pid tempPid = (Pid) o;
            initPid(tempPid, returnStr, x_startPosition, y_startPosition);
        } else if (name.indexOf("aerosurface") > 0) {
            returnStr = imageFile.append("gain.png").toString();
            AerosurfaceScale tempAero = (AerosurfaceScale) o;
            initAerosurfaceScale(tempAero, returnStr);
        } else if (name.indexOf("sensor") > 0) {
            returnStr = imageFile.append("sensor.png").toString();
            Sensor tempSensor = (Sensor) o;
            initSensor(tempSensor, returnStr);
        }

    }

    private void initSensor(Sensor tempSensor, String returnStr) {
        SensorComp sensor = new SensorComp(tempSensor.getDescription(), tempSensor.getInput(), tempSensor.getLag(), tempSensor.getNoise(),
                tempSensor.getQuantization(), tempSensor.getDriftRate(), tempSensor.getBias(), tempSensor.getClipto(), tempSensor.getOutput(), tempSensor.getName(), returnStr);

        if (tempSensor.getInput() != null) {
            DraggableIcon inputIcon = new DraggableIcon(tempSensor.getInput(), "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/input.png");
            inputIcon.setBounds(x_startPosition - 100, y_startPosition, 50, 50);
            add(inputIcon);

            // lineStart at right-center of the component => x + 50, y + 25
            lineStart = new Point(x_startPosition - 100 + 50, y_startPosition + 25);
        }

        // set bound for the current component
        sensor.setBounds(x_startPosition, y_startPosition, 50, 50);
        // add to the panel
        add(sensor);

        // lineEnd at left-center of the component => y + 25
        lineEnd = new Point(x_startPosition, y_startPosition + 25);
        addNewLine(lineStart, lineEnd);

        if (tempSensor.getOutput() != null) {
            lineStart = new Point(lineEnd.x + 50, lineEnd.y);
            DraggableIcon outputIcon = new DraggableIcon(tempSensor.getOutput().toString(), "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/destination.png");
            outputIcon.setBounds(x_startPosition + 100, y_startPosition, 50, 50);
            add(outputIcon);

            lineEnd = new Point(x_startPosition + 100, y_startPosition + 25);
            addNewLine(lineStart, lineEnd);
        }
    }

    private void initAerosurfaceScale(AerosurfaceScale tempAero, String returnStr) {
        AerosurfaceComp aeroSurface = new AerosurfaceComp(tempAero, returnStr);

        if (tempAero.getInput() != null) {
            DraggableIcon inputIcon = new DraggableIcon(tempAero.getInput(), "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/input.png");
            inputIcon.setBounds(x_startPosition - 100, y_startPosition, 50, 50);
            add(inputIcon);

            // lineStart at right-center of the component => x + 50, y + 25
            lineStart = new Point(x_startPosition - 100 + 50, y_startPosition + 25);
        }

        // set bound for the current component
        aeroSurface.setBounds(x_startPosition, y_startPosition, 50, 50);
        // add to the panel
        add(aeroSurface);

        // lineEnd at left-center of the component => y + 25
        lineEnd = new Point(x_startPosition, y_startPosition + 25);
        addNewLine(lineStart, lineEnd);

        if (tempAero.getOutput() != null) {
            lineStart = new Point(lineEnd.x + 50, lineEnd.y);
            DraggableIcon outputIcon = new DraggableIcon(tempAero.getOutput(), "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/destination.png");
            outputIcon.setBounds(x_startPosition + 100, y_startPosition, 50, 50);
            add(outputIcon);

            lineEnd = new Point(x_startPosition + 100, y_startPosition + 25);
            addNewLine(lineStart, lineEnd);
        }
    }

    private void initFilter(LagFilter tempFilter, String returnStr) {
        LagFilterComp lagFilter = new LagFilterComp(tempFilter, returnStr);

        if (tempFilter.getInput() != null) {
            DraggableIcon inputIcon = new DraggableIcon(tempFilter.getInput(), "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/input.png");
            inputIcon.setBounds(x_startPosition - 100, y_startPosition, 50, 50);
            add(inputIcon);

            // lineStart at right-center of the component => x + 50, y + 25
            lineStart = new Point(x_startPosition - 100 + 50, y_startPosition + 25);
        }

        // set bound for the current component
        lagFilter.setBounds(x_startPosition, y_startPosition, 50, 50);
        // add to the panel
        add(lagFilter);

        // lineEnd at left-center of the component => y + 25
        lineEnd = new Point(x_startPosition, y_startPosition + 25);
        addNewLine(lineStart, lineEnd);

        if (tempFilter.getOutput() != null) {
            lineStart = new Point(lineEnd.x + 50, lineEnd.y);
            DraggableIcon outputIcon = new DraggableIcon(tempFilter.getOutput(), "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/destination.png");
            outputIcon.setBounds(x_startPosition + 100, y_startPosition, 50, 50);
            add(outputIcon);

            lineEnd = new Point(x_startPosition + 100, y_startPosition + 25);
            addNewLine(lineStart, lineEnd);
        }
    }

    private void initDeadBand(Deadband tempDead, String returnStr, int x_startPosition, int y_startPosition) {
        DeadBandComp deadB = new DeadBandComp(tempDead, returnStr);

        if (tempDead.getInput() != null) {
            DraggableIcon inputIcon = new DraggableIcon(tempDead.getInput(), "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/input.png");
            inputIcon.setBounds(x_startPosition - 100, y_startPosition, 50, 50);
            add(inputIcon);

            // lineStart at right-center of the component => x + 50, y + 25
            lineStart = new Point(x_startPosition - 100 + 50, y_startPosition + 25);
        }

        // set bound for the current component
        deadB.setBounds(x_startPosition, y_startPosition, 50, 50);
        // add to the panel
        add(deadB);

        // lineEnd at left-center of the component => y + 25
        lineEnd = new Point(x_startPosition, y_startPosition + 25);
        addNewLine(lineStart, lineEnd);
    }

    private void initGain(PureGain tempGain, String returnStr) {
        GainComp pure_gain = new GainComp(tempGain, returnStr);

        if (tempGain.getInput() != null) {
            DraggableIcon inputIcon = new DraggableIcon(tempGain.getInput(), "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/input.png");
            inputIcon.setBounds(x_startPosition - 100, y_startPosition, 50, 50);
            add(inputIcon);

            // lineStart at right-center of the component => x + 50, y + 25
            lineStart = new Point(x_startPosition - 100 + 50, y_startPosition + 25);
        }

        // set bound for the current component
        pure_gain.setBounds(x_startPosition, y_startPosition, 50, 50);
        // add to the panel
        add(pure_gain);

        // lineEnd at left-center of the component => y + 25
        lineEnd = new Point(x_startPosition, y_startPosition + 25);
        addNewLine(lineStart, lineEnd);

        if (tempGain.getOutput() != null) {
            lineStart = new Point(lineEnd.x + 50, lineEnd.y);
            DraggableIcon outputIcon = new DraggableIcon(tempGain.getOutput(), "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/destination.png");
            outputIcon.setBounds(x_startPosition + 100, y_startPosition, 50, 50);
            add(outputIcon);

            lineEnd = new Point(x_startPosition + 100, y_startPosition + 25);
            addNewLine(lineStart, lineEnd);
        }
    }

    private void initGain(ScheduledGain tempGain, String returnStr) {
        SGainComp scheduled_gain = new SGainComp(tempGain, returnStr);

        if (tempGain.getInput() != null) {
            DraggableIcon inputIcon = new DraggableIcon(tempGain.getInput(), "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/input.png");
            inputIcon.setBounds(x_startPosition - 100, y_startPosition, 50, 50);
            add(inputIcon);

            // lineStart at right-center of the component => x + 50, y + 25
            lineStart = new Point(x_startPosition - 100 + 50, y_startPosition + 25);
        }

        // set bound for the current component
        scheduled_gain.setBounds(x_startPosition, y_startPosition, 50, 50);
        // add to the panel
        add(scheduled_gain);

        // lineEnd at left-center of the component => y + 25
        lineEnd = new Point(x_startPosition, y_startPosition + 25);
        addNewLine(lineStart, lineEnd);

    }

    private void initFCS(FcsFunction tempFCS, String returnStr, int x_startPosition, int y_startPosition) {
        FCSFunctionComp fcs = new FCSFunctionComp(tempFCS.getInput(), tempFCS.getDescription(), tempFCS.getFunction(), tempFCS.getClipto(), tempFCS.getOutput(), tempFCS.getName(), returnStr);
        fcs.setBounds(x_startPosition, y_startPosition, 50, 50);

        add(fcs);
    }

    private void initPid(Pid tempPid, String returnStr, int x_startPosition, int y_startPosition) {
        PidComp pid = new PidComp(tempPid, returnStr);
        pid.setBounds(x_startPosition, y_startPosition, 50, 50);

        add(pid);
    }

    private void initSwitch(Switch tempSwitch, String returnStr, int x_startPosition, int y_startPosition) {
        SwitchComponent aSwitch = new SwitchComponent(tempSwitch, returnStr);
        aSwitch.setBounds(x_startPosition, y_startPosition, 50, 50);

        add(aSwitch);
    }

    private void initSummer(Summer tempSummer, String returnStr) {
        SummerComp summer = new SummerComp(tempSummer, returnStr);
        // lineEnd at left-center of the component => y + 25
        lineEnd = new Point(x_startPosition, y_startPosition + 25);

        // set bound for the current component
        summer.setBounds(x_startPosition, y_startPosition, 50, 50);
        // add to the panel
        add(summer);

        // if there are input(s)
        List<String> inputs = tempSummer.getInput();
        if (inputs != null) {
            int y_position = y_startPosition;
            for (String input : inputs) {
                DraggableIcon inputIcon = new DraggableIcon(input, "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/input.png");
                inputIcon.setBounds(x_startPosition - 100, y_position, 50, 50);
                add(inputIcon);

                // linestart at right-center of the component => x + 50, y + 25
                lineStart = new Point(x_startPosition - 100 + 50, y_position + 25);
                addNewLine(lineStart, lineEnd);

                y_position += 50;
            }

            maxY = y_position;
            if (x_startPosition >= 900 && x_startPosition <= 1140) {
                y_startPosition += 50 * inputs.size();
                x_startPosition = 120;
            }
        }
    }

    private void initKinematic(Kinematic tempKinematic, String returnStr, int x_startPosition, int y_startPosition) {
        KinematicComp kine = new KinematicComp(tempKinematic, returnStr);

        if (tempKinematic.getInput() != null) {
            DraggableIcon inputIcon = new DraggableIcon(tempKinematic.getInput(), "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/input.png");
            inputIcon.setBounds(x_startPosition - 100, y_startPosition, 50, 50);
            add(inputIcon);

            // lineStart at right-center of the component => x + 50, y + 25
            lineStart = new Point(x_startPosition - 100 + 50, y_startPosition + 25);
        }

        // set bound for the current component
        kine.setBounds(x_startPosition, y_startPosition, 50, 50);
        // add to the panel
        add(kine);

        // lineEnd at left-center of the component => y + 25
        lineEnd = new Point(x_startPosition, y_startPosition + 25);
        addNewLine(lineStart, lineEnd);

        if (tempKinematic.getOutput() != null) {
            lineStart = new Point(lineEnd.x + 50, lineEnd.y);
            DraggableIcon outputIcon = new DraggableIcon(tempKinematic.getOutput(), "src/main/java/uta/cse/cse3310/JSBSimEdit/ui/resources/destination.png");
            outputIcon.setBounds(x_startPosition + 100, y_startPosition, 50, 50);
            add(outputIcon);

            lineEnd = new Point(x_startPosition + 100, y_startPosition + 25);
            addNewLine(lineStart, lineEnd);
        }

    }

    private void addNewLine(Point start, Point end) {
        Line currentLine = new Line(start, end);
        currentLine.setName("(" + lines.size() + ")");
        lines.put(lines.size(), currentLine);
        // when a new line is added, repaint the Panel
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
//        for (Line line : lines) {
//            line.draw(g);
//        }

        for(Map.Entry<Integer, Line> entry : lines.entrySet()) {
            int key = entry.getKey();
            Line line = entry.getValue();
            line.draw(g);
        }
    }

    static class Line {
        private Point startPoint;
        private Point endPoint;
        private String name;

        public Line(Point startPoint, Point endPoint) {
            this.startPoint = startPoint;
            this.endPoint = endPoint;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void draw(Graphics g) {
            g.setColor(Color.BLACK);
            g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);

            // Draw an arrowhead at the end of the line
            int arrowSize = 10; // You can adjust the size as needed
            double angle = Math.atan2(endPoint.y - startPoint.y, endPoint.x - startPoint.x);
            int x1 = endPoint.x - (int) (arrowSize * Math.cos(angle - Math.PI / 6));
            int y1 = endPoint.y - (int) (arrowSize * Math.sin(angle - Math.PI / 6));
            int x2 = endPoint.x - (int) (arrowSize * Math.cos(angle + Math.PI / 6));
            int y2 = endPoint.y - (int) (arrowSize * Math.sin(angle + Math.PI / 6));

            Polygon arrowHead = new Polygon();
            arrowHead.addPoint(endPoint.x, endPoint.y);
            arrowHead.addPoint(x1, y1);
            arrowHead.addPoint(x2, y2);

            g.fillPolygon(arrowHead);

            // Draw the name of the line
            int nameX = (startPoint.x + endPoint.x) / 2;
            int nameY = (startPoint.y + endPoint.y) / 2;
            g.drawString(name, nameX, nameY);
        }
    }

    public HashMap<Integer, Line> getLines() {
        return this.lines;
    }

    public void setLines(HashMap<Integer, Line> lines) {
        this.lines = lines;
    }

}
