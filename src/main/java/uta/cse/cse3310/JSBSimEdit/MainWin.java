package uta.cse.cse3310.JSBSimEdit;

import javax.xml.parsers.SAXParserFactory;

import generated.FdmConfig;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import uta.cse.cse3310.JSBSimEdit.Helper.HelperHandler;

import javax.xml.parsers.SAXParser;

import javax.swing.JFrame; // for main window
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JMenuBar; // row of menu selections
import javax.swing.JMenu; // menu selection that offers another menu
import javax.swing.JMenuItem; // menu selection that does something
import javax.swing.ImageIcon;
import javax.swing.JButton; // regular button

import javax.swing.JLabel; // text or image holder

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// import java.io.BufferedReader;
// import java.io.BufferedWriter;
import java.io.File; // opens a file
// import java.io.FileReader;
// import java.io.FileWriter;

import javax.swing.filechooser.FileFilter;
import java.awt.BorderLayout; // layout manager for main window
import java.awt.Container;
import java.awt.Font; // rich text in a JLabel or similar widget

public class MainWin extends JFrame {
    private JLabel display;
    private File filename;
    private JTextArea resultTextArea;
    
    public MainWin(String title) {
        super(title);
        this.setLocationByPlatform(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1190, 720);

        filename = new File("untitled.xml");
        
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem quit = new JMenuItem("Quit");
        
        /* FILE Menu */
        /* Handling input file and deliver output file */
        open.addActionListener(event -> onOpenClick());
        quit.addActionListener(event -> onQuitClick());

        file.add(open);
        file.add(quit);

        menubar.add(file);

        setJMenuBar(menubar);

        // ///////////// //////////////////////////////////////////////////////////
        // T O O L B A R
        // Add a toolbar to the PAGE_START region below the menu
        JToolBar toolbar = new JToolBar("JSBSim Edit Controls");
        JButton openFile = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/resources/airplane.png"));
        openFile.setActionCommand("Open File");
        openFile.setToolTipText("Open an existing file");
        openFile.addActionListener(event -> onOpenClick());

        JButton quitB = new JButton(new ImageIcon("src/main/java/uta/cse/cse3310/JSBSimEdit/resources/reject.png"));
        quitB.setActionCommand("Quit");
        quitB.setToolTipText("Exit Window");
        quitB.addActionListener(event -> onQuitClick());

        toolbar.add(openFile);
        toolbar.add(quitB);

        Container pane = this.getContentPane();
        pane.add(toolbar, BorderLayout.NORTH);

        // D I S P L A Y ////////////////////////////////////
        display = new JLabel();
        display.setFont(new Font("SansSerif", Font.BOLD, 17));
        add(display, BorderLayout.CENTER);

        //FIXME: Remove when possible 
        // R E S U L T
        // resultTextArea = new JTextArea();
        // resultTextArea.setEditable(false);

        // JPanel btnPanel = new JPanel();
        // btnPanel.add(openFile);
        // btnPanel.add(quitB);

        // JScrollPane scrollPane = new JScrollPane(resultTextArea);
        // getContentPane().setLayout(new BorderLayout());
        // getContentPane().add(btnPanel, BorderLayout.LINE_START);
        // getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Make everything in the JFrame visible
        setVisible(true);
    }

    private void parseXMLFile() {
        try {
            JAXBContext jc = JAXBContext.newInstance("generated");
    
            Unmarshaller um = jc.createUnmarshaller();
            // Unmarshal (read) an XML document into a Java object
            FdmConfig cfg = (FdmConfig) um.unmarshal(filename);
            // System.out.println(cfg.getName());
            
            // System.out.println(cfg);
            // System.out.println(cfg.getFileheader().getCopyright());
            // System.out.println(cfg.getFileheader().getVersion() );
            // System.out.println(cfg.getAerodynamics().getAxis().get(0).getName());
            // System.out.println(cfg.getAerodynamics().getAxis().get(0).getFunction());
            // System.out.println(cfg.getAerodynamics().getAxis().get(0).getClass());


            Marshaller m = jc.createMarshaller();
            m.setProperty("jaxb.formatted.output", true);
            m.marshal(cfg, new File("output.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        // try {
        //     resultTextArea.setText("");
        //     SAXParserFactory factory = SAXParserFactory.newInstance();
        //     SAXParser saxParser = factory.newSAXParser();
        //     HelperHandler handler = new HelperHandler(resultTextArea);
        //     saxParser.parse(filename, handler);
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     resultTextArea.setText("Something is wrong: " + e.getMessage());
        // }
    }

    protected void onOpenClick() {
        final JFileChooser fc = new JFileChooser(filename); // Create a file chooser dialog
        FileFilter xmlFiles = new FileNameExtensionFilter("xml files", "xml");
        fc.addChoosableFileFilter(xmlFiles); // Add "xml file" filter
        fc.setFileFilter(xmlFiles); // Show xml files only by default

        int result = fc.showOpenDialog(this); // Run dialog, return button clicked
        if (result == JFileChooser.APPROVE_OPTION) { // Also CANCEL_OPTION and ERROR_OPTION
            filename = fc.getSelectedFile(); // Obtain the selected File object

            parseXMLFile();

            new Commander("JSBSim Commander");
        }
    }

    protected void onQuitClick() {
        System.exit(0);
    }


}