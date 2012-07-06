/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SAXPractice;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
/**
 *
 * @author Sweord
 */
public class XMLFun {
    public static void main(){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            
            DefaultHandler handler = new DefaultHandler();
            
        } catch (Exception ex) {
            Logger.getLogger(XMLFun.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
