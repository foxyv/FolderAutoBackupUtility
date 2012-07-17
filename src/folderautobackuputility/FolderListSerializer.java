/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package folderautobackuputility;

import java.awt.Frame;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * A file object holding a list of folders with serialization support.
 *
 * @author Sweord
 */
public class FolderListSerializer {

    boolean modified = false;
    final static long serialVersionUID = 564546564l;
    ArrayList<VernaFolder> folders = new ArrayList<VernaFolder>();
    public static final int SAVE_SUCCESS = 1;
    public static final int SAVE_CANCELLED = 2;

    /**
     * Gets a copy of the folders from the array.
     *
     * @return
     */
    public ArrayList<VernaFolder> getFolderArrayCopy() {
        return new ArrayList<VernaFolder>(folders);
    }

    public SortedListModel getFolderListModel(){
        SortedListModel newModel = new SortedListModel();
        for(VernaFolder aFolder : folders){
            newModel.addElement(aFolder);
            
        }
        return newModel;
    }
    
    //Add a folder to the file
    public boolean addFolder(VernaFolder e) {
        boolean success = folders.add(e);
        return success;
    }

    public void removeFolder(VernaFolder folderToRemove) {
        folders.remove(folderToRemove);
    }
    //Read the folders from the file

    public void readFromFile(File aFile) {
        FileInputStream FileIS = null;
        try {

            if (!aFile.exists()) {
                throw new Exception("Tried to read from non-existant file: " + aFile.getAbsolutePath());
            }
            FileIS = new FileInputStream(aFile);
            ObjectInputStream rawr = new ObjectInputStream(FileIS);

            Object ReadObject = rawr.readObject();
            if (ReadObject.getClass() == ArrayList.class) {
                folders = new ArrayList<VernaFolder>();
                for (Object anObject : (ArrayList) ReadObject) {
                    if (anObject.getClass() == VernaFolder.class) {
                        folders.add((VernaFolder) anObject);
                    }
                }
                
            } else {
                throw new Exception("Class mismatch when loading configuration file.");
            }


        } catch (Exception ex) {
            if (ex.getMessage().contains("local class incompatible")) {
                JOptionPane.showMessageDialog(null, "Not a compatable File");

            }
            Logger.getLogger(FolderListSerializer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                FileIS.close();
            } catch (IOException ex) {
                Logger.getLogger(FolderListSerializer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void writeToFile(File target) {
        FileOutputStream fout = null;
        try {
            System.out.println("Saving : " + target.getName());
            if (!target.exists()) {
                target.createNewFile();
            }
            fout = new FileOutputStream(target);

            ObjectOutputStream rawr = new ObjectOutputStream(fout);
            rawr.writeObject(folders);

        } catch (Exception ex) {
            Logger.getLogger(FolderListSerializer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(FolderListSerializer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int performSaveAsDialog(Frame Parent) {
        JFileChooser rawr = new JFileChooser(new File("./"));
        rawr.setFileFilter(new FbcFileFilter());
        rawr.setDialogType(JFileChooser.SAVE_DIALOG);


        int option = rawr.showDialog(Parent, null);
        if (option == JFileChooser.APPROVE_OPTION) {
            String Extension = FileWrapper.getExtension(rawr.getSelectedFile());
            if (Extension.isEmpty()) {
                System.out.println("No extension on file!");
            }


            writeToFile(rawr.getSelectedFile());
            return SAVE_SUCCESS;
        }
        else{
            return SAVE_CANCELLED;
        }

    }
}
