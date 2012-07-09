/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package folderautobackuputility;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * A file object holding information about folders. Also has Serialization
 * methods.
 *
 * @author Sweord
 */
public class FolderInformationFile extends File {
    
    boolean modified = false;
    final static long serialVersionUID = 564546564l;
    //All the folders held in the file
    ArrayList<VernaFolder> folders = new ArrayList<VernaFolder>();

    //Dump the folders
    public ArrayList<VernaFolder> getFolders() {
        return folders;
    }

    //Add a folder to the file
    public boolean addFolder(VernaFolder e) {
        boolean success = folders.add(e);
        writeToFile();
        return success;
    }

    public void removeFolder(VernaFolder folderToRemove){
        folders.remove(folderToRemove);
    }
    //Read the folders from the file
    public void readFromFile() {
        FileInputStream FileIS = null;
        try {

            FileIS = new FileInputStream(this);
            ObjectInputStream rawr = new ObjectInputStream(FileIS);
            
            Object ReadObject = rawr.readObject();
            if(ReadObject.getClass() == ArrayList.class) {
                    folders = new ArrayList<VernaFolder>();
                    for(Object anObject : (ArrayList)ReadObject){
                        if(anObject.getClass() == VernaFolder.class) folders.add((VernaFolder) anObject);
                    }
            }
            else throw new Exception("Class mismatch when loading configuration file.");
                    

        } catch (Exception ex) {
            if(ex.getMessage().contains("local class incompatible")){
                JOptionPane.showMessageDialog(null, "Not a compatable File");
                
            }
            Logger.getLogger(FolderInformationFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                FileIS.close();
            } catch (IOException ex) {
                Logger.getLogger(FolderInformationFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Write the folders to the file
    public void writeToFile() {
        writeToFile(this);
    }
    
    public void writeToFile(File target){
        FileOutputStream fout = null;
        try {
            System.out.println("Saving : " + target.getName());
if(!target.exists()) target.createNewFile();
            fout = new FileOutputStream(target);
            
            ObjectOutputStream rawr = new ObjectOutputStream(fout);
            rawr.writeObject(folders);

        } catch (Exception ex) {
            Logger.getLogger(FolderInformationFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(FolderInformationFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public FolderInformationFile(URI uri) {
        super(uri);
    }

    public FolderInformationFile(File parent, String child) {
        super(parent, child);
    }

    public FolderInformationFile(String parent, String child) {
        super(parent, child);
    }

    public FolderInformationFile(String pathname) {
        super(pathname);
    }// </editor-fold>
}
