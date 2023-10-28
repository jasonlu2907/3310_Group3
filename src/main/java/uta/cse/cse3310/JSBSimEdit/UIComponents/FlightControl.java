package uta.cse.cse3310.JSBSimEdit.UIComponents;

import java.awt.BorderLayout;
import java.awt.Color;
// import java.awt.Container;
import java.awt.FlowLayout;
// import java.awt.FlowLayout;
import java.awt.Graphics;
// import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

// import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
// import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
// import javax.swing.border.Border;
// import javax.swing.border.TitledBorder;

import uta.cse.cse3310.JSBSimEdit.Helper.DraggableIcon;

public class FlightControl extends JPanel {
  private ArrayList<DraggableIcon> icons = new ArrayList<>();
  private ArrayList<Line> lines = new ArrayList<>();
  private Point lineStart, lineEnd;
  private Line currentLine;
  JPanel drawPanel = new JPanel(new FlowLayout());


  public FlightControl() {
    setLayout(null);
    setLayout(new BorderLayout());
    
    //////////////////TOOLBAR/////////////////////////////////
    JToolBar toolbar = new JToolBar("Toolbar");

    JButton addComponent = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/resources/new_component.png"));
    addComponent.setActionCommand("Add new component");
    addComponent.setToolTipText("Add new component");
    addComponent.addActionListener(e -> addNewComponent());

    JButton deleteLine = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/resources/delete.png"));
    deleteLine.setActionCommand("Delete Line");
    deleteLine.setToolTipText("Delete file");
    // newFile.addActionListener(event -> onNewClick());

    toolbar.add(addComponent);
    toolbar.add(deleteLine);
    add(toolbar, BorderLayout.NORTH);

    ////////////////DRAGGING PAD///////////////////////////////////////
    // Create and add icons to the frame

    DraggableIcon icon1 = new DraggableIcon("Component 1");
    icon1.setBounds(100, 100, 100, 50);
    add(icon1);
    icons.add(icon1);

    DraggableIcon icon2 = new DraggableIcon("Component 2");
    icon2.setBounds(300, 200, 100, 50);
    add(icon2);
    icons.add(icon2);

    DraggableIcon icon3 = new DraggableIcon("Component 3");
    icon3.setBounds(500, 100, 100, 50);
    add(icon3);
    icons.add(icon3);

    DraggableIcon icon4 = new DraggableIcon("Component 4");
    icon4.setBounds(200, 250, 100, 50);
    add(icon4);
    icons.add(icon4);
    // DO NOT add icons to the drawpanel
    // Add drawpanel to the main panel
    add(drawPanel, BorderLayout.CENTER);

    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
          lineStart = e.getPoint();
          currentLine = new Line(lineStart);
          lines.add(currentLine);
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        lineEnd = e.getPoint();
        // Generate a name based on the index
        String lineName = "(" + (lines.size() - 1) + ")";

        currentLine.setEndPoint(lineEnd);
        currentLine.setName(lineName);
        repaint();
      }
    });



    setVisible(true);
  }

  // protected void paintComponent(Graphics g) {
  //   super.paintComponent(g);

  //   if (lineStart != null && lineEnd != null) {
  //       Graphics2D g2d = (Graphics2D) g;
  //       g2d.setColor(Color.BLACK);
  //       g2d.drawLine(lineStart.x, lineStart.y, lineEnd.x, lineEnd.y);
  //   }
  // }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    for (Line Line : lines) {
      Line.draw(g);
    }
  }

  class Line {
    private Point startPoint;
    private Point endPoint;
    private String name;

    public Line(Point startPoint) {
      this.startPoint = startPoint;
      // this.endPoint = startPoint;
    }

    public void setEndPoint(Point endPoint) {
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

  /////////////// BUTTON EVENTS
  protected void addNewComponent() {
    DraggableIcon newIcon = new DraggableIcon("New Component");
    newIcon.setBounds(150, 50, 100, 50);
    // Add the new icon to the list and drawPanel
    add(newIcon);
    icons.add(newIcon);
    // Repaint the panel to display the new icon
    repaint();
  }

  
}

