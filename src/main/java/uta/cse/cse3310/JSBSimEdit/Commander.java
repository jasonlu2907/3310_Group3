package uta.cse.cse3310.JSBSimEdit;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
// import java.awt.event.ActionListener;

// import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
// import javax.swing.JList;
// import javax.swing.JOptionPane;
// import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import uta.cse.cse3310.JSBSimEdit.components.BuoyantForce;
import uta.cse.cse3310.JSBSimEdit.components.ExternalReaction;

public class Commander extends JFrame {
  private JLabel display;

  public Commander(String title) {
    super(title);
    this.setLocationByPlatform(true);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setSize(1100, 700);

    JToolBar toolbar = new JToolBar("JSBSim Edit Controls");

    JButton newFile = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/resources/new-document.png"));
    newFile.setActionCommand("New File");
    newFile.setToolTipText("Create new file");
    newFile.addActionListener(event -> onNewClick());

    JButton openFile = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/resources/folder.png"));
    openFile.setActionCommand("Open File");
    openFile.setToolTipText("Open an existing file");
    openFile.addActionListener(event -> onOpenClick());

    JButton saveFile = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/resources/diskette.png"));
    saveFile.setActionCommand("save File");
    saveFile.setToolTipText("save an existing file");
    saveFile.addActionListener(event -> onSaveClick());

    JButton quitB = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/resources/reject.png"));
    quitB.setActionCommand("Quit");
    quitB.setToolTipText("Exit Window");
    quitB.addActionListener(event -> onQuitClick());

    toolbar.add(newFile);
    toolbar.add(openFile);
    toolbar.add(saveFile);
    toolbar.add(quitB);
    Container pane = this.getContentPane();
    pane.add(toolbar, BorderLayout.NORTH);

    // D I S P L A Y ////////////////////////////////////
    display = new JLabel();
    display.setFont(new Font("SansSerif", Font.BOLD, 17));
    add(display, BorderLayout.CENTER);

    //  Create a tabbed pane for the navigation bar
    JTabbedPane tabbedPane = new JTabbedPane();
    ////////////////////////////////
    // EXTERNAL REACTIONS //////////////////////////////////
    ExternalReaction ext_reaction = new ExternalReaction();
    BuoyantForce buoyant_force = new BuoyantForce();

    tabbedPane.addTab("external_reactions", ext_reaction);
    tabbedPane.addTab("buoyant_forces", buoyant_force);

    // Add the Tabbed Pane to the Commander Window
    add(tabbedPane, BorderLayout.CENTER);

    setVisible(true);
  }



  protected void onNewClick() {
  }
  
  protected void onSaveClick() {
  }
  
  protected void onOpenClick() {}

  protected void onQuitClick() {
    dispose();
  }
}