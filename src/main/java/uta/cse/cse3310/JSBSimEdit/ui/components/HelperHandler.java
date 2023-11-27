package uta.cse.cse3310.JSBSimEdit.ui.components;


import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.JTextArea;
import org.xml.sax.Attributes;

/* READ XML FILE AND PRINT CHILD ELEMENTS */

public class HelperHandler extends DefaultHandler {
  private boolean foundRootElement = false;
  private JTextArea textArea;

  public HelperHandler(JTextArea textArea) {
    this.textArea = textArea;
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if (!foundRootElement) {
          // The first element will be the root element
          textArea.append("Root Element: " + qName + "\n");
          foundRootElement = true;
      } else {
          textArea.append("Child Element: " + qName+ "\n");
      }
  }

  // @Override
  // public void characters(char[] ch, int start, int length) throws SAXException {
  //     // Process element content if needed
  //     String content = new String(ch, start, length).trim();
  //     if (!content.isEmpty()) {
  //          textArea.append("Element content: " + content);
  //     }
  // }
}
