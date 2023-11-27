package uta.cse.cse3310.JSBSimEdit.ui.components;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;

public class GeneralInformation extends JPanel{
    public GeneralInformation(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel firstRowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel acName = new JLabel("Aircraft Name:");
        JTextField acNameField = new JTextField("",10);
        JLabel fileName = new JLabel("File Name:");
        JTextField fileNameField = new JTextField("",10);
        firstRowPanel.add(acName);
        firstRowPanel.add(acNameField);
        firstRowPanel.add(fileName);
        firstRowPanel.add(fileNameField);

        JLabel author = new JLabel("Author:");
        JTextField authField = new JTextField("",10);
        JLabel email = new JLabel("Email:");
        JTextField emailfField = new JTextField("",10);
        JLabel fdate = new JLabel("File Date:");
        JTextField fdateField = new JTextField("",10);
        firstRowPanel.add(fdate);
        firstRowPanel.add(fdateField);
        firstRowPanel.add(author);
        firstRowPanel.add(authField);
        firstRowPanel.add(email);
        firstRowPanel.add(emailfField);
        
        add(firstRowPanel);

        JPanel secondRowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel release = new JLabel("Release Level:");
        JTextField releasField = new JTextField("",10);
        JLabel configVer = new JLabel("Configuration Version:");
        JTextField configVerField = new JTextField("",10);
        JLabel fmVer = new JLabel("Flight Model Version:");
        JTextField fmVerField = new JTextField("",10);
        secondRowPanel.add(release);
        secondRowPanel.add(releasField);
        secondRowPanel.add(configVer);
        secondRowPanel.add(configVerField);
        secondRowPanel.add(fmVer);
        secondRowPanel.add(fmVerField);
        
        add(secondRowPanel);

    
        JPanel fourthRowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel org = new JLabel("Organization:");
        JTextArea organization = new JTextArea(5,20);
        organization.setEditable(true); 
        JScrollPane scrollOrg = new JScrollPane(organization);
        scrollOrg.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollOrg.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        fourthRowPanel.add(org);
        fourthRowPanel.add(scrollOrg);
        JLabel desc = new JLabel("Description:");
        JTextArea descArea = new JTextArea(5,20);
        descArea.setEditable(true); 
        JScrollPane scrollDesc = new JScrollPane(descArea);
        scrollDesc.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollDesc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        fourthRowPanel.add(desc);
        fourthRowPanel.add(scrollDesc);
        add(fourthRowPanel);

        String[][] data = {};
        String[] columnNames = { "Ref ID", "Author", "Title", "Date" };   
        JTable ref = new JTable(data, columnNames);
        ref.setSize(5, 5);
        JScrollPane scrollRef = new JScrollPane(ref);
        scrollRef.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollRef.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollRef);

        JLabel limits = new JLabel("Limitations:");
        JTextArea limAarea = new JTextArea(5,20);
        limAarea.setEditable(true); 
        JScrollPane scrollLim = new JScrollPane(limAarea);
        scrollLim.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(limits);
        add(scrollLim);

        JLabel notes = new JLabel("Notes:");
        JTextArea notesArea = new JTextArea(5,20);
        notesArea.setEditable(true); 
        JScrollPane scrollNotes = new JScrollPane(notesArea);
        scrollNotes.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(notes);
        add(scrollNotes);

    }
}
