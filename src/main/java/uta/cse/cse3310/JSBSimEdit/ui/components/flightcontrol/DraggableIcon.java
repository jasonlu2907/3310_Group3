package uta.cse.cse3310.JSBSimEdit.ui.components.flightcontrol;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class DraggableIcon extends JLabel {
  private int offsetX, offsetY;
  private final String text;

  public DraggableIcon(String text, String file) {
    super(new ImageIcon(file));
    this.text = text;

    setOpaque(true);
//    setBackground(Color.WHITE);

    ClickListener clickListener = new ClickListener();
    this.addMouseListener(clickListener);

    DragListener dragListener = new DragListener();
    this.addMouseMotionListener(dragListener);

  }

  private class ClickListener extends MouseAdapter {
    public void mouseClicked(MouseEvent e) {
      if (e.getClickCount() == 2) {
          showIconDetailsDialog(DraggableIcon.this);
      }
    }

    public void mousePressed(MouseEvent e) {
        offsetX = e.getX();
        offsetY = e.getY();
    }
  }

  private class DragListener extends MouseMotionAdapter {
    public void mouseDragged(MouseEvent e) {
      int newX = getX() + e.getX() - offsetX;
      int newY = getY() + e.getY() - offsetY;
      setLocation(newX, newY);
    }
  }

  protected void showIconDetailsDialog(DraggableIcon icon) {
    JOptionPane.showMessageDialog(this, "Input/Output Name: " + icon.text, "Input/Output Details", JOptionPane.INFORMATION_MESSAGE);
  }

}
